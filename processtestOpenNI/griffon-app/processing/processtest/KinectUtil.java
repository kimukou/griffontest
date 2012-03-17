package processtest;

import SimpleOpenNI.*;
import processing.core.*;

public class KinectUtil {

    private static SimpleOpenNI kinect = null;

    public synchronized static SimpleOpenNI getInstance(PApplet parent){
        if(kinect!=null) return kinect;

        System.out.println("Class:"+ parent.getClass().getName());

        kinect = new SimpleOpenNI(parent,SimpleOpenNI.RUN_MODE_MULTI_THREADED);
        kinect.setMirror(true);

        //enable depthMap generation
        kinect.enableDepth();
        kinect.enableRGB();
        kinect.alternativeViewPointDepthToImage();

        // enable hands + gesture generation <1>
        kinect.enableGesture();
        kinect.enableHands();

        return kinect;
    }

    public static void setKinect(SimpleOpenNI kinect_){
        kinect = kinect_;
    }
}
