set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-w
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin


::call griffon clean
::griffon package izpack
call griffon prepare-izpack
call griffon create-izpack

