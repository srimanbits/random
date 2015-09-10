/****** Object:  Table [Review].[ONOMinutes]    Script Date: 5/6/2015 3:36:09 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [Review].[ONOMinutes](
	[ONOMinutesId] [int] IDENTITY(1,1) NOT NULL,
	[AppraiseId] [int] NOT NULL,
	[AppraiserId] [int] NULL,
	[MeetingMinutes] [nvarchar](max) NULL,
	[CreatedDate] [datetime] NULL,
	[LastUpdatedDate] [datetime] NULL,
	[CreatedId] [int] NOT NULL,
	[LastUpdatedId] [int] NOT NULL,
	[IsApproved] [bit] NOT NULL DEFAULT ((0)),
 CONSTRAINT [ONOMinutes_Id] PRIMARY KEY CLUSTERED 
(
	[ONOMinutesId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [Review].[ONOMinutes]  WITH CHECK ADD  CONSTRAINT [FK_ONOMinutes_Appraise] FOREIGN KEY([AppraiseId])
REFERENCES [Gal].[Employee] ([EmpId])
GO

ALTER TABLE [Review].[ONOMinutes] CHECK CONSTRAINT [FK_ONOMinutes_Appraise]
GO

ALTER TABLE [Review].[ONOMinutes]  WITH CHECK ADD  CONSTRAINT [FK_ONOMinutes_Appraiser] FOREIGN KEY([AppraiserId])
REFERENCES [Gal].[Employee] ([EmpId])
GO

ALTER TABLE [Review].[ONOMinutes] CHECK CONSTRAINT [FK_ONOMinutes_Appraiser]
GO

ALTER TABLE [Review].[ONOMinutes]  WITH CHECK ADD  CONSTRAINT [FK_ONOMinutes_Created] FOREIGN KEY([CreatedId])
REFERENCES [Gal].[Employee] ([EmpId])
GO

ALTER TABLE [Review].[ONOMinutes] CHECK CONSTRAINT [FK_ONOMinutes_Created]
GO

ALTER TABLE [Review].[ONOMinutes]  WITH CHECK ADD  CONSTRAINT [FK_ONOMinutes_LastUpdated] FOREIGN KEY([LastUpdatedId])
REFERENCES [Gal].[Employee] ([EmpId])
GO

ALTER TABLE [Review].[ONOMinutes] CHECK CONSTRAINT [FK_ONOMinutes_LastUpdated]
GO


