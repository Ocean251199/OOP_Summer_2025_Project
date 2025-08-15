@echo off
echo ============================================
echo    Library Management System - Simple Run
echo ============================================
echo.
echo HƯỚNG DẪN SỬ DỤNG:
echo.
echo 1. Mở Command Prompt/PowerShell
echo 2. Chạy lệnh sau:
echo.
echo    cd "D:\CNTT_Shared\OOP\OOP_Summer_2025_Project"
echo    mvn clean compile jetty:run
echo.
echo 3. Truy cập: http://localhost:8080/btl/
echo.
echo ============================================
echo.
echo Bạn có muốn tự động chạy lệnh không? (y/n)
set /p choice=

if /i "%choice%"=="y" (
    echo.
    echo Đang chạy...
    cd /d "D:\CNTT_Shared\OOP\OOP_Summer_2025_Project"
    mvn clean compile jetty:run
) else (
    echo.
    echo Hãy copy và paste lệnh trên vào terminal của bạn.
    echo.
    pause
)
