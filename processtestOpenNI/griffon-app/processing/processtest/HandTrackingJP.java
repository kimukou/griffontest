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


	//String moji = "臨兵闘者皆陣裂在前";
	String moji = "ＪＧＧＵＧ";
	int len = moji.length();
	int angle = 360 / len;
	int radius = 50;


	SimpleOpenNI kinect;
	Map hands = new HashMap();

	public void setup() {
	  kinect = KinectUtil.getInstance(this);
	  /*
	  kinect = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
	  kinect.setMirror(true);

	  kinect.enableDepth();
	  kinect.enableRGB();
	  kinect.alternativeViewPointDepthToImage();

	  kinect.enableGesture();
	  kinect.enableHands();
	  */


	  //kinect.addGesture("Wave");
	  //kinect.addGesture("Click");
	  //kinect.addGesture("RaiseHand");//腕あげる
	  addGesture();
	  size(kinect.depthWidth(), kinect.depthHeight());


	  //PFont mahou = loadFont("mahou.vlw");
	  PFont mahou = createFont("メイリオ", 30);
	  textFont(mahou, 20*b);
	  colorMode(HSB, 360, 100, 100);
	  textAlign(CENTER);
	  smooth();
	  
		//noLoop();
	}


	private void addGesture(){
	  kinect.addGesture("Wave");
	  kinect.addGesture("Click");
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

	    translate((float)posScreen.x, (float)posScreen.y);
			int len_moji = moji == null ? 0 :moji.length();
	    for (int i = 0; i  < len_moji; i++) {
	      pushMatrix();
	      rotate(radians(i * angle));
	      translate((float)(radius*0.9*b), 0L);
	      rotate(radians(90));
	      fill(179, 53, 100);
	      text(moji.charAt(i), 0, 0);
	      popMatrix();
	    }


	    rectMode(CENTER);
	    noFill();
	    strokeWeight((float)2.5*b);
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
	    filter(BLUR, (float)1.5);
	  }

	}
	// -----------------------------------------------------------------
	// hand events <5>
	public void onCreateHands(int handId, PVector pos , float time) {
		println("onCreateHands:"+handId);

	  //kinect.convertRealWorldToProjective(pos, pos);
	  hands.put(handId, pos);
	}

	public void onUpdateHands(int handId, PVector pos, float time) {
		println("onUpdateHands:"+handId);

	  //kinect.convertRealWorldToProjective(pos, pos);
	  hands.put(handId, pos);
	}

	public void onDestroyHands(int handId, float time) {
		println("onDestroyHands:"+handId);
	  //hands.remove(handId);

	  hands.clear();
	  addGesture();
	}

	// -----------------------------------------------------------------
	// gesture events <4>
	public void onRecognizeGesture(String strGesture, PVector idPosition, PVector endPosition) {
	  println("onRecognizeGesture: "+ strGesture);
	  kinect.startTrackingHands(endPosition);

	  kinect.removeGesture(strGesture); 
	}

}
