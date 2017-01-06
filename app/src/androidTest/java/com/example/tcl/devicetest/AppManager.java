package com.example.tcl.devicetest;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ruizhang on 2016/8/10.
 */
@RunWith(AndroidJUnit4.class)
public class AppManager {
    private Instrumentation instrumentation;
    private UiDevice device;
    private Configurator configurator;
    private static final String TAG = "APPMANAGER";
    private Utils utils;

    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        configurator = Configurator.getInstance();
        configurator = Configurator.getInstance();
        utils = new Utils();

    }
    @After
    public void tearDown(){
        device.pressBack();
        device.pressBack();
        device.pressBack();
        device.pressHome();
    }
    public void testBack(){
        //进入应用管理
        device.wait(Until.findObject(By.text("应用管理")),5000);
        UiObject2 appManager = device.findObject(By.text("应用管理"));
        appManager.clickAndWait(Until.newWindow(),5000);

        sleep(1000);
        //进入权限管理
        device.wait(Until.findObject(By.text("权限管理")),2000);
        UiObject2 permissonManager = device.findObject(By.text("权限管理"));
        permissonManager.clickAndWait(Until.newWindow(),2000);
        device.wait(Until.findObject(By.res("android:id/list")),2000);
        //进去权限管理列表
        final UiObject2 appList = device.findObject(By.res("android:id/list"));
        device.performActionAndWait(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <5 ; i++) {
                    appList.scroll(Direction.DOWN,0.8F);
                }
            }
        }, Until.scrollFinished(Direction.DOWN),2000);
    }
    @Test
    public void backAppmanager(){
        UiObject2 appmanager= null;
        for (int i = 0; i <5 ; i++) {
            device.wait(Until.findObject(By.text("应用管理")),5000);
            appmanager = device.findObject(By.text("应用管理"));
            appmanager.clickAndWait(Until.newWindow(),2000);
            device.pressBack();
            sleep(2000);
        }

    }
    @Test
    public void scaner(){
        device.wait(Until.findObject(By.text("应用管理")),2000);
        UiObject2 appmanagerText = device.findObject(By.text("应用管理"));
        appmanagerText.clickAndWait(Until.newWindow(),2000);
        sleep(2000);
        UiObject2 scanerText;
        for (int i = 0; i <5 ; i++) {
            scanerText = device.findObject(By.textEndsWith("扫描"));
            scanerText.click();
            sleep(10000);
        }
    }
    @Test
    public void scanAdvertisement(){
        //进入应用管理
        device.wait(Until.findObject(By.text("应用管理")),5000);
        UiObject2 appManager = device.findObject(By.text("应用管理"));
        appManager.clickAndWait(Until.newWindow(),5000);

        sleep(2000);
        //进入广告拦截
        device.wait(Until.findObject(By.text("广告拦截")),2000);
        UiObject2 advertisementText = device.findObject(By.text("广告拦截"));
        advertisementText.clickAndWait(Until.newWindow(),2000);
        sleep(2000);
        for (int i = 0; i <5 ; i++) {
            UiObject2 repetitionText = device.findObject(By.textEndsWith("扫描"));
            repetitionText.click();
            sleep(10000);
        }
    }
    public void testMethod(){
        utils.swipLeft();
        utils.sleep(1000);
        utils.swipLeft();
    }

    public void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
