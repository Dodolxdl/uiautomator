package com.example.tcl.devicetest;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

/**
 * Created by ruizhang on 2016/6/12.
 */
@RunWith(AndroidJUnit4.class)
public class GetAppStartTime {
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
        for(ResolveInfo info:resolveInfos){
            try {
                String[] str = info.toString().split(" ");
                String string = str[1];
                String command = "am start -W "+ string;
                device.executeShellCommand(command);
                sleep(5000);
            } catch (IOException e) {
                e.printStackTrace();
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
