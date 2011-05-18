set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-1
set GRADLE_HOME=D:\Tooldev\gradle-1.0-milestone-3
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRADLE_HOME%/bin;%JAVA_HOME%/bin;%GRIFFON_HOME%/bin
::set PATH=%PATH%;%GROOVY_HOME%/bin;%GRADLE_HOME%/bin;%JAVA_HOME%/bin;%GRIFFON_HOME%/bin


::call gradle run-app
call gradle clean compile

pause
