package processtest

import processing.video.*
import processing.core.*
import jp.nyatla.nyar4psg.*


import static griffon.util.GriffonApplicationUtils.isWindows

public class NyARBoardProcessing extends PApplet {

	def cam
	MultiMarker nya

	public void setup() {
		  size(640,480,P3D)
		  colorMode(RGB, 100)
		  println(MultiMarker.VERSION)

			if(isWindows) cam=new CaptureDS(this,width,height)
		  else					cam=new Capture(this,width,height)

		  nya=new MultiMarker(this,width,height,"camera_para.dat",NyAR4PsgConfig.CONFIG_PSG)
		  nya.addARMarker("patt.hiro",80)
	}

	public void draw() {
		  if (cam.available() !=true)return
		  cam.read()
		  nya.detect(cam)
		  background(0)
		  nya.drawBackground(cam)//frustumを考慮した背景描画
		  if((!nya.isExistMarker(0))){
		    return
		  }
		  nya.beginTransform(0)
		  fill(0,0,255)
		  translate(0,0,20)
		  box(40)
		  nya.endTransform()
	}

}
