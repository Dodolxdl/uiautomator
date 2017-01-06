package com.example.tcl.devicetest;

import android.app.Instrumentation;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ruizhang on 2016/12/7.
 */
@RunWith(AndroidJUnit4.class)
public class GetBytes {
    private Instrumentation instrumentation;
    private UiDevice device;
    private PackageManager pm;
    private Context context;
    public static final String TAG = "LOG";

    @Before
    public void setUp(){
        instrumentation = InstrumentationRegistry.getInstrumentation();
        device = UiDevice.getInstance(instrumentation);
        context = instrumentation.getContext();
        PackageManager pm = context.getPackageManager();
    }

    @Test
    public void getByte(){
         /*
        static long  getMobileRxBytes()  //获取通过Mobile连接收到的字节总数，不包含WiFi
        static long  getMobileRxPackets()  //获取Mobile连接收到的数据包总数
        static long  getMobileTxBytes()  //Mobile发送的总字节数
        static long  getMobileTxPackets()  //Mobile发送的总数据包数
        static long  getTotalRxBytes()  //获取总的接受字节数，包含Mobile和WiFi等
        static long  getTotalRxPackets()  //总的接受数据包数，包含Mobile和WiFi等
        static long  getTotalTxBytes()  //总的发送字节数，包含Mobile和WiFi等
        static long  getTotalTxPackets()  //发送的总数据包数，包含Mobile和WiFi等
        static long  getUidRxBytes(int uid)  //获取某个网络UID的接受字节数
        static long  getUidTxBytes(int uid) //获取某个网络UID的发送字节数
        */

        int uid = 21181;
        long tx = TrafficStats.getTotalTxBytes();//发送的 上传的流量byte
        long rx = TrafficStats.getTotalTxBytes();//下载的流量 byte
        TrafficStats.getMobileTxBytes();//获取手机3g/2g网络上传的总流量
        TrafficStats.getMobileRxBytes();//手机2g/3g下载的总流量
        TrafficStats.getTotalTxBytes();//手机全部网络接口 包括wifi，3g、2g上传的总流量
        TrafficStats.getTotalRxBytes();//手机全部网络接口 包括wifi，3g、2g下载的总流量
        Log.i(TAG, "getByte: "+tx);
        Log.i(TAG, "getByte: "+rx);


    }
}
