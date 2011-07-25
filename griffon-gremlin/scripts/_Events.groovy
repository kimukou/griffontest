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


    //test\cli\org\codehaus\griffon\resolve\IvyDependencyManagerTests.groovy see
    def manager = griffonSettings.dependencyManager

    //manager.flatDirResolver name: 'gremlinPluginLib', dirs: "${gremlinPluginDir}/lib"
    manager.parseDependencies {
           repositories {
				        flatDir name: 'gremlinPluginLib', dirs: "${gremlinPluginDir}/lib"

                griffonPlugins()
                griffonHome()
                griffonCentral()

                //mavenLocal()
                mavenCentral()

                mavenRepo "http://snapshots.repository.codehaus.org" 
                mavenRepo "http://repository.codehaus.org" 
                mavenRepo "http://download.java.net/maven/2/" 
                mavenRepo "http://repository.jboss.com/maven2/" 
                // Tinkerpop Maven2 Repository 
                mavenRepo "http://tinkerpop.com/maven2" 
                mavenRepo "http://repo.aduna-software.org/maven2/releases/" 
								mavenRepo "http://www.orientechnologies.com/listing/m2"
								mavenRepo "https://repo.neo4j.org/content/groups/dev/"
           }
           dependencies  {
                // Gremlin 
                compile 'com.tinkerpop:gremlin:1.1' 
                compile 'com.tinkerpop.blueprints:blueprints-neo4j-graph:0.8'

								compile 'com.tinkerpop.blueprints.pgm:blueprints-core:0.8'

								compile 'net.fortytwo:linked-data-sail:0.7-SNAPSHOT'
           }
     }
}

