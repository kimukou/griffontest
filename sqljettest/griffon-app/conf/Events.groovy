// No windows have been created before this step
onBootstrapEnd = { app ->
  println "==onBootstrapEnd=="
    app.windowDisplayHandler = new griffon.util.Dropper()
}

onStartupStart = { app ->
  println "==onStartupStart=="
}


onStartupEnd = { app ->
  println "==onStartupEnd=="
}

onReadyStart = { app ->
  println "==onReadyStart=="
}
onReadyEnd = { app ->
  println "==onReadyEnd=="
}
onShutdownStart = { app ->
  println "==onShutdownStart=="
}

onNewInstance = { klass, type, instance ->
    def mc = instance.app.artifactManager.findGriffonClass(klass).metaClass
    //mc.log = java.util.logging.Logger.getLogger(klass.name)
    mc.log = org.apache.commons.logging.LogFactory.getLog(klass.name)
}

