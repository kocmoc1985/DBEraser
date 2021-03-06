SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe.csv]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe.csv](
	[id] [int] NOT NULL,
	[Field1] [nchar](20) NOT NULL,
	[Field2] [nchar](20) NOT NULL,
	[Field3] [nchar](20) NOT NULL,
	[Field4] [nchar](20) NOT NULL,
	[Field5] [nchar](20) NOT NULL,
	[Field6] [nchar](20) NOT NULL,
	[Field7] [nchar](20) NOT NULL,
	[Field8] [nchar](20) NOT NULL,
	[Field9] [nchar](20) NOT NULL,
	[Field10] [nchar](20) NOT NULL,
	[Field11] [nchar](20) NOT NULL,
	[Field12] [nchar](20) NOT NULL,
	[Field13] [nchar](20) NOT NULL,
	[Field14] [nchar](20) NOT NULL,
	[Field15] [nchar](20) NOT NULL,
	[Field16] [nchar](20) NOT NULL,
	[Field17] [nchar](20) NOT NULL,
	[Field18] [nchar](20) NOT NULL,
	[Field19] [nchar](20) NOT NULL,
	[Field20] [nchar](20) NOT NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CASNO]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[CASNO](
	[EC_Number] [nchar](10) NULL,
	[CAS_Number] [nchar](12) NULL,
	[ChemName] [nvarchar](max) NULL,
	[ChemNameAlt] [nvarchar](max) NULL,
	[Date_Reg] [datetime] NULL,
	[UpdatedOn] [datetime] NULL,
	[UpdatedBy] [nchar](3) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingred_Group]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingred_Group](
	[Id] [bigint] IDENTITY(1,1) NOT NULL,
	[Grupp] [varchar](50) NULL,
	[Descr] [nvarchar](255) NULL,
 CONSTRAINT [PK_Ingred_Group] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_Ingred_Group] UNIQUE NONCLUSTERED 
(
	[Grupp] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Group]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Group](
	[Recipe_Group_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Main_Group] [varchar](50) NULL,
	[main_Info] [varchar](50) NULL,
	[Detailed_Group] [varchar](50) NULL,
	[Detailed_Info] [varchar](50) NULL,
	[Note_Main] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [char](10) NULL,
 CONSTRAINT [PK_Recipe_Group] PRIMARY KEY CLUSTERED 
(
	[Recipe_Group_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_Recipe_Group_1] UNIQUE NONCLUSTERED 
(
	[Detailed_Group] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
