
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
//import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import objects3D.Cube;
import objects3D.Icosahedron;
import objects3D.Tetrahedron;
import udp.EchoClient;
import objects3D.Octahedron;
import objects3D.Sphere;
import objects3D.Cylinder;

//Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment. 
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment. 
// 

// Again !!!! Do not touch this class, I will be making a version of it for your 3rd Assignment 
public class MainWindow {

	/** position of quad */
	float x = 400, y = 300;
	/** angle of quad rotation */
//	float rotation = 0;
	public static float rotationx=0;
	public static float rotationy=0;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	// basic colours
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

	// static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};

	public FloatBuffer ConvertForGL(float[] ToConvert)

	{
		FloatBuffer Any4 = BufferUtils.createFloatBuffer(4);
		Any4.put(ToConvert[0]).put(ToConvert[1]).put(ToConvert[2]).put(ToConvert[3]).flip();
		return Any4;
	}

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(1200, 800));
			Display.create();
			Display.setSwapInterval(1);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			update(delta);
			renderGL();
			Display.update();
			Display.sync(600); // cap fps to 60fps
		}
		Display.destroy();
		System.exit(1);
	}

	public void update(int delta) {
		// rotate quad
		// org.lwjgl.input.Mouse
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
		    rotationx+=2.35f;
		    Receiver.send=true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
		    rotationx-=2.35f;
		    Receiver.send=true;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
		    rotationy+=2.35f;
		    Receiver.send=true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
		    rotationy-=2.35f;
		    Receiver.send=true;
		}


		// keep quad on the screen
		if (x < 0)
			x = 0;
		if (x > 1200)
			x = 1200;
		if (y < 0)
			y = 0;
		if (y > 800)
			y = 800;

		updateFPS(); // update FPS Counter
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1200, 0, 800, 1000, -1000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
		lightPos.put(10000f).put(1000f).put(1000).put(0).flip();

		FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
		lightPos2.put(0f).put(1000f).put(0).put(-1000f).flip();

		FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
		lightPos3.put(-10000f).put(1000f).put(1000).put(0).flip();

		FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
		lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPos); // specify the
																	// position
																	// of the
																	// light
		// GL11.glEnable(GL11.GL_LIGHT0); // switch light #0 on // I've setup specific materials so in real light it will look abit strange 

		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos); // specify the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, ConvertForGL(spot));

		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
																	// the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, ConvertForGL(grey));

		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPos4); // specify
																	// the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT3); // switch light #0 on
		 GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, ConvertForGL(grey));

		GL11.glEnable(GL11.GL_LIGHTING); // switch lighting on
		GL11.glEnable(GL11.GL_DEPTH_TEST); // make sure depth buffer is switched
											// on
		GL11.glEnable(GL11.GL_NORMALIZE); // normalize normal vectors for safety
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);

	}

	public void changeOrth() {

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1000, -1000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glShadeModel(GL11.GL_SMOOTH); // use flat lighting

	}

	public void renderGL() {
		// First drawing a Tetrahedron in the corner 
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); 
		GL11.glColor3f(0.5f, 0.5f, 1.0f); 
		
		GL11.glPushMatrix();
		GL11.glTranslatef(200, 650, 0); 
		GL11.glRotatef(rotationy, 0, 1, 0);
		GL11.glRotatef(rotationx, 1, 0, 0);

		 //GL11.glTranslatef(-x, -y, 0);
		Tetrahedron My4die = new Tetrahedron();
		GL11.glScalef(90f, 90f, 90f);
		GL11.glColor3f(red[0], red[1], red[2]);
		GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, ConvertForGL(red)); 
		My4die.DrawTetrahedron();

		GL11.glPopMatrix();

		// cube draw
		GL11.glPushMatrix();
		GL11.glTranslatef(600, 600, 0); 
		GL11.glRotatef(rotationy, 0, 1, 0);
		GL11.glRotatef(rotationx, 1, 0, 0);
		Cube MyCube = new Cube();
		GL11.glScalef(90f, 90f, 90f);
		GL11.glColor3f(blue[0], blue[1], blue[2]);
		GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, ConvertForGL(blue));

		MyCube.DrawCube();

		GL11.glPopMatrix();

		// Octahedron Draw
		GL11.glPushMatrix();
		GL11.glTranslatef(1000, 600, 0); 
		GL11.glRotatef(rotationy, 0, 1, 0);
		GL11.glRotatef(rotationx, 1, 0, 0);
		Octahedron MyOctahedron = new Octahedron();
		GL11.glScalef(90f, 90f, 90f);
		GL11.glColor3f(yellow[0], yellow[1], yellow[2]);
		GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, ConvertForGL(yellow));

		MyOctahedron.DrawOctahedron();

		GL11.glPopMatrix();

		// Icosahedron Draw
		GL11.glPushMatrix();
		GL11.glTranslatef(200, 200, 0); 
		GL11.glRotatef(rotationy, 0, 1, 0);
		GL11.glRotatef(rotationx, 1, 0, 0);
		Icosahedron MyIcosahedron = new Icosahedron();
		GL11.glScalef(90f, 90f, 90f);
		GL11.glColor3f(green[0], green[1], green[2]);
		GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, ConvertForGL(green));

		MyIcosahedron.DrawIcosahedron();

		GL11.glPopMatrix();

		// Cylinder Draw
		GL11.glPushMatrix();
		GL11.glTranslatef(600, 250, 0); 
		GL11.glRotatef(rotationy, 0, 1, 0);
		GL11.glRotatef(rotationx, 1, 0, 0);
		 //GL11.glTranslatef(-x, -y, 0);
		Cylinder MyCylinder = new Cylinder();
		GL11.glScalef(90f, 90f, 90f);
		GL11.glColor3f(magenta[0], magenta[1], magenta[2]);
		GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_AMBIENT_AND_DIFFUSE, ConvertForGL(magenta));

		MyCylinder.DrawCylinder(0.5f, 1.5f, 16);

		GL11.glPopMatrix();

		// Sphere Draw
		GL11.glPushMatrix();
		GL11.glTranslatef(1000, 250, 0); 
		GL11.glRotatef(rotationy, 0, 1, 0);
		GL11.glRotatef(rotationx, 1, 0, 0);
		Sphere MySphere = new Sphere();
		GL11.glScalef(90f, 90f, 90f);
		GL11.glColor3f(cyan[0], cyan[1], cyan[2]);
		GL11.glMaterial(GL11.GL_FRONT_AND_BACK, GL11.GL_SPECULAR, ConvertForGL(cyan));
		
		MySphere.DrawSphere(1f, 32, 32);

		GL11.glPopMatrix();

	}
	

	public static void main(String[] argv) {
		MainWindow hello = new MainWindow();
		Receiver receiver=new Receiver();
		receiver.start();
		hello.start();
	}
}
