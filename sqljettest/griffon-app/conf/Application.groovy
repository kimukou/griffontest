application {
    title = 'Sqljettest'
    startupGroups = ['sqljettest']

    // Should Griffon exit when no Griffon created frames are showing?
    autoShutdown = true

    // If you want some non-standard application class, apply it here
    //frameClass = 'javax.swing.JFrame'
}
mvcGroups {
    // MVC Group for "sqljettest"
    'sqljettest' {
        model = 'sqljettest.SqljettestModel'
        controller = 'sqljettest.SqljettestController'
        view = 'sqljettest.SqljettestView'
        service = 'sqljettest.SqljettestService'
    }

}



//delay access Variable
def controller =null
def service = null
def view =null
def model = null
def font = null
