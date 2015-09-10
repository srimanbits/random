ALTER procedure [Review].[CopyReviewCompetencies](@reviewHeaderId int,@target Varchar(40),@actionType varchar(100)) as
BEGIN

DECLARE @CompetencyId int
DECLARE @AppraiseRatingId int
DECLARE @AppraiserRatingId int
DECLARE @AppraiseCommentId int
DECLARE @AppraiserCommentId int
DECLARE @ExpectedProficiencyLevelId int
DECLARE @ExpectedProficiencyLevelIdAsPerGrade int

-- moving records to history .

INSERT INTO [History].[ReviewCompetency]
           ([ReviewCompetencyId]
            ,[CompetencyId]
			,[AppraiseRatingId]
			,[AppraiserRatingId]
			,[AppraiseCommentId]
			,[AppraiserCommentId]
			,[ReviewHeaderId]
			,[CreatedDate]
			,[LastModifiedDate]
			,[ExpectedProficiencyLevelId]
			,[Owner]
			,[ExpectedProficiencyLevelIdAsPerGrade]
			,[Action]
			,[ActionTime])
     select r.*,@actionType,GETDATE() from Review.ReviewCompetency R where R.ReviewHeaderId=@reviewHeaderId and R.Owner != @target


--- deleting target version competencies 

delete from Review.ReviewCompetency where ReviewHeaderId=@reviewHeaderId and owner=@target



DECLARE cursor_competencies CURSOR -- Declare and initialise cursor

LOCAL SCROLL STATIC
 
FOR
Select 
            [CompetencyId]
			,[AppraiseRatingId]
			,[AppraiserRatingId]
			,[AppraiseCommentId]
			,[AppraiserCommentId]
			,[ExpectedProficiencyLevelId]
			,[ExpectedProficiencyLevelIdAsPerGrade]
from Review.ReviewCompetency RC where RC.ReviewHeaderId=@reviewHeaderId and RC.[Owner]!=@target

open cursor_competencies --open the cursor
FETCH NEXT FROM cursor_competencies INTO
@CompetencyId ,
@AppraiseRatingId ,
@AppraiserRatingId ,
@AppraiseCommentId ,
@AppraiserCommentId ,
@ExpectedProficiencyLevelId,
@ExpectedProficiencyLevelIdAsPerGrade
WHILE @@Fetch_status = 0
BEGIN
DEclare @Temp_Competency Table(
Id  int,fieldname varchar(50)
)
--AppraiseCommentId
insert into Review.Comment(CommentText)
output inserted.CommentId,'AppraiseCommentId' into @Temp_Competency(Id,fieldname) 
select CommentText from Review.Comment
where CommentId=@AppraiseCommentId

--AppraiserCommentId
insert into Review.Comment(CommentText)
output inserted.CommentId,'AppraiserCommentId' into @Temp_Competency(Id,fieldname) 
select CommentText from Review.Comment
where CommentId=@AppraiserCommentId

INSERT INTO [Review].[ReviewCompetency]
           ([CompetencyId]
			,[AppraiseRatingId]
			,[AppraiserRatingId]
			,[AppraiseCommentId]
			,[AppraiserCommentId]
			,[ReviewHeaderId]
			,[CreatedDate]
			,[LastModifiedDate]
			,[ExpectedProficiencyLevelId]
            ,[Owner]
			,[ExpectedProficiencyLevelIdAsPerGrade])
     VALUES
     
     (@CompetencyId,@AppraiseRatingId,@AppraiserRatingId,(select Id from @Temp_Competency where fieldname ='AppraiseCommentId'),
     (select Id from @Temp_Competency where fieldname ='AppraiserCommentId'),
     @reviewHeaderId,GetDate(),GetDate(),@ExpectedProficiencyLevelId,@target,@ExpectedProficiencyLevelIdAsPerGrade
     )
FETCH NEXT FROM cursor_competencies INTO 
 @CompetencyId ,
 @AppraiseRatingId ,
 @AppraiserRatingId ,
 @AppraiseCommentId ,
 @AppraiserCommentId ,
 @ExpectedProficiencyLevelId,
 @ExpectedProficiencyLevelIdAsPerGrade
delete from @Temp_Competency
END
End