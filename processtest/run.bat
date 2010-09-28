for /F "delims=" %%s in ('cd') do @set PWD=%%s

set GROOVY_HOME=C:\grails\groovy-1.7.5
set GRIFFON_HOME=C:\grails\griffon-0.9
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin
set JAVA_OPTS=-Djava.library.path="%PWD%\lib\libraries\opengl\library"

griffon run-app
