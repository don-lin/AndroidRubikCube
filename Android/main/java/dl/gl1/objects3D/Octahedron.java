package dl.gl1.objects3D;

import javax.microedition.khronos.opengles.GL10;

import dl.gl1.GraphicsObjects.Point4f;
import dl.gl1.GraphicsObjects.Vector4f;

public class Octahedron {

	//I store the vertices into an array.
	public static final int[][] verticesArr= {
			{-1,0,0},
			{0,1,0},
			{1,0,0},
			{0,0,1},
			{0,0,-1},
			{0,-1,0},
	};

	//I store the vertices id into an array for the purpose of drawing each face
	public static final int[][] faces = {
			{2,4,5},
			{2,3,5},
			{1,2,3},
			{1,2,4},
			{0,1,3},
			{0,1,4},
			{0,4,5},
			{0,3,5},
	};
	public static Point4f[] vertices=null;
	public Octahedron()
	{
		if(vertices!=null)
			return;
		vertices  =new Point4f[verticesArr.length];
		//create the points object array from the vertices values array
		for(int i=0;i<vertices.length;i++) {
			vertices[i]=new Point4f((float)(verticesArr[i][0]),(float)(verticesArr[i][1]),(float)(verticesArr[i][2]),0f);
		}
	}

	public void DrawOctahedron(GL10 gl)
 {

		//Draw the cube with GL_TRIANGLES mode, every face will generated from three vertices
		for (int face = 0; face < faces.length; face++) { // per face
			Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]); 
			Vector4f w = vertices[faces[face][2]].MinusPoint(vertices[faces[face][0]]);
			//compute the normal vector for each faces
			Vector4f normal = v.cross(w).Normal();
			//set the normal vector
		 	gl.glNormal3f(normal.x, normal.y, normal.z);

		 	Triangle.drawTriangle(gl,vertices[faces[face][0]],vertices[faces[face][1]],vertices[faces[face][2]]);

					} // per face
	}
	
	

}
 