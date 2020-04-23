//================================================================================================================================
//
// Copyright (c) 2015-2019 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package dl.gl1.AugmentReaility;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

import cn.easyar.Matrix44F;
import dl.gl1.RubikCubeObjects.CubeData;

// all methods of this class can only be called on one thread with the same OpenGLES context
public class BoxRenderer
{
    private EGLContext current_context = null;
    private int program_box;
    private int pos_coord_box;
    private int pos_color_box;
    private int pos_trans_box;
    private int pos_proj_box;
    private int vbo_coord_box;
    private int vbo_color_box;


    public static ArrayList<Float> rubikCubeList;

    public static float rotateAngle=0;
    public static float scaleValue=1;

    private String box_vert="uniform mat4 trans;\n"
            + "uniform mat4 proj;\n"
            + "attribute vec4 coord;\n"
            + "attribute vec4 color;\n"
            + "varying vec4 vcolor;\n"
            + "\n"
            + "void main(void)\n"
            + "{\n"
            + "    vcolor = color;\n"
            + "    gl_Position = proj*trans*coord;\n"
            + "}\n"
            + "\n"
    ;

    private String box_frag="#ifdef GL_ES\n"
            + "precision highp float;\n"
            + "#endif\n"
            + "varying vec4 vcolor;\n"
            + "\n"
            + "void main(void)\n"
            + "{\n"
            + "    gl_FragColor = vcolor;\n"
            + "}\n"
            + "\n"
    ;


    private byte[] byteArrayFromIntArray(int[] a)
    {
        byte[] l = new byte[a.length];
        for (int k = 0; k < a.length; k += 1) {
            l[k] = (byte)(a[k] & 0xFF);
        }
        return l;
    }

    private int generateOneBuffer()
    {
        int[] buffer = {0};
        GLES20.glGenBuffers(1, buffer, 0);
        return buffer[0];
    }
    private void deleteOneBuffer(int id)
    {
        int[] buffer = {id};
        GLES20.glDeleteBuffers(1, buffer, 0);
    }

    private static float[] getGLMatrix(Matrix44F m)
    {
        float[] d = m.data;
        return new float[]{d[0], d[4], d[8], d[12], d[1], d[5], d[9], d[13], d[2], d[6], d[10], d[14], d[3], d[7], d[11], d[15]};
    }

    public BoxRenderer()
    {
        current_context = ((EGL10)EGLContext.getEGL()).eglGetCurrentContext();
        program_box = GLES20.glCreateProgram();
        int vertShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertShader, box_vert);
        GLES20.glCompileShader(vertShader);
        int fragShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragShader, box_frag);
        GLES20.glCompileShader(fragShader);
        GLES20.glAttachShader(program_box, vertShader);
        GLES20.glAttachShader(program_box, fragShader);
        GLES20.glLinkProgram(program_box);
        GLES20.glUseProgram(program_box);
        GLES20.glDeleteShader(vertShader);
        GLES20.glDeleteShader(fragShader);
        pos_coord_box = GLES20.glGetAttribLocation(program_box, "coord");
        pos_color_box = GLES20.glGetAttribLocation(program_box, "color");
        pos_trans_box = GLES20.glGetUniformLocation(program_box, "trans");
        pos_proj_box = GLES20.glGetUniformLocation(program_box, "proj");

        vbo_coord_box = generateOneBuffer();
        vbo_color_box = generateOneBuffer();

        colorsArray=new int[16];

        RubikCube rubikCube=new RubikCube(3);
        CubeData cubeData=new CubeData(3);
        rubikCube.DrawCube(cubeData.getData(),0,0);
        this.rubikCubeList=rubikCube.pointsList;

    }
    public void dispose()
    {
        if (((EGL10)EGLContext.getEGL()).eglGetCurrentContext().equals(current_context)) { //destroy resources unless the context has lost
            GLES20.glDeleteProgram(program_box);

            deleteOneBuffer(vbo_coord_box);
            deleteOneBuffer(vbo_color_box);
        }
    }

    public void render(Matrix44F projectionMatrix, Matrix44F cameraview)
    {
        //开启深度测试，绑定gl程序
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glUseProgram(program_box);

        float[] cameraViewArryarray=getGLMatrix(cameraview);

        Matrix.rotateM(cameraViewArryarray,0,rotateAngle,0,0,1);
        Matrix.scaleM(cameraViewArryarray,0,scaleValue,scaleValue,scaleValue);

        //加载位置矩阵（gltranslate glrotate）
        GLES20.glUniformMatrix4fv(pos_trans_box, 1, false, cameraViewArryarray, 0);
        GLES20.glUniformMatrix4fv(pos_proj_box, 1, false, getGLMatrix(projectionMatrix), 0);

        float points[]={0,.6f,0,
                -0.5f,-0.3f,0,
                0.5f,-0.3f,0,
                0.5f,.6f,0
        };

        //drawSquare(points);

        initColorArray(255,255,255,255);
        drawArrayList(rubikCubeList);
    }


    public static float dx,dy,dz,angle;

    public boolean colorChange=true;

    public int[] colorsArray;

    public void initColorArray(float r,float g,float b,float a){
        initColorArray((int)(r*255),(int)(g*255),(int)(b*255),(int)(a*255));
    }

    public void initColorArray(int r,int g,int b,int a){
        for(int i=0;i<16;i+=4){
            colorsArray[i]=r;
            colorsArray[i+1]=g;
            colorsArray[i+2]=b;
            colorsArray[i+3]=a;
        }
        Log.d("colorChange",""+r+","+g+","+b+","+a+",");
        colorChange=true;
    }

    public void drawArrayList(ArrayList<Float> ls) {
        float[] points = new float[12];
        for (int i = 0; i < ls.size(); i += 12) {
            if (Float.isInfinite(ls.get(i))) {
                initColorArray(ls.get(i + 1), ls.get(i + 2), ls.get(i + 3), ls.get(i + 4));
                i -= 7;
                continue;
            }
            for(int j=0;j<12;j++) {
                points[j] = ls.get(i + j);
            }
            drawSquare(points);
        }
    }

    public void drawSquare(float[] points){
        draw(points,GLES20.GL_TRIANGLE_STRIP,4);
    }


    private FloatBuffer cube_vertices_buffer;

    public void draw(float[] points,int drawType,int drawNumber){
        //将坐标存入缓存
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_coord_box);
        //创建缓存
        if(cube_vertices_buffer==null)
            //cube_vertices_buffer = FloatBuffer.wrap(points);
            cube_vertices_buffer=FloatBuffer.allocate(12);
        else {
            //cube_vertices_buffer.position(0);
            cube_vertices_buffer.put(points);
            cube_vertices_buffer.position(0);
        }

        //将创建好的缓存读入显存
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, cube_vertices_buffer.limit() * 4, cube_vertices_buffer, GLES20.GL_DYNAMIC_DRAW);

        if(colorChange) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_coord_box);//画图时变换坐标
            GLES20.glEnableVertexAttribArray(pos_coord_box);
            GLES20.glVertexAttribPointer(pos_coord_box, 3, GLES20.GL_FLOAT, false, 0, 0);//绑定坐标

            //将颜色存入缓存
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_color_box);
            //创建缓存
            ByteBuffer colors_buffer = ByteBuffer.wrap(byteArrayFromIntArray(colorsArray));
            //将创建好的缓存读入显存
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, colors_buffer.limit(), colors_buffer, GLES20.GL_STATIC_DRAW);
            colorChange=false;

            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vbo_color_box);//加载颜色
            GLES20.glEnableVertexAttribArray(pos_color_box);
            GLES20.glVertexAttribPointer(pos_color_box, 4, GLES20.GL_UNSIGNED_BYTE, true, 0, 0);//传入颜色

        }

        GLES20.glDrawArrays(drawType,0,drawNumber);
    }
}
