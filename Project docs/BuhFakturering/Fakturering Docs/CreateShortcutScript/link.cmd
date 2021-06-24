@echo off
SETLOCAL ENABLEDELAYEDEXPANSION
SET LinkName=LAFaktureringTest
SET Esc_LinkDest=%%HOMEDRIVE%%%%HOMEPATH%%\Desktop\!LinkName!.lnk
SET Esc_LinkTarget=%CD%\la.jar
REM SET "IconLocation=%CD%\la\ic.ico"
SET cSctVBS=CreateShortcut.vbs
SET LOG=".\%~N0_.log"
((
  echo Set oWS = WScript.CreateObject^("WScript.Shell"^) 
  echo sLinkFile = oWS.ExpandEnvironmentStrings^("!Esc_LinkDest!"^)
  echo Set oLink = oWS.CreateShortcut^(sLinkFile^) 
  echo oLink.TargetPath = oWS.ExpandEnvironmentStrings^("!Esc_LinkTarget!"^)
  echo oLink.IconLocation = "%CD%\la\ic.ico"
  echo oLink.WorkingDirectory = "%CD%"
  echo oLink.Save
)1>!cSctVBS!
cscript //nologo .\!cSctVBS!
DEL !cSctVBS! /f /q
)1>>!LOG! 2>>&1