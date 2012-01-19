def eventClosure1 = binding.variables.containsKey('eventSetClasspath') ? eventSetClasspath : {cl->}
eventSetClasspath = { cl ->
    eventClosure1(cl)
    if(compilingPlugin('groovycsv')) return

    griffonSettings.dependencyManager.addPluginDependency('groovycsv', [
        conf: 'compile',
        group: 'com.xlson.groovycsv',
        name: 'groovycsv',
        version: '0.2'
    ])
    griffonSettings.dependencyManager.addPluginDependency('groovycsv', [
        conf: 'compile',
        group: 'net.sf.opencsv',
        name: 'opencsv',
        version: '2.3'
    ])
}

