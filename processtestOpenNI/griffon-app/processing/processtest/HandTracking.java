//  see https://gist.github.com/1478237
//
package processtest;

import SimpleOpenNI.*;
import processing.core.*;
import java.util.*;

public class HandTracking extends PApplet {


	SimpleOpenNI kinect;
	ArrayList<PVector> handPositions;

	PVector currentHand;
	PVector previousHand;

	public void setup() {
		//kinect = KinectUtil.getInstance(this);
	  kinect = new SimpleOpenNI(this,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
	  kinect.setMirror(true);

	  //enable depthMap generation 
	  kinect.enableDepth();
	  kinect.enableRGB();
	  kinect.alternativeViewPointDepthToImage();
	  
	  // enable hands + gesture generation <1>
	  kinect.enableGesture();
	  kinect.enableHands();

	  kinect.addGesture("RaiseHand"); // <2>

	  size(kinect.depthWidth(), kinect.depthHeight());
	  stroke(255, 0, 0);
	  strokeWeight(2);

	  handPositions = new ArrayList(); // <3>
	}

	public void draw() {
	  kinect.update();
	  //image(kinect.depthImage(), 0, 0);
	  image(kinect.rgbImage(), 0, 0);

		int len_handPositions = handPositions == null ? 0:handPositions.size();
	  for (int i = 1; i < len_handPositions; i++) { //<6>
	    currentHand = handPositions.get(i);
	    previousHand = handPositions.get(i-1);
	    line(previousHand.x, previousHand.y, currentHand.x, currentHand.y);
	  }
	}

	// -----------------------------------------------------------------
	// hand events <5>
	public void onCreateHands(int handId, PVector position, float time) {
	  kinect.convertRealWorldToProjective(position, position);
	  handPositions.add(position);
	}

	public void onUpdateHands(int handId, PVector position, float time) {
	  kinect.convertRealWorldToProjective(position, position);
	  handPositions.add(position);
	}

	public void onDestroyHands(int handId, float time) {
	  handPositions.clear();
	  kinect.addGesture("RaiseHand");
	}

	// -----------------------------------------------------------------
	// gesture events <4>
	public void onRecognizeGesture(String strGesture, 
	                        PVector idPosition, 
	                        PVector endPosition) 
	{
	  kinect.startTrackingHands(endPosition);
	  kinect.removeGesture("RaiseHand"); 
	}
}
