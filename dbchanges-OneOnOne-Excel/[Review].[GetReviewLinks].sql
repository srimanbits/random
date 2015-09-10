/****** Object:  StoredProcedure [Review].[GetReviewLinks]    Script Date: 6/3/2015 8:43:17 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER Procedure [Review].[GetReviewLinks](@managerEmpid int,@reviewCycleId int ,@start int, @stop int,@searchTerm varchar(max),@colIndex varchar(max),@sortDir varchar(max)) as
begin 
Declare @statement nvarchar(max)
Declare @dynamicStatement nvarchar(max)=''
Declare @colName nvarchar(max)
if(@colIndex=1)
	set @colName='outerCte.displayName ' +@sortDir
else if(@colIndex=4)
	set @colName='status.ReviewStatusDescription '+ @sortDir
else if(@colIndex=3)
	set @colName='grade.Grade '+ @sortDir +',desg.ShortName '+ @sortDir
else if(@colIndex=5)
	set @colName='outerCte.mainAppraiserEmployeeId '+ @sortDir
else if(@colIndex=6)
	set @colName='outerCte.project '+@sortDir 

If @searchTerm is not null
set @dynamicStatement = ' ( lower(outerCte.displayName) like ''%'+@searchTerm+'%'' 
OR lower(outerCte.email) like ''%'+@searchTerm+'%'' 
OR lower(grade.grade) like ''%'+@searchTerm+'%''
OR lower(desg.shortName) like ''%'+@searchTerm+'%''
OR lower(outerCte.project) like ''%'+@searchTerm+'%''
OR lower(status.ReviewStatusDescription) like ''%'+@searchTerm+'%''
OR lower(outerCte.mainAppraiserDisplayName) like ''%'+@searchTerm+'%'' )'
Set @statement=' 
WITH ReviewForms_CTE AS (
select rh.ReviewHeaderId as reviewHeaderId,emp.DisplayName as displayName,emp.Email as email,rc.ReviewCycleName as reviewCycleName,
rh.ReviewStatusCode as reviewFormStatus,emp.EmpId as employeeId,mgr.EmpId as mainAppraiserEmployeeId,
mgr.DisplayName as mainAppraiserDisplayName,rh.CurrentGradeId as gradeId,rh.CurrentDesgId as designationId,emp.defaultProject as project,1 as flag
from Review.ReviewHeader rh
 join
Review.ReviewCycle rc
on rc.ReviewCycleId = rh.ReviewCycleId
 join
Gal.Employee emp
on emp.EmpId = rh.EmployeeId and emp.isActive=1
 join
Gal.Employee mgr
on mgr.EmpId = rh.MainAppraiserEmployeeId
where rc.ReviewCycleId ='+CONVERT(varchar(10), @reviewCycleId)  +'and rh.MainAppraiserEmployeeId ='+ CONVERT(varchar(10), @managerEmpid) +
'UNION ALL
select rh.ReviewHeaderId as reviewHeaderId,emp.DisplayName as displayName,emp.Email as email,rc.ReviewCycleName as reviewCycleName,
rh.ReviewStatusCode as reviewFormStatus,emp.EmpId as employeeId,mgr.EmpId as mainAppraiserEmployeeId,
mgr.DisplayName as mainAppraiserDisplayName,rh.CurrentGradeId as gradeId,rh.CurrentDesgId as designationId,emp.defaultProject as project,0 as flag
from Review.ReviewHeader rh
 join
Review.ReviewCycle rc
on rc.ReviewCycleId = rh.ReviewCycleId
 join
Gal.Employee emp
on emp.EmpId = rh.EmployeeId and emp.isActive=1
 join
Gal.Employee mgr
on mgr.EmpId = rh.MainAppraiserEmployeeId

INNER JOIN ReviewForms_CTE cte
on rh.MainAppraiserEmployeeId = cte.employeeId
where rc.ReviewCycleId ='+CONVERT(varchar(10), @reviewCycleId) +
')
select reviewHeaderId as reviewHeaderId,displayName as employeeDisplayName,cast(email as varchar(50)) as email,
reviewCycleName,
reviewFormStatus,employeeId, mainAppraiserEmployeeId,
mainAppraiserDisplayName,grade, designation,project,sharedAppraiserId,sharedByEmpId as assignedByEmployeeId,
sharedByEmployeeName as assignedByEmployeeName,sharedToEmpId as assignedToEmployeeId,sharedToEmployeeName as assignedToEmployeeName
from
(
SELECT ROW_NUMBER () over (order by outerCte.flag desc ,'+@colName+') rn,outerCte.*,status.ReviewStatusDescription as statusDescription,
grade.grade as grade ,desg.shortName as designation,shared.sharedAppraiserId as sharedAppraiserId,sharedBy.EmpId as sharedByEmpId,sharedBy.Displayname as sharedByEmployeeName,
sharedTo.EmpId as sharedToEmpId,sharedTo.Displayname as sharedToEmployeeName

FROM ReviewForms_CTE outerCte
left join
Gal.Grade grade
on outerCte.gradeId = grade.gradeId
left join
Gal.Designation desg
on outerCte.designationId = desg.id
left join
Review.SharedAppraiser shared 
on outerCte.reviewHeaderId = shared.reviewHeaderId and shared.isActive=1
left join
Gal.Employee sharedBy
on sharedBy.EmpId = shared.AssignedByEmployeeId
left join
Gal.Employee sharedTo
on sharedTo.EmpId = shared.AssignedToEmployeeId
join
Review.ReviewStatus status
on status.ReviewStatusCode = outerCte.reviewFormStatus
where'+ @dynamicStatement +'

)  finalResult'
if (@stop!=-1)
set @statement=@statement+' where rn between ' +CONVERT(varchar(10), @start) +' and ' +CONVERT(varchar(10), @stop)
Execute SP_ExecuteSQL  @statement 
end