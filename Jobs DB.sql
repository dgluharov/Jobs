USE [master]
GO
/****** Object:  Database [Jobs]    Script Date: 11-May-19 6:02:45 PM ******/
CREATE DATABASE [Jobs]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Jobs', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\Jobs.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Jobs_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\Jobs_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [Jobs] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Jobs].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Jobs] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Jobs] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Jobs] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Jobs] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Jobs] SET ARITHABORT OFF 
GO
ALTER DATABASE [Jobs] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Jobs] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Jobs] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Jobs] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Jobs] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Jobs] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Jobs] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Jobs] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Jobs] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Jobs] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Jobs] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Jobs] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Jobs] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Jobs] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Jobs] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Jobs] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Jobs] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Jobs] SET RECOVERY FULL 
GO
ALTER DATABASE [Jobs] SET  MULTI_USER 
GO
ALTER DATABASE [Jobs] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Jobs] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Jobs] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Jobs] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Jobs] SET DELAYED_DURABILITY = DISABLED 
GO
EXEC sys.sp_db_vardecimal_storage_format N'Jobs', N'ON'
GO
ALTER DATABASE [Jobs] SET QUERY_STORE = OFF
GO
USE [Jobs]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 11-May-19 6:02:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](50) NOT NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[JobsAd]    Script Date: 11-May-19 6:02:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[JobsAd](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Description] [nvarchar](max) NOT NULL,
	[IsActive] [bit] NOT NULL,
	[EmployerId] [int] NOT NULL,
	[CategoryId] [int] NOT NULL,
 CONSTRAINT [PK_JobsAd] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 11-May-19 6:02:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employee](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [AK_Name] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Records]    Script Date: 11-May-19 6:02:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Records](
	[EmployeeId] [int] NOT NULL,
	[JobsAdId] [int] NOT NULL,
 CONSTRAINT [PK_Records] PRIMARY KEY CLUSTERED 
(
	[EmployeeId] ASC,
	[JobsAdId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[ActiveAdsWithApplyingEmployees]    Script Date: 11-May-19 6:02:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[ActiveAdsWithApplyingEmployees]
AS
     SELEct Category.ID, Category.Name AS Category, 
            COUNT(JobsAd.ID) AS ActiveJobsAd, 
            COUNT(Employee.Name) AS ApplyingPeople
     FROM Category
          INNER JOIN JobsAd ON Category.Id = JobsAd.CategoryId
          INNER JOIN Records ON JobsAd.ID = Records.JobsAdId
          INNER JOIN Employee ON Records.EmployeeId = Employee.id
     WHERE JobsAd.IsActive = 1
     GROUP BY Category.Id, 
              Category.Name;
GO
/****** Object:  Table [dbo].[Employer]    Script Date: 11-May-19 6:02:45 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Employer](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
	[ActiveJobsAdCounter] [int] NOT NULL,
 CONSTRAINT [PK_Employer] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [Unique_Name] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Employer] ADD  CONSTRAINT [DF_Employer_JobsAdCounter]  DEFAULT ((0)) FOR [ActiveJobsAdCounter]
GO
ALTER TABLE [dbo].[JobsAd]  WITH CHECK ADD  CONSTRAINT [FK_JobsAd_Category] FOREIGN KEY([CategoryId])
REFERENCES [dbo].[Category] ([ID])
GO
ALTER TABLE [dbo].[JobsAd] CHECK CONSTRAINT [FK_JobsAd_Category]
GO
ALTER TABLE [dbo].[JobsAd]  WITH CHECK ADD  CONSTRAINT [FK_JobsAd_Employer] FOREIGN KEY([EmployerId])
REFERENCES [dbo].[Employer] ([ID])
GO
ALTER TABLE [dbo].[JobsAd] CHECK CONSTRAINT [FK_JobsAd_Employer]
GO
ALTER TABLE [dbo].[Records]  WITH CHECK ADD  CONSTRAINT [FK_Records_Employee] FOREIGN KEY([EmployeeId])
REFERENCES [dbo].[Employee] ([ID])
GO
ALTER TABLE [dbo].[Records] CHECK CONSTRAINT [FK_Records_Employee]
GO
ALTER TABLE [dbo].[Records]  WITH CHECK ADD  CONSTRAINT [FK_Records_JobsAd1] FOREIGN KEY([JobsAdId])
REFERENCES [dbo].[JobsAd] ([ID])
GO
ALTER TABLE [dbo].[Records] CHECK CONSTRAINT [FK_Records_JobsAd1]
GO
USE [master]
GO
ALTER DATABASE [Jobs] SET  READ_WRITE 
GO
