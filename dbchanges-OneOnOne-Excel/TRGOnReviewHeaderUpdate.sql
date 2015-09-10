/****** Object:  Trigger [Review].[TRGOnReviewHeaderUpdate]    Script Date: 7/24/2015 3:46:03 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
ALTER TRIGGER [Review].[TRGOnReviewHeaderUpdate]
ON [Review].[ReviewHeader] AFTER UPDATE
AS 
   INSERT INTO [Review].[FeedbackRequest] (OnEmployeeId ,ReviewHeaderId, ToEmployeeId , FromEmployeeId , RequestedDate,RequestDueDate,IsPendingAssistance,type)
      SELECT d.EmployeeId, i.ReviewHeaderId, i.MainAppraiserEmployeeId ,d.MainAppraiserEmployeeId,GetDate(),GetDate()+5,1,'projectchange'
      FROM Inserted i
      INNER JOIN 
	  Deleted d ON d.ReviewHeaderId = i.ReviewHeaderId and d.MainAppraiserEmployeeId <> i.MainAppraiserEmployeeId and i.MainAppraiserEmployeeId <> i.EmployeeId