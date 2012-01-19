package processtest

import SimpleOpenNI.*;
import processing.core.*;

class ProcesstestController {
    // these will be injected by Griffon
    def model
    def view

    void mvcGroupInit(Map args) {
        // this method is called after model and view are injected

				//model.pAppletOpenNI = new SimpleOpenNITest()
				//model.pHandTracking = new HandTracking()
				model.pHandTrackingJP = new HandTrackingJP()

				if(model.pAppletOpenNI!=null){
					  model.pAppletOpenNI.init()
				}
				if(model.pHandTracking!=null){
						model.pHandTracking.init()
				}
				if(model.pHandTrackingJP!=null){
						model.pHandTrackingJP.init()
				}

				if(model.pAppletOpenNI!=null){
					  model.pAppletOpenNI.stop()
				}
				if(model.pHandTracking!=null){
					  model.pHandTracking.stop()
				}
				if(model.pHandTrackingJP!=null){
					  model.pHandTrackingJP.stop()
				}


    }

    /*
    def action = { evt = null ->
    }
    */


		def onStartupEnd = {app -> 

				def title =view.tab.getTitleAt(view.tab.selectedIndex)
				println "title=$title"

				switch(title){
					case "OpenNItest":
						if(model.pAppletOpenNI!=null){
							setActive(model.pAppletOpenNI)
						}
						break;
					case "HandTracking":
						if(model.pHandTracking!=null)  {
							setActive(model.pHandTracking)
						}
						break;
					case "HandTrackingJP":
						if(model.pHandTrackingJP!=null){
							setActive(model.pHandTrackingJP)
						}
						break;
					
				}
		}

		def changeActive = {title -> 
	
				println "changeActive ${title.dump()}"

				if(model.pAppletOpenNI!=null){
					  model.pAppletOpenNI.stop()
				}
				if(model.pHandTracking!=null){
					  model.pHandTracking.stop()
				}
				if(model.pHandTrackingJP!=null){
					  model.pHandTrackingJP.stop()
				}

				switch(title.newValue){
					case "OpenNItest":
						if(model.pAppletOpenNI!=null){
							setActive(model.pAppletOpenNI)
						}
						break;
					case "HandTracking":
						if(model.pHandTracking!=null)  {
							setActive(model.pHandTracking)
						}
						break;
					case "HandTrackingJP":
						if(model.pHandTrackingJP!=null){
							setActive(model.pHandTrackingJP)
						}
						break;
					
				}
		}

		def setActive(PApplet parent){
				def kinect = KinectUtil.getInstance(parent);
				kinect._parent    = parent;
		    parent.registerDispose(kinect);
		    
		    // setup the callbacks
				kinect._userCbObject           = kinect._parent;
		    kinect._calibrationCbObject    = kinect._parent;
		    kinect._poseCbObject           = kinect._parent;
		    kinect._handsCbObject          = kinect._parent;
		    kinect._gestureCbObject        = kinect._parent;
		    kinect._sessionCbObject        = kinect._parent;

				KinectUtil.setKinect(kinect);
				parent.start();
		}


}

