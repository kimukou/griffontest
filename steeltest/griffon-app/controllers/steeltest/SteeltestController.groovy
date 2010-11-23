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

//2010/11/16 add look and feel change dialog add start
		//showLaf action add
    def showLaf = { evt = null ->
        griffon.lookandfeel.LookAndFeelManager.instance.showLafDialog(app)
    }
//2010/11/16 add look and feel change dialog add end

}