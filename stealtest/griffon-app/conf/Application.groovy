application {
    title = 'Stealtest'
    startupGroups = ['stealtest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "stealtest"
    'stealtest' {
        model = 'stealtest.StealtestModel'
        controller = 'stealtest.StealtestController'
        view = 'stealtest.StealtestView'
    }

}
