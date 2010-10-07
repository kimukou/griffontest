package griffon.util

class ServiceArtifactHandler{
    def onNewInstance = { klass, t, instance ->
        if(type == t || app.config?.griffon?.basic_injection?.disable) return
        klass.metaClass.properties.name.each { propertyName ->
            if(SERVICES[propertyName]) {
                instance[propertyName] = SERVICES[propertyName]
            } else {
                def artifact = findArtifact(propertyName, false)
                if(artifact) {
                    def service = artifact.newInstance()
                    service.metaClass.app = app
                    SERVICES[propertyName] = service
                    instance[propertyName] = service
                }
            }
        }
    }
}
