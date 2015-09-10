USE [GGKPortal_DEV]
GO
/****** Object:  StoredProcedure [Review].[ReturnCommentTextCount]    Script Date: 5/6/2015 3:17:00 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO



CREATE procedure [Review].[ReturnCommentTextCount] (@reviewHeaderId INT,@reviewFormRole varchar(20))
as 
begin


select  COUNT(*) OVER () AS TotalRecords
 from Review.ReviewActionLog A
 where A.ReviewHeaderId=@reviewHeaderId and A.CreatedByRole =(case when @reviewFormRole='APPRAISE' then 'APPRAISE' else A.CreatedByRole end )
 group by 
 A.CreatedByName
,A.CreatedByRole
,A.ReviewFieldGroupType 
,convert(varchar(30),A.CreatedDate,120)


end