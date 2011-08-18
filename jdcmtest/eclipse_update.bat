call ../setEnvG9.bat

set USER_HOME=%USERPROFILE%
echo %USER_HOME%

call griffon clean
call griffon eclipse-update
