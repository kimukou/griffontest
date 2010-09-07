application {
    title = 'Jdcmtest'
    startupGroups = ['jdcmtest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "jdcmtest"
    'jdcmtest' {
        model = 'jdcmtest.JdcmtestModel'
        controller = 'jdcmtest.JdcmtestController'
        view = 'jdcmtest.JdcmtestView'
    }

}
