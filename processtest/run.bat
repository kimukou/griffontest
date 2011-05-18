set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-1
set JAVA_HOME=c:\opt\jdk

for /F "delims=" %%s in ('cd') do @set PWD=%%s
echo %PWD%
::set JAVA_OPTS=-Djava.library.path="%PWD%\lib\libraries\opengl\library"
echo %JAVA_OPTS%

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

griffon run-app
