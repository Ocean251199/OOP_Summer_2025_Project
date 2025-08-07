@echo off
echo ============================================
echo    Upload to GitHub - Quick Setup
echo ============================================
echo.

cd /d "c:\Users\noina\Downloads\OOP_Summer_2025_Project-main (2)\OOP_Summer_2025_Project-main"

echo Current directory: %cd%
echo.

echo [1] Checking Git status...
if exist ".git" (
    echo Git repository already exists.
    git status
) else (
    echo Initializing new Git repository...
    git init
    echo.
    echo Adding all files...
    git add .
    echo.
    echo Creating initial commit...
    git commit -m "Library Management System with Middleware API

- Java backend with complete servlet-based middleware layer
- HTML/JavaScript frontend with AJAX integration  
- RESTful API endpoints for all CRUD operations
- Book management system with borrow/return functionality
- Real-time inventory tracking and updates"
)

echo.
echo ============================================
echo Next steps:
echo 1. Create new repository on GitHub.com
echo 2. Copy these commands and run them:
echo.
echo    git remote add origin YOUR_GITHUB_REPO_URL
echo    git branch -M main
echo    git push -u origin main
echo.
echo Replace YOUR_GITHUB_REPO_URL with your actual GitHub repo URL
echo ============================================

pause
