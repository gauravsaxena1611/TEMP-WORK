@echo off
setlocal

REM ── Airborne Copier launcher ──────────────────────────────────────────────
REM Drop this file next to airborne-1.0.0.jar and double-click, or run from
REM any terminal opened in the same folder.

set JAR=%~dp0airborne-1.0.0.jar

REM Resolve java executable — prefer JAVA_HOME, fall back to PATH
if defined JAVA_HOME (
    set JAVA="%JAVA_HOME%\bin\java.exe"
) else (
    set JAVA=java
)

REM Check the JAR is present
if not exist "%JAR%" (
    echo ERROR: airborne-1.0.0.jar not found next to this script.
    echo        Place both files in the same folder and try again.
    pause
    exit /b 1
)

REM Launch — 512 MB initial heap, 2 GB max (adjust -Xmx if your VDI is tight)
%JAVA% -Xms512m -Xmx2g -jar "%JAR%"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Application exited with error code %ERRORLEVEL%.
    pause
)

endlocal
