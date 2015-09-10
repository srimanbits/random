ALTER PROCEDURE [Review].[ReturnEmployeeMails] (
 @statusMessage1 NVARCHAR(30)
 ,@statusMessage2 NVARCHAR(30)
 ,@statusMessage3 NVARCHAR(30)
 ,@reviewCycleId INT
 ,@daysRemaining INT
 )
AS
BEGIN
 SELECT  DISTINCT cast(mgr.Email as nvarchar(100)) AS 'managerEmailId',cast(mgr.EmpId as int)AS 'managerId', cast(header.ReviewHeaderId as int)'reviewHeaderId',  cast(emp.Email as nvarchar(100)) AS 'employeeEmailId', cast(emp.DisplayName as nvarchar(100)) AS 'employeeName',
cast(mgr.DisplayName as nvarchar(100))AS 'managerName', reminderMessages.DaysRemaining as 'pendingDays', reminderMessages.Message as 'message',
reminderMessages.Subject as 'subject',header.ReviewStatusCode as 'employeeStatus',employeeRole.RoleId as 'role'
,reviewCycle.AppraisalPeriodTypeId as 'appraisalPeriodTypeId'
 FROM  [Gal].Employee emp ,[Gal].[Employee] mgr, [Review].ReminderMessages reminderMessages,[Review].ReviewHeader header,[Review].Employee_Role employeeRole,
 Review.ReviewCycle reviewCycle 
 WHERE 
    emp.IsActive = 1
 AND mgr.EmpId = header.MainAppraiserEmployeeId
 AND mgr.IsActive=1
 AND header.EmployeeId=emp.EmpId
 AND employeeRole.EmployeeId=emp.managerEmpId
 AND (header.ReviewStatusCode=@statusMessage1
 OR header.ReviewStatusCode=@statusMessage2
 OR header.ReviewStatusCode=@statusMessage3)
 AND reminderMessages.Status=@statusMessage1
 AND reminderMessages.IsActive=1
 AND reminderMessages.DaysRemaining=@daysRemaining
 AND header.ReviewCycleId=@reviewCycleId
 AND header.ReviewCycleId = reviewCycle.ReviewCycleId
 AND reviewCycle.AppraisalPeriodTypeId=reminderMessages.AppraisalPeriodTypeId


 
 
END
GO