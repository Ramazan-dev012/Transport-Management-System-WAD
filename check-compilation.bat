@echo off
echo ========================================
echo Проверка компиляции Transport System
echo ========================================
echo.

echo Шаг 1: Очистка предыдущей сборки...
call mvnw.cmd clean

echo.
echo Шаг 2: Компиляция проекта...
call mvnw.cmd compile

echo.
echo ========================================
echo Проверка завершена!
echo ========================================
echo.
echo Если компиляция прошла успешно (BUILD SUCCESS),
echo значит код написан правильно.
echo.
echo Ошибки в IDE можно исправить так:
echo 1. Откройте панель Maven справа
echo 2. Нажмите кнопку "Reload All Maven Projects"
echo 3. Подождите завершения индексации
echo.
pause

