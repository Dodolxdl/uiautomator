package com.example.tcl.devicetest;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.Configurator;
import android.support.test.uiautomator.Direction;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

/**
 * Created by ruizhang on 2016/8/11.
 */
@RunWith(AndroidJUnit4.class)
public class TestAssert {
    private Instrumentation instrumentation;
    private UiDevice device;
    public Method method;
    private static final String TAG = "TestAssert";
    private Configurator configurator;

    @Before
    public void setUp() {
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        configurator = Configurator.getInstance();


    }

    @Test
    public void assertTest() {
        UiObject2 name = device.findObject(By.text("文件管理器"));
        name.clickAndWait(Until.newWindow(), 5000);
        UiObject2 file = device.findObject(By.res("com.jrdcom.filemanager:id/size_percent"));
        file.clickAndWait(Until.newWindow(), 2000);
        String packageName = device.getCurrentPackageName();
        Assert.assertEquals("file", "com.jrdcom.filemanager", packageName);
        UiObject2 listView = device.findObject(By.depth(5));
    }
    @Test
    public void testClick(){
        UiObject2 wieght = device.findObject(By.res("com.moji.mjweather:id/HotAreaDownLayout"));
        boolean check = wieght.isLongClickable();
        Log.d(TAG, "testClick: "+check);
//        wieght.longClick();
        device.swipe(500,600,500,610,300);
    }
    @Test
    public void testScorll(){
        final UiObject2 listView = device.findObject(By.clazz("android.widget.ScrollView"));
        boolean b = device.performActionAndWait(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <10 ; i++) {
                    listView.scroll(Direction.DOWN,0.6f);
                }
            }
        }, Until.scrollFinished(Direction.DOWN),15000);
        Assert.assertTrue(b);
        UiSelector ui = new UiSelector().className("");
    }
    @Test
    public void testConfig(){
        long time = configurator.getActionAcknowledgmentTimeout();
        Log.d(TAG, "testConfig: "+time);
        Configurator c = configurator.setActionAcknowledgmentTimeout(1000);
        long time1 = c.getActionAcknowledgmentTimeout();
        Log.d(TAG, "testConfig: "+time1);
    }

    @Test
    public void quicklyClick(){
        long time = configurator.getActionAcknowledgmentTimeout();
        configurator.setActionAcknowledgmentTimeout(0);
        int x = device.getDisplayWidth();
        int y= device.getDisplayHeight();
        for (int i = 0; i <2 ; i++) {
            device.click(x/2,y/2);
        }
        configurator.setActionAcknowledgmentTimeout(time);
        device.getCurrentPackageName();
    }

}
