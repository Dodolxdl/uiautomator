package com.example.tcl.devicetest;

import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by ruizhang on 2016/6/7.
 */
@RunWith(AndroidJUnit4.class)
public class StartApp {
    private Instrumentation instrumentation;
    private UiDevice device;
    private Context context;
    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = InstrumentationRegistry.getContext();

    }
    @Test
    public void startActivity(){
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        //获取包信息、应用信息
//        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
//        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(0);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //获取包管理
        PackageManager pm = context.getPackageManager();
        //查找启动方式为LAUNCHER并且是行为ACTION_MAIN的APP
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,0);
        Intent intent = new Intent();
        for(ResolveInfo resolveInfo :resolveInfos){
            String activityName = resolveInfo.activityInfo.name;
            String packageName = resolveInfo.activityInfo.packageName;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(new ComponentName(packageName,activityName));
            context.startActivity(intent);
            device.pressBack();
            device.pressBack();
            device.pressHome();
//            sleep(5000);
//            clearRecentApps();
//            sleep(5000);

        }

    }
    public void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void clearRecentApps(){
        try {
            device.pressRecentApps();
            sleep(5000);
            UiObject2 clearObject = device.findObject(By.res("com.android.systemui:id/clear_recents_image"));
            clearObject.clickAndWait(Until.newWindow(),2000);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}

