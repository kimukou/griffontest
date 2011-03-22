JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home

# MacPorts Install
GROOVY_HOME=/opt/local/share/java/groovy/bin/groovy
GRIFFON_HOME=/opt/local/share/java/griffon/bin/griffon

export JAVA_OPTS='-Dgroovy.source.encoding=UTF-8 -Dfile.encoding=UTF-8'

export PATH=$JAVA_HOME/bin:$GROOVY_HOME/bin:$GRIFFON_HOME/bin

griffon run-app
