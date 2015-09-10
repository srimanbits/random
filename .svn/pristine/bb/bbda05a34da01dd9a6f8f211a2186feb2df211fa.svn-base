-- Add a column called 'ExpectedProficiencyLevelIdAsPerGrade' in Review.ReviewCompetency.

ALTER TABLE [Review].[ReviewCompetency] ADD ExpectedProficiencyLevelIdAsPerGrade int NULL

-- Add forign key constrain to 'ExpectedProficiencyLevelIdAsPerGrade' column

ALTER TABLE [Review].[ReviewCompetency]  WITH CHECK ADD  CONSTRAINT [FK_ReviewCompetency_Expected_ProficiencyLevel_AsPerGrade] FOREIGN KEY([ExpectedProficiencyLevelIdAsPerGrade])
REFERENCES [Review].[ProficiencyLevel] ([ProficiencyLevelId])
GO

-- Add a column called 'ExpectedProficiencyLevelIdAsPerGrade' in History.ReviewCompetencies

ALTER TABLE [History].[ReviewCompetency] ADD ExpectedProficiencyLevelIdAsPerGrade int NULL



-- Update SPROC 'Review.CreateReviewCompetencies' -  See attachement  'Review.CreateReviewCompetencies'
-- Update SPROC 'Review.CopyReviewCompetencies' - See attachment 'Review.CopyReviewCompetencies'

-- Add a column called 'ReviewCycleStatus' in Review.ReviewCycle

ALTER TABLE review.ReviewCycle ADD ReviewCycleStatus VARCHAR(45) DEFAULT 'COMPLETED'

-- Update SPROC 'Review.CreateReviewCycle' -- See attchement 'Review.CreateReviewCycle'

-- Add a table called 'Review.ReminderMessages' -- See attachment 'Review.ReminderMessages'

-- Update SPROC 'Review.ReturnEmployeeMails' -- See attachment 'Review.ReturnEmployeeMails'



