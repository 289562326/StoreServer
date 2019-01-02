@echo off
set PATH=%path%
title %cd% 
color 0D
set JAVA_HOME=%JAVA_HOME%
@echo %PATH%|findstr "%JAVA_HOME%"
if %errorlevel%==0 (
	goto start
) else (
	java -version
	if %errorlevel%==0 (
		goto start
	) else (	
		goto exit
	)
)

:start
cd ..
java -jar storeserver-0.0.1-SNAPSHOT.jar
@pause
:exit
@echo "this system has no jdk"
@pause
