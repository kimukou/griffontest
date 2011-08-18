root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}
root.'SpringGriffonAddon'.addon=true

root.'JalarmsGriffonAddon'.addon=true

root.'MailGriffonAddon'.addon=true

root.'MemcachedGriffonAddon'.addon=true
