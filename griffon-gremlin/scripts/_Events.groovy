def eventClosure1 = binding.variables.containsKey('eventSetClasspath') ? eventSetClasspath : {cl->}
eventSetClasspath = { cl ->
		//println cl.dump()
    eventClosure1(cl)
    if(compilingPlugin('gremlin')) return


		//test\cli\org\codehaus\griffon\resolve\IvyDependencyManagerTests.groovy see
		def manager = griffonSettings.dependencyManager
    manager.parseDependencies {
           repositories {
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
				        mavenRepo "http://repo.aduna-software.org/maven2" 
								

           }
           dependencies  {
								// Gremlin 
						    compile 'com.tinkerpop:gremlin:1.1' 
						    compile 'com.tinkerpop.blueprints:blueprints-neo4j-graph:0.8' 
           }
     }
}

