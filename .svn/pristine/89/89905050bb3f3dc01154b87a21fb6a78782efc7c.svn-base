ALTER PROCEDURE [Review].[CreateReviewCycle] (
@empId int,
@reviewCycleId varchar(10),
@appraisalPeriodTypeId varchar (1)
)
as 
begin 

/*Create ReviewForm for each employee in Gal.Employee */
Insert into Review.ReviewHeader(EmployeeId,ReviewCycleId,CreatedDate,LastModifiedDate,CreatedByEmployeeId,MainAppraiserEmployeeId,ReviewStatusCode,LastModifiedByEmployeeId,CurrentGradeId,CurrentDesgId)
select EmpId,@reviewCycleId,GETDATE(),GETDATE(),@empId,managerEmpId,'NOT_STARTED',@empId,GradeId,DesgId from
Gal.Employee emp LEFT JOIN Review.ReviewHeader rh ON emp.EmpId = rh.EmployeeId and rh.ReviewCycleId = @reviewCycleId
where emp.IsActive=1 and emp.AppraisalPeriodTypeId= @appraisalPeriodTypeId and rh.ReviewHeaderId is null

end