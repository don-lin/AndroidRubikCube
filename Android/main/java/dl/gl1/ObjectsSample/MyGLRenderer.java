/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dl.gl1.ObjectsSample;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.nio.FloatBuffer;

import dl.gl1.objects3D.Cylinder;
import dl.gl1.objects3D.Icosahedron;
import dl.gl1.objects3D.Octahedron;
import dl.gl1.objects3D.Sphere;
import dl.gl1.objects3D.Spheree;
import dl.gl1.objects3D.Tetrahedron;
import dl.gl1.objects3D.TextureCube;
import dl.gl1.objects3D.Triangle;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Context context;
    private Triangle mTriangle;
    private Square mSquare;
    private TextureCube textureCube;
    private Cylinder cylinder;
    private Icosahedron icosahedron;
    private Octahedron octahedron;
    private Sphere sphere;
    private Spheree spheree;
    private Tetrahedron tetrahedron;
    public static float mAnglex;
    public static float mAngley;

    static float black[] = { 0.0f, 0.0f, 0.0f, 1.0f };
    static float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

    static float grey[] = { 0.5f, 0.5f, 0.5f, 1.0f };
    static float spot[] = { 0.1f, 0.1f, 0.1f, 0.5f };

    // primary colours
    static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
    static float green[] = { 0.0f, 1.0f, 0.0f, 1.0f };
    static float blue[] = { 0.0f, 0.0f, 1.0f, 1.0f };

    // secondary colours
    static float yellow[] = { 1.0f, 1.0f, 0.0f, 1.0f };
    static float magenta[] = { 1.0f, 0.0f, 1.0f, 1.0f };
    static float cyan[] = { 0.0f, 1.0f, 1.0f, 1.0f };

    // other colours
    static float orange[] = { 1.0f, 0.5f, 0.0f, 1.0f, 1.0f };
    static float brown[] = { 0.5f, 0.25f, 0.0f, 1.0f, 1.0f };
    static float dkgreen[] = { 0.0f, 0.5f, 0.0f, 1.0f, 1.0f };
    static float pink[] = { 1.0f, 0.6f, 0.6f, 1.0f, 1.0f };

    public MyGLRenderer(Context context){
        this.context=context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background frame color
//        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mTriangle = new Triangle();
        mSquare = new Square();
        textureCube =new TextureCube();
        textureCube.loadTexture(gl,context);
        cylinder=new Cylinder();
        icosahedron=new Icosahedron();
        octahedron=new Octahedron();
        sphere=new Sphere();
        spheree=new Spheree();
        tetrahedron=new Tetrahedron();
        Triangle.init();

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glClearColor(1,0.6f,0.6f,1);

        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0, 1200, 0, 800, 1000, -1000);
        gl.glMatrixMode(gl.GL_MODELVIEW);
        FloatBuffer lightPos=FloatBuffer.allocate(4);
        lightPos.put(1).put(1).put(1).put(0).flip();
        gl.glLightfv(gl.GL_LIGHT0,gl.GL_POSITION,lightPos);
        gl.glEnable(gl.GL_LIGHT0);


        gl.glEnable(gl.GL_LIGHTING); // switch lighting on
        gl.glEnable(gl.GL_DEPTH_TEST); // make sure depth buffer is switched
        // on
        gl.glEnable(gl.GL_NORMALIZE); // normalize normal vectors for safety
        gl.glEnable(gl.GL_COLOR_MATERIAL);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        // Draw background color
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        // Set GL_MODELVIEW transformation mode
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();   // reset the matrix to its default state

        // When using GL_MODELVIEW, you must set the view point
        GLU.gluLookAt(gl, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Draw square
        //mSquare.draw(gl);

        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);


        drawScene(gl);
        gl.glTranslatef(0,1,0);
        drawScene(gl);



    }

    public void drawScene(GL10 gl){
        gl.glClearColor(1,0.6f,0.6f,1);

        gl.glPushMatrix();{
            gl.glTranslatef(0,-.5f,.5f);
            gl.glRotatef(mAnglex, 0.0f, 1.0f, 0.0f);
            gl.glRotatef(mAngley, 1.0f, 0.0f, 0.0f);
            gl.glScalef(.1f,.1f,.1f);

            gl.glColor4f(magenta[0],magenta[1],magenta[2],magenta[3]);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 10);
            cylinder.DrawCylinder(gl,1,3,20);}
        gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(0f,0,.5f);
            gl.glRotatef(mAnglex, 0.0f, 1.0f, 0.0f);
            gl.glRotatef(mAngley, 1.0f, 0.0f, 0.0f);
            gl.glScalef(.1f,.1f,.1f);

            gl.glColor4f(white[0],white[1],white[2],white[3]);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 10);
            textureCube.draw(gl);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(.5f,-.5f,.5f);
            gl.glRotatef(mAnglex, 0.0f, 1.0f, 0.0f);
            gl.glRotatef(mAngley, 1.0f, 0.0f, 0.0f);
            gl.glScalef(.1f,.1f,.1f);

            gl.glColor4f(green[0],green[1],green[2],green[3]);
            gl.glMaterialfv(GL10.GL_FRONT_AND_BACK,GL10.GL_AMBIENT_AND_DIFFUSE,green,0);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 10);
            icosahedron.DrawIcosahedron(gl);}
        gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(-.5f,0f,.5f);
            gl.glRotatef(mAnglex, 0.0f, 1.0f, 0.0f);
            gl.glRotatef(mAngley, 1.0f, 0.0f, 0.0f);
            gl.glScalef(.1f,.1f,.1f);

            gl.glColor4f(yellow[0],yellow[1],yellow[2],yellow[3]);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 10);
            octahedron.DrawOctahedron(gl);}
        gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(-.5f,-.5f,.5f);
            gl.glRotatef(mAnglex, 0.0f, 1.0f, 0.0f);
            gl.glRotatef(mAngley, 1.0f, 0.0f, 0.0f);
            gl.glScalef(.1f,.1f,.1f);

            gl.glColor4f(cyan[0],cyan[1],cyan[2],cyan[3]);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 10);
            spheree.draw(gl);
            //sphere.DrawSphere(gl,1,20,20);
        }
        gl.glPopMatrix();

        gl.glPushMatrix();{
            gl.glTranslatef(.5f,0f,.5f);
            gl.glRotatef(mAnglex, 0.0f, 1.0f, 0.0f);
            gl.glRotatef(mAngley, 1.0f, 0.0f, 0.0f);
            gl.glScalef(.1f,.1f,.1f);

            gl.glColor4f(red[0],red[1],red[2],red[3]);
            gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 10);
            tetrahedron.DrawTetrahedron(gl);}
        gl.glPopMatrix();

        gl.glPopMatrix();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Adjust the viewport based on geometry changes
        // such as screen rotations
        gl.glViewport(0, 0, width, height);

        // make adjustments for screen ratio
        float ratio = (float) width / height;
        gl.glMatrixMode(GL10.GL_PROJECTION);        // set matrix to projection mode
        gl.glLoadIdentity();                        // reset the matrix to its default state
        gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);  // apply the projection matrix
    }


    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
    public void setAngle(float anglex,float angley) {
        this.mAnglex=anglex;
        this.mAngley=angley;
    }

    public void addAngle(float dx,float dy){
        this.mAnglex=this.mAnglex+dx;
        this.mAngley=this.mAngley+dy;
    }
}