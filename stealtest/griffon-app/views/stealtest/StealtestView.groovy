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
  size: [800,800],
  //pack: true,
  //location: [150,150],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
    //borderLayout()
	  migLayout(layoutConstraints: "gap 0,insets 0,fill")

		//1
		panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
	   		boxLayout()
			label(text:'1  ')
			//poi(name:'hogehoge',lat:30,lon:70)
			poi(id:'hogehoge',name:'hogehoge',lat:30,lon:70)
			altimeter(preferredSize: [150,150])
			clock(id:'clock',preferredSize: [150,150],backgroundColor:BackgroundColor.WHITE,frameDesign:FrameDesign.SHINY_METAL)
			compass (preferredSize: [150,150])

			clock.CLOCK_TIMER.addActionListener([
					actionPerformed: { source -> 
							println "clock == ${view.clock.hour}:${view.clock.minute} <${java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)}:${view.clock.minute}>"
					}
				] as ActionListener)
			digitalRadialGauge (preferredSize: [150,150])
			digitalRadialLcdGauge (id:'lcdgauge',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう',preferredSize: [150,150])
			//displayCircular(id:'circular',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう',preferredSize: [150,150])
		}
		//2
		panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0),preferredSize: [800,150]){
	   		boxLayout()
			label(text:'2  ')
			displayCircular(id:'circular',value:0,lcdDecimals:3,unitString:'SEC',lcdColor:LcdColor.ORANGE_LCD,backgroundColor:BackgroundColor.GREEN,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [150,150])
			//circular.setValueAnimated model.count
			displayCircular(id:'circular2',value:bind{model.count},lcdDecimals:0,unitString:'SEC',lcdColor:LcdColor.BLUEBLUE_LCD,backgroundColor:BackgroundColor.RED,frameDesign:FrameDesign.BLACK_METAL,digitalFont:true,preferredSize: [150,150])
			panel(){
		   		boxLayout(axis:javax.swing.BoxLayout.PAGE_AXIS)
				displayMulti(preferredSize: [140,40])
				displayRectangular(preferredSize: [140,60],minimumSize: [140,60]) 
				displaySingle(preferredSize: [140,40]) 
			}
			radial2TopGauge(preferredSize: [150,150])
			radialCounterGauge(preferredSize: [150,150])
		}
		//3
		panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0), cssClass: "active"){
	   		boxLayout()
			label(text:'3  ')
			level (preferredSize: [150,150])
			linearGauge (preferredSize: [150,150])
			linearLcdGauge(preferredSize: [150,150]) 
			radar(id:'radar',preferredSize: [150,150])
			radar.add(hogehoge)
			radar.animate() 
			panel(cssClass: "active"){
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
		//4
		panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
	   		boxLayout()
			label(text:'4  ')
			radial1Gauge(preferredSize: [150,150],minValue:0,maxValue:10000) 
			radial1LcdGauge(preferredSize: [150,150],value:bind{model.cpu_usage},thresholdVisible:true,minMeasuredValueVisible :true,maxMeasuredValueVisible :true) 
			radial1SquareGauge(id:'squaregauge',preferredSize: [150,150],minValue:0,maxValue:100,title:'cpu meter',unitString:'%') 
			radial1VerticalGauge(preferredSize: [150,150]) 
			radial2Gauge(preferredSize: [150,150]) 
		}
		//5
		panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
		  	boxLayout()
			label(text:'5  ')
			radial2LcdGauge(preferredSize: [150,150]) 
			radial3Gauge(preferredSize: [150,150]) 
			radial3LcdGauge(preferredSize: [150,150]) 
			radial4Gauge(preferredSize: [150,150]) 
			radial4LcdGauge(preferredSize: [150,150]) 
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





