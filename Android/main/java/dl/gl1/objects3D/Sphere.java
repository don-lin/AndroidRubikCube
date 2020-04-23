package dl.gl1.objects3D;

import javax.microedition.khronos.opengles.GL10;

import dl.gl1.GraphicsObjects.Point4f;
import dl.gl1.GraphicsObjects.Vector4f;

import java.util.LinkedList;

public class Sphere {

	public Sphere() {
	}

	public static Point4f[] arrs;

	public static final float PI=3.141592653f;
	public static final float hPI=PI/2;

	//@param angle the radians value that will be used to compute sin
	private float sin(float angle) {
		if(angle>PI)
			return -sin(angle-PI);
		if(angle>hPI)
			return sin(PI-angle);

		//Taylor Series for sin(x)
		//sin(x) = x - x^3/3! + x^5/5! - x^7/7! + ...
		float x=angle;
		float res=0, pow=angle, fact=1;
		for(int i=0; i<4; ++i)
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

	//get the point in specific phi and theta with radius
	public Point4f getPoint(float radius,float phi, float theta) {
		float r = (float)(radius * cos(phi));
		float x = (float)(r * cos(theta));
		float y = (float)(r * sin(theta));
		float z = (float)(radius * sin(phi));
		return new Point4f(x,y,z,0f);
	}

	public void init(float nSlices,float nSegments) {
		float inctheta = (float)(2.0f*Math.PI)/(nSlices);
		float incphi = (float)Math.PI/(nSegments);
		float radius=1;
		LinkedList<Point4f> pointList=new LinkedList<Point4f>();

		for(float theta=-(float)Math.PI; theta < Math.PI; theta+=inctheta) {
			for(float phi=-(float)(Math.PI/2.0f); phi<(Math.PI/2.0f); phi+=incphi) {
				//the points that consist of a quad
				Point4f points[] = {getPoint(radius, phi, theta),getPoint(radius, phi+incphi, theta),
						getPoint(radius, phi+incphi, theta+inctheta),getPoint(radius, phi, theta+inctheta)};
				//draw a quad by using these points
				for (int i = 0; i < points.length; i++) {
					pointList.add(points[i]);
				}
			}
		}
		arrs=new Point4f[pointList.size()];
		for(int i=0;i<pointList.size();i++) {
			arrs[i]=pointList.get(i);
		}

	}

	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8
	// 7b should be your primary source, we will cover more about circles in later lectures to understand why the code works
	public void DrawSphere(GL10 gl,float radius,float nSlices,float nSegments) {
		gl.glPushMatrix();{
			gl.glScalef(radius, radius,radius);

			if(arrs==null)
				init(nSlices, nSegments);


			for(int i=0;i<arrs.length;i+=4) {
				gl.glNormal3f(arrs[i].x, arrs[i].y, arrs[i].z);
				Triangle.drawSquare(gl,arrs[i],arrs[i+1],arrs[i+2],arrs[i+3]);
			}



		}gl.glPopMatrix();
	}

}
