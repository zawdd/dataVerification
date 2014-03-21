@echo off


cd %~dp0
cd ..
call gradle cleanEclipse eclipse
cd bin
pause