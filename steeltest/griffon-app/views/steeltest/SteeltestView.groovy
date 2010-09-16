package steeltest


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
java.awt.Color.black 黒を表します。
java.awt.Color.BLACK 黒を表します。
java.awt.Color.blue  青を表します。
java.awt.Color.BLUE  青を表します。
java.awt.Color.cyan  シアンを表します。
java.awt.Color.CYAN  シアンを表します。
java.awt.Color.DARK_GRAY ダークグレイを表します。
java.awt.Color.darkGray  ダークグレイを表します。
java.awt.Color.gray  グレイを表します。
java.awt.Color.GRAY  グレイを表します。
java.awt.Color.green 緑を表します。
java.awt.Color.GREEN 緑を表します。
java.awt.Color.LIGHT_GRAY ライトグレイを表します。
java.awt.Color.lightGray  ライトグレイを表します。
java.awt.Color.magenta    マゼンタを表します。
java.awt.Color.MAGENTA    マゼンタを表します。
java.awt.Color.orange     オレンジを表します。
java.awt.Color.ORANGE     オレンジを表します。
java.awt.Color.pink       ピンクを表します。
java.awt.Color.PINK       ピンクを表します。
java.awt.Color.red        赤を表します。
java.awt.Color.RED        赤を表します。
java.awt.Color.white      白を表します。
java.awt.Color.WHITE      白を表します。
java.awt.Color.yellow     黄を表します。
java.awt.Color.YELLOW     黄を表します。
*/

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
PointerColor.BLACK  //from 0.2.1
PointerColor.GRAY		//from 0.2.1
PointerColor.WHITE	//from 0.2.1

*/


frame = application(title: app.config.application.title,
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
			clock(id:'clock',preferredSize: [150,150],
						backgroundColor:BackgroundColor.WHITE,
						frameDesign:FrameDesign.SHINY_METAL)

			//g = clock.backgroundImage.getGraphics()
			g = clock.foregroundImage.createGraphics()
			icon = imageIcon('/griffon-icon-48x48.png').image
			g.drawImage(icon, 0,0, null)
			g.drawString("Sine Wave", 0, 0); // Draw some text
			g.dispose()

			compass (id:'compass',preferredSize: [150,150])
			compass.setValueAnimated 90

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
			displayCircular(id:'circular',value:0,lcdDecimals:3,unitString:'SEC',
					lcdColor:LcdColor.ORANGE_LCD,
					backgroundColor:BackgroundColor.GREEN,
					frameDesign:FrameDesign.BLACK_METAL,
					digitalFont:true,
					preferredSize: [150,150])
			//circular.setValueAnimated model.count
			displayCircular(id:'circular2',value:bind{model.count},lcdDecimals:0,unitString:'SEC',
					lcdColor:LcdColor.BLUEBLUE_LCD,
					backgroundColor:BackgroundColor.RED,
					frameDesign:FrameDesign.BLACK_METAL,
					digitalFont:true,preferredSize: [150,150])
			panel(){
		   		boxLayout(axis:javax.swing.BoxLayout.PAGE_AXIS)
				displayMulti(preferredSize: [140,40])
				displayRectangular(preferredSize: [140,60],minimumSize: [140,60]) 
				displaySingle(preferredSize: [140,40]) 
			}
			radial2TopGauge(preferredSize: [150,150],value:20)    //NEW★
			radialCounterGauge(id:'radialCounter', preferredSize: [150,150],value:7) //NEW★
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

			//Support for large ranges ?
			radial1Gauge(preferredSize: [150,150],minValue:0,maxValue:10000,
				trackVisible :true,
				trackSection :100
			)
			 
			radial1LcdGauge(preferredSize: [150,150],value:bind{model.cpu_usage},
				thresholdVisible:true,minMeasuredValueVisible :true,maxMeasuredValueVisible :true) 
			radial1SquareGauge(id:'squaregauge',preferredSize: [150,150],
				minValue:0,maxValue:100,title:'cpu meter',unitString:'%') 

			radial1VerticalGauge(id:'radial1Vertical',preferredSize: [150,150],
				backgroundColorFromTheme:false	//★ NEED!!
			) 
			radial1Vertical.setCustomBackground(
				new java.awt.LinearGradientPaint(
						new java.awt.geom.Point2D.Double(radial1Vertical.bounds2D.minX + 20, radial1Vertical.bounds2D.minY + 20), 
						new java.awt.geom.Point2D.Double(radial1Vertical.bounds2D.maxX - 20, radial1Vertical.bounds2D.maxY - 20), 
						[0.0f,0.25f,0.5f,0.75f,1.0f ] as float[], 
						[java.awt.Color.MAGENTA,java.awt.Color.YELLOW,java.awt.Color.GREEN,java.awt.Color.BLUE,java.awt.Color.RED] as java.awt.Color[]
					)
			)


			radial2Gauge(id:'radial2',preferredSize: [150,150],
				backgroundColorFromTheme:false	//★ NEED!!
			) 
			radial2.setCustomBackground(
					new java.awt.RadialGradientPaint(
						//radial2.center, 
						radial1Vertical.center, 
						(float)(radial2.bounds2D.width * 0.4f), 
						[0.0f,0.25f,0.5f,0.75f,1.0f ] as float[], 
						[java.awt.Color.MAGENTA,java.awt.Color.YELLOW,java.awt.Color.GREEN,java.awt.Color.BLUE,java.awt.Color.RED] as java.awt.Color[]
					)
			)
		}
		//5
		panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
		  	boxLayout()
			label(text:'5  ')
			radial2LcdGauge(preferredSize: [150,150],
				customBackground:java.awt.Color.MAGENTA,
				,backgroundColorFromTheme:false //★ NEED!!
			) 

			radial3Gauge(preferredSize: [150,150],
						sectionsVisible :true,//★ NEED!!
						sections:[[0, 33, java.awt.Color.GREEN],[33, 66, java.awt.Color.YELLOW],[66, 100, java.awt.Color.RED]] as Section[] 
			)

			radial3LcdGauge(preferredSize: [150,150],
						areaColor:java.awt.Color.CYAN,
						areaStart :80,
						//areaStop  :100,
						areaVisible :true	//★ NEED!!
			) 

			radial4Gauge(preferredSize: [150,150],
				title:'hogehoge',unitString:'fuga',
				frameDesign:FrameDesign.BLACK_METAL,
				pointerColor:PointerColor.BLUE,
				backgroundColor:BackgroundColor.BEIGE,
				tickmarkColor:java.awt.Color.RED,
				tickmarkColorFromTheme:false,			//★ NEED!!
				trackStartColor:java.awt.Color.LIGHT_GRAY,
				trackSectionColor:java.awt.Color.PINK,
				trackStopColor:java.awt.Color.MAGENTA
			) 

				radial4LcdGauge(preferredSize: [150,150],
					ledColor:LedColor.YELLOW_LED,
					pointerColor:PointerColor.WHITE,
					labelColor:java.awt.Color.GREEN,
					labelColorFromTheme:false				//★ NEED!!
					) 
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





