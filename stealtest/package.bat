::パッケージングバッチファイル
::DTagSendor\dist\jar に作成されます

set GROOVY_HOME=C:\grails\groovy-1.7.4
set GRIFFON_HOME=C:\grails\griffon-0.9
set JAVA_HOME=c:\opt\jdk

set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin


call griffon clean
call griffon prod package
