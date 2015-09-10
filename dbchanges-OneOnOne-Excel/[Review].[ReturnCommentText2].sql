/****** Object:  StoredProcedure [Review].[ReturnCommentText2]    Script Date: 5/6/2015 3:16:09 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER procedure [Review].[ReturnCommentText2] (@reviewHeaderId INT,@reviewFormRole varchar(20),@startIndex INT,@pageDisplayLength INT,@searchTerm varchar(20),@sortDirection INT,@colIndex INT)
as 
begin

with Final as (
select  A.CreatedByName,
ROW_NUMBER() OVER(ORDER BY CASE WHEN @SortDirection = 1 THEN
        convert(varchar(30),A.CreatedDate,120)
    END ASC
    , CASE WHEN @SortDirection = 0 THEN
        convert(varchar(30),A.CreatedDate,120)
    END DESC  ) as intRow
,A.CreatedByRole
,A.ReviewFieldGroupType as ReviewFieldGroupType
,convert(varchar(30),A.CreatedDate,120) AS CreatedDate,
CHAR(13)+(SELECT  isnull(cast (case when ActionType='ADD' then '  Added ' + 
                      case when   FieldName IS null then ' new ' +LOWER(ReviewFieldGroupType)+' with name '''+ReviewFieldGroupName+' '' ' else ''''+FieldName+'''  field in '''+ReviewFieldGroupName+''' '+ LOWER(ReviewFieldGroupType) +' With Value '''+ UpdatedValue+'''' end
                    --separate code for projects multi select
                    when ActionType='UPDATE' and UserCommentText IS null and FieldName='Projects' then 'Added/Deleted Projects to '+LOWER(ReviewFieldGroupType)+' '' ' +ReviewFieldGroupName+' '' ' +UpdatedValue
                    when ActionType='UPDATE' and UserCommentText IS null then '  Updated Value Of ''' +FieldName +'''  field in '''+case when ReviewFieldGroupName='Review Summary'then '' else ReviewFieldGroupName end+''' '+ LOWER(ReviewFieldGroupType) + ' From '''+InitialValue+''' To ''' +UpdatedValue +''''
                    
                    when ActionType='UPDATE' and UserCommentText IS not null then UserCommentText
                    
                    when ActionType='DELETE' then '  Deleted ' + LOWER(ReviewFieldGroupType)+' '' '+ReviewFieldGroupName+' '' '
                    
                    when ActionType='APPROVE_APPRAISAL' then LOWER(CreatedByRole)+' ( '+CreatedByName+' ) approved appraisal for appraise with internal comments as '''+UserCommentText+''''
                    when ActionType='APPROVE_GOALS' then LOWER(CreatedByRole)+' ( '+CreatedByName+' ) approved goals for appraise with internal comments as '''+UserCommentText+''''
                    when ActionType='RE_SUBMIT_TO_APPRAISE' then LOWER(CreatedByRole)+' ( '+CreatedByName+' ) resubmitted to appraise with internal comments as '''+UserCommentText+''''
                    when ActionType='SUBMIT' then LOWER(CreatedByRole)+' ( '+CreatedByName+' ) submitted to appraiser with internal comments as '''+UserCommentText+''''
                    WHEN ActionType='SHARE' then UserCommentText
                    WHEN ActionType='UNSHARE' then LOWER(CreatedByRole)+' ( '+CreatedByName+' ) unshared appraisal with internal comments as '''+UserCommentText+''''
                    WHEN ActionType='ACCEPT_GOALS' then LOWER(CreatedByRole)+' ( '+CreatedByName+' ) accepted goals with internal comments as '''+UserCommentText+''''
					WHEN ActionType='ACCEPT_APPRAISAL' then LOWER(CreatedByRole)+' ( '+CreatedByName+' ) accepted appraisal with internal comments as '''+UserCommentText+''''
  end +'DEL_COMMTMNTS'
   as VARCHAR(MAX)),'') FROM Review.ReviewActionLog B 
where A.CreatedByName=B.CreatedByName
and A.CreatedByRole=B.CreatedByRole
and A.ReviewFieldGroupType=B.ReviewFieldGroupType
and convert(varchar(30),A.CreatedDate,120)=convert(varchar(30),B.CreatedDate,120)
and B.reviewHeaderId=@reviewHeaderId and  B.CreatedByRole =(case when @reviewFormRole='APPRAISE' then 'APPRAISE' else B.CreatedByRole end )
FOR XML PATH('')
) CommentText
 from Review.ReviewActionLog A
 where A.ReviewHeaderId=@reviewHeaderId and A.CreatedByRole =(case when @reviewFormRole='APPRAISE' then 'APPRAISE' else A.CreatedByRole end )
group by 
 A.CreatedByName
,A.CreatedByRole
,A.ReviewFieldGroupType 
,convert(varchar(30),A.CreatedDate,120)
)
select  CreatedByName
,CreatedByRole
,ReviewFieldGroupType 
,CreatedDate,REPLACE(REPLACE(REPLACE(Replace (CommentText,'DEL_COMMTMNTS',CHAR(13)+CHAR(13)),'&#x0D;',CHAR(13)),'&lt;','<'),'&gt;','>') from Final
WHERE intRow BETWEEN @startIndex+1 AND @startIndex+@pageDisplayLength 
end