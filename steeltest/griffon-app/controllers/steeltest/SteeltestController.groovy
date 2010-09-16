package steeltest

class SteeltestController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
			app.config.controller = args.controller
			app.config.view = args.view
			app.config.model = args.model
    }

    /*
    def action = { evt = null ->
    }
    */
}