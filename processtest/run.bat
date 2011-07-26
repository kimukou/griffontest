call ../setEnvG9.bat

for /F "delims=" %%s in ('cd') do @set PWD=%%s
echo %PWD%
::set JAVA_OPTS=-Djava.library.path="%PWD%\lib\libraries\opengl\library"
echo %JAVA_OPTS%

griffon run-app
