package dl.gl1.AugmentReaility;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dl.gl1.GraphicsObjects.Point4f;
import dl.gl1.GraphicsObjects.Vector4f;

public class RubikCube {

    private int Layers = 3;

    /**
     * @param layers layers of the rubik's cube
     */
    public RubikCube(int layers) {
        init(layers);
    }

    /**
     * @return the layers of the cube
     */

    public int getLayers() {
        return Layers;
    }

    private void init(int layers) {
        this.Layers = layers;
        for (int i = 0; i < rotateOtherFacex.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (rotateOtherFacex[i][j] > 0) {
                    rotateOtherFacex[i][j] = layers - 1;
                }
                if (rotateOtherFacey[i][j] > 0) {
                    rotateOtherFacey[i][j] = layers - 1;
                }
            }
        }
    }

    private boolean decodeRules(int currentFace, int currentX, int currentY, int face, int[] otherFace, int[] otherFacex, int[] otherFacey) {
        if (currentFace == face)
            return true;
        for (int i = 0; i < 4; i++) {
            if (currentFace == otherFace[i]) {
                if (otherFacex[i] == -1) {
                    if (otherFacey[i] == currentY)
                        return true;
                }
                if (otherFacey[i] == -1) {
                    if (otherFacex[i] == currentX)
                        return true;
                }
            }
        }
        return false;
    }

    private int offsetLayer(int value, int layer) {
        if (value == -1)
            return -1;
        if (value == 0) {
            return value + layer;
        } else {
            return value - layer;
        }
    }

    private boolean decodeRules(int currentFace, int currentX, int currentY, int face, int layer, int[] otherFace, int[] otherFacex, int[] otherFacey) {
        for (int i = 0; i < 4; i++) {
            if (currentFace == otherFace[i]) {
                if (otherFacex[i] == -1) {
                    if (offsetLayer(otherFacey[i], layer) == currentY)
                        return true;
                }
                if (otherFacey[i] == -1) {
                    if (offsetLayer(otherFacex[i], layer) == currentX)
                        return true;
                }
            }
        }
        return false;
    }


    //Six points correspond six faces
    private static final Point4f points[] = {
            new Point4f(-1.0f, 1.0f, 1.0f),
            new Point4f(-1.0f, 1.0f, -1.0f),
            new Point4f(1.0f, 1.0f, -1.0f),
            new Point4f(1.0f, 1.0f, 1.0f),
            new Point4f(-1.0f, 1.0f, 1.0f),
            new Point4f(1.0f, -1.0f, -1.0f)};

    //Each face is one point and two vectors
    private static Vector4f vectorsx[] = {
            new Vector4f(0, 0, -2),
            new Vector4f(2, 0, 0),
            new Vector4f(0, 0, 2),
            new Vector4f(-2, 0, 0),
            new Vector4f(0, 0, -2),
            new Vector4f(0, 0, 2)
    };
    private static Vector4f vectorsy[] = {
            new Vector4f(2, 0, 0),
            new Vector4f(0, -2, 0),
            new Vector4f(0, -2, 0),
            new Vector4f(0, -2, 0),
            new Vector4f(0, -2, 0),
            new Vector4f(-2, 0, 0)
    };

    private static final int[] rotateAxis = {
            2, 1, 4, 1, 4, 2
    };

    private static final int[] rotateMainFace = {
            0, 1, 2, 3, 4, 5
    };
    private static final int[][] rotateOtherFace = {
            {1, 2, 3, 4},
            {0, 4, 5, 2},
            {1, 0, 3, 5},
            {0, 4, 5, 2},
            {0, 1, 5, 3},
            {1, 2, 3, 4}
    };

    private static int[][] rotateOtherFacex = {
            {-1, -1, -1, -1},
            {2, 2, 0, 0},
            {2, -1, 0, -1},
            {0, 0, 2, 2},
            {-1, 0, -1, 2},
            {-1, -1, -1, -1}
    };

    private static final int[][] rotateOtherFacey = {
            {0, 0, 0, 0},
            {-1, -1, -1, -1},
            {-1, 2, -1, 0},
            {-1, -1, -1, -1},
            {0, -1, 2, -1},
            {2, 2, 2, 2}
    };

    private boolean judge(int face, int x, int y, int m) {
        if (m < 10)
            return decodeRules(face, x, y, rotateMainFace[m], rotateOtherFace[m], rotateOtherFacex[m], rotateOtherFacey[m]);
        return
                decodeRules(face, x, y, rotateMainFace[m % 10], m / 10, rotateOtherFace[m % 10], rotateOtherFacex[m % 10], rotateOtherFacey[m % 10]);
    }

    /**
     *
     * @param face which face to rotate, between 0 and 5
     * @param x value of the x axis
     * @param y value of the y axis
     * @param m rotate method
     * @param angle rotate angle
     * @return true means rotate finish, false means no rotate
     */
    public boolean doRotate(int face, int x, int y, int m, float angle) {
        if (judge(face, x, y, m)) {
            m = m % 10;
            //GL11.glRotatef(angle, rotateAxis[m] & 4, rotateAxis[m] & 2, rotateAxis[m] & 1);
            return true;
        }
        return false;
    }

    private void color4f(float r,float g,float b,float a){
        pointsList.add(Float.NEGATIVE_INFINITY);
        pointsList.add(r);
        pointsList.add(g);
        pointsList.add(b);
        pointsList.add(a);
    }

    private void decodeColor(int color) {
        switch (color) {
            case 0:
                color4f(1, 0, 0, 0.8f);
                break;
            case 1:
                color4f(0, 1, 0, 0.8f);
                break;

            case 2:
                color4f(0, 0, 1, 0.8f);
                break;

            case 3:
                color4f(0, 1, 1, 0.8f);
                break;

            case 4:
                color4f(1, 1, 0, 0.8f);
                break;

            case 5:
                color4f(1, 1, 1, 0.8f);
                break;
            default:
                break;
        }
    }


    /**
     * @param status       the status of rubik's cube, it should be an array 6*layer*layer
     * @param rotateMethod a*10+b. a is the layer that need to rotate, b is the face between 0 and 5
     * @param angle        a float angle number
     */
    public void DrawCube( int[][] status, int rotateMethod, float angle) {
        pointsList=new ArrayList<Float>();

        for (int i = 0; i < 6; i++) {
            Point4f o = points[i];
            Vector4f vh = vectorsx[i];
            Vector4f vv = vectorsy[i];

            color4f(1, 1, 1, 1);

            Vector4f normal = vh.cross(vv).Normal();
            //glNormal3f(normal.x, normal.y, normal.z);
            for (int x = 0; x < Layers; x++) {
                for (int y = 0; y < Layers; y++) {
//                    GL11.glPushMatrix();
                    doRotate(i, x, y, rotateMethod, angle);
                    decodeColor(status[i][x * Layers + y]);
                    addVertex(o.PlusVector(vh.byScalar(1f / Layers * x)).PlusVector(vv.byScalar(1f / Layers * y)));
                    addVertex(o.PlusVector(vh.byScalar(1f / Layers * (x + 1))).PlusVector(vv.byScalar(1f / Layers * y)));
                    addVertex(o.PlusVector(vh.byScalar(1f / Layers * (x + 1))).PlusVector(vv.byScalar(1f / Layers * (y + 1))));
                    addVertex(o.PlusVector(vh.byScalar(1f / Layers * x)).PlusVector(vv.byScalar(1f / Layers * (y + 1))));
//                    GL11.glPopMatrix();

                }
            }
        }
    }

    public ArrayList<Float> pointsList;

    public void addVertex(Point4f p) {
        pointsList.add(p.x);
        pointsList.add(p.y);
        pointsList.add(p.z);
    }

}