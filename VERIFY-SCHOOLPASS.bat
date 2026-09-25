@echo off
setlocal
cd /d "%~dp0"
echo ============================================
echo SchoolPass Rwanda - Verification
 echo ============================================
where java >nul 2>nul || (echo Java is not installed or not on PATH.& pause&exit /b 1)
where mvn >nul 2>nul || (echo Maven is not installed or not on PATH.& pause&exit /b 1)
java -version
mvn -version
mvn clean test
if errorlevel 1 (
  echo.
  echo VERIFICATION FAILED. SchoolPass was NOT started.
  pause
  exit /b 1
)
echo.
echo VERIFICATION PASSED.
echo The project compiled and tests passed.
pause
