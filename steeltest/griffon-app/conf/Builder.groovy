root {
    'groovy.swing.SwingBuilder' {
        controller = ['Threading']
        view = '*'
    }
    'griffon.app.ApplicationBuilder' {
        view = '*'
    }
}

root.'LookandfeelGriffonAddon'.addon=true

root.'LookandfeelJtattooGriffonAddon'.addon=true



root.'TrayBuilderGriffonAddon'.addon=true

root.'I18nGriffonAddon'.addon=true

root.'griffon.builder.jide.JideBuilder'.view = '*'

root.'MiglayoutGriffonAddon'.addon=true

root.'griffon.builder.css.CSSBuilder'.view = '*'
root.'griffon.builder.css.CSSBuilder'.controller = ['CSS']







root.'griffon.builder.trident.TridentBuilder'.view = '*'

root.'SteelGriffonAddon'.addon=true

root.'MousegesturesGriffonAddon'.addon=true

root.'TransitionsGriffonAddon'.addon=true
