CREATE TABLE MCRecipeUsers
(
id int IDENTITY(1,1) PRIMARY KEY,
userName varchar(50) NOT NULL,
pass varchar(50) NOT NULL,
role varchar(50) NOT NULL,
dateCreated datetime NOT NULL DEFAULT GETDATE(),
dateChanged datetime NOT NULL DEFAULT GETDATE()
)


insert into MCRecipeUsers values (
'rule_free_entrance',
'',
'enabled',
getDate(),
getDate()
)