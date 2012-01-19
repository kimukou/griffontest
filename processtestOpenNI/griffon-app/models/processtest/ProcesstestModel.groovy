package processtest

import griffon.transform.PropertyListener
import groovy.beans.Bindable

class ProcesstestModel {
  // @Bindable String propName
	@Bindable def pApplet2D = new e2DProcessing()
	@Bindable def pApplet3D = new e3DProcessing()
	@Bindable def pAppletOpenNI = null
	@Bindable def pHandTracking = null
	@Bindable def pHandTrackingJP = null

	ProcesstestModel(){
			pApplet2D.init()
			pApplet3D.init()
	}

	def controller

  @Bindable
  @PropertyListener({controller.changeActive(it)})
  String title
}
