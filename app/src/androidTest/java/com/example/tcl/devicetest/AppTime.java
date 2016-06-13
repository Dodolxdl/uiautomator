package com.example.tcl.devicetest;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ruizhang on 2016/6/13.
 */
@RunWith(AndroidJUnit4.class)
public class AppTime {
    private Instrumentation instrumentation;
    private UiDevice device;
    private Context context;


    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = InstrumentationRegistry.getContext();

    }
    @After
    public void tearDown(){
        device.pressBack();
        device.pressBack();
        device.pressBack();
        device.pressHome();
    }
    @Test
    public void getTime(){
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(0);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,0);
        Intent intent = new Intent();
        File file1 = new File(Environment.getExternalStorageDirectory(),"Total.txt");
        if(file1.exists()){
            file1.delete();
        }
        FileWriter fos =null;
        FileWriter fos1 = null;
        for(ResolveInfo info:resolveInfos){
            try {
                fos1 = new FileWriter(file1,true);
                String[] str = info.toString().split(" ");
                String waitTime = device.executeShellCommand("am start -W "+ str[1]);
                Pattern pattern = Pattern.compile("WaitTime:\\s\\d*");
                Matcher matcher = pattern.matcher(waitTime);
                String activityName = info.activityInfo.name;
                if(matcher.find()){
                    fos1.write(activityName + ":" + matcher.group(0) + "\n");
                    sleep(5000);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos1!=null){
                    try {
                        fos1.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if(fos!=null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
