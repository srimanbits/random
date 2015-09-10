/****** Object:  Table [Review].[EmpSelfNotes]    Script Date: 5/6/2015 3:36:41 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [Review].[EmpSelfNotes](
	[SelfNotesId] [int] IDENTITY(1,1) NOT NULL,
	[EmployeeId] [int] NOT NULL,
	[Notes] [nvarchar](max) NULL,
	[TempNotes] [nvarchar](max) NULL,
	[LastUpdatedDate] [datetime] NULL,
 CONSTRAINT [PK_Employee_SelfNotes] PRIMARY KEY CLUSTERED 
(
	[SelfNotesId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO

ALTER TABLE [Review].[EmpSelfNotes]  WITH CHECK ADD  CONSTRAINT [FK_SelfNotes_emp] FOREIGN KEY([EmployeeId])
REFERENCES [Gal].[Employee] ([EmpId])
GO

ALTER TABLE [Review].[EmpSelfNotes] CHECK CONSTRAINT [FK_SelfNotes_emp]
GO