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

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import dl.gl1.ObjectsSample.MyGLRenderer;

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    NetworkThread networkThread;

    public MyGLSurfaceView(Context context) {
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new MyGLRenderer(context);


        getHolder().setFormat( PixelFormat.TRANSLUCENT);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

        networkThread=new NetworkThread();
        NetworkThread.myGLSurfaceView=this;
        networkThread.start();
   }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, we are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;


                mRenderer.addAngle(dx*TOUCH_SCALE_FACTOR,dy*TOUCH_SCALE_FACTOR);

//                mRenderer.setAngle(
//                        mRenderer.getAngle() +
//                        ((dx + dy) * TOUCH_SCALE_FACTOR));  // = 180.0f / 320
                requestRender();
                NetworkThread.needSend=true;
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }

    public void onResume(){
        super.onResume();
        if(networkThread!=null){
            NetworkThread.shutdown=true;
        }
        networkThread=new NetworkThread();
        NetworkThread.myGLSurfaceView=this;
            networkThread.start();
    }
    @Override
    public void onPause(){
        super.onPause();
        NetworkThread.shutdown=true;
    }

}
