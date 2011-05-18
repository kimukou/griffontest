::set GROOVY_HOME=C:\opt\groovypp-0.4.116
set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-1
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

::call griffon compile
call griffon -Dgriffon.disable.threading.injection=true compile 

pause
