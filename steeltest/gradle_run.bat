set GROOVY_HOME=C:\opt\groovy-1.7.6
set GRIFFON_HOME=C:\opt\griffon-0.9.2-beta-3
set GRADLE_HOME=D:\Tooldev\gradle-0.9
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRADLE_HOME%/bin;%JAVA_HOME%/bin;%GRIFFON_HOME%/bin


::call gradle run-app
call gradle clean compile
