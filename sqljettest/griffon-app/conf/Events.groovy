// No windows have been created before this step
import java.awt.*
onBootstrapEnd = { app ->
  println "==onBootstrapEnd=="
  app.windowDisplayHandler = new griffon.util.Dropper()

	//Custom Font using

	//def fontname="onryou.TTF"
	//def fontname="hakidame.TTF"
	def fontname="MadokaMusical.TTF"
	InputStream is = null

	try {
		is=getClass().classLoader.getResourceAsStream(fontname)
		Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24.0f)
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font)
		is.close()

		//griffon.util.ApplicationHolder.application.config.font = font
		app.config.font = font
	}catch(Exception ex){
		ex.printStackTrace()
	}
	finally{
		if(is!=null)is.close()
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
		//println instance.app.artifactManager.findGriffonClass(klass).metaClass.dump()
    //mc.log = java.util.logging.Logger.getLogger(klass.name)
    mc.log = org.apache.commons.logging.LogFactory.getLog(klass.name)

		//if(instance==null || instance?.metaClass==null || instance?.metaClass?.log==null)return
		//instance.metaClass.log = org.apache.commons.logging.LogFactory.getLog(klass.name)
}

