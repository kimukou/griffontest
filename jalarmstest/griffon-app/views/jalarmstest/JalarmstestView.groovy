package jalarmstest

import javax.swing.*

application(title: 'jalarmstest',
  //size: [320,480],
  pack: true,
  //location: [50,50],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
    label('Content Goes Here') // deleteme
		boxLayout(axis:BoxLayout.Y_AXIS)
		button ("console_test", actionPerformed: controller.consoleAction)
		button ("nagios_test", actionPerformed: controller.nagiosAction)
		button ("twitter_test", actionPerformed: {controller.twitterAction()})
		button ("mail_test", actionPerformed: controller.mailAction)
		button ("http_test", actionPerformed: {controller.httpAction()})
		button ("alarmSender_test", actionPerformed: controller.alarmSenderAction)
}
