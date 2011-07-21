set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-2
set JAVA_HOME=c:\opt\jdk

::for /F "delims=" %%s in ('cd') do @set PWD=%%s
::set JAVA_OPTS=-Djava.library.path=%PWD%\lib\libraries\opengl\library;%PWD%\lib\native
set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

griffon run-app
