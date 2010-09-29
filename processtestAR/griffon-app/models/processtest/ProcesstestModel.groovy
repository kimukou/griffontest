package processtest

import groovy.beans.Bindable

class ProcesstestModel {
  // @Bindable String propName
	@Bindable pApplet2D = new e2DProcessing()
	@Bindable pApplet3D = new e3DProcessing()
	@Bindable pAppletAR = new NyARBoardProcessing()

	ProcesstestModel(){
			pApplet2D.init()
			pApplet3D.init()
			pAppletAR.init()
	}
}
