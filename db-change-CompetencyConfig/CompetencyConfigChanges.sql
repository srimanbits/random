-- Preconditions

--Check if there is a table called Gal.Department
--Check if there is a column called 'DepartmentId' in 'Gal.Employee' table which references to Gal.Department
--If DepartmentId exists in Gal.Employee , Default all employees department to 'Technical' 
--(Select DepartmentId From Gal.Department Where DepartmentName='Technology') 


-- Add DeptId to [Review].[GradeCompetencyExpectation]

Alter table [Review].[GradeCompetencyExpectation]  add [DeptId] [int] NULL

ALTER TABLE [Review].[GradeCompetencyExpectation]  WITH CHECK ADD  CONSTRAINT [FK_GradeCompetencyExpectation_Department] FOREIGN KEY([DeptId])
REFERENCES [Gal].[Department] ([DepartmentId])
GO

-- If Management is not included in the Department then execute below insert query

Select DepartmentId From Gal.Department Where DepartmentName='Management'

INSERT INTO [Gal].[Department] ([DepartmentName],[DepartmentLongName])
     VALUES ('Management','Management')

-- Check if below query returns a row.

Select DepartmentId From Gal.Department Where DepartmentName='Technology'



-- Set currently configured values to 'Technology'

Update Review.GradeCompetencyExpectation set DeptId = ? (Select DepartmentId From Gal.Department Where DepartmentName='Technology')


-- Update below SPROC in GGKPortal

/****** Object:  StoredProcedure [Review].[CreateReviewCompetencies]    Script Date: 7/3/2015 5:21:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER PROCEDURE [Review].[CreateReviewCompetencies]
(@reviewHeaderId int,@employeeId int,@owner varchar(40))
AS
BEGIN
DECLARE @GradeId int
DECLARE @DeptId int
DECLARE @Count int
SET @Count= 0

-- Get GradeId of Employee
SET @GradeId=( Select employee.GradeId From Gal.Employee employee Where  employee.EmpId=@employeeId )

-- Get DeptId of Employee
SET @DeptId=( Select employee.DepartmentId From Gal.Employee employee Where  employee.EmpId=@employeeId )

-- If DeptId is null , set Dept = 'Technology' (Default one)
If @DeptId is null
SET @DeptId = ( Select DepartmentId From Gal.Department Where DepartmentName='Technology' )

-- Count no of competencies configured for @GradeId , @DeptId
SET @Count = (

Select Count (profCompetency.CompetencyId)
From review.reviewHeader rh
Join gal.Employee emp
On rh.EmployeeId=emp.EmpId
Join review.GradeCompetencyExpectation expectation
On expectation.GradeId= @GradeId and expectation.DeptId = @DeptId
Join review.ProficiencyLevelCompetency profCompetency
On expectation.ProficiencyLevelCompetencyId=profCompetency.ProficiencyLevelCompetencyId
Where ReviewHeaderId=@reviewHeaderId

)

Print @Count 
-- If @count == 0 , then insert all competencies with ExpectedProficiencyLevel as null.
If @Count = 0
Insert into review.ReviewCompetency
(CompetencyId, ReviewHeaderId,CreatedDate, LastModifiedDate,Owner) 
Select CompetencyId,@reviewHeaderId,GETDATE(),GETDATE(),@owner 
From Review.Competency


Else

Insert Into review.ReviewCompetency
(CompetencyId, ReviewHeaderId,CreatedDate, LastModifiedDate, ExpectedProficiencyLevelId,ExpectedProficiencyLevelIdAsPerGrade,Owner) 
Select profCompetency.CompetencyId, rh.ReviewHeaderId, 
GETDATE(),
GETDATE(),
profCompetency.ProficiencyLevelId,profCompetency.ProficiencyLevelId,@owner
From review.reviewHeader rh
Join gal.Employee emp
On rh.EmployeeId=emp.EmpId
Join review.GradeCompetencyExpectation expectation
On expectation.GradeId= @GradeId and expectation.DeptId = @DeptId
Join review.ProficiencyLevelCompetency profCompetency
On expectation.ProficiencyLevelCompetencyId=profCompetency.ProficiencyLevelCompetencyId
Where ReviewHeaderId=@reviewHeaderId;
End