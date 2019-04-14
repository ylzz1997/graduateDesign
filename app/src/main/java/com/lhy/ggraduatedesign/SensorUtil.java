package com.lhy.ggraduatedesign;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;

public class SensorUtil implements SensorEventListener {
    private float[] r = new float[9];
    private float[] values = new float[3];
    private float[] gravity = null;
    private float[] geomagnetic = null;
    private float[] linerAcceleration = null;
    private float[] gyroscope = null;
    private SensorManager mSensorManager;
    private Context context;
    private float[] result= new float[3];

    private List<float[]> angal = new ArrayList<>();
    private List<float[]> accelerations = new ArrayList<>();
    private List<float[]> gyroscopes = new ArrayList<>();

    private Sensor acceleSensor;
    private Sensor LacceleSensor;
    private Sensor magSensor;
    private Sensor gyroscrope;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==0){
                if(gravity!=null && geomagnetic!=null) {
                    accelerations.add(new float[]{linerAcceleration[0],linerAcceleration[1],linerAcceleration[2]});
                    gyroscopes.add(new float[]{gyroscope[0],gyroscope[1],gyroscope[2]});
                        if(SensorManager.getRotationMatrix(r, null, gravity, geomagnetic)) {
                        SensorManager.getOrientation(r, values);
                        result[0] = (float) ((360f+values[0]*180f/Math.PI)%360);
                        result[1] = (float) ((360f+values[1]*180f/Math.PI)%360);
                        result[2] = (float) ((360f+values[2]*180f/Math.PI)%360);
                        angal.add(new float[]{result[0],result[1],result[2]});
                    }
            }
                /*
                result[0] = (float) ((360f+geomagnetic[0]*180f/Math.PI)%360);
                result[1] = (float) ((360f+geomagnetic[1]*180f/Math.PI)%360);
                result[2] = (float) ((360f+geomagnetic[2]*180f/Math.PI)%360);
                angal.add(new float[]{result[0],result[1],result[2]});
                TextView tv = ((Activity)context).findViewById(R.id.main_text);
                tv.setText("x="+result[0]+"y="+result[1]+"z="+result[2]+"  \n x="+gravity[0]+"y="+gravity[1]+"z="+gravity[2]);
                   */
            }
        }
    };


    public SensorUtil(Context context) {
        this.context = context;
        mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        LacceleSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        acceleSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        gyroscrope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void Initialization(){
        mSensorManager.registerListener(this, LacceleSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, acceleSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, magSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, gyroscrope, SensorManager.SENSOR_DELAY_GAME);
    }

    public void Pause(){
        mSensorManager.unregisterListener(this);
        handler.sendEmptyMessage(1);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        System.out.print("执行中");
        switch (event.sensor.getType()) {
            case Sensor.TYPE_LINEAR_ACCELERATION:
                linerAcceleration = event.values;
                handler.sendEmptyMessage(0);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                gravity = event.values;
                handler.sendEmptyMessage(0);
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                geomagnetic = event.values;
                handler.sendEmptyMessage(0);
                break;
            case Sensor.TYPE_GYROSCOPE:
                gyroscope = event.values;
                handler.sendEmptyMessage(0);
                break;
        }
    }

    public void Reset(){
        angal = new ArrayList<>();
        accelerations = new ArrayList<>();
        gyroscopes = new ArrayList<>();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public List<float[]> getAngal() {
        return angal;
    }

    public void setAngal(List<float[]> angal) {
        this.angal = angal;
    }

    public List<float[]> getAccelerations() {
        return accelerations;
    }

    public Sensor getLacceleSensor() {
        return LacceleSensor;
    }

    public void setAccelerations(List<float[]> accelerations) {
        this.accelerations = accelerations;
    }

    public List<float[]> getGyroscopes() {
        return gyroscopes;
    }
}
