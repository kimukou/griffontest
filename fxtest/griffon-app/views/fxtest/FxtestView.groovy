package fxtest

import griffon.builder.fx.FxBuilder

import javafx.stage.*
import javafx.scene.*
import javafx.scene.shape.*
import javafx.scene.paint.*
import javafx.scene.control.*
import javafx.scene.text.*

import org.widgetfx.*

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


	tabbedPane(){
		panel(title:"normal",border: emptyBorder(0)) {
    	label('Content Goes Here') // deleteme
		}
		panel(title:"FXBuilder",border: emptyBorder(0)) {
			def instance=new FxBuilder().build{
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
			println "instance=${instance.class}/${instance.dump()}"
			//widget(instance.getWindow())
		}
/*
		panel(title:"WidgetTest",border: emptyBorder(0)) {
			def builder = new FxBuilder()
			//builder.registerFactory("Widget",new WidgetFactory(Widget))
			builder.registerBeanFactory("Widget",org.widgetfx.Widget)
			def instance2=builder.build{
				Widget (
				    width: 100,
				    height: 100,
				    visible: true,
						content: eclipse (
		          centerX: 50,
		          centerY: 50,
		          radiusX: 50,
		          radiusY: 50,
		          fill: Color.$RED
			      )
				)
			}
			//widget(instance2)
		}
*/
	}

	// src/javafx/*.fx
	new FxBuilder().build(MouseEventSample)
}

