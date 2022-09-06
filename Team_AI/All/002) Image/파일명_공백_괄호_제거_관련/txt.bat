@echo off 
setlocal
setlocal EnableDelayedExpansion
for %%a in (*.txt) do (
set f=%%a
set f=!f:^(=!
set f=!f:^)=!
ren "%%a" "!f!"
)