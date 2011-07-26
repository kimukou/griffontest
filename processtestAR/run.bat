call ../setEnvG9.bat

::for /F "delims=" %%s in ('cd') do @set PWD=%%s
::set JAVA_OPTS=-Djava.library.path=%PWD%\lib\libraries\opengl\library;%PWD%\lib\native

griffon run-app
