application {
    title = 'Jalarmstest'
    startupGroups = ['jalarmstest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "jalarmstest"
    'jalarmstest' {
        model = 'jalarmstest.JalarmstestModel'
        controller = 'jalarmstest.JalarmstestController'
        view = 'jalarmstest.JalarmstestView'
    }

}


def props

