//================================================================================================================================
//
// Copyright (c) 2015-2019 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
// EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
// and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package dl.gl1.AugmentReaility.SurfaceTracking;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.HashMap;

import cn.easyar.Engine;
import dl.gl1.R;


public class SrufaceTrackingActivity extends Activity
{
    private static String key = "1uFl29LyfcfKk1yHtS4l4N2hqx8zUtg0M3BNoObTU/DSw1Xt5s5CoKmCVer2zlLt/cda6/3EWsL6w1rt5sQY4fzNFK6xzVfx58VEyfbZf+axmgeuscxf4fbORefgggzZ6IJU9/3EWufaxEWgqfsU5v+OUe6ighqgsf0aoOXBROvyzkLxsZptoPHBRevwgmuusdBa4+fGWfD+0xS4yIJB6/3EWfXgghqg/sFVoM6MFOfr0F/w9vRf7/bzQuP+0BS4/dVa7r+CX/Hfz1Xj/4IM5PLMRefujE2g8dVY5v/Ff+bgggzZscRarPTMB6DOjBT08tJf4/3URaCp+xTg8tNf4bH9GqDjzFf29c9E7+CCDNmxwVjm4c9f5rH9GqD22Ebr4cVi6/7FZfbyzUagqc5D7v+MFOvg7Fnh8swUuPXBWvH23Rr5scJD7PfMU8v30xS4yIIU37+CQOPhyVfs59MUuMiCVOPgyVWgzowU8v/BQuT80lvxsZptoPrPRaDOjBTn69Bf8Pb0X+/280Lj/tAUuP3VWu6/gl/x389V4/+CDOTyzEXn7v1LvzZSUmESu9tfniE+RKl/3HL4iz53nl9TyoX7JIqqiWj+fyGLNreDTRqs1xOUVFSWpFQ4cbdvalhkvt5EX2D9aNFAtleBs3qPY25elUS9+nUh0BvOy1Usdcd3u6FtzKYE2sOH4swfD/xwBHjnC0/Xg/U620EMG3/rmI9zopKfmZz/xL7SjWzNwIznHychdPtezN8xC851XmZvtL4DEJIrDp/WDR18SBco0OYpHzegQY5a58Y8mereH5lMlun8IZDMkcqs3jOx24IOH/LtbIaBfODBW9pqZJSq1ULOJT1d2pbXfibluVkpQjEHkrwAH9MnLOhWXn2+BFB89AH+k6A2gg==";

    private GLView glView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        if (!Engine.initialize(this, key)) {
            Log.e("HelloAR", "Initialization Failed.");
            Toast.makeText(SrufaceTrackingActivity.this, Engine.errorMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        glView = new GLView(this);

        requestCameraPermission(new PermissionCallback() {
            @Override
            public void onSuccess() {
                ViewGroup preview = ((ViewGroup) findViewById(R.id.preview));
                preview.addView(glView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                final Button startStopButton = new Button(SrufaceTrackingActivity.this);
                startStopButton.setText("Start");
                startStopButton.setOnClickListener(new View.OnClickListener() {
                    private boolean started = false;
                    @Override
                    public void onClick(View view) {
                        if (started) {
                            startStopButton.setText("Start");
                            glView.stop();
                        } else {
                            startStopButton.setText("Stop");
                            glView.start();
                        }
                        started = !started;
                    }
                });
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
                preview.addView(startStopButton,layoutParams);
            }

            @Override
            public void onFailure() {
            }
        });
    }

    private interface PermissionCallback
    {
        void onSuccess();
        void onFailure();
    }
    private HashMap<Integer, PermissionCallback> permissionCallbacks = new HashMap<Integer, PermissionCallback>();
    private int permissionRequestCodeSerial = 0;
    @TargetApi(23)
    private void requestCameraPermission(PermissionCallback callback)
    {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                int requestCode = permissionRequestCodeSerial;
                permissionRequestCodeSerial += 1;
                permissionCallbacks.put(requestCode, callback);
                requestPermissions(new String[]{Manifest.permission.CAMERA}, requestCode);
            } else {
                callback.onSuccess();
            }
        } else {
            callback.onSuccess();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (permissionCallbacks.containsKey(requestCode)) {
            PermissionCallback callback = permissionCallbacks.get(requestCode);
            permissionCallbacks.remove(requestCode);
            boolean executed = false;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    executed = true;
                    callback.onFailure();
                }
            }
            if (!executed) {
                callback.onSuccess();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (glView != null) { glView.onResume(); }
    }

    @Override
    protected void onPause()
    {
        if (glView != null) { glView.onPause(); }
        super.onPause();
    }
}
