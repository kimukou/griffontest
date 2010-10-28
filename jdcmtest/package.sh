JAVA_HOME=/System/Library/Frameworks/JavaVM.framework/Versions/1.6/Home
GROOVY_HOME=/usr/share/groovy-1.7.5
GRIFFON_HOME=/usr/share/griffon-0.9.1a

export JAVA_OPTS='-Dgroovy.source.encoding=UTF-8 -Dfile.encoding=UTF-8'

export PATH=$JAVA_HOME/bin:$GROOVY_HOME/bin:$GRIFFON_HOME/bin

griffon clean
griffon prod package
