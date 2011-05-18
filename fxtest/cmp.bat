set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-1
set JAVA_HOME=c:\opt\jdk
set JAVAFX_HOME=D:\Tooldev\javafx123

echo %JAVA_OPTS%
set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin
::set CLASSPATH=%JAVAFX_HOME%/lib/shared/;%JAVAFX_HOME%/lib/desktop/

call griffon compile
pause
