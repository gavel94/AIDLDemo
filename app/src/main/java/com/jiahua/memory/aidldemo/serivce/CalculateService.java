package com.jiahua.memory.aidldemo.serivce;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.jiahua.memory.aidldemo.ICalculateInterface;

/**
 * Created by jhhuang on 2017/2/6.
 * QQ:781913268
 * Description：CalculateService
 */
public class CalculateService extends Service
{

    private final ICalculateInterface.Stub mBinder = new ICalculateInterface.Stub()
    {
        @Override
        public double doCalculate(double a, double b) throws RemoteException
        {
            return a + b;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}