root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}
		root.'TwitterGriffonAddon'.addon=true
	
root.'JoglCompatGriffonAddon'.addon=true

root.'WorldwindGriffonAddon'.addon=true

root.'GlazedlistsGriffonAddon'.addon=true

root.'I18nGriffonAddon'.addon=true
