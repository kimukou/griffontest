JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home
GROOVY_HOME=/usr/share/groovy-1.7.7
GRIFFON_HOME=c:/opt/griffon-0.9.2-rc1
JAVAFX_HOME=/Library/Frameworks/JavaFX.framework/Versions/1.2

export JAVA_OPTS='-Dgroovy.source.encoding=UTF-8 -Dfile.encoding=UTF-8'

export PATH=$JAVA_HOME/bin:$GROOVY_HOME/bin:$GRIFFON_HOME/bin

griffon run-app
