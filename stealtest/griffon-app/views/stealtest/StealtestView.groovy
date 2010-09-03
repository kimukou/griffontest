package stealtest


import eu.hansolo.steelseries.tools.*
import java.awt.*

application(title: 'stealtest',
  //size: [320,480],
  pack: true,
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
			compass ()
			digitalRadialGauge ()
			digitalRadialLcdGauge (id:'lcdgauge',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう')
			//displayCircular(id:'circular',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう')
			displayCircular(id:'circular',value:50,lcdDecimals:3,unitString:'SEC',lcdColor:LcdColor.ORANGE_LCD,backgroundColor:BackgroundColor.GREEN,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [100,100])
			displayCircular(id:'circular2',value:bind{model.count},lcdDecimals:0,unitString:'SEC',lcdColor:LcdColor.BLUEBLUE_LCD,backgroundColor:BackgroundColor.RED,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [100,100])
			circular.setValueAnimated(50)
			displayMulti()
			displayRectangular() 
			displaySingle() 
		}
		panel(constraints: CENTER){
	   boxLayout()
			level ()
			linearGauge ()
			linearLcdGauge() 
			radar(id:'radar',preferredSize: [100,100])
			radar.animate() 
			radial1Gauge() 
			radial1LcdGauge() 
			radial1SquareGauge() 
			radial1VerticalGauge() 
		}
		panel(constraints: SOUTH){
		   boxLayout()
			radial2Gauge() 
			radial2LcdGauge() 
			radial3Gauge() 
			radial3LcdGauge() 
			radial4Gauge() 
			radial4LcdGauge() 
			textField( id: 'propName',text: bind{model.propName},columns:30)
		}
}
