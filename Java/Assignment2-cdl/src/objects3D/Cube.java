package objects3D;

import org.lwjgl.opengl.GL11;
import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

public class Cube {

	
	public Cube() {

	}
	
	public void DrawCube() {
		//This is the points in a cube(8)
		Point4f vertices[] = { 	new Point4f(-1.0f, -1.0f, -1.0f,0.0f), 
				new Point4f(-1.0f, 1.0f, 1.0f,0.0f),
				new Point4f(1.0f, -1.0f, 1.0f,0.0f),
				new Point4f(1.0f, 1.0f, -1.0f,0.0f),
				new Point4f(-1.0f, -1.0f, 1.0f,0.0f), 
				new Point4f(1.0f, -1.0f, -1.0f,0.0f),
				new Point4f(-1.0f, 1.0f, -1.0f,0.0f), 
				new Point4f(1.0f, 1.0f, 1.0f,0.0f)};
		//This is the faces array that contain the index of points
		int[][] faces = { 	
			{ 0, 5, 2, 4 }, 
			{ 6, 3, 5, 0 },
			{ 6, 0, 4, 1 }, 
			{ 5, 3, 7, 2 },
			{ 4, 2, 7, 1},
			{ 3, 6, 1, 7}};
		
		
		for(int i=0;i<6;i++) {
		    Point4f o=vertices[faces[i][0]];
		    Vector4f vh=vertices[faces[i][1]].MinusPoint(vertices[faces[i][0]]);
		    Vector4f vv=vertices[faces[i][3]].MinusPoint(vertices[faces[i][0]]);
		    
		    GL11.glColor3f(1, 1, 1);

		    Vector4f normal = vh.cross(vv).Normal();
		    GL11.glNormal3f(normal.x, normal.y, normal.z);
		    for(int x=0;x<3;x++) {
			for(int y=0;y<3;y++) {
			    GL11.glPushMatrix();
			    if(i==0)
				GL11.glRotated(0, 0, 1, 0);
			    GL11.glBegin(GL11.GL_QUADS);
			    addVertex(o.PlusVector(vh.byScalar(1f/3*x)).PlusVector(vv.byScalar(1f/3*y)));
			    addVertex(o.PlusVector(vh.byScalar(1f/3*(x+1))).PlusVector(vv.byScalar(1f/3*y)));
			    addVertex(o.PlusVector(vh.byScalar(1f/3*(x+1))).PlusVector(vv.byScalar(1f/3*(y+1))));
			    addVertex(o.PlusVector(vh.byScalar(1f/3*x)).PlusVector(vv.byScalar(1f/3*(y+1))));
			    GL11.glEnd();
			    GL11.glPopMatrix();
			    
			}
		    }
		    
		    
		}
	}
	
	public void addVertex(Point4f p) {
	    GL11.glVertex3f(p.x,p.y,p.z);    
	}
	
	// Implement using notes and examine Tetrahedron to aid in the coding  look at lecture  7 , 7b and 8 
	public void DrawCubeTriangle() 
	{
		//I store the vertices into an array.
		int[][] verticesArr= {
				{1,1,1},
				{1,1,-1},
				{1,-1,1},
				{1,-1,-1},
				{-1,-1,1},
				{-1,-1,-1},
				{-1,1,-1},
				{-1,1,1}};

		Point4f vertices[] =new Point4f[verticesArr.length];
		//I store the vertices id into an array for the purpose of drawing each face
		int[][] faces = { 	
							{6,5,7},
							{4,5,7}, 
							{0,1,7}, 
							{1,6,7},
							{5,3,4},
							{2,3,4},
							{2,0,4}, 
							{7,0,4},
							{3,1,5},
							{6,1,5}, 
							{0,1,2},
							{3,1,2}};
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
			//two triangle have the same normal vector but different vertices.
			face++;
			GL11.glVertex3f(vertices[faces[face][0]].x, vertices[faces[face][0]].y, vertices[faces[face][0]].z);
			GL11.glVertex3f(vertices[faces[face][1]].x, vertices[faces[face][1]].y, vertices[faces[face][1]].z);
			GL11.glVertex3f(vertices[faces[face][2]].x, vertices[faces[face][2]].y, vertices[faces[face][2]].z);
			
		} // per face
		GL11.glEnd();
	}	
}