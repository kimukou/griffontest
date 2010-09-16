application {
    title = 'Steeltest'
    startupGroups = ['steeltest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "steeltest"
    'steeltest' {
        model = 'steeltest.SteeltestModel'
        controller = 'steeltest.SteeltestController'
        view = 'steeltest.SteeltestView'
    }

}


def controller
def view
def model

