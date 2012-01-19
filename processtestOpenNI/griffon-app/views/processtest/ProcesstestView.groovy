package processtest

application(title: 'processtest',
  size: [640,480],
  //pack: true,
  //location: [50,50],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
    label('Content Goes Here') // deleteme

	tabbedPane(id:'tab'){
/*
		panel(title:"normal",border: emptyBorder(0)) {
			label('Content Goes Here') // deleteme
		}
		panel(title:"2Dtest",border: emptyBorder(0)) {
			widget(model.pApplet2D)
		}
		panel(title:"3Dtest",border: emptyBorder(0)) {
			widget(model.pApplet3D)
		}
*/
		if(model.pAppletOpenNI!=null){
			panel(title:"OpenNItest",border: emptyBorder(0)) {
				widget(model.pAppletOpenNI)
			}
		}

		if(model.pHandTracking!=null){
			panel(title:"HandTracking",border: emptyBorder(0)) {
				widget(model.pHandTracking)
			}
		}

		if(model.pHandTrackingJP!=null){
			panel(title:"HandTrackingJP",border: emptyBorder(0)) {
				widget(model.pHandTrackingJP)
			}
		}
	}

	def model_ = model

	tab.addChangeListener(
		 new ChangeListener() {
      public void stateChanged(ChangeEvent changeEvent) {
        JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
        int index = sourceTabbedPane.getSelectedIndex();
        println "Tab changed to: " + sourceTabbedPane.getTitleAt(index)
				model_.title = sourceTabbedPane.getTitleAt(index)
      }
		}
	)

}
