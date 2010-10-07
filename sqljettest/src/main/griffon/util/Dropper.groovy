package griffon.util

import java.awt.Window
import griffon.swing.SwingUtils
import griffon.swing.WindowDisplayHandler
import griffon.core.GriffonApplication
import griffon.effects.Effects
class Dropper implements WindowDisplayHandler {
    void show(Window window, GriffonApplication app) {
		println "==Dropper::show=="
        SwingUtils.centerOnScreen(window)
		if(app.config.seach_auto_start)return
		
		app.execOutside {
            //Effects.dropIn(window, wait: true)
			Effects.appear(window, duration: 2000, wait: true)
        }
    }

    void hide(Window window, GriffonApplication app) {
				println "==Dropper::hide=="
        app.execOutside {
            //Effects.dropOut(window, wait: true)
			Effects.puff(window, duration: 2000, wait: true)
			//Effects.fade(window, wait: true)
        }
    }
}