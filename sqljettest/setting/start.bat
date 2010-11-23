set JAVA_HOME=c:\opt\jdk
set PATH=%JAVA_HOME%/bin

::set JAVA_OPS=-server -Xms1024m -Xmx1024m -XX:PermSize=128m -XX:MaxPermSize=128m -XX:+PrintGCDetails

::set JAVA_OPS=-server -Xmn256m -Xms1024m -Xmx1024m -XX:PermSize=256m -Xss256k
::set JAVA_OPS=-server -Xmn128m -Xms512m -Xmx512m -XX:PermSize=128m -Xss256k
set JAVA_OPS=-server -Xmn128m -Xms256m -Xmx256m -XX:PermSize=128m -Xss256k
set JAVA_OPS=%JAVA_OPS% -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled
set JAVA_OPS=%JAVA_OPS% -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=8 -XX:TargetSurvivorRatio=90
::set JAVA_OPS=%JAVA_OPS% -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintClassHistogram -XX:+HeapDumpOnOutOfMemoryError 
set JAVA_OPS=%JAVA_OPS% -Djava.net.preferIPv4Stack=true -Dava.net.preferIPv6Addresses=false
set JAVA_OPS=%JAVA_OPS% -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Dsun.lang.ClassLoader.allowArraySyntax=true

%JAVA_HOME%/bin/java %JAVA_OPS% -jar sqljettest.jar
