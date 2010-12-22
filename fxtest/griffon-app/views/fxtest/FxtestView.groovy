package fxtest

/*
application(title: 'fxtest',
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
}
*/


import griffon.builder.fx.FxBuilder

import javafx.stage.*
import javafx.scene.*
import javafx.scene.shape.*
import javafx.scene.paint.*

new FxBuilder().edt {
    stage(
        title: "Sample by FxBuilder of Groovy",
        width: 600,
        height: 450,
        scene: scene(fill: Color.$LIGHTSKYBLUE) {
            rectangle(
                x: 25, y: 40,
                width: 100, height: 50,
                fill: Color.$RED
            )
        }
    )
}
