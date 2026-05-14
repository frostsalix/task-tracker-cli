@echo off
setlocal

set "PROJECT_DIR=%~dp0"
set "CLASSES_DIR=%PROJECT_DIR%target\classes"

if not exist "%CLASSES_DIR%" (
    echo [tt] First run — compiling...
    call "%PROJECT_DIR%mvnw.cmd" -f "%PROJECT_DIR%pom.xml" compile -q
    if errorlevel 1 (
        echo [tt] Build failed.
        exit /b 1
    )
)

:: Build classpath file if missing or if pom.xml was updated
set "CPFILE=%PROJECT_DIR%target\classpath.txt"
if not exist "%CPFILE%" (
    call "%PROJECT_DIR%mvnw.cmd" -f "%PROJECT_DIR%pom.xml" -q dependency:build-classpath -Dmdep.outputFile="%CPFILE%" >nul 2>&1
)

set "CP=%CLASSES_DIR%"
if exist "%CPFILE%" (
    for /f "usebackq delims=" %%i in ("%CPFILE%") do set "CP=%CLASSES_DIR%;%%i"
)

java -cp "%CP%" com.tasktracker.Main %*
