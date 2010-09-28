root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}
root.'ProcessingGriffonAddon'.addon=true

root.'JoglGriffonAddon'.addon=true
