package dl.gl1.RubikCubeObjects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import dl.gl1.GraphicsObjects.Point4f;

public class GraphicsBase {
    ByteBuffer vbb;
    FloatBuffer vBuf;
    GL10 gl;
    int n;
    float v[][]=new float[4][3];

    int[] aa={0,1,3,2};

    public GraphicsBase(GL10 gl) {
        n = 0;
        this.gl=gl;
        vbb = ByteBuffer.allocateDirect(v.length*v[0].length* 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();
    }

    public void addVertex(Point4f p){
        int nn=aa[n];
        v[nn][0]=p.x;
        v[nn][1]=p.y;
        v[nn][2]=p.z;
        n++;
        if (n >= 4) {
            flush();
        }
    }

    public void flush(){
        for(int i=0;i<4;i++)
            vBuf.put(v[i]);
        vBuf.position(0);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vBuf);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, n);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        n = 0;
    }
}
