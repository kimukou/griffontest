set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-2
set JAVA_HOME=c:\opt\jdk
set USER_HOME=%USERPROFILE%
echo %USER_HOME%


set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

call griffon clean
call griffon eclipse-update
