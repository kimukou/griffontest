package steeltest

import eu.hansolo.signaltower.*
import eu.hansolo.custom.mbutton.*
import eu.hansolo.custom.*

import com.jidesoft.swing.AnimatorListener
import static com.jidesoft.swing.MeterProgressBar.*

import eu.hansolo.steelseries.extras.* //since 2.1
import eu.hansolo.steelseries.tools.*
import eu.hansolo.steelseries.gauges.*
import java.awt.*
import java.awt.event.*
import javax.imageio.ImageIO


import com.dreamarts.system.utils.*
import net.miginfocom.swing.MigLayout


import griffon.transitions.FadeTransition2D


/*
import griffon.builder.css.CSSDecorator
// make all components have a white background
def style = """

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
*/


/*
java.awt.Color.black 黒
java.awt.Color.BLACK 黒
java.awt.Color.blue  青
java.awt.Color.BLUE  青
java.awt.Color.cyan  シアン
java.awt.Color.CYAN  シアン
java.awt.Color.DARK_GRAY ダークグレイ
java.awt.Color.darkGray  ダークグレイ
java.awt.Color.gray  グレイ
java.awt.Color.GRAY  グレイ
java.awt.Color.green 緑
java.awt.Color.GREEN 緑
java.awt.Color.LIGHT_GRAY ライトグレイ
java.awt.Color.lightGray  ライトグレイ
java.awt.Color.magenta    マゼンタ
java.awt.Color.MAGENTA    マゼンタ
java.awt.Color.orange     オレンジ
java.awt.Color.ORANGE     オレンジ
java.awt.Color.pink       ピンク
java.awt.Color.PINK       ピンク
java.awt.Color.red        赤
java.awt.Color.RED        赤
java.awt.Color.white      白
java.awt.Color.WHITE      白
java.awt.Color.yellow     黄
java.awt.Color.YELLOW     黄
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
LcdColor.BLUE_LCD

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
PointerColor.GRAY   //from 0.2.1
PointerColor.WHITE  //from 0.2.1

*/


actions {
// Look & Feel dialog disp action
   action(id:'showLaf',
        name: 'show Look & Feel',
        closure: controller.showLaf,
        mnemonic: 'L',
        accelerator: 'ctrl L')
}

frame = application(id:"mainFrame",title: app.config.application.title,
  size: [800,800],
  //pack: true,
  //location: [300,300],
  locationByPlatform:true,
  iconImage: imageIcon('/griffon-icon-48x48.png').image,
  iconImages: [imageIcon('/griffon-icon-48x48.png').image,
               imageIcon('/griffon-icon-32x32.png').image,
               imageIcon('/griffon-icon-16x16.png').image]) {
    // add content here
    //borderLayout()

    menuBar(){
        menu(mnemonic:'L', 'Look & Feel'){
            menuItem(action:showLaf)
        }
    }

current.contentPane.background = Color.BLACK
main = current.contentPane
transitionLayout(defaultDuration: 2000, defaultTransition: new FadeTransition2D(Color.BLACK))


panel(constraints: 'page1', opaque: false) {
    migLayout(layoutConstraints: "gap 0,insets 0,fill")
    //1
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
        label(text:'1  ')

      altimeter(preferredSize: [300,300])

      clock(id:'clock',preferredSize: [200,200],
            backgroundColor:BackgroundColor.WHITE,
            frameDesign:FrameDesign.SHINY_METAL)
      clock.CLOCK_TIMER.addActionListener([
        actionPerformed: { source -> 
          //println "clock == ${view.clock.hour}:${view.clock.minute} <${java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)}:${view.clock.minute}>"
        }
      ] as ActionListener)

//2011/03/23 view initialzed Clock move start
      g = clock.bImage.getGraphics()
      //g = clock.fImage.createGraphics()
      icon = imageIcon('/griffon-icon-48x48.png').image
      //icon = ImageIO.read(new File('/griffon-icon-48x48.png'))
      g.drawImage(icon, 0,0, null)
      g.drawString("Sine Wave", 0, 0); // Draw some text
      g.dispose()
//2011/03/23 view initialzed Clock move end

      compass (id:'compass',preferredSize: [300,300])
      compass.setValueAnimated 90
    }
    //2
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'2  ')
      digitalRadialGauge (preferredSize: [300,300])
      //since 0.5 remove
      //digitalRadialLcdGauge (id:'lcdgauge',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう',preferredSize: [300,300])

      //displayCircular(id:'circular',minValue:0,maxValue:100,lcdValue:50,title:'ほげほげ',unitString:'ふがふが',lcdUnitString:'まいう',preferredSize: [300,300])
      displayCircular(id:'circular',value:0,lcdDecimals:3,unitString:'SEC',
          lcdColor:LcdColor.ORANGE_LCD,
          backgroundColor:BackgroundColor.GREEN,
          frameDesign:FrameDesign.BLACK_METAL,
          digitalFont:true,
          preferredSize: [300,300])
      //circular.setValueAnimated model.count
      displayCircular(id:'circular2',value:bind{model.count},lcdDecimals:0,unitString:'SEC',
          lcdColor:LcdColor.BLUE_LCD,
          backgroundColor:BackgroundColor.RED,
          frameDesign:FrameDesign.BLACK_METAL,
          digitalFont:true,preferredSize: [300,300])
    }
    //3
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'3  ')
      panel(){
          boxLayout(axis:javax.swing.BoxLayout.PAGE_AXIS)
        displayMulti(preferredSize: [140,40])
        displayRectangular(preferredSize: [140,60],minimumSize: [140,60]) 
        displaySingle(preferredSize: [140,40]) 
      }
      radial2TopGauge(preferredSize: [300,300],value:20)    //NEW★
      radialCounterGauge(id:'radialCounter', preferredSize: [300,300],value:7) //NEW★
    }
}

panel(constraints: 'page2', opaque: false) {
    migLayout(layoutConstraints: "gap 0,insets 0,fill")
    //4
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'4  ')
      level (preferredSize: [300,300])
      linearGauge (preferredSize: [300,300])
      //since 0.5 remove
      //linearLcdGauge(preferredSize: [300,300]) 
    }

    //5
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0), cssClass: "active"){
        boxLayout()
      label(text:'5  ')

      radial1SquareGauge(id:'squaregauge',preferredSize: [300,300],
        minValue:0,maxValue:100,title:'cpu meter',unitString:'%') 

      radial1VerticalGauge(id:'radial1Vertical',preferredSize: [300,300],
        backgroundVisible:true  //★ NEED!!
      ) 
      radial1Vertical.setCustomBackground(
        new java.awt.LinearGradientPaint(
            new java.awt.geom.Point2D.Double(radial1Vertical.bounds2D.minX + 20, radial1Vertical.bounds2D.minY + 20), 
            new java.awt.geom.Point2D.Double(radial1Vertical.bounds2D.maxX - 20, radial1Vertical.bounds2D.maxY - 20), 
            [0.0f,0.25f,0.5f,0.75f,1.0f ] as float[], 
            [java.awt.Color.MAGENTA,java.awt.Color.YELLOW,java.awt.Color.GREEN,java.awt.Color.BLUE,java.awt.Color.RED] as java.awt.Color[]
          )
      )

      radialGauge(id:'radial2',preferredSize: [300,300],
        backgroundVisible:true  //★ NEED!!
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

    //6
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'6  ')
      //poi test
      poi(id:'RAITH',name:'Raith',lat:51.485605,lon:7.479544)
      poi(id:'HOME',name:'Home',lat:51.911784,lon:7.633789)
      poi(id:'MUENSTER',name:'Munster',lat:51.972502,lon:7.62989)
      poi(id:'ESSEN',name:'Essen',lat:51.462721,lon:7.015057)
      poi(id:'BOCHUM',name:'Bochum',lat:51.487526,lon:7.211781)
      poi(id:'WUPPERTAL',name:'Wuppertal',lat:51.260783,lon:7.149982)
      radar(id:'radar',preferredSize: [300,300],
        range:70000,
        myLocation:RAITH
      )
      radar.add HOME
      radar.add MUENSTER
      radar.add ESSEN
      radar.add BOCHUM
      radar.add WUPPERTAL
      radar.animate(true) 

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

/*
      //since 0.5 remove
      //Support for large ranges ?
      radial1Gauge(id:'radial1',preferredSize: [300,300],minValue:0,maxValue:10000,
        scaleDividerPower:3
        //trackVisible :true,
        //trackSection :100
      )
       
      radial1LcdGauge(id:'radial1Lcd',preferredSize: [300,300],value:bind{model.cpu_usage},
        thresholdVisible:true,minMeasuredValueVisible :true,maxMeasuredValueVisible :true) 
*/
    }
}

panel(constraints: 'page3', opaque: false) {
    migLayout(layoutConstraints: "gap 0,insets 0,fill")
    //7
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'7  ')
/*
      //since 0.5 remove
      radial2LcdGauge(preferredSize: [300,300],
        customBackground:java.awt.Color.MAGENTA,
        backgroundVisible:true //★ NEED!!
      ) 

      radial3Gauge(preferredSize: [300,300],
        sectionsVisible :true,//★ NEED!!
        sections:[[0, 33, java.awt.Color.GREEN],[33, 66, java.awt.Color.YELLOW],[66, 100, java.awt.Color.RED]] as Section[] 
      )

      //this.registerBeanFactory("radial3LcdGauge", Radial3Lcd.class)
      radial3LcdGauge(id:'radial3Lcd',
        preferredSize: [300,300],
        areaColor:java.awt.Color.CYAN,
        areaStart:40,
        areaStop:70,
        areaVisible:true  //★ NEED!!
      ) 
*/
    }
    //8
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'8  ')

/*
      //since 0.5 remove
      radial4Gauge(preferredSize: [300,300],
        title:'hogehoge',unitString:'fuga',
        frameDesign:FrameDesign.BLACK_METAL,
        pointerColor:PointerColor.BLUE,
        backgroundColor:BackgroundColor.BEIGE,
        tickmarkColor:java.awt.Color.RED,
        tickmarkColorFromThemeEnabled:false,      //★ NEED!!
        trackStartColor:java.awt.Color.LIGHT_GRAY,
        trackSectionColor:java.awt.Color.PINK,
        trackStopColor:java.awt.Color.MAGENTA
      ) 

      radial4LcdGauge(preferredSize: [300,300],
        ledColor:LedColor.YELLOW_LED,
        pointerColor:PointerColor.WHITE,
        labelColor:java.awt.Color.GREEN,
        labelColorFromThemeEnabled:false        //★ NEED!!
      ) 
*/
      led(preferredSize: [300,300])//since 0.3
    }

    //9
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'9  ')
/*
      //since 0.5 remove
      linearBargraph (
        preferredSize: [300,300],
        value:50,
        barGraphColor:ColorDef.CYAN
      )//since 0.3
      linearBargraphLcd (
        preferredSize:  [150,300],
        value:50,
        barGraphColor:ColorDef.MAGENTA,
        orientation:javax.swing.SwingConstants.VERTICAL
      )//since 0.3
*/
    }

}

panel(constraints: 'page4', opaque: false) {
    migLayout(layoutConstraints: "gap 0,insets 0,fill")
    //10
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
        boxLayout()
      label(text:'10  ')
/*
      //since 0.5 remove
      radialBargraph1(preferredSize: [300,300],
        barGraphColor:ColorDef.RED,
        value:50,
        customBackground:java.awt.Color.MAGENTA,
        backgroundVisible:true //★ NEED!!
      ) 
      radialBargraph1Lcd(preferredSize: [300,300],
        barGraphColor:ColorDef.ORANGE,
        value:50
      )
      //■section color not action
      radialBargraph2(preferredSize: [300,300],
        barGraphColor:ColorDef.YELLOW,
        value:50,
        sectionsVisible :true,//★ NEED!!
        sections:[[0, 33, java.awt.Color.GREEN],[33, 66, java.awt.Color.YELLOW],[66, 100, java.awt.Color.RED]] as Section[] 
      )
*/
      sparkLine(id:'sparkLine',
        backgroundVisible:true,
        infoLabelsVisible:true,
        baseLineVisible:true,
        averageVisible:true,
        normalAreaVisible:true,
        enabled:true
      )
    }

    //11
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
      boxLayout()
      label(text:'11  ')
      airCompass(id:'airCompass',
        frameType:FrameType.SQUARE,
        rotateTickmarks:true
      )
      horizon(id:'horizon',
        frameVisible:true,
        foregroundVisible:true,
        frameType:FrameType.SQUARE
      )
      radialSquareSmall(id:'radialSquareSmall',
        title:'hogehoge',
        unitString:'fugafuga',
        trackVisible:true,
        thresholdVisible:true,
        minMeasuredValueVisible:true,
        maxMeasuredValueVisible:true,
        ledVisible:true,
        sectionsVisible:true
      )

/*
      //since 0.5 remove
      //■aria color not action
      radialBargraph2Lcd(preferredSize: [300,300],
        barGraphColor:ColorDef.GREEN,
        value:50,
        areaColor:java.awt.Color.CYAN,
        areaStart:40,
        areaStop:70,    
        areaVisible:true  //★ NEED!!
      ) 
      radialBargraph3(preferredSize: [300,300],
        barGraphColor:ColorDef.BLUE,
        value:50,
        title:'hogehoge',unitString:'fuga',
        frameDesign:FrameDesign.BLACK_METAL,
        backgroundColor:BackgroundColor.BEIGE,
        tickmarkColor:java.awt.Color.RED,
        tickmarkColorFromThemeEnabled:false,      //★ NEED!!
        trackStartColor:java.awt.Color.LIGHT_GRAY,
        trackSectionColor:java.awt.Color.PINK,
        trackStopColor:java.awt.Color.MAGENTA
      ) 
      radialBargraph3Lcd(preferredSize: [300,300],
        barGraphColor:ColorDef.RAITH,
        value:50,
        ledColor:LedColor.YELLOW_LED,
        pointerColor:PointerColor.WHITE,
        labelColor:java.awt.Color.GREEN,
        labelColorFromThemeEnabled:false        //★ NEED!!
      ) 
*/
    }

    //12
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
      boxLayout()
      label(text:'12  ')
/*
      //since 0.5 remove
      radialBargraph4(preferredSize: [300,300],
        barGraphColor:ColorDef.GREEN_LCD,
        value:50
      )
      radialBargraph4Lcd(preferredSize: [300,300],
        titleAndUnitFont:new Font("ＭＳ ゴシック",Font.PLAIN,20),   //Font Change(to Verdana)
        titleAndUnitFontEnabled:true,                   //Font Change(to Verdana)
        title:'ほげほげ',
        unitString:'ふがふが',
        lcdUnitString:'まいう',
        barGraphColor:ColorDef.JUG_GREEN,
        value:50
      )
*/
      linearBargraph(preferredSize: [100,100],value:50,
        barGraphColor:ColorDef.CYAN
      )
/*
      linearBargraphLcd(preferredSize: [100,100],value:50,
        barGraphColor:ColorDef.MAGENTA,
        orientation:javax.swing.SwingConstants.HORIZONTAL
      )
*/
      panel(){
        boxLayout(axis:javax.swing.BoxLayout.Y_AXIS)
        led(preferredSize: [30,30],
          ledColor:LedColor.RED_LED,
          ledOn:true
        )
        led(preferredSize: [30,30],
          ledColor:LedColor.GREEN_LED,
          ledOn:false
        )
        led(preferredSize: [30,30],
          ledColor:LedColor.BLUE_LED,
          ledBlinking:true
        )
        led(preferredSize: [30,30],
          ledColor:LedColor.YELLOW_LED,
          ledBlinking:true,
          ledOn:false
        )
        led(preferredSize: [30,30],
          ledColor:LedColor.ORANGE_LED,
          ledBlinking:false,
          ledOn:true
        )
      }
    }

}
panel(constraints: 'page5', opaque: false) {
    migLayout(layoutConstraints: "gap 0,insets 0,fill")
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
      boxLayout(axis:javax.swing.BoxLayout.X_AXIS)
      label(text:'13  ')
      //using SignalTower.jar
      signalTower(preferredSize: [100,200],
            redOn:true
      )
      signalTower(preferredSize: [100,200],
            yellowOn:true
      )
      signalTower(preferredSize: [100,200],
            greenOn:true
      )
    }
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
      boxLayout(axis:javax.swing.BoxLayout.Y_AXIS)
      label(text:'14  ')
      mbutton(text:"homepage"
        ,preferredSize: [100,20]
        ,alpha:0.0f
        ,effectColor:Color.RED
        ,effectColorPressed:Color.RED
      )
      mbutton(text:"about me"
        ,preferredSize: [100,20]
        ,alpha:0.0f
        ,effectColor:Color.BLUE
        ,effectColorPressed:Color.BLUE
      )
      mbutton(text:"services"
        ,preferredSize: [100,20]
        ,alpha:0.0f
        ,effectColor:Color.GREEN
        ,effectColorPressed:Color.GREEN
      )
      mbutton(text:"portfolio"
        ,preferredSize: [100,20]
        ,alpha:0.0f
        ,effectColor:Color.ORANGE 
        ,effectColorPressed:Color.ORANGE 
      )
      mbutton(text:"contact"
        ,preferredSize: [100,20]
        ,alpha:0.0f
        ,effectColor:Color.CYAN 
        ,effectColorPressed:Color.CYAN 
      )
    }

    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
      boxLayout(axis:javax.swing.BoxLayout.Y_AXIS)
      label(text:'15  ')
      steelCheckBox(
        text:"standard",
        preferredSize: [100,20]
        //ui
      )
      steelCheckBox(
        text:"green",
        preferredSize: [100,20],
        colored:true,
        rised:true,
        selectedColor:eu.hansolo.tools.ColorDef.GREEN
      )
      steelCheckBox(
        text:"red",
        preferredSize: [100,20],
        colored:true,
        rised:true,
        selectedColor:eu.hansolo.tools.ColorDef.RED
      )
      steelCheckBox(
        text:"disable",
        preferredSize: [100,20],
        enabled :false
      )
    }


    //BRIGHT,
    //DARK,
    //CUSTOM
    panel(constraints: "span,wrap, gapbottom 0,gaptop 0",border: emptyBorder(0)){
      boxLayout(axis:javax.swing.BoxLayout.X_AXIS)
      label(text:'16  ')
      rollingCounter(
        id:"rollingCounter1",
        preferredSize: [20,100],
        theme:Theme.BRIGHT,
        maxValue:3,
        switchTime:10,
        offsetIncrement:2,
        offsetDecrement:3
      )
      rollingCounter1.increment()
      rollingCounter(
        id:"rollingCounter2",
        preferredSize: [20,100],
        theme:Theme.DARK
      )
      rollingCounter2.decrement()
      rollingCounter(
        id:"rollingCounter3",
        preferredSize: [20,100],
        theme:Theme.CUSTOM,
        backgroundColor:new java.awt.Color(107, 105, 99, 255)
      )
    }
}
swingRepaintTimeline(main, loop: true)

  //CSSDecorator.applyStyle(style,app.appFrames[0]) //CSS adapt (from griffon 0.3)
  //CSSDecorator.applyStyle(style,app.windowManager.windows[0]) //CSS adapt(from griffon 0.9)

  //It displays and it non-displays it by double-clicking. 
  systemTray {
    trayIcon(id: "trayIcon",
        resource: "/griffon-icon-48x48.png",
        class: groovy.ui.Console,
        toolTip: getMessage("view.tray.tooltip") ,
        actionPerformed: {  frame.visible = !frame.visible }) {
    }
  }

  //Mouse gesture. It is a screen switch in the right and the left.  push the mouse
  def page=1
  def page_min=1
  def page_max=5
  mouseGestures(start: true) {
     // receives gestures as they are recognized (cummulative)
       onGestureMovementRecognized { String s -> println s }
       // the final recognized gesture
      onProcessGesture { String s -> 
      println "<< $s >>" 
      if(s == 'R'){
        page++
        if(page>page_max)page=page_min
        main.layout.show(main, "page${page}")
      }
      if(s == 'L'){
        page--
        if(page<page_min)page=page_max
        main.layout.show(main, "page${page}")
      }

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

// Look & Feel add setting
frame.defaultLookAndFeelDecorated = true

/*
frame.windowOpened={println 'Opened'}   //When it starts first time
frame.windowClosing={println 'closing'} //closing frame by the system menu
frame.windowClosed={println 'closed'}   //dispose called
*/
//Normal＝＞Minimization
frame.windowIconified={
  println 'Iconified'
  view.anim.stop()
  //t.yield()
  pausef=true
} 
//Minimization＝＞Normal
frame.windowDeiconified={
  println 'Deiconified'
  view.anim.start()
  //t.resume()
  pausef=false
}

//actived
frame.windowActivated={
  println 'Activated'
  if(!frame.visible)return
  view.anim.start()
  //t.resume()
  pausef=false
}

//unactived
frame.windowDeactivated={
  println 'Deactivated'
  if(frame.visible)return
  view.anim.stop()
  //t.yield()
  pausef=true
}





