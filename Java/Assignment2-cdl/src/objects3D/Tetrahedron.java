package objects3D;

import org.lwjgl.opengl.GL11;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

/*
 * This class has not been removed to help you understand how to convert the pseudo code and maths
 *  in your lecture notes into the objects for this assignment. 
 * 
 */
public class Tetrahedron {

	public Tetrahedron() {

	}

	// I have implemented this for you as we have not covered enough openGL to allow you to complete the assignment without a reference class. 
	// This should be your reference to aid you in completing all the other Objects3D classes. 
	
	public void DrawTetrahedron() {

	    //initialize the array that store all the vertices of the object.
		Point4f vertices[] = { 	new Point4f(-1.0f, -1.0f, -1.0f,0.0f), 
								new Point4f(-1.0f, 1.0f, 1.0f,0.0f),
								new Point4f(1.0f, -1.0f, 1.0f,0.0f), 
								new Point4f(1.0f, 1.0f, -1.0f,0.0f) };

		//initialize the array that store the id of vertices to make every face
		int[][] faces = { 			{ 0, 2, 1 }, 
							{ 1, 3, 0 },
							{ 2, 3, 1 }, 
							{ 3, 2, 0 } };
		 //Use the GL_TRIANGLES to convert the vertices to 3D model
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int face = 0; face < 4; face++) { // per face
			Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]); 
			Vector4f w = vertices[faces[face][2]].MinusPoint(vertices[faces[face][0]]);
			//compute the normal vector of each face
			Vector4f normal = v.cross(w).Normal();
			//set the normal vector
		 	GL11.glNormal3f(normal.x, normal.y, normal.z);
		 	//set the vertices of each face
			GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);
			GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);
			GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);
		} // per face
		GL11.glEnd();
	}

}
