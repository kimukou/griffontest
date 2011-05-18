set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-1
set JAVA_HOME=c:\opt\jdk

set JAVA_OPTS=-Xms256m -Xmx256m
set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin
set JAVA_OPTS="-Xmx1024m"


call griffon clean
call griffon prod package jar
::call griffon  -Dgriffon.disable.threading.injection=true prod package jar
