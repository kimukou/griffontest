root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}

root.'JoglCompatGriffonAddon'.addon=true

root.'ProcessingGriffonAddon'.addon=true

root.'OpenniGriffonAddon'.addon=true
