package com.example.zhizhong.myapplication03;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.PowerManager;
import android.speech.RecognizerIntent;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private ScreenStateReceiver mReceiver;
    private ProxService proxService;
    Intent intent = new Intent(MainActivity.this, ProxService.class);

    private SensorManager mySM;
    private SensorManager mySM1;
    private TextView count;
    private TextView count1;
    public MediaRecorder mRecorder  =null;
    public float audiolevel=0,maxal=0;
    boolean actRuning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(intent);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new ScreenStateReceiver();
        registerReceiver(mReceiver, intentFilter);
    count =(TextView)findViewById(R.id.count);
        count1 =(TextView)findViewById(R.id.count1);

        mySM=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mySM1=(SensorManager)getSystemService(Context.SENSOR_SERVICE);

        MediaRecorder mRecorder  = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mRecorder.setOutputFile("/dev/null");
        try {
            mRecorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mRecorder.start();
        audiolevel=mRecorder.getMaxAmplitude ();
        Log.d("223", String.valueOf(audiolevel));

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = powerManager.isInteractive();
        } else {
            isScreenOn = powerManager.isScreenOn();
        }

        if (!isScreenOn) {

            // The screen has been locked
            // do stuff...
            System.out.print("111231");
            Log.d("222","DSF");

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actRuning=true;
       // Sensor cou=mySM.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        //Sensor det=mySM.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor dest=mySM.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if(count!=null){
           // mySM.registerListener(this,cou,SensorManager.SENSOR_DELAY_FASTEST);
           // mySM1.registerListener(this,det,SensorManager.SENSOR_DELAY_FASTEST);
            mySM.registerListener(this,dest,SensorManager.SENSOR_DELAY_FASTEST);


        }
        else {
            Toast.makeText(this,"not",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
    if(actRuning   )    {
        count.setText(String.valueOf(event.values[0]));
        count1.setText(String.valueOf(event.values[0]));
    }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
