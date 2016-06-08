package com.example.tcl.devicetest;
import android.app.ActivityManager;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Debug;
import android.os.Environment;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ruizhang on 2016/5/31.
 */
@RunWith(AndroidJUnit4.class)
public class getMeminfo {
    private Context context;
    private UiDevice device;
    private Instrumentation instrumentation;
    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = InstrumentationRegistry.getContext();
    }
    @Test
    public void getMemery() throws IOException{
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String str3 = null;
        Runtime runtime = Runtime.getRuntime();
        java.lang.Process process = null;
        try {
            process = runtime.exec("adb shell dumpsys meminfo com.android.music");
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str = null;
            while ((str=br.readLine())!=null){
                sb.append(str+" ");
            }
            String str1 = str.toString();
            String str2=str1.substring(str1.indexOf("Objects")-60,str1.indexOf("Objects"));
            str3 = str2.substring(0, 10);
            str3.trim();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            process.destroy();
        }
    }
    @Test
    public void getM(){

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        File file = new File(Environment.getExternalStorageDirectory(),"meminfo.txt");
        BufferedWriter bw = null;
        try {
            String string = device.executeShellCommand("dumpsys meminfo com.android.music");
            Log.i("TAG",string);
            bw = new BufferedWriter(new FileWriter(file,true));
            bw.write(string);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(bw!=null){
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Test
    public void getMe(){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(info);
        Log.i("TAG","系统剩余内存:"+(info.availMem >> 10)+"k");
        long string = info.availMem;
    }
    @Test
    public void getMeminfoTest(){
        File file = new File(Environment.getExternalStorageDirectory(),"meminfo.txt");
        FileOutputStream fos = null;
        try {
            String string = device.executeShellCommand("dumpsys meminfo com.android.music");
            Log.i("TAG",string);
            Pattern pattern = Pattern.compile("TOTAL(\\s+\\d+){7}");
//            boolean b = string.matches("TOTAL(\\s+\\d+){7}");
            Matcher matcher = pattern.matcher(string);
            String str = matcher.group();
            fos = new FileOutputStream(file);
            fos.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Test
    public void getRunningAppProcessInfo() {
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> list = mActivityManager.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : list) {
            // 进程ID号
            int pid = info.pid;
            // 用户ID
            int uid = info.uid;
            // 进程名
            String processName = info.processName;
            // 占用的内存
            int[] pids = new int[] {pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(pids);
            int memorySize = memoryInfo[0].dalvikPrivateDirty;
            Log.i("TAG",memorySize+"");

            System.out.println("processName="+processName+",pid="+pid+",uid="+uid+",memorySize="+memorySize+"kb");
        }
    }

}
