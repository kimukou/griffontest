call ../setEnvG9.bat

::call griffon compile
call griffon -Dgriffon.disable.threading.injection=true compile 

pause
