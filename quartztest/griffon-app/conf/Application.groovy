application {
    title = 'Quartztest'
    startupGroups = ['quartztest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "quartztest"
    'quartztest' {
        model = 'quartztest.QuartztestModel'
        controller = 'quartztest.QuartztestController'
        view = 'quartztest.QuartztestView'
    }

}
