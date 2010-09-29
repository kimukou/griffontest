application {
    title = 'MvcApp'
    startupGroups = ['MvcApp']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "DetailPanel"
    'DetailPanel' {
        model = 'mvcapp.DetailPanelModel'
        controller = 'mvcapp.DetailPanelController'
        view = 'mvcapp.DetailPanelView'
    }

    // MVC Group for "MvcApp"
    'MvcApp' {
        model = 'mvcapp.MvcAppModel'
        controller = 'mvcapp.MvcAppController'
        view = 'mvcapp.MvcAppView'
    }
}
