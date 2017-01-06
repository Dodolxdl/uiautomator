package com.example.tcl.devicetest;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ruizhang on 2016/12/9.
 */
@RunWith(AndroidJUnit4.class)
public class Utils {
    private Instrumentation instrumentation;
    private UiDevice device;
    private int Xpoint;
    private int Ypoint;

    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        Xpoint = device.getDisplayWidth();
        Ypoint = device.getDisplayHeight();
    }
    @After
    public void tearDown(){
        sleep(2000);
    }
    @Test
    public void swipUp(){
        device.swipe( Xpoint/2, Ypoint/2, Xpoint/2, Ypoint/2-500,5);
    }
    @Test
    public void swipDonw(){
        device.swipe(Xpoint/2, Ypoint/2, Xpoint/2, Ypoint/2+600,5);
    }
    @Test
    public void swipLeft(){
        device.swipe(Xpoint/2, Ypoint/2, Xpoint/2-400, Ypoint/2,5);
    }
    @Test
    public void swipRingt(){
        device.swipe(Xpoint/2, Ypoint/2, Xpoint/2+400, Ypoint/2,5);
    }


    public void sleep(int sleep){
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
