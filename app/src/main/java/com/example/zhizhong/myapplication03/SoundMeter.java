package com.example.zhizhong.myapplication03;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

import java.io.IOException;

/**
 * Created by zhizhong on 2016-09-21.
 */

public class SoundMeter {

    private MediaRecorder mRecorder = null;

    public void start(){
        if (mRecorder == null) {
            try {
                mRecorder = new MediaRecorder();

                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile("/dev/null");
                mRecorder.prepare();

            }
            catch (Exception e) {
                android.util.Log.e("TrackingFlow", "Exception", e);
            }
            mRecorder.start();
        }
    }

    public void stop() {
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getAmplitude() {
        if (mRecorder != null)
            return mRecorder.getMaxAmplitude();
        else
            return 0;

    }
}
