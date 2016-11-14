package com.example.tcl.devicetest;

import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
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
    private static final String TAG = "ZHANG";
    private ActivityManager am;
    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = InstrumentationRegistry.getContext();
        am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    }
    @After
    public void setDown(){
        device.pressBack();
        device.pressBack();
        device.pressBack();
        device.pressHome();
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
        Log.d(TAG, "getActivity: "+activity);
    }
    public boolean isSdCardExits(){
        //判断SD卡是否存在
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
    public String getPathSdcard(){
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
//    @Test
//    public String getFilePath(){
//        //获取文件路径
//        String filePath = "";
//        File file = new File(Environment.getExternalStorageDirectory(),"test.txt");
//        if(file.exists()){
//            filePath = file.getAbsolutePath();
//        }else {
//            filePath = "不适用";
//        }
//        return filePath;
//    }
    @Test
    public void readFile(){
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"test.txt");
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
            File file = new File(Environment.getExternalStorageDirectory(),"test.txt");
            if (file.exists()) {
                file.delete();
            }
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
            File file = new File(Environment.getExternalStorageDirectory(),"text2.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            bw.write("nihao");
            bw.write("\n");
            sleep(1000);
            bw.write("zhangrui");
            bw.flush();
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
