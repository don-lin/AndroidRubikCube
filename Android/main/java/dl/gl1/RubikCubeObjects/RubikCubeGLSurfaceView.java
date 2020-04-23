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
package dl.gl1.RubikCubeObjects;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import dl.gl1.ObjectsSample.MyGLRenderer;

/**
 * draw the rubik cube
 */
public class RubikCubeGLSurfaceView extends GLSurfaceView {

    private final RubikCubeGLRenderer mRenderer;

    public RubikCubeGLSurfaceView(Context context,int layer) {
        super(context);
        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new RubikCubeGLRenderer();
        mRenderer.layer=layer;
        setRenderer(mRenderer);

        GraphicsWithTexture.context=getContext();

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    private float startX;
    private float startAngle;
    private float startY;


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX=x;
                startY=y;
                mPreviousX=x;
                mPreviousY=y;
                startAngle=mRenderer.layerRotate;

                if(y>getHeight()/5*4){
                    if(x>getWidth()/2){
                        int l=mRenderer.rotateMethod/10;
                        l++;
                        if(l>=mRenderer.rubikCube.getLayers()-1)
                            l=0;
                        int f=mRenderer.rotateMethod%10;
                        mRenderer.rotateMethod=l*10+f;
                        RubikCubeGLRenderer.rightButton=true;
                    }else{
                        int l=mRenderer.rotateMethod/10;
                        int f=mRenderer.rotateMethod%10;
                        f++;
                        if(f>=6)
                            f=0;
                        mRenderer.rotateMethod=l*10+f;
                        RubikCubeGLRenderer.leftButton=true;
                    }
                }

                requestRender();
                return true;

            case MotionEvent.ACTION_MOVE:
                if(startY<getHeight()/3) {
                    RubikCubeGLRenderer.press=true;
                    RubikCubeGLRenderer.pressx=1-x/getWidth()*2;
                    RubikCubeGLRenderer.pressy=1-y/getHeight()*2;
                    float dx = x - mPreviousX;
                    float dy = y - mPreviousY;
                    mRenderer.addAngle(dx * TOUCH_SCALE_FACTOR, dy * TOUCH_SCALE_FACTOR);
                }
                else if(startY<getHeight()/5*4){
                    mRenderer.layerRotate=startAngle+(x-startX)*TOUCH_SCALE_FACTOR;
                }
//                mRenderer.setAngle(
//                        mRenderer.getAngle() +
//                        ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();
                break;
            case MotionEvent.ACTION_UP:
                RubikCubeGLRenderer.rightButton=false;
                RubikCubeGLRenderer.leftButton=false;
                RubikCubeGLRenderer.press=false;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

}
