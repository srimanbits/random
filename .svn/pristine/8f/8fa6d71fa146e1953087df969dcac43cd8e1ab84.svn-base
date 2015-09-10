CREATE TABLE [Review].[ReminderMessages](
	[MessageId] [int] IDENTITY(1,1) NOT NULL,
	[DaysRemaining] [int] NULL,
	[Status] [nvarchar](50) NULL,
	[Message] [nvarchar](1000) NULL,
	[IsActive] [bit] NULL,
	[LastUpdatedDate] [datetime] NULL,
	[Subject] [nvarchar](50) NULL,
	[AppraisalPeriodTypeId] [int] NULL,
 CONSTRAINT [PK__Reminder__C87C0C9C0BCFFDC3] PRIMARY KEY CLUSTERED 
(
	[MessageId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [Review].[ReminderMessages]  WITH CHECK ADD  CONSTRAINT [FK_ReminderMessages_AppraisalPeriodTypeId] FOREIGN KEY([AppraisalPeriodTypeId])
REFERENCES [Review].[AppraisalPeriodType] ([AppraisalPeriodTypeId])
GO

ALTER TABLE [Review].[ReminderMessages] CHECK CONSTRAINT [FK_ReminderMessages_AppraisalPeriodTypeId]
GO