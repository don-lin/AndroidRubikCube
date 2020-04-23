package dl.gl1.GravitySensorService;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import dl.gl1.GLES10RubikCubeFragment;

public class GravitySensor {

    private static SensorManager sensorManager;
    private static SensorEventListener sensorEventListener;
    public static float x,y,z;

    public static void regeister(Context context){
        if(sensorManager!=null)
            return;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorEventListener=new GravitySensorListener();

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                z=event.values[0];
                y=event.values[1];
                x=event.values[2];
                GLES10RubikCubeFragment.mGLView.requestRender();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {}

        }, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);
    }
    public static void unregeister(){
        if(sensorManager!=null)
            sensorManager.unregisterListener(sensorEventListener);
        sensorManager=null;
        sensorEventListener=null;
        x=0;
        y=0;
        z=0;

    }
}
