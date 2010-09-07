package stealtest


import com.jidesoft.swing.AnimatorListener
import static com.jidesoft.swing.MeterProgressBar.*


import eu.hansolo.steelseries.tools.*
import java.awt.*

frame = application(title: 'stealtest',
  size: [900,600],
  //pack: true,
  //location: [50,50],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
    borderLayout()

		panel(constraints: NORTH){
	   boxLayout()
			clock(id:'clock',preferredSize: [100,100],backgroundColor:BackgroundColor.WHITE,frameDesign:FrameDesign.SHINY_METAL)
			compass (preferredSize: [100,100])
			digitalRadialGauge (preferredSize: [100,100])
			digitalRadialLcdGauge (id:'lcdgauge',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう')
			//displayCircular(id:'circular',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう')
			displayCircular(id:'circular',value:50,lcdDecimals:3,unitString:'SEC',lcdColor:LcdColor.ORANGE_LCD,backgroundColor:BackgroundColor.GREEN,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [100,100])
			displayCircular(id:'circular2',value:bind{model.count},lcdDecimals:0,unitString:'SEC',lcdColor:LcdColor.BLUEBLUE_LCD,backgroundColor:BackgroundColor.RED,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [100,100])
			circular.setValueAnimated(50)
			displayMulti(preferredSize: [100,100])
			displayRectangular(preferredSize: [100,100]) 
			displaySingle(preferredSize: [100,100]) 
		}
		panel(constraints: CENTER){
	   boxLayout()
			level (preferredSize: [100,100])
			linearGauge (preferredSize: [100,100])
			linearLcdGauge(preferredSize: [100,100]) 
			radar(id:'radar',preferredSize: [100,100])
			radar.animate() 
			radial1Gauge(preferredSize: [100,100]) 
			radial1LcdGauge(preferredSize: [100,100]) 
			radial1SquareGauge(preferredSize: [100,100]) 
			radial1VerticalGauge(preferredSize: [100,100]) 
		}
		panel(constraints: SOUTH){
		  boxLayout()
			radial2Gauge(preferredSize: [100,100]) 
			radial2LcdGauge(preferredSize: [100,100]) 
			radial3Gauge(preferredSize: [100,100]) 
			radial3LcdGauge(preferredSize: [100,100]) 
			radial4Gauge(preferredSize: [100,100]) 
			radial4LcdGauge(preferredSize: [100,100]) 

			panel(){
				migLayout(constraints: "growx, wrap, gaptop 0")
				meterProgressBar(id :'m2', style: STYLE_PLAIN,constraints: "growx, wrap, gaptop 0, gapright 0")
				meterProgressBar(id :'m1', style: STYLE_GRADIENT,constraints: "gaptop 0, gapright 0")
				
				//toggleButton(id:'serarchB',constraints: "")
				//animator(id: 'anim', source: serarchB, delay: 30, totalSteps: 100)
				animator(id: 'anim', source: m1, delay: 30, totalSteps: 100)
				anim.addAnimatorListener([
					animationStarts: { source -> },
					animationFrame: { source, totalSteps, frame ->
								   m1.value = m2.value = frame
								 if(frame==100)anim._currentStep=0
					},
					animationEnds: { source ->}
				] as com.jidesoft.swing.AnimatorListener)
				anim.start()
			}
		}

	//ダブルクリックで表示、非表示
	systemTray {
		trayIcon(id: "trayIcon",
				resource: "/griffon-icon-48x48.png",
				class: groovy.ui.Console,
				toolTip: getMessage("view.tray.tooltip") ,
				actionPerformed: { 	frame.visible = !frame.visible }) {
		}
	}

}


