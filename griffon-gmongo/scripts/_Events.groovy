def eventClosure1 = binding.variables.containsKey('eventSetClasspath') ? eventSetClasspath : {cl->}
eventSetClasspath = { cl ->
		//println cl.dump()
    eventClosure1(cl)
    if(compilingPlugin('gmongo')) return


		//test\cli\org\codehaus\griffon\resolve\IvyDependencyManagerTests.groovy see
		def manager = griffonSettings.dependencyManager
		manager.parseDependencies {
            inherits "global"
            resolvers {
                griffonHome()
 			          mavenCentral()
                mavenRepo "http://repo1.maven.org/maven2/"
            }
            //runtime( [group:"opensymphony", name:"oscache", version:"2.4.1", transitive:false],
            //         [group:"junit", name:"junit", version:"4.8.1", transitive:true] )

            //runtime("opensymphony:foocache:2.4.1") {
            //         excludes 'jms'
            //}
						//compile 'com.gmongo:gmongo:0.9'
    }
    manager.addPluginDependency('gmongo', [
        conf: 'compile',
        group: 'com.gmongo',
        name: 'gmongo',
        version: '0.9.2'
    ])
}

