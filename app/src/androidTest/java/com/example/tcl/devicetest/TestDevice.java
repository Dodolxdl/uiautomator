package com.example.tcl.devicetest;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.ComponentName;
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
import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by ruizhang on 2016/5/25.
 */
@RunWith(AndroidJUnit4.class)
public class TestDevice {
    private UiDevice device;
    private Instrumentation instrumentation;
    private Context context;
    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = InstrumentationRegistry.getContext();
    }
    @After
    public void setDown(){
        device.pressBack();
        device.pressBack();
        device.pressBack();
        device.pressHome();
    }
    @Test
    public void startApp(){
        PackageManager pm = context.getPackageManager();
        //获取安装包的的信息
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        //获取应用的信息
        List<ApplicationInfo> applicationInfos =pm.getInstalledApplications(0);

        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        //获取每个应用的启动入口component信息
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);


        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,0);
        Intent intent = new Intent();
        for(ResolveInfo info:resolveInfos){
            String activityName = info.activityInfo.name;
            String packageName = info.activityInfo.packageName;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//每次启动为标记为新任务
            intent.setComponent(new ComponentName(packageName,activityName));
            context.startActivity(intent);
            sleep(5000);
        }
    }
    public void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void deviceTest(){
        device.pressBack();
        device.pressHome();
    }
    @Test
    public void getActivity(){
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        String activity = am.getRunningTasks(1).get(0).topActivity.getClassName();
    }
    public static boolean isSdCardExits(){
        //判断SD卡是否存在
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    public static String getPathSdcard(){
        //获取sd路径
        boolean exist = isSdCardExits();
        String sdPath = "";
        if(exist){
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else {
            sdPath = "不适用";
        }
        return sdPath;

    }
    public static String getFilePath(){
        //获取文件路径
        String filePath = "";
        File file = new File(Environment.getExternalStorageDirectory(),"test.txt");
        if(file.exists()){
            filePath = file.getAbsolutePath();
        }else {
            filePath = "不适用";
        }
        return filePath;
    }
    public void readFile(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"txt.txt");
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            fis.read(bytes);
            String str = new String(bytes);
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void writeFile(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"text.txt");
//            File file = new File("/storage/08FC-171A/text.txt");
            FileOutputStream fos = new FileOutputStream(file);
            ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
            String activity = am.getRunningTasks(1).get(0).topActivity.getClassName();
            fos.write(activity.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void bufferWrite(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"text1.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            bw.write("nihao");
            bw.flush();
            bw.write("\n");
            sleep(10000);
            bw.write("zhangrui");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getStartTime(){
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appinfo = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info:appinfo){
            if(info.processName.equals("com.android.music")){
                Log.e("TAG",System.currentTimeMillis()+"");
            }
        }
    }

}
