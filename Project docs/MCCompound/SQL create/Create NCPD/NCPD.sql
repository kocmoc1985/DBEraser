SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_ListOf_Masters]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'-- check in of reqursion in recipes 

CREATE FUNCTION [dbo].[Recipe_ListOf_Masters] (@rECIPEnAME NVARCHAR(50), @release NVARCHAR(1) ) 
RETURNS  @output TABLE  
 (MateriaL varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL, Recip varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	Info varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL)
 --  reqursive list of all recipes including masterbatches
begin 
 Declare @MatIndex  as varchar(50) 
 Declare @materiaL as varchar(50) 
 Declare @OldRecipe as varchar(50) 

declare @count as int
set @count=1
DECLARE _cursor CURSOR FOR 
SELECT     Recipe_Recipe.IngredName AS material, Recipe_Recipe.MatIndex
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID
WHERE     (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Recipe.MatIndex = ''R'')  AND (Recipe_Prop_Main.Release = @release)
open _cursor
FETCH NEXT FROM _cursor INTO  @material ,  @MatIndex  
--INSERT INTO @output (MateriaL,Matindex)  select @rECIPEnAME, @MatIndex 
WHILE @@FETCH_STATUS <> -1 
begin

if  @rECIPEnAME <> @material and @MatIndex=''R'' --and (@material <> @OldRecipe)
		begin
		
		INSERT INTO @output (MateriaL,Recip,info)  select @material , @rECIPEnAME, ''*'' --from 	[recursive](@material)
		INSERT INTO @output (MateriaL,Recip,info)  select MateriaL , Recip,info+''*'' from 	[Recipe_ListOf_Masters](@material, @release)

		break
		end
 if    @rECIPEnAME = @material and @MatIndex=''R'' --and (@material <> @OldRecipe)
		begin
		INSERT INTO @output (MateriaL,Recip,info )  select @material , @rECIPEnAME,''###''   --from 	[recursive](@material)
		break 
		end
if  @rECIPEnAME <> @material and @MatIndex=''R'' --and (@material= @OldRecipe)
		begin
		INSERT INTO @output (MateriaL,Recip, info)  select @material , @rECIPEnAME, ''*RCRS01'' --from 	[recursive](@material)
		break
		end

	
FETCH NEXT FROM _cursor INTO 	  @material ,  	  @MatIndex  
set @count =@count+1
end
close _cursor
deallocate _cursor
return
end
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_0.csv]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_0.csv](
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
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Import_to_Recipe_Recipe_Actual__7]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- USE PROCEDURE fnInsert_RECIPENEW1_1

CREATE PROCEDURE [dbo].[Import_to_Recipe_Recipe_Actual__7]
AS
delete from Recipe_Recipe
INSERT INTO Recipe_Recipe
                      (Recipe_ID, IngredName, PHR, Weight, SiloID, Phase, MatIndex, UpdatedOn, UpdatedBy)
SELECT     Recipe_Prop_Main.Recipe_ID, fnInsert_RECIPENEW1_1.IngredName, fnInsert_RECIPENEW1_1.PHR, fnInsert_RECIPENEW1_1.Weight, 
                      fnInsert_RECIPENEW1_1.SiloID, fnInsert_RECIPENEW1_1.Phase, fnInsert_RECIPENEW1_1.MatIndex, GETDATE() AS UpdatedOn, 
                      ''SB'' AS UpdatedBy
FROM         dbo.fnInsert_RECIPENEW1() AS fnInsert_RECIPENEW1_1 RIGHT OUTER JOIN
                      Recipe_Prop_Main ON fnInsert_RECIPENEW1_1.Code = Recipe_Prop_Main.Code AND fnInsert_RECIPENEW1_1.Release = Recipe_Prop_Main.Release
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_tbl_Adm_ComponentRelations]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_tbl_Adm_ComponentRelations](
	[name] [nchar](50) NULL,
	[ParentName] [nchar](50) NULL,
	[Childname] [nchar](50) NULL,
	[id] [bigint] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK__A_Tables] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_INTO_Ingred_Free_Info__14]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- =============================================
-- Procedure used fnGet_Ingred_Free_Info
-- =============================================
CREATE  PROCEDURE  [dbo].[Insert_INTO_Ingred_Free_Info__14]
AS
BEGIN
	SET NOCOUNT ON;


DELETE FROM Ingred_Free_Info
INSERT INTO Ingred_Free_Info
                      (IngredientCode_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
SELECT     Ingredient_Code.IngredientCode_ID, fnGet_Ingred_Free_Info_1.NoteName, fnGet_Ingred_Free_Info_1.NoteValue, GETDATE() AS Expr2, 
                      ''SB'' AS Expr1
FROM         dbo.fnGet_Ingred_Free_Info() AS fnGet_Ingred_Free_Info_1 INNER JOIN
                      Ingredient_Code ON fnGet_Ingred_Free_Info_1.Code = Ingredient_Code.Name
END
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[GLTABLE]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[GLTABLE](
	[GLTABLES_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[SCOPE] [nvarchar](15) NULL,
	[CODE] [nvarchar](10) NULL,
	[TABLEFIELD] [nvarchar](30) NULL,
 CONSTRAINT [PK_GLTABLE] PRIMARY KEY CLUSTERED 
(
	[GLTABLES_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[INSERT_INTO_Ingred_Comments_14]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE  PROCEDURE [dbo].[INSERT_INTO_Ingred_Comments_14] as

BEGIN
	-- IngredientCode table should be fileld already
	-- interfering with SELECT statements.
	-- SET NOCOUNT ON;
delete from Ingred_Comments

INSERT INTO Ingred_Comments
                      (IngredientCode_ID, Comments, UpdatedOn, UpdatedBy)
SELECT     Ingredient_Code.IngredientCode_ID, CPIngred_1.NOTE, GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy
FROM         CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
WHERE     (CPIngred_1.NOTE IS NOT NULL)
END
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Igredients_Warehouse_Select]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'






-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Igredients_Warehouse_Select]
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@IngredCode_ID varchar (50)

-- select * from [fn_Igredients_Warehouse_Select] (''30725'')

)
RETURNS TABLE 
AS
RETURN 
(
SELECT     Ingredient_Warehouse.SiloId, Ingredient_Warehouse.BalanceID, Ingredient_Warehouse.LotNO, Ingredient_Warehouse.BOxNo, 
                      Ingredient_Warehouse.Location, Ingredient_Warehouse.Storetime, Ingredient_Warehouse.SttempMin, Ingredient_Warehouse.StTempMax, 
                      Ingredient_Warehouse.StoreDate, Ingredient_Warehouse.STDATEEXT, Ingredient_Warehouse.ActStock, Ingredient_Warehouse.MinStock, 
                      Ingredient_Warehouse.LastUsed, Ingredient_Warehouse.UsageDate, Ingredient_Warehouse.StupDate, Ingredient_Warehouse.Form, 
                      Ingredient_Warehouse.Quantity, Ingredient_Warehouse.QuantUnit, Ingredient_Warehouse.[ORDER], Ingredient_Warehouse.OrderUnit, 
                      Ingredient_Warehouse.SWFINE, Ingredient_Warehouse.SWVeryFine, Ingredient_Warehouse.StopSignal, Ingredient_Warehouse.Tolerance, 
                      Ingredient_Warehouse.FreeInfo, Ingredient_Warehouse.UpdatedOn, Ingredient_Warehouse.UpdatedBy, Ingredient_Code.Name
FROM         Ingredient_Warehouse INNER JOIN
                      Ingredient_Code ON Ingredient_Warehouse.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @IngredCode_ID)
)
--
' 
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
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_to_REcipe_Prop_Free_info__4]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- =============================================
-- Procedure used fnGet_Recipe_Free_Info_1
-- =============================================
CREATE PROCEDURE  [dbo].[Insert_to_REcipe_Prop_Free_info__4]
AS
BEGIN
	SET NOCOUNT ON;




DELETE FROM Recipe_Prop_Free_Info
INSERT INTO Recipe_Prop_Free_Info
                      (NoteName, NoteValue, Recipe_ID)
SELECT     fnGet_Recipe_Free_Info_1.NoteName, fnGet_Recipe_Free_Info_1.NoteValue, Recipe_Prop_Main.Recipe_ID
FROM         dbo.fnGet_Recipe_Free_Info() AS fnGet_Recipe_Free_Info_1 INNER JOIN
                      Recipe_Prop_Main ON fnGet_Recipe_Free_Info_1.Code = Recipe_Prop_Main.Code AND 
                      fnGet_Recipe_Free_Info_1.Release = Recipe_Prop_Main.Release
END
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_to_Ingred_Free_Info__14]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- =============================================
-- Procedure used fnGet_Ingred_Free_Info
-- =============================================
create  PROCEDURE  [dbo].[Insert_to_Ingred_Free_Info__14]
AS
BEGIN
	SET NOCOUNT ON;


DELETE FROM Ingred_Free_Info
INSERT INTO Ingred_Free_Info
                      (IngredientCode_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
SELECT     Ingredient_Code.IngredientCode_ID, fnGet_Ingred_Free_Info_1.NoteName, fnGet_Ingred_Free_Info_1.NoteValue, GETDATE() AS Expr2, 
                      ''SB'' AS Expr1
FROM         dbo.fnGet_Ingred_Free_Info() AS fnGet_Ingred_Free_Info_1 INNER JOIN
                      Ingredient_Code ON fnGet_Ingred_Free_Info_1.Code = Ingredient_Code.Name
END
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'


--dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(''@recipe'', ''@release'')
-- THE INGREDIENT (phr''s) ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT weights
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredMaterials] (''00-8-N939'',''0'')
CREATE   FUNCTION [dbo].[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)) 

--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS] (''00-8-N939'',''0'')
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
INSERT INTO @output 
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM       Recipe_Tempory
WHERE     (material IS NOT NULL)

declare @CurrentItem_Weight decimal(8,2),
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 

SET @COUNT=0


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				if (@DENSITY = NULL or @DENSITY = 0) set  @DENSITY=1.1
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
-- calculate Ingredients Volume
WHILE @@FETCH_STATUS = 0 
	begin
		if @Item_Density =NULL or @Item_Density= 0 
				set @Item_Density=1
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE @OUTPUT 
		SET  weight_recalc = @CurrentItem_Weight, volume = @CurrentItem_Volume,  
		volume_Recalc = @CurrentItem_Volume, phr_Recalc = @Item_PHR WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Scenario 2
begin -- begin 1
		Declare 
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_Wight_Rubber float
		
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_Wight_Rubber
			set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR*0.01
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1
	-- Step 1.Finish. calculate total @Summ_Wight_Rubber

		-- here we recalculate weights
		DECLARe _crs_2 cursor FOR SELECT PHR, density,PERCRUBBER   FROM  @output 

		open _crs_2
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight=@Item_PHR*@Summ_Wight_Rubber/100				-- Step.2. Ingredient weight
				
				if @Item_Density =NULL or @Item_Density= 0 
				set @Item_Density=1

				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE @OUTPUT 
				SET  weight_recalc =@CurrentItem_Weight, volume_Recalc = @CurrentItem_Volume 
				--,  ID=@Count 
WHERE CURRENT OF _crs_2;	
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
			
			end
		close _crs_2
deallocate _crs_2
	


end -- begin 1, density_recalc

RETURN   
end 

--
--go 
--SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
--                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
--FROM         dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(''01-8-N753'', ''0'')

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into_Ingredient_Warehouse_17]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE procedure [dbo].[Insert_Into_Ingredient_Warehouse_17]
as
delete from Ingredient_Warehouse
INSERT INTO Ingredient_Warehouse
                      (IngredientCode_ID, LotNO, BOxNo, Location, Storetime, SttempMin, StTempMax, StoreDate, STDATEEXT, ActStock, MinStock, LastUsed, UsageDate, 
                      StupDate, Form, Quantity, QuantUnit, [ORDER], OrderUnit, SiloId, BalanceID, SWFINE, SWVeryFine, StopSignal, Tolerance, UpdatedOn)
/*
SELECT     Ingredient_Code.IngredientCode_ID, CPM.dbo.CPWare.LOTNO, CPM.dbo.CPWare.BOXNO, CPM.dbo.CPWare.LOCATION1, 
                      CPM.dbo.CPWare.STORETIME, CPM.dbo.CPWare.STTEMPMIN, CPM.dbo.CPWare.STTEMPMAX, CPM.dbo.CPWare.STOREDATE, 
                      CPM.dbo.CPWare.STDATEEXT, CPM.dbo.CPWare.ACTSTOCK, CPM.dbo.CPWare.MINSTOCK, CPM.dbo.CPWare.LASTUSED, 
                      CPM.dbo.CPWare.USAGEDATE, CPM.dbo.CPWare.STUPDATE, CPM.dbo.CPWare.FORM, CPM.dbo.CPWare.QUANTITY, CPM.dbo.CPWare.QUANTUNIT, 
                      CPM.dbo.CPWare.[ORDER], CPM.dbo.CPWare.ORDERUNIT, CPM.dbo.CPWare.SILOID, CPM.dbo.CPWare.BALANCEID, CPM.dbo.CPWare.SWFINE, 
                      CPM.dbo.CPWare.SWVERYFINE, CPM.dbo.CPWare.STOPSIGNAL, CPM.dbo.CPWare.TOLERANCE, CPM.dbo.CPWare.LASTUPDATE
FROM         CPM.dbo.CPWare INNER JOIN
                      Ingredient_Code ON CPM.dbo.CPWare.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
                      */
                      
SELECT     Ingredient_Code.IngredientCode_ID, CPWare_1.LOTNO, CPWare_1.BOXNO, CPWare_1.LOCATION1, CPWare_1.STORETIME, 
                      CPWare_1.STTEMPMIN, CPWare_1.STTEMPMAX, CPWare_1.STOREDATE, CPWare_1.STDATEEXT, CPWare_1.ACTSTOCK, 
                      CPWare_1.MINSTOCK, CPWare_1.LASTUSED, CPWare_1.USAGEDATE, CPWare_1.STUPDATE, CPWare_1.FORM, 
                      CPWare_1.QUANTITY, CPWare_1.QUANTUNIT, CPWare_1.[ORDER], CPWare_1.ORDERUNIT, CPWare_1.SILOID, 
                      CPWare_1.BALANCEID, CPWare_1.SWFINE, CPWare_1.SWVERYFINE, CPWare_1.STOPSIGNAL, CPWare_1.TOLERANCE, 
                      CPWare_1.LASTUPDATE
FROM         CPRECIPE...CPWare AS CPWare_1 INNER JOIN
                      Ingredient_Code ON  CPWare_1.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into_Ingredient_phys_Properties_13]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE proc [dbo].[Insert_Into_Ingredient_phys_Properties_13]
as 
/*
-- Original CODE
delete from Ingredient_phys_Properties
INSERT INTO Ingredient_phys_Properties
                      (percRubber, PercRubtOl, PercActMat, ViscTemp, ViscML, ViscMLTOl, density, DensityTOl, UpdatedOn, UpdatedBy, IngredientCode_ID)
SELECT     CPM.dbo.CPIngred.PERCRUBBER AS Expr1, CPM.dbo.CPIngred.PERCRUBTOL AS Expr2, CPM.dbo.CPIngred.PERCACTMAT AS Expr3, 
                      CPM.dbo.CPIngred.VISCTEMP AS Expr4, CPM.dbo.CPIngred.VISCML AS Expr5, CPM.dbo.CPIngred.VISCMLTOL AS Expr6, 
                      CPM.dbo.CPIngred.DENSITY AS Expr7, CPM.dbo.CPIngred.DENSITYTOL AS Expr8, CPM.dbo.CPIngred.LASTUPDATE, 
                      CPM.dbo.CPIngred.UPDATEDBY AS Expr9, Ingredient_Code.IngredientCode_ID AS Expr10
FROM         CPM.dbo.CPIngred INNER JOIN

                      Ingredient_Code ON CPM.dbo.CPIngred.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
                      */
delete from Ingredient_phys_Properties
INSERT INTO Ingredient_phys_Properties
                      (percRubber, PercRubtOl, PercActMat, ViscTemp, ViscML, ViscMLTOl, density, DensityTOl, UpdatedOn, UpdatedBy, IngredientCode_ID)
SELECT     CPIngred_1.PERCRUBBER AS Expr1, CPIngred_1.PERCRUBTOL AS Expr2, CPIngred_1.PERCACTMAT AS Expr3, CPIngred_1.VISCTEMP AS Expr4, 
                      CPIngred_1.VISCML AS Expr5, CPIngred_1.VISCMLTOL AS Expr6, CPIngred_1.DENSITY AS Expr7, CPIngred_1.DENSITYTOL AS Expr8, CPIngred_1.LASTUPDATE, 
                      CPIngred_1.UPDATEDBY AS Expr9, Ingredient_Code.IngredientCode_ID AS Expr10
FROM         CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_to_REcipe_Prop_Free_Text__5]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE  [dbo].[Insert_to_REcipe_Prop_Free_Text__5]
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;
DELETE FROM [NCPD].[dbo].[Recipe_Prop_Free_Text]
INSERT INTO Recipe_Prop_Free_Text
                      (NoteName, NoteValue, Recipe_ID, UpdatedOn, UpdatedBy)
SELECT     fnGet_Recipe_Free_Text_1.NoteName, fnGet_Recipe_Free_Text_1.NoteValue, Recipe_Prop_Main.Recipe_ID, Recipe_Prop_Main.UpdatedOn, 
                      Recipe_Prop_Main.UpdatedBy
FROM         Recipe_Prop_Main INNER JOIN
                      dbo.fnGet_Recipe_Free_Text() AS fnGet_Recipe_Free_Text_1 ON Recipe_Prop_Main.Code = fnGet_Recipe_Free_Text_1.Code AND 
                      Recipe_Prop_Main.Release = fnGet_Recipe_Free_Text_1.Release
END
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CheckSequencesWitoutRecipes]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[CheckSequencesWitoutRecipes]
as
SELECT     dbo.Recipe_Sequence_Main.Recipe_Sequence_Main_ID, dbo.Recipe_Sequence_Main.Code, dbo.Recipe_Sequence_Main.Release, 
                      dbo.Recipe_Sequence_Main.Mixer_Code, dbo.Recipe_Sequence_Main.Info, dbo.Recipe_Sequence_Main.Status, 
                      dbo.Recipe_Sequence_Main.UpdatedOn, dbo.Recipe_Sequence_Main.UpdatedBy
FROM         dbo.Recipe_Prop_Main RIGHT OUTER JOIN
                      dbo.Recipe_Sequence_Main ON dbo.Recipe_Prop_Main.Code = dbo.Recipe_Sequence_Main.Code AND 
                      dbo.Recipe_Prop_Main.Release = dbo.Recipe_Sequence_Main.Release AND 
                      dbo.Recipe_Prop_Main.Mixer_Code = dbo.Recipe_Sequence_Main.Mixer_Code
WHERE     (NOT (dbo.Recipe_Sequence_Main.Code IN
                          (SELECT     Code
                            FROM          dbo.Recipe_Prop_Main AS Recipe_Prop_Main_1)))
--go
--exec CheckSequencesWitoutRecipes
--go
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Igredients_Display_Warehouse]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'








-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Igredients_Display_Warehouse]
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@IngredCode varchar (50)

--go
-- select * from [fn_ITF_Igredients_Display_Warehouse] (''17460'')
)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     Ingredient_Warehouse.Location, Ingredient_Warehouse.SiloId, Ingredient_Warehouse.BalanceID, Ingredient_Warehouse.FreeInfo, 
                      Ingredient_Warehouse.Ingredient_Warehouse_ID, Ingredient_Warehouse.IngredientCode_ID
FROM         Ingredient_Code INNER JOIN
                      Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @IngredCode)
)

--go
-- select * from [fn_ITF_Igredients_Display_Warehouse] (''17460'')
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Delete_Duplicates_Recipe_Sequence_Main]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'create procedure [dbo].[Delete_Duplicates_Recipe_Sequence_Main]
as
DELETE FROM Recipe_Sequence_Main
WHERE     (Recipe_Sequence_Main_ID NOT IN
                          (SELECT     MIN(Recipe_Sequence_Main_ID) AS Expr1
                            FROM          Recipe_Sequence_Main AS Recipe_Sequence_Main_1
                            GROUP BY Code, Release))
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_A_Tables]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_A_Tables](
	[name] [sysname] NOT NULL,
	[modify_date] [datetime] NOT NULL,
	[type] [char](2) NOT NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Procedure]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Procedure](
	[ID] [bigint] NOT NULL,
	[TestCode] [varchar](50) NULL,
	[Group] [varchar](50) NULL,
	[Describtion] [varchar](50) NULL,
	[Norm] [varchar](50) NULL,
	[Note] [varchar](50) NULL,
	[Import] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Procedure] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_TEXT]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE   FUNCTION [dbo].[fn_ITF_Recipes_TEXT]
(	
	-- Add the parameters for the function here
@code NVARCHAR(50),			--''00-0-0012''
@Release NVARCHAR(1),			--''W''
@Mixer_Code NVARCHAR(50)	--''AB''
-- All parameters Obligatory
-- select * from [fn_ITF_Recipes_TEXT] (''00-0-0012'',''0'',''AB'')ORDER BY code 

)
RETURNS TABLE 
AS
RETURN 
(
SELECT     Recipe_Prop_Main.Code AS Recipe_Version, Recipe_Prop_Main.Release AS Recipe_Addditional, Recipe_Prop_Main.Mixer_Code, 
                      Recipe_Prop_Free_Text.NoteName, Recipe_Prop_Free_Text.NoteValue, Recipe_Prop_Free_Text.UpdatedOn, Recipe_Prop_Free_Text.UpdatedBy, 
                      Recipe_Prop_Free_Text.Recipe_Prop_Free_Text_ID
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Prop_Free_Text ON Recipe_Prop_Main.Recipe_ID = Recipe_Prop_Free_Text.Recipe_ID
WHERE     (Recipe_Prop_Main.Mixer_Code = @Mixer_Code) AND (Recipe_Prop_Main.Release = @Release) AND (Recipe_Prop_Main.Code = @Code)
)
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_A_procedures]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_A_procedures](
	[name] [sysname] NOT NULL,
	[modify_date] [datetime] NOT NULL,
	[type] [char](2) NOT NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_A_Functions]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_A_Functions](
	[name] [sysname] NOT NULL,
	[modify_date] [datetime] NOT NULL,
	[type] [char](2) NOT NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Order]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Order](
	[ID] [bigint] NOT NULL,
	[Order] [varchar](50) NULL,
	[Info] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Order] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_GivenWeights]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

CREATE   Proc [dbo].[Recipe_Tempory_Recalc_GivenWeights] 
( 
@recipe NVARCHAR(50),
@release NVARCHAR(50)=''0''

) 
as
 
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]
begin try
INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM         dbo.Recipe_Full_RZPT_Recalc_PHR_GivenWeights(@recipe, @release) AS Recipe_Full_RZPT_Recalc_PHR_GivenWeights_1
end try

begin catch
begin
 SELECT ERROR_MESSAGE() AS ErrorMessage;
return -1
end
end catch


begin try
begin
delete from Recipe_Tempory

INSERT INTO Recipe_Tempory
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
FROM         #WWW

return  0
end
end try

begin catch

select ''error 2''
return -2
end catch

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_Recalc_PHR_GivenWeights]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'








-- THE INGREDIENT WEIGHTS ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT PHRs
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_Recalc_PHR_GivenWeights] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)) 
--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 
--
--select  * from [Recipe_Full_RZPT_Recalc_PHR_GivenWeights](''00-0-0006'',''0'')

RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] int NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
	declare @material varchar(50)
	DECLARE @matIndex VARCHAR(1)
	declare @weight float 
	SET @COUNT=1

-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
INSERT INTO @output 
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          Recipe_Tempory
	WHERE     (material IS NOT NULL)





	declare @CurrentItem_Weight float,
			@CurrentItem_Volume float,
			@Item_Density float, 
			@Item_PHR float,
			@Item_percRubber float 

	SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
					open _crs
					FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
					close _CRS
					DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor INTO @material,@matIndex,@weight
end 
close _cursor
DEALLOCATE _cursor

-- 
-- volume calculation ??? this for show old values before recalculation!
DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume, phr = @Item_PHR WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3




  -- Here we recalculate the ingredients, see instructions for Scenario 2
begin -- begin 1
		Declare 
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_Wight_Rubber float
		
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
			-- Step 1.Start. calculate total @Summ_Wight_Rubber
				set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR/100
				FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
	-- Step 1.Finish. calculate total @Summ_Wight_Rubber

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @Item_PHR= @CurrentItem_Weight/@Summ_Wight_Rubber*100				-- Step.2. Ingredient phr
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE @OUTPUT 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume , PHR =@Item_PHR
				--, ID=@Count 
			WHERE CURRENT OF _crs_1;	
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			
			end
		close _crs_1
deallocate _crs_1
	
-- sumarry we do not recalc
end -- begin 1, density_recalc

RETURN   
end 













' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TRADENAME_MAIN]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[TRADENAME_MAIN](
	[Tradename_Main_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Cas_Number] [varchar](50) NULL,
	[TradeName] [varchar](50) NULL,
	[MSDS] [nvarchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_TRADENAME_MAIN] PRIMARY KEY CLUSTERED 
(
	[Tradename_Main_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_1_O_W_User_Check]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'







CREATE  Procedure [dbo].[Recipe_Full_RZPT_1_O_W_User_Check] 
	( -- PROCEDURE TO CALCULATE SUMMARIES from the DBtable with the name ''USER'' see ''Create_Recipe_Tempory_USER''!
	--declare @RS as int
	--exec @RS = [dbo].[Recipe_Full_RZPT_1_O_W_User] ''AA'',''72'',''SB''
	--select @RS as "Error"
  @MixerCode NVARCHAR(50) -- for definition of loading factor 
, @LoadFactor float -- not used for this procedure , can be dummy!
, @user   NVARCHAR(50) -- already created file with basic recipe
) 

as 


/* CREATE TEMPORY TABLE*/
IF OBJECT_ID(N''tempdb..#output'', N''U'') IS NOT NULL 
DROP TABLE #output;
Create   TABLE  #output( -- structure of SUmmary table
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
Recipe_Recipe_ID bigint,
RecipeID bigint
)
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(@user) AND type in (N''U''))
return 0 -- error code for: base table does not exists

 

begin try 
	exec (''INSERT INTO #output
	select * from ''+@user+'' where material is not NULL'')
end try
begin catch
	select ERROR_MESSAGE() as ''error ''
	return -15
end catch

BEGIN
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
/*
will check amount of recipes
*/
DECLARE	@return_value int
		begin try 
				DECLARE _cursor CURSOR FOR SELECT     COUNT(*) AS Expr1
				FROM     #output 
				open _cursor
				FETCH NEXT FROM _cursor INTO  @return_value 
				close _cursor
				deallocate _cursor
				if  @return_value = 0 -- no records in basic tables nothing to calculate 
			 	return 0 
					
		end try
		begin catch
				return -12
		end catch


-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated TOTAL values will be put into [PHR_recalc] and [weight_Recalc] output 1 raw table.


Declare	-- for ingredients
@density float ,@Percentage float 
declare @REcipeId bigint
declare  @rECIPEnAME NVARCHAR(50), @Release NVARCHAR(50)
/* CALCULATING TOTALS START */
DECLARe _crs_1 cursor FOR SELECT 
density, percRubber  FROM  #output 
declare @Flag int
set @Flag =0

		open _crs_1
		FETCH NEXT from _crs_1 into  @density  ,@Percentage  
		WHILE @@FETCH_STATUS =0 
			begin
			if @density  is NULL  OR @Percentage is null
				set @Flag =1
			FETCH NEXT from _crs_1 into  @density  ,@Percentage  
			end
		close _crs_1
		
DEALLOCATE _crs_1
/* CALCULATING TOTALS FINISH */

/* CREATE OUTPUT USER1 TABLE */

begin try 
if @Flag=1 
begin
set @user=@user+''1''
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(@user) AND type in (N''U''))
exec (''DROP TABLE '' + @user)

exec (
''CREATE TABLE ''+ @user+''(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) 
''
)

declare @SQLString nvarchar(1000)

set @SQLString =		
 ''Insert into ''+ @user +'' (id,
descr)
values(99, ''''''+''Error:Missing Density Or Rubber content '' + '''''')''
--select @SQLString as ''@SQLString''

exec (@SQLString)

end
end try
begin catch
select ''-16'' as ''Error''
select ERROR_MESSAGE() as ''error''

	return -16 -- error undefined
end catch	


IF OBJECT_ID(N''tempdb..#output'', N''U'') IS NOT NULL 
DROP TABLE #output;
RETURN  0 

end










' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_FillFactor]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE   Proc [dbo].[Recipe_Tempory_Recalc_FillFactor]
( 
@recipe VARCHAR(50),
@release VARCHAR(50)=''0'',
@NewFillFactor VARCHAR(50)=''0'',
@MixerId VARCHAR(50)=''AB''
) 
as
if @NewFillFactor =''0''
begin
	return -1 -- select the New Total Weight Parameter 
end
else
	begin try

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewFillFactor_2(@recipe, @release,@NewFillFactor,@MixerId) 

	delete from Recipe_Tempory

	INSERT INTO Recipe_Tempory
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW

	return 0 
	end try
	begin catch
	return -2 -- error undefined
end catch





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Profile]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Profile](
	[ID] [bigint] NOT NULL,
	[Profile] [varchar](50) NULL,
	[Customer] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Profile] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[VENDOR]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[VENDOR](
	[VENDOR_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[VendorNo] [varchar](50) NULL,
	[VendorName] [varchar](50) NULL,
	[Adress] [varchar](50) NULL,
	[ZipCode] [varchar](50) NULL,
	[City] [varchar](50) NULL,
	[Country] [varchar](50) NULL,
	[Phone] [varchar](50) NULL,
	[Fax] [varchar](50) NULL,
	[Email] [varchar](50) NULL,
	[Website] [varchar](50) NULL,
	[FreeInfo] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_VENDOR] PRIMARY KEY CLUSTERED 
(
	[VENDOR_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Sequence_Init_SelectSet]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'













-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Sequence_Init_SelectSet]
/*
selects the set of recipes according to choosen code and release.
If code is NULL and release is NULL all possible sets are selected
IF code is ''XX-X-XXXX''chosen and Release is NULL the set with for code ''XX-X-XXXX'' with all possible release selected
IF code is NULLchosen and Release is ''X'' the set with for all possible codes  which have relese ''X'' selected
this function used for user interface list boxes  engine: 
for Code list box : fn_ITF_Sequence_Init_SelectSet(''NULL'',RElease_Listbox_Value)
for Release list box : fn_ITF_Sequence_Init_SelectSet(Code_Listbox_Value,''NULL'')

*/
(	
	-- Add the parameters for the function here
@Code varchar (50),
@Release varchar (50)

-- select * from [fn_ITF_Recipes_Init] (NULL,''0'',Null,Null,Null,Null,Null,Null)ORDER BY code

)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     TOP (100) PERCENT Recipe_Sequence_Main.Code, Recipe_Sequence_Main.Release, CAST(Recipe_Sequence_Steps.Step_NB AS Integer) AS Step_NB,
                       Recipe_Sequence_Commands.Command_Name, Recipe_Sequence_Steps.Command_Param
FROM         Recipe_Sequence_Main INNER JOIN
                      Recipe_Sequence_Steps ON Recipe_Sequence_Main.Recipe_Sequence_Main_ID = Recipe_Sequence_Steps.Recipe_Sequence_Main_ID INNER JOIN
                      Recipe_Sequence_Commands ON 
                      Recipe_Sequence_Steps.Recipe_Sequence_Commands = Recipe_Sequence_Commands.Recipe_Sequence_Commands
WHERE     ((@Code IS NULL) OR (@Release IS NULL)) AND
                      ((@Code = Recipe_Sequence_Main.Code) OR (@Release = Recipe_Sequence_Main.Release))
ORDER BY Recipe_Sequence_Main.Code, Recipe_Sequence_Main.Release
)
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AA]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AA](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_newTotalWeight]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



--go 
--select  * from [Recipe_Full_RZPT_NewWeight_2](''01-8-N944'',''0'',''300'')
--go
--DECLARE	@return_value int
--
--EXEC	@return_value = [dbo].[Recipe_Tempory_Recalc_newTotalWeight]''01-8-N944'',''0'',''300''
--select * from Recipe_Full_RZPT_1_O (''01-8-N944'',''0'')
--SELECT	''Return Value'' = @return_value
--go 
--select * from Recipe_Tempory

CREATE   Proc [dbo].[Recipe_Tempory_Recalc_newTotalWeight]
( 
@recipe VARCHAR(50),
@release VARCHAR(50)=''0'',
@NewTotalWeight VARCHAR(50)=''0''

) 
as
if @NewTotalWeight=''0''
begin
	return -1 -- select the New Total Weight Parameter 
end
else
	--begin try
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]



begin try 
INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewWeight_2(@recipe, @release,@NewTotalWeight) AS Recipe_Full_RZPT_NewWeight_2_1
--select * from #WWW
	end try
begin catch
	return -2 -- error undefined
end catch

delete from Recipe_Tempory


begin try 
INSERT INTO Recipe_Tempory
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW

	end try
begin catch
	return -3 -- error undefined
end catch
return 0 
--go
--select * from Recipe_Tempory
--go
--exec [dbo].[Recipe_Tempory_Recalc_newTotalWeight]  ''00-8-N939'',''0'',''250''
--go
--select * from Recipe_Tempory
--go
--select * from dbo.Recipe_Full_RZPT_1_O (''00-8-N939'',''0'')
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Sequence_Steps__11]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE procedure  [dbo].[Insert_Sequence_Steps__11]
as
delete from Recipe_Sequence_Steps
INSERT INTO Recipe_Sequence_Steps
                      (Recipe_Sequence_Commands, Command_Param, Step_NB, Recipe_Sequence_Main_ID, UpdatedOn, UpdatedBy)
SELECT     Recipe_Sequence_Commands.Recipe_Sequence_Commands, ssss.param, ssss.step, Recipe_Sequence_Main.Recipe_Sequence_Main_ID, GETDATE() AS Expr2, 
                      ''SB'' AS Expr1
FROM         ssss INNER JOIN
                      Recipe_Sequence_Commands ON ssss.command = Recipe_Sequence_Commands.Command_Name INNER JOIN
                      Recipe_Sequence_Main ON ssss.code = Recipe_Sequence_Main.Code AND ssss.mixerid = Recipe_Sequence_Main.Mixer_Code AND 
                      ssss.release = Recipe_Sequence_Main.Release
ORDER BY Recipe_Sequence_Main.Recipe_Sequence_Main_ID, ssss.step
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_deleteSingle]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

/*
this is  for useer interface table sequence 
deleting single row in sequence table

*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_deleteSingle]
( 
@Recipe_Sequence_Steps_ID bigint
)


as


DELETE TOP (100) PERCENT
FROM         Recipe_Sequence_Steps
WHERE     (Recipe_Sequence_Steps_ID = @Recipe_Sequence_Steps_ID)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe](
	[ID] [bigint] NOT NULL,
	[Quality] [varchar](50) NULL,
	[RecDescribtion] [varchar](50) NULL,
	[RecNotes] [varchar](50) NULL,
	[Customer] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Recipe] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_INSERT_INTO]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE   Proc [dbo].[Recipe_Tempory_INSERT_INTO] 
( 
@Code NVARCHAR(50) , 
@release NVARCHAR(50),
@ingredID NVARCHAR(50)
) 
as
	DECLARE @CNT NVARCHAR(50)
	DECLARE _cursor CURSOR FOR select   COUNT(*) AS CNT FROM    Recipe_Tempory 
	open _cursor
	FETCH NEXT FROM _cursor INTO  @CNT 
	
close _cursor
deallocate _cursor


DECLARE @recipeID bigint
	DECLARE _cursor1 CURSOR FOR 
		SELECT     RecipeID
		FROM         Recipe_Tempory
		GROUP BY RecipeID
		HAVING      (RecipeID IS NOT NULL)
	open _cursor1
	FETCH NEXT FROM _cursor1 INTO  @recipeID 
--select @recipeID as ''@recipeID''
close _cursor1
deallocate _cursor1


--declare @density nvarchar(50)
--set @density =(SELECT     density FROM  dbo.fn_Recipe_Ingredient_Insert(@ingredID) )
--select @density as ''''@density''''
--
--set @density= ''''1.5''''
--select  cast (@density as float )  as ''''@density''''
--
--if cast (@density  as float ) = 0  print ''''lala''''

INSERT INTO Recipe_Tempory
                      (Id, RecipeName, Release, material, SiloId, percRubber, density, BalanceID, PriceKG, PriceData, Descr, GRP, RecipeID, MatIndex, Phase, ContainerNB,
                       PHR, weight)
SELECT     @CNT + 1 AS Id, @code AS RecipeName, @release AS release, @ingredID AS material, SiloId, percRubber, density, BalanceID, PriceKG, PriceData, 
                      Descr, GRP, @RecipeId AS RecipeID, ''I'' AS MatIndex, ''1'' AS Phase, ''A'' AS ContainerNB, 1 AS PHR, 1 AS weight
FROM         dbo.fn_Recipe_Ingredient_Insert(@ingredID)


return 0

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AA1]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AA1](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_Edit]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


/*
this is  for useer interface table sequence 
*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_Edit]
( 
@Recipe_Sequence_Steps_ID bigint,
@Command_Name varchar(50),
@Step_NB varchar(50),
@Command_Param varchar(50)
)


as
declare @Recipe_Sequence_Commands bigint

DECLARE _cursor CURSOR FOR 
SELECT     Recipe_Sequence_Commands
FROM         Recipe_Sequence_Commands
WHERE     (Command_Name = @Command_Name)
OPEN _cursor
FETCH NEXT FROM _cursor 
INTO @Recipe_Sequence_Commands

CLOSE _cursor
DEALLOCATE _cursor


UPDATE    TOP (100) PERCENT Recipe_Sequence_Steps
SET              Step_NB = @Step_NB, Command_Param = @Command_Param, Recipe_Sequence_Commands = @Recipe_Sequence_Commands
WHERE     (Recipe_Sequence_Steps_ID = @Recipe_Sequence_Steps_ID)
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_newTotalVolume]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'







CREATE   Proc [dbo].[Recipe_Tempory_Recalc_newTotalVolume]
( 
@recipe VARCHAR(50),
@release VARCHAR(50)=''0'',
@NewTotalVolume VARCHAR(50)=''0''

) 
as
if @NewTotalVolume=''0''
begin
	return -1 -- select the New Total Weight Parameter 
end
else
	begin try

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewVolume_2(@recipe, @release,@NewTotalVolume) 

	delete from Recipe_Tempory

	INSERT INTO Recipe_Tempory
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW

	return 0 
	end try
	begin catch
	return -2 -- error undefined
end catch



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

/*
this is  for useer interface table sequence 
*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_insert]
( 
@Recipe_Sequence_Main_ID bigint,
@Command_Name varchar(50),
@Step_NB varchar(50),
@Command_Param varchar(50)
)

as
declare @Recipe_Sequence_Commands bigint

DECLARE _cursor CURSOR FOR 
SELECT     Recipe_Sequence_Commands
FROM         Recipe_Sequence_Commands
WHERE     (Command_Name = @Command_Name)
OPEN _cursor
FETCH NEXT FROM _cursor 
INTO @Recipe_Sequence_Commands
CLOSE _cursor
DEALLOCATE _cursor

INSERT      TOP (100) PERCENT
INTO            Recipe_Sequence_Steps(Step_NB, Command_Param, Recipe_Sequence_Commands, Recipe_Sequence_Main_ID)
VALUES     (@Step_NB,@Command_Param,@Recipe_Sequence_Commands,@Recipe_Sequence_Main_ID)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_deleteAll]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


/*
this is  for useer interface table sequence 
deleting single row in sequence table

*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_deleteAll]
( 
@Recipe_Sequence_Main_ID bigint
)
as
DELETE TOP (100) PERCENT
FROM         Recipe_Sequence_Steps
WHERE     (Recipe_Sequence_Main_ID = @Recipe_Sequence_Main_ID)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[NA]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[NA](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_Edit_Main]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


/*
this is  for useer interface table sequence 
*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_Edit_Main]
( 
@Recipe_Sequence_Main_ID bigint,
@Mixer_Code varchar(50),
@Info varchar(50),
@UpdatedOn datetime,
@UpdatedBy varchar(50)

)


as



UPDATE    TOP (100) PERCENT Recipe_Sequence_Main
SET              Mixer_Code = @Mixer_Code, Info = @Info, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy
WHERE     (Recipe_Sequence_Main_ID = @Recipe_Sequence_Main_ID)





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SB_C]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[SB_C](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AB_C]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AB_C](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_Insert_Main]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



/*
this is  for useer interface table sequence 
*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_Insert_Main]
/*
to use only if no sequence_main record for the current recipe+release
*/
( 
@Code varchar(50),
@Release varchar(50),
@Mixer_Code varchar(50)
)
as
declare @flag int
 
declare @Recipe_Sequence_Commands bigint

DECLARE _cursor CURSOR FOR 
SELECT     1 AS flag
FROM         Recipe_Sequence_Main
WHERE     (Code = @Code) AND (Release = @Release)AND (Mixer_Code = @Mixer_Code)

OPEN _cursor
FETCH next  FROM _cursor 
INTO @flag
CLOSE _cursor
DEALLOCATE _cursor


IF @@FETCH_STATUS = -1 or  @@FETCH_STATUS = -2
BEGIN
INSERT      TOP (100) PERCENT
INTO            Recipe_Sequence_Main(Code, Release, Mixer_Code)
VALUES     (@Code,@Release,@Mixer_Code)
end


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnGet_Recipe_Free_Info]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'

CREATE  FUNCTION[dbo].[fnGet_Recipe_Free_Info] () 
RETURNS @output TABLE([Code] [varchar](50) NULL,
[Release] [varchar](50) NULL,
	[NoteName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[NoteValue] [text] COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) 
BEGIN 

--Declare 
DECLARE @code nvarchar(10),@release nvarchar(2), @mixID nvarchar(3);
DECLARE 
@FreeInfo1  nvarchar(Max) ,
@FreeInfo2  nvarchar(Max) ,
@FreeInfo3  nvarchar(Max) ,
@FreeInfo4  nvarchar(Max) ,
@FreeInfo5  nvarchar(Max) ,
@FreeInfo6  nvarchar(Max) ,
@FreeInfo7  nvarchar(Max) ,
@FreeInfo8  nvarchar(Max) ,
@FreeInfo9  nvarchar(Max) ,
@FreeInfo10  nvarchar(Max) ,
@FreeInfo11  nvarchar(Max) ,
@FreeInfo12  nvarchar(Max) 

DECLARE @count int 

DECLARE _cursor CURSOR FOR 
SELECT     CODE, releaSE, 
FreeInfo1  ,
FreeInfo2   ,
FreeInfo3   ,
FreeInfo4  ,
FreeInfo5   ,
FreeInfo6   ,
FreeInfo7   ,
FreeInfo8   ,
FreeInfo9   ,
FreeInfo10  ,
FreeInfo11   ,
FreeInfo12  
FROM         CPRECIPE...CPRECIPE

OPEN _cursor
set @count=1


FETCH NEXT FROM _cursor 
INTO  @code, @release,
@FreeInfo1  ,
@FreeInfo2   ,
@FreeInfo3   ,
@FreeInfo4  ,
@FreeInfo5   ,
@FreeInfo6   ,
@FreeInfo7   ,
@FreeInfo8   ,
@FreeInfo9   ,
@FreeInfo10  ,
@FreeInfo11   ,
@FreeInfo12  

set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END

WHILE @@FETCH_STATUS <> -1 --AND @count<=2000
BEGIN
set @count=@count+1



if @FreeInfo1 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''Color:'', @FreeInfo1)


if @FreeInfo2 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''recept type:'', @FreeInfo2)


if @FreeInfo3 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''industry:'', @FreeInfo3)

if @FreeInfo4 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''curing process:'', @FreeInfo4)

if @FreeInfo5 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''curing system:'', @FreeInfo5)

if @FreeInfo6 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''Hardnes Sha:'', @FreeInfo6)

if @FreeInfo7 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''filler:'', @FreeInfo7)

if @FreeInfo8 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''schelflife(weeks):'', @FreeInfo8)

if @FreeInfo9 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''certificate:'', @FreeInfo9)

if @FreeInfo10 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''packed'', @FreeInfo10)

if @FreeInfo11 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''pallete:'', @FreeInfo11)

if @FreeInfo12 is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''info:'', @FreeInfo12)


FETCH NEXT FROM _cursor 
INTO  @code, @release,
@FreeInfo1  ,
@FreeInfo2   ,
@FreeInfo3   ,
@FreeInfo4  ,
@FreeInfo5   ,
@FreeInfo6   ,
@FreeInfo7   ,
@FreeInfo8   ,
@FreeInfo9   ,
@FreeInfo10  ,
@FreeInfo11   ,
@FreeInfo12  

set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END
end
--select * from @output
CLOSE _cursor;
DEALLOCATE _cursor;
    RETURN 
END


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_deleteMain]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



/*
this is  for useer interface table sequence 
deleting single row in sequence table

*/


create   procedure  [dbo].[prc_ITF_Sequence_deleteMain]
( -- delete all step records for the REcipe , main record remains
@Code varchar(50),
@Release varchar(50)

)
as
DELETE TOP (100) PERCENT
FROM         Recipe_Sequence_Main
WHERE     (Code = @Code) AND (Release = @Release)





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[NA1]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[NA1](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
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
	[UpdatedBy] [nvarchar](50) NULL,
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
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Tag]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Tag](
	[ID] [bigint] NOT NULL,
	[Name] [varchar](50) NULL,
	[Tag] [varchar](50) NULL,
	[Device] [varchar](50) NULL,
	[Min] [varchar](50) NULL,
	[Max] [varchar](50) NULL,
	[Unit] [varchar](50) NULL,
	[Note] [varchar](50) NULL,
	[Condition] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Tag] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnBasic_RZPT_REcordsInTemp]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE  FUNCTION [dbo].[fnBasic_RZPT_REcordsInTemp]()

/*
to use 
declare @Amount1 int
set @Amount1=  dbo.[fnBasic_RZPT_AmountOfIngredients](''08-0-1174'',''0'')
select @Amount1
go
*/
RETURNS  int 
AS
begin
	declare @Amount int
		DECLARE _cursor CURSOR FOR SELECT     COUNT(*) AS Expr1
FROM         dbo.Recipe_Tempory
open _cursor

	FETCH NEXT FROM _cursor INTO  @Amount 
	return @Amount
end
--go 
--declare @Amount1 int
--set @Amount1 = [dbo].[fnBasic_RZPT_REcordsInTemp]()
--select @Amount1

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Test_Procedures]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Test_Procedures](
	[Ingred_test_Procedures_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Test_Code] [varchar](50) NULL,
	[Descript] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[GRUPPE] [varchar](50) NULL,
	[Report] [varchar](50) NULL,
	[NOTE] [varchar](max) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[NORM] [varchar](50) NULL,
	[Class] [varchar](50) NULL,
 CONSTRAINT [PK_Ingredient_Test_Procedures] PRIMARY KEY CLUSTERED 
(
	[Ingred_test_Procedures_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Sequence_Steps]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Sequence_Steps](
	[Recipe_Sequence_Steps_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Recipe_Sequence_Main_ID] [bigint] NULL,
	[Recipe_Sequence_Commands] [bigint] NOT NULL,
	[Step_NB] [varchar](50) NULL,
	[Command_Param] [varchar](50) NULL,
	[Command_Status] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Recipe_Sequence_Steps] PRIMARY KEY CLUSTERED 
(
	[Recipe_Sequence_Steps_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Sequence_Commands]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Sequence_Commands](
	[Recipe_Sequence_Commands] [bigint] IDENTITY(1,1) NOT NULL,
	[Command_Name] [varchar](50) NULL,
	[Command_Info] [varchar](50) NULL,
	[Command_Parameter] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Recipe_Sequence_Commands] PRIMARY KEY CLUSTERED 
(
	[Recipe_Sequence_Commands] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ZZZ2]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ZZZ2](
	[Recipe_ID] [bigint] NOT NULL,
	[Recipe_Origin] [varchar](4) NULL,
	[Recipe_Additional] [varchar](50) NULL,
	[Recipe Version] [varchar](50) NULL,
	[Recipe Stage] [varchar](6) NULL,
	[Detailed_Group] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[Class] [varchar](50) NULL,
	[Mixer_Code] [varchar](50) NULL,
	[Descr] [varchar](max) NULL,
	[Loadfactor] [varchar](50) NULL,
	[MixTime] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[NoteName] [varchar](50) NULL,
	[NoteValue] [varchar](50) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[output2]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[output2](
	[FF] [varchar](50) NULL,
	[MV] [varchar](50) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Mashine]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Mashine](
	[ID] [bigint] NOT NULL,
	[Name] [varchar](50) NULL,
	[Specs] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Mashine] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_GivenWeights_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



-- THE INGREDIENT WEIGHTS ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT PHRs
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
CREATE   proc [dbo].[Recipe_Tempory_Recalc_GivenWeights_USER] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50) , @user   NVARCHAR(50))
--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 
--
--exec [Recipe_Tempory_Recalc_newTotalWeight_USER]''00-0-0006'',''0'', ''SB''

as
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]

BEGIN
  DECLARE @COUNT INT
	declare @material varchar(50)
	DECLARE @matIndex VARCHAR(1)
	declare @weight float 
	SET @COUNT=1

-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
exec (''
INSERT INTO #WWW
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          ''+@user+'' 
	WHERE     (material IS NOT NULL)
'')




	declare @CurrentItem_Weight float,
			@CurrentItem_Volume float,
			@Item_Density float, 
			@Item_PHR float,
			@Item_percRubber float 

	SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from #WWW 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,''0'') 
					open _crs
					FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
					close _CRS
					DEALLOCATE _CRS
				UPDATE #WWW 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END

	FETCH NEXT FROM _cursor INTO @material,@matIndex,@weight
end 
close _cursor
DEALLOCATE _cursor

-- 
-- volume calculation ??? this for show old values before recalculation!
DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from #WWW 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		if @Item_Density =NULL or @Item_Density= 0  set @Item_Density=1
		
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE #WWW 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume, phr = @Item_PHR 
		,weight_recalc =@CurrentItem_Weight, volume_Recalc = @CurrentItem_Volume , phr_Recalc  = @Item_PHR 
		WHERE CURRENT OF _crs_3;	
		fETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3




  -- Here we recalculate the ingredients, see instructions for Scenario 2
begin -- begin 1
		Declare 
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_Wight_Rubber float
		
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  #WWW 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber

		WHILE @@FETCH_STATUS <> -1 
			begin
			-- Step 1.Start. calculate total @Summ_Wight_Rubber
				set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR/100
				FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1
	-- Step 1.Finish. calculate total @Summ_Wight_Rubber
		DECLARe _crs_2 cursor FOR SELECT weight, density,PERCRUBBER   FROM  #WWW 

		open _crs_2
		FETCH NEXT from _crs_2 into @CurrentItem_Weight,@Item_Density, @Item_percRubber

		
				WHILE @@FETCH_STATUS <> -1 
					begin
						set @Item_PHR= @CurrentItem_Weight/@Summ_Wight_Rubber*100				-- Step.2. Ingredient phr
						set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
						--set @COUNT=@COUNT+1
						UPDATE #WWW 
						SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume , PHR =@Item_PHR
						--, ID=@Count 
					WHERE CURRENT OF _crs_2;	
					FETCH NEXT from _crs_2 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
					
					end
				close _crs_2
		deallocate _crs_2
		begin try 
		exec (''delete  from '' + @user)

exec (''
INSERT INTO ''+@User +''
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW''
)

	end try
begin catch
	return -3 -- error undefined
end catch
	
-- sumarry we do not recalc
end -- begin 1, density_recalc

RETURN   
end 


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Phys_Properties_Edit]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
create  procedure  [dbo].[prc_ITF_Igredients_Phys_Properties_Edit]
(@IngredCode_ID bigint,
 @percRubber varchar(50),
@Form varchar(50),
@PercRubtOl varchar(50), 
@PercActMat varchar(50),
@ViscTemp varchar(50),
@ViscML varchar(50),
@ViscMLTOl varchar(50),
@density varchar(50),
@DensityTOl varchar(50),
@ViscTime varchar(50)

)
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''

as
UPDATE    Ingredient_phys_Properties
SET               percRubber = @percRubber, Form = @Form, PercRubtOl = @PercRubtOl, PercActMat = @PercActMat, ViscTemp = @ViscTemp, 
                      ViscTime = @ViscTime, ViscML = @ViscML, ViscMLTOl = @ViscMLTOl, density = @density, DensityTOl = @DensityTOl
WHERE     (IngredientCode_ID = @IngredCode_ID)


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_1_O_W]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_1_O_W] ( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)
) 
returns table 
as 
return
SELECT     TOP (100) Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB AS [Weighing S.], Phase AS [Loading S.], SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, 
                      RecipeID
FROM         dbo.Recipe_Full_RZPT_1_O(''AA'', 72) AS Recipe_Full_RZPT_1_O_1
ORDER BY GRP
--

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_givenPHRS_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'





--dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(''@recipe'', ''@release'')
-- THE INGREDIENT (phr''s) ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT weights
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
--go 
--exec  [Recipe_Tempory_Recalc_givenPHRS_USER] ''00-8-N939'',''0'',''SB''
CREATE   procedure [dbo].[Recipe_Tempory_Recalc_givenPHRS_USER] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50) , @user   NVARCHAR(50))
as 
Create   TABLE  #output(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1

exec (''
INSERT INTO #output
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          ''+@user+'' 
	WHERE     (material IS NOT NULL)
'')


declare @CurrentItem_Weight decimal(8,2),
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 

SET @COUNT=0


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from #output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,''0'') 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				if (@DENSITY = NULL or @DENSITY = 0) set  @DENSITY=1.1
				UPDATE #output 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from #output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
-- calculate Ingredients Volume
WHILE @@FETCH_STATUS = 0 
	begin
		if @Item_Density =NULL or @Item_Density= 0 
				set @Item_Density=1
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE #output 
		SET  weight_recalc = @CurrentItem_Weight, volume = @CurrentItem_Volume,  
		volume_Recalc = @CurrentItem_Volume, phr_Recalc = @Item_PHR WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Scenario 2
begin -- begin 1
		Declare 
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_Wight_Rubber float
		
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  #output 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_Wight_Rubber
			set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR*0.01
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1
	-- Step 1.Finish. calculate total @Summ_Wight_Rubber
		
--Step 2  here we Check PHR for rubber ingredients
		DECLARe _crs_4 cursor FOR SELECT weight, PHR,PERCRUBBER   FROM  #output 
		 
		open _crs_4
		FETCH NEXT from _crs_4 into @CurrentItem_Weight,@Item_PHR, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
			if @Item_percRubber<>0 
				begin
				set @Item_PHR = @CurrentItem_Weight/@Summ_Wight_Rubber *100
				UPDATE #output  SET  PHR = @Item_PHR   WHERE CURRENT OF _crs_4;	
			end 
		FETCH NEXT from _crs_4 into @CurrentItem_Weight,@Item_PHR, @Item_percRubber
			end
		close _crs_4
		deallocate _crs_4
--Step 2 here we  finish Check PHR for rubber ingredients




		-- here we recalculate weights
		DECLARe _crs_2 cursor FOR SELECT PHR, density,PERCRUBBER   FROM  #output 

		open _crs_2
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight=@Item_PHR*@Summ_Wight_Rubber/100				-- Step.2. Ingredient weight
				
				if @Item_Density =NULL or @Item_Density= 0 
				set @Item_Density=1

				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE #output 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume 
				--,  ID=@Count 
WHERE CURRENT OF _crs_2;	
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
			
			end
		close _crs_2
deallocate _crs_2
begin try 
exec (''delete  from '' + @user)

exec (''
INSERT INTO ''+@User +''
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #output''
)

	end try
begin catch
	return -3 -- error undefined
end catch	


end -- begin 1, density_recalc

RETURN   
end 








' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_main_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


-- varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)
CREATE  procedure  [dbo].[prc_ITF_Igredients_main_Insert]
( 
 @name bigint, 
@Descr varchar(50), 
@Info_01 varchar(50), 
@Info_02 varchar(50), 
@Form varchar(50), 
@Cas_Number varchar(50),
 @Group varchar(50), 
@ActualPreisePerKg varchar(50), 
@UpdatedOn smalldatetime, 
@UpdatedBy varchar(10), 
@Status varchar(50), 
@Class varchar(50) 

)
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''

as
INSERT INTO Ingredient_Code
                      ([Name], Descr, Info_01, Info_02, Form, Cas_Number, [Group], ActualPreisePerKg, UpdatedOn, UpdatedBy, Status, Class)
VALUES     (@name, @Descr,@Info_01,@Info_02,@Form,@Cas_Number,@Group,@ActualPreisePerKg,@UpdatedOn,@UpdatedBy,@Status,@Class)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AG_C]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AG_C](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_newTotalVolume_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'





CREATE  procedure  [dbo].[Recipe_Tempory_Recalc_newTotalVolume_USER] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50) ,  @New_Total_Volume float, @user   NVARCHAR(50))
--- parameter @New_Total_Weight is not used


--go 
--
--select  * from [Recipe_Full_RZPT_NewVolume_2](''00-0-0006'',''0'',230)
--
--go


as 
Create   TABLE  #output(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
exec (''
INSERT INTO #output
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          ''+@user+'' 
	WHERE     (material IS NOT NULL)
'')


declare @CurrentItem_Weight float ,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 

SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from #output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,''0'') 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE #output 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from #output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		--set @count=@count+1
		UPDATE #output 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume
--, id=@count 
WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Reverce
if @New_Total_Volume > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_DIV_dens float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_DIV_dens= 0
		DECLARe _crs_1 cursor FOR SELECT PHR,DENSITY, percRubber  FROM  #output 

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR_DIV_dens
			set @Summ_PHR_DIV_dens= @Summ_PHR_DIV_dens+ @Item_PHR/@Item_Density
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1
	-- Step 1.Finish. calculate total @Summ_PHR_DIV_dens

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @RubberWeight= @New_Total_Volume/@Summ_PHR_DIV_dens			-- Step.1.Total rubber weight 
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				UPDATE #output 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1

		DEALLOCATE _crs_1

--Calculating Total
	/*	
Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float

DECLARe _crs_1 cursor FOR SELECT phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  #output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1

			
			FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
		Insert into  #output 
				(phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc)
				values(@phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,@weight_recalc1T/@volume_recalc1T)   ;
		DEALLOCATE _crs_1
*/
end -- begin 1
begin try 
exec (''delete  from '' + @user)

exec (''
INSERT INTO ''+@User +''
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM        #output''
)

	end try
begin catch
	return -3 -- error undefined
end catch	

RETURN   
end 









' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_C]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_C](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_A_relations]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_A_relations](
	[name] [sysname] NULL,
	[modify_date] [datetime] NULL,
	[type] [char](5) NULL,
	[source] [nchar](25) NULL,
	[ioType] [nchar](2) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Mixer_InfoBasic]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Mixer_InfoBasic](
	[Mixer_InfoBasic_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Code] [varchar](50) NULL,
	[Name] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[Costs_perKilo] [varchar](50) NULL,
	[Waste] [varchar](50) NULL,
	[Dust] [varchar](50) NULL,
	[StartTime] [varchar](50) NULL,
	[CycleOVH] [varchar](50) NULL,
	[Decimals] [varchar](50) NULL,
	[Note_Main] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](20) NULL,
 CONSTRAINT [PK_Mixer_InfoBasic] PRIMARY KEY CLUSTERED 
(
	[Mixer_InfoBasic_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_Mixer_InfoBasic] UNIQUE NONCLUSTERED 
(
	[Code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_newTotalWeight_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'




CREATE  procedure  [dbo].[Recipe_Tempory_Recalc_newTotalWeight_USER]
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @New_Total_Weight float
, @user   NVARCHAR(50)
) 

as 
Create   TABLE  #output(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=0
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
exec (''
INSERT INTO #output
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          ''+@user+'' 
	WHERE     (material IS NOT NULL)
'')



declare @CurrentItem_Weight float,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from #output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS = 0
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER  float 
				Declare @Density  float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,''0'') 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE #output 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
			FETCH NEXT FROM _cursor 
			INTO @material,@matIndex,@weight

			end 
			close _cursor
			DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from #output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE #output 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Scenario 3
if @New_Total_Weight > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_= 0
		DECLARe _crs_1 cursor FOR SELECT PHR, density  FROM  #output 

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR
			set @Summ_PHR_= @Summ_PHR_+ @Item_PHR
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
			end
		close _crs_1
		set @RubberWeight= @New_Total_Weight/@Summ_PHR_
	-- Step 1.Finish. calculate total @Summ_PHR

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR, @Item_Density
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE #output 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume
--, Id=@Count 
WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
			end
		close _crs_1

		DEALLOCATE _crs_1

Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float
/*
DECLARe _crs_1 cursor FOR SELECT phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  #output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1

			
			FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
		Insert into  #output 
				(phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc)
				values(@phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,@weight_recalc1T/@volume_recalc1T)   ;
		DEALLOCATE _crs_1

*/
end -- begin 1

begin try 
exec (''delete  from '' + @user)
exec (''
INSERT INTO ''+@User +''
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM        #output''
)

	end try
begin catch
	return -3 -- error undefined
end catch	


RETURN   
end









' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Production_plan]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Production_plan](
	[PLANDATE] [smalldatetime] NULL,
	[PLANID] [nvarchar](15) NULL,
	[ORDERNO] [nvarchar](6) NULL,
	[RCODE] [nvarchar](9) NULL,
	[MTYPE] [nvarchar](1) NULL,
	[STATUS] [float] NULL,
	[BATCHQTY] [float] NULL,
	[PRODQTY] [float] NULL,
	[FIRSTBATCH] [nvarchar](10) NULL,
	[PRIORITY] [float] NULL,
	[CANCELQTY] [float] NULL,
	[BOOKEDQTY] [float] NULL,
	[PRODDATE] [smalldatetime] NULL,
	[ORIGIN] [nvarchar](1) NULL,
	[MODIFIED] [float] NULL,
	[REMARK] [nvarchar](20) NULL,
	[LASTUPDATE] [smalldatetime] NULL,
	[UPDATEDBY] [nvarchar](20) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SYS_SeeObjectsModData]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[SYS_SeeObjectsModData]

as
/****** Object:  Table [dbo].[_A_Tables]    Script Date: 02/12/2015 13:15:59 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[_A_Tables]'')) -- AND type in (N''U''))
DROP TABLE [dbo].[_A_Tables]
--go 
SELECT     name,   modify_date , type
INTO            _A_Tables
FROM         sys.tables
ORDER BY modify_date
--go
SELECT     name, modify_date, type
FROM         _A_Tables
ORDER BY modify_date DESC

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[_A_procedures]'')) --AND type in (N''P''))
DROP TABLE [dbo].[_A_procedures]
--go 
SELECT     name, modify_date, type
INTO            _A_procedures
FROM         sys.procedures
ORDER BY modify_date
--go 
SELECT     name, modify_date, type
FROM         _A_procedures
ORDER BY modify_date DESC

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[_A_Functions]'')) --AND type in (N''TF''))
DROP TABLE [dbo].[_A_Functions]
--go 
SELECT     name, modify_date, type
INTO            _A_Functions
FROM         sys.objects
WHERE     (type = ''IF'' or type = ''TF''or type = ''TF''or type = ''FN''or type = ''FS''or type = ''FT'' )
ORDER BY modify_date desc
--go 
SELECT     name, modify_date, type
FROM         _A_Functions
ORDER BY modify_date DESC
return 




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingredient_Vulco_Code]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingredient_Vulco_Code](
	[Ingredient_Vulco_Code_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[AgeingCode] [varchar](50) NULL,
	[Type] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[Method] [varchar](50) NULL,
	[Temp] [varchar](50) NULL,
	[Time] [varchar](50) NULL,
	[Article] [varchar](50) NULL,
	[MixerId] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[Note] [varchar](max) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](20) NULL,
 CONSTRAINT [PK_Ingredient_Vulco_Code] PRIMARY KEY CLUSTERED 
(
	[Ingredient_Vulco_Code_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AAAAAS]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'





CREATE  FUNCTION [dbo].[AAAAAS] 
( 
   @user varchar(50)
) 
--go 
--select * from [dbo].[Recipe_Full_RZPT_1_OW] (''AA'')
-- gets recipe from recipe.temp !!! parameters not relevant! , use [Recipe_Full_RZPT_1_O]('''','''')
-- calculates the total string
RETURNS  TABLE


as return

SELECT name  FROM sysobjects where name = @user


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AA_C]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AA_C](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Igredients_Init_Load_Warehause_1]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'







-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE FUNCTION [dbo].[fn_ITF_Igredients_Init_Load_Warehause_1]
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@IngredCode_ID varchar (50)

--go 
--select * from [fn_ITF_Igredients_Init_Load_Warehause_1]  (''00014'')
)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     Ingredient_Warehouse.Location, Ingredient_Warehouse.UpdatedOn, Ingredient_Warehouse.UpdatedBy, Ingredient_Warehouse.SiloId, 
                      Ingredient_Warehouse.BalanceID, Ingredient_Warehouse.FreeInfo, Ingredient_Warehouse.IngredientCode_ID, 
                      Ingredient_Warehouse.Ingredient_Warehouse_ID
FROM         Ingredient_Warehouse INNER JOIN
                      Ingredient_Code ON Ingredient_Warehouse.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @IngredCode_ID)
)
--
--go 
--select * from [fn_ITF_Igredients_Init_Load_Warehause_1]  (''00014'')

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Warehause_EDIT]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'








-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_Warehause_EDIT]
(	@IngredientCode_ID bigint,
 @SiloId varchar (50),
@BalanceID varchar (50),
@LotNO varchar (50),
@BOxNo varchar (50),
@Location varchar (50),
@Storetime varchar (50),
@SttempMin varchar (50),
@StTempMax varchar (50),
@StoreDate varchar (50),
 @STDATEEXT varchar (50),
 @ActStock varchar (50),
@MinStock varchar (50),
@LastUsed varchar (50),
 @UsageDate varchar (50),
 @StupDate varchar (50),
 @Form varchar (50), 
 @Quantity varchar (50),
 @QuantUnit varchar (50),
@ORDER varchar (50), 
@OrderUnit varchar (50),
@SWFINE varchar (50), 
@SWVeryFine varchar (50), 
@StopSignal varchar (50),
@Tolerance varchar (50), 
@FreeInfo varchar (50),
@UpdatedOn varchar (50),
@UpdatedBy varchar (50)

)


AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
UPDATE    Ingredient_Warehouse
SET              SiloId = @SiloId, BalanceID = @BalanceID, LotNO = @LotNO, BOxNo = @BOxNo, Location = @Location, Storetime = @Storetime, 
                      SttempMin = @SttempMin, StTempMax = @StTempMax, StoreDate = @StoreDate, STDATEEXT = @STDATEEXT, ActStock = @ActStock, 
                      MinStock = @MinStock, LastUsed = @LastUsed, UsageDate = @UsageDate, StupDate = @StupDate, Form = @Form, Quantity = @Quantity, 
                      QuantUnit = @QuantUnit, [ORDER] = @ORDER, OrderUnit = @OrderUnit, SWFINE = @SWFINE, SWVeryFine = @SWVeryFine, 
                      StopSignal = @StopSignal, Tolerance = @Tolerance, FreeInfo = @FreeInfo, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy
FROM         Ingredient_Warehouse INNER JOIN
                      Ingredient_Code ON Ingredient_Warehouse.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Warehouse.IngredientCode_ID = @IngredientCode_ID)
--
--go
--select *   from [fn_ITF_Igredients_Init_Load_Warehause] (''17543'')




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_NewVolume_2]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'

CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_NewVolume_2] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @New_Total_Volume float) 
--- parameter @New_Total_Weight is not used


--go 
--
--select  * from [Recipe_Full_RZPT_NewVolume_2](''00-0-0006'',''0'',230)
--
--go


RETURNS @output TABLE
(
		[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
INSERT INTO @output 
select * from  [Recipe_Tempory]  WHERE     (material IS NOT NULL)

declare @CurrentItem_Weight float ,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 

SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		--set @count=@count+1
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume
--, id=@count 
WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Reverce
if @New_Total_Volume > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_DIV_dens float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_DIV_dens= 0
		DECLARe _crs_1 cursor FOR SELECT PHR,DENSITY, percRubber  FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR_DIV_dens
			set @Summ_PHR_DIV_dens= @Summ_PHR_DIV_dens+ @Item_PHR/@Item_Density
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1
	-- Step 1.Finish. calculate total @Summ_PHR_DIV_dens

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @RubberWeight= @New_Total_Volume/@Summ_PHR_DIV_dens			-- Step.1.Total rubber weight 
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				UPDATE @OUTPUT 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1

		DEALLOCATE _crs_1

--Calculating Total
	/*	
Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float

DECLARe _crs_1 cursor FOR SELECT phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  @output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1

			
			FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
		Insert into  @OUTPUT 
				(phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc)
				values(@phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,@weight_recalc1T/@volume_recalc1T)   ;
		DEALLOCATE _crs_1
*/
end -- begin 1

RETURN   
end 





' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_UPDATE_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


create   Proc [dbo].[Recipe_Tempory_UPDATE_USER] 
( 
@rowId NVARCHAR(50),
@phr NVARCHAR(50)=NULL,
@weight NVARCHAR(50)=NULL,
@ContainerNb NVARCHAR(50)=NULL,
@phase NVARCHAR(50)=NULL,
@matindex NVARCHAR(50)=NULL,
@user NVARCHAR(50)=''AA''
) 
as
exec (
''UPDATE   '' + @user +
'' SET              PHR = '''''' + @phr+'''''', weight = ''''''+@weight+'''''' , MatIndex = ''''''+@matindex+'''''', ContainerNB = '''''' + @ContainerNb+'''''', Phase = ''''''+ @phase+
 '''''' WHERE     Id = '' + @rowId
)


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_FreeInfo_Delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

CREATE  procedure  [dbo].[prc_ITF_Ingredients_FreeInfo_Delete]
( 
 @Ingred_Free_Info_ID bigint
)


as
DELETE FROM Ingred_Free_Info
WHERE     (Ingred_Free_Info_ID = @Ingred_Free_Info_ID)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_NewWeight_2]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_NewWeight_2] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @New_Total_Weight float
) 
--  Given: Total weight, ingred. phrs, ingred densities
-- To Define:
-- ingred weights
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 

--go 
--
--select  * from [Recipe_Full_RZPT_NewWeight_2](''00-0-0006'',''0'',250)
--
--go
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=0
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
INSERT INTO @output 
select * from  Recipe_Tempory  WHERE     (material IS NOT NULL)
declare @CurrentItem_Weight float,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS = 0
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER  float 
				Declare @Density  float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
			FETCH NEXT FROM _cursor 
			INTO @material,@matIndex,@weight

			end 
			close _cursor
			DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Scenario 3
if @New_Total_Weight > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_= 0
		DECLARe _crs_1 cursor FOR SELECT PHR, density  FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR
			set @Summ_PHR_= @Summ_PHR_+ @Item_PHR
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
			end
		close _crs_1
		set @RubberWeight= @New_Total_Weight/@Summ_PHR_
	-- Step 1.Finish. calculate total @Summ_PHR

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR, @Item_Density
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE @OUTPUT 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume
--, Id=@Count 
WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
			end
		close _crs_1

		DEALLOCATE _crs_1

Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float
/*
DECLARe _crs_1 cursor FOR SELECT phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  @output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1

			
			FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
		Insert into  @OUTPUT 
				(phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc)
				values(@phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,@weight_recalc1T/@volume_recalc1T)   ;
		DEALLOCATE _crs_1

*/
end -- begin 1

RETURN   
end








' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Delete_User]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


create   Proc [dbo].[Recipe_Tempory_Delete_User] 
( 
@Recipe_Recipe_ID bigint, 
 @user   NVARCHAR(50)

) 
as
exec (
''DROP TABLE '' + @user)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_Coments_Edit]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE  procedure  [dbo].[prc_ITF_Ingredients_Coments_Edit]
( 
@IngredientCode_ID bigint,
@Comments varchar(50),
@UpdatedOn varchar(50),
@UpdatedBy varchar(50)
)


as
UPDATE    Ingred_Comments
SET              Comments = @Comments, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy
WHERE     (IngredientCode_ID = @IngredientCode_ID)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_Ingred_AllsupplierS]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE FUNCTION [dbo].[fn_Ingred_AllsupplierS]()

RETURNS @output TABLE (vendor nvarchar(50))
begin
INSERT INTO @output   
       
SELECT DISTINCT PRODUCER AS Vendor
FROM         cprecipe...CPINGRED
UNION
SELECT DISTINCT SUPPLIER AS Vendor
FROM          cprecipe...CPINGRED AS MCCPINGRED_Origin_1
UNION
SELECT DISTINCT [NAME] COLLATE SQL_Latin1_General_CP1_CI_AS AS Vendor
FROM         cprecipe...CPTRADE

RETURN 
end
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_Coments_Delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

create  procedure  [dbo].[prc_ITF_Ingredients_Coments_Delete]
( 
 @Ingred_Info_ID bigint,
@IngredientCode_ID bigint,
@Comments varchar(50),
@NoteValue varchar(50),
@UpdatedOn varchar(50),
@UpdatedBy varchar(50)
)


as
DELETE FROM Ingred_Comments
WHERE     (Ingred_Info_ID = @Ingred_Info_ID)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AAA]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE Function [dbo].[AAA]()
/*
go
select * from [AAA]() where release is not null AND CODE is not null
go
*/
RETURNS  TABLE 
AS
RETURN 
(
SELECT     MIXERID, CASE WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(CODE, 
                      10, 1) WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(origincmp, 10, 1) 
                      WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(CODE, 7, 1) 
                      WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(origincmp, 7, 1) 
                      END AS release, 
                      CASE WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN code WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN origincmp WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(CODE,
                       1, 9) WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(origincmp, 1, 9) 
                      WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''00-'' + code WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''00-'' + origincmp WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN ''00-'' + SUBSTRING(CODE, 1, 
                      6) WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN ''00-'' + SUBSTRING(origincmp, 1, 6) 
                      END AS CODE, CAST(MIXINFO AS varchar(MAX)) AS Expr1, CODE AS Expr2, ORIGINCMP
FROM         CPRECIPE...CPMIXCTR AS CPMIXCTR_1
WHERE     (MIXINFO IS NOT NULL)
)
/*
go
select * from [AAA]() where release is not null AND CODE is not null
go
*/
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Sequence_Steps_SSSW__8]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

CREATE PROCEDURE [dbo].[Insert_Sequence_Steps_SSSW__8]
--
AS
SET NOCOUNT ON;
--Declare 
DECLARE @code nvarchar(10),@release nvarchar(1), @mixerID nvarchar(2), @mixinfo nvarchar(MAX);
DECLARE @count int 

DECLARE _cursor CURSOR FOR 
SELECT     MIXERID, CASE WHEN LEN(CODE) = 9 AND len(ORIGINCMP) = 9 THEN ''0'' WHEN LEN(CODE) > 9 AND len(ORIGINCMP) > 9 THEN ''0'' WHEN LEN(CODE) 
                      = 9 AND len(ORIGINCMP) < 9 THEN ''0'' WHEN LEN(CODE) = 9 AND (ORIGINCMP) IS NULL THEN ''0'' WHEN LEN(CODE) < 9 AND len(ORIGINCMP) 
                      = 9 THEN ''0'' WHEN LEN(CODE) = 6 AND ORIGINCMP IS NULL THEN ''0'' WHEN LEN(CODE) = 7 AND ORIGINCMP IS NULL THEN ''0'' WHEN LEN(CODE) 
                      = 6 AND len(ORIGINCMP) = 6 THEN ''0'' WHEN LEN(CODE) >= 9 AND ORIGINCMP IS NULL THEN substring(ORIGINCMP, 10, 1) WHEN LEN(CODE) 
                      <= 9 AND len(ORIGINCMP) >= 9 THEN substring(ORIGINCMP, 10, 1) WHEN LEN(CODE) >= 9 AND len(ORIGINCMP) >= 9 THEN substring(ORIGINCMP, 10, 
                      1) WHEN LEN(CODE) >= 9 AND len(ORIGINCMP) < 9 THEN substring(ORIGINCMP, 10, 1) WHEN CODE IS NULL AND len(ORIGINCMP) 
                      = 9 THEN substring(ORIGINCMP, 10, 1) END AS release, CASE WHEN LEN(CODE) = 9 AND len(ORIGINCMP) = 9 THEN ORIGINCMP WHEN LEN(CODE) 
                      = 6 AND ORIGINCMP IS NULL THEN ''00-'' + code WHEN LEN(CODE) = 7 AND ORIGINCMP IS NULL THEN ''00-'' + code WHEN LEN(CODE) = 6 AND 
                      len(ORIGINCMP) = 6 THEN ''00-'' + ORIGINCMP WHEN LEN(CODE) >= 9 AND ORIGINCMP IS NULL THEN code WHEN LEN(CODE) <= 9 AND 
                      len(ORIGINCMP) >= 9 THEN substring(ORIGINCMP, 1, 9) WHEN LEN(CODE) >= 9 AND len(ORIGINCMP) >= 9 THEN substring(ORIGINCMP, 1, 9) 
                      WHEN LEN(CODE) >= 9 AND len(ORIGINCMP) < 9 THEN substring(CODE, 1, 9) WHEN CODE IS NULL AND len(ORIGINCMP) 
                      = 9 THEN ORIGINCMP END AS CODE, CAST(MIXINFO AS varchar(MAX)) AS Expr1
FROM         CPRECIPE...CPMIXCTR AS CPMIXCTR_1
--FROM         CPM.dbo.CPMIXCTR  -- original version


OPEN _cursor
set @count=1
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW(
	[mixerid] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[release] [nvarchar](1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[code] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[splitdata] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[step] [int] NULL
) ON [PRIMARY]

DELETE FROM #WWW

FETCH NEXT FROM _cursor 
INTO @mixerID,@release, @code, @mixinfo
WHILE @@FETCH_STATUS <> -1 AND @count<=100000
BEGIN
set @count=@count+1
insert   into #WWW  select @mixerID as mixerID,@release as release,  @code as code , [splitdata], [step]  from dbo.fnSplitString_steps(@mixinfo,
''
'') 
FETCH NEXT FROM _cursor 
INTO @mixerID, @code, @release, @mixinfo
--PRINT @count
end
CLOSE _cursor;
DEALLOCATE _cursor;
--__________
SET NOCOUNT ON;
DECLARE  @step INT;
set @count=1
----++++++++++++++++++++++++++++
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[WWWW]'') AND type in (N''U''))
DROP TABLE [dbo].[WWWW]

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[WWWW]'') AND type in (N''U''))
CREATE TABLE [dbo].[WWWW](
	[mixerid] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[code] [nvarchar](10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	release [nvarchar](1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[command] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[param] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[step] [int] NULL
) ON [PRIMARY]


DECLARE _cursor_SS CURSOR FOR 
SELECT mixerid, code, release,  splitdata,step
FROM #WWW
OPEN _cursor_SS

DELETE FROM [dbo].[WWWW]

FETCH NEXT FROM _cursor_SS 
INTO @mixerid, @code,@release,  @mixinfo,@step
WHILE @@FETCH_STATUS <> -1 ---AND @count<=100000
BEGIN
if (select count(*) from dbo.fnSplitString01(@mixinfo,Char(10)+Char(13))) > 0 
insert  into [dbo].[WWWW]  select @mixerid as mixerid,@release as release, @code as code,
 field1 as command, field2 as param , @step as step     from dbo.fnSplitString01(@mixinfo,Char(10)+Char(13)) 
where ((LEN(field1) >0) AND (LEN(field2) >0))
FETCH NEXT FROM _cursor_SS 
INTO @mixerid, @code, @release, @mixinfo,@step

set @count=@count+1
end



CLOSE _cursor_SS;
DEALLOCATE _cursor_SS;


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnGet_Recipe_test_names]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE  FUNCTION[dbo].[fnGet_Recipe_test_names] () 
RETURNS @output TABLE([Code] [varchar](50) NULL,
	[NoteName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[NoteValue] [text] COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) 
BEGIN 

--Declare 
DECLARE @code nvarchar(50);
DECLARE 
@TestVar1  nvarchar(Max) ,
@TestVar2  nvarchar(Max) ,
@TestVar3  nvarchar(Max) ,
@TestVar4  nvarchar(Max) ,
@TestVar5  nvarchar(Max) ,
@TestVar6  nvarchar(Max) ,
@TestVar7  nvarchar(Max) ,
@TestVar8  nvarchar(Max) ,
@TestVar9  nvarchar(Max) ,
@TestVar10  nvarchar(Max) ,
@TestVar11  nvarchar(Max) ,
@TestVar12  nvarchar(Max) ,
@TestVar13  nvarchar(Max) ,
@TestVar14  nvarchar(Max) ,
@TestVar15  nvarchar(Max) 

DECLARE @count int 

DECLARE _cursor CURSOR FOR 
SELECT     CODE, 
TestVar1  ,
TestVar2   ,
TestVar3   ,
TestVar4  ,
TestVar5   ,
TestVar6   ,
TestVar7   ,
TestVar8   ,
TestVar9   ,
TestVar10  ,
TestVar11   ,
TestVar12 , 
TestVar13 , 
TestVar14 , 
TestVar15 
FROM         CPM.dbo.CPTproc

OPEN _cursor
set @count=1


FETCH NEXT FROM _cursor 
INTO  @code, 
@TestVar1  ,
@TestVar2   ,
@TestVar3   ,
@TestVar4  ,
@TestVar5   ,
@TestVar6   ,
@TestVar7   ,
@TestVar8   ,
@TestVar9   ,
@TestVar10  ,
@TestVar11   ,
@TestVar12 , 
@TestVar13   ,
@TestVar14   ,
@TestVar15  


WHILE @@FETCH_STATUS <> -1 AND @count<=2000
BEGIN
set @count=@count+1



if @TestVar1 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''opslagopgave:'', @TestVar1)


if @TestVar2 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''pre.reg. REACh:'', @TestVar2)


if @TestVar3 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''warehouse_location:'', @TestVar3)

if @TestVar4 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''risico beoord::'', @TestVar4)

if @TestVar5 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''BfR:'', @TestVar5)

if @TestVar6 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar6:'', @TestVar6)

if @TestVar7 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar7:'', @TestVar7)

if @TestVar8 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar8:'', @TestVar8)

if @TestVar9 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar9'', @TestVar9)

if @TestVar10 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar10'', @TestVar10)

if @TestVar11 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar11:'', @TestVar11)

if @TestVar12 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar12:'', @TestVar12)

if @TestVar13 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar13:'', @TestVar12)

if @TestVar14 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar14:'', @TestVar12)

if @TestVar15 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@TestVar15:'', @TestVar12)




FETCH NEXT FROM _cursor 
INTO  @code, 
@TestVar1  ,
@TestVar2   ,
@TestVar3   ,
@TestVar4  ,
@TestVar5   ,
@TestVar6   ,
@TestVar7   ,
@TestVar8   ,
@TestVar9   ,
@TestVar10  ,
@TestVar11   ,
@TestVar12  ,
@TestVar13   ,
@TestVar14   ,
@TestVar15  


end

CLOSE _cursor;
DEALLOCATE _cursor;
    RETURN 
END
--go
--select * from [fnGet_Recipe_test_names]()
--go
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_Coments_Select]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'




CREATE  procedure  [dbo].[prc_ITF_Ingredients_Coments_Select]
( 
 @IngredienCodeId bigint
--@IngredientCode_ID bigint,
--@Comments varchar(50),
--@NoteValue varchar(50),
--@UpdatedOn varchar(50),
--@UpdatedBy varchar(50)
)


as
SELECT     Ingred_Info_ID, IngredientCode_ID, Comments, UpdatedOn, UpdatedBy
FROM         Ingred_Comments
WHERE     (IngredientCode_ID = @IngredienCodeId)






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnGet_Recipe_Free_Text]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE  FUNCTION[dbo].[fnGet_Recipe_Free_Text] () 
RETURNS @output TABLE([Code] [varchar](50) NULL,
[Release] [varchar](50) NULL,
	[NoteName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[NoteValue] [text] COLLATE SQL_Latin1_General_CP1_CI_AS NULL

) 
BEGIN 

--Declare 
DECLARE @code nvarchar(10),@release nvarchar(2), @mixID nvarchar(3);
DECLARE 
@Note  nvarchar(Max) ,
@MixProc  nvarchar(Max),
 @Customer nvarchar(Max)
DECLARE @count int 

DECLARE _cursor CURSOR FOR 
SELECT     CODE, releaSE, 
Note, MixProc,customer
FROM         CPRECIPE...CPRECIPE

OPEN _cursor
set @count=1


FETCH NEXT FROM _cursor 
INTO  @code, @release,
@Note, @MixProc, @Customer

set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END

WHILE @@FETCH_STATUS <> -1 --AND @count<=2000
BEGIN
set @count=@count+1

if @note IS NOT NULL 
--INSERT INTO @output
---SELECT     CODE, RELEASE, ''Note '' as NoteName  , Note
---FROM         CPM.dbo.MCCPRECIPE
---WHERE     (CODE = @code) AND (RELEASE = @release)

if @note is not null
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''Note'', @note)
if @MixProc IS NOT NULL 
INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''MixProc'', @MixProc)

INSERT INTO @output
                    (code, release, noteName,NoteValue)
VALUES     (@code,@release, ''Customer'', @customer)


FETCH NEXT FROM _cursor 
INTO  @code, @release,
@Note, @MixProc,@customer

set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END
end
--select * from @output
CLOSE _cursor;
DEALLOCATE _cursor;
    RETURN 
END

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_Insert_Main_from_Other]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'






/*
this is  for useer interface table sequence 
*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_Insert_Main_from_Other]
/*
to use only if no sequence_main record for the current recipe+release
*/
( 
@Code varchar(50),  --target
@Release varchar(50),  --target
@Mixer_Code varchar(50),  --target
@Code2 varchar(50), --source
@Release2 varchar(50), --source
@Mixer_Code2 varchar(50), --source
@UpdatedOn datetime,
@UpdatedBy varchar(50)
)
as
declare @flag int
 
declare @Recipe_Sequence_Commands bigint

DECLARE _cursor CURSOR FOR 
SELECT     1 AS flag
FROM         Recipe_Sequence_Main
WHERE     (Code = @Code) AND (Release = @Release) AND (Mixer_Code = @Mixer_Code)

OPEN _cursor
FETCH next  FROM _cursor 
INTO @flag
CLOSE _cursor
DEALLOCATE _cursor


IF @@FETCH_STATUS = -1 or  @@FETCH_STATUS = -2
BEGIN
INSERT INTO Recipe_Sequence_Main (Code, Release, Mixer_Code,Info, Status, UpdatedOn, UpdatedBy)
SELECT     @Code AS Code, @Release AS Release, @Mixer_Code AS Mixer_Code, Info, Status, @UpdatedOn, @UpdatedBy
FROM         Recipe_Sequence_Main WHERE     (Code = @Code2) AND (Release = @Release2) AND (Mixer_Code = @Mixer_Code2)

end



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SSS]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[SSS](
	[mixerid] [nvarchar](50) NULL,
	[release] [nvarchar](1) NULL,
	[code] [nvarchar](50) NULL,
	[splitdata] [nvarchar](max) NULL,
	[step] [int] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Sequence_insert_from_Other]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'




/*
this is  for useer interface table sequence 
*/


CREATE   procedure  [dbo].[prc_ITF_Sequence_insert_from_Other]
( 
-- creates the step sequence from another  recipe
@Recipe_Sequence_Main_ID_Target bigint, -- this  is for new records
@Code varchar(50),-- this  is  from  source 
@Release varchar(50), -- this  is  from  source
@Mixer_Code varchar(50),---- this  is  from  source
@UpdatedOn datetime,
@UpdatedBy varchar(50)
)

as

INSERT INTO Recipe_Sequence_Steps
                      (Recipe_Sequence_Main_ID, Step_NB, Command_Param, Recipe_Sequence_Commands, UpdatedOn, UpdatedBy)
SELECT     @Recipe_Sequence_Main_ID_Target AS Recipe_Sequence_Main_ID, Recipe_Sequence_Steps.Step_NB, Recipe_Sequence_Steps.Command_Param, 
                      Recipe_Sequence_Steps.Recipe_Sequence_Commands, @UpdatedOn AS UpdatedOn, @UpdatedBy AS UpdatedOn
FROM         Recipe_Sequence_Steps INNER JOIN
                      Recipe_Sequence_Main ON Recipe_Sequence_Steps.Recipe_Sequence_Main_ID = Recipe_Sequence_Main.Recipe_Sequence_Main_ID
WHERE     (@Release = Recipe_Sequence_Main.Release) AND (@Code = Recipe_Sequence_Main.Code) AND 
                      (@Mixer_Code = Recipe_Sequence_Main.Mixer_Code)

return





' 
END
GO
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
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_GivenWeights_R]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE   Proc [dbo].[Recipe_Tempory_Recalc_GivenWeights_R] 
( 
@recipe NVARCHAR(50),
@release NVARCHAR(50)=''0''
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]''
) 
as
 
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]

begin try
begin

if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
		INSERT INTO #WWW
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.[Recipe_Full_RZPT_Recalc_PHR_GivenWeights_R](@recipe, @release,@user) AS Recipe_Full_RZPT_Recalc_PHR_GivenWeights_1
				delete from [AA]
		INSERT INTO [AA]
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
		FROM         #WWW
	end 
if @user=''AB'' 
	begin
		INSERT INTO #WWW
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM        dbo.[Recipe_Full_RZPT_Recalc_PHR_GivenWeights_R](@recipe, @release,@user) AS Recipe_Full_RZPT_Recalc_PHR_GivenWeights_1
				delete from [AB]
		INSERT INTO [AB]
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
		FROM         #WWW
	end 
if @user=''AC'' 
	begin
		INSERT INTO #WWW
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.[Recipe_Full_RZPT_Recalc_PHR_GivenWeights_R](@recipe, @release,@user) AS Recipe_Full_RZPT_Recalc_PHR_GivenWeights_1
				delete from [AC]
		INSERT INTO [AC]
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
		FROM         #WWW
	end 
end
else
begin
	INSERT INTO #WWW
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.Recipe_Full_RZPT_Recalc_PHR_GivenWeights(@recipe, @release) AS Recipe_Full_RZPT_Recalc_PHR_GivenWeights_1
		delete from [Recipe_Tempory]
		INSERT INTO [Recipe_Tempory]
					  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
					  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
					  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
		FROM         #WWW
	end
return  0
end
end try

begin catch

select ''error 2''
return -2
end catch


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fndeleteFirstChars]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE  FUNCTION [dbo].[fndeleteFirstChars] 
( 
    @string NVARCHAR(MAX))
 
RETURNS  VARCHAR(50) 
 as
BEGIN
    DECLARE @start INT, @output varchar(50);
	set @output= @string
    while   LEN (@output)-1 >0
begin 
set @start = CHARINDEX(''0'', @output) ;
if @start = 1 
begin
 set @output= RIGHT  (@output, LEN (@output)-1 );continue
end
else break
end

RETURN  @output
end
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_Recalc_PHRS_Check]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



--dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(''@recipe'', ''@release'')
-- THE INGREDIENT (phr''s) ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT weights
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredMaterials] (''00-8-N939'',''0'')
CREATE   FUNCTION [dbo].[Recipe_Full_RZPT_Recalc_PHRS_Check] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)) 

--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS] (''00-8-N939'',''0'')
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1


INSERT INTO @output 
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM       Recipe_Tempory
WHERE     (material IS NOT NULL)

declare @CurrentItem_Weight decimal(8,2),
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 

SET @COUNT=0


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				if (@DENSITY = NULL or @DENSITY = 0) set  @DENSITY=1.1
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

end 
close _cursor
DEALLOCATE _cursor

  -- Here we check PHR=100 for all ingredients with polymer content
 -- begin 0
		Declare 
		@Summ_PHR_ float -- total recipe PHR

		set @Summ_PHR_ = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  @output 
		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_Wight_Rubber
			set @Summ_PHR_ = @Summ_PHR_+@CurrentItem_Weight* @Item_percRubbeR*0.01
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1
if @Summ_PHR_ not  between 0.99 and 1.01 
		-- Step 1.Checking if ingredient rubber , edit PHR
begin
 		DECLARe _crs_1 cursor FOR SELECT [PHR], PERCRUBBER   FROM  @output 
		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
			if @Item_percRubber <> 0 
			begin 
				set @Item_PHR = @Item_PHR* @Summ_PHR_
				UPDATE @OUTPUT  	SET  [PHR_recalc] =@Item_PHR 	WHERE CURRENT OF _crs_2;
				FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
			end
		close _crs_1
		deallocate _crs_1
end



	-- Step 1.Finish. calculate total @Summ_Wight_Rubber



  -- Here we recalculate the ingredients, see instructions for Scenario 2
-- begin 1
		Declare 
		@Summ_Wight_Rubber float
		
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_Wight_Rubber
			set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR*0.01
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1
	-- Step 1.Finish. calculate total @Summ_Wight_Rubber


		-- here we recalculate weights
		DECLARe _crs_2 cursor FOR SELECT PHR, density,PERCRUBBER   FROM  @output 

		open _crs_2
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight=@Item_PHR*@Summ_Wight_Rubber/100				-- Step.2. Ingredient weight
				
				if @Item_Density =NULL or @Item_Density= 0 
				set @Item_Density=1

				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE @OUTPUT 
				SET  weight_recalc =@CurrentItem_Weight, volume_Recalc = @CurrentItem_Volume 
				--,  ID=@Count 
WHERE CURRENT OF _crs_2;	
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
			
			end
		close _crs_2
deallocate _crs_2
	



RETURN   
end 

--
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_main_Update]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


-- varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)
CREATE  procedure  [dbo].[prc_ITF_Igredients_main_Update]
( 
 @name bigint, 
@Descr varchar(50), 
@Info_01 varchar(50), 
@Info_02 varchar(50), 
@Form varchar(50), 
@Cas_Number varchar(50),
 @Group varchar(50), 
@ActualPreisePerKg varchar(50), 
@UpdatedOn smalldatetime, 
@UpdatedBy varchar(10), 
@Status varchar(50), 
@Class varchar(50) 

)
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''

as
UPDATE    Ingredient_Code
SET               Descr = @Descr, Info_01 = @Info_01, Info_02 = @Info_02, Form = @Form, Cas_Number = @Cas_Number, [Group] = @Group, 
                      ActualPreisePerKg = @ActualPreisePerKg, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy, Status = @Status, Class = @Class
WHERE     (Name = @name)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_Recalc_PHR_GivenWeights_R]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'









-- THE INGREDIENT WEIGHTS ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT PHRs
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
create   FUNCTION [dbo].[Recipe_Full_RZPT_Recalc_PHR_GivenWeights_R] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]'') 
--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 
--
--select  * from [Recipe_Full_RZPT_Recalc_PHR_GivenWeights](''00-0-0006'',''0'')

RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] int NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
	declare @material varchar(50)
	DECLARE @matIndex VARCHAR(1)
	declare @weight float 
	SET @COUNT=1

-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]

if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AA
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AB'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AB
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AC'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AC
		WHERE     (material IS NOT NULL)
	end 
else
	INSERT INTO @output 
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          Recipe_Tempory
	WHERE     (material IS NOT NULL)
end 





	declare @CurrentItem_Weight float,
			@CurrentItem_Volume float,
			@Item_Density float, 
			@Item_PHR float,
			@Item_percRubber float 

	SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
					open _crs
					FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
					close _CRS
					DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor INTO @material,@matIndex,@weight
end 
close _cursor
DEALLOCATE _cursor

-- 
-- volume calculation ??? this for show old values before recalculation!
DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume, phr = @Item_PHR WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3




  -- Here we recalculate the ingredients, see instructions for Scenario 2
begin -- begin 1
		Declare 
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_Wight_Rubber float
		
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
			-- Step 1.Start. calculate total @Summ_Wight_Rubber
				set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR/100
				FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
	-- Step 1.Finish. calculate total @Summ_Wight_Rubber

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @Item_PHR= @CurrentItem_Weight/@Summ_Wight_Rubber*100				-- Step.2. Ingredient phr
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE @OUTPUT 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume , PHR =@Item_PHR
				--, ID=@Count 
			WHERE CURRENT OF _crs_1;	
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			
			end
		close _crs_1
deallocate _crs_1
	
-- sumarry we do not recalc
end -- begin 1, density_recalc

RETURN   
end 














' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_Temp_View_RecipeSequence_MainB]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE FUNCTION [dbo].[fn_Temp_View_RecipeSequence_MainB]
(	
--Procedure extracts mixing step procedures from original Eclipse file 	
-- original FROM         CPM.dbo.CPMIXCTR
)
RETURNS  TABLE 
AS
RETURN 
(
SELECT DISTINCT 
                      TOP (100) PERCENT CASE WHEN LEN(ORIGINCMP) = 9 AND len(code) = 9 THEN ORIGINCMP WHEN LEN(ORIGINCMP) = 9 AND len(code) 
                      < 9 THEN ORIGINCMP WHEN LEN(ORIGINCMP) = 9 AND ORIGINCMP <> CODE THEN ORIGINCMP WHEN LEN(ORIGINCMP) 
                      = 10 THEN substring(ORIGINCMP, 1, 9) WHEN LEN(CODE) = 9 THEN code WHEN LEN(CODE) > 9 THEN substring(CODE, 1, 9) WHEN LEN(CODE) 
                      < 9 AND LEN(CODE) = 6 THEN ''00-'' + code WHEN LEN(CODE) < 9 AND LEN(CODE) >= 6 THEN ''00-'' + substring(CODE, 1, 6) END AS code, 
                      CASE WHEN LEN(CODE) < 9 AND LEN(CODE) > 6 THEN substring(CODE, 7, 1) WHEN LEN(ORIGINCMP) = 9 AND len(code) 
                      = 9 THEN ''0'' WHEN LEN(ORIGINCMP) = 9 AND len(code) < 9 THEN ''0'' WHEN LEN(ORIGINCMP) = 9 AND 
                      ORIGINCMP <> CODE THEN ''0'' WHEN LEN(ORIGINCMP) = 10 THEN substring(ORIGINCMP, 10, 1) WHEN LEN(CODE) = 9 THEN ''0'' WHEN LEN(CODE) 
                      > 9 THEN substring(CODE, 10, 1) WHEN LEN(CODE) < 9 AND LEN(CODE) = 6 THEN ''0'' END AS release, MIXERID, DESCR, LASTUPDATE, UPDATEDBY, 
                      STATUS, CODE AS Expr1, ORIGINCMP
FROM         CPRecipe...CPMIXCTR
-- original FROM         CPM.dbo.CPMIXCTR
WHERE     (CODE LIKE N''[0-9][0-9][-][0-8][-]____'' OR
                      CODE LIKE N''[0-8][-]____'' OR
                      CODE LIKE N''[0-9][0-9][-][0-8][-]_____'' OR
                      CODE LIKE N''[0-8][-]_____'') AND (ORIGINCMP IS NULL OR
                      ORIGINCMP LIKE N''[0-9][0-9][-][0-8][-]____'' OR
                      ORIGINCMP LIKE N''[0-9][0-9][-][0-8][-]_____'' OR
                      ORIGINCMP LIKE N''[0-8][-]____'' OR
                      ORIGINCMP LIKE N''[0-8][-]_____'' OR
                      ORIGINCMP LIKE N''______'')
)
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnSplitString01]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE FUNCTION [dbo].[fnSplitString01] 
( 
    @string NVARCHAR(MAX), 
    @delimiter CHAR(1) 
) 
RETURNS @output TABLE(field1 NVARCHAR(MAX), field2   NVARCHAR(MAX)) 

BEGIN 
    DECLARE @start INT, @end INT 
   
 set @start = 1;
	set @end = CHARINDEX(Char(166), @string) ;
    IF @end > 0 AND @start < LEN(@string) + 1 BEGIN 
        IF @end = 0  
            SET @end = LEN(@string) + 1
       
        INSERT INTO @output (field1,field2)  
        VALUES(
		RTRIM(Ltrim(SUBSTRING(@string, @start, @end - @start))),
		RTRIM(Ltrim(SUBSTRING(@string,  (@end - @start+ len(@delimiter))+1,(len(@string) - @end - @start+ len(@delimiter))) ))
		)
	return 
    end
   

 set @start = 1;
	set @end = CHARINDEX(Char(9), @string) ;
    IF @end > 0 AND @start < LEN(@string) + 1 BEGIN 
        IF @end = 0  
            SET @end = LEN(@string) + 1 
            INSERT INTO @output (field1,field2)  
			VALUES(RTRIM(Ltrim(SUBSTRING(@string, @start, @end - @start))), RTRIM(Ltrim(SUBSTRING(@string,  (@end - @start+ len(@delimiter))+1, (len(@string) - @end - @start+ len(@delimiter))) )))
	return
	end

--print @string+''3D ''+@delimiter+'' ''+RTRIM(Ltrim(SUBSTRING(@string, @start, @end - @start)))+'' ''+RTRIM(Ltrim(SUBSTRING(@string,  (@end - @start+ len(@delimiter))+1,(len(@string) - @end - @start+ len(@delimiter))) ))

    RETURN 
END

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingredient_Aeging_Code]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingredient_Aeging_Code](
	[Ingredient_Aeging_Code_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[AgeingCode] [varchar](50) NULL,
	[Type] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[Method] [varchar](50) NULL,
	[Temp] [varchar](50) NULL,
	[Time] [varchar](50) NULL,
	[Article] [varchar](50) NULL,
	[MixerId] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[Note] [varchar](max) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[TimeUnit] [nchar](10) NULL,
 CONSTRAINT [PK_Ingredient_Aeging_Code] PRIMARY KEY CLUSTERED 
(
	[Ingredient_Aeging_Code_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnSplitString_Steps]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE FUNCTION [dbo].[fnSplitString_Steps] 
( 
    @string NVARCHAR(MAX), 
    @delimiter CHAR(2) 
) 
RETURNS @output TABLE(splitdata NVARCHAR(MAX) , step INT
) 
-- splits string like SET ROTOR (speed)¦20.0  ADD PHASE¦1.0  WAIT UNTIL (temp)¦75.0  ADD PHASE¦2.0  WAIT UNTIL (temp)¦85.0  SWEEP (time)¦1.0  WAIT UNTIL (temp)¦108.0  SET RAM (0=UP/2=HALF/1=DOWN)¦0.0  SET DISCHARGE (1=OPEN/0=CLOSE)¦1.0  lege regel¦  lege regel¦  lege regel¦  lege regel¦  lege regel¦  lege regel¦  lege regel¦  lege regel¦  lege regel¦  lege regel¦  lege regel¦  MIXCONT SEQUENCE¦  IDEAL TIME MIN¦160.0  IDEAL TIME MAX¦200.0  DISCHARGE TEMPERATURE MIN¦104.0  DISCHARGE TEMPERATURE MAX¦108.0  BASIC SPEED¦20.0  PRESSURE LIMIT MIN¦7.0  PRESSURE LIMIT MAX¦7.0  CONTROL TYPE¦3.0  VISCOSITY LEVEL¦3.0  ADD PHASE¦1.0  PRE-CONTROL¦  ADD PHASE¦2.0  MAIN CONTROL¦  SET RAM (0=UP/2=HALF/1=DOWN)¦0.0  SET DISCHARGE (1=OPEN/0=CLOSE)¦1.0
-- on command steps!
-- to test use procedure/script saved 
BEGIN 
DECLARE @step INT
set @step=1
    DECLARE @start INT, @end INT 
    SELECT @start = 1, @end = CHARINDEX(@delimiter, @string) 
    WHILE @start < LEN(@string) + 1 BEGIN 
        IF @end = 0  
            SET @end = LEN(@string) + 2
       
       INSERT INTO @output (splitdata, step)  
       VALUES(SUBSTRING(@string, @start, @end - @start), @step) 
        SET @start = @end + len(@delimiter)
        SET @end = CHARINDEX(@delimiter, @string, @start)
        set @step=@step+1
    END 
    RETURN 
END
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_Recalc_PHRS_Check1]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'






--dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(''@recipe'', ''@release'')
-- THE INGREDIENT (phr''s) ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT weights
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredMaterials] (''00-8-N939'',''0'')
CREATE   procedure [dbo].[Recipe_Full_RZPT_Recalc_PHRS_Check1] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)) 

--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS] (''00-8-N939'',''0'')
as 
declare @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)

  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1

select * from Recipe_Tempory
INSERT INTO @output 
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM       Recipe_Tempory
WHERE     (material IS NOT NULL)

declare @CurrentItem_Weight decimal(8,2),
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@PHR_recalc float,
		@Item_percRubber float 

SET @COUNT=0


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				if (@DENSITY = NULL or @DENSITY = 0) set  @DENSITY=1.1
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

end 
close _cursor
DEALLOCATE _cursor


  -- Here we check PHR=100 for all ingredients with polymer content
 -- begin 0
		Declare 
		@Summ_PHR_ float -- total recipe PHR

		set @Summ_PHR_ = 0
 			DECLARe _crs_1 cursor FOR SELECT [PHR], PERCRUBBER   FROM  @output 
		open _crs_1
			FETCH NEXT from _crs_1 into @Item_PHR, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
			set @Summ_PHR_ = @Summ_PHR_+@Item_PHR*@Item_percRubbeR/100
			FETCH NEXT from _crs_1 into @Item_PHR, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1
			

-- Step 1.Checking if ingredient rubber PHR =100%, edit PHR
if @Summ_PHR_ not  between 0.99 and 1.01 
	begin
	DECLARe _crs_1 cursor FOR SELECT PHR,  PERCRUBBER   FROM  @output 
		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
		begin
				if @Item_percRubber <> 0 set @Item_PHR = @Item_PHR/ @Summ_PHR_*100
				UPDATE @OUTPUT  	SET  PHR =@Item_PHR	WHERE CURRENT OF _crs_1;
			FETCH NEXT from _crs_1 into @Item_PHR, @Item_percRubber
		end
end
close _crs_1
deallocate _crs_1

--select * from @output order by grp asc
--return

	-- Step 1.Finish. calculate total @Summ_Wight_Rubber

  -- Here we recalculate the ingredients, see instructions for Scenario 2
-- begin 1
		Declare @Summ_Wight_Rubber float
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_Wight_Rubber
			set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR*0.01
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1

	-- Step 1.Finish. calculate total @Summ_Wight_Rubber
		-- here we recalculate weights
		DECLARe _crs_2 cursor FOR SELECT PHR,  density,PERCRUBBER   FROM  @output 
		open _crs_2
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
			if @Item_Density =NULL or @Item_Density= 0 	set @Item_Density=1
			WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight=@Item_PHR*@Summ_Wight_Rubber/100				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
				UPDATE @OUTPUT 
				SET  weight = @CurrentItem_Weight, volume= @CurrentItem_Volume 
				--,  ID=@Count 
WHERE CURRENT OF _crs_2;	
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
			
			end
		close _crs_2
deallocate _crs_2

--select * from @output
delete from Recipe_Tempory
INSERT INTO Recipe_Tempory select * from @output order by grp asc

RETURN   
--go
-- execute [Recipe_Full_RZPT_Recalc_PHRS_Check1] '''',''''

--

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_main_Delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'









-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
create  procedure [dbo].[prc_ITF_Igredients_main_Delete]

(	
@name varchar (50)
)

AS


DELETE FROM Ingredient_Code
WHERE     (Name = @name)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_NewWeight_2R]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
create  FUNCTION [dbo].[Recipe_Full_RZPT_NewWeight_2R] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @New_Total_Weight float, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]''
) 
--  Given: Total weight, ingred. phrs, ingred densities
-- To Define:
-- ingred weights
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 

--go 
--
--select  * from [Recipe_Full_RZPT_NewWeight_2](''00-0-0006'',''0'',250)
--
--go
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=0
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AA
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AB'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AB
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AC'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AC
		WHERE     (material IS NOT NULL)
	end 
else
	INSERT INTO @output 
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          Recipe_Tempory
	WHERE     (material IS NOT NULL)
end 




declare @CurrentItem_Weight float,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS = 0
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER  float 
				Declare @Density  float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
			FETCH NEXT FROM _cursor 
			INTO @material,@matIndex,@weight

			end 
			close _cursor
			DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Scenario 3
if @New_Total_Weight > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_= 0
		DECLARe _crs_1 cursor FOR SELECT PHR, density  FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR
			set @Summ_PHR_= @Summ_PHR_+ @Item_PHR
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
			end
		close _crs_1
		set @RubberWeight= @New_Total_Weight/@Summ_PHR_
	-- Step 1.Finish. calculate total @Summ_PHR

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR, @Item_Density
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE @OUTPUT 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume
--, Id=@Count 
WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density
			end
		close _crs_1

		DEALLOCATE _crs_1

Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float
/*
DECLARe _crs_1 cursor FOR SELECT phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  @output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1

			
			FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
		Insert into  @OUTPUT 
				(phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc)
				values(@phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,@weight_recalc1T/@volume_recalc1T)   ;
		DEALLOCATE _crs_1

*/
end -- begin 1

RETURN   
end







' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnSplit_RECIPE_MAterial]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE FUNCTION [dbo].[fnSplit_RECIPE_MAterial] 
( 
    @string NVARCHAR(MAX)
) 
  

RETURNS @output TABLE(matindex NVARCHAR(50),ingredName NVARCHAR(50),PHR NVARCHAR(50),PHASE NVARCHAR(50),SiloId NVARCHAR(50),weight NVARCHAR(50)) 

BEGIN

DECLARE  @matindex NVARCHAR(1)
DECLARE @ingredName NVARCHAR(50)
DECLARE @PHR NVARCHAR(50)
DECLARE @PHASE NVARCHAR(50)
DECLARE @SiloId NVARCHAR(50)
DECLARE @weight NVARCHAR(50)
IF  @string <> NULL or len (LTRIM(RTRIM(@string)))<>0
BEGIN
set @matindex= CONVERT (varchar, SUBSTRING(@string, 1, 1));
set @ingredName=  CONVERT(varchar, SUBSTRING(@string, 2, 11));
set @PHR= CONVERT(varchar, SUBSTRING(@string, 12, 7))  ;
set @PHASE= CONVERT(varchar, SUBSTRING(@string, 19, 1));
set @SiloId=  CONVERT(varchar, sUBSTRING(@string, 20, 1));
set @weight=  CONVERT(decimal(10, 2), SUBSTRING(@string, 21, 7)) ;

INSERT INTO @output (matindex,ingredName,PHR,PHASE,SiloId,weight)  
        VALUES(
RTRIM(Ltrim(@matindex)),
RTRIM(Ltrim(@ingredName)),
RTRIM(Ltrim(@PHR)),
RTRIM(Ltrim(@PHASE)),
RTRIM(Ltrim(@SiloId)),
RTRIM(Ltrim(@weight)))
       

END
return

  RETURN   
end
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SSSS]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[SSSS](
	[mixerid] [nvarchar](50) NULL,
	[code] [nvarchar](10) NULL,
	[release] [nvarchar](1) NULL,
	[command] [nvarchar](max) NULL,
	[param] [nvarchar](max) NULL,
	[step] [int] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnGet_Ingred_Free_Info]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE  FUNCTION[dbo].[fnGet_Ingred_Free_Info] () 
RETURNS @output TABLE([Code] [varchar](50) NULL,
	[NoteName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[NoteValue] [text] COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) 
--select * from [fnGet_Ingred_Free_Info]()

BEGIN 

--Declare 
DECLARE @code nvarchar(10),@release nvarchar(2), @mixID nvarchar(3);
DECLARE 
@FreeInfo1  nvarchar(Max) ,
@FreeInfo2  nvarchar(Max) ,
@FreeInfo3  nvarchar(Max) ,
@FreeInfo4  nvarchar(Max) ,
@FreeInfo5  nvarchar(Max) ,
@FreeInfo6  nvarchar(Max) ,
@FreeInfo7  nvarchar(Max) ,
@FreeInfo8  nvarchar(Max) ,
@FreeInfo9  nvarchar(Max) ,
@FreeInfo10  nvarchar(Max) ,
@FreeInfo11  nvarchar(Max) ,
@FreeInfo12  nvarchar(Max) 

DECLARE @count int 

DECLARE _cursor CURSOR FOR 
SELECT     CODE, 
FreeInfo1  ,
FreeInfo2   ,
FreeInfo3   ,
FreeInfo4  ,
FreeInfo5   ,
FreeInfo6   ,
FreeInfo7   ,
FreeInfo8   ,
FreeInfo9   ,
FreeInfo10  ,
FreeInfo11   ,
FreeInfo12  
FROM         CPRECIPE...CPingred
---FROM         CPM.dbo.CPingred

OPEN _cursor
set @count=1


FETCH NEXT FROM _cursor 
INTO  @code, 
@FreeInfo1  ,
@FreeInfo2   ,
@FreeInfo3   ,
@FreeInfo4  ,
@FreeInfo5   ,
@FreeInfo6   ,
@FreeInfo7   ,
@FreeInfo8   ,
@FreeInfo9   ,
@FreeInfo10  ,
@FreeInfo11   ,
@FreeInfo12  


WHILE @@FETCH_STATUS <> -1 
BEGIN
set @count=@count+1



if @FreeInfo1 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''opslagopgave:'', @FreeInfo1)


if @FreeInfo2 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''pre.reg. REACh:'', @FreeInfo2)


if @FreeInfo3 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''warehouse_location:'', @FreeInfo3)

if @FreeInfo4 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''risico beoord::'', @FreeInfo4)

if @FreeInfo5 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''BfR:'', @FreeInfo5)

if @FreeInfo6 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@FreeInfo6:'', @FreeInfo6)

if @FreeInfo7 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@FreeInfo7:'', @FreeInfo7)

if @FreeInfo8 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@FreeInfo8:'', @FreeInfo8)

if @FreeInfo9 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@FreeInfo9'', @FreeInfo9)

if @FreeInfo10 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@FreeInfo10'', @FreeInfo10)

if @FreeInfo11 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@FreeInfo11:'', @FreeInfo11)

if @FreeInfo12 is not null
INSERT INTO @output
                    (code,  noteName,NoteValue)
VALUES     (@code, ''@FreeInfo12:'', @FreeInfo12)


FETCH NEXT FROM _cursor 
INTO  @code, 
@FreeInfo1  ,
@FreeInfo2   ,
@FreeInfo3   ,
@FreeInfo4  ,
@FreeInfo5   ,
@FreeInfo6   ,
@FreeInfo7   ,
@FreeInfo8   ,
@FreeInfo9   ,
@FreeInfo10  ,
@FreeInfo11   ,
@FreeInfo12  

end

CLOSE _cursor;
DEALLOCATE _cursor;
    RETURN 
END
--go 
--select * from [fnGet_Ingred_Free_Info]()
--go

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[generate_Empty_CSVColumn]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'--Error codes
-- 0 Acomplited OK
-- all other see source coode
CREATE procedure   [dbo].[generate_Empty_CSVColumn] 
	-- Add the parameters for the stored procedure here
   ( @Column int )
as  
	DECLARE @count as int
	DECLARE @count1 as int
	Declare @_step Int, @PLCCode varchar (50), @Command_Param varchar (50)
	declare @str varchar(200)

begin try  --TRY 1

	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' ='''''''''''''''''''''''''''''''''''''''''''''''' where ID=2 ''
	exec (@str)
	--print ''-- setting @PLCCode=@rECIPEnAME to  field 3''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' ='''''''''''''''''''''''''''''''''''''''''''''''' where ID=3 ''
	exec (@str)
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''0'''' where ID=4 ''
	exec (@str)

end try
begin catch
	return -8
end catch 

begin try 
	-- Step(1) cleaning up all records in chosen column 
	set @PLCCode=''0''+''.''
	-- setting 0. to all column cells
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+ @PLCCode+'''''' where ID>4 and ID <125''
	exec (@str)

end try
begin catch
	return -7
end catch 

-- set NULL and ZERO fields to weighing after weighing was filled in prev. step
begin try 

	set @count= 125
	
	declare @material varchar(50)
	declare @WeighingId varchar(5)
	declare @weight decimal(10,3)
	declare @phase varchar(5)

while @count<=204
begin 
	set @material=''''''''''''''''''''+''''+''''''''''''''''''''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+ @material+'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		--print @str
	execute  (@str)
	set @Count=@Count+1

	set @WeighingId=''0''
	set @WeighingId=cast (@WeighingId as varchar(5))+''.''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@WeighingId as varchar(5)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1

	set @weight =0.000
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@weight as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1

	set @phase =''0''
	set @phase =@phase+''.''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@phase as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1
end
end try
begin catch
	return -4
end catch 
return 0
--go 
--DECLARE	@return_value int

--EXEC	@return_value = [dbo].[generate_Empty_CSVColumn]
		--@Column = 5

--SELECT	''Return Value'' = @return_value

--go 
--select * from dbo.[Recipe.csv]
--go
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Phys_Properties_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
create  procedure  [dbo].[prc_ITF_Igredients_Phys_Properties_Insert]
(@IngredCode_ID bigint,
 @percRubber varchar(50),
@Form varchar(50),
@PercRubtOl varchar(50), 
@PercActMat varchar(50),
@ViscTemp varchar(50),
@ViscML varchar(50),
@ViscMLTOl varchar(50),
@density varchar(50),
@DensityTOl varchar(50),
@ViscTime varchar(50)

)

as
INSERT INTO Ingredient_phys_Properties
                      (percRubber, Form, PercRubtOl, PercActMat, ViscTemp, ViscTime, ViscML, ViscMLTOl, density, DensityTOl, IngredientCode_ID)
VALUES     (@percRubber,@Form,@PercRubtOl,@PercActMat,@ViscTemp,@ViscTime,@ViscML,@ViscMLTOl,@density,@DensityTOl,@IngredCode_ID)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[CountFreeInfo]'))
EXEC dbo.sp_executesql @statement = N'
CREATE  VIEW [dbo].[CountFreeInfo]
AS
SELECT     NoteName
FROM         dbo.Recipe_Prop_Free_Info
GROUP BY NoteName
' 
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_NewVolume_2_R]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'


create  FUNCTION [dbo].[Recipe_Full_RZPT_NewVolume_2_R] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @New_Total_Volume float , @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]'')
--- parameter @New_Total_Weight is not used


--go 
--
--select  * from [Recipe_Full_RZPT_NewVolume_2](''00-0-0006'',''0'',230)
--
--go


RETURNS @output TABLE
(
		[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AA
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AB'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AB
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AC'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AC
		WHERE     (material IS NOT NULL)
	end 
else
	INSERT INTO @output 
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          Recipe_Tempory
	WHERE     (material IS NOT NULL)
end 


declare @CurrentItem_Weight float ,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 

SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		--set @count=@count+1
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume
--, id=@count 
WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Reverce
if @New_Total_Volume > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_DIV_dens float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_DIV_dens= 0
		DECLARe _crs_1 cursor FOR SELECT PHR,DENSITY, percRubber  FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR_DIV_dens
			set @Summ_PHR_DIV_dens= @Summ_PHR_DIV_dens+ @Item_PHR/@Item_Density
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1
	-- Step 1.Finish. calculate total @Summ_PHR_DIV_dens

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @RubberWeight= @New_Total_Volume/@Summ_PHR_DIV_dens			-- Step.1.Total rubber weight 
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				UPDATE @OUTPUT 
				SET  weight =@CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1

		DEALLOCATE _crs_1

--Calculating Total
	/*	
Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float

DECLARe _crs_1 cursor FOR SELECT phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  @output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1

			
			FETCH NEXT from _crs_1 into @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
		Insert into  @OUTPUT 
				(phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc)
				values(@phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,@weight_recalc1T/@volume_recalc1T)   ;
		DEALLOCATE _crs_1
*/
end -- begin 1

RETURN   
end 






' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE   Proc [dbo].[Recipe_Tempory_Delete] 
( 
@Recipe_Recipe_ID bigint

) 
as

DELETE FROM Recipe_Tempory
WHERE     (Recipe_Recipe_ID = @Recipe_Recipe_ID)


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Phys_Properties_Select]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE  procedure  [dbo].[prc_ITF_Igredients_Phys_Properties_Select]
(@name varchar(50))

as
SELECT     Ingredient_phys_Properties.Ingredient_phys_Properties_ID, Ingredient_phys_Properties.IngredientCode_ID, Ingredient_phys_Properties.Form, 
                      Ingredient_phys_Properties.percRubber, Ingredient_phys_Properties.PercRubtOl, Ingredient_phys_Properties.PercActMat, 
                      Ingredient_phys_Properties.ViscTemp, Ingredient_phys_Properties.ViscTime, Ingredient_phys_Properties.ViscML, 
                      Ingredient_phys_Properties.ViscMLTOl, Ingredient_phys_Properties.density, Ingredient_phys_Properties.DensityTOl, 
                      Ingredient_phys_Properties.UpdatedOn, Ingredient_phys_Properties.UpdatedBy
FROM         Ingredient_phys_Properties INNER JOIN
                      Ingredient_Code ON Ingredient_phys_Properties.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @name)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[TEMP_DATA_TRANSFEREXTERNALSERVER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[TEMP_DATA_TRANSFEREXTERNALSERVER]
as
exec sp_addlinkedserver 
@server = N''[192.168.2.11]'', 
@srvproduct=N''SQL Server'' ;

--go
EXEC sp_addlinkedsrvlogin 
 @useself = ''FALSE'',
@rmtsrvname = N''[192.168.2.11]'',  
@rmtuser = ''sa'',             -- add here your login on Azure DB
@rmtpassword =  ''Mcpc1Service'' -- add here your password on Azure DB
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Tempory](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS_R]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



--dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(''@recipe'', ''@release'')
-- THE INGREDIENT (phr''s) ARE GIVEN/CHANGED. RECALCULATE THE :
-- INGREDIENT weights
-- RECIPE TOTALS
-- THIS IS USED FOR RECIPE MANAGEMNET - interaction calculation by changing weights
-- IF there is need to ''play '' with changing of ingredients ''PHRs'' we have to use ''Recipe_Full_RZPT_NewVolume_1'' or Recipe_Full_RZPT_NewWeight_1
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredMaterials] (''00-8-N939'',''0'')
create   FUNCTION [dbo].[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS_R] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50) 
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]'') 

--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 
--select * from [Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS] (''00-8-N939'',''0'')
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AA
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AB'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AB
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AC'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AC
		WHERE     (material IS NOT NULL)
	end 
else
	INSERT INTO @output 
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          Recipe_Tempory
	WHERE     (material IS NOT NULL)
end 



declare @CurrentItem_Weight float ,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 

SET @COUNT=0


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				if (@DENSITY = NULL or @DENSITY = 0) set  @DENSITY=1.1
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight 

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
-- calculate Ingredients Volume
WHILE @@FETCH_STATUS = 0 
	begin
		if @Item_Density =NULL or @Item_Density= 0 
				set @Item_Density=1
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		UPDATE @OUTPUT 
		SET  weight_recalc = @CurrentItem_Weight, volume = @CurrentItem_Volume,  
		volume_Recalc = @CurrentItem_Volume, phr_Recalc = @Item_PHR WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Scenario 2
begin -- begin 1
		Declare 
		@Summ_PHR_ float, -- total recipe PHR
		@Summ_Wight_Rubber float
		
		set @Summ_Wight_Rubber = 0
		DECLARe _crs_1 cursor FOR SELECT weight, density,PERCRUBBER   FROM  @output 

		open _crs_1
		FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_Wight_Rubber
			set @Summ_Wight_Rubber = @Summ_Wight_Rubber+@CurrentItem_Weight* @Item_percRubbeR*0.01
			FETCH NEXT from _crs_1 into @CurrentItem_Weight,@Item_Density, @Item_percRubber
			end
		close _crs_1
		deallocate _crs_1
	-- Step 1.Finish. calculate total @Summ_Wight_Rubber

		-- here we recalculate weights
		DECLARe _crs_2 cursor FOR SELECT PHR, density,PERCRUBBER   FROM  @output 

		open _crs_2
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @CurrentItem_Weight=@Item_PHR*@Summ_Wight_Rubber/100				-- Step.2. Ingredient weight
				
				if @Item_Density =NULL or @Item_Density= 0 
				set @Item_Density=1

				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density				-- step.4. Ingredient volume
				--set @COUNT=@COUNT+1
				UPDATE @OUTPUT 
				SET  weight_recalc =@CurrentItem_Weight, volume_Recalc = @CurrentItem_Volume 
				--,  ID=@Count 
WHERE CURRENT OF _crs_2;	
		FETCH NEXT from _crs_2 into @Item_PHR,@Item_Density, @Item_percRubber
			
			end
		close _crs_2
deallocate _crs_2
	


end -- begin 1, density_recalc

RETURN   
end 

--
--go 
--SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
--                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
--FROM         dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(''01-8-N753'', ''0'')


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Sequence_temp]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE  Function [dbo].[Sequence_temp]()
/*
go
select * from [Sequence_temp]() where release is not null AND CODE is not null
go
*/
RETURNS  TABLE 
AS
RETURN 
(
SELECT     MIXERID, CASE WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(CODE, 
                      10, 1) WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(origincmp, 10, 1) 
                      WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''0'' WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(CODE, 7, 1) 
                      WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(origincmp, 7, 1) 
                      END AS release, 
                      CASE WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN code WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN origincmp WHEN CODE LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(CODE,
                       1, 9) WHEN origincmp LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND 
                      CODE NOT LIKE ''[ 0-9][0-9][-][0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN SUBSTRING(origincmp, 1, 9) 
                      WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''00-'' + code WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' AND 
                      CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9]'' THEN ''00-'' + origincmp WHEN CODE LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN ''00-'' + SUBSTRING(CODE, 1, 
                      6) WHEN origincmp LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' AND CODE NOT LIKE ''[0-9][-][0-9,A-Z][0-9,A-Z][0-9][0-9][A-Z]'' THEN ''00-'' + SUBSTRING(origincmp, 1, 6) 
                      END AS CODE, CAST(MIXINFO AS varchar(MAX)) AS MIXINFO, CODE AS Expr2, ORIGINCMP
FROM         CPRECIPE...CPMIXCTR AS CPMIXCTR_1
WHERE     (MIXINFO IS NOT NULL)
)
/*
go
select * from [Sequence_temp]() where release is not null AND CODE is not null
go
*/
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_Seq]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_Seq] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @mixer_code NVARCHAR(50)
) 

-- gets recipe for the final batches, all masters are calculated recurcively
--go
--select * from  [Recipe_Full_RZPT] (''01-8-1432'', ''0'')
RETURNS @output TABLE
(
	[RecipeID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Mixer_Code] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Weight] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,	
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [smalldatetime] NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)
--SELECT     RecipeID, Release,mixer_code, material, PHR, Weight, ContainerNB, Phase, percRubber, density, weighingID, BalanceID, SiloId, MatIndex, PriceKG, PriceData,   Descr, GRP
--FROM         dbo.[Recipe_Full_RZPT_Seq](''01-8-1432'', ''0'',''AA'') 
--ORDER BY Phase, ContainerNB
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
SET @COUNT=1
INSERT INTO @output
--select * from [Recipe__RZPT_Query](@rECIPEnAME,@Rrelease)
SELECT     Recipe_Prop_Main.Code AS RecipeID, Recipe_Prop_Main.Release, Recipe_Prop_Main.Mixer_Code, Recipe_Recipe.IngredName AS material, 
                      Recipe_Recipe.PHR, Recipe_Recipe.Weight, CASE WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      dbo.Recipe_Recipe.SiloId = '''') THEN ''A'' WHEN Recipe_Recipe.SiloID IS NOT NULL THEN Recipe_Recipe.SiloID END AS ContainerNB, 
                      Recipe_Recipe.Phase, Ingredient_phys_Properties.percRubber, Ingredient_phys_Properties.density, 
                      CASE WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 256 + Ingredient_Warehouse.BalanceID WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NULL THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      ingredient_Warehouse.BalanceID IS NOT NULL THEN 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      CONVERT(float, dbo.Recipe_Recipe.PHR) >= 100 THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 2 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 3 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 4 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 5 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 6 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 7 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 7 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 7 * 256 + 1 END AS weighingID, CASE WHEN Ingredient_Warehouse.BalanceID IS NULL 
                      THEN 1 WHEN Ingredient_Warehouse.BalanceID IS NOT NULL THEN Ingredient_Warehouse.BalanceID END AS BalanceID, 
                      Ingredient_Warehouse.SiloId, Recipe_Recipe.MatIndex, Ingred_Preise.PreisePerKg AS PriceKG, Ingred_Preise.Prisedate AS PriceData, 
                      Ingredient_Code.Descr, Ingredient_Code.[Group] AS GRP
FROM         Ingredient_phys_Properties RIGHT OUTER JOIN
                      Ingredient_Code ON Ingredient_phys_Properties.IngredientCode_ID = Ingredient_Code.IngredientCode_ID LEFT OUTER JOIN
                      Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID LEFT OUTER JOIN
                      Ingred_Preise ON Ingredient_Code.IngredientCode_ID = Ingred_Preise.IngredientCode_ID RIGHT OUTER JOIN
                      Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID ON Ingredient_Code.Name = Recipe_Recipe.IngredName
WHERE     (Recipe_Prop_Main.Code IS NOT NULL) AND (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Prop_Main.Release = @Rrelease) AND 
                      (@Mixer_Code IS NULL OR
                      @Mixer_Code = Recipe_Prop_Main.Mixer_Code)
SET @COUNT=@COUNT+1


DECLARE _cursor CURSOR FOR  select material, MatIndex from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER varchar(50) 
				Declare @Density varchar(50)
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
	INTO @material,@matIndex

end 
close _cursor
DEALLOCATE _cursor
  RETURN   


end 

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Phys_Properties_Delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
create  procedure  [dbo].[prc_ITF_Igredients_Phys_Properties_Delete]
(@name varchar(50))

as
DELETE FROM Ingredient_phys_Properties
FROM         Ingredient_phys_Properties INNER JOIN
                      Ingredient_Code ON Ingredient_phys_Properties.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @name)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_UPDATE]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE   Proc [dbo].[Recipe_Tempory_UPDATE] 
( 
@rowId NVARCHAR(50),
@phr NVARCHAR(50)=NULL,
@weight NVARCHAR(50)=NULL,
@ContainerNb NVARCHAR(50)=NULL,
@phase NVARCHAR(50)=NULL,
@matindex NVARCHAR(50)=NULL
) 
as

UPDATE    Recipe_Tempory
SET              PHR = @phr, weight = @weight, MatIndex = @matindex, ContainerNB = @ContainerNb, Phase = @phase
WHERE     (Id = @rowId)


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[A]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[A](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_givenPHRS_R]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE    Proc [dbo].[Recipe_Tempory_Recalc_givenPHRS_R]
( 
@recipe NVARCHAR(50),
@release NVARCHAR(50)=''0''
,@user NVARCHAR(50)=''[dbo].[Recipe_Tempory]''
) 
as
 
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]
--
begin try
begin

if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
	INSERT INTO #WWW
                (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS_R](@recipe, @release,@user)				
		delete from [AA]
		INSERT INTO [AA]
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
		FROM         #WWW
	end 
if @user=''AB'' 
	begin
	INSERT INTO #WWW
                (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
			SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
			FROM         dbo.[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS_R](@recipe, @release,@user)
		delete from [AB]
		INSERT INTO [AB]
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
		FROM         #WWW
	end 
if @user=''AC'' 
	begin
		INSERT INTO #WWW
						(Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
							  volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
							  volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS_R](@recipe, @release,@user)		
		
		delete from [AC]
		INSERT INTO [AC]
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID 
		FROM         #WWW
	end 
end
else
begin
INSERT INTO #WWW
                (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM         dbo.[Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS_R](@recipe, @release,@user)

delete from Recipe_Tempory
INSERT INTO Recipe_Tempory
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM         #WWW
 
	end
return  0
end
end try

begin catch

select ''error 2''
return -2
end catch








' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_FillFactor_R]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
create   Proc [dbo].[Recipe_Tempory_Recalc_FillFactor_R]
( 
@recipe VARCHAR(50),
@release VARCHAR(50)=''0'',
@NewFillFactor VARCHAR(50)=''0'',
@MixerId VARCHAR(50)=''AB''
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]''

) 
as
if @NewFillFactor =''0''
begin
	return -1 -- select the New Total Weight Parameter 
end
else
	begin try

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]
	
if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewFillFactor_2_R(@recipe, @release,@NewFillFactor,@MixerId,@user) 

	delete from [AA]

	INSERT INTO [AA]
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
	end
if @user=''AB'' 
	begin
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewFillFactor_2_R(@recipe, @release,@NewFillFactor,@MixerId,@user) 

	delete from [AB]

	INSERT INTO [AB]
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
	end
if @user=''AC'' 
	begin
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewFillFactor_2_R(@recipe, @release,@NewFillFactor,@MixerId,@user) 

	delete from [AC]

	INSERT INTO [AC]
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
	end

else
begin
INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewFillFactor_2_R(@recipe, @release,@NewFillFactor,@MixerId,@user) 

	delete from Recipe_Tempory

	INSERT INTO Recipe_Tempory
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
	end
return  0
end
end try

begin catch

select ''error 2''
return -2
end catch








' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Warehause_Delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'









-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_Warehause_Delete]
(	@Ingredient_name varchar (50)


)


AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
DELETE FROM Ingredient_Warehouse
FROM         Ingredient_Warehouse INNER JOIN
                      Ingredient_Code ON Ingredient_Warehouse.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @Ingredient_name)
--
--go
-- [prc_ITF_Igredients_Warehause_Delete] (''17543'')





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Import_to_Recipe_RECIPENEW_old_Example]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- reads each row from MCCPRECIPE and writes REcipe information as separate records

CREATE PROCEDURE [dbo].[Import_to_Recipe_RECIPENEW_old_Example]
--
AS
SET NOCOUNT ON;
--Declare 
DECLARE @code nvarchar(10),@release nvarchar(2), @mixID nvarchar(3);
DECLARE 
@MATERIAL1  nvarchar(50),
@MATERIAL2  nvarchar(50),
@MATERIAL3  nvarchar(50),
@MATERIAL4  nvarchar(50),
@MATERIAL5  nvarchar(50),
@MATERIAL6  nvarchar(50),
@MATERIAL7  nvarchar(50),
@MATERIAL8  nvarchar(50),
@MATERIAL9  nvarchar(50),
@MATERIAL10  nvarchar(50),
@MATERIAL11  nvarchar(50),
@MATERIAL12  nvarchar(50),
@MATERIAL13  nvarchar(50),
@MATERIAL14  nvarchar(50),
@MATERIAL15  nvarchar(50),
@MATERIAL16  nvarchar(50),
@MATERIAL17  nvarchar(50),
@MATERIAL18  nvarchar(50),
@MATERIAL19  nvarchar(50),
@MATERIAL20  nvarchar(50)


DECLARE @count int 

DECLARE _cursor CURSOR FOR 
SELECT     CODE, releaSE, 
Material1, Material2,Material3,Material4,Material5,Material6,Material7,Material8,Material9,Material10,
Material11, Material12,Material13,Material14,Material15,Material16,Material17,Material18,Material19,Material20
FROM         CPM.dbo.MCCPRECIPE

OPEN _cursor
set @count=1
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#VVV'')AND type in (N''U''))
DROP TABLE #VVV
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#VVV'') AND type in (N''U''))
CREATE TABLE #VVV(
	[Code] [varchar](50) NULL,
[Release] [varchar](50) NULL,
	[IngredName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Weight] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
	) 

DELETE FROM #VVV

FETCH NEXT FROM _cursor 
INTO  @code, @release,
@Material1, @Material2,@Material3,@Material4,@Material5,@Material6,@Material7,@Material8,@Material9,@Material10,
@Material11, @Material12,@Material13,@Material14,@Material15,@Material16,@Material17,@Material18,@Material19,@Material20

set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END

WHILE @@FETCH_STATUS <> -1 AND @count<=200
BEGIN
set @count=@count+1

if @material1 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material1) 
if @material2 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material2) 
if @material3 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material3) 
if @material4 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material4) 
if @material5 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material5) 
if @material6 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material6) 
if @material7 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material7) 
if @material8 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material8) 
if @material9 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material9) 
if @material10 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material10) 
if @material11 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material11) 
if @material12 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material12) 
if @material13 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material13) 
if @material14 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material14) 
if @material15 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material15) 
if @material16 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material16) 
if @material17 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material17) 
if @material18 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material18) 
if @material19 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material19) 
if @material20 IS NOT NULL 
insert   into #VVV  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material20) 

FETCH NEXT FROM _cursor 
INTO  @code, @release,
@Material1, @Material2,@Material3,@Material4,@Material5,@Material6,@Material7,@Material8,@Material9,@Material10,
@Material11, @Material12,@Material13,@Material14,@Material15,@Material16,@Material17,@Material18,@Material19,@Material20
set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END
end
select * from #VVV
CLOSE _cursor;
DEALLOCATE _cursor;
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_newTotalVolume_R]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


create   Proc [dbo].[Recipe_Tempory_Recalc_newTotalVolume_R]
( 
@recipe VARCHAR(50),
@release VARCHAR(50)=''0'',
@NewTotalVolume VARCHAR(50)=''0''
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]''
) 
as
if @NewTotalVolume=''0''
begin
	return -1 -- select the New Total Weight Parameter 
end
else

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]

begin try
begin
if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 

begin --0
select ''AA''
	if @user=''AA'' 
	begin --1
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewVolume_2_R(@recipe, @release,@NewTotalVolume,@user) 

	delete from Recipe_Tempory

	INSERT INTO Recipe_Tempory
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
	end --1
if @user=''AB'' 
	begin --2
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewVolume_2_R(@recipe, @release,@NewTotalVolume,@user) 

	delete from [AB]

	INSERT INTO [AB]
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
	end --2

if @user=''AC'' 
	begin --3
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewVolume_2_R(@recipe, @release,@NewTotalVolume,@user) 

	delete from [AC]

	INSERT INTO [AC]
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
	end --3
end --0
else 
begin --00
		INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewVolume_2(@recipe, @release,@NewTotalVolume) 

	delete from Recipe_Tempory

	INSERT INTO Recipe_Tempory
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         #WWW
	end --0
return  0
end
end try

begin catch

select ''error 2''
return -2
end catch







' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Sequence_Commands__10]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'create procedure [dbo].[Insert_Sequence_Commands__10]
as
delete from Recipe_Sequence_Commands
INSERT INTO Recipe_Sequence_Commands
                      (Command_Name, UpdatedOn, UpdatedBy)
SELECT     command, GETDATE() AS Expr2, ''SB'' AS Expr1
FROM         ssss
GROUP BY command
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Production_plan_22]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'create procedure [dbo].[Insert_Production_plan_22] 
as
delete from [Production_plan]
INSERT INTO [NCPD].[dbo].[Production_plan]
           ([PLANDATE]
           ,[PLANID]
           ,[ORDERNO]
           ,[RCODE]
           ,[MTYPE]
           ,[STATUS]
           ,[BATCHQTY]
           ,[PRODQTY]
           ,[FIRSTBATCH]
           ,[PRIORITY]
           ,[CANCELQTY]
           ,[BOOKEDQTY]
           ,[PRODDATE]
           ,[ORIGIN]
           ,[MODIFIED]
           ,[REMARK]
           ,[LASTUPDATE]
           ,[UPDATEDBY])
SELECT     PLANDATE, PLANID, ORDERNO, RCODE, MTYPE, STATUS, BATCHQTY, PRODQTY, FIRSTBATCH, PRIORITY, CANCELQTY, BOOKEDQTY, PRODDATE, ORIGIN, 
                      MODIFIED, REMARK, LASTUPDATE, UPDATEDBY
FROM         CPRECIPE...CPplan AS CPplan_1
ORDER BY PLANDATE DESC
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_newTotalWeight_R]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

create   Proc [dbo].[Recipe_Tempory_Recalc_newTotalWeight_R]
( 
@recipe VARCHAR(50),
@release VARCHAR(50)=''0'',
@NewTotalWeight VARCHAR(50)=''0''
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]''

) 
as
if @NewTotalWeight=''0''
	return -1 -- select the New Total Weight Parameter 

	--begin try
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]




if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin  --2
	if @user=''AA'' 
	begin --3
	INSERT INTO #WWW
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.Recipe_Full_RZPT_NewWeight_2(@recipe, @release,@NewTotalWeight) AS Recipe_Full_RZPT_NewWeight_2_1
		delete from [AA]
		INSERT INTO [AA]
				   (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         #WWW
	end --3

	if @user=''AB'' 
	begin --33
	INSERT INTO #WWW
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.Recipe_Full_RZPT_NewWeight_2(@recipe, @release,@NewTotalWeight) AS Recipe_Full_RZPT_NewWeight_2_1
		delete from [AB]
		INSERT INTO [AB]
				   (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         #WWW
	end --33

if @user=''AC'' 
	begin --333
	INSERT INTO #WWW
						  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.Recipe_Full_RZPT_NewWeight_2(@recipe, @release,@NewTotalWeight) AS Recipe_Full_RZPT_NewWeight_2_1
		delete from [AC]
		INSERT INTO [AC]
				   (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         #WWW
	end --333
end--2

else 
begin	--4
	INSERT INTO #WWW
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB,  Phase,SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber,PHR,  PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         dbo.Recipe_Full_RZPT_NewWeight_2(@recipe, @release,@NewTotalWeight) AS Recipe_Full_RZPT_NewWeight_2_1
	delete from Recipe_Tempory
	INSERT INTO Recipe_Tempory
               (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase,SiloId,  BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM         #WWW
end		--4

	




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into_Recipe_Test_Procedures_12]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE Proc [dbo].[Insert_Into_Recipe_Test_Procedures_12]
AS
begin
delete from Recipe_Test_Procedures
INSERT INTO Recipe_Test_Procedures
                      (Test_Code, Descript, NORM, Status, Class, GRUPPE, Report, NOTE, UpdatedOn, UpdatedBy)
SELECT     CODE, DESCRIPT, NORM, STATUS, CLASS, [GROUP], REPORT, NOTE, getdate(), ''SB''
FROM         CPRECIPE...CPTPROC
---original FROM         CPM.dbo.CPTPROC
end
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[CASNO_ID]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[CASNO_ID](
	[CASNO_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[CAS_Number] [varchar](50) NULL,
	[EC_Number] [varchar](50) NULL,
	[Chem_Name] [varchar](max) NULL,
	[Chem_NameAlt] [varchar](max) NULL,
	[DateReg] [varchar](50) NULL,
	[Info_01] [varchar](max) NULL,
	[Info_02] [varchar](max) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](20) NULL,
 CONSTRAINT [PK_CASNO_ID] PRIMARY KEY CLUSTERED 
(
	[CASNO_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_CASNO_ID] UNIQUE NONCLUSTERED 
(
	[CAS_Number] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into_Recipe_csv_21]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
--PURPOSE--------------------------
-- import recipe.csv from source database named ''CPMD''

CREATE  Proc [dbo].[Insert_Into_Recipe_csv_21]
--PURPOSE--------------------------
-- import recipe.csv from source database named ''CPMD''

as
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[Recipe.csv]'') AND type in (N''U''))
DROP TABLE [dbo].[Recipe.csv]--INSERT INTO [Recipe.csv]
--(Field1, Field2, Field3, Field4, Field5, Field6, Field7, Field8, Field9, Field10, Field11, Field12, Field13, Field14, Field15, Field16, Field17, Field18, Field19, 
 --                     Field20)
--SELECT    Field1, Field2, Field3, Field4, Field5, Field6, Field7, Field8, Field9, Field10, Field11, Field12, Field13, Field14, Field15, Field16, Field17, Field18, Field19, 
  --                    Field20
--FROM         CPMD.dbo.[Recipe.csv]

SELECT     id, Field1, Field2, Field3, Field4, Field5, Field6, Field7, Field8, Field9, Field10, Field11, Field12, Field13, Field14, Field15, Field16, Field17, Field18, 
                      Field19, Field20
INTO            [Recipe.csv]
FROM         CPM.dbo.[Recipe.csv]
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Production_Plan_Csv]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Production_Plan_Csv](
	[Prod#Order] [int] NULL,
	[Artikel] [varchar](255) NULL,
	[Omschrijving] [varchar](255) NULL,
	[Te Maken] [int] NULL,
	[Multiple] [int] NULL,
	[Batches] [int] NULL,
	[Startdatum] [datetime] NULL,
	[Sorteren] [varchar](255) NULL,
	[Volgorde] [int] NULL,
	[Vraagorder] [varchar](255) NULL,
	[Klantcode] [varchar](255) NULL,
	[KlantNaam] [varchar](255) NULL,
	[Opmerkingen] [varchar](255) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into_Ingredient_Vulco_Code_15]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE Proc [dbo].[Insert_Into_Ingredient_Vulco_Code_15]
as
delete from Ingredient_Vulco_Code
INSERT INTO Ingredient_Vulco_Code
                      (AgeingCode, Type, Descr, Method, Temp, Time, Article, Status, Note, UpdatedOn, UpdatedBy)
SELECT     VULCCODE, TYPE, DESCR, METHOD, TEMP, TIME, ARTICLE, STATUS, NOTE, LASTUPDATE, UPDATEDBY
FROM         CPRECIPE...CPVULMET
--FROM         cpm.dbo.CPVULMET
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Warehause_EDIT_1]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'









-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_Warehause_EDIT_1]
(	@IngredientCode_ID bigint,
 @SiloId varchar (50),
@BalanceID varchar (50),
@Location varchar (50),
@FreeInfo varchar (50),
@UpdatedOn varchar (50),
@UpdatedBy varchar (50)
)
--go
--execute   [prc_ITF_Igredients_Warehause_EDIT_1] (@IngredientCode_ID,@SiloId,@BalanceID,@Location,@FreeInfo,@UpdatedOn,@UpdatedBy)

AS
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
UPDATE    Ingredient_Warehouse
SET              SiloId = @SiloId, BalanceID = @BalanceID, Location = @Location, 
                      FreeInfo = @FreeInfo, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy
FROM         Ingredient_Warehouse INNER JOIN
                      Ingredient_Code ON Ingredient_Warehouse.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Warehouse.IngredientCode_ID = @IngredientCode_ID)
--
-- go
-- execute   [prc_ITF_Igredients_Warehause_EDIT_1] (@IngredientCode_ID,@SiloId,@BalanceID,@Location,@FreeInfo,@UpdatedOn,@UpdatedBy)





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_UPDATE_R]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'




CREATE   Proc [dbo].[Recipe_Tempory_UPDATE_R] 
( 
@rowId NVARCHAR(50),
@phr NVARCHAR(50)=NULL,
@weight NVARCHAR(50)=NULL,
@ContainerNb NVARCHAR(50)=NULL,
@phase NVARCHAR(50)=NULL,
@matindex NVARCHAR(50)=NULL,
@user  NVARCHAR(50)=''Recipe_Tempory''
) 
as

if @user=''AA'' OR @user=''AB'' OR @user=''AC''
begin 
	if @user=''AA''
	begin--''AA''
		UPDATE    [AA]
		SET              PHR = @phr, weight = @weight, MatIndex = @matindex, ContainerNB = @ContainerNb, Phase = @phase
		WHERE     (Id = @rowId)
	end --''AA''
	if @user=''AB''
	begin
		UPDATE    [AB]
		SET              PHR = @phr, weight = @weight, MatIndex = @matindex, ContainerNB = @ContainerNb, Phase = @phase
		WHERE     (Id = @rowId)
	end --''AA''
	if @user=''AC''
	begin
		UPDATE    [AC]
		SET              PHR = @phr, weight = @weight, MatIndex = @matindex, ContainerNB = @ContainerNb, Phase = @phase
		WHERE     (Id = @rowId)
	end --''AA''

end --if @user=''AA'' OR @user=''AB'' OR @user=''AC''
else 
begin --if @user=''AA'' OR @user=''AB'' OR @user=''AC''
		UPDATE    [Recipe_Tempory]
		SET              PHR = @phr, weight = @weight, MatIndex = @matindex, ContainerNB = @ContainerNb, Phase = @phase
		WHERE     (Id = @rowId)
end --if @user=''AA'' OR @user=''AB'' OR @user=''AC''


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into_Ingredient_Aeging_Code_16]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE Proc [dbo].[Insert_Into_Ingredient_Aeging_Code_16]
as
delete from Ingredient_Aeging_Code
INSERT INTO Ingredient_Aeging_Code
                      (AgeingCode, Type, Descr, Method, Temp, Time, TimeUnit, Status, Note, UpdatedOn, UpdatedBy)
SELECT     AGEINGCODE, TYPE, DESCR, METHOD, TEMP, TIME, TIMEUNIT, STATUS, NOTE, LASTUPDATE, UPDATEDBY
FROM         cpRECIPE...CPAGEMET
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Warehause_Insert_1]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'










-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_Warehause_Insert_1]
(	@IngredientCode_ID bigint,
 @SiloId varchar (50),
@BalanceID varchar (50),
@Location varchar (50),
@FreeInfo varchar (50),
@UpdatedOn varchar (50),
@UpdatedBy varchar (50)
)
--go
--execute   [prc_ITF_Igredients_Warehause_EDIT_1] (@IngredientCode_ID,@SiloId,@BalanceID,@Location,@FreeInfo,@UpdatedOn,@UpdatedBy)

AS
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
INSERT INTO Ingredient_Warehouse
                      (SiloId, BalanceID, Location, FreeInfo, UpdatedOn, UpdatedBy)
VALUES     (@SiloId,@BalanceID,@Location,@FreeInfo,@UpdatedOn,@UpdatedBy)
--
-- go
-- execute   [prc_ITF_Igredients_Warehause_Insert_1] (@IngredientCode_ID,@SiloId,@BalanceID,@Location,@FreeInfo,@UpdatedOn,@UpdatedBy)






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SB]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[SB](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_CASNO_ID_19]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE  proc [dbo].[Insert_CASNO_ID_19]
as
delete from CASNO_ID
INSERT INTO CASNO_ID
                      (EC_Number, CAS_Number, Chem_Name, Chem_NameAlt, DateReg, UpdatedBy, UpdatedOn)
SELECT     EC_Number, CAS_Number, ChemName, ChemNameAlt, Date_Reg, ''SB'' AS Expr1, GETDATE() AS Expr2
FROM         cpm.dbo.MC_CasNo

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[SB1]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[SB1](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Delete_R]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
create   Proc [dbo].[Recipe_Tempory_Delete_R] 
( 
@Recipe_Recipe_ID bigint,
@user  NVARCHAR(50)=''Recipe_Tempory''
) 
as

if @user=''AA'' OR @user=''AB'' OR @user=''AC''
begin /*@user=''AA'' OR @user=''AB'' OR @user=''AC''*/
	if @user=''AA''
	begin--''AA''
	DELETE FROM [AA] WHERE     (Recipe_Recipe_ID = @Recipe_Recipe_ID)
	end--''AA''

	if @user=''AB''
	begin--''AA''
	DELETE FROM [AB] WHERE     (Recipe_Recipe_ID = @Recipe_Recipe_ID)
	end--''AA''

	if @user=''AC''
	begin--''AA''
	DELETE FROM [AC] WHERE     (Recipe_Recipe_ID = @Recipe_Recipe_ID)
	end--''AA''
end /*@user=''AA'' OR @user=''AB'' OR @user=''AC''*/
else
begin
	DELETE FROM Recipe_Tempory WHERE     (Recipe_Recipe_ID = @Recipe_Recipe_ID)

END


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Import_to_MixerInfoBasic_00__2]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- =============================================
CREATE PROCEDURE [dbo].[Import_to_MixerInfoBasic_00__2]

AS
DELETE FROM Mixer_InfoBasic

INSERT INTO Mixer_InfoBasic

		SELECT 
[CODE]
      ,[NAME]
      ,[VOLUME]
      ,[COST] as costs_perKilo
      ,[WASTE]
      ,[DUST]
      ,[STARTTIME]
      ,[CYCLEOVH]
      ,[DECIMALS]
      ,[NOTE1]as [Note_Main]
           ,GETDATE ( )  as UpdatedOn
,''SB'' as UpdatedBy
  FROM [cprecipe]...[CPMIXSET]
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AB]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AB](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[MCRecipeUsers]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[MCRecipeUsers](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[userName] [varchar](50) NOT NULL,
	[pass] [varchar](50) NOT NULL,
	[role] [varchar](50) NOT NULL,
	[dateCreated] [datetime] NOT NULL DEFAULT (getdate()),
	[dateChanged] [datetime] NOT NULL DEFAULT (getdate()),
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[AB1]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[AB1](
	[Id] [varchar](50) NULL,
	[RecipeName] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[material] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[GRP] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[density_Recalc] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[PHR_recalc] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[weight_Recalc] [varchar](50) NULL,
	[volume_Recalc] [varchar](50) NULL,
	[Volume] [varchar](50) NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[PriceKG] [varchar](50) NULL,
	[PriceData] [varchar](50) NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_1_O_W_R]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_1_O_W_R] ( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]'') 
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[Weighing S.] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	 [Loading S.] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
Recipe_Recipe_ID bigint,
RecipeID bigint
)
begin 
insert into @output SELECT     TOP (100) Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB AS [Weighing S.], Phase AS [Loading S.], SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, 
                      RecipeID  FROM         dbo.Recipe_Full_RZPT_1_O_R(@rECIPEnAME, @Rrelease, @user)  ORDER BY GRP
return
end



' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Production_Plan_Csv_Temp]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Production_Plan_Csv_Temp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[Prod#Order] [int] NULL,
	[Artikel] [varchar](255) NULL,
	[Omschrijving] [varchar](255) NULL,
	[Te Maken] [int] NULL,
	[Multiple] [int] NULL,
	[Batches] [int] NULL,
	[Startdatum] [datetime] NULL,
	[Sorteren] [varchar](255) NULL,
	[Volgorde] [int] NULL,
	[Vraagorder] [varchar](255) NULL,
	[Klantcode] [varchar](255) NULL,
	[KlantNaam] [varchar](255) NULL,
	[Opmerkingen] [varchar](255) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Import_to_Recipe_Group__1]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE PROCEDURE [dbo].[Import_to_Recipe_Group__1]
AS

DECLARE @prm int
DELETE FROM NCPD.[dbo].Recipe_Group

INSERT INTO NCPD.[dbo].Recipe_Group
SELECT 
		 [Main_Group]
      ,[main_Info]
      ,[Detailed_Group]
      ,[Detailed_Info]
      ,[Note_Main]
      ,[UpdatedOn]
    ,''SB'' as UpdatedBy
  FROM [CPM].[dbo].[Recipe_Group]
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_DisplVendor_Edit]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'










-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
create procedure [dbo].[prc_ITF_Igredients_DisplVendor_Edit]
(	@IngredientCode_ID bigint,
@VENDOR_ID bigint,
@VendorNo varchar (50),
 @VendorName varchar (50),
 @Adress varchar (50),
@ZipCode varchar (50),
@City varchar (50),
@Country varchar (50),
 @Phone varchar (50),
 @Fax varchar (50),
 @Email varchar (50),
 @Website varchar (50),
 @FreeInfo varchar (50),
 @Status varchar (50),
@UpdatedOn smalldatetime = getdate,
@UpdatedBy varchar(50)

)


AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
UPDATE    VENDOR
SET              VendorNo = @VendorNo, VendorName = @VendorName, Adress = @Adress, ZipCode = @ZipCode, City = @City, Country = @Country, 
                      Phone = @Phone, Fax = @Fax, Email = @Email, Website = @Website, FreeInfo = @FreeInfo, Status = @Status, UpdatedOn = @UpdatedOn, 
                      UpdatedBy = @UpdatedBy
WHERE     (VENDOR_ID = @VENDOR_ID)
--
--go
-- [prc_ITF_Igredients_DisplVendor_Edit](@IngredientCode_ID bigint,
--@VENDOR_ID ,@VendorNo , @VendorName , @Adress ,@ZipCode ,@City ,@Country ,
-- @Phone , @Fax , @Email , @Website , @FreeInfo , @Status ,@UpdatedOn ,@UpdatedBy )






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Update_REcipe_prop_Main_DEL]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[Update_REcipe_prop_Main_DEL]
	-- Add the parameters for the stored procedure here
	(
@REcipe_ID NVARCHAR(50),
 @Detailed_Group NVARCHAR(50), 
 @Mixer_Code NVARCHAR(50),  
@Code NVARCHAR(50),  
@release NVARCHAR(50),
@Descr NVARCHAR(50),  
@Status NVARCHAR(50), 
@Class  NVARCHAR(50),  
@Loadfactor NVARCHAR(50),  
@MixTime NVARCHAR(50),  
@UpdatedOn smalldatetime,  
@UpdatedBy NVARCHAR(10)
)
AS

BEGIN
begin try 
UPDATE    Recipe_Prop_Main
SET              Detailed_Group = @Detailed_Group, Mixer_Code = @Mixer_Code, Code = @Code,Release = @release,  Descr = @Descr, Status = @Status, 
                  Class = @Class, Loadfactor = @Loadfactor, MixTime = @MixTime, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy
WHERE     (Recipe_ID = @REcipe_ID)
end try
begin catch
	return -1
end catch 
END
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Test_fnInsert_RECIPENEW1]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE  procedure [dbo].[Test_fnInsert_RECIPENEW1]
as 
BEGIN try
--select * from  fnInsert_RECIPENEW()
select *  from fnInsert_RECIPENEW1()

END TRY
BEGIN CATCH

END CATCH
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ZZZ]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ZZZ](
	[Recipe_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Recipe_Origin] [varchar](4) NULL,
	[Recipe_Additional] [varchar](50) NULL,
	[Recipe Version] [varchar](50) NULL,
	[Recipe Stage] [varchar](6) NULL,
	[Detailed_Group] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[Class] [varchar](50) NULL,
	[Mixer_Code] [varchar](50) NULL,
	[Descr] [varchar](max) NULL,
	[Loadfactor] [varchar](50) NULL,
	[MixTime] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_DisplVendor_INSERT]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'









-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_DisplVendor_INSERT]
(	@IngredientCode_ID bigint,
@VENDOR_ID bigint,
@UpdatedBy varchar(50),
@UpdatedOn datetime = getdate

)


AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
INSERT INTO _INTRF_IngredientCode_ID__Vendor_ID
                      (IngredientCode_ID, VENDOR_ID, UpdatedOn, UpdatedBy)
VALUES     (@IngredientCode_ID,@VENDOR_ID,@UpdatedOn,@UpdatedBy)
--
--go
-- [prc_ITF_Igredients_DisplVendor_INSERT] (@IngredientCode_ID, @VENDOR_ID,@UpdatedBy,@UpdatedOn)





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingredient_Code]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingredient_Code](
	[IngredientCode_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Ingredient_Test_Procedures_ID] [bigint] NULL,
	[Name] [varchar](50) NULL,
	[Descr] [varchar](50) NULL,
	[Info_01] [varchar](50) NULL,
	[Info_02] [varchar](50) NULL,
	[Form] [varchar](50) NULL,
	[Cas_Number] [varchar](50) NULL,
	[Group] [varchar](50) NULL,
	[ActualPreisePerKg] [decimal](10, 2) NULL,
	[Status] [varchar](50) NULL,
	[Class] [varchar](50) NULL,
	[ChemName] [nvarchar](100) NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[CreatedBy] [nvarchar](50) NULL,
	[CreatedOn] [smalldatetime] NULL,
 CONSTRAINT [PK_Ingredient_Code] PRIMARY KEY CLUSTERED 
(
	[IngredientCode_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_Ingredient_Code] UNIQUE NONCLUSTERED 
(
	[Name] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Limits]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Limits](
	[ID] [bigint] NOT NULL,
	[Recipe_ID] [bigint] NULL,
	[ProcID] [bigint] NULL,
	[Ramark1] [varchar](50) NULL,
	[Remark2] [varchar](50) NULL,
	[Aautostart] [varchar](50) NULL,
	[Version] [varchar](50) NULL,
	[Vulccode] [varchar](50) NULL,
	[Ageingcode] [varchar](50) NULL,
	[Note] [varchar](50) NULL,
	[StartBatch] [varchar](50) NULL,
	[BatchStep] [varchar](50) NULL,
	[Hide] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Limits] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ProcTest]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ProcTest](
	[ID] [bigint] NOT NULL,
	[Proc_ID] [bigint] NULL,
	[Tag_ID] [bigint] NULL,
	[Line] [varchar](50) NULL,
	[ProcID] [varchar](50) NULL,
	[TagID] [varchar](50) NULL,
	[Report] [varchar](50) NULL,
	[Min] [varchar](50) NULL,
	[Max] [varchar](50) NULL,
	[Precision] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_ProcTest] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Result]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Result](
	[ID] [bigint] NOT NULL,
	[0rder_ID] [bigint] NULL,
	[Limits_ID] [bigint] NULL,
	[Machine_ID] [bigint] NULL,
	[Quality] [varchar](50) NULL,
	[BatchNo] [varchar](50) NULL,
	[TestNo] [varchar](50) NULL,
	[TestDate] [smalldatetime] NULL,
	[Laborer] [varchar](50) NULL,
	[ValidateBy] [varchar](50) NULL,
	[Status] [varchar](50) NULL,
	[Remark1] [varchar](50) NULL,
	[Remark2] [varchar](50) NULL,
	[CurveID] [bigint] NULL,
	[Note] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Result] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_INTRF_IngredientCode_ID__Vendor_ID](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NOT NULL,
	[VENDOR_ID] [bigint] NOT NULL,
	[Tradename_Main_ID] [bigint] NOT NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[VM] [nchar](1) NULL,
 CONSTRAINT [PK__INTRF_IngredientCode_ID__Vendor_ID] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Vendor_Contact]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Vendor_Contact](
	[Vendor_Contact_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[VENDOR_ID] [bigint] NOT NULL,
	[ContactName] [varchar](50) NULL,
	[position] [varchar](50) NULL,
	[Phone] [varchar](50) NULL,
	[Email] [varchar](50) NULL,
	[DateExport] [smalldatetime] NULL,
	[Dateprocessed] [smalldatetime] NULL,
	[Status] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Vendor_Contact] PRIMARY KEY CLUSTERED 
(
	[Vendor_Contact_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID](
	[ID] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NOT NULL,
	[Tradename_Main_ID] [bigint] NOT NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK__INTRF_IngredientCode_ID__Tradename_Main_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[ProfileTest]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[ProfileTest](
	[ID] [bigint] NOT NULL,
	[Profile_ID] [bigint] NULL,
	[TestCode] [varchar](50) NULL,
	[StartBatch] [varchar](50) NULL,
	[BatchStep] [varchar](50) NULL,
	[Remark] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_ProfileTest] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Prop_Main]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Prop_Main](
	[Recipe_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Detailed_Group] [varchar](50) NULL,
	[Mixer_Code] [varchar](50) NULL,
	[Code] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[Descr] [varchar](max) NULL,
	[Status] [varchar](50) NULL,
	[Class] [varchar](50) NULL,
	[Loadfactor] [varchar](50) NULL,
	[MixTime] [varchar](50) NULL,
	[PriceKG] [nchar](50) NULL,
	[PriceL] [nchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
	[CreatedOn] [smalldatetime] NULL,
	[CreatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Recipe_Prop_Main_1] PRIMARY KEY CLUSTERED 
(
	[Recipe_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY],
 CONSTRAINT [IX_Recipe_Prop_Main] UNIQUE NONCLUSTERED 
(
	[Code] ASC,
	[Release] ASC,
	[Mixer_Code] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = ON, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingredient_phys_Properties]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingredient_phys_Properties](
	[Ingredient_phys_Properties_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NOT NULL,
	[Form] [varchar](50) NULL,
	[percRubber] [varchar](50) NULL,
	[PercRubtOl] [varchar](50) NULL,
	[PercActMat] [varchar](50) NULL,
	[ViscTemp] [varchar](50) NULL,
	[ViscTime] [varchar](50) NULL,
	[ViscML] [varchar](50) NULL,
	[ViscMLTOl] [varchar](50) NULL,
	[density] [varchar](50) NULL,
	[DensityTOl] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Ingredient_phys_Properties] PRIMARY KEY CLUSTERED 
(
	[Ingredient_phys_Properties_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingred_Preise]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingred_Preise](
	[Ingred_Preise_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NULL,
	[PreisePerKg] [varchar](50) NULL,
	[PreisePerLiter] [varchar](50) NULL,
	[Prisedate] [smalldatetime] NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Ingred_Preise] PRIMARY KEY CLUSTERED 
(
	[Ingred_Preise_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingred_Free_Info]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingred_Free_Info](
	[Ingred_Free_Info_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NOT NULL,
	[NoteName] [varchar](50) NULL,
	[NoteValue] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Ingred_Free_Info] PRIMARY KEY CLUSTERED 
(
	[Ingred_Free_Info_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Recipe]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Recipe](
	[Recipe_Recipe_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Recipe_ID] [bigint] NULL,
	[IngredName] [varchar](50) NULL,
	[PHR] [varchar](50) NULL,
	[Weight] [varchar](50) NULL,
	[SiloID] [varchar](50) NULL,
	[Phase] [varchar](50) NULL,
	[MatIndex] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Recipe_Recipe] PRIMARY KEY CLUSTERED 
(
	[Recipe_Recipe_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID](
	[ID] [nchar](10) NOT NULL,
	[IngredientCode_ID] [bigint] NULL,
	[Ingredient_Aeging_Code_ID] [bigint] NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK__INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingredient_Warehouse]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingredient_Warehouse](
	[Ingredient_Warehouse_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NULL,
	[LotNO] [varchar](50) NULL,
	[BOxNo] [varchar](50) NULL,
	[SchelfLife] [varchar](50) NULL,
	[Location] [varchar](50) NULL,
	[Storetime] [varchar](50) NULL,
	[SttempMin] [varchar](50) NULL,
	[StTempMax] [varchar](50) NULL,
	[StoreDate] [smalldatetime] NULL,
	[STDATEEXT] [varchar](50) NULL,
	[ActStock] [varchar](50) NULL,
	[MinStock] [varchar](50) NULL,
	[LastUsed] [varchar](50) NULL,
	[UsageDate] [smalldatetime] NULL,
	[StupDate] [smalldatetime] NULL,
	[Form] [varchar](50) NULL,
	[Quantity] [varchar](max) NULL,
	[QuantUnit] [varchar](50) NULL,
	[ORDER] [varchar](50) NULL,
	[OrderUnit] [varchar](50) NULL,
	[SiloId] [varchar](50) NULL,
	[BalanceID] [varchar](50) NULL,
	[SWFINE] [varchar](50) NULL,
	[SWVeryFine] [varchar](50) NULL,
	[StopSignal] [varchar](50) NULL,
	[Tolerance] [varchar](50) NULL,
	[FreeInfo] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Ingredient_Warehouse] PRIMARY KEY CLUSTERED 
(
	[Ingredient_Warehouse_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingred_Comments]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingred_Comments](
	[Ingred_Info_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NULL,
	[Comments] [varchar](max) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Ingred_Info] PRIMARY KEY CLUSTERED 
(
	[Ingred_Info_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID](
	[ID] [nchar](10) NOT NULL,
	[IngredientCode_ID] [bigint] NULL,
	[Ingredient_Vulco_Code_ID] [bigint] NOT NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK__INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingred_Safety_Main]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingred_Safety_Main](
	[Tradename_Safety_Main_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[IngredientCode_ID] [bigint] NOT NULL,
	[Visual_Code] [varchar](50) NULL,
	[MSDS_Specs] [varchar](50) NULL,
	[REach_Reg_Nm] [varchar](50) NULL,
	[FDA_Approved] [varchar](50) NULL,
	[RaandS_PHPhrases] [varchar](50) NULL,
	[HandP_PHPhrases] [varchar](50) NULL,
	[Safety_Class_OLD] [varchar](50) NULL,
	[Safety_Class_New] [varchar](50) NULL,
	[Risk_Assesment] [varchar](50) NULL,
	[Free_Info] [varchar](50) NULL,
	[BoilTemp] [varchar](50) NULL,
	[Melt_Temp] [varchar](50) NULL,
	[Flash_Temp] [varchar](50) NULL,
	[Ignit_Temp] [varchar](50) NULL,
	[DecomTemp] [varchar](50) NULL,
	[SoLub] [varchar](50) NULL,
	[Hygro] [varchar](50) NULL,
	[MacPPM] [varchar](50) NULL,
	[MacMGM3] [varchar](50) NULL,
	[Noxic] [varchar](50) NULL,
	[Inflam] [varchar](50) NULL,
	[Explo] [varchar](50) NULL,
	[Copro] [varchar](50) NULL,
	[Oxyd] [varchar](50) NULL,
	[Carc] [varchar](50) NULL,
	[Vent] [varchar](50) NULL,
	[Fire] [varchar](50) NULL,
	[Dust] [varchar](50) NULL,
	[Face] [varchar](50) NULL,
	[Eye] [varchar](50) NULL,
	[Gloves] [varchar](50) NULL,
	[Spec] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Tradename_Safety_Main] PRIMARY KEY CLUSTERED 
(
	[Tradename_Safety_Main_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[LimitTest]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[LimitTest](
	[ID] [bigint] NOT NULL,
	[Limits_ID] [bigint] NULL,
	[Proctest_ID] [bigint] NULL,
	[LSL] [varchar](50) NULL,
	[USL] [varchar](50) NULL,
	[LWL] [varchar](50) NULL,
	[UWL] [varchar](50) NULL,
	[Target] [varchar](50) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_LimitTest] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Result_Test]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Result_Test](
	[Id] [bigint] NOT NULL,
	[Result_ID] [bigint] NULL,
	[Limit_Test_ID] [bigint] NULL,
	[Result] [nchar](10) NULL,
	[Check] [nchar](10) NULL,
	[LastUpdate] [smalldatetime] NULL,
	[UpdatedBy] [varchar](50) NULL,
 CONSTRAINT [PK_Result_Test] PRIMARY KEY CLUSTERED 
(
	[Id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Prop_Free_Text]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Prop_Free_Text](
	[Recipe_Prop_Free_Text_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Recipe_ID] [bigint] NULL,
	[NoteName] [varchar](50) NULL,
	[NoteValue] [varchar](max) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Recipe_Prop_Free_Text] PRIMARY KEY CLUSTERED 
(
	[Recipe_Prop_Free_Text_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Sequence_Main]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Sequence_Main](
	[Recipe_Sequence_Main_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Code] [varchar](50) NULL,
	[Release] [varchar](50) NULL,
	[Mixer_Code] [varchar](50) NULL,
	[Info] [varchar](max) NULL,
	[Status] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Recipe_Sequence_Main] PRIMARY KEY CLUSTERED 
(
	[Recipe_Sequence_Main_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Prop_Free_Info]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Recipe_Prop_Free_Info](
	[Recipe_Prop_Free_Info_ID] [bigint] IDENTITY(1,1) NOT NULL,
	[Recipe_ID] [bigint] NULL,
	[NoteName] [varchar](50) NULL,
	[NoteValue] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) NULL,
 CONSTRAINT [PK_Recipe_Prop_Free_Info] PRIMARY KEY CLUSTERED 
(
	[Recipe_Prop_Free_Info_ID] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Ingred_Safety_Components]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Ingred_Safety_Components](
	[Tradename_Safety_Components] [bigint] NOT NULL,
	[Tradename_Safety_Main_ID] [bigint] NOT NULL,
	[CAS_Number] [varchar](50) NULL,
	[Prcnt] [varchar](50) NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](20) NULL,
 CONSTRAINT [PK_Tradename_Safety_Components] PRIMARY KEY CLUSTERED 
(
	[Tradename_Safety_Components] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredient_NEW_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE  procedure  [dbo].[prc_ITF_Ingredient_NEW_Insert]
(  @IngredientCode_ID bigint ,
@CreatedOn smalldatetime,
@CreatedBy varchar(50))
/*
create the new INGREDIENT with the name "NEW" 
by reference from other
*/
as


-- delete first ''NEW'' if exists already
--begin try
DELETE FROM Ingredient_Code
WHERE     (name = ''NEW'')
--end try
--begin catch
--	return -1
--end catch 
--return -101

declare @Code as varchar(50);
DECLARE @OLD_IngredientCode_ID    bigint 

begin try
set @OLD_IngredientCode_ID = (SELECT     IngredientCode_ID
FROM         Ingredient_Code
WHERE     (IngredientCode_ID = @IngredientCode_ID))
print N''M1'' +  str(@OLD_IngredientCode_ID )
if @OLD_IngredientCode_ID IS NULL 
	begin
		print N''NO SUCH Ingredient''
		return -22
	end 

end try
begin catch
	return -2
end catch 
--return -202



begin try 
INSERT INTO Ingredient_Code
                      (Name, Ingredient_Test_Procedures_ID, Descr, Info_01, Info_02, Form, Cas_Number, [Group], ActualPreisePerKg, UpdatedOn, UpdatedBy, Status, 
                      Class, CreatedOn,CreatedBy )
SELECT     ''NEW'' AS NAME, Ingredient_Test_Procedures_ID, Descr, Info_01, Info_02, Form, Cas_Number, [Group], ActualPreisePerKg, UpdatedOn, UpdatedBy, 
                      Status, Class,@CreatedOn as CreatedOn, @CreatedBy as CreatedBy
FROM         Ingredient_Code AS Ingredient_Code_1
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 
begin catch 
	print ''return''
	return -3
end catch 
--return -303




--print @NEW_Recipr_ID
DECLARE @NEW_IngredientCode_ID    bigint 
set @NEW_IngredientCode_ID =@@identity
print ''@NEW_IngredientCode_ID:''+ cast (@NEW_IngredientCode_ID as varchar)
--return 999


begin try 

INSERT INTO Ingred_Free_Info
                      (IngredientCode_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy
FROM         Ingred_Free_Info
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 

begin catch 
	print ''return''
	return -4
end catch 
--return -404


begin try 
INSERT INTO Ingredient_phys_Properties
                      (IngredientCode_ID, UpdatedOn, UpdatedBy, Form, percRubber, PercRubtOl, PercActMat, ViscTemp, ViscTime, ViscML, ViscMLTOl, density, DensityTOl)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, UpdatedOn, UpdatedBy, Form, percRubber, PercRubtOl, PercActMat, ViscTemp, ViscTime, ViscML, 
                      ViscMLTOl, density, DensityTOl
FROM         Ingredient_phys_Properties
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 


begin catch 
	print ''return''
	return -5
end catch 
--return -505




begin try 
INSERT INTO Ingredient_Warehouse
                      (IngredientCode_ID, UpdatedOn, UpdatedBy, LotNO, BOxNo, Location, Storetime, SttempMin, StTempMax, StoreDate, STDATEEXT, ActStock, MinStock, 
                      LastUsed, UsageDate, StupDate, Form, Quantity, QuantUnit, [ORDER], OrderUnit, SiloId, BalanceID, SWFINE, SWVeryFine, StopSignal, Tolerance, 
                      FreeInfo)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, UpdatedOn, UpdatedBy, LotNO, BOxNo, Location, Storetime, SttempMin, StTempMax, StoreDate, STDATEEXT, ActStock, MinStock, 
                      LastUsed, UsageDate, StupDate, Form, Quantity, QuantUnit, [ORDER], OrderUnit, SiloId, BalanceID, SWFINE, SWVeryFine, StopSignal, Tolerance, 
                      FreeInfo
FROM         Ingredient_Warehouse AS Ingredient_Warehouse_1
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 

begin catch 
	print ''return''
	return -6
end catch 
--return -606


begin try 
INSERT INTO Ingred_Comments
                      (IngredientCode_ID, Comments, UpdatedOn, UpdatedBy)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, Comments, UpdatedOn, UpdatedBy
FROM         Ingred_Comments 
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 
begin catch 
	print ''return''
	return -7
end catch 
--return -707



begin try 
INSERT INTO Ingred_Preise
            (IngredientCode_ID, PreisePerKg, PreisePerLiter, Prisedate, UpdatedOn, UpdatedBy)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, PreisePerKg, PreisePerLiter, Prisedate, UpdatedOn, UpdatedBy
FROM         Ingred_Preise 
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 

begin catch 
	print ''return''
	return -8
end catch 
--return -808


begin try 
INSERT INTO Ingred_Safety_Main
                      (IngredientCode_ID, UpdatedOn, UpdatedBy, Visual_Code, MSDS_Specs, REach_Reg_Nm, FDA_Approved, RaandS_PHPhrases, HandP_PHPhrases, 
                      Safety_Class_OLD, Safety_Class_New, Risk_Assesment, Free_Info, BoilTemp, Melt_Temp, Flash_Temp, Ignit_Temp, DecomTemp, SoLub, Hygro, 
                      MacPPM, MacMGM3, Noxic, Inflam, Explo, Copro, Oxyd, Carc, Vent, Fire, Dust, Face, Eye, Gloves, Spec)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, UpdatedOn, UpdatedBy, Visual_Code, MSDS_Specs, REach_Reg_Nm, FDA_Approved, 
                      RaandS_PHPhrases, HandP_PHPhrases, Safety_Class_OLD, Safety_Class_New, Risk_Assesment, Free_Info, BoilTemp, Melt_Temp, Flash_Temp, 
                      Ignit_Temp, DecomTemp, SoLub, Hygro, MacPPM, MacMGM3, Noxic, Inflam, Explo, Copro, Oxyd, Carc, Vent, Fire, Dust, Face, Eye, Gloves, Spec
FROM         Ingred_Safety_Main AS Ingred_Safety_Main_1
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 
begin catch 
	print ''return''
	return -9
end catch 
--return -909


begin try 
INSERT INTO _INTRF_IngredientCode_ID__Vendor_ID
                      (IngredientCode_ID, VENDOR_ID, UpdatedOn, UpdatedBy)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, VENDOR_ID, UpdatedOn, UpdatedBy
FROM         _INTRF_IngredientCode_ID__Vendor_ID AS _INTRF_IngredientCode_ID__Vendor_ID_1
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 
begin catch 
	print ''return''
	return -10
end catch 
--return -10010


begin try 
INSERT INTO _INTRF_IngredientCode_ID__Tradename_Main_ID
                      (IngredientCode_ID, Tradename_Main_ID, UpdatedOn, UpdatedBy)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, Tradename_Main_ID, UpdatedOn, UpdatedBy
FROM         _INTRF_IngredientCode_ID__Tradename_Main_ID AS _INTRF_IngredientCode_ID__Tradename_Main_ID_1
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 
begin catch 
	print ''return''
	return -11
end catch
--return -10011

 
begin try 
INSERT INTO _INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID
                      (IngredientCode_ID, Ingredient_Aeging_Code_ID, UpdatedOn, UpdatedBy)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, Ingredient_Aeging_Code_ID, UpdatedOn, UpdatedBy
FROM         _INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID AS _INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID_1
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 
begin catch 
	print ''return''
	return -12
end catch 
--return -10012


begin try 
INSERT INTO _INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID
                      (IngredientCode_ID, UpdatedOn, UpdatedBy)
SELECT     @NEW_IngredientCode_ID AS IngredientCode_ID, UpdatedOn, UpdatedBy
FROM         _INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID AS _INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID_1
WHERE     (IngredientCode_ID = @IngredientCode_ID)
end try 
begin catch 
	print ''return''
	return -13
end catch 
--return -10013





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_ListOf_Masters_ALL]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'-- CHECKS RECURSIVELY ALL RECIPES WHICH HAVE MASTERBATCHES INSIDE 

CREATE  FUNCTION [dbo].[Recipe_ListOf_Masters_ALL] (@release NVARCHAR(1)) 
RETURNS @output TABLE
 (MaterialID varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
 Recipe varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
 MatIndex varchar(50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL	)

begin 
	Declare @material as varchar(50) 
	declare @rECIPEnAME  as VARCHAR(50)
	Declare @MatIndex  as varchar(50) 
declare @count as int
set @count = 0

DECLARE _cursor CURSOR FOR 

SELECT     Recipe_Prop_Main.Code, Recipe_Recipe.IngredName
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID
WHERE     (Recipe_Prop_Main.Release = @release) AND (Recipe_Recipe.MatIndex = ''R'')
open _cursor

FETCH NEXT FROM _cursor INTO  @rECIPEnAME , @material
set @count = @count+1

WHILE @@FETCH_STATUS <> -1 
 begin
	

		 --if (@count =1517)
		--begin
			INSERT INTO @output (MaterialID, Recipe, Matindex)  select @material ,@rECIPEnAME, @count 
			INSERT INTO @output (MaterialID, Recipe,Matindex)  select Material,Recip ,  info   from 	[Recipe_ListOf_Masters](@material, @release)
		--end

FETCH NEXT FROM _cursor INTO  @rECIPEnAME , @material
set @count = @count+1

end
close _cursor
deallocate _cursor
return
end
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Bill_Materials]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'--shows complete list of materials foor the final batch back to masterbatch 

CREATE  procedure [dbo].[Recipe_Bill_Materials] 
(@rECIPEnAME NVARCHAR(50) = ''00-0-0006'', 
 @Rrelease NVARCHAR(50)=''0'' 
)
as
declare _CRS  cursor FOR  select Material, Recip,Info from Recipe_ListOf_Masters(@rECIPEnAME,@Rrelease)
declare @RecipeId varchar(50)
declare @MatIndex varchar(50)
declare @Info varchar(50)

select * from [Recipe_Full_RZPT](@rECIPEnAME,@Rrelease)

open _CRS
FETCH NEXT FROM _CRS 
INTO @RecipeId,@MatIndex,@Info
WHILE @@FETCH_STATUS <> -1 
		begin
		select * from [Recipe_Full_RZPT](@RecipeId,@Rrelease)
		FETCH NEXT FROM _CRS 
		INTO @RecipeId,@MatIndex,@Info
		end
close _CRS
DEALLOCATE _CRS
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_main_Select]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE  procedure [dbo].[prc_ITF_Igredients_main_Select]

(	
	-- Add the parameters for the function here
@IngredCode_ID varchar (50)

)
AS
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     Ingredient_Code.Name, Ingredient_Code.Descr, Ingredient_Code.Cas_Number, Ingredient_Code.ChemName, Ingredient_Code.Class, 
                      Ingredient_Code.Status, Ingredient_Code.ActualPreisePerKg, Ingredient_Code.[Group], Ingred_Group.Grupp, Ingred_Group.Descr AS GroupName, 
                      Ingredient_Code.Form, Ingredient_phys_Properties.percRubber, Ingredient_phys_Properties.PercRubtOl, Ingredient_phys_Properties.PercActMat, 
                      Ingredient_phys_Properties.density, Ingredient_phys_Properties.DensityTOl, Ingredient_phys_Properties.ViscTemp, 
                      Ingredient_phys_Properties.ViscTime, Ingredient_phys_Properties.ViscML, Ingredient_phys_Properties.ViscMLTOl, 
                      Ingredient_Code.Info_01, Ingredient_Code.Info_02, Ingredient_Code.IngredientCode_ID, Ingred_Group.Id, 
                      Ingredient_phys_Properties.Ingredient_phys_Properties_ID, Ingredient_Code.CreatedBy, LEFT(CONVERT(VARCHAR(19),Ingredient_Code.CreatedOn,126),10) as CreatedOn, Ingredient_Code.UpdatedBy, 
                       LEFT(CONVERT(VARCHAR(19),Ingredient_Code.UpdatedOn,126),10) as UpdatedOn
FROM         Ingredient_Code INNER JOIN
                      Ingredient_phys_Properties ON Ingredient_Code.IngredientCode_ID = Ingredient_phys_Properties.IngredientCode_ID INNER JOIN
                      Ingred_Group ON Ingredient_Code.[Group] = Ingred_Group.Grupp
WHERE     (Ingredient_Code.Name = @IngredCode_ID)
)






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Igredients_Init_Load]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'






-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Igredients_Init_Load]
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@IngredCode_ID varchar (50)

-- select * from [fn_ITF_Recipes_Init] (NULL,''0'',Null,Null,Null,Null,Null,Null)ORDER BY code

)
RETURNS TABLE 
AS
RETURN 
(
SELECT     Ingredient_Code.Name, Ingredient_Code.Info_01 AS [Cross Reference], Ingredient_Code.Descr AS Description, 
                      Ingredient_Code.Cas_Number AS CASNO, CASNO_ID.Chem_Name AS [CHEMICAL NUMBER], Ingredient_Code.Class AS CLASS, 
                      Ingredient_Code.Status AS STATUS, Ingredient_Code.[Group] AS [GROUP], Ingred_Group.Grupp AS [GROUP NAME], 
                      Ingredient_Code.Form AS APPEARANCE, Ingredient_phys_Properties_1.percRubber AS [Percentage Rubber], 
                      Ingredient_phys_Properties_1.PercRubtOl AS [RUBBER TOLLERANCES], Ingredient_phys_Properties_1.PercActMat AS ACTIVITY, 
                      Ingredient_phys_Properties_1.density AS DENSITY, Ingredient_phys_Properties_1.DensityTOl AS [DENSITY TOLLERANCE], 
                      Ingredient_phys_Properties_1.ViscTemp AS [MOONEY TEMPEARTURE], Ingredient_phys_Properties_1.ViscTime AS [MOONEY TIME], 
                      Ingredient_phys_Properties_1.ViscML AS [MOONEY VISCOSITY], Ingredient_phys_Properties_1.ViscMLTOl AS [MOONEY TOLERANCES], 
                      Ingredient_Code.UpdatedOn, Ingredient_Code.UpdatedBy, Ingredient_Code.Info_02 AS [TECHNICAL DATASHEET]
FROM         Ingredient_Code INNER JOIN
                      Ingred_Group ON Ingredient_Code.[Group] = Ingred_Group.Grupp INNER JOIN
                      Ingredient_phys_Properties AS Ingredient_phys_Properties_1 ON 
                      Ingredient_Code.IngredientCode_ID = Ingredient_phys_Properties_1.IngredientCode_ID LEFT OUTER JOIN
                      CASNO_ID ON Ingredient_Code.Cas_Number = CASNO_ID.CAS_Number
WHERE     (Ingredient_Code.Name = @IngredCode_ID)
)
--
--go
--select * from [fn_ITF_Igredients_Init_Load] (''17543'')


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Igredients_BasSearch]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Igredients_BasSearch]
-- Seek engine for Java interfact. 
-- Gets list of Ingredients dependent on choosen input fields
-- zeros assume -''any''
--go
--select * from [fn_ITF_Igredients_BasSearch](''00013'',''R'',''I'',''N'',''Struktol WB42'',''pellets'',0,''Struktol WB42'',''Schill & Seilacher'',null)

(	
	-- Add the parameters for the function here
@name varchar (50),
@Class NVARCHAR(1),
@Group varchar (50),
@Status NVARCHAR(1),
		--''O/S''
@Description varchar (50),
@form varchar (50),
@PercRubber varchar (50),
@TradeName varchar (50),
@VENDOR varchar (50),
@Cas_Number varchar (50)
-- select * from [fn_ITF_Igredients_BasSearch](null,null,null,null,null,null,null,null,null,null,''Schill & Seilacher'')
-- use patterns
-- %  Any string of zero or more characters
--_ (underscore)  Any single character.
--[ ] Any single character within the specified range ([a-f]) or set ([abcdef]).
-- [^]  Any single character not within the specified range ([^a-f]) or set ([^abcdef]).
 
 
 
)
RETURNS TABLE 
AS
RETURN 
(
SELECT     Ingredient_Code.Name, Ingredient_Code.Class AS class, Ingred_Group.Grupp, Ingredient_Code.Status, Ingredient_Code.Descr, Ingredient_Code.Form, 
                      Ingredient_phys_Properties.percRubber, TRADENAME_MAIN.TradeName, VENDOR.VendorName, Ingredient_Code.Cas_Number, VENDOR.VendorNo, 
                      VENDOR.VENDOR_ID, _INTRF_IngredientCode_ID__Vendor_ID.VM
FROM         Ingred_Group INNER JOIN
                      Ingredient_Code ON Ingred_Group.Grupp = Ingredient_Code.[Group] LEFT OUTER JOIN
                      Ingredient_phys_Properties ON Ingredient_Code.IngredientCode_ID = Ingredient_phys_Properties.IngredientCode_ID LEFT OUTER JOIN
                      VENDOR INNER JOIN
                      _INTRF_IngredientCode_ID__Vendor_ID ON VENDOR.VENDOR_ID = _INTRF_IngredientCode_ID__Vendor_ID.VENDOR_ID INNER JOIN
                      TRADENAME_MAIN ON _INTRF_IngredientCode_ID__Vendor_ID.Tradename_Main_ID = TRADENAME_MAIN.Tradename_Main_ID ON 
                      Ingredient_Code.IngredientCode_ID = _INTRF_IngredientCode_ID__Vendor_ID.IngredientCode_ID
WHERE     (@name IS NULL OR
                      Ingredient_Code.Name = @name) AND (@Group IS NULL OR
                      Ingred_Group.Grupp = @Group) AND (@Class IS NULL OR
                      Ingredient_Code.Class = @Class) AND (@Status IS NULL OR
                      Ingredient_Code.Status = @Status) AND (@description IS NULL OR
                      Ingredient_Code.Descr LIKE @description) AND (@form IS NULL OR
                      Ingredient_Code.Form LIKE @form) AND (@PercRubber IS NULL OR
                      Ingredient_phys_Properties.percRubber = @PercRubber) AND (@TradeName IS NULL OR
                      TRADENAME_MAIN.TradeName LIKE @TradeName) AND (@Vendor IS NULL OR
                      VENDOR.VendorName LIKE @Vendor)

)
--


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Import_to_Ingred_Group__1]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE PROCEDURE [dbo].[Import_to_Ingred_Group__1]
AS

DECLARE @prm int

DELETE FROM Ingred_Group

INSERT INTO [NCPD].[dbo].[Ingred_Group]
           ([Grupp]
           ,[Descr])
SELECT     Grupp, descr
FROM         CPM.dbo.Ingred_Group
INSERT INTO [NCPD].[dbo].[Ingred_Group]
           ([Grupp]
           ,[Descr])
SELECT     CPIngred_1.[GROUP], ''new! add description!'' AS descr
FROM         CPRECIPE...CPIngred AS CPIngred_1 LEFT OUTER JOIN
                      Ingred_Group ON CPIngred_1.[GROUP] = Ingred_Group.Grupp
GROUP BY CPIngred_1.[GROUP], Ingred_Group.Grupp
HAVING      (Ingred_Group.Grupp IS NULL) AND (NOT (CPIngred_1.[GROUP] IS NULL))




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_GLTABLE_20]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'CREATE procedure [dbo].[Insert_GLTABLE_20] 
as
delete from GLTABLE
INSERT INTO GLTABLE
                      (SCOPE, CODE, TABLEFIELD)
SELECT     SCOPE, CODE, TABLEFIELD
FROM         CPRECIPE...GLTABLES
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_Sequence_Get]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_Sequence_Get]
(	
	-- Add the parameters for the function here
    @rECIPEnAME NVARCHAR(50),
	 @Rrelease NVARCHAR(50)

)
RETURNS TABLE 
AS
RETURN 
(
SELECT     TOP (100) PERCENT Recipe_Sequence_Commands.Command_Name, Recipe_Sequence_Main.Code, Recipe_Sequence_Main.Release, 
                      Recipe_Sequence_Steps.Step_NB, Recipe_Sequence_Steps.Command_Param, GLTABLE.CODE AS PLCCode, 
                      CAST(Recipe_Sequence_Steps.Step_NB AS decimal) AS _step
FROM         Recipe_Sequence_Steps INNER JOIN
                      Recipe_Sequence_Commands ON 
                      Recipe_Sequence_Steps.Recipe_Sequence_Commands = Recipe_Sequence_Commands.Recipe_Sequence_Commands INNER JOIN
                      GLTABLE ON Recipe_Sequence_Commands.Command_Name = GLTABLE.TABLEFIELD RIGHT OUTER JOIN
                      Recipe_Sequence_Main ON Recipe_Sequence_Steps.Recipe_Sequence_Main_ID = Recipe_Sequence_Main.Recipe_Sequence_Main_ID
WHERE     (Recipe_Sequence_Main.Code = @rECIPEnAME) AND (Recipe_Sequence_Main.Release = @Rrelease)
ORDER BY _step
)

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnBasic_RZPT_AmountOfIngredients]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE FUNCTION [dbo].[fnBasic_RZPT_AmountOfIngredients]
(
	@Recipe varchar(50)
, @release varchar (1))
/*
to use 
declare @Amount1 int
set @Amount1=  dbo.[fnBasic_RZPT_AmountOfIngredients](''08-0-1174'',''0'')
select @Amount1
go
*/
RETURNS  int 
AS
begin
	declare @Amount int
		DECLARE _cursor CURSOR FOR  --SELECT     COUNT(*) AS Expr1
--FROM         dbo.Recipe_Basic_RZPT(@Recipe, @release) AS Recipe_Basic_RZPT_1 
SELECT     COUNT(dbo.Recipe_Recipe.IngredName) AS Expr1
FROM         dbo.Recipe_Recipe INNER JOIN
                      dbo.Recipe_Prop_Main ON dbo.Recipe_Recipe.Recipe_ID = dbo.Recipe_Prop_Main.Recipe_ID
WHERE     (dbo.Recipe_Prop_Main.Code = @Recipe) AND (dbo.Recipe_Prop_Main.Release = @release)
open _cursor

	FETCH NEXT FROM _cursor INTO  @Amount 
	return @Amount
end

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'






CREATE  FUNCTION [dbo].[Recipe_Full_RZPT] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)
) 

--go
--select * from  [Recipe_Full_RZPT] (''01-0-1432'', ''0'')
RETURNS @output TABLE
(
	[RecipeID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Weight] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,	
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [smalldatetime] NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
SET @COUNT=1
INSERT INTO @output
--select * from [Recipe__RZPT_Query](@rECIPEnAME,@Rrelease)

SELECT     Recipe_Prop_Main.Code AS RecipeID, Recipe_Prop_Main.Release, Recipe_Recipe.IngredName AS material, 
                      Recipe_Recipe.PHR, Recipe_Recipe.Weight, CASE WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      dbo.Recipe_Recipe.SiloId = '''') THEN ''A'' WHEN Recipe_Recipe.SiloID IS NOT NULL THEN Recipe_Recipe.SiloID END AS ContainerNB, 
                      Recipe_Recipe.Phase, Ingredient_phys_Properties.percRubber, Ingredient_phys_Properties.density, 
                      CASE WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 256 + Ingredient_Warehouse.BalanceID WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NULL THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      ingredient_Warehouse.BalanceID IS NOT NULL THEN 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      CONVERT(float, dbo.Recipe_Recipe.PHR) >= 100 THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 2 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 3 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 4 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 5 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 6 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 7 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 7 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 7 * 256 + 1 END AS weighingID, CASE WHEN Ingredient_Warehouse.BalanceID IS NULL 
                      THEN 1 WHEN Ingredient_Warehouse.BalanceID IS NOT NULL THEN Ingredient_Warehouse.BalanceID END AS BalanceID, Ingredient_Warehouse.SiloId, Recipe_Recipe.MatIndex, 
                      Ingred_Preise.PreisePerKg AS PriceKG, Ingred_Preise.Prisedate AS PriceData, 
                      Ingredient_Code.Descr, Ingredient_Code.[Group] AS GRP
FROM         Ingredient_phys_Properties RIGHT OUTER JOIN
                      Ingredient_Code ON Ingredient_phys_Properties.IngredientCode_ID = Ingredient_Code.IngredientCode_ID LEFT OUTER JOIN
                      Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID LEFT OUTER JOIN
                      Ingred_Preise ON Ingredient_Code.IngredientCode_ID = Ingred_Preise.IngredientCode_ID RIGHT OUTER JOIN
                      Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID ON Ingredient_Code.Name = Recipe_Recipe.IngredName
WHERE     (Recipe_Prop_Main.Code IS NOT NULL) AND (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Prop_Main.Release = @Rrelease)

SET @COUNT=@COUNT+1


DECLARE _cursor CURSOR FOR  select material, MatIndex from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER varchar(50) 
				Declare @Density varchar(50)
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY FROM  RECIPE_FINAL_rzpt(@MATERIAL,''0'') 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
	INTO @material,@matIndex

end 
close _cursor
DEALLOCATE _cursor
  RETURN   


end 


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Recipes_Z_X_OR]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  Procedure [dbo].[prc_ITF_Recipes_Z_X_OR]
(	

/*
filtering with ingredients Logic ''OR''  for both ingredients
*/
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@customer NVARCHAR(50),
@ingred NVARCHAR(50),
 @ingred2 NVARCHAR(50)

)
as

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
SELECT DISTINCT 
                      Recipe_Prop_Main.Recipe_ID, RIGHT(RTRIM(Recipe_Prop_Main.Code), 4) AS [Recipe_Origin], Recipe_Prop_Main.Release AS [Recipe_Additional], 
                      Recipe_Prop_Main.Code AS [Recipe Version], Recipe_Prop_Main.Detailed_Group, Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, 
                      Recipe_Prop_Main.Mixer_Code , Recipe_Prop_Main.Descr, Recipe_Prop_Main.Loadfactor, Recipe_Prop_Main.MixTime, 
                      Recipe_Prop_Main.UpdatedOn, Recipe_Prop_Main.UpdatedBy 
INTO            ZZZ
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID INNER JOIN
                      Recipe_Recipe AS Recipe_Recipe_1 ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe_1.Recipe_ID
WHERE     (@rel IS NULL OR
                      Recipe_Prop_Main.Release = @rel) AND (@code_origin IS NULL OR
                      @code_origin = RIGHT(RTRIM(Recipe_Prop_Main.Code), 4)) AND (@code IS NULL OR
                      Recipe_Prop_Main.Code = @code) AND (@code_stage IS NULL OR
                      @code_stage = RIGHT(RTRIM(Recipe_Prop_Main.Code), 6)) AND (@group IS NULL OR
                      Recipe_Prop_Main.Detailed_Group = @group) AND (@Status IS NULL OR
                      Recipe_Prop_Main.Status = @Status) AND (@Class IS NULL OR
                      Recipe_Prop_Main.Class = @Class) AND (@Mixer_Code IS NULL OR
                      Recipe_Prop_Main.Mixer_Code = @Mixer_Code) AND (@Description IS NULL OR
                      Recipe_Prop_Main.Descr LIKE ''%'' + @Description + ''%'') 
AND (Recipe_Recipe.IngredName = @ingred OR
                      Recipe_Recipe.IngredName = @ingred2)







' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_Z_X_IngredName]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'

CREATE   FUNCTION [dbo].[fn_ITF_Recipes_Z_X_IngredName]
(	
	-- Add the parameters for the function here
)
 
RETURNS  TABLE
as
return
(
SELECT DISTINCT TOP (100) PERCENT IngredName
FROM         Recipe_Recipe
WHERE     (IngredName IS NOT NULL)
ORDER BY IngredName

)











' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Recipe_INSERT_New_material]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE   Proc [dbo].[Recipe_Recipe_INSERT_New_material] 
( 
@RecipeID bigint,  -- NOTE this is not the name , RecipeID is taken from the TAble recipe set!!!!
@IngredName NVARCHAR(50),
@PHR NVARCHAR(50),
@Weight NVARCHAR(50),
@ContainerNb NVARCHAR(50),
@Phase NVARCHAR(50),
@Matindex NVARCHAR(50),
@UpdatedBy NVARCHAR(50)
)  
as
INSERT INTO Recipe_Recipe
                      (Recipe_ID, IngredName, PHR, Weight, SiloID, Phase, MatIndex, UpdatedOn, UpdatedBy)
VALUES     (@RecipeID,@IngredName,@PHR,@Weight,@ContainerNb,@Phase,@Matindex, GETDATE(), @UpdatedBy)


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_Z_X_OR]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'








CREATE   FUNCTION [dbo].[fn_ITF_Recipes_Z_X_OR]
(	
-- This is when find Recipes with ingredients in it 

	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
-----
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@customer NVARCHAR(50),
@ingred NVARCHAR(50),
@ingred2 NVARCHAR(50)


)
--go
--SELECT   *
--FROM         dbo.[fn_ITF_Recipes_Z_X](
--null		--@code_origin NVARCHAR(50),	--''0012'',
--,null-- ''W''		--@rel NVARCHAR(1),	--''W''
--, null		--@code NVARCHAR(50),			--''00-0-0012''
--, null		--@code_stage NVARCHAR(50),   --''0-0012'',
--,null-- ''O''		--@group NVARCHAR(50),		--''AA''
--, null--''O''		--@Status NVARCHAR(1),		--''O/S''
--,null-- ''P''		--@Class NVARCHAR(1),			--''P''
--,null-- ''AA''		--@Mixer_Code NVARCHAR(50),	--''AB''
--, null--''DZ''		--@description , [ ] -Any single character within the specified range ([a-f]) or set ([abcdef]),
--			-- [^]Any single character not within the specified range ([^a-f]) or set ([^abcdef]).
--,null		--@Color NVARCHAR(50),
--,null		--@industry NVARCHAR(50),
--,null		--@recept_type NVARCHAR(50),
--,null		--@curing_system NVARCHAR(50),
--,null		--@curing_process NVARCHAR(50),
--,null		--@filler NVARCHAR(50),
--,null		--@certificate NVARCHAR(50),
--,null		--@schelflife1 NVARCHAR(50),
--,null		--@schelflife2 NVARCHAR(50),
--,''65''		--@Hardnes_Sha1 NVARCHAR(50),
--,''65''		--@Hardnes_Sha2 NVARCHAR(50),
--,null		-- ingred 1
--,''00001''		-- ingred 2
-- )
 
RETURNS @output TABLE

(

	[Recipe_ID] [bigint]  NULL,
	[Recipe_Origin] [varchar](4) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe_Addditional] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe Version] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe Stage] [varchar](6) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Detailed_Group] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Status] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Class] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Mixer_Code] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Loadfactor] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MixTime] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[IngredName1] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[IngredName2] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Name] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)



begin
insert into @output
		SELECT DISTINCT 
							  Recipe_Prop_Main.Recipe_ID, RIGHT(RTRIM(Recipe_Prop_Main.Code), 4) AS Recipe_Origin, Recipe_Prop_Main.Release AS Recipe_Addditional, 
							  Recipe_Prop_Main.Code AS [Recipe Version], RIGHT(RTRIM(Recipe_Prop_Main.Code), 6) AS [Recipe Stage], Recipe_Prop_Main.Detailed_Group, 
							  Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, Recipe_Prop_Main.Mixer_Code, Recipe_Prop_Main.Descr, Recipe_Prop_Main.Loadfactor, 
							  Recipe_Prop_Main.MixTime, Recipe_Prop_Main.UpdatedOn, Recipe_Prop_Main.UpdatedBy, Recipe_Recipe.IngredName AS IngredName1, 
							  Recipe_Recipe_1.IngredName AS IngredName2, Mixer_InfoBasic.Name
		FROM         Recipe_Prop_Main INNER JOIN
							  Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID INNER JOIN
							  Recipe_Recipe AS Recipe_Recipe_1 ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe_1.Recipe_ID INNER JOIN
							  Mixer_InfoBasic ON Recipe_Prop_Main.Mixer_Code = Mixer_InfoBasic.Code
		WHERE     (@rel IS NULL OR
							  Recipe_Prop_Main.Release = @rel) AND (@code_origin IS NULL OR
							  @code_origin = RIGHT(RTRIM(Recipe_Prop_Main.Code), 4)) AND (@code IS NULL OR
							  Recipe_Prop_Main.Code = @code) AND (@code_stage IS NULL OR
							  @code_stage = RIGHT(RTRIM(Recipe_Prop_Main.Code), 6)) AND (@group IS NULL OR
							  Recipe_Prop_Main.Detailed_Group = @group) AND (@Status IS NULL OR
							  Recipe_Prop_Main.Status = @Status) AND (@Class IS NULL OR
							  Recipe_Prop_Main.Class = @Class) AND (@Mixer_Code IS NULL OR
							  Recipe_Prop_Main.Mixer_Code = @Mixer_Code) AND (@Description IS NULL OR
							  Recipe_Prop_Main.Descr LIKE ''%'' + @Description + ''%'') AND (  Recipe_Recipe.IngredName = @ingred) 
OR (Recipe_Recipe_1.IngredName = @ingred2)
return
end


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_RECIPE_main_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE  procedure  [dbo].[prc_ITF_RECIPE_main_Insert]
(  @recipeID bigint,
 @CreatedOn smalldatetime,
@CreatedBy varchar(50))
/*
create the new recipe with the name "NEW" 
used by  clicking button 
*/
as


-- delete first ''NEW'' if exists already

	DELETE FROM Recipe_Prop_Main
	WHERE     (Code = ''NEW'')


/* Check if choosen recipe (RecipeID) as template for the new recipe  exists, MUST in general */
declare @Code as varchar(50);
DECLARE @NEW_Recipr_ID   bigint 

set @NEW_Recipr_ID = (SELECT     Recipe_ID FROM Recipe_Prop_Main WHERE     (Recipe_ID = @recipeID))
print N''M1'' +  str(@NEW_Recipr_ID)
if @NEW_Recipr_ID IS NULL 
	begin
		print N''NO SUCH RECIPE''
		return 
	end 

/* 

*/
DECLARE @MyTableVar table (Code varchar(50), Release varchar(50), Mixer_Code varchar(50) )
declare @Release  varchar(50)
declare @Mixer_Code  varchar(50)


--begin try 
	
INSERT INTO Recipe_Prop_Main
                      (Detailed_Group, Mixer_Code, Code, Release, Descr, Status, Class, Loadfactor, MixTime, UpdatedOn, UpdatedBy, CreatedOn, CreatedBy)
OUTPUT    inserted.Code, inserted.Release, inserted.Mixer_Code 
INTO @MyTableVar

SELECT     Detailed_Group, Mixer_Code, ''NEW'' AS CODE, Release, Descr, Status, Class, Loadfactor, MixTime, UpdatedOn, UpdatedBy, @CreatedOn as CreatedOn,@CreatedBy as  CreatedBy
FROM         Recipe_Prop_Main AS Recipe_Prop_Main_1
WHERE     (Recipe_ID = @recipeID)

--print @NEW_Recipr_ID
set @NEW_Recipr_ID =@@identity
print ''@NEW_Recipr_ID:''+ cast (@NEW_Recipr_ID as varchar)


	INSERT INTO Recipe_Prop_Free_Info (Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
	SELECT     @NEW_Recipr_ID AS Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy
	FROM         Recipe_Prop_Free_Info AS Recipe_Prop_Free_Info_1
	WHERE     (Recipe_ID = @recipeID)



INSERT INTO Recipe_Prop_Free_Text(Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
SELECT     @NEW_Recipr_ID AS Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy
FROM         Recipe_Prop_Free_Text AS Recipe_Prop_Free_Text_1
WHERE     (Recipe_ID = @recipeID)

print ''@recipeID Recipe_Recipe :''+ cast (@recipeID as varchar)

INSERT INTO Recipe_Recipe (Recipe_ID, IngredName, PHR, Weight, SiloID, Phase, MatIndex, UpdatedOn, UpdatedBy)
SELECT     @NEW_Recipr_ID AS Recipe_ID, IngredName, PHR, Weight, SiloID, Phase, MatIndex, UpdatedOn, UpdatedBy
FROM         Recipe_Recipe AS Recipe_Recipe_1
WHERE     (Recipe_ID = @recipeID)

/*-----------------------------------*/	
DECLARE @NEW_Recipr_ID1   bigint 
--SELECT     @recipeID AS [@recipeID] 
print ''Sequence -----''
set @Code  = (SELECT Code FROM  Recipe_Prop_Main WHERE (Recipe_ID = @RecipeID))
--select @Code as [code ]
set @Release  = (select Release from @MyTableVar)
set @Mixer_Code  = (select Mixer_Code from @MyTableVar)



DELETE FROM Recipe_Sequence_Main
WHERE     (Code = ''NEW'')

--print (''INSERT INTO Recipe_Sequence_Main (Code, Release, Mixer_Code, Info, Status, UpdatedOn, UpdatedBy)'')
INSERT INTO Recipe_Sequence_Main (Code, Release, Mixer_Code, Info, Status, UpdatedOn, UpdatedBy)
SELECT     ''NEW'' as Code, Release, Mixer_Code, Info, Status, UpdatedOn, UpdatedBy
FROM         Recipe_Sequence_Main AS Recipe_Sequence_Main_1
WHERE     (Release = @Release) AND (Mixer_Code = @Mixer_Code) AND (Code = @Code)


--print (''set @NEW_Recipr_ID1  = (SELECT     Recipe_Sequence_Main.Recipe_Sequence_Main_ID'')
set @NEW_Recipr_ID1  = (SELECT     Recipe_Sequence_Main.Recipe_Sequence_Main_ID
FROM         Recipe_Sequence_Main INNER JOIN
                      Recipe_Prop_Main ON Recipe_Sequence_Main.Code = Recipe_Prop_Main.Code AND 
                      Recipe_Sequence_Main.Release = Recipe_Prop_Main.Release AND Recipe_Sequence_Main.Mixer_Code = Recipe_Prop_Main.Mixer_Code
WHERE     (Recipe_Prop_Main.Code = ''NEW''))
--select @NEW_Recipr_ID1 as [NEW_Recipr_ID1_sequence ]
print  ''@NEW_Recipr_ID1 for Sequence'' + cast (@NEW_Recipr_ID1 as varchar)


--print (''INSERT INTO Recipe_Sequence_Steps '')
INSERT INTO Recipe_Sequence_Steps
                      (Recipe_Sequence_Main_ID, Recipe_Sequence_Commands, Step_NB, Command_Param, Command_Status, UpdatedOn, UpdatedBy)
SELECT     @NEW_Recipr_ID1 AS Recipe_Sequence_Main_ID, Recipe_Sequence_Steps_1.Recipe_Sequence_Commands, Recipe_Sequence_Steps_1.Step_NB, 
                      Recipe_Sequence_Steps_1.Command_Param, Recipe_Sequence_Steps_1.Command_Status, Recipe_Sequence_Steps_1.UpdatedOn, 
                      Recipe_Sequence_Steps_1.UpdatedBy
FROM         Recipe_Sequence_Steps AS Recipe_Sequence_Steps_1 INNER JOIN
                      Recipe_Sequence_Main ON Recipe_Sequence_Steps_1.Recipe_Sequence_Main_ID = Recipe_Sequence_Main.Recipe_Sequence_Main_ID INNER JOIN
                      Recipe_Prop_Main ON Recipe_Sequence_Main.Code = Recipe_Prop_Main.Code AND 
                      Recipe_Sequence_Main.Release = Recipe_Prop_Main.Release AND Recipe_Sequence_Main.Mixer_Code = Recipe_Prop_Main.Mixer_Code
WHERE     (Recipe_Prop_Main.Recipe_ID = @recipeID)


--go 
--declare @Code1 integer
--execute  @Code1 =   [prc_ITF_RECIPE_main_Insert] 813193, ''2015-12-02'',''AD''
--select ''Return Value'' = @Code1
--select * from Recipe_Prop_Main where recipe_Id =813193
--
--SELECT     Recipe_ID, Detailed_Group, Mixer_Code, Code, Release, Descr, Status, Class, Loadfactor, MixTime, UpdatedOn, UpdatedBy, CreatedOn, 
--                      CreatedBy
--FROM         Recipe_Prop_Main
--WHERE     (Recipe_ID = 831254)





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_FINAL_RZPT]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



--select * from  [Recipe_FINAL_RZPT] (''08-8-N830'',''0'') 


CREATE     function   [dbo].[Recipe_FINAL_RZPT] (@rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)) 

RETURNS @output1 TABLE
-- calculation of parametrs for masterbatch to be used in final
-- calculation of parametrs for final batch using interactive calculations for masterbatches
(
	[RecipeID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Weight] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Price] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)
BEGIN
 DECLARE @COUNT INT
declare @totalWEight  float
declare @totalVolume  float
declare @totalRubber   float 
declare @totalDensity  float
declare @totalPrice  float
declare @totalPHR float 
declare @totalDescr varchar(50)


set @totalWEight =   0
set @totalVolume =  0
set @totalRubber    = 0
set @totalPrice  =0
set @totalPHR = 0
set @totalPrice =0
set @totalDescr=''Master/remix''
	declare @SiloId varchar(50) 
	declare @RecipeID [varchar](50)
	declare @Release [varchar](50) 
	declare @material [varchar](50) 
	declare @PHR float 
	declare @Weight float
	declare @ContainerNB [varchar](50) 
	declare @Phase [varchar](50) 
	declare @percRubber float
	declare @density float
	declare @weighingID [int] 
	declare @BalanceID [varchar](50) 
	declare @MatIndex [varchar](50) 
	declare @Descr [varchar](50) 
	declare @GRP [varchar](50)
declare @PriceKg  [varchar](50)
declare @Dateprice [varchar](50)

DECLARE _cursor CURSOR FOR 
SELECT     Ingredient_Warehouse.SiloId, Recipe_Prop_Main.Code AS RecipeID, Recipe_Prop_Main.Release, Recipe_Recipe.IngredName AS material, 
                      Recipe_Recipe.PHR, Recipe_Recipe.Weight, Recipe_Recipe.SiloID AS ContainerNB, Recipe_Recipe.Phase, Ingredient_phys_Properties.percRubber, 
                      Ingredient_phys_Properties.density, CASE WHEN dbo.Recipe_Recipe.SiloId = ''0'' THEN 256 + (dbo.Ingredient_Warehouse.BALANCEID) 
                      WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND dbo.Ingredient_Warehouse.BALANCEID IS NOT NULL THEN 256 + (dbo.Ingredient_Warehouse.BALANCEID) 
                      WHEN dbo.Recipe_Recipe.SiloId = ''B'' THEN 2 * 256 + (dbo.Ingredient_Warehouse.BALANCEID) WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      > 100 THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' THEN 2 * 256 + (dbo.Ingredient_Warehouse.BALANCEID) 
                      WHEN dbo.Recipe_Recipe.SiloId = ''C'' THEN 3 * 256 + (dbo.Ingredient_Warehouse.BALANCEID) 
                      WHEN dbo.Recipe_Recipe.SiloId = ''D'' THEN 4 * 256 + (dbo.Ingredient_Warehouse.BALANCEID) 
                      WHEN dbo.Recipe_Recipe.SiloId = ''E'' THEN 5 * 256 + (dbo.Ingredient_Warehouse.BALANCEID) 
                      WHEN dbo.Recipe_Recipe.SiloId = ''F'' THEN 6 * 256 + (dbo.Ingredient_Warehouse.BALANCEID) 
                      WHEN dbo.Recipe_Recipe.SiloId = ''H'' THEN 7 * 256 + (dbo.Ingredient_Warehouse.BALANCEID) END AS weighingID, Ingredient_Warehouse.BalanceID, 
                      Recipe_Recipe.MatIndex, Ingredient_Code.Descr, Ingredient_Code.[Group] AS GRP, Ingredient_Code.ActualPreisePerKg AS PreisePerKg
FROM         Ingredient_phys_Properties RIGHT OUTER JOIN
                      Ingredient_Code ON Ingredient_phys_Properties.IngredientCode_ID = Ingredient_Code.IngredientCode_ID LEFT OUTER JOIN
                      Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID LEFT OUTER JOIN
                      Ingred_Preise ON Ingredient_Code.IngredientCode_ID = Ingred_Preise.IngredientCode_ID RIGHT OUTER JOIN
                      Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID ON Ingredient_Code.Name = Recipe_Recipe.IngredName
WHERE     (Recipe_Prop_Main.Code IS NOT NULL) AND (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Prop_Main.Release = @Rrelease)

open _cursor

FETCH NEXT FROM _cursor 
INTO
		@SiloId, 	
		@RecipeID ,
	  @Release  , 
	  @material ,  
	  @PHR   ,
	  @Weight  , 
	  @ContainerNB ,  
	  @Phase   ,
	  @percRubber ,  
	  @density   ,
	  @weighingID,
	  @BalanceID  , 
	  @MatIndex  , 
	  @Descr   ,
	  @GRP   ,
@priceKg

if rtrim(ltrim(@rECIPEnAME))=Rtrim(Ltrim(@material)) 
begin
insert into @output1(
recipeId,
	  Release  , 
	  PHR   ,
	  Weight  , 
	  percRubber ,  
	  density   ,
	  Descr  ,
Price
	
) values
(
''RECURS '', 
	  ''RECURS''  , 
	  1   ,
	 1  , 
	  1 ,  
	 1,
	 ''ERROR!'' ,
	''1'' 

)
return
end


WHILE @@FETCH_STATUS <> -1 
	begin
				if @matindex=''I'' 
					begin
						set @totalWEight =   @totalWEight + @Weight
						set @totalVolume =  @totalVolume + @Weight/@density
						set @totalPrice =@totalPrice+ @priceKg
						set @totalPHR = @totalPHR+@PHR
						--set  @totalDescr = ''MASTER''
					end 
				if @matindex=''R'' 
					begin

					declare @RCP varchar(50)
					declare @RLS  varchar (50)

					DECLARE _cursor03 CURSOR FOR  SELECT     percRubber, density, Descr, RecipeID, Release
							FROM dbo.Recipe_FINAL_RZPT(@material, ''0'') 

					OPEN _cursor03

									FETCH NEXT FROM _cursor03 INTO @percRubber, @density,@descr, @RCP,@RLS

									WHILE @@FETCH_STATUS <> -1 
									begin
										set @totalWEight =   @totalWEight + @Weight
										set @totalVolume =  @totalVolume + @Weight/@density
										set @totalPrice =@totalPrice+ @priceKg
										set @totalPHR = @totalPHR+@PHR
										set @totalDescr =  ''final/remix''	
										FETCH NEXT FROM _cursor03 INTO @percRubber, @density,@descr, @RCP,@RLS

									end
									
											close _cursor03
											deallocate _cursor03
					end 

					FETCH NEXT FROM _cursor 
					INTO
							@SiloId, 	
							@RecipeID ,
						  @Release  , 
						  @material ,  
						  @PHR   ,
						  @Weight  , 
						  @ContainerNB ,  
						  @Phase   ,
						  @percRubber ,  
						  @density   ,
						  @weighingID,
						  @BalanceID  , 
						  @MatIndex  , 
						  @Descr   ,
						  @GRP   ,
					@priceKg

	end 
if 	@totalVolume=0  set @totalDescr  = -11
		else		set @totalDensity  =@totalweight/@totalVolume
	
if 	@totalPHR=0  set @totalDescr  = -12
		else			set @totalRubber = 1/@totalPHR*100*100

  
insert into @output1(
recipeId,
	  Release  , 
	  PHR   ,
	  Weight  , 
	  percRubber ,  
	  density   ,
	  Descr  ,
Price
		

) values
(
@rECIPEnAME, 
	  @Rrelease  , 
	  @totalPHR   ,
	  @totalWEight  , 
	  @totalRubber ,  
	  @totalDensity   ,
	 @totalDescr  ,
	@totalPrice 

)
close _cursor
deallocate _cursor


return 
end -- BEGIN   DECLARE @COUNT INT





' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Basic_RZPT]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'










CREATE  FUNCTION [dbo].[Recipe_Basic_RZPT] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)
) 
-- just read data from recipe
-- recipe basic: normal for masterbatches for final batches no calculation for masters inside
RETURNS @output TABLE
(
	[RecipeID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Weight] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,	
	[weighingID] [int] NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [smalldatetime] NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)
BEGIN
  DECLARE @COUNT INT
SET @COUNT=1
INSERT INTO @output
SELECT     Recipe_Prop_Main.Code AS RecipeID, Recipe_Prop_Main.Release, Recipe_Recipe.IngredName AS material, Recipe_Recipe.PHR, 
                      Recipe_Recipe.Weight, CASE WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') THEN ''A'' WHEN Recipe_Recipe.SiloID IS NOT NULL THEN Recipe_Recipe.SiloID END AS ContainerNB, 
                      Recipe_Recipe.Phase, Ingredient_phys_Properties.percRubber, Ingredient_phys_Properties.density, 
                      CASE WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 256 + Ingredient_Warehouse.BalanceID WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NULL THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      ingredient_Warehouse.BalanceID IS NOT NULL THEN 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      CONVERT(float, dbo.Recipe_Recipe.PHR) >= 100 THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 2 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 3 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 4 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 5 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 6 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 7 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 7 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 7 * 256 + 1 END AS weighingID, CASE WHEN Ingredient_Warehouse.BalanceID IS NULL 
                      THEN 1 WHEN Ingredient_Warehouse.BalanceID IS NOT NULL THEN Ingredient_Warehouse.BalanceID END AS BalanceID, 
                      Ingredient_Warehouse.SiloId, Recipe_Recipe.MatIndex, Ingredient_Code.ActualPreisePerKg AS PreisePerKg, NULL AS PriceData, 
                      Ingredient_Code.Descr, Ingredient_Code.[Group] AS GRP
FROM         Ingredient_phys_Properties RIGHT OUTER JOIN
                      Ingredient_Code ON Ingredient_phys_Properties.IngredientCode_ID = Ingredient_Code.IngredientCode_ID LEFT OUTER JOIN
                      Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID RIGHT OUTER JOIN
                      Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID ON Ingredient_Code.Name = Recipe_Recipe.IngredName
WHERE     (Recipe_Prop_Main.Code IS NOT NULL) AND (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Prop_Main.Release = @Rrelease)

SET @COUNT=@COUNT+1


  RETURN   


end


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_Z_X_original]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'





CREATE  FUNCTION [dbo].[fn_ITF_Recipes_Z_X_original]
(	
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
-----
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@customer NVARCHAR(50),
@ingred NVARCHAR(50),
@ingred2 NVARCHAR(50)


)
--go
--SELECT   *
--FROM         dbo.[fn_ITF_Recipes_Z_X](
--null		--@code_origin NVARCHAR(50),	--''0012'',
--,null-- ''W''		--@rel NVARCHAR(1),	--''W''
--, null		--@code NVARCHAR(50),			--''00-0-0012''
--, null		--@code_stage NVARCHAR(50),   --''0-0012'',
--,null-- ''O''		--@group NVARCHAR(50),		--''AA''
--, null--''O''		--@Status NVARCHAR(1),		--''O/S''
--,null-- ''P''		--@Class NVARCHAR(1),			--''P''
--,null-- ''AA''		--@Mixer_Code NVARCHAR(50),	--''AB''
--, null--''DZ''		--@description , [ ] -Any single character within the specified range ([a-f]) or set ([abcdef]),
--			-- [^]Any single character not within the specified range ([^a-f]) or set ([^abcdef]).
--,null		--@Color NVARCHAR(50),
--,null		--@industry NVARCHAR(50),
--,null		--@recept_type NVARCHAR(50),
--,null		--@curing_system NVARCHAR(50),
--,null		--@curing_process NVARCHAR(50),
--,null		--@filler NVARCHAR(50),
--,null		--@certificate NVARCHAR(50),
--,null		--@schelflife1 NVARCHAR(50),
--,null		--@schelflife2 NVARCHAR(50),
--,''65''		--@Hardnes_Sha1 NVARCHAR(50),
--,''65''		--@Hardnes_Sha2 NVARCHAR(50),
--,null		-- ingred 1
--,''00001''		-- ingred 2
-- )
 
RETURNS TABLE 
AS
RETURN 
(
SELECT DISTINCT 
                      Recipe_Prop_Main.Recipe_ID, RIGHT(RTRIM(Recipe_Prop_Main.Code), 4) AS Recipe_Origin, Recipe_Prop_Main.Release AS Recipe_Addditional, 
                      Recipe_Prop_Main.Code AS [Recipe Version], RIGHT(RTRIM(Recipe_Prop_Main.Code), 6) AS [Recipe Stage], Recipe_Prop_Main.Detailed_Group, 
                      Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, Recipe_Prop_Main.Mixer_Code, Recipe_Prop_Main.Descr, Recipe_Prop_Main.Loadfactor, 
                      Recipe_Prop_Main.MixTime, Recipe_Prop_Main.UpdatedOn, Recipe_Prop_Main.UpdatedBy, Recipe_Recipe.IngredName AS IngredName1, 
                      Recipe_Recipe_1.IngredName AS IngredName2, Mixer_InfoBasic.Name
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID INNER JOIN
                      Recipe_Recipe AS Recipe_Recipe_1 ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe_1.Recipe_ID INNER JOIN
                      Mixer_InfoBasic ON Recipe_Prop_Main.Mixer_Code = Mixer_InfoBasic.Code
WHERE     (@rel IS NULL OR
                      Recipe_Prop_Main.Release = @rel) AND (@code_origin IS NULL OR
                      @code_origin = RIGHT(RTRIM(Recipe_Prop_Main.Code), 4)) AND (@code IS NULL OR
                      Recipe_Prop_Main.Code = @code) AND (@code_stage IS NULL OR
                      @code_stage = RIGHT(RTRIM(Recipe_Prop_Main.Code), 6)) AND (@group IS NULL OR
                      Recipe_Prop_Main.Detailed_Group = @group) AND (@Status IS NULL OR
                      Recipe_Prop_Main.Status = @Status) AND (@Class IS NULL OR
                      Recipe_Prop_Main.Class = @Class) AND (@Mixer_Code IS NULL OR
                      Recipe_Prop_Main.Mixer_Code = @Mixer_Code) AND (@Description IS NULL OR
                      Recipe_Prop_Main.Descr LIKE ''%'' + @Description + ''%'') AND (@ingred IS NULL OR
                      Recipe_Recipe.IngredName = @ingred) AND (@ingred2 IS NULL OR
                      Recipe_Recipe_1.IngredName = @ingred2)

)


--go
--SELECT   *
--FROM         dbo.[fn_ITF_Recipes_Z_X](
--null		--@code_origin NVARCHAR(50),	--''0012'',
--,null-- ''W''		--@rel NVARCHAR(1),	--''W''
--, null		--@code NVARCHAR(50),			--''00-0-0012''
--, null		--@code_stage NVARCHAR(50),   --''0-0012'',
--,null-- ''O''		--@group NVARCHAR(50),		--''AA''
--, null--''O''		--@Status NVARCHAR(1),		--''O/S''
--,null-- ''P''		--@Class NVARCHAR(1),			--''P''
--,null-- ''AA''		--@Mixer_Code NVARCHAR(50),	--''AB''
--, null--''DZ''		--@description , [ ] -Any single character within the specified range ([a-f]) or set ([abcdef]),
--			-- [^]Any single character not within the specified range ([^a-f]) or set ([^abcdef]).
--,null		--@Color NVARCHAR(50),
--,null		--@industry NVARCHAR(50),
--,null		--@recept_type NVARCHAR(50),
--,null		--@curing_system NVARCHAR(50),
--,null		--@curing_process NVARCHAR(50),
--,null		--@filler NVARCHAR(50),
--,null		--@certificate NVARCHAR(50),
--,null		--@schelflife1 NVARCHAR(50),
--,null		--@schelflife2 NVARCHAR(50),
--,''65''		--@Hardnes_Sha1 NVARCHAR(50),
--,''65''		--@Hardnes_Sha2 NVARCHAR(50),
--,null		-- ingred 1
--,''00001''		-- ingred 2
-- )




' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Recipes_Z_X]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE Procedure [dbo].[prc_ITF_Recipes_Z_X]
(	

/*
filtering with ingredients LOGIC ''AND '' for ingredients
*/
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@customer NVARCHAR(50),
@ingred NVARCHAR(50),
 @ingred2 NVARCHAR(50)

)

as

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
SELECT DISTINCT 
                      Recipe_Prop_Main.Recipe_ID, RIGHT(RTRIM(Recipe_Prop_Main.Code), 4) AS [Recipe_Origin], Recipe_Prop_Main.Release AS [Recipe_Additional], 
                      Recipe_Prop_Main.Code AS [Recipe Version], Recipe_Prop_Main.Detailed_Group, Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, 
                      Recipe_Prop_Main.Mixer_Code , Recipe_Prop_Main.Descr, Recipe_Prop_Main.Loadfactor, Recipe_Prop_Main.MixTime, 
                      Recipe_Prop_Main.UpdatedOn, Recipe_Prop_Main.UpdatedBy 
INTO            ZZZ
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID INNER JOIN
                      Recipe_Recipe AS Recipe_Recipe_1 ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe_1.Recipe_ID
WHERE     (@rel IS NULL OR
                      Recipe_Prop_Main.Release = @rel) AND (@code_origin IS NULL OR
                      @code_origin = RIGHT(RTRIM(Recipe_Prop_Main.Code), 4)) AND (@code IS NULL OR
                      Recipe_Prop_Main.Code = @code) AND (@code_stage IS NULL OR
                      @code_stage = RIGHT(RTRIM(Recipe_Prop_Main.Code), 6)) AND (@group IS NULL OR
                      Recipe_Prop_Main.Detailed_Group = @group) AND (@Status IS NULL OR
                      Recipe_Prop_Main.Status = @Status) AND (@Class IS NULL OR
                      Recipe_Prop_Main.Class = @Class) AND (@Mixer_Code IS NULL OR
                      Recipe_Prop_Main.Mixer_Code = @Mixer_Code) AND (@Description IS NULL OR
                      Recipe_Prop_Main.Descr LIKE ''%'' + @Description + ''%'') AND ((@ingred IS NULL OR
                      Recipe_Recipe.IngredName = @ingred) AND (@ingred2 IS NULL OR
                      Recipe_Recipe_1.IngredName = @ingred2))



/*
Recipe_Origin to Recipe, 
Recipe_Additional to Add.,
 Descr. To Description,
 Detailed_group to Group, Mixer_code to Mixer, 
UpdatedOn to Updated On, 
UpdatedBy to Updated By.
*/









' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_RECIPE_Scratch]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'





CREATE   procedure  [dbo].[prc_ITF_RECIPE_Scratch]
(  @recipeID bigint,
 @CreatedOn smalldatetime,
@CreatedBy varchar(50)
-- ,@Recipe_Id bigint OUTPUT 
)
/*
create the new recipe with the name "NEW" 
used by  clicking button 
*/
as


-- delete first ''NEW'' if exists already

	DELETE FROM Recipe_Prop_Main
	WHERE     (Code = ''NEW'')

DELETE FROM Recipe_Prop_Free_Info
FROM         Recipe_Prop_Free_Info INNER JOIN
                      Recipe_Prop_Main ON Recipe_Prop_Free_Info.Recipe_ID = Recipe_Prop_Main.Recipe_ID
WHERE     (Recipe_Prop_Main.Code = ''NEW'')

DELETE FROM Recipe_Prop_Free_Text
FROM         Recipe_Prop_Free_Text INNER JOIN
                      Recipe_Prop_Main ON Recipe_Prop_Free_Text.Recipe_ID = Recipe_Prop_Main.Recipe_ID
WHERE     (Recipe_Prop_Main.Code = ''NEW'')

DELETE FROM Recipe_Recipe
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID
WHERE     (Recipe_Prop_Main.Code = ''NEW'')

DELETE FROM Recipe_Sequence_Steps
FROM         Recipe_Sequence_Main INNER JOIN
                      Recipe_Sequence_Steps ON Recipe_Sequence_Main.Recipe_Sequence_Main_ID = Recipe_Sequence_Steps.Recipe_Sequence_Main_ID
WHERE     (Recipe_Sequence_Main.Code = ''NEW'')

DELETE FROM Recipe_Sequence_Main
WHERE     (Code = ''NEW'')



INSERT INTO [NCPD].[dbo].[Recipe_Prop_Main]
           ([Detailed_Group]
           ,[Mixer_Code]
           ,[Code]
           ,[Release]
           ,[Descr]
           ,[Status]
           ,[Class]
           ,[Loadfactor]
           ,[MixTime]
           ,[UpdatedOn]
           ,[UpdatedBy]
           ,[CreatedOn]
           ,[CreatedBy])
     VALUES
           (NULL 
           ,''AA''
           ,''NEW''
           ,''0'' 
           ,NULL 
           ,NULL 
           ,NULL 
           ,NULL 
           ,NULL 
           ,@CreatedOn 
           ,@CreatedBy 
           ,@CreatedOn 
           ,@CreatedBy )


/* 

*/
--print @NEW_Recipr_ID
--DECLARE @NEW_Recipr_ID   bigint 
--set @NEW_Recipr_ID =@@identity




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_Z_X]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



CREATE   FUNCTION [dbo].[fn_ITF_Recipes_Z_X]
(	

-- This is when find Recipes with ingredients in it 
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
-----
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@customer NVARCHAR(50),
@ingred NVARCHAR(50),
@ingred2 NVARCHAR(50)


)

-- )
 
RETURNS @output TABLE

(

	[Recipe_ID] [bigint]  NULL,
	[Recipe_Origin] [varchar](4) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe_Addditional] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe Version] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe Stage] [varchar](6) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Detailed_Group] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Status] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Class] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Mixer_Code] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Loadfactor] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MixTime] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[IngredName1] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[IngredName2] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Name] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)


begin
insert into @output
		SELECT DISTINCT 
							  Recipe_Prop_Main.Recipe_ID, RIGHT(RTRIM(Recipe_Prop_Main.Code), 4) AS Recipe_Origin, Recipe_Prop_Main.Release AS Recipe_Addditional, 
							  Recipe_Prop_Main.Code AS [Recipe Version], RIGHT(RTRIM(Recipe_Prop_Main.Code), 6) AS [Recipe Stage], Recipe_Prop_Main.Detailed_Group, 
							  Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, Recipe_Prop_Main.Mixer_Code, Recipe_Prop_Main.Descr, Recipe_Prop_Main.Loadfactor, 
							  Recipe_Prop_Main.MixTime, Recipe_Prop_Main.UpdatedOn, Recipe_Prop_Main.UpdatedBy, Recipe_Recipe.IngredName AS IngredName1, 
							  Recipe_Recipe_1.IngredName AS IngredName2, Mixer_InfoBasic.Name
		FROM         Recipe_Prop_Main INNER JOIN
							  Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID INNER JOIN
							  Recipe_Recipe AS Recipe_Recipe_1 ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe_1.Recipe_ID INNER JOIN
							  Mixer_InfoBasic ON Recipe_Prop_Main.Mixer_Code = Mixer_InfoBasic.Code
		WHERE     (@rel IS NULL OR
							  Recipe_Prop_Main.Release = @rel) AND (@code_origin IS NULL OR
							  @code_origin = RIGHT(RTRIM(Recipe_Prop_Main.Code), 4)) AND (@code IS NULL OR
							  Recipe_Prop_Main.Code = @code) AND (@code_stage IS NULL OR
							  @code_stage = RIGHT(RTRIM(Recipe_Prop_Main.Code), 6)) AND (@group IS NULL OR
							  Recipe_Prop_Main.Detailed_Group = @group) AND (@Status IS NULL OR
							  Recipe_Prop_Main.Status = @Status) AND (@Class IS NULL OR
							  Recipe_Prop_Main.Class = @Class) AND (@Mixer_Code IS NULL OR
							  Recipe_Prop_Main.Mixer_Code = @Mixer_Code) AND (@Description IS NULL OR
							  Recipe_Prop_Main.Descr LIKE ''%'' + @Description + ''%'') AND (@ingred IS NULL OR
							  Recipe_Recipe.IngredName = @ingred) AND (@ingred2 IS NULL OR
							  Recipe_Recipe_1.IngredName = @ingred2)
return
end











' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Vendor_17]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE procedure [dbo].[Insert_Vendor_17] 
as
                   
                      
INSERT INTO VENDOR
                      (VendorName, Adress, ZipCode, City, Country, Phone, Fax, Email, Website, FreeInfo, UpdatedOn, UpdatedBy)
SELECT     fn_Ingred_AllsupplierS_1.vendor, CPTRADE_1.ADDRESS, CPTRADE_1.CODE, CPTRADE_1.CITY, CPTRADE_1.COUNTRY, 
                     CPTRADE_1.PHONE, CPTRADE_1.FAX, NULL AS Expr2, NULL AS Expr3, NULL AS Expr4, CPTRADE_1.LASTUPDATE, ''SB'' AS Expr1
FROM         CPRECIPE...CPTRADE AS CPTRADE_1 INNER JOIN
                      dbo.fn_Ingred_AllsupplierS() AS fn_Ingred_AllsupplierS_1 ON CPTRADE_1.NAME COLLATE SQL_Latin1_General_CP1_CI_AS = fn_Ingred_AllsupplierS_1.vendor



delete      
FROM         dbo.VENDOR
WHERE     (VENDOR_ID IN
                        (SELECT     MIN(VENDOR_ID) AS Expr2
FROM         VENDOR AS VENDOR_1
GROUP BY VendorName
HAVING      (COUNT(VendorName) > 1)))
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Vendor_Init_Load]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'









-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE FUNCTION [dbo].[fn_ITF_Vendor_Init_Load]
-- Loads Vendor of the tradename
-- The vendor is from VendorID taken from Interface Display_Purchase, when choosing single vendor
(	
	-- Add the parameters for the function here
@VENDOR_ID varchar (50)

--go
--select * from [fn_ITF_Vendor_Init_Load] (''9284'')
)
RETURNS TABLE 
AS
RETURN 
(
SELECT     VendorName, VendorNo, Adress, ZipCode, City, Country, Phone, Fax, Email, Website, FreeInfo, Status, UpdatedOn, UpdatedBy, VENDOR_ID
FROM         VENDOR
WHERE     (VENDOR_ID = @VENDOR_ID)
--union 
--SELECT     VendorName, VendorNo, Adress, ZipCode, City, Country, Phone, Fax, Email, Website, FreeInfo, Status, UpdatedOn, UpdatedBy, VENDOR_ID
--FROM         Vendor_MANUFACTURER
--WHERE     (VENDOR_ID = @VENDOR_ID)
)
--


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Igredients_Display_Purchase]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Igredients_Display_Purchase]
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@IngredCode varchar (50)

--go
--select * from [fn_ITF_Igredients_Display_Purchase] (''17460'')


)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT DISTINCT 
                      TRADENAME_MAIN.TradeName, Ingredient_Code.Name AS Ingredient_Id, VENDOR.VendorName, TRADENAME_MAIN.Cas_Number, 
                      TRADENAME_MAIN.MSDS, Ingredient_Code.ActualPreisePerKg, VENDOR.VENDOR_ID, Ingredient_Code.IngredientCode_ID, 
                      TRADENAME_MAIN.Tradename_Main_ID, _INTRF_IngredientCode_ID__Vendor_ID.VM, 
                      _INTRF_IngredientCode_ID__Vendor_ID.Tradename_Main_ID AS Expr1, _INTRF_IngredientCode_ID__Vendor_ID.id
FROM         Ingredient_Code INNER JOIN
                      _INTRF_IngredientCode_ID__Vendor_ID ON 
                      Ingredient_Code.IngredientCode_ID = _INTRF_IngredientCode_ID__Vendor_ID.IngredientCode_ID INNER JOIN
                      VENDOR ON _INTRF_IngredientCode_ID__Vendor_ID.VENDOR_ID = VENDOR.VENDOR_ID INNER JOIN
                      TRADENAME_MAIN ON _INTRF_IngredientCode_ID__Vendor_ID.Tradename_Main_ID = TRADENAME_MAIN.Tradename_Main_ID
WHERE     (Ingredient_Code.Name = @IngredCode)
)





' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into__INTRF_IngredientCode_ID__Vendor_ID_18]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'





CREATE  Proc [dbo].[Insert_Into__INTRF_IngredientCode_ID__Vendor_ID_18]
as


delete from _INTRF_IngredientCode_ID__Vendor_ID


INSERT INTO _INTRF_IngredientCode_ID__Vendor_ID
                      (IngredientCode_ID, VENDOR_ID, VM, Tradename_Main_ID)
SELECT     Ingredient_Code.IngredientCode_ID, VENDOR.VENDOR_ID, ''M'' AS VM, TRADENAME_MAIN.Tradename_Main_ID
FROM         VENDOR INNER JOIN
                      CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE COLLATE SQL_Latin1_General_CP1_CI_AS = Ingredient_Code.Name ON 
                      VENDOR.VendorName = CPIngred_1.PRODUCER INNER JOIN
                      TRADENAME_MAIN ON CPIngred_1.TRADENAME = TRADENAME_MAIN.TradeName

UNION
SELECT     Ingredient_Code.IngredientCode_ID, VENDOR.VENDOR_ID, ''S'' AS VM, TRADENAME_MAIN.Tradename_Main_ID
FROM         VENDOR INNER JOIN
                      CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE COLLATE SQL_Latin1_General_CP1_CI_AS = Ingredient_Code.Name ON 
                      VENDOR.VendorName = CPIngred_1.SUPPLIER INNER JOIN
                      TRADENAME_MAIN ON CPIngred_1.TRADENAME = TRADENAME_MAIN.TradeName
WHERE     (dbo.VENDOR.VendorName IS NOT NULL)





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Vendor_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



-- varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)varchar(50)
CREATE  procedure  [dbo].[prc_ITF_Vendor_Insert]
( 
 @vendorName varchar(50)
)
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''

as
INSERT INTO VENDOR
                      (VendorName)
VALUES     (@vendorName)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE  Proc [dbo].[Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19]
as
-- select * from [Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19]
delete from _INTRF_IngredientCode_ID__Tradename_Main_ID
INSERT INTO _INTRF_IngredientCode_ID__Tradename_Main_ID
                      (Tradename_Main_ID, IngredientCode_ID, UpdatedOn, UpdatedBy)
SELECT     TOP (100) PERCENT TRADENAME_MAIN.Tradename_Main_ID, Ingredient_Code.IngredientCode_ID, GETDATE() AS Expr2, ''SB'' AS Expr1
FROM         CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE COLLATE SQL_Latin1_General_CP1_CI_AS = Ingredient_Code.Name INNER JOIN
                      TRADENAME_MAIN ON CPIngred_1.TRADENAME COLLATE SQL_Latin1_General_CP1_CI_AS = TRADENAME_MAIN.TradeName
ORDER BY Ingredient_Code.Name


--GO
-- select * from [Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19]

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[INSERT_INTO_TRADENAME_MAIN_18]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

CREATE procedure [dbo].[INSERT_INTO_TRADENAME_MAIN_18]
as
delete from TRADENAME_MAIN
INSERT INTO TRADENAME_MAIN
                      (TradeName, Cas_Number, UpdatedBy, UpdatedOn)
SELECT DISTINCT TRADENAME, CASNO, NULL AS UPDATEDBY, NULL AS UPDATEDOn
--FROM    original     CPM.dbo.CPIngred
FROM         CPRECIPE...CPIngred
WHERE     (TRADENAME IS NOT NULL) AND (TRADENAME <> N'' _'')
ORDER BY UPDATEDOn

DELETE TOP (100) PERCENT  -- delete duplicates
FROM         TRADENAME_MAIN
WHERE     (Tradename_Main_ID IN
                          (SELECT     TOP (100) PERCENT MIN(Tradename_Main_ID) AS Expr2
                            FROM          TRADENAME_MAIN AS TRADENAME_MAIN_1
                            GROUP BY TradeName
                            HAVING      (COUNT(TradeName) > 1)
                            ORDER BY TradeName)) --AND (Cas_Number IS NULL)


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_1_O_W_User]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE  Procedure [dbo].[Recipe_Full_RZPT_1_O_W_User] 
	( -- PROCEDURE TO CALCULATE SUMMARIES from the DBtable with the name ''USER'' see ''Create_Recipe_Tempory_USER''!
----	declare @RS as int
----	exec @RS = [dbo].[Recipe_Full_RZPT_1_O_W_User] ''AB'',''72'',''SB''
----	select @RS as "Error"
  @MixerCode NVARCHAR(50) -- for definition of loading factor 
, @LoadFactor float -- not used for this procedure , can be dummy!
, @user   NVARCHAR(50) -- already created file with basic recipe
) 

as 


/* CREATE TEMPORY TABLE*/
IF OBJECT_ID(N''tempdb..#output'', N''U'') IS NOT NULL 
DROP TABLE #output;
Create   TABLE  #output( -- structure of SUmmary table
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
Recipe_Recipe_ID bigint,
RecipeID bigint
)
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(@user) AND type in (N''U''))
return 0 -- error code for: base table does not exists

 

begin try 
	exec (''INSERT INTO #output
	select * from ''+@user+'' where material is not NULL'')
end try
begin catch
	select ERROR_MESSAGE() as ''error ''
	return -15
end catch

BEGIN
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
/*
will check amount of recipes
*/
DECLARE	@return_value int
		begin try 
				DECLARE _cursor CURSOR FOR SELECT     COUNT(*) AS Expr1
				FROM     #output 
				open _cursor
				FETCH NEXT FROM _cursor INTO  @return_value 
				close _cursor
				deallocate _cursor
				if  @return_value = 0 -- no records in basic tables nothing to calculate 
			 	return 0 
					
		end try
		begin catch
				return -12
		end catch


-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated TOTAL values will be put into [PHR_recalc] and [weight_Recalc] output 1 raw table.


Declare  --TOTALS 
@phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare	-- for ingredients
@phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float
declare @REcipeId bigint
declare @REcipeLoadFactor float 
declare  @rECIPEnAME NVARCHAR(50), @Release NVARCHAR(50)
Declare @phase nvarchar(1), @Volume_PhaseM float 
/* CALCULATING TOTALS START */
DECLARe _crs_1 cursor FOR SELECT 
recipename, release,phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, phase   FROM  #output 

select
@phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0, @Volume_PhaseM=0
		open _crs_1
		FETCH NEXT from _crs_1 into
								 @rECIPEnAME,@Release, @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1,@phase
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume1
				if @phase=''M'' set @Volume_PhaseM=@Volume_PhaseM+@volume_recalc1 -- checking phase ''M''
			FETCH NEXT from _crs_1 into
									 @rECIPEnAME,@Release,@phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1,@phase
			end
		close _crs_1
				set @REcipeLoadFactor = 100* (@volume1T -@Volume_PhaseM) -- If there are phase M exclude it!
				/( SELECT     Volume FROM         Mixer_InfoBasic WHERE     (Code = @MixerCode) )
DEALLOCATE _crs_1
/* CALCULATING TOTALS FINISH */
declare @userPrim varchar (50)
/* CREATE OUTPUT USER1 TABLE */
declare @descr  nvarchar(50), @GRP  varchar(50), @code varchar(50)
begin try 
set @userPrim= @user
set @user=@user+''1''
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(@user) AND type in (N''U''))
exec (''DROP TABLE '' + @user)

exec (
''CREATE TABLE ''+ @user+''(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe_Recipe_ID] [bigint] NULL,
	[RecipeID] [bigint] NULL
) 
''
)
end try
begin catch
select ''-16'' as ''Error''
select ERROR_MESSAGE() as ''error''

	return -16 -- error undefined
end catch	

--select @rECIPEnAME as ''@rECIPEnAME''
--select @phr1T,@PHR_recalc1T,@weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,@PHR_recalc1T,@weight1T/@volume1T,@weight_recalc1T,@volume_recalc1T
declare @SQLString nvarchar(1000)
begin try

set @SQLString =		
 ''Insert into ''+ @user +'' (
RecipeName,Release,MatIndex,phr,PHR_recalc,weight,weight_recalc,volume,
volume_recalc, percRubber, density, density_recalc,id,descr)
values(''''''
+ cast(@rECIPEnAME as varchar) + '''''',''''''
+ cast (@Release as varchar) + '''''',''
+ ''''''R''''''+ '',''
+ cast (@phr1T  as varchar)+ '',''
+ cast (@PHR_recalc1T as varchar) + '',''
+ cast (@weight1T  as varchar)+ '',''
+ cast (@weight_recalc1T  as varchar)+ '',''
+ cast (@volume1T  as varchar)+ '',''
+ cast (@volume_recalc1T as varchar) + '','' 
+ cast (1/ @PHR_recalc1T *10000 as varchar) + '',''
+ cast (@weight1T / @volume1T as varchar) + '',''
+ cast (@weight_recalc1T/ @volume_recalc1T as varchar)
+ '', 99,'' 
+ cast (@REcipeLoadFactor as varchar) + '')''

end try
begin catch
select ''-5'' as ''Error''
select ERROR_MESSAGE() as ''errorA''
	return -5 -- error undefined
end catch	

--select @SQLString as ''@SQLString''
begin try
exec (@SQLString)
end try
begin catch
select ''-6'' as ''Error''
--select ERROR_MESSAGE() as ''errorA''
return -6 -- error undefined
end catch


IF OBJECT_ID(N''tempdb..#output'', N''U'') IS NOT NULL 
DROP TABLE #output
--select @MixerCode,@LoadFactor,@user
exec Recipe_Full_RZPT_1_O_W_User_Check   @MixerCode , @LoadFactor ,  @userPrim  
--select @MixerCode,@LoadFactor,@user

RETURN  0 
 
end



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Recipes_Z_A]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'






/*
MOD required 
add Customer ID to ZZZ tables!
*/

CREATE  Procedure [dbo].[prc_ITF_Recipes_Z_A]
(
/* Recipe Free info FILTER!
INPUT: 
Table ZZZ
OUTPUT:
Table ZZZ 
TEMPRORY:
TABLE ZZZ1 
TABLE ZZZ2 is Spare table copy of ZZZ (check this)
*/
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@customer NVARCHAR(50)
)

as

 

IF not  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
return 
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1


if @Color is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''color:'') AND (Recipe_Prop_Free_Info.NoteValue = @color)
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
end


if @industry is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''industry:'') AND (Recipe_Prop_Free_Info.NoteValue = @industry)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end



if @recept_type is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''recept type:'') AND (Recipe_Prop_Free_Info.NoteValue = @recept_type)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end

if @curing_system is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''curing system:'') AND (Recipe_Prop_Free_Info.NoteValue = @curing_system)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end

if @curing_process is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''curing process:'') AND (Recipe_Prop_Free_Info.NoteValue = @curing_process)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end

if @filler is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''filler:'') AND (Recipe_Prop_Free_Info.NoteValue = @filler)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end


if @certificate is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            ZZZ1
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''certificate:'') AND (Recipe_Prop_Free_Info.NoteValue = @certificate)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end


if @schelflife1  is not null AND @schelflife2  is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''schelflife(weeks):'' AND   Recipe_Prop_Free_Info.NoteValue between @schelflife1 and @schelflife2)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end

if @Hardnes_Sha1  is not null AND @Hardnes_Sha2  is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            [ZZZ1]
FROM         Recipe_Prop_Free_Info INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Info.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = ''Hardnes Sha:'' AND   Recipe_Prop_Free_Info.NoteValue between @Hardnes_Sha1 and @Hardnes_Sha2)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end


if @customer is not null
begin 
SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy
INTO            ZZZ1
FROM         Recipe_Prop_Free_Text INNER JOIN
                      ZZZ ON Recipe_Prop_Free_Text.Recipe_ID = ZZZ.Recipe_ID
WHERE     (Recipe_Prop_Free_Text.NoteName = ''customer'') AND (Recipe_Prop_Free_Text.NoteValue = @customer)

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
SELECT     *  INTO   zzz  FROM         [ZZZ1]
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1
--go

end



IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ2'') AND type in (N''U''))
DROP TABLE ZZZ2

SELECT     ZZZ.Recipe_ID, ZZZ.Recipe_Origin, ZZZ.Recipe_Additional, ZZZ.[Recipe Version], ZZZ.[Recipe Stage], ZZZ.Detailed_Group, ZZZ.Status, ZZZ.Class, 
                      ZZZ.Mixer_Code, ZZZ.Descr, ZZZ.Loadfactor, ZZZ.MixTime, ZZZ.UpdatedOn, ZZZ.UpdatedBy, Recipe_Prop_Free_Info.NoteName, 
                      Recipe_Prop_Free_Info.NoteValue
into ZZZ2
FROM         ZZZ INNER JOIN
                      Recipe_Prop_Free_Info ON ZZZ.Recipe_ID = Recipe_Prop_Free_Info.Recipe_ID












' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_PropFree_Info_fullList]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'





-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE   FUNCTION [dbo].[fn_ITF_Recipes_PropFree_Info_fullList]
--go 
--SELECT     NoteValue
--FROM         dbo.fn_ITF_Recipes_PropFree_Info_fullList(''Color:'') AS fn_ITF_Recipes_PropFree_Info_fullList_1
--ORDER BY NoteValue
(	
@notename nvarchar(50)
)
RETURNS TABLE 
AS
RETURN 
(
SELECT DISTINCT TOP (100) PERCENT NoteValue
FROM         Recipe_Prop_Free_Info
WHERE     (NoteName = @notename)
ORDER BY NoteValue
)

--go 
--SELECT     NoteValue
--FROM         dbo.fn_ITF_Recipes_PropFree_Info_fullList(''Color:'') AS fn_ITF_Recipes_PropFree_Info_fullList_1
--ORDER BY NoteValue


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_RECIPE_FreeInfo_Update]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'










/*
Procedure ADDS record with FreeInfo
--exec [prc_ITF_RECIPE_FreeInfo_Update] ''@Recipe_ID'',''@NoteName'',''@NoteValue'',''@UpdatedOn'',''@UpdatedBy''

*/


CREATE   procedure  [dbo].[prc_ITF_RECIPE_FreeInfo_Update]
( 
@Recipe_ID bigint,  --current recipe 
@NoteName nvarchar(50), -- current note name must be empty! 
@NoteValue nvarchar(50), -- input value 
@UpdatedOn smalldatetime,
@UpdatedBy nvarchar(50)
)
as
declare @FL  nvarchar(50) 


-- check if exists NoteName for current recipe
set @FL = (SELECT     NoteName FROM         Recipe_Prop_Free_Info
WHERE     (Recipe_ID = @Recipe_ID) AND (NoteName = @NoteName))

--select @FL as [@FL] 
if @FL is NULL  -- NoteName for current recipe NOT EXISTS
begin 

INSERT      TOP (100) PERCENT
INTO            Recipe_Prop_Free_Info(Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
VALUES     (@Recipe_ID,@NoteName,@NoteValue,@UpdatedOn,@UpdatedBy)
end 

--else
--begin
--UPDATE    TOP (100) PERCENT Recipe_Prop_Free_Info
--SET              NoteValue = @NoteValue, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy
--WHERE     (Recipe_ID = @Recipe_ID) AND (NoteName = @NoteName)
--END
--return
--go 
--exec [prc_ITF_RECIPE_FreeInfo_Update] ''@Recipe_ID'',''@NoteName'',''@NoteValue'',''@UpdatedOn'',''@UpdatedBy''










' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_RECIPE_FreeInfo_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'






/*
this is  for useer interface table sequence 
*/


create   procedure  [dbo].[prc_ITF_RECIPE_FreeInfo_Insert]
( 
--@Recipe_Prop_Free_TEXT_ID bigint, -- can be NULL if no records for FreeText for current @Recipe_ID, or the Recipe_Prop_Free_TEXT_ID
@Recipe_ID bigint,  --current recipe , must exists,
@NoteName nvarchar(50),
@NoteValue nvarchar(200),
@UpdatedOn smalldatetime,
@UpdatedBy nvarchar(50)
)
as


-- check if exists NoteName for current recipe

--select @FL as [@FL] 
INSERT      TOP (100) PERCENT
INTO            Recipe_Prop_Free_Info(Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
VALUES     (@Recipe_ID,@NoteName,@NoteValue,@UpdatedOn,@UpdatedBy)
	

return

--go 
--execute [prc_ITF_RECIPE_FreeTEXT_Insert]  530172,@UpdatedOn,@UpdatedBy






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_1_O]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_1_O] 
( 
    @MixerCode NVARCHAR(50), @LoadFactor float
) 
--go 
--select * from [dbo].[Recipe_Full_RZPT_1_O] (''01-0-1432'',''0'')
-- gets recipe from recipe.temp !!! parameters not relevant! , use [Recipe_Full_RZPT_1_O]('''','''')
-- calculates the total string
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
Recipe_Recipe_ID bigint,
RecipeID bigint
)
--select * from [dbo].[Recipe_Full_RZPT_1_O] (''01-0-1432'',''0'')
--go 
--select * from [dbo].[Recipe_Full_RZPT_1_O] (''01-0-1432'',''0'')
BEGIN
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 

DECLARE	@return_value int
set @return_value =[dbo].[fnBasic_RZPT_REcordsInTemp]( )
if  @return_value = 0 
	begin
		return
	end

-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]

INSERT INTO @output 
select * from Recipe_Tempory where material is not NULL

--Calculating Total last raw -Summary in the table

Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float
declare @REcipeId bigint
declare @REcipeLoadFactor float 
declare  @rECIPEnAME NVARCHAR(50), @Release NVARCHAR(50)

DECLARe _crs_1 cursor FOR SELECT recipename, release,phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  @output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @rECIPEnAME,@Release, @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1
			FETCH NEXT from _crs_1 into @rECIPEnAME,@Release,@phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
				set @REcipeLoadFactor = 100* @volume1T /( SELECT     Volume FROM         Mixer_InfoBasic WHERE     (Code = @MixerCode) )
DEALLOCATE _crs_1
declare @descr  nvarchar(50), @GRP  varchar(50), @code varchar(50)

DECLARe _crs_1 cursor FOR SELECT Material, matindex, descr, GRP  FROM  @output 
		open _crs_1
		FETCH NEXT from _crs_1 into @Material,@Matindex, @descr,@GRP
		WHILE @@FETCH_STATUS =0 
		begin
			if @matindex =''R''
			begin
				set @descr =(SELECT      Descr
				FROM         Recipe_Prop_Main
				WHERE     (Code = @Material) AND (Release = ''0''))
				set @GRP =(SELECT     Detailed_Group
				FROM         Recipe_Prop_Main
				WHERE     (Code = @Material) AND (Release = ''0''))
				UPDATE @OUTPUT  set descr = @descr, GRP=@GRP WHERE CURRENT OF _crs_1;
			end
		FETCH NEXT from _crs_1 into @Material,@Matindex, @descr,@GRP
		end
		close _crs_1
		--DEALLOCATE _crs_1


		Insert into  @OUTPUT 
				(RecipeName ,Release,MatIndex,phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc,id,material,descr)
				values(@rECIPEnAME,@Release,''R'', @phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,
				@weight_recalc1T/@volume_recalc1T,99,@rECIPEnAME,  cast ( @REcipeLoadFactor as  varchar  )  ) 
		DEALLOCATE _crs_1


-- begin 1, density_recalc

RETURN   

end


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_REcipeVolume_1_O]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'


CREATE  FUNCTION [dbo].[Recipe_REcipeVolume_1_O] 
( 
) 
--go 
--declare @Amount1 real
--set @Amount1 = [dbo].[Recipe_REcipeVolume_1_O] ()
--select @Amount1
RETURNS real 
as	

BEGIN
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 

DECLARE	@return_value int
set @return_value =[dbo].[fnBasic_RZPT_REcordsInTemp]( )
if  @return_value = 0 
	begin
		return -1
	end

-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]


--Calculating Total last raw -Summary in the table

Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T real
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float
declare @REcipeId bigint
declare  @rECIPEnAME NVARCHAR(50), @Release NVARCHAR(50)

DECLARe _crs_1 cursor FOR SELECT recipename, release,phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  Recipe_Tempory 

select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @rECIPEnAME,@Release, @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
--				set @phr1T= @phr1+		@phr1T						-- Total phr
--				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
--				set @weight1T=@weight1T+@weight1
--				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1
			FETCH NEXT from _crs_1 into @rECIPEnAME,@Release,@phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1


		DEALLOCATE _crs_1


-- begin 1, density_recalc

RETURN   @volume_recalc1T

end

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_FillFactor_1_O]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



CREATE  FUNCTION [dbo].[Recipe_FillFactor_1_O] 
( 
    @MixerCode NVARCHAR(50), @LoadFactorNew float
) 

RETURNS float 
as	

BEGIN
--declare @material varchar(50)
--DECLARE @matIndex VARCHAR(1)
--declare @weight float 

DECLARE	@return_value int
set @return_value =[dbo].[fnBasic_RZPT_REcordsInTemp]( )
if  @return_value = 0 
	begin
		return -1
	end

-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]


--Calculating Total last raw -Summary in the table

Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float
Declare @volume_recalc1T float,@volume_recalc1 float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float 
declare @REcipeId bigint
declare @REcipeLoadFactor float 
declare  @rECIPEnAME NVARCHAR(50), @Release NVARCHAR(50)

DECLARe _crs_1 cursor FOR SELECT 
recipename, release,phr,PHR_recalc,weight,weight_recalc,volume,
volume_recalc   FROM  Recipe_Tempory 

select  
@phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, 
@volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into 
@rECIPEnAME,@Release, @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,
@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1
			FETCH NEXT from _crs_1 into 
@rECIPEnAME,@Release,@phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,
@volume_recalc1
			end
		close _crs_1
				set @REcipeLoadFactor = 100* @volume_recalc1T /( SELECT     Volume FROM         Mixer_InfoBasic WHERE     (Code = @MixerCode) )


		DEALLOCATE _crs_1


-- begin 1, density_recalc

RETURN   @REcipeLoadFactor

end

--
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_Recipe_Ingredient_InsertOLD]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_Recipe_Ingredient_InsertOLD]
(	
	-- Add the parameters for the function here
@name NVARCHAR(50)		--''00-0-0012''

)
RETURNS TABLE 
AS
RETURN 
(
SELECT     Ingredient_Code.Name, Ingredient_Warehouse.SiloId, Ingredient_phys_Properties.percRubber, Ingredient_phys_Properties.density, 
                      Ingredient_Warehouse.BalanceID, Ingred_Preise.PreisePerKg AS PriceKG, Ingred_Preise.Prisedate AS PriceData, Ingredient_Code.Descr, 
                      Ingredient_Code.[Group] AS GRP
FROM         Ingredient_Code LEFT OUTER JOIN
                      Ingredient_phys_Properties ON Ingredient_Code.IngredientCode_ID = Ingredient_phys_Properties.IngredientCode_ID LEFT OUTER JOIN
                      Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID LEFT OUTER JOIN
                      Ingred_Preise ON Ingredient_Code.IngredientCode_ID = Ingred_Preise.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @name)

--UNION 
--SELECT     RecipeID AS Name, ''1'' AS SiloId, percRubber, density, ''1'' AS balanceId, Price AS PricKg, NULL AS PriceData, Descr, GRP
--FROM         dbo.Recipe_FINAL_RZPT(@name, ''0'') AS Recipe_FINAL_RZPT_1
)
--go
--select * from [fn_Recipe_Ingredient_Insert](''01246'')



' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Igredients_Init_Load_Warehause]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'









-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE FUNCTION [dbo].[fn_ITF_Igredients_Init_Load_Warehause]
-- Seek engine for Java interfact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@IngredCode_ID varchar (50)

-- select * from [fn_ITF_Recipes_Init] (NULL,''0'',Null,Null,Null,Null,Null,Null)ORDER BY code

)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     Ingredient_Warehouse.SiloId, Ingredient_Warehouse.BalanceID, Ingredient_Warehouse.LotNO, Ingredient_Warehouse.BOxNo, 
                      Ingredient_Warehouse.SchelfLife, Ingredient_Warehouse.Location, Ingredient_Warehouse.Storetime, Ingredient_Warehouse.SttempMin, 
                      Ingredient_Warehouse.StTempMax, Ingredient_Warehouse.StoreDate, Ingredient_Warehouse.STDATEEXT, Ingredient_Warehouse.ActStock, 
                      Ingredient_Warehouse.MinStock, Ingredient_Warehouse.LastUsed, Ingredient_Warehouse.UsageDate, Ingredient_Warehouse.StupDate, 
                      Ingredient_Warehouse.Form, Ingredient_Warehouse.Quantity, Ingredient_Warehouse.QuantUnit, Ingredient_Warehouse.[ORDER], 
                      Ingredient_Warehouse.OrderUnit, Ingredient_Warehouse.SWFINE, Ingredient_Warehouse.SWVeryFine, Ingredient_Warehouse.StopSignal, 
                      Ingredient_Warehouse.Tolerance, Ingredient_Warehouse.FreeInfo, Ingredient_Warehouse.UpdatedOn, Ingredient_Warehouse.UpdatedBy
FROM         Ingredient_Warehouse INNER JOIN
                      Ingredient_Code ON Ingredient_Warehouse.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @IngredCode_ID)
)
--


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_Recipe_Ingredient_Insert]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'



-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_Recipe_Ingredient_Insert]
(	
	-- Add the parameters for the function here
@name NVARCHAR(50)		--''00-0-0012''

)
RETURNS @output TABLE 

(
[Name] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [smalldatetime] NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
)


begin
if  len(rtrim(ltrim (@name))) = 5
begin
	insert into @output
	SELECT     Ingredient_Code.Name, Ingredient_Warehouse.SiloId, Ingredient_phys_Properties.percRubber, Ingredient_phys_Properties.density, 
						  Ingredient_Warehouse.BalanceID, Ingred_Preise.PreisePerKg AS PriceKG, Ingred_Preise.Prisedate AS PriceData, Ingredient_Code.Descr, 
						  Ingredient_Code.[Group] AS GRP 
	FROM         Ingredient_Code LEFT OUTER JOIN
						  Ingredient_phys_Properties ON Ingredient_Code.IngredientCode_ID = Ingredient_phys_Properties.IngredientCode_ID LEFT OUTER JOIN
						  Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID LEFT OUTER JOIN
						  Ingred_Preise ON Ingredient_Code.IngredientCode_ID = Ingred_Preise.IngredientCode_ID
	WHERE     (Ingredient_Code.Name = @name)
end
else
begin
	insert into @output
	SELECT     RecipeID AS Name, ''1'' AS SiloId, percRubber, density, ''1'' AS balanceId, Price AS PricKg, NULL AS PriceData, Descr, GRP
	FROM        dbo.Recipe_FINAL_RZPT(@name, ''0'') AS Recipe_FINAL_RZPT_1
end

return 
end



--UNION 
--SELECT     RecipeID AS Name, ''1'' AS SiloId, percRubber, density, ''1'' AS balanceId, Price AS PricKg, NULL AS PriceData, Descr, GRP
--FROM         dbo.Recipe_FINAL_RZPT(@name, ''0'') AS Recipe_FINAL_RZPT_1

--go
--select * from [fn_Recipe_Ingredient_Insert1](''00-0-0012'')




' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_FreeInfo_Select]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE  procedure  [dbo].[prc_ITF_Ingredients_FreeInfo_Select]
( 
 @Name varchar(50)
)


as
SELECT     Ingredient_Code.Name, Ingred_Free_Info.IngredientCode_ID, Ingred_Free_Info.NoteName, Ingred_Free_Info.NoteValue, 
                      Ingred_Free_Info.Ingred_Free_Info_ID, Ingred_Free_Info.UpdatedOn, Ingred_Free_Info.UpdatedBy
FROM         Ingredient_Code INNER JOIN
                      Ingred_Free_Info ON Ingredient_Code.IngredientCode_ID = Ingred_Free_Info.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @Name)
ORDER BY Ingredient_Code.Name





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredient_NEW_fromScratch]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'







CREATE  procedure  [dbo].[prc_ITF_Ingredient_NEW_fromScratch]
(  @IngredientCode_ID bigint , -- not used !
@CreatedOn smalldatetime,
@CreatedBy varchar(50))
/*
create the new INGREDIENT with the name "NEW" 
by reference from other
*/
as



DELETE FROM Ingredient_Code
WHERE     (name = ''NEW'')


INSERT INTO Ingredient_Code
                      ([Name], [Group], UpdatedBy, UpdatedOn, CreatedBy, CreatedOn)
VALUES     (''NEW'', ''AAA'',@CreatedBy,@CreatedOn,@CreatedBy,@CreatedOn)  -- name and parent table  value to avoid key problem


--print @NEW_Recipr_ID
DECLARE @NEW_IngredientCode_ID    bigint 
set @NEW_IngredientCode_ID =@@identity
print ''@NEW_IngredientCode_ID:''+ cast (@NEW_IngredientCode_ID as varchar)



INSERT INTO Ingredient_phys_Properties
                      (UpdatedOn, UpdatedBy, IngredientCode_ID)
VALUES     (@CreatedOn,@CreatedBy,@NEW_IngredientCode_ID)







INSERT INTO Ingredient_Warehouse
                      (UpdatedOn, UpdatedBy, IngredientCode_ID)
VALUES     (@CreatedOn,@CreatedBy,@NEW_IngredientCode_ID)




INSERT INTO Ingred_Comments
                      (UpdatedOn, UpdatedBy, IngredientCode_ID)
VALUES     (@CreatedOn,@CreatedBy,@NEW_IngredientCode_ID)





INSERT INTO Ingred_Preise
                      (UpdatedOn, UpdatedBy, IngredientCode_ID)
VALUES     (@CreatedOn,@CreatedBy,@NEW_IngredientCode_ID)




--INSERT INTO Ingred_Safety_Main
--                      (UpdatedOn, UpdatedBy, IngredientCode_ID)
--VALUES     (@CreatedOn,@CreatedBy,@NEW_IngredientCode_ID)





--INSERT INTO _INTRF_IngredientCode_ID__Vendor_ID
--                      (UpdatedOn, UpdatedBy, IngredientCode_ID, VM)
--VALUES     (@CreatedOn,@CreatedBy,@NEW_IngredientCode_ID,''M'')


return 0
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Warehause_Select]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_Warehause_Select]
(	@name varchar (50)
)


AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     Ingredient_Warehouse.SiloId, Ingredient_Warehouse.BalanceID, Ingredient_Warehouse.LotNO, Ingredient_Warehouse.BOxNo, 
                      Ingredient_Warehouse.SchelfLife, Ingredient_Warehouse.Location, Ingredient_Warehouse.Storetime, Ingredient_Warehouse.SttempMin, 
                      Ingredient_Warehouse.StTempMax, Ingredient_Warehouse.StoreDate, Ingredient_Warehouse.STDATEEXT, Ingredient_Warehouse.ActStock, 
                      Ingredient_Warehouse.MinStock, Ingredient_Warehouse.LastUsed, Ingredient_Warehouse.UsageDate, Ingredient_Warehouse.StupDate, 
                      Ingredient_Warehouse.Form, Ingredient_Warehouse.Quantity, Ingredient_Warehouse.QuantUnit, Ingredient_Warehouse.[ORDER], 
                      Ingredient_Warehouse.OrderUnit, Ingredient_Warehouse.SWFINE, Ingredient_Warehouse.SWVeryFine, Ingredient_Warehouse.StopSignal, 
                      Ingredient_Warehouse.Tolerance, Ingredient_Warehouse.FreeInfo, Ingredient_Warehouse.UpdatedOn, Ingredient_Warehouse.UpdatedBy, 
                      Ingredient_Warehouse.IngredientCode_ID, Ingredient_Warehouse.Ingredient_Warehouse_ID
FROM         Ingredient_Warehouse INNER JOIN
                      Ingredient_Code ON Ingredient_Warehouse.IngredientCode_ID = Ingredient_Code.IngredientCode_ID
WHERE     (Ingredient_Code.Name = @name)
--
--go
--select *   from [fn_ITF_Igredients_Init_Load_Warehause] (''17543'')







' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Sequence_Init_SelectUnic]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'








CREATE  FUNCTION [dbo].[fn_ITF_Sequence_Init_SelectUnic]
/*
Selects single recipe with fixed CODE and RELEASE to fill the user inerface table

*/
(	
	-- Add the parameters for the function here
@Code varchar (50),
@Release varchar (50),
@Mixer_Code varchar (50)

--go 
--
--SELECT     Code, Release, Step_NB, Command_Name, Command_Param, Mixer_Code, Info, UpdatedOn, UpdatedBy, Recipe_Sequence_Commands, 
--                      Recipe_Sequence_Main_ID, Recipe_Sequence_Steps_ID
--FROM         dbo.fn_ITF_Sequence_Init_SelectUnic(''01-8-1432'', ''0'') 

)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     TOP (100) PERCENT Recipe_Sequence_Main.Code, Recipe_Sequence_Main.Release, Recipe_Sequence_Main.Mixer_Code, 
                      CAST(Recipe_Sequence_Steps.Step_NB AS int) AS Step_Nb, Recipe_Sequence_Commands.Command_Name, 
                      Recipe_Sequence_Steps.Command_Param, Recipe_Sequence_Main.Info, Recipe_Sequence_Main.UpdatedOn, Recipe_Sequence_Main.UpdatedBy, 
                      Recipe_Sequence_Steps.Recipe_Sequence_Steps_ID, Recipe_Sequence_Steps.Recipe_Sequence_Commands, 
                      Recipe_Sequence_Main.Recipe_Sequence_Main_ID
FROM         Recipe_Sequence_Commands INNER JOIN
                      Recipe_Sequence_Steps ON 
                      Recipe_Sequence_Commands.Recipe_Sequence_Commands = Recipe_Sequence_Steps.Recipe_Sequence_Commands INNER JOIN
                      Recipe_Sequence_Main ON Recipe_Sequence_Steps.Recipe_Sequence_Main_ID = Recipe_Sequence_Main.Recipe_Sequence_Main_ID
WHERE     (Recipe_Sequence_Main.Code = @Code) AND (Recipe_Sequence_Main.Release = @Release) AND 
                      (Recipe_Sequence_Main.Mixer_Code = @Mixer_Code)
ORDER BY Recipe_Sequence_Main.Code, Recipe_Sequence_Main.Release
)

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Sequence_Main_Get]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Sequence_Main_Get]
(	
--go 
--select * from [fn_ITF_Sequence_Main_Get](''00-0-0006'',''0'',''G1'')
	-- Add the parameters for the function here
    @rECIPEnAME NVARCHAR(50),
	 @Rrelease NVARCHAR(50),
	@Mixer_Code NVARCHAR(50)

)
RETURNS TABLE 
AS
RETURN 
(
SELECT     TOP (100) PERCENT Code, Release, Mixer_Code, Info, Status, LEFT(CONVERT(VARCHAR(19),UpdatedOn,126),10) as UpdatedOn, UpdatedBy
FROM         Recipe_Sequence_Main
WHERE     (Code = @rECIPEnAME) AND (Release = @Rrelease) AND (Mixer_Code = @Mixer_Code)

)
--go 
--select * from [fn_ITF_Sequence_Main_Get](''00-0-0006'',''0'',''G1'')

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Sequence_Init_SelectSet_2]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'

















-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Sequence_Init_SelectSet_2]
/*
selects the set of recipes according to choosen code and release.
If code is NULL and release is NULL all possible sets are selected
IF code is ''XX-X-XXXX''chosen and Release is NULL the set with for code ''XX-X-XXXX'' with all possible release selected
IF code is NULLchosen and Release is ''X'' the set with for all possible codes  which have relese ''X'' selected
this function used for user interface list boxes  engine: 
for Code list box : fn_ITF_Sequence_Init_SelectSet_2(''NULL'',RElease_Listbox_Value,, Mixer_Code_ListBox)
for Release list box : fn_ITF_Sequence_Init_SelectSet_2(Code_Listbox_Value,''NULL'', Mixer_Code_ListBox)
for Mixer_Code list box : fn_ITF_Sequence_Init_SelectSet_2(Code_Listbox_Value,RElease_Listbox_Value, NULL)

*/
(	
	-- Add the parameters for the function here
@Code varchar (50),
@Release varchar (50),
@Mixer_Code varchar (50)

)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
SELECT     Recipe_Sequence_Main.Code, Recipe_Sequence_Main.Release, Recipe_Sequence_Main.Mixer_Code, Mixer_InfoBasic.Name
FROM         Recipe_Sequence_Main INNER JOIN
                      Mixer_InfoBasic ON Recipe_Sequence_Main.Mixer_Code = Mixer_InfoBasic.Code
WHERE     (@Code IS NULL OR
                      Recipe_Sequence_Main.Code = @Code) AND (@Release IS NULL OR
                      Recipe_Sequence_Main.Release = @Release) AND (@Mixer_Code IS NULL OR
                      Recipe_Sequence_Main.Mixer_Code = @Mixer_Code)
)


' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_FillFactor_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'





CREATE  procedure  [dbo].[Recipe_Tempory_Recalc_FillFactor_USER]
( 
  @recipe VARCHAR(50),
@release VARCHAR(50)=''0'',
@NewFillFactor VARCHAR(50)=''0'',
@MixerId VARCHAR(50)=''AB'',
 @user   NVARCHAR(50)
)
as 

Create   TABLE  #output2(
FF float, -- fill factor
V_Recalc float 	--old volume
)
exec (''
INSERT INTO   #output2 (FF, V_Recalc)
	SELECT     Descr,  volume_Recalc FROM '' + @user+''1'')

declare @FF float, @V_Recalc float
declare @Nff float
set @FF = (select FF from #output2)
set @V_Recalc =  (SELECT     V_Recalc FROM #output2)
--select @FF as ''@FF'', @V_Recalc as ''@V_Recalc''
set @Nff=@NewFillFactor
  -- Here we recalculate the ingredients, see instructions for Reverce
if @Nff > 0 --AND  @NewFillFactor <> @FF 
begin try 
	declare @res int 
	declare @New_Weight float
	set @New_Weight = @Nff/@FF*@V_Recalc
	select @New_Weight as ''@New_Weight''
	exec @res = [dbo].[Recipe_Tempory_Recalc_givenPHRS_USER]     @recipe , @release ,   @user 
	exec @res = [dbo].[Recipe_Tempory_Recalc_newTotalVolume_USER]     @recipe , @release ,  @New_Weight , @user  
end TRY
begin catch
return -1
end catch

RETURN   




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_FreeInfo_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



create  procedure  [dbo].[prc_ITF_Ingredients_FreeInfo_Insert]
( 
@IngredientCode_ID bigint,
@NoteName nvarchar(50),
@NoteValue nvarchar(50),
@UpdatedOn smalldatetime,
@UpdatedBy nvarchar(50)
)


as
INSERT INTO Ingred_Free_Info
                      (IngredientCode_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
VALUES     (@IngredientCode_ID,@NoteName,@NoteValue,@UpdatedOn,@UpdatedBy)





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_FreeInfo_Update]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE  procedure  [dbo].[prc_ITF_Ingredients_FreeInfo_Update]
( 
 @Ingred_Free_Info_ID bigint,
@IngredientCode_ID bigint,
@NoteName varchar(50),
@NoteValue varchar(50),
@UpdatedOn varchar(50),
@UpdatedBy varchar(50)
)


as
UPDATE    Ingred_Free_Info
SET              NoteValue = @NoteValue, UpdatedOn = @UpdatedOn, UpdatedBy = @UpdatedBy
WHERE     (Ingred_Free_Info_ID = @Ingred_Free_Info_ID) 




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_RECIPE_FreeTEXT_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'





/*
this is  for useer interface table sequence 
*/


CREATE   procedure  [dbo].[prc_ITF_RECIPE_FreeTEXT_Insert]
( 
--@Recipe_Prop_Free_TEXT_ID bigint, -- can be NULL if no records for FreeText for current @Recipe_ID, or the Recipe_Prop_Free_TEXT_ID
@Recipe_ID bigint,  --current recipe , must exists,
@NoteName nvarchar(50),
@NoteValue nvarchar(200),
@UpdatedOn smalldatetime,
@UpdatedBy nvarchar(50)
)
as


-- check if exists NoteName for current recipe

--select @FL as [@FL] 
INSERT      TOP (100) PERCENT
INTO            Recipe_Prop_Free_Text(Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
VALUES     (@Recipe_ID,@NoteName,@NoteValue,@UpdatedOn,@UpdatedBy)
	

return

--go 
--execute [prc_ITF_RECIPE_FreeTEXT_Insert]  530172,@UpdatedOn,@UpdatedBy





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[User_Int_RecipeInvert]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'




CREATE  FUNCTION [dbo].[User_Int_RecipeInvert] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @Mix_Code NVARCHAR(50)
) 
-- Function to perform th einput for Recipe Invereted main table it take data from 
-- Recipe_Prop_Main   plus field Customer which filled from Recipe_Free_Text
-- select * from [User_Int_RecipeInvert] (''00-0-0002'',''0'',''GK'')

RETURNS @output TABLE
(
		[Code] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Status] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Class] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Detailed_Group] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Mixer_Code] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Name] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Loadfactor] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MixTime] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[UpdatedOn] [varchar](50) NULL,
	[UpdatedBy] [char](10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[NoteValue] [text] COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Recipe_ID] [bigint],
	[Recipe_Prop_Free_Text_ID] [bigint],
	[CreatedOn] [varchar](50) NULL,
	[CreatedBy] [char](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
PriceKg [varchar](50) NULL,
PriceL [varchar](50) NULL
)
BEGIN
  DECLARE @COUNT INT
SET @COUNT=1
-- step one : select data from Recipe_Prop_Main and inserts it to Output table
INSERT INTO @output
SELECT     TOP (100) PERCENT Recipe_Prop_Main.Code, Recipe_Prop_Main.Release, Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, 
                      Recipe_Prop_Main.Detailed_Group, Recipe_Prop_Main.Mixer_Code, Mixer_InfoBasic.Name, Recipe_Prop_Main.Loadfactor, 
                      Recipe_Prop_Main.MixTime, LEFT(CONVERT(VARCHAR(19), Recipe_Prop_Main.UpdatedOn, 126), 10) AS UpdatedOn, Recipe_Prop_Main.UpdatedBy, 
                      Recipe_Prop_Main.Descr, '''' AS NoteValue, Recipe_Prop_Main.Recipe_ID, '''' AS Recipe_Prop_Free_Text_ID, LEFT(CONVERT(VARCHAR(19), 
                      Recipe_Prop_Main.CreatedOn, 126), 10) AS CreatedOn, Recipe_Prop_Main.CreatedBy, Recipe_Prop_Main.PriceKG, Recipe_Prop_Main.PriceL
FROM         Recipe_Prop_Main INNER JOIN
                      Mixer_InfoBasic ON Recipe_Prop_Main.Mixer_Code = Mixer_InfoBasic.Code
WHERE     (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Prop_Main.Release = @Rrelease) AND (Recipe_Prop_Main.Mixer_Code = @Mix_Code)

-- step 2 : gets  data from Recipe_Prop_Free_Text for customer 

declare @customer NVARCHAR(50)
declare @Recipe_Free_Text_ID bigint

DECLARE _cursor CURSOR FOR  
SELECT     TOP (100) PERCENT Recipe_Prop_Free_Text.NoteValue AS Customer, Recipe_Prop_Free_Text.Recipe_Prop_Free_Text_ID
FROM         Recipe_Prop_Main LEFT OUTER JOIN
                      Recipe_Prop_Free_Text ON Recipe_Prop_Main.Recipe_ID = Recipe_Prop_Free_Text.Recipe_ID
WHERE     (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Prop_Main.Release = @Rrelease) AND (Recipe_Prop_Main.Mixer_Code = @Mix_Code) AND 
                      (Recipe_Prop_Free_Text.NoteName = ''Customer'')

open _cursor

-- step 3 : If Customer exists from   from Recipe_Prop_Free_Text upgrade Customer field from Output table

FETCH NEXT FROM _cursor INTO @customer,@Recipe_Free_Text_ID
--WHILE @@FETCH_STATUS <> -1 
	--begin 
		IF @customer IS NOT NULL UPDATE    @output SET NoteValue = @customer
		IF @Recipe_Free_Text_ID IS NOT NULL UPDATE    @output SET Recipe_Prop_Free_Text_ID = @Recipe_Free_Text_ID
	--end 
	close _cursor
	DEALLOCATE _cursor
return
end 
--test only
 --go 
--select * from [User_Int_RecipeInvert] (''00-0-0002'',''0'',''GK'')





' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[INSERT_INTO_Ingred_Preise_14]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE PROCEDURE [dbo].[INSERT_INTO_Ingred_Preise_14] as

BEGIN
	-- IngredientCode table should be fileld already
	-- interfering with SELECT statements.
	-- SET NOCOUNT ON;
delete from Ingred_Preise
CREATE TABLE #Ingred_Preise(
	[Ingred_Preise_ID] [bigint] NULL,
	[IngredientCode_ID] [bigint] NULL,
	[PreisePerKg] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PreisePerLiter] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Prisedate] [smalldatetime] NULL,
	[UpdatedOn] [smalldatetime] NULL,
	[UpdatedBy] [char](10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) ON [PRIMARY]


/*INSERT INTO #Ingred_Preise
                      (UpdatedOn, UpdatedBy, PreisePerKg, Prisedate, Ingred_Preise_ID)
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPM.dbo.CPIngred.PRICEKG1 AS Price, CPM.dbo.CPIngred.PRICEDATE1 AS priceDate, 
                      Ingredient_Code.IngredientCode_ID
FROM         CPM.dbo.CPIngred INNER JOIN
                      Ingredient_Code ON CPM.dbo.CPIngred.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
UNION
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPM.dbo.CPIngred.PRICEKG2 AS Price, CPM.dbo.CPIngred.PRICEDATE2 AS priceDate, 
                      Ingredient_Code.IngredientCode_ID
FROM         CPM.dbo.CPIngred INNER JOIN
                      Ingredient_Code ON CPM.dbo.CPIngred.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
UNION
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPM.dbo.CPIngred.PRICEKG3 AS Price, CPM.dbo.CPIngred.PRICEDATE3 AS priceDate, 
                      Ingredient_Code.IngredientCode_ID
FROM         CPM.dbo.CPIngred INNER JOIN
                      Ingredient_Code ON CPM.dbo.CPIngred.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
UNION
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPM.dbo.CPIngred.PRICEKG4 AS Price, CPM.dbo.CPIngred.PRICEDATE4 AS priceDate, 
                      Ingredient_Code.IngredientCode_ID
FROM         CPM.dbo.CPIngred INNER JOIN
                      Ingredient_Code ON CPM.dbo.CPIngred.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS

INSERT INTO Ingred_Preise ( UpdatedOn,  UpdatedBy, PreisePerKg, 
                      Prisedate)

select distinct  GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, PreisePerKg, 
                     Prisedate from #Ingred_Preise
                     */
INSERT INTO #Ingred_Preise
                      (UpdatedOn, UpdatedBy, PreisePerKg, Prisedate, Ingred_Preise_ID)
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPIngred_1.PRICEKG1 AS Price, CPIngred_1.PRICEDATE1 AS priceDate, Ingredient_Code.IngredientCode_ID
FROM         CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
UNION
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPIngredINNER_1.PRICEKG2 AS Price, CPIngredINNER_1.PRICEDATE2 AS priceDate, 
                      Ingredient_Code.IngredientCode_ID
FROM         CPRECIPE...CPIngredINNER AS CPIngredINNER_1 INNER JOIN
                      Ingredient_Code ON CPIngredINNER_1.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
UNION
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPIngred_1.PRICEKG3 AS Price, CPIngred_1.PRICEDATE3 AS priceDate, Ingredient_Code.IngredientCode_ID
FROM         CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS
UNION
SELECT     GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, CPIngred_1.PRICEKG4 AS Price, CPIngred_1.PRICEDATE4 AS priceDate, Ingredient_Code.IngredientCode_ID
FROM         CPRECIPE...CPIngred AS CPIngred_1 INNER JOIN
                      Ingredient_Code ON CPIngred_1.CODE = Ingredient_Code.Name COLLATE Latin1_General_CI_AS

INSERT INTO Ingred_Preise ( UpdatedOn,  UpdatedBy, PreisePerKg, 
                      Prisedate)

select distinct  GETDATE() AS UpdatedOn, ''SB'' AS UpdatedBy, PreisePerKg, 
                     Prisedate from #Ingred_Preise
                     
                     
                     


END

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_Init]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'






-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Recipes_Init]
-- Seek engine for Java interact. 
-- Gets list of Recipes dependent on choosen input fields
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50)	--''AB''
--go 
--
--SELECT     Recipe_Origin, Recipe_Addditional, [Recipe Version], [Recipe Stage], Detailed_Group, Status, Class, Mixer_Code, Description, Loadfactor, MixTime, 
--                      UpdatedOn, UpdatedBy, Recipe_ID
--FROM         dbo.fn_ITF_Recipes_Init(NULL, ''W'', ''13-0-N362'', ''0-N362'', ''O'', ''O'', ''P'', ''AA'')
--go 
--SELECT   distinct   Recipe_Origin
--FROM         dbo.fn_ITF_Recipes_Init(NULL, ''W'', ''13-0-N362'', ''0-N362'', ''O'', ''O'', ''P'', ''AA'')
--go 
--SELECT   distinct   Recipe_Addditional
--FROM         dbo.fn_ITF_Recipes_Init(''N362'', null, ''13-0-N362'', ''0-N362'', ''O'', ''O'', ''P'', ''AA'')
--go
--SELECT   distinct   [Recipe Version]
--FROM         dbo.fn_ITF_Recipes_Init(''N362'', ''W'', null, ''0-N362'', ''O'', ''O'', ''P'', ''AA'') 
)
RETURNS TABLE 
AS
RETURN 
(
SELECT     RIGHT(RTRIM(Recipe_Prop_Main.Code), 4) AS Recipe_Origin, Recipe_Prop_Main.Release AS Recipe_Addditional, Recipe_Prop_Main.Code AS [Recipe Version], 
                      RIGHT(RTRIM(Recipe_Prop_Main.Code), 6) AS [Recipe Stage], Recipe_Prop_Main.Detailed_Group, Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, 
                      Recipe_Prop_Main.Mixer_Code, Recipe_Prop_Main.Descr AS Description, Recipe_Prop_Main.Loadfactor, Recipe_Prop_Main.MixTime, 
                      Recipe_Prop_Main.UpdatedOn, Recipe_Prop_Main.UpdatedBy, Recipe_Prop_Main.Recipe_ID, Mixer_InfoBasic.Name
FROM         Recipe_Prop_Main INNER JOIN
                      Mixer_InfoBasic ON Recipe_Prop_Main.Mixer_Code = Mixer_InfoBasic.Code
WHERE     (@rel IS NULL OR
                      Release = @rel) AND (@code_origin IS NULL OR
                      @code_origin = RIGHT(RTRIM(Recipe_Prop_Main.Code), 4)) AND (@code IS NULL OR
                      Recipe_Prop_Main.Code = @code) AND (@code_stage IS NULL OR
                      @code_stage = RIGHT(RTRIM(Recipe_Prop_Main.Code), 6)) AND (@group IS NULL OR
                      Detailed_Group = @group) AND (@Status IS NULL OR
                      Status = @Status) AND (@Class IS NULL OR
                      Class = @Class) AND (@Mixer_Code IS NULL OR
                      Mixer_Code = @Mixer_Code)
)



' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Sequence_Init_SelectSet_1]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'















-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Sequence_Init_SelectSet_1]
/*
selects the set of recipes according to choosen code and release.
If code is NULL and release is NULL all possible sets are selected
IF code is ''XX-X-XXXX''chosen and Release is NULL the set with for code ''XX-X-XXXX'' with all possible release selected
IF code is NULLchosen and Release is ''X'' the set with for all possible codes  which have relese ''X'' selected
this function used for user interface list boxes  engine: 

for Code list box : fn_ITF_Sequence_Init_SelectSet(null,RElease_Listbox_Value, Mixer_CodeListBox_Value)
for Release list box : fn_ITF_Sequence_Init_SelectSet(Code_Listbox_Value, null, Mixer_CodeListBox_Value)
for Mixer_Code list box : fn_ITF_Sequence_Init_SelectSet(Code_Listbox_Value,RElease_Listbox_Value,null)

*/
(	
	-- Add the parameters for the function here
@Code varchar (50),
@Release varchar (50),
@Mixer_Code varchar (50)


)
RETURNS TABLE 
AS
RETURN 
(
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
/* OLD VERSION
SELECT     Code, Release, Mixer_Code
FROM         Recipe_Prop_Main
WHERE     (@Code IS NULL OR
                      Code = @Code) AND (@Release IS NULL OR
                      Release = @Release)
*/
/* current VERSION */

SELECT     TOP (100) PERCENT Recipe_Prop_Main.Code, Recipe_Prop_Main.Release, Recipe_Prop_Main.Mixer_Code, Mixer_InfoBasic.Name
FROM         Recipe_Prop_Main LEFT OUTER JOIN
                      Mixer_InfoBasic ON Recipe_Prop_Main.Mixer_Code = Mixer_InfoBasic.Code
WHERE     (@Code IS NULL) AND (@Release IS NULL) AND (@Mixer_Code IS NULL) OR
                      (@Code IS NULL) AND (@Mixer_Code IS NULL) AND (Recipe_Prop_Main.Release = @Release) OR
                      (@Release IS NULL) AND (@Mixer_Code IS NULL) AND (Recipe_Prop_Main.Code = @Code) OR
                      (@Mixer_Code IS NULL) AND (Recipe_Prop_Main.Release = @Release) AND (Recipe_Prop_Main.Code = @Code) OR
                      (@Code IS NULL) AND (@Release IS NULL) AND (Recipe_Prop_Main.Mixer_Code = @Mixer_Code) OR
                      (@Code IS NULL) AND (Recipe_Prop_Main.Release = @Release) AND (Recipe_Prop_Main.Mixer_Code = @Mixer_Code) OR
                      (@Release IS NULL) AND (Recipe_Prop_Main.Code = @Code) AND (Recipe_Prop_Main.Mixer_Code = @Mixer_Code) OR
                      (Recipe_Prop_Main.Release = @Release) AND (Recipe_Prop_Main.Code = @Code) AND (Recipe_Prop_Main.Mixer_Code = @Mixer_Code)
ORDER BY Recipe_Prop_Main.Code

)



' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_Z]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE  FUNCTION [dbo].[fn_ITF_Recipes_Z]
-- Seek engine for Java interact. 
-- Gets list of Recipes dependent on choosen input fields only main seeking field!!!! Additional are Zero parameters
-- zeros assume -''any''
(	
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
-----
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@Customer NVARCHAR(50)
)

RETURNS TABLE 
AS
RETURN 
(
SELECT     RIGHT(RTRIM(Recipe_Prop_Main.Code), 4) AS Recipe_Origin, Recipe_Prop_Main.Release AS Recipe_Addditional, 
                      Recipe_Prop_Main.Code AS [Recipe Version], RIGHT(RTRIM(Recipe_Prop_Main.Code), 6) AS [Recipe Stage], Recipe_Prop_Main.Detailed_Group, 
                      Recipe_Prop_Main.Status, Recipe_Prop_Main.Class, Recipe_Prop_Main.Mixer_Code, Recipe_Prop_Main.Descr, Recipe_Prop_Main.Loadfactor, 
                      Recipe_Prop_Main.MixTime, Recipe_Prop_Main.UpdatedOn, Recipe_Prop_Main.UpdatedBy, Recipe_Prop_Main.Recipe_ID, 
                      Mixer_InfoBasic.Name
FROM         Recipe_Prop_Main INNER JOIN
                      Mixer_InfoBasic ON Recipe_Prop_Main.Mixer_Code = Mixer_InfoBasic.Code
WHERE     (@rel IS NULL OR
                      Recipe_Prop_Main.Release = @rel) AND (@code_origin IS NULL OR
                      @code_origin = RIGHT(RTRIM(Recipe_Prop_Main.Code), 4)) AND (@code IS NULL OR
                      Recipe_Prop_Main.Code = @code) AND (@code_stage IS NULL OR
                      @code_stage = RIGHT(RTRIM(Recipe_Prop_Main.Code), 6)) AND (@group IS NULL OR
                      Recipe_Prop_Main.Detailed_Group = @group) AND (@Status IS NULL OR
                      Recipe_Prop_Main.Status = @Status) AND (@Class IS NULL OR
                      Recipe_Prop_Main.Class = @Class) AND (@Mixer_Code IS NULL OR
                      Recipe_Prop_Main.Mixer_Code = @Mixer_Code) AND (@Description IS NULL OR
                      Recipe_Prop_Main.Descr LIKE ''%'' + @Description + ''%'')
)

--go
--SELECT   *
--FROM         dbo.[fn_ITF_Recipes_Z](
--null		--@code_origin NVARCHAR(50),	--''0012'',
--, ''W''		--@rel NVARCHAR(1),	--''W''
--, null		--@code NVARCHAR(50),			--''00-0-0012''
--, null		--@code_stage NVARCHAR(50),   --''0-0012'',
--, ''O''		--@group NVARCHAR(50),		--''AA''
--, ''O''		--@Status NVARCHAR(1),		--''O/S''
--, ''P''		--@Class NVARCHAR(1),			--''P''
--, ''AA''		--@Mixer_Code NVARCHAR(50),	--''AB''
--, ''DZ''		--@description , [ ] -Any single character within the specified range ([a-f]) or set ([abcdef]),
--			-- [^]Any single character not within the specified range ([^a-f]) or set ([^abcdef]).
--,null		--@Color NVARCHAR(50),
--,null		--@industry NVARCHAR(50),
--,null		--@recept_type NVARCHAR(50),
--,null		--@curing_system NVARCHAR(50),
--,null		--@curing_process NVARCHAR(50),
--,null		--@filler NVARCHAR(50),
--,null		--@certificate NVARCHAR(50),
--,null		--@schelflife1 NVARCHAR(50),
--,null		--@schelflife2 NVARCHAR(50),
--,''65''		--@Hardnes_Sha1 NVARCHAR(50)  !!! irrelivant
--,''65''		--@Hardnes_Sha2 NVARCHAR(50)  !!! irrelivant
-- )





' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Import_to_Recipe_Prop_Main__3]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

CREATE PROCEDURE [dbo].[Import_to_Recipe_Prop_Main__3]
as
delete from Recipe_Prop_Main
INSERT INTO Recipe_Prop_Main
                      (CPRECIPE.CODE , Release, Descr, Status, Class, Loadfactor, MixTime, UpdatedOn, UpdatedBy, Detailed_Group, Mixer_Code,CreatedOn, CreatedBy)
SELECT     CODE, CASE WHEN release IS NOT NULL THEN release WHEN release IS NULL THEN ''0'' END AS release, DESCR, STATUS, CLASS, LOADFACTOR, 
                      MIXTIME1, LASTUPDATE AS UpdatedOn, UPDATEDBY AS UpdatedBy, [GROUP], MIXCODE, DEVELDATE, DEVELBY
FROM         CPRECIPE...CPRECIPE AS CPRECIPE_1
WHERE     (MIXCODE IN
                          (SELECT     Code COLLATE Latin1_General_CI_AS AS Expr1
                            FROM          Mixer_InfoBasic
                            GROUP BY Code))
UPDATE    Recipe_Prop_Main
SET              Detailed_Group = ''T''
WHERE     (Detailed_Group IS NULL)

   
' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_1_O_R]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE   FUNCTION [dbo].[Recipe_Full_RZPT_1_O_R] 
( 
    @MixerCode NVARCHAR(50), @LoadFactor float
, @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]''
) 
--go 
--select * from [dbo].[Recipe_Full_RZPT_1_O] (''01-0-1432'',''0'')
-- gets recipe from recipe.temp !!! parameters not relevant! , use [Recipe_Full_RZPT_1_O]('''','''')
-- calculates the total string
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
Recipe_Recipe_ID bigint,
RecipeID bigint
)
--select * from [dbo].[Recipe_Full_RZPT_1_O] (''01-0-1432'',''0'')
--go 
--select * from [dbo].[Recipe_Full_RZPT_1_O] (''01-0-1432'',''0'')
BEGIN
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 

DECLARE	@return_value int
set @return_value =(select COUNT(*) AS Expr1 FROM  dbo.Recipe_Tempory )
if  @return_value = 0 
	begin
		return
	end

-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]

if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin --''AA'' 
	if @user=''AA'' 
	begin
		set @return_value =(select COUNT(*) AS Expr1 FROM  [AA])
		if  @return_value = 0 return

		INSERT INTO @output 
		select * from [dbo].AA where material is not NULL
	end
	if @user=''AB'' 
	begin
		set @return_value =(select COUNT(*) AS Expr1 FROM  [AB])
		if  @return_value = 0 return
		INSERT INTO @output 
		select * from [AB] where material is not NULL
	end
	if @user=''AC'' 
	begin
		set @return_value =(select COUNT(*) AS Expr1 FROM  [AC])
		if  @return_value = 0 return
		INSERT INTO @output 
		select * from [AC] where material is not NULL
	end
end --''AA'' 
else
begin
		set @return_value =(select COUNT(*) AS Expr1 FROM  Recipe_Tempory)
		if  @return_value = 0 return
		INSERT INTO @output 
		select * from Recipe_Tempory where material is not NULL
end 


--Calculating Total last raw -Summary in the table

Declare @phr1T float,@PHR_recalc1T float, @weight1T  float, @weight_recalc1T float, @volume1T float, @volume_recalc1T float
Declare @phr1 float ,@PHR_recalc1 float ,@weight1 float,@weight_recalc1 float ,@volume1 float ,@volume_recalc1 float
declare @REcipeId bigint
declare @REcipeLoadFactor float 
declare  @rECIPEnAME NVARCHAR(50), @Release NVARCHAR(50)

DECLARe _crs_1 cursor FOR SELECT recipename, release,phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc   FROM  @output 
select  @phr1T=0, @PHR_recalc1T=0,  @weight1T  =0, @weight_recalc1T =0, @volume1T =0, @volume_recalc1T =0
		open _crs_1
		FETCH NEXT from _crs_1 into @rECIPEnAME,@Release, @phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
		WHILE @@FETCH_STATUS =0 
			begin
				set @phr1T= @phr1+		@phr1T						-- Total phr
				set @PHR_recalc1T= @PHR_recalc1+@PHR_recalc1T		-- Total recalc phr
				set @weight1T=@weight1T+@weight1
				set @weight_recalc1T=@weight_recalc1T+@weight_recalc1
				set @volume1T=@volume1T+@volume1
				set @volume_recalc1T=@volume_recalc1T+@volume_recalc1
			FETCH NEXT from _crs_1 into @rECIPEnAME,@Release,@phr1,@PHR_recalc1,@weight1,@weight_recalc1,@volume1,@volume_recalc1
			end
		close _crs_1
				set @REcipeLoadFactor = 100* @volume1T /( SELECT     Volume FROM         Mixer_InfoBasic WHERE     (Code = @MixerCode) )
DEALLOCATE _crs_1
declare @descr  nvarchar(50), @GRP  varchar(50), @code varchar(50)

DECLARe _crs_1 cursor FOR SELECT Material, matindex, descr, GRP  FROM  @output 
		open _crs_1
		FETCH NEXT from _crs_1 into @Material,@Matindex, @descr,@GRP
		WHILE @@FETCH_STATUS =0 
		begin
			if @matindex =''R''
			begin
				set @descr =(SELECT      Descr
				FROM         Recipe_Prop_Main
				WHERE     (Code = @Material) AND (Release = ''0''))
				set @GRP =(SELECT     Detailed_Group
				FROM         Recipe_Prop_Main
				WHERE     (Code = @Material) AND (Release = ''0''))
				UPDATE @OUTPUT  set descr = @descr, GRP=@GRP WHERE CURRENT OF _crs_1;
			end
		FETCH NEXT from _crs_1 into @Material,@Matindex, @descr,@GRP
		end
		close _crs_1
		--DEALLOCATE _crs_1


		Insert into  @OUTPUT 
				(RecipeName ,Release,MatIndex,phr,PHR_recalc,weight,weight_recalc,volume,volume_recalc, percRubber, density, density_recalc,id,material,descr)
				values(@rECIPEnAME,@Release,''R'', @phr1T,@PHR_recalc1T, @weight1T,@weight_recalc1T,@volume1T,@volume_recalc1T,1/@PHR_recalc1T*10000,@weight1T/@volume1T,
				@weight_recalc1T/@volume_recalc1T,99,@rECIPEnAME,  cast ( @REcipeLoadFactor as  varchar  )  ) 
		DEALLOCATE _crs_1


-- begin 1, density_recalc

RETURN   

end





' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_DisplPurchase_INSERT]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


--changed 2015-11-24
--DO NOT USE

CREATE procedure [dbo].[prc_ITF_Igredients_DisplPurchase_INSERT]
(	@IngredientCode_ID bigint,
@Tradename_Main_ID bigint,
@UpdatedBy varchar(50),
@UpdatedOn datetime = getdate
, @VENDOR_ID bigint

)

AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/

INSERT INTO _INTRF_IngredientCode_ID__Vendor_ID
                      (IngredientCode_ID, Tradename_Main_ID, UpdatedBy, UpdatedOn, VENDOR_ID, VM)
VALUES     (@IngredientCode_ID,@Tradename_Main_ID,@UpdatedBy,@UpdatedOn,@VENDOR_ID, N''M'')

--
--
--go
-- execute dbo.[prc_ITF_Igredients_DisplPurchase_INSERT] ''55161'', ''37205'',NULL,NULL
--
--go 
--select * from _INTRF_IngredientCode_ID__Tradename_Main_ID where IngredientCode_ID=''55161''




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_DisplVendor_Delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_DisplVendor_Delete]
(	
@ID bigint
)
AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
DELETE FROM _INTRF_IngredientCode_ID__Vendor_ID
WHERE     (id = @ID)
--
--go
-- [dbo].[prc_ITF_Igredients_DisplVendor_Delete] @VENDOR_ID








' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_DisplPurchase_delete]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
-- changed 2015-11-24




CREATE procedure [dbo].[prc_ITF_Igredients_DisplPurchase_delete]
(	
@Tradename_Main_ID bigint,
@IngredCode_ID bigint,
@Vendor_Id bigint
)
AS
/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
DELETE FROM _INTRF_IngredientCode_ID__Vendor_ID
WHERE     (Tradename_Main_ID = @Tradename_Main_ID) AND (IngredientCode_ID = @IngredCode_ID) AND (VENDOR_ID = @VENDOR_ID)
--
--go
-- [prc_ITF_Igredients_DisplPurchase_delete] ( @Tradename_Main_ID)









' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Import_Ingred_Code_Primary__6]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE PROCEDURE [dbo].[Import_Ingred_Code_Primary__6] 
as
DELETE FROM [NCPD].[dbo].[Ingredient_phys_Properties] --must be deleted before because it is secondary

delete from Ingredient_Code
INSERT INTO Ingredient_Code
                      (Name, Descr, Class, Cas_Number, ChemName, [Group], Form, Status, Info_01, CreatedBy, CreatedOn, UpdatedBy, UpdatedOn)
SELECT     CODE, DESCR, CLASS, CASNO, CHEMNAME, [GROUP], FORM, CASE WHEN status IS NULL THEN ''A'' ELSE Status END AS Status, XREFCODE, 
                      UPDATEDBY AS CreatedBy, LASTUPDATE AS CreatedOn, UPDATEDBY AS UpdatedBy, LASTUPDATE AS UpdatedOn
FROM         CPRECIPE...CPIngred AS CPIngred_1




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_Temp_View_RecipeSequence_Main]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE FUNCTION [dbo].[fn_Temp_View_RecipeSequence_Main]
(	
-- 	
-- this done to keep foreign key constraint (code, release, mixerID)
-- fn_Temp_View_RecipeSequence_Main_1 = dbo.fn_Temp_View_RecipeSequence_MainB()!!!!
)
RETURNS  TABLE 
AS
RETURN 
(
SELECT DISTINCT 
                      TOP (100) PERCENT fn_Temp_View_RecipeSequence_Main_1.code, fn_Temp_View_RecipeSequence_Main_1.release, 
                      fn_Temp_View_RecipeSequence_Main_1.MIXERID, fn_Temp_View_RecipeSequence_Main_1.DESCR, 
                      fn_Temp_View_RecipeSequence_Main_1.LASTUPDATE, fn_Temp_View_RecipeSequence_Main_1.UPDATEDBY, 
                      fn_Temp_View_RecipeSequence_Main_1.STATUS
FROM         Recipe_Prop_Main INNER JOIN 
                      dbo.fn_Temp_View_RecipeSequence_MainB() AS fn_Temp_View_RecipeSequence_Main_1 ON 
                      Recipe_Prop_Main.Code = fn_Temp_View_RecipeSequence_Main_1.code COLLATE SQL_Latin1_General_CP1_CI_AS AND 
                      Recipe_Prop_Main.Release = fn_Temp_View_RecipeSequence_Main_1.release COLLATE SQL_Latin1_General_CP1_CI_AS AND 
                      Recipe_Prop_Main.Mixer_Code = fn_Temp_View_RecipeSequence_Main_1.MIXERID COLLATE SQL_Latin1_General_CP1_CI_AS

)
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Sequence_Steps_SSSS__8]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
-- Batch submitted through debugger: SQLQuery2.sql|7|0|C:\Users\mcsupport\AppData\Local\Temp\2\~vs3C57.sql

CREATE PROCEDURE [dbo].[Insert_Sequence_Steps_SSSS__8]
/*
go 
DECLARE	@return_value int
EXEC	@return_value = [dbo].[Insert_Sequence_Steps_SSSW__8]
SELECT	''Return Value'' = @return_value
GO
*/
AS
SET NOCOUNT ON;
--Declare 
DECLARE @code nvarchar(10),@release nvarchar(1), @mixerID nvarchar(2), @mixinfo nvarchar(MAX);
DECLARE @count int 
/* from here
use sequence temp wneh mixinfor is not null
*/
DECLARE _cursor CURSOR FOR 
SELECT     MIXERID, CODE, release, MIXINFO 
FROM         dbo.Sequence_temp() 
WHERE     MIXINFO IS NOT NULL

OPEN _cursor
set @count=1
--IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#SSS'')AND type in (N''U''))

IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[SSS]'')AND type in (N''U''))
DROP TABLE SSS
--IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#SSS'') AND type in (N''U''))

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[SSS]'') AND type in (N''U''))
CREATE TABLE SSS(
	[mixerid] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[release] [nvarchar](1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[code] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[splitdata] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[step] [int] NULL
) ON [PRIMARY]

DELETE FROM SSS

FETCH NEXT FROM _cursor 
INTO @mixerID, @code,@release, @mixinfo
WHILE @@FETCH_STATUS <> -1 --AND @count<=100000
BEGIN
set @count=@count+1
insert   into SSS select @mixerID as mixerID,@release as release,  @code as code , [splitdata], [step]
  from dbo.fnSplitString_steps(@mixinfo,''
'') 
FETCH NEXT FROM _cursor 
INTO @mixerID, @code, @release, @mixinfo
--PRINT @count
end
CLOSE _cursor;
DEALLOCATE _cursor;

DECLARE  @step INT;
set @count=1
----++++++++++++++++++++++++++++
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[SSSS]'') AND type in (N''U''))
DROP TABLE [dbo].[SSSS]

IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[SSSS]'') AND type in (N''U''))
CREATE TABLE [dbo].[SSSS](
	[mixerid] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[code] [nvarchar](10) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	release [nvarchar](1) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[command] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[param] [nvarchar](max) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[step] [int] NULL
) ON [PRIMARY]


DECLARE _cursor_SS CURSOR FOR 
SELECT mixerid, code, release,  splitdata,step
FROM SSS
OPEN _cursor_SS

DELETE FROM [dbo].[SSSS]

FETCH NEXT FROM _cursor_SS 
INTO @mixerid, @code,@release,  @mixinfo,@step
WHILE @@FETCH_STATUS <> -1 --AND @count<=100000
BEGIN
if (select count(*) from dbo.fnSplitString01(@mixinfo,Char(10)+Char(13))) > 0 
begin
--print  cast(@count as varchar(10)) + '';'' + cast (@code as varchar(10) ) +'';'' +  cast (@release as varchar(10) )
INSERT INTO ssss
SELECT     @mixerid AS mixerid, @code AS code, @release AS release, field1 AS command, case when  len (field2) = 0 then ''0.0'' else field2 END  AS param, @step AS step
FROM         dbo.fnSplitString01(@mixinfo, CHAR(10) + CHAR(13))
WHERE     (LEN(field1) > 0) --AND (LEN(field2) > 0)
end
FETCH NEXT FROM _cursor_SS 
INTO @mixerid, @code, @release, @mixinfo,@step

set @count=@count+1
end



CLOSE _cursor_SS;
DEALLOCATE _cursor_SS;
--/*

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_Recalc_givenPHRS]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'









CREATE   Proc [dbo].[Recipe_Tempory_Recalc_givenPHRS]
( 
@recipe NVARCHAR(50),
@release NVARCHAR(50)=''0''

) 
as
 
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'')AND type in (N''U''))
DROP TABLE #WWW
IF  NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''tempdb..#WWW'') AND type in (N''U''))
CREATE TABLE #WWW
(
[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
) ON [PRIMARY]
--
/*  version befor 11/11
INSERT INTO #WWW
                (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM         dbo.Recipe_Full_RZPT_Recalc_IngredWeights_givenPHRS(@recipe, @release)

delete from Recipe_Tempory
INSERT INTO Recipe_Tempory
                      (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, SiloId, Phase, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
FROM         #WWW
version befor 11/11 --
*/ 

execute [Recipe_Full_RZPT_Recalc_PHRS_Check1] @recipe,@release


return 






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnInsert_RECIPENEW1]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
CREATE FUNCTION[dbo].[fnInsert_RECIPENEW1] () 
RETURNS @output TABLE([Code] [varchar](50) NULL,
[Release] [varchar](50) NULL,
	[IngredName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Weight] [nvarchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL
) 
BEGIN 

--Declare 
DECLARE @code nvarchar(10),@release nvarchar(2), @mixID nvarchar(3);
DECLARE 
@MATERIAL1  nvarchar(50),
@MATERIAL2  nvarchar(50),
@MATERIAL3  nvarchar(50),
@MATERIAL4  nvarchar(50),
@MATERIAL5  nvarchar(50),
@MATERIAL6  nvarchar(50),
@MATERIAL7  nvarchar(50),
@MATERIAL8  nvarchar(50),
@MATERIAL9  nvarchar(50),
@MATERIAL10  nvarchar(50),
@MATERIAL11  nvarchar(50),
@MATERIAL12  nvarchar(50),
@MATERIAL13  nvarchar(50),
@MATERIAL14  nvarchar(50),
@MATERIAL15  nvarchar(50),
@MATERIAL16  nvarchar(50),
@MATERIAL17  nvarchar(50),
@MATERIAL18  nvarchar(50),
@MATERIAL19  nvarchar(50),
@MATERIAL20  nvarchar(50)


DECLARE @count int 

DECLARE _cursor CURSOR FOR 
SELECT     CODE, releaSE, 
Material1, Material2,Material3,Material4,Material5,Material6,Material7,Material8,Material9,Material10,
Material11, Material12,Material13,Material14,Material15,Material16,Material17,Material18,Material19,Material20
FROM         CPRECIPE...CPRECIPE

OPEN _cursor
set @count=1


FETCH NEXT FROM _cursor 
INTO  @code, @release,
@Material1, @Material2,@Material3,@Material4,@Material5,@Material6,@Material7,@Material8,@Material9,@Material10,
@Material11, @Material12,@Material13,@Material14,@Material15,@Material16,@Material17,@Material18,@Material19,@Material20

set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END

WHILE @@FETCH_STATUS <> -1 
BEGIN
set @count=@count+1

if @material1 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material1) 
if @material2 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material2) 
if @material3 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material3) 
if @material4 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material4) 
if @material5 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material5) 
if @material6 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material6) 
if @material7 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material7) 
if @material8 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material8) 
if @material9 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material9) 
if @material10 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material10) 
if @material11 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material11) 
if @material12 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material12) 
if @material13 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material13) 
if @material14 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material14) 
if @material15 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material15) 
if @material16 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material16) 
if @material17 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material17) 
if @material18 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material18) 
if @material19 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material19) 
if @material20 IS NOT NULL 
insert   into @output  select @code as code , @release as release,ingredName,PHR, weight, SiloId,PHASE, matindex  from fnSplit_RECIPE_MAterial(@material20) 

FETCH NEXT FROM _cursor 
INTO  @code, @release,
@Material1, @Material2,@Material3,@Material4,@Material5,@Material6,@Material7,@Material8,@Material9,@Material10,
@Material11, @Material12,@Material13,@Material14,@Material15,@Material16,@Material17,@Material18,@Material19,@Material20
set @release= case when @release IS NOT NULL THEN @release WHEN @release IS NULL THEN ''0'' END
end
--select * from @output
CLOSE _cursor;
DEALLOCATE _cursor;
    RETURN 
END

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fn_ITF_Recipes_Info]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE FUNCTION [dbo].[fn_ITF_Recipes_Info]
(	
	-- Add the parameters for the function here
@code NVARCHAR(50),			--''00-0-0012''
@Release NVARCHAR(1),			--''W''
@Mixer_Code NVARCHAR(50)	--''AB''
-- All parameters Obligatory
-- select * from [fn_ITF_Recipes_Info] (''00-0-0011'',''0'',''GK'')ORDER BY Recipe_Version 


)
RETURNS TABLE 
AS
RETURN 
(
SELECT     Recipe_Prop_Main.Code AS Recipe_Version, Recipe_Prop_Main.Release AS Recipe_Addditional, Recipe_Prop_Main.Mixer_Code, 
                      CountFreeInfo.NoteName, Recipe_Prop_Free_Info.NoteValue AS Note_Value, Recipe_Prop_Free_Info.UpdatedOn, Recipe_Prop_Free_Info.UpdatedBy, 
                      Recipe_Prop_Free_Info.Recipe_Prop_Free_Info_ID
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Prop_Free_Info ON Recipe_Prop_Main.Recipe_ID = Recipe_Prop_Free_Info.Recipe_ID RIGHT OUTER JOIN
                      CountFreeInfo ON Recipe_Prop_Free_Info.NoteName = CountFreeInfo.NoteName AND Recipe_Prop_Main.Mixer_Code = @Mixer_Code AND 
                      Recipe_Prop_Main.Release = @Release AND Recipe_Prop_Main.Code = @Code
)

--go
--select * from [fn_ITF_Recipes_Info] (''00-0-0011'',''0'',''GK'')ORDER BY Recipe_Version

' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Igredients_Warehause_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'









-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE procedure [dbo].[prc_ITF_Igredients_Warehause_Insert]
(	@IngredientCode_ID bigint,
 @SiloId varchar (50),
@BalanceID varchar (50),
@LotNO varchar (50),
@BOxNo varchar (50),
@SchelfLife varchar (50),
@Location varchar (50),
@Storetime varchar (50),
@SttempMin varchar (50),
@StTempMax varchar (50),
@StoreDate varchar (50),
 @STDATEEXT varchar (50),
 @ActStock varchar (50),
@MinStock varchar (50),
@LastUsed varchar (50),
 @UsageDate varchar (50),
 @StupDate varchar (50),
 @Form varchar (50), 
 @Quantity varchar (50),
 @QuantUnit varchar (50),
@ORDER varchar (50), 
@OrderUnit varchar (50),
@SWFINE varchar (50), 
@SWVeryFine varchar (50), 
@StopSignal varchar (50),
@Tolerance varchar (50), 
@FreeInfo varchar (50),
@UpdatedOn varchar (50),
@UpdatedBy varchar (50)

)


AS

/*WHERE     (Ingredient_Code.Name = @IngredCode_ID)*/
INSERT INTO Ingredient_Warehouse
                      (SiloId, BalanceID, LotNO, BOxNo,SchelfLife, Location, Storetime, SttempMin, StTempMax, StoreDate, STDATEEXT, ActStock, MinStock, LastUsed, UsageDate, 
                      StupDate, Form, Quantity, QuantUnit, [ORDER], OrderUnit, SWFINE, SWVeryFine, StopSignal, Tolerance, FreeInfo, UpdatedOn, UpdatedBy, 
                      IngredientCode_ID)
VALUES     (@SiloId,@BalanceID,@LotNO,@BOxNo,@SchelfLife, @Location,@Storetime,@SttempMin,@StTempMax,@StoreDate,@STDATEEXT,@ActStock,@MinStock,@LastUsed,@UsageDate,@StupDate,@Form,@Quantity,@QuantUnit,@ORDER,@OrderUnit,@SWFINE,@SWVeryFine,@StopSignal,@Tolerance,@FreeInfo,@UpdatedOn,@UpdatedBy,@IngredientCode_ID)
--
--go
--select *   from [fn_ITF_Igredients_Init_Load_Warehause] (''17543'')





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Tempory_INSERT_INTO_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


--exec [Recipe_Tempory_INSERT_INTO_USER] (''06-0-N830'',''0'',)

CREATE   Proc [dbo].[Recipe_Tempory_INSERT_INTO_USER] 
( 
@Code NVARCHAR(50) , 
@release NVARCHAR(50),
@ingredID NVARCHAR(50),
@user NVARCHAR(50)
) 
as
	DECLARE @CNT NVARCHAR(50)
exec	(''DECLARE _cursor CURSOR FOR   (select   COUNT(*) AS CNT FROM    ''+@user + '')'')
	open _cursor
	FETCH NEXT FROM _cursor INTO  @CNT 
	
close _cursor
deallocate _cursor


DECLARE @recipeID bigint
set @RecipeId=(
SELECT     Recipe_ID 
FROM         Recipe_Prop_Main
WHERE     (Code = @code) AND (Release = @release))

if  len(rtrim(ltrim (@ingredID))) = 5
begin
exec  (
''INSERT INTO ''+@user + 
''  (Id, RecipeName, Release, material, SiloId, percRubber, density, BalanceID, PriceKG, PriceData,
 Descr, GRP, RecipeID, MatIndex, Phase, ContainerNB, PHR, weight) 
SELECT     ''+ @CNT+'' + 1 AS Id, ''''''+@code+'''''' AS RecipeName, ''''''+@release+'''''' AS release, ''''''+@ingredID+'''''' AS material,
 SiloId, percRubber, density, BalanceID, PriceKG, PriceData, Descr, GRP, ''''''+@RecipeId+'''''' AS RecipeID,
 ''+''''''I''''''+ '' AS MatIndex, ''+''1''+'' AS Phase, ''+''''''A''''''+'' AS ContainerNB, 1 AS PHR, 1 AS weight
FROM         dbo.fn_Recipe_Ingredient_Insert(''''''+@ingredID+'''''')

'')
end 
else
exec  (
''INSERT INTO ''+@user + 
''  (Id, RecipeName, Release, material, SiloId, percRubber, density, BalanceID, PriceKG, PriceData,
 Descr, GRP, RecipeID, MatIndex, Phase, ContainerNB, PHR, weight) 
SELECT     ''+ @CNT+'' + 1 AS Id, ''''''+@code+'''''' AS RecipeName, ''''''+@release+'''''' AS release,  RecipeID AS material,
''+''''''0''''''+ '' AS SiloId,  percRubber, density, ''+''''''1''''''+ '' AS balanceId, Price AS PricKg, NULL AS PriceData, Descr, GRP, ''''''+@RecipeId+'''''' AS RecipeID,
 ''+''''''R''''''+ '' AS MatIndex, ''+''1''+'' AS Phase, ''+''''''A''''''+'' AS ContainerNB, 1 AS PHR, 1 AS weight
FROM         dbo.Recipe_FINAL_RZPT(''''''+@ingredID+'''''', ''+''''''0''''''+'') AS Recipe_FINAL_RZPT_1
'')

return 0






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Recipes_Z_Z]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE Procedure [dbo].[prc_ITF_Recipes_Z_Z]
(	
/*
Basic Filter only recipt main to fill Recipe_SET table
*/
	-- Add the parameters for the function here
@code_origin NVARCHAR(50),	--''0012'',
@rel NVARCHAR(1),	--''W''
@code NVARCHAR(50),			--''00-0-0012''
@code_stage NVARCHAR(50),   --''0-0012'',
@group NVARCHAR(50),		--''AA''
@Status NVARCHAR(1),		--''O/S''
@Class NVARCHAR(1),			--''P''
@Mixer_Code NVARCHAR(50),	--''AB''
@Description NVARCHAR(50),  -- "#Wamp"
@Color NVARCHAR(50),
@industry NVARCHAR(50),
@recept_type NVARCHAR(50),
@curing_system NVARCHAR(50),
@curing_process NVARCHAR(50),
@filler NVARCHAR(50),
@certificate NVARCHAR(50),
@schelflife1 NVARCHAR(50),
@schelflife2 NVARCHAR(50),
@Hardnes_Sha1 NVARCHAR(50),
@Hardnes_Sha2 NVARCHAR(50),
@customer NVARCHAR(50)
)

as


IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[ZZZ]'')AND type in (N''U''))
DROP TABLE ZZZ
IF   EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].ZZZ1'') AND type in (N''U''))
DROP TABLE ZZZ1

SELECT     Recipe_ID, RIGHT(RTRIM(Code), 4) as [Recipe_Origin] , Release AS [Recipe_Additional], Code AS [Recipe Version], RIGHT(RTRIM(Code), 6) AS [Recipe Stage], 
                      Detailed_Group , Status, Class, Mixer_Code , Descr , Loadfactor, MixTime,
 UpdatedOn  , UpdatedBy 
INTO            ZZZ
FROM         Recipe_Prop_Main
WHERE     (@rel IS NULL OR
                      Release = @rel) AND (@code_origin IS NULL OR
                      @code_origin = RIGHT(RTRIM(Code), 4)) AND (@code IS NULL OR
                      Code = @code) AND (@code_stage IS NULL OR
                      @code_stage = RIGHT(RTRIM(Code), 6)) AND (@group IS NULL OR
                      Detailed_Group = @group) AND (@Status IS NULL OR
                      Status = @Status) AND (@Class IS NULL OR
                      Class = @Class) AND (@Mixer_Code IS NULL OR
                      Mixer_Code = @Mixer_Code) AND (@Description IS NULL OR
                      Descr LIKE ''%'' + @Description + ''%'')
--select * from zzz
/*
Recipe_Origin to Recipe, 
Recipe_Additional to Add.,
 Descr. To Description,
 Detailed_group to Group, Mixer_code to Mixer, 
UpdatedOn to Updated On, 
UpdatedBy to Updated By.
*/










' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[prc_ITF_Ingredients_Coments_Insert]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


CREATE  procedure  [dbo].[prc_ITF_Ingredients_Coments_Insert]
( 
 @Ingred_Info_ID bigint,
@IngredientCode_ID bigint,
@Comments varchar(50),
@NoteValue varchar(50),
@UpdatedOn varchar(50),
@UpdatedBy varchar(50)
)


as
INSERT INTO Ingred_Comments
                      (IngredientCode_ID, Ingred_Info_ID, Comments, UpdatedOn, UpdatedBy)
VALUES     (@IngredientCode_ID,@Ingred_Info_ID,@Comments,@UpdatedOn,@UpdatedBy)




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Production_plan_csv_22]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE procedure [dbo].[Insert_Production_plan_csv_22] 
as
delete from [Production_Plan_Csv]
INSERT INTO Production_Plan_Csv
SELECT     Prod#Order, Artikel, Omschrijving, [Te Maken], Multiple, Batches, Startdatum, Sorteren, Volgorde, Vraagorder, Klantcode, KlantNaam, 
                      Opmerkingen
FROM         PRODPLAN...[woplanning.csv] AS [woplanning.csv_1]
ORDER BY Sorteren DESC

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_NewFillFactor_2]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'












CREATE  FUNCTION [dbo].[Recipe_Full_RZPT_NewFillFactor_2] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @New_FillFactor float, @MixerID NVARCHAR(50) ) 
--- parameter @New_Total_Weight is not used


--go 
--
--select  * from [Recipe_Full_RZPT_NewFillFactor_2](''00-0-0006'',''0'',60, ''AA'')
--
--go


RETURNS @output TABLE
(
		[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
INSERT INTO @output 
select * from  [Recipe_Tempory]  WHERE     (material IS NOT NULL)



declare @CurrentItem_Weight float,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float ,
@New_Total_Volume float

SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		--set @count=@count+1
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume
--, id=@count 
WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Reverce
if @New_FillFactor > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_DIV_dens float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_DIV_dens= 0
		DECLARe _crs_1 cursor FOR SELECT PHR,DENSITY, percRubber  FROM  @output 
		set @New_Total_Volume = @New_FillFactor/[dbo].Recipe_FillFactor_1_O (@MixerID,NULL)*[dbo].Recipe_REcipeVolume_1_O()
		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR_DIV_dens
			set @Summ_PHR_DIV_dens= @Summ_PHR_DIV_dens+ @Item_PHR/@Item_Density
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1
	-- Step 1.Finish. calculate total @Summ_PHR_DIV_dens

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @RubberWeight= @New_Total_Volume/@Summ_PHR_DIV_dens			-- Step.1.Total rubber weight 
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				UPDATE @OUTPUT 
				SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1

		DEALLOCATE _crs_1


end -- begin 1

RETURN   
end 



' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe_Full_RZPT_NewFillFactor_2_R]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'

create  FUNCTION [dbo].[Recipe_Full_RZPT_NewFillFactor_2_R] 
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @New_FillFactor float, @MixerID NVARCHAR(50), @user NVARCHAR(50)=''[dbo].[Recipe_Tempory]'' ) 
--- parameter @New_Total_Weight is not used


--go 
--
--select  * from [Recipe_Full_RZPT_NewFillFactor_2](''00-0-0006'',''0'',60, ''AA'')
--
--go


RETURNS @output TABLE
(
		[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
	Recipe_Recipe_ID bigint,
	RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=1
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
if @user = ''AA'' OR  @user = ''AB'' OR @user = ''AC'' 
begin
	if @user=''AA'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AA
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AB'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AB
		WHERE     (material IS NOT NULL)
	end 
	if @user=''AC'' 
	begin
		INSERT INTO @output 
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
						  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM          AC
		WHERE     (material IS NOT NULL)
	end 
else
	INSERT INTO @output 
	SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
	FROM          Recipe_Tempory
	WHERE     (material IS NOT NULL)
end 




declare @CurrentItem_Weight float,
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float ,
@New_Total_Volume float

SET @COUNT=0

-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER float 
				Declare @Density float
				DECLARe _crs cursor FOR SELECT PERCRUBBER,DENSITY, WEIGHT  FROM  RECIPE_FINAL_rzpt(@MATERIAL,@Rrelease) 
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight
				close _CRS
				DEALLOCATE _CRS
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float) WHERE CURRENT OF _cursor;
			END
	FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

end 
close _cursor
DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density

WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		--set @count=@count+1
		UPDATE @OUTPUT 
		SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume
--, id=@count 
WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 

close _crs_3
DEALLOCATE _crs_3



  -- Here we recalculate the ingredients, see instructions for Reverce
if @New_FillFactor > 0 
begin -- begin 1
		Declare @RubberWeight  float,	
		@Summ_PHR_DIV_dens float, -- total recipe PHR
		@Summ_WeightTotal float,  -- total recipe weight
		@Summ_density float
		
		set @Summ_PHR_DIV_dens= 0
		DECLARe _crs_1 cursor FOR SELECT PHR,DENSITY, percRubber  FROM  @output 
		set @New_Total_Volume = @New_FillFactor/[dbo].Recipe_FillFactor_1_O (@MixerID,NULL)*[dbo].Recipe_REcipeVolume_1_O()
		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
		-- Step 1.Start. calculate total @Summ_PHR_DIV_dens
			set @Summ_PHR_DIV_dens= @Summ_PHR_DIV_dens+ @Item_PHR/@Item_Density
			FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1
	-- Step 1.Finish. calculate total @Summ_PHR_DIV_dens

		open _crs_1
		FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
		WHILE @@FETCH_STATUS <> -1 
			begin
				set @RubberWeight= @New_Total_Volume/@Summ_PHR_DIV_dens			-- Step.1.Total rubber weight 
				set @CurrentItem_Weight= @Item_PHR*@RubberWeight				-- Step.2. Ingredient weight
				set @CurrentItem_Volume= @CurrentItem_Weight/@Item_Density		-- step.4. Ingredient volume
				UPDATE @OUTPUT 
				SET  weight = @CurrentItem_Weight, volume = @CurrentItem_Volume WHERE CURRENT OF _crs_1;	
				FETCH NEXT from _crs_1 into @Item_PHR,@Item_Density,@Item_percRubber
			end
		close _crs_1

		DEALLOCATE _crs_1


end -- begin 1

RETURN   
end 




' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Recipe__RZPT_Query_1]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'






CREATE  FUNCTION [dbo].[Recipe__RZPT_Query_1] 
-- PREPARES DATA FOR RECIPE_TEMPRORY by exec Create_Recipe_Tempory() . SUMMARY calculation is not included - use Recipe_Full_RZPT_1_O for that!
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)) 
--  Given: Total weight,  ingred densities
-- To Define:
-- ingred phr
-- ingred volumes
-- Recalculate recipetotals (use funct full_RZPT) 
--go 

--
--go 
--select * from  [Recipe__RZPT_Query_1] (''06-0-N830'',''0'')
--go 
--exec [Create_Recipe_Tempory]''01-8-1432'',''0''
--select * from  Recipe_Tempory
RETURNS @output TABLE
(
	[Id] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[RecipeName] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Release] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[material] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Descr] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[MatIndex] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[GRP] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[density_Recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[percRubber] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PHR_recalc] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weight_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[volume_Recalc]varchar(50)COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Volume] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[weighingID] [int] NULL,
	[ContainerNB] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[Phase] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[SiloId] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[BalanceID] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceKG] [varchar](50) COLLATE SQL_Latin1_General_CP1_CI_AS NULL,
	[PriceData] [varchar](50) NULL,
Recipe_Recipe_ID bigint,
RecipeID bigint
)
BEGIN
  DECLARE @COUNT INT
declare @material varchar(50)
DECLARE @matIndex VARCHAR(1)
declare @weight float 
SET @COUNT=0
-- we take data from the DB. Ingredients PHR and Weights are not checked /recalculated
-- recalculated values will be put into [PHR_recalc] and [weight_Recalc]
INSERT INTO @output (
recipeName,
release,
material,
descr,
MatIndex,
[GRP],
density,
percRubber,
phr,
weight,
[Volume],
weighingId,
containerNB,
Phase,
SiloId,
BalanceId,
PriceKg,
PriceData,
Recipe_Recipe_ID,
RecipeID
)

SELECT     Recipe_Prop_Main.Code AS RecipeName, Recipe_Prop_Main.Release, Recipe_Recipe.IngredName AS material, Ingredient_Code.Descr, 
                      Recipe_Recipe.MatIndex, Ingredient_Code.[Group] AS GRP, Ingredient_phys_Properties.density, Ingredient_phys_Properties.percRubber, 
                      Recipe_Recipe.PHR, Recipe_Recipe.Weight, CAST(Recipe_Recipe.Weight AS float) / CAST(Ingredient_phys_Properties.density AS float) AS Vol, 
                      CASE WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 256 + Ingredient_Warehouse.BalanceID WHEN (dbo.Recipe_Recipe.SiloId IS NULL OR
                      ltrim(dbo.Recipe_Recipe.SiloId) = '''') AND ingredient_Warehouse.BalanceID IS NULL THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      ingredient_Warehouse.BalanceID IS NOT NULL THEN 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND 
                      CONVERT(float, dbo.Recipe_Recipe.PHR) >= 100 THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''A'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 2 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''B'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 2 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 3 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''C'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 3 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 4 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''D'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 4 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 5 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''E'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 5 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 6 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''F'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 6 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND CONVERT(float, dbo.Recipe_Recipe.PHR) 
                      >= 100 THEN 7 * 256 + 1 WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NOT NULL 
                      THEN 7 * 256 + ingredient_Warehouse.BalanceID WHEN dbo.Recipe_Recipe.SiloId = ''H'' AND ingredient_Warehouse.BalanceID IS NULL 
                      THEN 7 * 256 + 1 END AS weighingID, CASE WHEN Recipe_Recipe.SiloID IS NULL OR
                      dbo.Recipe_Recipe.SiloId = '''' THEN ''A'' WHEN Recipe_Recipe.SiloID IS NOT NULL THEN Recipe_Recipe.SiloID END AS ContainerNB, 
                      Recipe_Recipe.Phase, Ingredient_Warehouse.SiloId, CASE WHEN Ingredient_Warehouse.BalanceID IS NULL 
                      THEN 1 WHEN Ingredient_Warehouse.BalanceID IS NOT NULL THEN Ingredient_Warehouse.BalanceID END AS BalanceID, 
                      Ingredient_Code.ActualPreisePerKg, NULL AS PriceData, Recipe_Recipe.Recipe_Recipe_ID, Recipe_Recipe.Recipe_ID
FROM         Ingredient_phys_Properties RIGHT OUTER JOIN
                      Ingredient_Code ON Ingredient_phys_Properties.IngredientCode_ID = Ingredient_Code.IngredientCode_ID LEFT OUTER JOIN
                      Ingredient_Warehouse ON Ingredient_Code.IngredientCode_ID = Ingredient_Warehouse.IngredientCode_ID LEFT OUTER JOIN
                      Ingred_Preise ON Ingredient_Code.IngredientCode_ID = Ingred_Preise.IngredientCode_ID RIGHT OUTER JOIN
                      Recipe_Prop_Main INNER JOIN
                      Recipe_Recipe ON Recipe_Prop_Main.Recipe_ID = Recipe_Recipe.Recipe_ID ON Ingredient_Code.Name = Recipe_Recipe.IngredName
WHERE     (Recipe_Prop_Main.Code IS NOT NULL) AND (Recipe_Prop_Main.Code = @rECIPEnAME) AND (Recipe_Prop_Main.Release = @Rrelease)
ORDER BY Recipe_Recipe.Phase, ContainerNB

declare @CurrentItem_Weight decimal(8,2),
		@CurrentItem_Volume float,
		@Item_Density float, 
		@Item_PHR float,
		@Item_percRubber float 


-- here we find and set the PERCRUBBER,DENSITY, volume for the masterbatches

DECLARE _cursor CURSOR FOR  select material, MatIndex , weight from @output 
open _cursor
FETCH NEXT FROM _cursor 
INTO @material,@matIndex,@weight

--return

WHILE @@FETCH_STATUS <> -1 
begin
		IF @matIndex=''R''
			BEGIN
				Declare @PERCRUBBER varchar(50) 
				Declare @Density varchar(50)
				Declare @RecipeID varchar(50)
				Declare @Descr varchar(50)
				DECLARe _crs cursor FOR SELECT     percRubber, density, [Weight], RecipeID, Descr
				FROM dbo.Recipe_FINAL_RZPT(@MATERIAL, ''0'') AS Recipe_FINAL_RZPT_1
				open _crs
				FETCH NEXT from _crs into @PERCRUBBER,@DENSITY,@Weight,@RecipeID, @Descr
				close _CRS
				DEALLOCATE _CRS
				
				if (@RecipeID=''RECURS'' )
					UPDATE @OUTPUT 
					SET  descr =''ERROR: Recipe includes itself'', material =@RecipeID , PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float)  WHERE CURRENT OF _cursor;
				if @DENSITY IS NULL 
					UPDATE @OUTPUT 
					SET  descr =''ERROR: Density is UNDEFINED, check ingredients!'', material =@RecipeID , PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float)  WHERE CURRENT OF _cursor;
				UPDATE @OUTPUT 
				SET  PERCRUBBER =@PERCRUBBER, DENSITY= @DENSITY, volume = cast (@Weight as float)/cast(@DENSITY as float), descr =@descr WHERE CURRENT OF _cursor;
			END
			FETCH NEXT FROM _cursor 
			INTO @material,@matIndex,@weight

			end 
			close _cursor
			DEALLOCATE _cursor

DECLARE _crs_3 CURSOR FOR  select  weight,  phr , density from @output 
open _crs_3
FETCH NEXT FROM _crs_3 
INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
Declare @fla as int 
set @fla =0
WHILE @@FETCH_STATUS = 0 
	begin
		set @CurrentItem_Volume=@CurrentItem_Weight/@Item_Density
		set @count=@count+1
		UPDATE @OUTPUT 
		SET  weight_recalc = @CurrentItem_Weight, volume = @CurrentItem_Volume,  volume_Recalc = @CurrentItem_Volume, phr_Recalc = @Item_PHR, id=@count WHERE CURRENT OF _crs_3;	
		FETCH NEXT FROM _crs_3 INTO @CurrentItem_Weight,@Item_PHR, @Item_Density
	end 
close _crs_3
DEALLOCATE _crs_3
return 
end

--go 
----
----
--select * from  [Recipe__RZPT_Query_1] (''06-0-N830'',''0'')









' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Insert_Sequence_Main__9]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE  procedure [dbo].[Insert_Sequence_Main__9]
--uses fn_Temp_View_RecipeSequence_Main()
as

--begin try 
delete from Recipe_Sequence_Steps
--end try
--begin catch 
--return -91
--end catch

--begin try 
delete from Recipe_Sequence_Main
--end try

--begin catch 
--return -92
--end catch

--begin try 
INSERT INTO Recipe_Sequence_Main
                      (Code, Release, Status, Mixer_Code, Info, UpdatedBy, UpdatedOn)
SELECT     code, Release, STATUS, MIXERID, DESCR, UPDATEDBY, LASTUPDATE
FROM         fn_Temp_View_RecipeSequence_Main()
--Comment 
--end try
--begin catch
--return -93
--end catch
--begin try 

DECLARE	@return_value int

EXEC	@return_value = [dbo].[Delete_Duplicates_Recipe_Sequence_Main]

--SELECT	''Delete_Duplicates_Recipe_Sequence_Main'' = @return_value
--end try
--begin catch
--return -94
--end catch

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Create_Recipe_Tempory]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



-- creates new temprory recipe table [Recipe_Tempory]
--returns 
--''-9'' : if no records in recipe
---''N'': amount of records in recipe
-- ''-1'' : if sever problem executing procedure


CREATE   procedure  [dbo].[Create_Recipe_Tempory] 

--go
--exec [Create_Recipe_Tempory] ''01-8-1432'', ''0''
--go 
--select * from [Recipe_Tempory]

( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50)
) 
as
DECLARE	@return_value int
set @return_value =[dbo].[fnBasic_RZPT_AmountOfIngredients](@rECIPEnAME , @Rrelease )
if  @return_value = 0 
	begin
		print (''No records for recipe: '' + @rECIPEnAME)
		return(-9)

	end
begin TRY

	IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N''[dbo].[Recipe_Tempory]'') AND type in (N''U''))
	DROP TABLE [dbo].[Recipe_Tempory]
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
INTO            Recipe_Tempory
FROM         dbo.Recipe__RZPT_Query_1(@rECIPEnAME, @Rrelease) AS Recipe__RZPT_Query_1_1
ORDER BY Phase, ContainerNB ASC
return @return_value
end try 

 begin catch
print (''Error Creating Recipe_Temprory table'')
	return -1
 end catch
-- --creates new temprory recipe table [Recipe_Tempory]
--go 
exec [Create_Recipe_Tempory] ''00-0-L842'', ''A''


' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Create_Recipe_Tempory_USER_Compare]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'


/*
DESCRIBTION
Gets RECIPE FORMULATION AND PUTS IT TO the TABLE FOR COMPARE
INPUT PARAMETERS :-- Recipe name  (''01-8-1432''), version number (''0''),  Current user  (''AA'')
IF ANY of PARAMETERS NULL or EMPTY the TEMPRORY FILE is deleted
IF file dooes  not exit , it is being created

THE RESULTS ARE PUT INTO THE TABLE @USER+''_C''!!! FILL interface from this table
*/


CREATE   procedure  [dbo].[Create_Recipe_Tempory_USER_Compare] 

--go
--exec [Create_Recipe_Tempory_USER_Compare] ''01-8-1432'', ''0'',''AG''

( 
 @rECIPEnAME NVARCHAR(50), -- Recipe name ''01-8-1432''
 @Rrelease NVARCHAR(50),	--version number ''0''
 @user NVARCHAR(50)	
 			-- Current user defind by Host programm
) 
as

DECLARE	@return_value int
Set @user=@user+''_C''
--START BLOACK 1. CHECKIN if to delete the temprory Compare file  
if (@rECIPEnAME IS NULL OR @rECIPEnAME = '''' 
or @user IS NULL OR @user = ''''
or @Rrelease IS NULL OR @Rrelease= '''')
AND EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(@user) AND type in (N''U'')) 
 begin 
	 exec (''DROP TABLE '' + @user) -- delete table conditions fulfilled
 return
 end 
--END BLOCK 1. CHECKIN if to delete the temprory Compare file  

--STARTR BLOCK 2. CHECKIN RECIPE is not empty , continue

set @return_value =[dbo].[fnBasic_RZPT_AmountOfIngredients](@rECIPEnAME , @Rrelease )
if  @return_value = 0 
	begin
		print (''No records for recipe: '' + @rECIPEnAME)
		return -1
	end
--END  BLOCK 2. CHECKIN RECIPE is not empty , continue



-- START BLOCK 3. IF COMPARE FILE IS NOT EXISTS , CREATE IT and fill. IF EXISTS  ADD RECORDS
	IF NOT  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(@user) AND type in (N''U''))

		exec (''
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
							  volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		INTO            '' + @user+''
		FROM         dbo.Recipe__RZPT_Query_1(''''''+ @rECIPEnAME +'''''',''''''+ @Rrelease+'''''') AS Recipe__RZPT_Query_1_1
		ORDER BY Phase, ContainerNB ASC
		'')
else
exec (''
		INSERT INTO   '' + @user+''
							  (Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, volume_Recalc, Volume, 
							  weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID)
		SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, volume_Recalc, Volume, 
							  weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
		FROM         dbo.Recipe__RZPT_Query_1(''''''+ @rECIPEnAME +'''''',''''''+ @Rrelease+'''''') AS Recipe__RZPT_Query_1_1
		'')
-- END. BLOCK 3. IF COMPARE FILE IS NOT EXISTS , CREATE IT and fill. IF EXISTS  ADD RECORDS


--return @return_value






' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Create_Recipe_Tempory1_USER]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'

-- creates new temprory recipe table [Recipe_Tempory USER]
--returns 
--''-9'' : if no records in recipe
---''N'': amount of records in recipe
-- ''-1'' : if sever problem executing procedure


CREATE   procedure  [dbo].[Create_Recipe_Tempory1_USER] 

--go
-- Create_Recipe_Tempory1_USER ''00-L-1161'',''A'',''SB''
--  
--select * from SB
( 
    @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50), @user NVARCHAR(50)
) 
as
DECLARE	@return_value int
set @return_value =[dbo].[fnBasic_RZPT_AmountOfIngredients](@rECIPEnAME , @Rrelease )
if  @return_value = 0 
	begin
		exec (''delete from '' +@user)
--exec (''DROP TABLE '' + @user)

		print (''No records for recipe: '' + @rECIPEnAME)
		return(-9)

	end
--begin TRY
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(@user) AND type in (N''U''))
exec (''DROP TABLE '' + @user)
exec (''
SELECT     Id, RecipeName, Release, material, Descr, MatIndex, GRP, density, density_Recalc, percRubber, PHR, PHR_recalc, weight, weight_Recalc, 
                      volume_Recalc, Volume, weighingID, ContainerNB, Phase, SiloId, BalanceID, PriceKG, PriceData, Recipe_Recipe_ID, RecipeID
INTO            '' + @user+'' 
FROM         dbo.Recipe__RZPT_Query_1(''''''+ @rECIPEnAME +'''''',''''''+ @Rrelease+'''''') 
ORDER BY Phase, ContainerNB ASC''
)



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[fnSequence_AmountOfSteps]') AND type in (N'FN', N'IF', N'TF', N'FS', N'FT'))
BEGIN
execute dbo.sp_executesql @statement = N'CREATE FUNCTION [dbo].[fnSequence_AmountOfSteps]
(
	@Recipe varchar(50)
, @release varchar (1))
RETURNS  int 
AS
begin
	declare @Amount int
	DECLARE _cursor CURSOR FOR SELECT     COUNT(*) 	FROM         dbo.fn_Sequence_Get(@Recipe, @release) AS fn_Sequence_Get_1
	open _cursor

	FETCH NEXT FROM _cursor INTO  @Amount 
	return @Amount
end
' 
END

GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Test_Recipe_ListOf_Masters_ALL]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
CREATE   procedure [dbo].[Test_Recipe_ListOf_Masters_ALL]
as 
--select * from  fnInsert_RECIPENEW()
select *  from Recipe_ListOf_Masters_ALL(''0'')

' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DELETE_CREATE_ALL_RECIPENew]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'






CREATE PROCEDURE [dbo].[DELETE_CREATE_ALL_RECIPENew]
as

DECLARE @RC int
declare @Dst datetime 
declare @DstTotal datetime 

set @DstTotal = GETDATE()
set @Dst = GETDATE()
begin try
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]
DELETE FROM [NCPD].[dbo].[Recipe_Sequence_Steps]
DELETE FROM [NCPD].[dbo].Recipe_Sequence_Main
DELETE FROM [NCPD].[dbo].[Recipe_Sequence_Commands]
DELETE FROM [NCPD].[dbo].[Recipe_Prop_Free_Info]
DELETE FROM [NCPD].[dbo].[Recipe_Prop_Free_Text]
DELETE FROM [NCPD].[dbo].[Recipe_Recipe]
DELETE FROM [NCPD].[dbo].[Recipe_Prop_Main]
DELETE FROM [NCPD].[dbo].[Mixer_InfoBasic]
DELETE FROM [NCPD].[dbo].[Recipe_Group]
--Ingredient Group tables
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]
DELETE FROM [NCPD].[dbo].[Ingred_Free_Info]
DELETE FROM [NCPD].[dbo].[Ingred_Comments]
DELETE FROM [NCPD].[dbo].[Ingredient_phys_Properties]
DELETE FROM [NCPD].[dbo].[Ingredient_Code]

DELETE FROM [NCPD].[dbo].[Ingredient_Warehouse]
DELETE FROM [NCPD].[dbo].[Ingred_Preise]
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]
DELETE FROM [NCPD].[dbo].[Ingredient_Aeging_Code]
DELETE FROM [NCPD].[dbo].[Ingredient_Vulco_Code]
DELETE FROM [NCPD].[dbo].[Ingred_Group]
--Ingred Safety
DELETE FROM [NCPD].[dbo].[Ingred_Safety_Components]
DELETE FROM [NCPD].[dbo].[Ingred_Safety_Main]
DELETE FROM [NCPD].[dbo].[Ingred_Preise]
--DELETE FROM [NCPD].[dbo].[Casno_ID]
DELETE FROM [NCPD].[dbo].[Tradename_Main]
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]
DELETE FROM [NCPD].[dbo].[Vendor_Contact]
DELETE FROM [NCPD].[dbo].[Vendor]
end try

begin catch
select 	''Return Value'' = @@error, ''procedure Deleting''= ERROR_MESSAGE(), ''time''=   datediff(ss,@Dst,GETDATE()  )
end catch

begin try

set @Dst = GETDATE()
set	@RC = 1
EXEC [dbo].[Import_to_Recipe_Group__1]
SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_Recipe_Group__1 '', ''time''=   datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
set	@RC = 2
EXEC [dbo].[Import_to_Ingred_Group__1]
SELECT	''Return Value'' = @RC, ''Import_to_Ingred_Group__1 '', ''time''=   datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
set	@RC = 3
EXEC [NCPD].[dbo].[Import_to_MixerInfoBasic_00__2] 
SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_MixerInfoBasic_00__2 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 4
EXEC [NCPD].[dbo].[Import_to_Recipe_Prop_Main__3] 
SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_Recipe_Prop_Main__3 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 5
EXEC [NCPD].[dbo].[Insert_to_REcipe_Prop_Free_info__4] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_to_REcipe_Prop_Free_info__4 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 6
EXEC [NCPD].[dbo].[Insert_to_REcipe_Prop_Free_Text__5] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_to_REcipe_Prop_Free_Text__5 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 7
EXEC [NCPD].[dbo].[Import_Ingred_Code_Primary__6] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Import_Ingred_Code_Primary__6 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 8
EXEC [NCPD].[dbo].[Import_to_Recipe_Recipe_Actual__7] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_Recipe_Recipe_Actual__7 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 9

EXEC [NCPD].[dbo].[Insert_Sequence_Steps_SSSS__8] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Steps_SSSS__8 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 10
EXEC [NCPD].[dbo].[Insert_Sequence_Main__9] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Main__9 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 11
EXEC [NCPD].[dbo].[Insert_Sequence_Commands__10] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Commands__10 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 12
EXEC [NCPD].[dbo].[Insert_Sequence_Steps__11] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Steps__11 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 13
EXEC [NCPD].[dbo].[Insert_Into_Recipe_Test_Procedures_12]  
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Recipe_Test_Procedures_12'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 14
EXEC [NCPD].[dbo].[Insert_Into_Ingredient_phys_Properties_13] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_phys_Properties_13'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 15
EXEC [NCPD].[dbo].[Insert_into_Ingred_Free_Info__14]
--SELECT	''Return Value'' = @RC, ''procedure''=''[Insert_into_Ingred_Free_Info__14]'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 16
EXEC [NCPD].[dbo].[INSERT_INTO_Ingred_Comments_14] 
--SELECT	''Return Value'' = @RC, ''procedure''=''[INSERT_INTO_Ingred_Comments_14]'', ''time''=   datediff(ss,@Dst,GETDATE()  )



set @Dst = GETDATE()
set	@RC = 18
EXEC [NCPD].[dbo].[Insert_Into_Ingredient_Vulco_Code_15] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_Vulco_Code_15'', ''time''=   datediff(ss,@Dst,GETDATE()  )



set @Dst = GETDATE()
set	@RC = 19
EXEC [NCPD].[dbo].[Insert_Into_Ingredient_Aeging_Code_16] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_Aeging_Code_16'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 20
EXEC [NCPD].[dbo].[Insert_Into_Ingredient_Warehouse_17] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_Warehouse_17'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 21
EXEC [NCPD].[dbo].[Insert_Vendor_17] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Vendor_17'', ''time''=   datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
set	@RC = 22
EXEC [NCPD].[dbo].[INSERT_INTO_TRADENAME_MAIN_18] 
--SELECT	''Return Value'' = @RC, ''procedure''=''INSERT_INTO_TRADENAME_MAIN_18'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 23
EXEC [NCPD].[dbo].[Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
set	@RC = 24
EXEC [NCPD].[dbo].[Insert_Into__INTRF_IngredientCode_ID__Vendor_ID_18] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into__INTRF_IngredientCode_ID__Vendor_ID_18'', ''time''=   datediff(ss,@Dst,GETDATE()  )

--GO


--DECLARE @RC int

--EXEC [NCPD].[dbo].[Insert_CASNO_ID_19] 
--GO
set @Dst = GETDATE()
set	@RC = 25
EXEC [NCPD].[dbo].[Insert_GLTABLE_20] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_GLTABLE_20'', ''time''=   datediff(ss,@Dst,GETDATE()  )

--GO
--GO
--DECLARE @RC int


set @Dst = GETDATE()
set	@RC = 26
EXEC [NCPD].[dbo].[Insert_Into_Recipe_csv_21] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Recipe_csv_21'', ''time''= @DstTotal- datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
set	@RC = 27
EXEC [NCPD].[dbo].[Insert_Production_plan_22] 
--SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Production_plan_22'', ''time''= @DstTotal- datediff(ss,@Dst,GETDATE()  )
--set @Dst = GETDATE()

set	@RC = 27
EXEC [NCPD].[dbo].[INSERT_INTO_Ingred_Preise_14] 
--SELECT	''Return Value'' = @RC, ''procedure''=''INSERT_INTO_Ingred_Preise_14'', ''time''=   datediff(ss,@Dst,GETDATE()  )
return 0
end try 
begin catch
return @RC
end catch





' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[DELETE_CREATE_ALL_RECIPE]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE PROCEDURE [dbo].[DELETE_CREATE_ALL_RECIPE]
as

DECLARE @RC int
declare @Dst datetime 
declare @DstTotal datetime 

set @DstTotal = GETDATE()
set @Dst = GETDATE()
begin try
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]
DELETE FROM [NCPD].[dbo].[Recipe_Sequence_Steps]
DELETE FROM [NCPD].[dbo].Recipe_Sequence_Main
DELETE FROM [NCPD].[dbo].[Recipe_Sequence_Commands]
DELETE FROM [NCPD].[dbo].[Recipe_Prop_Free_Info]
DELETE FROM [NCPD].[dbo].[Recipe_Prop_Free_Text]
DELETE FROM [NCPD].[dbo].[Recipe_Recipe]
DELETE FROM [NCPD].[dbo].[Recipe_Prop_Main]
DELETE FROM [NCPD].[dbo].[Mixer_InfoBasic]
DELETE FROM [NCPD].[dbo].[Recipe_Group]
--Ingredient Group tables
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]
DELETE FROM [NCPD].[dbo].[Ingred_Free_Info]
DELETE FROM [NCPD].[dbo].[Ingred_Comments]
DELETE FROM [NCPD].[dbo].[Ingredient_phys_Properties]
DELETE FROM [NCPD].[dbo].[Ingredient_Code]

DELETE FROM [NCPD].[dbo].[Ingredient_Warehouse]
DELETE FROM [NCPD].[dbo].[Ingred_Preise]
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]
DELETE FROM [NCPD].[dbo].[Ingredient_Aeging_Code]
DELETE FROM [NCPD].[dbo].[Ingredient_Vulco_Code]
DELETE FROM [NCPD].[dbo].[Ingred_Group]
--Ingred Safety
DELETE FROM [NCPD].[dbo].[Ingred_Safety_Components]
DELETE FROM [NCPD].[dbo].[Ingred_Safety_Main]
DELETE FROM [NCPD].[dbo].[Ingred_Preise]
--DELETE FROM [NCPD].[dbo].[Casno_ID]
DELETE FROM [NCPD].[dbo].[Tradename_Main]
DELETE FROM [NCPD].[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]
DELETE FROM [NCPD].[dbo].[Vendor_Contact]
DELETE FROM [NCPD].[dbo].[Vendor]
end try

begin catch
select 	''Return Value'' = @@error, ''procedure Deleting''= ERROR_MESSAGE(), ''time''=   datediff(ss,@Dst,GETDATE()  )
end catch



set @Dst = GETDATE()
EXEC	@RC = [dbo].[Import_to_Recipe_Group__1]
SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_Recipe_Group__1 '', ''time''=   datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
EXEC	 @RC = [dbo].[Import_to_Ingred_Group__1]
SELECT	''Return Value'' = @RC, ''Import_to_Ingred_Group__1 '', ''time''=   datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Import_to_MixerInfoBasic_00__2] 
SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_MixerInfoBasic_00__2 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Import_to_Recipe_Prop_Main__3] 
SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_Recipe_Prop_Main__3 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_to_REcipe_Prop_Free_info__4] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_to_REcipe_Prop_Free_info__4 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_to_REcipe_Prop_Free_Text__5] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_to_REcipe_Prop_Free_Text__5 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Import_Ingred_Code_Primary__6] 
SELECT	''Return Value'' = @RC, ''procedure''=''Import_Ingred_Code_Primary__6 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Import_to_Recipe_Recipe_Actual__7] 
SELECT	''Return Value'' = @RC, ''procedure''=''Import_to_Recipe_Recipe_Actual__7 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Sequence_Steps_SSSS__8] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Steps_SSSS__8 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Sequence_Main__9] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Main__9 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Sequence_Commands__10] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Commands__10 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Sequence_Steps__11] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Sequence_Steps__11 '', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Into_Recipe_Test_Procedures_12]  
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Recipe_Test_Procedures_12'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Into_Ingredient_phys_Properties_13] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_phys_Properties_13'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_into_Ingred_Free_Info__14]
SELECT	''Return Value'' = @RC, ''procedure''=''[Insert_into_Ingred_Free_Info__14]'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[INSERT_INTO_Ingred_Comments_14] 
SELECT	''Return Value'' = @RC, ''procedure''=''[INSERT_INTO_Ingred_Comments_14]'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[INSERT_INTO_Ingred_Preise_14] 
SELECT	''Return Value'' = @RC, ''procedure''=''INSERT_INTO_Ingred_Preise_14'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Into_Ingredient_Vulco_Code_15] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_Vulco_Code_15'', ''time''=   datediff(ss,@Dst,GETDATE()  )



set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Into_Ingredient_Aeging_Code_16] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_Aeging_Code_16'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Into_Ingredient_Warehouse_17] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Ingredient_Warehouse_17'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Vendor_17] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Vendor_17'', ''time''=   datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[INSERT_INTO_TRADENAME_MAIN_18] 
SELECT	''Return Value'' = @RC, ''procedure''=''INSERT_INTO_TRADENAME_MAIN_18'', ''time''=   datediff(ss,@Dst,GETDATE()  )

set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into__INTRF_IngredientCode_ID__Tradename_Main_ID_19'', ''time''=   datediff(ss,@Dst,GETDATE()  )
set @Dst = GETDATE()

EXECUTE @RC = [NCPD].[dbo].[Insert_Into__INTRF_IngredientCode_ID__Vendor_ID_18] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into__INTRF_IngredientCode_ID__Vendor_ID_18'', ''time''=   datediff(ss,@Dst,GETDATE()  )

--GO


--DECLARE @RC int

--EXECUTE @RC = [NCPD].[dbo].[Insert_CASNO_ID_19] 
--GO
set @Dst = GETDATE()

EXECUTE @RC = [NCPD].[dbo].[Insert_GLTABLE_20] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_GLTABLE_20'', ''time''=   datediff(ss,@Dst,GETDATE()  )

--GO
--GO
--DECLARE @RC int


set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Into_Recipe_csv_21] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Into_Recipe_csv_21'', ''time''= @DstTotal- datediff(ss,@Dst,GETDATE()  )


set @Dst = GETDATE()
EXECUTE @RC = [NCPD].[dbo].[Insert_Production_plan_22] 
SELECT	''Return Value'' = @RC, ''procedure''=''Insert_Production_plan_22'', ''time''= @DstTotal- datediff(ss,@Dst,GETDATE()  )




' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[generate_CSVColumn_1]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'
-- Batch submitted through debugger: SQLQuery52.sql|7|0|C:\Users\mcsupport\AppData\Local\Temp\1\~vsB35D.sql







CREATE procedure   [dbo].[generate_CSVColumn_1] 
	-- Add the parameters for the stored procedure here
   ( @rECIPEnAME NVARCHAR(50) -- basic recipe it is used to get ingredients
   , @Rrelease NVARCHAR(50)
   ,@Column int
   ,@order NVARCHAR(50)
   ,@amount int
   ,@rECIPEnAME_1 NVARCHAR(50)
   , @Rrelease_1 NVARCHAR(50) )
-- EXEC	@return_value = [dbo].[generate_CSVColumn_1] ''37-0-1637'', ''0'', 5,''1111'',66,''37-0-1637'',''0''

as  
	DECLARE @count as int
	DECLARE @count1 as int
	Declare @_step Int, @PLCCode varchar (50), @Command_Param varchar (50)
	declare @str varchar(200)
	
	
--- check are there is sequence for that recipe!!!!

if (@rECIPEnAME_1 is null or @Rrelease_1 is null )
begin
DECLARE	@return_value int
execute @return_value =  generate_CSVColumn @rECIPEnAME,@Rrelease,@Column,@order,@amount
 return @return_value
end


-- THE RECIPE IS From first recipe @rECIPEnAME THE SEQUENCE WILL BE FROM second @rECIPEnAME_1!!!! 

if  dbo.fnBasic_RZPT_AmountOfIngredients(@rECIPEnAME,@Rrelease)=0 
		begin 
		print  ''MCDebugger:Error: no ingredients for  recipe ''+@rECIPEnAME +'':''+ @Rrelease;
		print  '' OR the recipe number or reelease are wrong''
		print  ''MCDebugger: Procedure aborted'';

		return -1992 
		end
		
if  dbo.fnSequence_AmountOfSteps(@rECIPEnAME_1,@Rrelease_1)=0 
		begin 
		print  ''MCDebugger:Error: no parameters for sequence for recipe ''+@rECIPEnAME_1 +'':''+ @Rrelease_1;
		print  ''MCDebugger: Procedure aborted'';
SELECT     Code, Release, Mixer_Code, Info, Status, UpdatedOn, UpdatedBy, RIGHT(Code, 4) AS Expr1
FROM         Recipe_Sequence_Main
WHERE     (RIGHT(Code, 4) = RIGHT(@rECIPEnAME_1,4))
return -1991 
		end
--select ''amount of Ingredients''+ Cast (dbo.fnBasic_RZPT_AmountOfIngredients(@rECIPEnAME,@Rrelease) as varchar(10))
--select ''amount of Steps''+ Cast(dbo.fnSequence_AmountOfSteps(@rECIPEnAME,@Rrelease)as varchar(10))
--select ''amount of Ingredients''+ Cast (dbo.fnBasic_RZPT_AmountOfIngredients(@rECIPEnAME,@Rrelease) as varchar(10))


begin try  --TRY 1

	if  len (@rECIPEnAME+'' ''+ @order) >= 17 
	begin 
		print  ''MCDebugger: Condition len (@rECIPEnAME+ @order) >= 17 : ''+ cast(len (@rECIPEnAME+'' ''+ @order) as varchar(50));
		print  ''MCDebugger: Procedure aborted'';
		return -10 
	end
		
	-- Step(1) cleaning up all records in chosen column 
	set @PLCCode=''0''+''.''
	-- setting 0. to all column cells
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+ @PLCCode+'''''' where ID>4 ''
	exec (@str)
	
	
	-- Step(2) Put recipe name in fields 2 and 3
	set @PLCCode=@rECIPEnAME+'' ''+ @order

	if  len (@PLCCode) <> ''''  set @PLCCode= @PLCCode+SPACE(2)
	else set @PLCCode= SPACE(20)
	
	--print char(34)+ @PLCCode+char(34)
	--print ''-- setting @PLCCode=@rECIPEnAME to  field 2''
	set @PLCCode= cast ((char(34)+ @PLCCode+char(34)+SPACE(2)) as varchar(20))
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+@PLCCode+'''''' where ID=2 ''
	exec (@str)
	--print ''-- setting @PLCCode=@rECIPEnAME to  field 3''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+@PLCCode+'''''' where ID=3 ''
	exec (@str)



	-- Step(3) setting number of batches to mix 

	set @PLCCode=@amount
	print ''Step(3) setting number of batches to mix ''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''+ @PLCCode+'' where ID=4 ''
	exec (@str)
end try	--TRY 1
begin catch
	return -1001
end catch 


-- setting mixing sequence parameters use second recipe : @rECIPEnAME_1,@Rrelease_1  


begin try 
	DECLARE _cursor CURSOR FOR  select _step, PLCCode,Command_Param  from fn_Sequence_Get(@rECIPEnAME_1,@Rrelease_1 ) ORDER BY _step
	--  TEST : select * from fn_Sequence_Get('''','''' )
	open _cursor
	set @count= 5
	set @count1=60

	FETCH NEXT FROM _cursor 
	INTO @_step, @PLCCode, @Command_Param

	WHILE @@FETCH_STATUS =0 --AND @count<=40
	BEGIN --begin02
		-- setting PLC code
		set @PLCCode=@PLCCode+''.''
		set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+ 
			@PLCCode+'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		execute  (@str)
		
		-- setting PLC parameters
		set @Command_Param=@Command_Param 
		set @count1=@count+60
		set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+ 
			@Command_Param+'''''' where ID=''+cast(@Count1 as varchar(3))+'' ''
		execute  (@str)

		FETCH NEXT FROM _cursor  -- next
		INTO @_step, @PLCCode, @Command_Param
		set @count=@count+1

	end	--begin02
	CLOSE _cursor;
	DEALLOCATE _cursor;
end try
begin catch
	return -2
end catch


-- here inserting weights from forst recipe !!!! @rECIPEnAME,@Rrelease
print '' inserting weights''
begin try 
	set @count= 125
	set @count1=@count
	DECLARE _cursor1 CURSOR FOR  
	select material,WeighingId,  weight, phase from [Recipe_Basic_RZPT](@rECIPEnAME,@Rrelease )
	order by Phase, ContainerNB
	
	declare @material varchar(50)
	declare @WeighingId varchar(5)
	declare @weight decimal(10,3)
	declare @phase varchar(5)

	open _cursor1
	FETCH NEXT FROM _cursor1 
	INTO @material, @WeighingId, @weight,@phase
	print ''materials''
	--For debug 
	--print convert  (varchar(50), @material  ) + convert(varchar(10),@WeighingId) +  convert(varchar (12),@weight)+ convert(varchar( 10),@phase)

	WHILE @@FETCH_STATUS =0  --AND @count<=40
	BEGIN  --begin 03
	--set @material= @material	
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+'' =''''''+ char(34)+  @material+char(34)	+'''''' where ID=''+cast(@Count as varchar(3))+'' ''
					print ''@str=''+@str
		execute  (@str)
		set @Count=@Count+1
	
		set @WeighingId=cast (@WeighingId as varchar(5))+''.''
		set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+ ''=''''''+ cast (@WeighingId as varchar(5)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		execute  (@str)
		set @Count=@Count+1

		set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+ ''=''''''+ cast (@weight as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		execute  (@str)
		set @Count=@Count+1

		set @phase =@phase+''.''
		set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+ ''=''''''+ cast (@phase as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		execute  (@str)
		set @Count=@Count+1



		FETCH NEXT FROM _cursor1 
		INTO @material, @WeighingId, @weight,@phase
	end	--begin 03
	CLOSE _cursor1;
	DEALLOCATE _cursor1;
end try
begin catch
	return -3
end catch

-- set NULL and ZERO fields to weighing after weighing was filled in prev. step
begin try 

while @count<=204
begin 
	set @material =''''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' = ''''''+char(34)+ @material+char(34)+'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		--print @str
	execute  (@str)
	set @Count=@Count+1

set @WeighingId=''0''
	set @WeighingId=cast (@WeighingId as varchar(5))+''.''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@WeighingId as varchar(5)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1

set @weight =0.000
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@weight as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1

	set @phase =''0''
	set @phase =@phase+''.''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@phase as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1
end
end try
begin catch
return -4
end catch 
return 0



' 
END
GO
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[generate_CSVColumn]') AND type in (N'P', N'PC'))
BEGIN
EXEC dbo.sp_executesql @statement = N'



CREATE procedure   [dbo].[generate_CSVColumn] 
	-- Add the parameters for the stored procedure here
   ( @rECIPEnAME NVARCHAR(50), @Rrelease NVARCHAR(50),@Column int, @order NVARCHAR(50), @amount int  )

as  
	DECLARE @count as int
	DECLARE @count1 as int
	Declare @_step Int, @PLCCode varchar (50), @Command_Param varchar (50)
	declare @str varchar(200)
	
	/*
	
	go
DECLARE	@return_value int

EXEC	@return_value = [dbo].[generate_CSVColumn]
		@rECIPEnAME = N''00-0-1834'',
		@Rrelease = N''W'',
		@Column = 14,
		@order = N''98888888888888'',
		@amount = 98

SELECT	''Return Value'' = @return_value

GO
select * from dbo.[recipe.csv]
go
	*/
--- check are there is sequence for that recipe!!!!
if  dbo.fnBasic_RZPT_AmountOfIngredients(@rECIPEnAME,@Rrelease)=0 
		begin 
		print  ''MCDebugger:Error: no ingredients for  recipe ''+@rECIPEnAME +'':''+ @Rrelease;
		print  '' OR the recipe number or reelease are wrong''
		print  ''MCDebugger: Procedure aborted'';

		return -1992 
		end
		
if  dbo.fnSequence_AmountOfSteps(@rECIPEnAME,@Rrelease)=0 
		begin 
		print  ''MCDebugger:Error: no parameters for sequence for recipe ''+@rECIPEnAME +'':''+ @Rrelease;
		print  ''MCDebugger: Procedure aborted'';
SELECT     Code, Release, Mixer_Code, Info, Status, UpdatedOn, UpdatedBy, RIGHT(Code, 4) AS Expr1
FROM         Recipe_Sequence_Main
WHERE     (RIGHT(Code, 4) = RIGHT(@rECIPEnAME,4))
return -1991 
		end
--select ''amount of Ingredients''+ Cast (dbo.fnBasic_RZPT_AmountOfIngredients(@rECIPEnAME,@Rrelease) as varchar(10))
--select ''amount of Steps''+ Cast(dbo.fnSequence_AmountOfSteps(@rECIPEnAME,@Rrelease)as varchar(10))



begin try  --TRY 1
print len (@rECIPEnAME+'' ''+ @order)
	if  len (@rECIPEnAME+'' ''+ @order) >= 17 
		begin 
		print  ''MCDebugger: Condition len (@rECIPEnAME+ @order) >= 17 : ''+ cast(len (@rECIPEnAME+'' ''+ @order) as varchar(50));
		print  ''MCDebugger: Procedure aborted'';
		return -10 
	end
		
	-- Step(1) cleaning up all records in chosen column 
	set @PLCCode=''0''+''.''
	-- setting 0. to all column cells
	set @str= ''update dbo.[Recipe.csv] set Field''+ rtrim(cast (@column as varchar(2)))+'' =''''''+ @PLCCode+'''''' where ID>4 ''
	exec (@str)

	-- Step(2) Put recipe name in fields 2 and 3
	set @PLCCode=@rECIPEnAME+'' ''+ @order
		
	if  len (@PLCCode) <> ''''  set @PLCCode= @PLCCode+SPACE(2)
	else set @PLCCode= SPACE(20)

--print char(34)+ @PLCCode+char(34)
--print ''-- setting @PLCCode=@rECIPEnAME to  field 2''
set @PLCCode= cast ((char(34)+ @PLCCode+char(34)+SPACE(2)) as varchar(20))
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+ @PLCCode +'''''' where ID=2 ''
	exec (@str)
	--print ''-- setting @PLCCode=@rECIPEnAME to  field 3''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' =''''''+ @PLCCode +'''''' where ID=3 ''
	exec (@str)



	-- Step(3) setting number of batches to mix 

	set @PLCCode=@amount
	print ''Step(3) setting number of batches to mix ''
	set @str= ''update dbo.[Recipe.csv] set Field''+ rtrim(cast (@column as varchar(2)))+'' =''+ @PLCCode+'' where ID=4 ''
	exec (@str)
	end try	--TRY 1
	begin catch
	 print (''MCDebugger: procedure aborted. Code:  -1001'')
		return -1001
	end catch 


-- setting mixing sequence parameters

		
begin try 
	DECLARE _cursor CURSOR FOR  select _step, PLCCode,Command_Param  from fn_Sequence_Get(@rECIPEnAME,@Rrelease ) ORDER BY _step
	open _cursor
	set @count= 5
	set @count1=60

	FETCH NEXT FROM _cursor 
	INTO @_step, @PLCCode, @Command_Param

	WHILE @@FETCH_STATUS =0 --AND @count<=40
	BEGIN --begin02
		-- setting PLC code
		set @PLCCode=@PLCCode+''.''
		set @str= ''update dbo.[Recipe.csv] set Field''+ rtrim(cast (@column as varchar(2)))+'' =''''''+ 
			@PLCCode+'''''' where ID=''+ rtrim(cast(@Count as varchar(3)))+'' ''
		execute  (@str)
		
		-- setting PLC parameters
		set @Command_Param=@Command_Param 
		set @count1=@count+60
		set @str= ''update dbo.[Recipe.csv] set Field''+ rtrim(cast (@column as varchar(2)))+'' =''''''+ 
			@Command_Param+'''''' where ID=''+ rtrim(cast(@Count1 as varchar(3)))+'' ''
		execute  (@str)

		FETCH NEXT FROM _cursor  -- next
		INTO @_step, @PLCCode, @Command_Param
		set @count=@count+1

	end	--begin02
	CLOSE _cursor;
	DEALLOCATE _cursor;
end try
begin catch
	 print (''MCDebugger: procedure aborted. Code:  -2'')

	return -2
end catch


-- here inserting weights
print '' inserting weights''
begin try 
	set @count= 125
	set @count1=@count
	DECLARE _cursor1 CURSOR FOR  
	select material,WeighingId,  weight, phase from [Recipe_Basic_RZPT](@rECIPEnAME,@Rrelease )
	order by Phase, ContainerNB
	
	declare @material varchar(50)
	declare @WeighingId varchar(5)
	declare @weight decimal(10,3)
	declare @phase varchar(5)

	open _cursor1
	FETCH NEXT FROM _cursor1 
	INTO @material, @WeighingId, @weight,@phase
	print ''materials''
	--For debug 
	--print convert  (varchar(50), @material  ) + convert(varchar(10),@WeighingId) +  convert(varchar (12),@weight)+ convert(varchar( 10),@phase)

	WHILE @@FETCH_STATUS =0  --AND @count<=40
	BEGIN  --begin 03
	--set @material= @material	
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+'' =''''''+ char(34)+  @material+char(34)	+'''''' where ID=''+cast(@Count as varchar(3))+'' ''
					print ''@str=''+@str
		execute  (@str)
		set @Count=@Count+1
	
		set @WeighingId=cast (@WeighingId as varchar(5))+''.''
		set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+ ''=''''''+ cast (@WeighingId as varchar(5)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		execute  (@str)
		set @Count=@Count+1

			set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+ ''=''''''+ cast (@weight as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		execute  (@str)
		set @Count=@Count+1

		set @phase =@phase+''.''
		set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))
					+ ''=''''''+ cast (@phase as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		execute  (@str)
		set @Count=@Count+1



		FETCH NEXT FROM _cursor1 
		INTO @material, @WeighingId, @weight,@phase
	end	--begin 03
	CLOSE _cursor1;
	DEALLOCATE _cursor1;
end try
begin catch
	 print (''MCDebugger: procedure aborted. Code:  -3'')
	return -3
end catch

-- set NULL and ZERO fields to weighing after weighing was filled in prev. step
begin try 

while @count<=204
begin 
	set @material =''''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+'' = ''''''+char(34)+ @material+char(34)+'''''' where ID=''+cast(@Count as varchar(3))+'' ''
		--print @str
	execute  (@str)
	set @Count=@Count+1

set @WeighingId=''0''
	set @WeighingId=cast (@WeighingId as varchar(5))+''.''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@WeighingId as varchar(5)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1

set @weight =0.000
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@weight as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1

	set @phase =''0''
	set @phase =@phase+''.''
	set @str= ''update dbo.[Recipe.csv] set Field''+cast (@column as varchar(2))+ ''=''''''+ cast (@phase as varchar(50)) +'''''' where ID=''+cast(@Count as varchar(3))+'' ''
			--print @str
	execute  (@str)
	set @Count=@Count+1
end
end try
begin catch
	 print (''MCDebugger: procedure aborted. Code:  -4'')
	return -4
end catch 
return 0
--/*


	
	

' 
END
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ingredient_Code_CASNO_ID]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingredient_Code]'))
ALTER TABLE [dbo].[Ingredient_Code]  WITH NOCHECK ADD  CONSTRAINT [FK_Ingredient_Code_CASNO_ID] FOREIGN KEY([Cas_Number])
REFERENCES [dbo].[CASNO_ID] ([CAS_Number])
NOT FOR REPLICATION 
GO
ALTER TABLE [dbo].[Ingredient_Code] NOCHECK CONSTRAINT [FK_Ingredient_Code_CASNO_ID]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ingredient_Code_Ingred_Group]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingredient_Code]'))
ALTER TABLE [dbo].[Ingredient_Code]  WITH NOCHECK ADD  CONSTRAINT [FK_Ingredient_Code_Ingred_Group] FOREIGN KEY([Group])
REFERENCES [dbo].[Ingred_Group] ([Grupp])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Ingredient_Code] CHECK CONSTRAINT [FK_Ingredient_Code_Ingred_Group]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Limits_Procedure]') AND parent_object_id = OBJECT_ID(N'[dbo].[Limits]'))
ALTER TABLE [dbo].[Limits]  WITH CHECK ADD  CONSTRAINT [FK_Limits_Procedure] FOREIGN KEY([ProcID])
REFERENCES [dbo].[Procedure] ([ID])
GO
ALTER TABLE [dbo].[Limits] CHECK CONSTRAINT [FK_Limits_Procedure]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Limits_Recipe1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Limits]'))
ALTER TABLE [dbo].[Limits]  WITH CHECK ADD  CONSTRAINT [FK_Limits_Recipe1] FOREIGN KEY([Recipe_ID])
REFERENCES [dbo].[Recipe] ([ID])
GO
ALTER TABLE [dbo].[Limits] CHECK CONSTRAINT [FK_Limits_Recipe1]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ProcTest_Procedure]') AND parent_object_id = OBJECT_ID(N'[dbo].[ProcTest]'))
ALTER TABLE [dbo].[ProcTest]  WITH CHECK ADD  CONSTRAINT [FK_ProcTest_Procedure] FOREIGN KEY([Proc_ID])
REFERENCES [dbo].[Procedure] ([ID])
GO
ALTER TABLE [dbo].[ProcTest] CHECK CONSTRAINT [FK_ProcTest_Procedure]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ProcTest_Tag]') AND parent_object_id = OBJECT_ID(N'[dbo].[ProcTest]'))
ALTER TABLE [dbo].[ProcTest]  WITH CHECK ADD  CONSTRAINT [FK_ProcTest_Tag] FOREIGN KEY([Tag_ID])
REFERENCES [dbo].[Tag] ([ID])
GO
ALTER TABLE [dbo].[ProcTest] CHECK CONSTRAINT [FK_ProcTest_Tag]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Result_Limits]') AND parent_object_id = OBJECT_ID(N'[dbo].[Result]'))
ALTER TABLE [dbo].[Result]  WITH CHECK ADD  CONSTRAINT [FK_Result_Limits] FOREIGN KEY([Limits_ID])
REFERENCES [dbo].[Limits] ([ID])
GO
ALTER TABLE [dbo].[Result] CHECK CONSTRAINT [FK_Result_Limits]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Result_Mashine]') AND parent_object_id = OBJECT_ID(N'[dbo].[Result]'))
ALTER TABLE [dbo].[Result]  WITH CHECK ADD  CONSTRAINT [FK_Result_Mashine] FOREIGN KEY([Machine_ID])
REFERENCES [dbo].[Mashine] ([ID])
GO
ALTER TABLE [dbo].[Result] CHECK CONSTRAINT [FK_Result_Mashine]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Result_Order1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Result]'))
ALTER TABLE [dbo].[Result]  WITH CHECK ADD  CONSTRAINT [FK_Result_Order1] FOREIGN KEY([0rder_ID])
REFERENCES [dbo].[Order] ([ID])
GO
ALTER TABLE [dbo].[Result] CHECK CONSTRAINT [FK_Result_Order1]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Vendor_ID_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Vendor_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Vendor_ID_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Vendor_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Vendor_ID_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Vendor_ID_TRADENAME_MAIN]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Vendor_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Vendor_ID_TRADENAME_MAIN] FOREIGN KEY([Tradename_Main_ID])
REFERENCES [dbo].[TRADENAME_MAIN] ([Tradename_Main_ID])
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Vendor_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Vendor_ID_TRADENAME_MAIN]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Vendor_ID_VENDOR]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Vendor_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Vendor_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Vendor_ID_VENDOR] FOREIGN KEY([VENDOR_ID])
REFERENCES [dbo].[VENDOR] ([VENDOR_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Vendor_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Vendor_ID_VENDOR]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Vendor_Contact_VENDOR]') AND parent_object_id = OBJECT_ID(N'[dbo].[Vendor_Contact]'))
ALTER TABLE [dbo].[Vendor_Contact]  WITH CHECK ADD  CONSTRAINT [FK_Vendor_Contact_VENDOR] FOREIGN KEY([VENDOR_ID])
REFERENCES [dbo].[VENDOR] ([VENDOR_ID])
GO
ALTER TABLE [dbo].[Vendor_Contact] CHECK CONSTRAINT [FK_Vendor_Contact_VENDOR]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Tradename_Main_ID_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Tradename_Main_ID_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Tradename_Main_ID_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Tradename_Main_ID_TRADENAME_MAIN]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Tradename_Main_ID_TRADENAME_MAIN] FOREIGN KEY([Tradename_Main_ID])
REFERENCES [dbo].[TRADENAME_MAIN] ([Tradename_Main_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Tradename_Main_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Tradename_Main_ID_TRADENAME_MAIN]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_ProfileTest_Profile]') AND parent_object_id = OBJECT_ID(N'[dbo].[ProfileTest]'))
ALTER TABLE [dbo].[ProfileTest]  WITH CHECK ADD  CONSTRAINT [FK_ProfileTest_Profile] FOREIGN KEY([Profile_ID])
REFERENCES [dbo].[Profile] ([ID])
GO
ALTER TABLE [dbo].[ProfileTest] CHECK CONSTRAINT [FK_ProfileTest_Profile]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Recipe_Prop_Main_Mixer_InfoBasic]') AND parent_object_id = OBJECT_ID(N'[dbo].[Recipe_Prop_Main]'))
ALTER TABLE [dbo].[Recipe_Prop_Main]  WITH NOCHECK ADD  CONSTRAINT [FK_Recipe_Prop_Main_Mixer_InfoBasic] FOREIGN KEY([Mixer_Code])
REFERENCES [dbo].[Mixer_InfoBasic] ([Code])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Recipe_Prop_Main] CHECK CONSTRAINT [FK_Recipe_Prop_Main_Mixer_InfoBasic]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Recipe_Prop_Main_Recipe_Group]') AND parent_object_id = OBJECT_ID(N'[dbo].[Recipe_Prop_Main]'))
ALTER TABLE [dbo].[Recipe_Prop_Main]  WITH NOCHECK ADD  CONSTRAINT [FK_Recipe_Prop_Main_Recipe_Group] FOREIGN KEY([Detailed_Group])
REFERENCES [dbo].[Recipe_Group] ([Detailed_Group])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Recipe_Prop_Main] CHECK CONSTRAINT [FK_Recipe_Prop_Main_Recipe_Group]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ingredient_phys_Properties_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingredient_phys_Properties]'))
ALTER TABLE [dbo].[Ingredient_phys_Properties]  WITH NOCHECK ADD  CONSTRAINT [FK_Ingredient_phys_Properties_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Ingredient_phys_Properties] CHECK CONSTRAINT [FK_Ingredient_phys_Properties_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ingred_Preise_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingred_Preise]'))
ALTER TABLE [dbo].[Ingred_Preise]  WITH NOCHECK ADD  CONSTRAINT [FK_Ingred_Preise_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
GO
ALTER TABLE [dbo].[Ingred_Preise] NOCHECK CONSTRAINT [FK_Ingred_Preise_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ingred_Free_Info_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingred_Free_Info]'))
ALTER TABLE [dbo].[Ingred_Free_Info]  WITH CHECK ADD  CONSTRAINT [FK_Ingred_Free_Info_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Ingred_Free_Info] CHECK CONSTRAINT [FK_Ingred_Free_Info_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Recipe_Recipe_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[Recipe_Recipe]'))
ALTER TABLE [dbo].[Recipe_Recipe]  WITH NOCHECK ADD  CONSTRAINT [FK_Recipe_Recipe_Ingredient_Code] FOREIGN KEY([IngredName])
REFERENCES [dbo].[Ingredient_Code] ([Name])
GO
ALTER TABLE [dbo].[Recipe_Recipe] NOCHECK CONSTRAINT [FK_Recipe_Recipe_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Recipe_Recipe_Recipe_Prop_Main]') AND parent_object_id = OBJECT_ID(N'[dbo].[Recipe_Recipe]'))
ALTER TABLE [dbo].[Recipe_Recipe]  WITH CHECK ADD  CONSTRAINT [FK_Recipe_Recipe_Recipe_Prop_Main] FOREIGN KEY([Recipe_ID])
REFERENCES [dbo].[Recipe_Prop_Main] ([Recipe_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Recipe_Recipe] CHECK CONSTRAINT [FK_Recipe_Recipe_Recipe_Prop_Main]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID_Ingredient_Aeging_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID_Ingredient_Aeging_Code] FOREIGN KEY([Ingredient_Aeging_Code_ID])
REFERENCES [dbo].[Ingredient_Aeging_Code] ([Ingredient_Aeging_Code_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID_Ingredient_Aeging_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Aeging_Code_ID_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ingredient_Warehouse_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingredient_Warehouse]'))
ALTER TABLE [dbo].[Ingredient_Warehouse]  WITH CHECK ADD  CONSTRAINT [FK_Ingredient_Warehouse_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Ingredient_Warehouse] CHECK CONSTRAINT [FK_Ingredient_Warehouse_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Ingred_Info_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingred_Comments]'))
ALTER TABLE [dbo].[Ingred_Comments]  WITH CHECK ADD  CONSTRAINT [FK_Ingred_Info_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Ingred_Comments] CHECK CONSTRAINT [FK_Ingred_Info_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK__INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID_Ingredient_Vulco_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]'))
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID]  WITH CHECK ADD  CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID_Ingredient_Vulco_Code] FOREIGN KEY([Ingredient_Vulco_Code_ID])
REFERENCES [dbo].[Ingredient_Vulco_Code] ([Ingredient_Vulco_Code_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[_INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID] CHECK CONSTRAINT [FK__INTRF_IngredientCode_ID__Ingredient_Vulco_Code_ID_Ingredient_Vulco_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Tradename_Safety_Main_Ingredient_Code]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingred_Safety_Main]'))
ALTER TABLE [dbo].[Ingred_Safety_Main]  WITH CHECK ADD  CONSTRAINT [FK_Tradename_Safety_Main_Ingredient_Code] FOREIGN KEY([IngredientCode_ID])
REFERENCES [dbo].[Ingredient_Code] ([IngredientCode_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Ingred_Safety_Main] CHECK CONSTRAINT [FK_Tradename_Safety_Main_Ingredient_Code]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_LimitTest_Limits]') AND parent_object_id = OBJECT_ID(N'[dbo].[LimitTest]'))
ALTER TABLE [dbo].[LimitTest]  WITH CHECK ADD  CONSTRAINT [FK_LimitTest_Limits] FOREIGN KEY([Limits_ID])
REFERENCES [dbo].[Limits] ([ID])
GO
ALTER TABLE [dbo].[LimitTest] CHECK CONSTRAINT [FK_LimitTest_Limits]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_LimitTest_ProcTest]') AND parent_object_id = OBJECT_ID(N'[dbo].[LimitTest]'))
ALTER TABLE [dbo].[LimitTest]  WITH CHECK ADD  CONSTRAINT [FK_LimitTest_ProcTest] FOREIGN KEY([Proctest_ID])
REFERENCES [dbo].[ProcTest] ([ID])
GO
ALTER TABLE [dbo].[LimitTest] CHECK CONSTRAINT [FK_LimitTest_ProcTest]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Result_Test_LimitTest]') AND parent_object_id = OBJECT_ID(N'[dbo].[Result_Test]'))
ALTER TABLE [dbo].[Result_Test]  WITH CHECK ADD  CONSTRAINT [FK_Result_Test_LimitTest] FOREIGN KEY([Limit_Test_ID])
REFERENCES [dbo].[LimitTest] ([ID])
GO
ALTER TABLE [dbo].[Result_Test] CHECK CONSTRAINT [FK_Result_Test_LimitTest]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Result_Test_Result]') AND parent_object_id = OBJECT_ID(N'[dbo].[Result_Test]'))
ALTER TABLE [dbo].[Result_Test]  WITH CHECK ADD  CONSTRAINT [FK_Result_Test_Result] FOREIGN KEY([Result_ID])
REFERENCES [dbo].[Result] ([ID])
GO
ALTER TABLE [dbo].[Result_Test] CHECK CONSTRAINT [FK_Result_Test_Result]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Recipe_Prop_Free_Text_Recipe_Prop_Main]') AND parent_object_id = OBJECT_ID(N'[dbo].[Recipe_Prop_Free_Text]'))
ALTER TABLE [dbo].[Recipe_Prop_Free_Text]  WITH CHECK ADD  CONSTRAINT [FK_Recipe_Prop_Free_Text_Recipe_Prop_Main] FOREIGN KEY([Recipe_ID])
REFERENCES [dbo].[Recipe_Prop_Main] ([Recipe_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Recipe_Prop_Free_Text] CHECK CONSTRAINT [FK_Recipe_Prop_Free_Text_Recipe_Prop_Main]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Recipe_Sequence_Main_Recipe_Prop_Main1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Recipe_Sequence_Main]'))
ALTER TABLE [dbo].[Recipe_Sequence_Main]  WITH CHECK ADD  CONSTRAINT [FK_Recipe_Sequence_Main_Recipe_Prop_Main1] FOREIGN KEY([Code], [Release], [Mixer_Code])
REFERENCES [dbo].[Recipe_Prop_Main] ([Code], [Release], [Mixer_Code])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Recipe_Sequence_Main] CHECK CONSTRAINT [FK_Recipe_Sequence_Main_Recipe_Prop_Main1]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Recipe_Prop_Free_Info_Recipe_Prop_Main]') AND parent_object_id = OBJECT_ID(N'[dbo].[Recipe_Prop_Free_Info]'))
ALTER TABLE [dbo].[Recipe_Prop_Free_Info]  WITH CHECK ADD  CONSTRAINT [FK_Recipe_Prop_Free_Info_Recipe_Prop_Main] FOREIGN KEY([Recipe_ID])
REFERENCES [dbo].[Recipe_Prop_Main] ([Recipe_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Recipe_Prop_Free_Info] CHECK CONSTRAINT [FK_Recipe_Prop_Free_Info_Recipe_Prop_Main]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Tradename_Safety_Components_CASNO_ID1]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingred_Safety_Components]'))
ALTER TABLE [dbo].[Ingred_Safety_Components]  WITH NOCHECK ADD  CONSTRAINT [FK_Tradename_Safety_Components_CASNO_ID1] FOREIGN KEY([CAS_Number])
REFERENCES [dbo].[CASNO_ID] ([CAS_Number])
GO
ALTER TABLE [dbo].[Ingred_Safety_Components] NOCHECK CONSTRAINT [FK_Tradename_Safety_Components_CASNO_ID1]
GO
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Tradename_Safety_Components_Tradename_Safety_Main]') AND parent_object_id = OBJECT_ID(N'[dbo].[Ingred_Safety_Components]'))
ALTER TABLE [dbo].[Ingred_Safety_Components]  WITH CHECK ADD  CONSTRAINT [FK_Tradename_Safety_Components_Tradename_Safety_Main] FOREIGN KEY([Tradename_Safety_Main_ID])
REFERENCES [dbo].[Ingred_Safety_Main] ([Tradename_Safety_Main_ID])
GO
ALTER TABLE [dbo].[Ingred_Safety_Components] CHECK CONSTRAINT [FK_Tradename_Safety_Components_Tradename_Safety_Main]
