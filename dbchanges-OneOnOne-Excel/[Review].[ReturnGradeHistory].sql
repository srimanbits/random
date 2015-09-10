/****** Object:  StoredProcedure [Review].[ReturnGradeHistory]    Script Date: 5/6/2015 3:19:42 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [Review].[ReturnGradeHistory] (@empId INT)
as 
begin

WITH Desg_CTE AS (
SELECT Top 1 eaa.EmpId,eaa.DisplayName,eaa.GradeId,eaa.LastUpdateDate,eaa.LastModifiedByUserId,eaa.DOJ FROM Hist.Employee_Archive eaa 
WHERE eaa.EmpId=@empId and eaa.GradeId is not null
order by eaa.LastUpdateDate asc
UNION ALL
SELECT CASE WHEN ROW_NUMBER() OVER (ORDER BY ear.LastUpdateDate) > 1 
         THEN NULL 
         ELSE ear.EmpId END AS EmpId,ear.DisplayName,ear.GradeId,ear.LastUpdateDate,ear.LastModifiedByUserId,ear.DOJ
FROM Hist.Employee_Archive ear
 inner JOIN Desg_CTE rcte 
ON  ear.EmpId =rcte.EmpId 
	
WHERE ear.LastUpdateDate > rcte.LastUpdateDate and ear.GradeId <> rcte.GradeId  

)
SELECT  cte.DisplayName as displayName,cte.DOJ as DOJ, g.Grade as grade,cte.LastUpdateDate as lastUpdatedDate,updationEmp.DisplayName LastModifiedByUser
FROM Desg_CTE cte
left join
Gal.Grade g on g.GradeId=cte.GradeId
left join
dbo.[User] usr on usr.userId = cte.LastModifiedByUserId
left Join
Gal.Employee updationEmp on usr.EmpId=updationEmp.EmpId
 where cte.EmpId is not null
;

end