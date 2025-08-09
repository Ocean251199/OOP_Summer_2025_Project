@echo off
echo ============================================
echo    Library Management System - Demo
echo ============================================
echo.

cd /d "c:\Users\noina\Downloads\OOP_Summer_2025_Project-main (2)\OOP_Summer_2025_Project-main"

echo [1] Testing compilation...
call mvn clean compile
if %ERRORLEVEL% neq 0 (
    echo ERROR: Compilation failed! Check the errors above.
    echo.
    echo Common issues:
    echo - Make sure Java JDK 17+ is installed
    echo - Make sure Maven is installed and in PATH
    echo - Check if there are any compilation errors
    pause
    exit /b 1
)

echo.
echo [2] Building WAR file...
call mvn war:war
if %ERRORLEVEL% neq 0 (
    echo ERROR: WAR build failed!
    pause
    exit /b 1
)

echo.
echo [3] Starting Jetty server...
echo    Access the application at: http://localhost:8080/btl/
echo    Press Ctrl+C to stop the server
echo.

call mvn jetty:run
