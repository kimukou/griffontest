application {
    title = 'Gmongotest'
    startupGroups = ['gmongotest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "gmongotest"
    'gmongotest' {
        model = 'gmongotest.GmongotestModel'
        controller = 'gmongotest.GmongotestController'
        view = 'gmongotest.GmongotestView'
    }

}
