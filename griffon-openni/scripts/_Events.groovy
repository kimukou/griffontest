import org.codehaus.griffon.resolve.IvyDependencyManager


def eventClosure1 = binding.variables.containsKey('eventSetClasspath') ? eventSetClasspath : {cl->}
eventSetClasspath = { cl ->
    eventClosure1(cl)
    if(compilingPlugin('openni')) return

    griffonSettings.dependencyManager.flatDirResolver name: 'griffon-openni-plugin', dirs: "${openniPluginDir}/addon"
    griffonSettings.dependencyManager.addPluginDependency('openni', [
        conf: 'compile',
        name: 'griffon-openni-addon',
        group: 'org.codehaus.griffon.plugins',
        version: openniPluginVersion
    ])

    def dependencyManager = griffonSettings.dependencyManager
    for(conf in IvyDependencyManager.ALL_CONFIGURATIONS) {
        dependencyManager.resolveDependencies(conf)
    }

}


def eventClosure2 = binding.variables.containsKey('eventCopyLibsEnd') ? eventCopyLibsEnd : {jardir->}
eventCopyLibsEnd = { jardir ->
    eventClosure2(jardir)
    if(compilingPlugin('openni')) return
    if(!(packagingType in ['applet', 'webstart'])) {
        def openniLibDir = "${getPluginDirForName('openni').file}/lib".toString()
        copyNativeLibs(openniLibDir, jardir)
    } else {
        if(Environment.current == Environment.DEVELOPMENT) {
            doWithPlatform(platform)
        } else {
            PLATFORMS.each { doWithPlatform(it.key) }
        }
    }
}

doWithPlatform = { platformOs ->
    def origPlatformOs = platformOs
    if(platformOs.endsWith('64')) platformOs -= '64'

    ant.fileset(dir: "${getPluginDirForName('openni').file}/lib/webstart", includes: "*${platformOs}.jar").each {
        griffonCopyDist(it.toString(), new File(jardir.toString(), 'webstart').absolutePath)
    }
    if(!buildConfig?.griffon?.extensions?.resources) buildConfig.griffon.extensions.resources = new ConfigObject()
    def rs = buildConfig.griffon.extensions.resources[origPlatformOs]
    if(!rs) {
        def co = new ConfigObject()
        co.nativelibs = joglCompatJnlpResources.find{it.os == platformOs}.nativelibs
        buildConfig.griffon.extensions.resources[origPlatformOs] = co
    } else {
        if(!rs.nativeLibs) rs.nativeLibs = [] 
        rs.nativeLibs.addAll(nativeLibs)
    }
}
