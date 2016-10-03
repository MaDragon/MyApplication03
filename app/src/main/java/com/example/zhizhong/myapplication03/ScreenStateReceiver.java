package com.example.zhizhong.myapplication03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by zhizhong on 2016-09-19.
 */
public class ScreenStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            //code
            Log.d("222","On");
            System.out.print("1111");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            //code
            System.out.print("1122211");
            Log.d("222","Down");}
    }
}
