@echo off
setlocal

set "DEPLOY_DIR=%LOCALAPPDATA%\MeetingAssistant"
set "EXE=%DEPLOY_DIR%\meeting_assistant.exe"

if not exist "%EXE%" (
    echo ERROR: meeting_assistant.exe not found at %DEPLOY_DIR%
    echo Please extract the deployment package to %DEPLOY_DIR% first.
    pause
    exit /b 1
)

cd /d "%DEPLOY_DIR%"

REM Start llama-server in background if not already running
tasklist /fi "imagename eq llama-server.exe" 2>nul | find /i "llama-server.exe" >nul
if errorlevel 1 (
    if exist "%DEPLOY_DIR%\llm\llama-server.exe" (
        echo Starting llama-server...
        start "" /b "%DEPLOY_DIR%\llm\llama-server.exe" ^
            -m "%DEPLOY_DIR%\llm\phi-4-mini-Q4_K_M.gguf" ^
            --port 8080 --ctx-size 4096 --threads 4
        timeout /t 3 /nobreak >nul
    )
)

echo Starting Meeting Assistant...
start "" /b "%EXE%"

endlocal
