package com.lwp.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lwp.aidltest.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //跨进程 调用服务方法
            IMyAidlInterface imai = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                imai.showProgress();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void operate(View v){
        switch(v.getId()){
            case R.id.start:
                //远程启动服务
                Intent it = new Intent();
                it.setAction("com.lwp.myservice");
                it.setPackage("com.lwp.aidltest");
                startService(it);
                break;
            case R.id.stop:
                //远程停止服务
                Intent it2 = new Intent();
                it2.setAction("com.lwp.myservice");
                it2.setPackage("com.lwp.aidltest");
                stopService(it2);
                break;

            case R.id.bind:
                //远程绑定服务
                Intent it3 = new Intent();
                it3.setAction("com.lwp.myservice");
                it3.setPackage("com.lwp.aidltest");
                bindService(it3,conn,BIND_AUTO_CREATE);
                break;
            case R.id.unbind:
                //远程解绑服务
                unbindService(conn);
                break;

        }
    }
}