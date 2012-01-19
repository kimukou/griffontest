package processtest

import groovy.beans.Bindable

class ProcesstestModel {
  // @Bindable String propName
	@Bindable pApplet2D = new e2DProcessing()
	@Bindable pApplet3D = new e3DProcessing()
	@Bindable pAppletOpenNI = new SimpleOpenNITest()
	@Bindable pHandTracking = new HandTracking()
	@Bindable pHandTrackingJP = new HandTrackingJP()

	ProcesstestModel(){
			pApplet2D.init()
			pApplet3D.init()
			pAppletOpenNI.init()
			pHandTracking.init()
			pHandTrackingJP.init()
	}
}
