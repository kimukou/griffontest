// No windows have been created before this step
import java.awt.*
onBootstrapEnd = { app ->
  println "==onBootstrapEnd=="
  app.windowDisplayHandler = new griffon.util.Dropper()

	//Custom Font using
	try {
		URL url = getClass().getResource("onryou.TTF")
		is = url.openStream()
		Font font = Font.createFont(Font.TRUETYPE_FONT, is)
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font)
		is.close()

		//[TODO] Not Set value(Error Occuered)
		//app.font = font
		//griffon.util.ApplicationHolder.application.font = font
	}catch(Exception ex){
		ex.printStackTrace()
	}
}

onStartupStart = { app ->
  println "==onStartupStart=="
}


onStartupEnd = { app ->
  println "==onStartupEnd=="
}

onReadyStart = { app ->
  println "==onReadyStart=="
}
onReadyEnd = { app ->
  println "==onReadyEnd=="
}
onShutdownStart = { app ->
  println "==onShutdownStart=="
}

onNewInstance = { klass, type, instance ->
    def mc = instance.app.artifactManager.findGriffonClass(klass).metaClass
    //mc.log = java.util.logging.Logger.getLogger(klass.name)
    mc.log = org.apache.commons.logging.LogFactory.getLog(klass.name)
}

