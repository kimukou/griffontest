set GROOVY_HOME=C:\opt\groovy-1.8.0
set GRIFFON_HOME=C:\opt\griffon-0.9.3-beta-2
set JAVA_HOME=c:\opt\jdk
set PATH=%GROOVY_HOME%/bin;%GRIFFON_HOME%/bin;%JAVA_HOME%/bin

call griffon replace-artifact -fileType=java -type=controller sqljettest.SqljettestController
call griffon replace-artifact -fileType=java -type=service sqljettest.SqljettestService
call griffon replace-artifact -fileType=java -type=model sqljettest.SqljettestModel
