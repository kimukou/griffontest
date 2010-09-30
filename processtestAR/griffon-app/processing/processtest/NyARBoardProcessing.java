package processtest;

import processing.core.*;
import processing.video.*;
import jp.nyatla.nyar4psg.*;
import processing.opengl.*;
import javax.media.opengl.*;

public class NyARBoardProcessing extends PApplet {
	//Capture cam;
	CaptureDS cam;
	NyARBoard nya;

	public void setup() {
	  size(640,480,OPENGL);
	  colorMode(RGB, 100);
		cam=new CaptureDS(this,width,height);
	  //cam=new Capture(this,width,height);
	  nya=new NyARBoard(this,width,height,"camera_para.dat","patt.hiro",80);
	}

	public void draw() {
	  background(255);
	  if (cam.available() ==true) {
	    cam.read();
	    image(cam,0,0);
	    if(nya.detect(cam)){
	      PGraphicsOpenGL pgl = (PGraphicsOpenGL) g;
	      nya.beginTransform(pgl);
	      noFill();
	      stroke(255,200,0);
	      translate(0,0,20);
	      box(40);
	      nya.endTransform();
	    }
	  }
	}
}
