set GROOVY_HOME=c:\opt\groovy-1.7.6
set GRIFFON_HOME=c:\opt\griffon-0.9.2-beta-3
set JAVA_HOME=c:\opt\jdk
set JAVAFX_HOME=D:\Tooldev\javafx123


echo %JAVA_OPTS%
set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin
::set CLASSPATH=%JAVAFX_HOME%/lib/shared/;%JAVAFX_HOME%/lib/desktop/

griffon run-app
