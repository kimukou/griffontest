package steeltest

import javax.imageio.ImageIO

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

//2011/03/23 view initialzed Clock add start
		def onStartupEnd = {
			def g = view.clock.backgroundImage.getGraphics()
			//g = view.clock.foregroundImage.createGraphics()
			def icon = view.imageIcon('/griffon-icon-48x48.png').image
			//icon = ImageIO.read(new File('/griffon-icon-48x48.png'))
			g.drawImage(icon, 0,0, null)
			g.drawString("Sine Wave", 0, 0); // Draw some text
			g.dispose()
		}
//2011/03/23  view initialzed Clock add end
}
