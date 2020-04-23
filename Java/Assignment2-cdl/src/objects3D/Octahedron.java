package objects3D;


import org.lwjgl.opengl.GL11;
import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

public class Octahedron {

	public void Draw() 
 {
		//I store the vertices into an array.
		int[][] verticesArr= {
				{-1,0,0},
				{0,1,0},
				{1,0,0},
				{0,0,1},
				{0,0,-1},
				{0,-1,0},
		};

		Point4f vertices[] =new Point4f[verticesArr.length];
		//I store the vertices id into an array for the purpose of drawing each face
		int[][] faces = { 	
							{2,4,5},
							{2,3,5}, 
							{1,2,3}, 
							{1,2,4},
							{0,1,3},
							{0,1,4},
							{0,4,5}, 
							{0,3,5},
							};
		//create the points object array from the vertices values array
		for(int i=0;i<vertices.length;i++) {
			vertices[i]=new Point4f((float)(verticesArr[i][0]),(float)(verticesArr[i][1]),(float)(verticesArr[i][2]),0f);
		}
		//Draw the cube with GL_TRIANGLES mode, every face will generated from three vertices
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int face = 0; face < faces.length; face++) { // per face
			Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]); 
			Vector4f w = vertices[faces[face][2]].MinusPoint(vertices[faces[face][0]]);
			//compute the normal vector for each faces
			Vector4f normal = v.cross(w).Normal();
			//set the normal vector
		 	GL11.glNormal3f(normal.x, normal.y, normal.z);
		 	
			GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);
			GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);
			GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);
					} // per face
		GL11.glEnd();
	}
	
	
	public Octahedron()
	{
		
	}
	
	
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	public void DrawOctahedron()
	{
		Draw();
	}
}
 