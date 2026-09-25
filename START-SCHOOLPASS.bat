@echo off
setlocal
cd /d "%~dp0"
where java >nul 2>nul || (echo Java 21 is required and must be on PATH.& pause&exit /b 1)
where mvn >nul 2>nul || (echo Maven 3.9+ is required and must be on PATH.& pause&exit /b 1)
echo Running verification before starting SchoolPass...
mvn clean test
if errorlevel 1 (
  echo.
  echo BUILD/TEST FAILED. The server will NOT start.
  echo Send the error output to the project developer.
  pause
  exit /b 1
)
echo.
echo Tests passed. Starting SchoolPass...
mvn spring-boot:run
pause
