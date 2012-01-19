import griffon.util.Environment
includeTargets << griffonScript("_GriffonSettings")

def eventClosure1 = binding.variables.containsKey('eventCopyLibsEnd') ? eventCopyLibsEnd : {jardir->}
eventCopyLibsEnd = { jardir ->
    eventClosure1(jardir)
    if(compilingPlugin('gremlin')) return
    ant.fileset(dir: "${getPluginDirForName('gremlin').file}/lib", includes: "*.jar").each {
        griffonCopyDist(it.toString(), new File(jardir.toString(), '').absolutePath)
    }
}


def eventClosure2 = binding.variables.containsKey('eventSetClasspath') ? eventSetClasspath : {cl->}
eventSetClasspath = { cl ->
    //println cl.dump()
    eventClosure2(cl)
    if(compilingPlugin('gremlin')) return

println "----a"
    //test\cli\org\codehaus\griffon\resolve\IvyDependencyManagerTests.groovy see
    def manager = griffonSettings.dependencyManager

println "----b"
    manager.parseDependencies {
          inherits "global"
          resolvers {
							mavenRepo "http://repo1.maven.org/maven2/"
			        griffonCentral()
			        mavenCentral()
              mavenRepo "http://tinkerpop.com/maven2"
							mavenRepo "http://fortytwo.net/maven2"
							mavenRepo "http://www.orientechnologies.com/listing/m2/"
							mavenRepo "http://repo.aduna-software.org/maven2/releases"
          }
					compile 'com.tinkerpop:gremlin:1.3' 
     }
println "----c"
/*
    manager.addPluginDependency('gremlin', [
        conf: 'compile',
        group: 'com.tinkerpop',
        name: 'gremlin',
        version: '1.2'
    ])
*/
println "----d"

}

