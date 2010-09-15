package stealtest


import com.jidesoft.swing.AnimatorListener
import static com.jidesoft.swing.MeterProgressBar.*


import eu.hansolo.steelseries.tools.*
import java.awt.*
import java.awt.event.*

import com.dreamarts.system.utils.*
import net.miginfocom.swing.MigLayout



import griffon.builder.css.CSSDecorator

// make all components have a white background
def style = """
/*
* {
  background-color: red;
}
*/

jbutton {
  background-color: blue;
  font-size: 120%;
}

#button1 {
  background-color: green;
  font-style: italic;
}

.active {
  background-color: yellow;
  font-weight: bold;
}

"""



/*
FrameDesign.BLACK_METAL
FrameDesign.METAL
FrameDesign.SHINY_METAL

LcdColor.BEIGE_LCD
LcdColor.BLUE_LCD
LcdColor.ORANGE_LCD
LcdColor.RED_LCD
LcdColor.YELLOW_LCD
LcdColor.WHITE_LCD
LcdColor.GRAY_LCD
LcdColor.BLACK_LCD
LcdColor.GREEN_LCD
LcdColor.BLUEBLACK_LCD
LcdColor.BLUEDARKBLUE_LCD
LcdColor.BLUEGRAY_LCD
LcdColor.STANDARD_LCD
LcdColor.BLUEBLUE_LCD

BackgroundColor.DARK_GRAY
BackgroundColor.LIGHT_GRAY
BackgroundColor.WHITE
BackgroundColor.BLACK
BackgroundColor.BEIGE
BackgroundColor.RED
BackgroundColor.GREEN
BackgroundColor.BLUE


LedColor.RED_LED
LedColor.GREEN_LED
LedColor.BLUE_LED
LedColor.YELLOW_LED
LedColor.ORANGE_LED

PointerColor.RED
PointerColor.GREEN
PointerColor.BLUE
PointerColor.ORANGE
PointerColor.YELLOW

*/


frame = application(title: 'stealtest',
  //size: [800,800],
  pack: true,
  //location: [100,100],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
    //borderLayout()
	  migLayout(layoutConstraints: "gap 0,insets 0,fill")

		panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
	   boxLayout()
			poi(name:'hogehoge',lat:30,lon:70)
			//poi(id:'hogehoge',name:'hogehoge',lat:30,lon:70)
			altimeter(preferredSize: [70,70])
			clock(id:'clock',preferredSize: [70,70],backgroundColor:BackgroundColor.WHITE,frameDesign:FrameDesign.SHINY_METAL)
			compass (preferredSize: [70,70])

			clock.CLOCK_TIMER.addActionListener([
					actionPerformed: { source -> 
							println "clock == ${view.clock.hour}:${view.clock.minute} <${java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)}:${view.clock.minute}>"
					}
				] as ActionListener)
		}
		panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
	   boxLayout()
			digitalRadialGauge (preferredSize: [100,100])
			digitalRadialLcdGauge (id:'lcdgauge',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう',preferredSize: [100,100])
			//displayCircular(id:'circular',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう',preferredSize: [100,100])
			displayCircular(id:'circular',value:0,lcdDecimals:3,unitString:'SEC',lcdColor:LcdColor.ORANGE_LCD,backgroundColor:BackgroundColor.GREEN,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [100,100])
			//circular.setValueAnimated model.count
			displayCircular(id:'circular2',value:bind{model.count},lcdDecimals:0,unitString:'SEC',lcdColor:LcdColor.BLUEBLUE_LCD,backgroundColor:BackgroundColor.RED,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [100,100])
		}
		panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
	   boxLayout()
			displayMulti(preferredSize: [100,100])
			displayRectangular(preferredSize: [100,100]) 
			displaySingle(preferredSize: [100,100]) 
		}
		panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0), cssClass: "active"){
	   boxLayout()
			level (preferredSize: [100,100])
			linearGauge (preferredSize: [100,100])
			linearLcdGauge(preferredSize: [100,100]) 
			radar(id:'radar',preferredSize: [100,100])
			//radar.add(hogehoge)
			radar.animate() 
		}
		panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
	   boxLayout()
			radial1Gauge(preferredSize: [70,70]) 
			radial1LcdGauge(preferredSize: [100,100],value:bind{model.cpu_usage},thresholdVisible:true,minMeasuredValueVisible :true,maxMeasuredValueVisible :true) 
			radial1SquareGauge(id:'squaregauge',preferredSize: [100,100],minValue:0,maxValue:100,title:'cpu meter',unitString:'%') 
			radial1VerticalGauge(preferredSize: [100,100]) 
		}
		panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
		  boxLayout()
			radial2Gauge(preferredSize: [100,100]) 
			radial2LcdGauge(preferredSize: [100,100]) 
			radial3Gauge(preferredSize: [100,100]) 
			radial3LcdGauge(preferredSize: [100,100]) 
		}
		panel(constraints: "span,grow,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
	   boxLayout()
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
			//this.registerBeanFactory("radial2TopGauge", eu.hansolo.steelseries.gauges.Radial2Top.class)
			//radial2TopGauge(preferredSize: [100,100])
			//this.registerBeanFactory("radialCounterGauge", eu.hansolo.steelseries.gauges.RadialCounter.class)
			//radialCounterGauge(preferredSize: [100,100])

		}

	//CSSDecorator.applyStyle(style,app.appFrames[0]) //CSS適応(from griffon 0.3)
	CSSDecorator.applyStyle(style,app.windowManager.windows[0]) //CSS適応(from griffon 0.9)

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

def pausef=false
def t =  new Thread()
t.start{
		while(true){
			 sleep(1000)
			 if(pausef)continue
			 //CPU
			 def c2 = new Date()
			 float usage = CpuUsageAnalyzer.getCpuUsage()
			 model.cpu_usage = usage
			 view.squaregauge.setValueAnimated model.cpu_usage
			 println "■■■■■■■ (CPU,TIME)=(${usage},${c2}) ■■■■■■■"

			 model.count++
			 if(model.count > 100)model.count =0

			 if(view.circular.value ==0){
			     view.circular.setValueAnimated(100)
			 }
       else if(view.circular.value ==100){
	 			 view.circular.setValueAnimated(0)
       }
		}
}


/*
frame.windowOpened={println 'Opened'}//初回起動時
frame.windowClosing={println 'closing'}//システムメニューでウィンドウを閉じようとしたとき
frame.windowClosed={println 'closed'}//disposeが呼ばれた時
*/
//通常＝＞最小化
frame.windowIconified={
	println 'Iconified'
	view.anim.stop()
	//t.yield()
	pausef=true
}	
//最小化＝＞通常
frame.windowDeiconified={
	println 'Deiconified'
	view.anim.start()
	//t.resume()
	pausef=false
}

//アクティブな時
frame.windowActivated={
	println 'Activated'
	if(!frame.visible)return
	view.anim.start()
	//t.resume()
	pausef=false
}

//アクティブじゃない時
frame.windowDeactivated={
	println 'Deactivated'
	if(frame.visible)return
	view.anim.stop()
	//t.yield()
	pausef=true
}





