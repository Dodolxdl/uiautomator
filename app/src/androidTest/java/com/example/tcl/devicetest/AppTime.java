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
import java.util.ArrayList;
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
    private PackageManager pm;

    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = InstrumentationRegistry.getContext();
        pm = context.getPackageManager();
    }
    @After
    public void tearDown(){
        device.pressBack();
        device.pressBack();
        device.pressBack();
        device.pressHome();
    }
    @Test
    public void getCoolTime(){
        //获取已安装应用包的信息
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        //获取已安装应用信息
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(0);
        //获取每个应用的component
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        //保存的文件路径
        File file = new File(Environment.getExternalStorageDirectory(),"CoolTime.txt");
        if(file.exists()){
            file.delete();
        }
        FileWriter fos =null;
        //查询每个应用启动的component
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,0);
        Intent intent = new Intent();
        for(ResolveInfo info:resolveInfos){
            try {
                //文件追加写入
                fos = new FileWriter(file,true);
                //获取component转换成字符串
                String[] str = info.toString().split(" ");
                //启动component获取到app启动信息
                String waitTime = device.executeShellCommand("am start -W "+ str[1]);
                //用正则去匹配所需要的字符
                Pattern pattern = Pattern.compile("WaitTime:\\s\\d*");
                Matcher matcher = pattern.matcher(waitTime);
                //获取包名
                String packageName = info.activityInfo.packageName;
                if(matcher.find()){
                    //写入数据到文件
                    fos.write(packageName + "首次冷启动时间" + ":"+ matcher.group(0) + "ms\n");
                    sleep(2000);
                }
                device.pressHome();
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
    }

    @Test
    public void getHootTime(){
        List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(0);
        Intent mainIntent = new Intent(Intent.ACTION_MAIN);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent,0);
        Intent intent = new Intent();
        Pattern pattern = Pattern.compile("WaitTime:\\s\\d*");
        File file = new File(Environment.getExternalStorageDirectory(),"HootTime.txt");
        if(file.exists()){
            file.delete();
        }
        FileWriter fw = null;
        for(ResolveInfo info:resolveInfos){
            List<String> time = new ArrayList<String>();
            try {
                for(int i =0;i<10;i++){
                    String[] str = info.toString().split(" ");
                    String waitTime = device.executeShellCommand("am start -W "+ str[1]);
                    Matcher matcher = pattern.matcher(waitTime);
                    String packageName = info.activityInfo.packageName;
                    if(matcher.find()){
                        String[] string = matcher.group().split(":");
                        String finalTime = string[1].trim();
                        time.add(finalTime);
                        sleep(1000);
                        device.pressHome();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String[] str = getSting(time);
            int[] ints = getInt(str);
            double averageTime = getAverageTime(ints);
//            String finalTime = averageTime+" ";
            String finalTime = String.valueOf(averageTime);
            String packageName = info.activityInfo.packageName;
            try {
                fw = new FileWriter(file,true);
                fw.write(packageName + ":");
                for(String string:str){
                    fw.write(string+" ");
                }
                fw.write("10次平均时间为："+ finalTime + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fw.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fw!=null){
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    //求平均数
    public double getAverageTime(int[] arrays){
        double sum=0;
        for(int i=0;i<arrays.length;i++){
            sum+=arrays[i];
        }
        return sum/10;
    }
    //将List集合转化为String数组
    public String[] getSting(List<String> list){
        String[] arraysList = list.toArray(new String[list.size()]);
        return arraysList;
    }
    //将String数组转化为int数组
    public int[] getInt(String[] strings){
        int[] ints = new int[strings.length];
        for(int i=0;i<strings.length;i++){
            ints[i] = Integer.parseInt(strings[i]);
        }
        return  ints;
    }
    public void sleep(int s){
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
