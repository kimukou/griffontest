application {
    title = 'Twittersphere2'
    startupGroups = ['twittersphere2']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "twittersphere2"
    'twittersphere2' {
        model      = 'twittersphere2.Twittersphere2Model'
        view       = 'twittersphere2.Twittersphere2View'
        controller = 'twittersphere2.Twittersphere2Controller'
    }

}
