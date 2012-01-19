//  see http://blog.livedoor.jp/reona396/archives/53573407.html
//      http://www.syuheiuda.com/
//      http://itpro.nikkeibp.co.jp/article/MAG/20101122/354400/

package processtest;

import SimpleOpenNI.*;
import processing.core.*;
import java.util.*;

public class HandTrackingJP extends PApplet {


	float b = 1.5F;
	int t;


	String moji = "臨兵闘者皆陣裂在前";
	int len = moji.length();
	int angle = 360 / len;
	int radius = 50;


	SimpleOpenNI kinect;
	Map hands = new HashMap();

	public void setup() {
	  kinect = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
	  kinect.setMirror(true);

	  kinect.enableDepth();
	  kinect.enableRGB();
	  kinect.alternativeViewPointDepthToImage();

	  kinect.enableGesture();
	  kinect.enableHands();
	  
	  //kinect.addGesture("Wave");
	  //kinect.addGesture("Click");
	  //kinect.addGesture("RaiseHand");//腕あげる

	  size(kinect.depthWidth(), kinect.depthHeight());
	  //PFont mahou = loadFont("mahou.vlw");
	  PFont mahou = createFont("メイリオ", 30);
	  textFont(mahou, 20*b);
	  colorMode(HSB, 360, 100, 100);
	  textAlign(CENTER);
	  smooth();

	  addGesture();
	}


	private void addGesture(){
	  //kinect.addGesture("Wave");
	  //kinect.addGesture("Click");
	  kinect.addGesture("RaiseHand");//腕あげる
	}

	public void draw() {
	  kinect.update();

	  image(kinect.rgbImage(), 0, 0);
	  mahoujin();
	}



	private void mahoujin() {

	  for (Object obj : hands.entrySet()) {
			Map.Entry entry = (Map.Entry)obj;
	    int id = (Integer)entry.getKey();
	    PVector pos3d = (PVector)entry.getValue();

	    PVector posScreen = new PVector();
	    kinect.convertRealWorldToProjective(pos3d, posScreen);

	    translate(posScreen.x, posScreen.y);
	    for (int i = 0; i  < 9; i++) {
	      pushMatrix();
	      rotate(radians(i * angle));
	      translate(radius*0.9*b, 0);
	      rotate(radians(90));
	      fill(179, 53, 100);
	      text(moji.charAt(i), 0, 0);
	      popMatrix();
	    }


	    rectMode(CENTER);
	    noFill();
	    strokeWeight(2.5*b);
	    stroke(179, 53, 100);
	    rect(0, 0, 50*b, 50*b);
	    pushMatrix();
	    rotate(radians(t));
	    rect(0, 0, 50*b, 50*b);
	    popMatrix();
	    pushMatrix();

	    for (int j = 0;j < 4;j++) {
	      rotate(radians(45*j));
	      ellipse(0, 0, 40*b, 15*b);
	    }
	    popMatrix();

	    ellipse(0, 0, 65*b, 65*b);
	    ellipse(0, 0, 75*b, 75*b);
	    ellipse(0, 0, 130*b, 130*b);

	    strokeWeight(5*b);
	    ellipse(0, 0, 140*b, 140*b);

	    t += 2;
	    filter(BLUR, 1.5);
	  }

	}
	// -----------------------------------------------------------------
	// hand events <5>
	public void onCreateHands(int handId, PVector pos , float time) {
	  //kinect.convertRealWorldToProjective(pos, pos);
	  hands.put(handId, pos);
	}

	public void onUpdateHands(int handId, PVector pos, float time) {
	  //kinect.convertRealWorldToProjective(pos, pos);
	  hands.put(handId, pos);
	}

	public void onDestroyHands(int handId, float time) {
	  //hands.remove(handId);
	  hands.clear();
	  //kinect.addGesture("RaiseHand");
	  addGesture();
	}

	// -----------------------------------------------------------------
	// gesture events <4>
	public void onRecognizeGesture(String strGesture, PVector idPosition, PVector endPosition) {
	  println("ジェスチャー名: "+ strGesture);
	  kinect.startTrackingHands(endPosition);
	  kinect.removeGesture(strGesture); 
	}

}
