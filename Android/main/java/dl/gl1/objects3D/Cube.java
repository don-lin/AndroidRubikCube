package dl.gl1.objects3D;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import dl.gl1.GraphicsObjects.Point4f;
import dl.gl1.GraphicsObjects.Vector4f;

public class Cube {

    public static final int[][]   verticesArr = {
            {1, 1, 1},
            {1, 1, -1},
            {1, -1, 1},
            {1, -1, -1},
            {-1, -1, 1},
            {-1, -1, -1},
            {-1, 1, -1},
            {-1, 1, 1}};
    public static final int[][] faces = {
            {6, 5, 7},
            {4, 5, 7},
            {0, 1, 7},
            {1, 6, 7},
            {5, 3, 4},
            {2, 3, 4},
            {2, 0, 4},
            {7, 0, 4},
            {3, 1, 5},
            {6, 1, 5},
            {0, 1, 2},
            {3, 1, 2}};

    //I store the vertices into an array.

    public static Point4f vertices[]=null;


    public Cube() {

        if (vertices!=null)
            return;

        vertices = new Point4f[verticesArr.length];
        //create the points object array from the vertices values array
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new Point4f((float) (verticesArr[i][0]), (float) (verticesArr[i][1]), (float) (verticesArr[i][2]), 0f);
        }
        //Draw the cube with GL_TRIANGLES mode, every face will generated from three vertices
    }

    //draw the cube
    public void DrawCube(GL10 gl) {

        for (int face = 0; face < faces.length; face++) { // per face
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][2]].MinusPoint(vertices[faces[face][0]]);
            //compute the normal vector for each faces
            Vector4f normal = v.cross(w).Normal();
            //set the normal vector
            gl.glNormal3f(normal.x, normal.y, normal.z);

            Triangle.drawTriangle(gl,vertices[faces[face][0]],vertices[faces[face][1]],vertices[faces[face][2]]);

            //two triangle have the same normal vector but different vertices.
            face++;
            Triangle.drawTriangle(gl,vertices[faces[face][0]],vertices[faces[face][1]],vertices[faces[face][2]]);

        } // per face
    }


    //draw the cube
    public void DrawCubeSix(GL10 gl) {

        float black[] = { 0.0f, 0.0f, 0.0f, 1.0f };
        float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

        float grey[] = { 0.5f, 0.5f, 0.5f, 1.0f };
        float spot[] = { 0.1f, 0.1f, 0.1f, 0.5f };

        // primary colours
        float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
        float green[] = { 0.0f, 1.0f, 0.0f, 1.0f };
        float blue[] = { 0.0f, 0.0f, 1.0f, 1.0f };

        // secondary colours
        float yellow[] = { 1.0f, 1.0f, 0.0f, 1.0f };
        float magenta[] = { 1.0f, 0.0f, 1.0f, 1.0f };
        float cyan[] = { 0.0f, 1.0f, 1.0f, 1.0f };

        // other colours
        float orange[] = { 1.0f, 0.5f, 0.0f, 1.0f, 1.0f };
        float brown[] = { 0.5f, 0.25f, 0.0f, 1.0f, 1.0f };
        float dkgreen[] = { 0.0f, 0.5f, 0.0f, 1.0f, 1.0f };
        float pink[] = { 1.0f, 0.6f, 0.6f, 1.0f, 1.0f };

        float colors[][]=new float[6][];
        colors[0]=white;
        colors[1]=green;
        colors[2]=blue;
        colors[3]=red;
        colors[4]=orange;
        colors[5]=yellow;

        for (int face = 0; face < faces.length; face++) { // per face
            gl.glColor4f(colors[face/2][0],colors[face/2][1],colors[face/2][2],colors[face/2][3]);
            Vector4f v = vertices[faces[face][1]].MinusPoint(vertices[faces[face][0]]);
            Vector4f w = vertices[faces[face][2]].MinusPoint(vertices[faces[face][0]]);
            //compute the normal vector for each faces
            Vector4f normal = v.cross(w).Normal();
            //set the normal vector
            gl.glNormal3f(normal.x, normal.y, normal.z);

            Triangle.drawTriangle(gl,vertices[faces[face][0]],vertices[faces[face][1]],vertices[faces[face][2]]);

            //two triangle have the same normal vector but different vertices.
            face++;
            Triangle.drawTriangle(gl,vertices[faces[face][0]],vertices[faces[face][1]],vertices[faces[face][2]]);

        } // per face
    }
}