set GROOVY_HOME=c:\opt\groovy-1.7.5
set GRIFFON_HOME=c:\opt\griffon-0.9
set JAVA_HOME=c:\opt\jdk

for /F "delims=" %%s in ('cd') do @set PWD=%%s
set JAVA_OPTS=-Djava.library.path="%PWD%\lib\libraries\opengl\library"

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

griffon run-app
