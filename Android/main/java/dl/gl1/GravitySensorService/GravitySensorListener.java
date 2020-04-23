package dl.gl1.GravitySensorService;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import dl.gl1.GLES10RubikCubeFragment;

public class GravitySensorListener implements SensorEventListener {
    public static float x,y,z;
        @Override
        public void onSensorChanged(SensorEvent event) {
            GravitySensor.z=event.values[0];
            GravitySensor.y=event.values[1];
            GravitySensor.x=event.values[2];
            if(GLES10RubikCubeFragment.mGLView!=null)
                if(!GLES10RubikCubeFragment.mGLView.getPreserveEGLContextOnPause())
                    GLES10RubikCubeFragment.mGLView.requestRender();
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
