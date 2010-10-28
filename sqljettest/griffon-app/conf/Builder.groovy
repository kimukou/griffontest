root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}
root.'MiglayoutGriffonAddon'.addon=true

root.'GlazedlistsGriffonAddon'.addon=true


root.'I18nGriffonAddon'.addon=true

root.'griffon.builder.trident.TridentBuilder'.view = '*'

root.'EffectsGriffonAddon'.addon=true

jx {
    'groovy.swing.SwingXBuilder' {
        view = '*'
    }
}



root.'GsqlGriffonAddon'.addon=true
