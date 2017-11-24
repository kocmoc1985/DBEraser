set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
go



/*
Procedure ADDS record with FreeInfo
--exec [prc_ITF_RECIPE_FreeInfo_Update] '@Recipe_ID','@NoteName','@NoteValue','@UpdatedOn','@UpdatedBy'

*/


ALTER   procedure  [dbo].[prc_ITF_RECIPE_FreeInfo_Update]
( 
@Code nvarchar(50),  --current recipe 
@NoteName nvarchar(50), -- current note name must be empty! 
@NoteValue nvarchar(50), -- input value 
@UpdatedOn nvarchar(50),
@UpdatedBy nvarchar(50)
)
as


-- check if exists NoteName for current recipe
declare @FL  nvarchar(50) 
set @FL = 
(SELECT     distinct   Recipe_Prop_Free_Info.NoteName
FROM         Recipe_Prop_Free_Info INNER JOIN
                      Recipe_Prop_Main ON Recipe_Prop_Free_Info.Recipe_ID = Recipe_Prop_Main.Recipe_ID
WHERE     (Recipe_Prop_Free_Info.NoteName = @NoteName) AND (Recipe_Prop_Main.Code = @Code))
                     
select '@FL' =@FL
--return 0

if @FL is NULL  -- NoteName for current recipe NOT EXISTS, insert new record
begin 
--select 'INSERT'
--return 0
INSERT INTO Recipe_Prop_Free_Info
                      (Recipe_ID, NoteName, NoteValue, UpdatedOn, UpdatedBy)
SELECT DISTINCT 
                      Recipe_Prop_Main.Recipe_ID, @NoteName AS NoteName, @NoteValue AS NoteValue, getdate() AS UpdatedOn, @UpdatedBy AS UpdatedBy
FROM         Recipe_Prop_Main LEFT OUTER JOIN
                      Recipe_Prop_Free_Info AS Recipe_Prop_Free_Info_1 ON Recipe_Prop_Main.Recipe_ID = Recipe_Prop_Free_Info_1.Recipe_ID
WHERE     (Recipe_Prop_Main.Code = @code)
end 

else -- NoteName for current recipe  EXISTS!! Update
	begin
--select 'update'
UPDATE    Recipe_Prop_Free_Info
SET              Recipe_ID = Recipe_Prop_Main.Recipe_ID, NoteValue = @NoteValue, UpdatedBy = @UpdatedBy, UpdatedOn =getdate()
FROM         Recipe_Prop_Main INNER JOIN
                      Recipe_Prop_Free_Info ON Recipe_Prop_Main.Recipe_ID = Recipe_Prop_Free_Info.Recipe_ID
WHERE     (Recipe_Prop_Main.Code = @code) AND  (NoteName = @NoteName)
	END
	return
--go 
--exec [prc_ITF_RECIPE_FreeInfo_Update] '00-0-N952','Color:','black','',  'CB'
--




