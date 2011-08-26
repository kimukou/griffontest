call ../setEnvG9.bat

set JAVA_OPTS=%JAVA_OPTS% -Xms256m -Xmx256m
set JAVA_OPTS=%JAVA_OPTS% -Xmx1024m


call griffon clean
call griffon prod package jar
::call griffon  -Dgriffon.disable.threading.injection=true prod package jar
