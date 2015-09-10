ALTER PROCEDURE [Review].[CreateReviewCompetencies]
(@reviewHeaderId int,@employeeId int,@owner varchar(40))
as
begin
DECLARE @GradeId int

SET @GradeId=(
Select employee.GradeId from 
Gal.Employee employee
where  employee.EmpId=@employeeId
)

print @GradeId 
if @GradeId is null
insert into review.ReviewCompetency
(CompetencyId, ReviewHeaderId,CreatedDate, LastModifiedDate,Owner) 
select CompetencyId,@reviewHeaderId,GETDATE(),GETDATE(),@owner 
from Review.Competency


else

insert into review.ReviewCompetency
(CompetencyId, ReviewHeaderId,CreatedDate, LastModifiedDate, ExpectedProficiencyLevelId,ExpectedProficiencyLevelIdAsPerGrade,Owner) 
select profCompetency.CompetencyId, rh.ReviewHeaderId, 
GETDATE(),
GETDATE(),
profCompetency.ProficiencyLevelId,profCompetency.ProficiencyLevelId,@owner
from review.reviewHeader rh
join gal.Employee emp
on rh.EmployeeId=emp.EmpId
join review.GradeCompetencyExpectation expectation
on emp.GradeId=expectation.GradeId
join review.ProficiencyLevelCompetency profCompetency
on expectation.ProficiencyLevelCompetencyId=profCompetency.ProficiencyLevelCompetencyId
where ReviewHeaderId=@reviewHeaderId;
end