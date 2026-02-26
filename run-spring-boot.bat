@echo off
echo ========================================
echo   Transport Management System
echo   Spring Boot Application
echo ========================================
echo.
echo Starting application...
echo.

cd /d "%~dp0"

rem Run Spring Boot application
call mvnw.cmd spring-boot:run

pause

