package com.example.tcl.devicetest;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ruizhang on 2016/8/11.
 */
@RunWith(AndroidJUnit4.class)
public class Meminfo {
    private static final String TAG = "appManager";
    private Runtime runtime;
    private UiDevice device;
    private Instrumentation instrumentation;
    private Context context;
    private PackageManager pm;
    private ActivityManager activityManager;
    private Method method;

    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = InstrumentationRegistry.getContext();
        pm = context.getPackageManager();
    }

    @Test
    public void getMemery(){
        File file = new File(Environment.getExternalStorageDirectory(),"meminfo.txt");
        if (file.exists()) {
            file.delete();
        }
        BufferedWriter bufferedWriter= null;
        for (int i = 0; i <10 ; i++) {
            try {
                String string = device.executeShellCommand("dumpsys meminfo com.monster.appmanager");
                Pattern pattern = Pattern.compile("TOTAL:\\s+\\d*");
                Matcher matcher = pattern.matcher(string);
                bufferedWriter = new BufferedWriter(new FileWriter(file,true));
                if (matcher.find()) {
                    String TotalTime = matcher.group(0).toString();
                    bufferedWriter.write(TotalTime);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    @Test
    public void getMemeryInfo(){
        File file = new File(Environment.getExternalStorageDirectory(),"meminfo.txt");
        if (file.exists()) {
            file.delete();
        }
        BufferedWriter bufferedWriter= null;
        try {
            String string = device.executeShellCommand("dumpsys meminfo com.monster.appmanager");
            Pattern pattern = Pattern.compile("TOTAL:\\s+\\d*");
            Matcher matcher = pattern.matcher(string);
            bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            if (matcher.find()) {
                String TotalTime = matcher.group(0).toString();
                Log.d(TAG, "getMemery: "+TotalTime);
                bufferedWriter.write(TotalTime);
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
