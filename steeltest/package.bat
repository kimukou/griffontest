call ../setEnvG9.bat

set JAVA_OPTS=%JAVA_OPTS% -Xmx1024m

call griffon clean
call griffon prod package
