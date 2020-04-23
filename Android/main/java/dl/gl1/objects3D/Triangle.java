package dl.gl1.objects3D;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import dl.gl1.GraphicsObjects.Point4f;

public class Triangle {
    private static FloatBuffer vertexTriangleBuffer;

    // number of coordinates per vertex in this array
    public static float triangleCoords[] ;
    public static float squareCoords[];


    public static void init(){
        triangleCoords=new float[9];
        squareCoords=new float[12];

        ByteBuffer bb = ByteBuffer.allocateDirect(
                triangleCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());
        vertexTriangleBuffer = bb.asFloatBuffer();

    }

    public static void drawTriangle(GL10 gl,float x0,float y0,float z0,float x1,float y1,float z1,float x2,float y2,float z2){
        triangleCoords[0]=x0;
        triangleCoords[1]=y0;
        triangleCoords[2]=z0;
        triangleCoords[3]=x1;
        triangleCoords[4]=y1;
        triangleCoords[5]=z1;
        triangleCoords[6]=x2;
        triangleCoords[7]=y2;
        triangleCoords[8]=z2;
        drawTriangle(gl);
    }

    public static void drawSquare(GL10 gl, Point4f a, Point4f b, Point4f c,Point4f d) {
        for(int i=0;i<3;i++){
            squareCoords[i]=a.getPostion(i);
        }
        for(int i=0;i<3;i++){
            squareCoords[i+3]=b.getPostion(i);
        }
        for(int i=0;i<3;i++){
            squareCoords[i+6]=c.getPostion(i);
        }
        for(int i=0;i<3;i++){
            squareCoords[i+9]=d.getPostion(i);
        }
        drawSquare(gl);
    }

    public static void drawTriangle(GL10 gl, Point4f a, Point4f b, Point4f c) {
        for(int i=0;i<3;i++){
            triangleCoords[i]=a.getPostion(i);
        }
        for(int i=0;i<3;i++){
            triangleCoords[i+3]=b.getPostion(i);
        }
        for(int i=0;i<3;i++){
            triangleCoords[i+6]=c.getPostion(i);
        }
        drawTriangle(gl);
    }

    private static void drawSquare(GL10 gl){
        for(int i=0;i<9;i++){
            triangleCoords[i]=squareCoords[i];
        }
        drawTriangle(gl);
        for(int i=0;i<6;i++){
            triangleCoords[i+3]=squareCoords[i+6];
        }
        drawTriangle(gl);

    }

    public static void vertex(GL10 gl,float x,float y,float z){
        triangleCoords[0]=x;
        triangleCoords[1]=y;
        triangleCoords[2]=z;

        vertexTriangleBuffer.put(triangleCoords);
        vertexTriangleBuffer.position(0);

        // Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);


        // draw the shape
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexTriangleBuffer);


        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 1);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

    }

    public static void drawTriangleForCylinder(GL10 gl,float x0,float y0,float z0,float x1,float y1,float z1,float x2,float y2,float z2){
        triangleCoords[0]=x0;
        triangleCoords[1]=y0;
        triangleCoords[2]=z0;
        triangleCoords[3]=x1;
        triangleCoords[4]=y1;
        triangleCoords[5]=z1;
        triangleCoords[6]=x2;
        triangleCoords[7]=y2;
        triangleCoords[8]=z2;

        vertexTriangleBuffer.put(triangleCoords);
        vertexTriangleBuffer.position(0);

        // Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);




        // draw the shape
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexTriangleBuffer);
        gl.glNormalPointer( GL10.GL_FLOAT, 0, vertexTriangleBuffer);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, triangleCoords.length / 3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }

    private static void drawTriangle(GL10 gl){
        vertexTriangleBuffer.put(triangleCoords);
        vertexTriangleBuffer.position(0);

        // Since this shape uses vertex arrays, enable them
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);


        // draw the shape
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexTriangleBuffer);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, triangleCoords.length / 3);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
