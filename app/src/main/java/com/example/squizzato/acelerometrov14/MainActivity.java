package com.example.squizzato.acelerometrov14;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import static java.lang.Math.floor;
import static java.lang.Math.round;
import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

public class MainActivity extends Activity implements SensorEventListener {
    private long last_update = 0, last_movement = 0;

    private float prevX = 0, prevY = 0, prevZ = 0;

    private float curX = 0, curY = 0, curZ = 0;


    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onResume() {
        super.onResume();
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sm.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        }

    }

    @Override
    protected void onStop() {
        SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        sm.unregisterListener(this);
        super.onStop();
    }

    @Override

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this) {

            long current_time = event.timestamp;


            curX = event.values[0];

            curY = event.values[1];

            curZ = event.values[2];


            if (prevX == 0 && prevY == 0 && prevZ == 0) {

                last_update = current_time;

                last_movement = current_time;

                prevX = curX;

                prevY = curY;

                prevZ = curZ;
            }


            long time_difference = current_time - last_update;

            double notg;
            /*double CurX2,CurY2,CurZ2;
            CurX2=curX;
            CurY2=curY;
            CurZ2=curZ;
            */

            /*int curx2,cury2,curz2;
            curx2=floor(curX*100);
            cury2=floor(curY*100);
            curz2=floor(curZ*100);
            double curx3,cury3,curz3;
            curx3=curx2/100;
            cury3=cury2/100;
            curz3=curz2/100;
            */


            notg=abs(9.81-sqrt(Math.pow(curX,2)+Math.pow(curY,2)+Math.pow(curZ,2)));
            double notgn=notg/9.81;
            String notgs,notgns;
            notgs=Double.toString(notg);
            notgns=Double.toString(notgn);
            ((TextView)findViewById(R.id.ms)).setText(notgs);
            ((TextView)findViewById(R.id.n)).setText(notgns);
        }
    }
}
