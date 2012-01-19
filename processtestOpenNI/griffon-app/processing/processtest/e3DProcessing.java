package processtest;

import processing.core.*;
import processing.opengl.*;


public class e3DProcessing extends PApplet {
	public void setup() {
			System.out.println("java.library.path="+System.getProperty("java.library.path"));
	    size(400, 400, OPENGL);
	    noStroke();
	    fill(255,190);
	}

	public void draw() {
	    //環境光
	    ambientLight(63, 31, 31);
	    //平行光
	    directionalLight(255,255,255,-1,0,0);
	    //点光源
	    pointLight(63, 127, 255, mouseX, mouseY, 200);
	    //スポットライト
	    spotLight(100, 100, 100, mouseX, mouseY, 200, 0, 0, -1, PI, 2); 

	    background(0);
	    translate(width / 2, height / 2, -20);

	    rotateX((float)(mouseX / 200.0));
	    rotateY((float)(mouseY / 100.0));

	    int dim = 18;
	    for(int i = -height/2; i < height/2; i += dim*1.4) {
		for(int j = -width/2; j < width/2; j += dim*1.4) {
		    pushMatrix();
		    translate(i,j);
		    rotateX((float)radians(30));
		    rotateY((float)radians(30));
		    box(dim,dim,dim);
		    popMatrix();
		}
	    }
	}
}
