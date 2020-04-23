package objects3D;

import org.lwjgl.opengl.GL11;
import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

public class Cylinder {

	public Cylinder() {
	}

	//@param angle the radians value that will be used to compute sin
	private float sin(float angle) {
	    //Taylor Series for sin(x)
	    //sin(x) = x - x^3/3! + x^5/5! - x^7/7! + ... 
	    float x=angle;
	    float res=0, pow=angle, fact=1;
	    for(int i=0; i<20; ++i)
	    {
	      res+=pow/fact;
	      pow*=-1*x*x;
	      fact*=(2*(i+1))*(2*(i+1)+1);
	    }

	    return res;
	    //return (float) Math.sin(angle);
	}
	
	//@param angle the radians value that will be used to compute cos
	private float cos(float angle) {
	    //cos(x) = sin(x+pi/2)
	    return sin(angle+3.141592653f/2);
	}
	
	// remember to use Math.PI isntead PI
	// Implement using notes and examine Tetrahedron to aid in the coding look at
	// lecture 7 , 7b and 8
	public void DrawCylinder(float radius, float height, int nSegments) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		float PI=(float)Math.PI;
		for (float i = 0.0f; i < nSegments; i += 1.0) {
			/* a loop around circumference of a tube */ 
			float angle = PI * i * 2.0f / nSegments;
			float nextAngle = PI * (i + 1.0f) * 2.0f / nSegments;
			
			/* compute sin & cosine */ 
			float x1 = radius*sin(angle), y1 = radius*cos(angle);
			float x2 = radius*sin(nextAngle), y2 = radius*cos(nextAngle);
			/* draw top triangle */ drawTriangle(x1, y1, 0.0f, x1, y1, 1.0f*height, x2, y2, 1.0f*height);
			/* draw bottom triangle */ drawTriangle(x1, y1, 0.0f, x2, y2, 0.0f, x2, y2, 1.0f*height);
			
			
			drawTriangle(x1,y1,0,x2,y2,0,0,0,0,true);
			drawTriangle(x1,y1,1*height,x2,y2,1*height,0,0,1*height,true);
		}
		GL11.glEnd();
	}

	
	private void drawTriangle(float x0,float y0,float z0,float x1,float y1,float z1,float x2,float y2,float z2) {	 	
		GL11.glNormal3f(x0, y0, 0);
		GL11.glVertex3f(x0,y0,z0);
		GL11.glNormal3f(x1, y1, 0);
		GL11.glVertex3f(x1,y1,z1);
		GL11.glNormal3f(x2, y2, 0);
		GL11.glVertex3f(x2,y2,z2);
	}
	
	private void drawTriangle(float x0,float y0,float z0,float x1,float y1,float z1,float x2,float y2,float z2,boolean needNormal) {
		Point4f a=new Point4f(x0,y0,z0,0f);
		Point4f b=new Point4f(x1,y1,z1,0f);
		Point4f c=new Point4f(x2,y2,z2,0f);
		
		
		Vector4f v = a.MinusPoint(b); 
		Vector4f w = a.MinusPoint(c);
		Vector4f normal = v.cross(w).Normal();
		if(needNormal)
	 	GL11.glNormal3f(normal.x, normal.y, normal.z);
	 	
		GL11.glVertex3f(x0,y0,z0);
		GL11.glVertex3f(x1,y1,z1);
		GL11.glVertex3f(x2,y2,z2);
	}
}
