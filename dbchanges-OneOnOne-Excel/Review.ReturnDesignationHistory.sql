/****** Object:  StoredProcedure [Review].[ReturnDesignationHistory]    Script Date: 5/6/2015 3:18:58 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE procedure [Review].[ReturnDesignationHistory] (@empId INT)
as 
begin

WITH Desg_CTE AS (
SELECT Top 1 eaa.EmpId,eaa.DisplayName,eaa.DesgId,eaa.LastUpdateDate,eaa.LastModifiedByUserId FROM Hist.Employee_Archive eaa 
WHERE eaa.EmpId=@empId and eaa.DesgId is not null
order by eaa.LastUpdateDate asc
UNION ALL
SELECT CASE WHEN ROW_NUMBER() OVER (ORDER BY ear.LastUpdateDate) > 1 
         THEN NULL 
         ELSE ear.EmpId END AS EmpId,ear.DisplayName,ear.DesgId,ear.LastUpdateDate,ear.LastModifiedByUserId
FROM Hist.Employee_Archive ear
 inner JOIN Desg_CTE rcte 
ON  ear.EmpId =rcte.EmpId 
	
WHERE ear.LastUpdateDate > rcte.LastUpdateDate and ear.DesgId <> rcte.DesgId  

)
SELECT  cte.DisplayName as displayName, d.ShortName as designation,cte.LastUpdateDate as lastUpdatedDate ,updationEmp.DisplayName as LastModifiedByUser
FROM Desg_CTE cte
left join
Gal.Designation d on d.Id=cte.DesgId
left join
dbo.[User] usr on usr.userId = cte.LastModifiedByUserId
left Join
Gal.Employee updationEmp on usr.EmpId=updationEmp.EmpId
 where cte.EmpId is not null

end