/****** Object:  StoredProcedure [Review].[GetReviewLinksCount]    Script Date: 6/3/2015 8:43:15 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER Procedure [Review].[GetReviewLinksCount](@managerEmpid int,@reviewCycleId int ,@searchTerm varchar(max)) as
begin 
Declare @statement nvarchar(max)
Declare @dynamicStatement nvarchar(max)=''
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
select
count(*)
from
(
SELECT ROW_NUMBER () over (order by outerCte.flag desc ,outerCte.mainAppraiserEmployeeId, outerCte.displayName) rn,outerCte.*,status.ReviewStatusDescription as statusDescription,grade.grade as grade ,desg.shortName as designation
FROM ReviewForms_CTE outerCte
left join
Gal.Grade grade
on outerCte.gradeId = grade.gradeId
left join
Gal.Designation desg
on outerCte.designationId = desg.id
join
Review.ReviewStatus status
on status.ReviewStatusCode = outerCte.reviewFormStatus
where'+ @dynamicStatement +'

)  finalResult'
Execute SP_ExecuteSQL  @statement 
end