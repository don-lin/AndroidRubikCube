package dl.gl1.RubikCubeObjects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import dl.gl1.GraphicsObjects.Point4f;
import dl.gl1.R;

public class GraphicsWithTexture {
    private FloatBuffer texBuffer;    // Buffer for texture-coords-array (NEW)
    FloatBuffer vBuf;
    GL10 gl;
    int n;
    float v[][]=new float[4][3];
    int[] aa={0,1,3,2};

    public static int currentTexture=0;
    public static Context context;

    float[] texCoords = { // Texture coords for the above face (NEW)
            0.0f, 1.0f,  // A. left-bottom (NEW)
            1.0f, 1.0f,  // B. right-bottom (NEW)
            0.0f, 0.0f,  // C. left-top (NEW)
            1.0f, 0.0f   // D. right-top (NEW)
    };
    int[] textureIDs = new int[6];   // Array for 1 texture-ID (NEW)

    // Constructor - Set up the buffers
    public GraphicsWithTexture(GL10 gl) {

        n = 0;
        this.gl=gl;
        ByteBuffer vbb;
        vbb = ByteBuffer.allocateDirect(v.length*v[0].length* 4);
        vbb.order(ByteOrder.nativeOrder());
        vBuf = vbb.asFloatBuffer();
        // Setup texture-coords-array buffer, in float. An float has 4 bytes (NEW)
        ByteBuffer tbb = ByteBuffer.allocateDirect(texCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        texBuffer = tbb.asFloatBuffer();
        texBuffer.put(texCoords);
        texBuffer.position(0);

        loadTexture(gl);
    }

    // Load an image into GL texture
    public void loadTexture(GL10 gl) {
        gl.glGenTextures(6, textureIDs, 0); // Generate texture-ID array
        loadTexture(0,R.drawable.dice);
        loadTexture(1,R.drawable.dices);
    }

    private void loadTexture(int num,int id){

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIDs[num]);   // Bind to texture ID
        // Set up texture filters
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        @SuppressLint("ResourceType") InputStream istream = context.getResources().openRawResource(id);
        Bitmap bitmap;
        try {
            // Read and decode input as bitmap
            bitmap = BitmapFactory.decodeStream(istream);
        } finally {
            try {
                istream.close();
            } catch (IOException e) {
            }
        }
        // Build Texture from loaded bitmap for the currently-bind texture ID
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();

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
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);  // Enable texture-coords-array (NEW)
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer); // Define texture-coords buffer (NEW)
        gl.glBindTexture(GL10.GL_TEXTURE_2D,textureIDs[currentTexture]);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);  // Disable texture-coords-array (NEW)
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        n = 0;
    }
}
