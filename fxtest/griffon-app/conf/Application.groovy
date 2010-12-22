application {
    title = 'Fxtest'
    startupGroups = ['fxtest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "fxtest"
    'fxtest' {
        model = 'fxtest.FxtestModel'
        controller = 'fxtest.FxtestController'
        view = 'fxtest.FxtestView'
    }

}
