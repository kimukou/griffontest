Using SteelSeries Plugin

  http://docs.codehaus.org/display/GRIFFON/Steel+Plugin
    SteelSeries 2.1

  http://docs.codehaus.org/display/GRIFFON/Harmoniccode+Plugin

-----------------------------------------------------------------------
>nessesary reference 

version 1.4
  http://kenai.com/projects/steelseries/sources/trunk/show/SteelSeries/src/eu/hansolo/steelseries/gauges
  http://kenai.com/projects/steelseries/sources/trunk/show/SteelSeries/src/eu/hansolo/steelseries/tools

  Javadoc<missing 
    http://idisk.mac.com/han.solo-Public/SteelSeries_Doc/index.html

version 2.2
  http://kenai.com/projects/steelseries/sources/v2/show/SteelSeries2/src/eu/hansolo/steelseries/gauges
  http://kenai.com/projects/steelseries/sources/v2/show/SteelSeries2/src/eu/hansolo/steelseries/tools

  Javadoc
    http://idisk.mac.com/han.solo-Public/SteelSeries2/javadoc/index.html

  SteelExtras(Clock parts)
    http://kenai.com/projects/steelseries/sources/v2/show/SteelExtras/src/eu/hansolo/steelseries/extras


  SteelSeries upgrade memo(1.4=>2.2) 
    1)eu.hansolo.steelseries.extras(SteelExtras.jar) moving component of One Part
      altimeter is 2.2 can use,but 2.1 not.

    2)Poi dupulicate 
				eu.hansolo.steelseries.extras.Poi
				eu.hansolo.steelseries.tools.Poi
			It's nessesary to above import 
			import eu.hansolo.steelseries.extras.* //since 2.1

    3)Change of name of property 
    useTitleAndUnitFont=> usingTitleAndUnitFont
    useCustomTickmarkLabels=>usingCustomTickmarkLabels
    backgroundColorFromTheme=>customBackgroundVisible,backgroundVisible 
    tickmarkColorFromTheme=>usingTickmarkColorFromTheme
    labelColorFromTheme => usingLabelColorFromTheme



SteelSeries upgrade memo(2.2=>3.9) 
	usingTitleAndUnitFont => titleAndUnitFontEnabled
	usingCustomTickmarkLabels => customTickmarkLabelsEnabled

	backgroundImage =>bImage
	foregroundImage =>fImage
	customBackgroundVisible -> backgroundVisible

	radar.animate() =>radar.animate(true) 

	remove component
		digitalRadialLcdGauge 
		linearLcdGauge 
		linearBargraphLcd 
		radial1Gauge 
		radial1LcdGauge 
		radial2Gauge 
		radial2LcdGauge 
		radial3Gauge 
		radial3LcdGauge 
		radial4Gauge 
		radial4LcdGauge 
		radialBargraph 
		radialBargraph1Lcd 
		radialBargraph2 
		radialBargraph2Lcd 
		radialBargraph3 
		radialBargraph3Lcd 
		radialBargraph4 
		radialBargraph4Lcd 
		radialSquareVertical 

	add component
		AirCompass 
		Horizon
		radialSquareSmall
		sparkLine



reference
  http://harmoniccode.blogspot.com/

capture

* <img src="https://github.com/kimukou/griffontest/raw/master/steeltest/screen_capture_page1.png" width="400" height="450">
* <img src="https://github.com/kimukou/griffontest/raw/master/steeltest/screen_capture_page2.png" width="400" height="450">
* <img src="https://github.com/kimukou/griffontest/raw/master/steeltest/screen_capture_page3.png" width="400" height="450">
* <img src="https://github.com/kimukou/griffontest/raw/master/steeltest/screen_capture_page4.png" width="400" height="450">

