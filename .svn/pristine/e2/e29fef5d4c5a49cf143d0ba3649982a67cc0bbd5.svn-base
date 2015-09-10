/****** Object:  StoredProcedure [Review].[ReturnSubOrdinates]    Script Date: 5/6/2015 3:18:36 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [Review].[ReturnSubOrdinates] (@managerEmployeeId int, @parameter nvarchar(20))
AS
BEGIN
WITH Employee_CTE AS(

SELECT emp.EmpId,emp.DisplayName, cast(emp.Email as nvarchar(100)) as Email
	FROM [Gal].[Employee] emp
	WHERE emp.IsActive = 1
	AND emp.managerEmpId = @managerEmployeeId

	UNION ALL
	SELECT emp.EmpId,emp.DisplayName, cast(emp.Email as nvarchar(100)) as Email
	FROM [Gal].[Employee] emp ,Employee_CTE cte
	WHERE emp.IsActive = 1
	AND emp.managerEmpId = cte.EmpId
)
SElect * from
Employee_CTE where DisplayName like @parameter+'%'
	
END