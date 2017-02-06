package com.jiahua.memory.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jiahua.memory.aidldemo.databinding.ActivityMainBinding;
import com.jiahua.memory.aidldemo.serivce.CalculateService;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding mBinding;
    private ICalculateInterface mService;

    private ServiceConnection mServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mService = ICalculateInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Intent intent = new Intent(this, CalculateService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        initEvent();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(mServiceConnection);
    }

    private void initEvent()
    {
        mBinding.btnSum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    int num1 = Integer.parseInt(mBinding.etNum1.getText().toString().trim());
                    int num2 = Integer.parseInt(mBinding.etNum2.getText().toString().trim());
                    double sum = mService.doCalculate(num1, num2);
                    mBinding.tvSum.setText(String.valueOf(sum));
                } catch (Exception e)
                {
                    e.printStackTrace();
                    mBinding.tvSum.setText("发生异常");
                }

            }
        });
    }
}
