package objects3D;

import org.lwjgl.opengl.GL11;

public class Sphere {

	public Sphere() {

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
	
	/*
	 * @param radius the radius of the sphere
	 * @param nSlices how many slices to split along the x-y plane
	 * @param nSegments how may segments to split for each slice. 
	 */
	public void DrawSphere(float radius, float nSlices, float nSegments) {
		float pi = (float) Math.PI;

		//compute the increase theta("¦È") value
		float incphi = pi / nSlices;
		//compute the increase phi("¦Õ") value
		float inctheta = 2 * pi / nSegments;
		//these three arrays will record twelve float values for the purpose of draw each quad in the sphere
		float[] x = new float[4];
		float[] y = new float[4];
		float[] z = new float[4];

		float angle_z = 0;
		float angle_xy = 0;
		int i = 0, j = 0;
		//To draw a sphere, I use the GL_QUADS mode, I will give four vertices for each face in the sphere.
		GL11.glBegin(GL11.GL_QUADS);
		for (i = 0; i < nSlices; i++) {
			angle_z = i * incphi;

			for (j = 0; j < nSegments; j++) {
				angle_xy = j * inctheta;
				x[0] = radius * sin(angle_z) * cos(angle_xy);
				y[0] = radius * sin(angle_z) * sin(angle_xy);
				z[0] = radius * cos(angle_z);

				x[1] = radius * sin(angle_z + incphi) * cos(angle_xy);
				y[1] = radius * sin(angle_z + incphi) * sin(angle_xy);
				z[1] = radius * cos(angle_z + incphi);

				x[2] = radius * sin(angle_z + incphi) * cos(angle_xy + inctheta);
				y[2] = radius * sin(angle_z + incphi) * sin(angle_xy + inctheta);
				z[2] = radius * cos(angle_z + incphi);

				x[3] = radius * sin(angle_z) * cos(angle_xy + inctheta);
				y[3] = radius * sin(angle_z) * sin(angle_xy + inctheta);
				z[3] = radius * cos(angle_z);

				for (int k = 0; k < 4; k++) {	
				    	//the normal vector is the same as the vector from origin point to the vertex
					GL11.glNormal3f(x[k], y[k], z[k]);
					GL11.glVertex3f(x[k], y[k], z[k]);
				}
			}
		}
		GL11.glEnd();
	}

	/*
	 * @param radius the radius of the sphere
	 * @param nSlices how many slices to split along the x-y plane
	 * @param nSegments how may segments to split for each slice. 
	 */
	public void DrawSphereSlower(float radius, float nSlices, float nSegments) {
		float pi = (float) Math.PI;
		
		//compute the increase theta("¦È") value
		float inctheta = (2.0f * pi) / nSlices;
		//compute the increase phi("¦Õ") value
		float incphi = pi / nSegments;
		float r = radius;
		GL11.glBegin(GL11.GL_POINTS);

		for (float theta = -pi; theta < pi; theta += inctheta) {
			for (float phi = -(pi / 2.0f); phi < (pi / 2.0); phi += incphi) {

				float rPhi = r * cos(phi);
				float z = r * sin(phi);
				float x = rPhi * cos(theta);
				float y = rPhi * sin(theta);

				GL11.glNormal3f(x, y, z);
				GL11.glVertex3f(x, y, z);
			}
		}
		GL11.glEnd();
	}

}
