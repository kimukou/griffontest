package processtest

application(title: 'processtest',
  size: [400,400],
  //pack: true,
  //location: [50,50],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
    label('Content Goes Here') // deleteme

	tabbedPane(){
		panel(title:"normal",border: emptyBorder(0)) {
			label('Content Goes Here') // deleteme
		}
		panel(title:"2Dtest",border: emptyBorder(0)) {
			widget(model.pApplet2D)
		}
		panel(title:"3Dtest",border: emptyBorder(0)) {
			widget(model.pApplet3D)
		}
	}


}
