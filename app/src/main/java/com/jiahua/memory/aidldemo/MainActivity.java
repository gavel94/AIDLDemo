package com.jiahua.memory.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jiahua.memory.aidldemo.databinding.ActivityMainBinding;
import com.jiahua.memory.aidldemo.moudle.Book;
import com.jiahua.memory.aidldemo.serivce.BookService;
import com.jiahua.memory.aidldemo.serivce.CalculateService;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding mBinding;
    private ICalculateInterface mService;
    private IBookManager mBookManager;

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

    private ServiceConnection mBookServiceConnection = new ServiceConnection()
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            mBookManager = IBookManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            mBookManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Intent intent = new Intent(this, CalculateService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        Intent bookIntent = new Intent(this, BookService.class);
        bindService(bookIntent, mBookServiceConnection, Context.BIND_AUTO_CREATE);

        initEvent();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unbindService(mServiceConnection);
        unbindService(mBookServiceConnection);
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

        mBinding.btnBook.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Book book = new Book();
                    book.setName("asasdf");
                    Random random = new Random();
                    int max = 20;
                    int min = 10;
                    int p = random.nextInt(max) % (max - min + 1) + min;
                    book.setPrice(p);

                    mBookManager.addBook(book);
                } catch (RemoteException e)
                {
                    e.printStackTrace();
                }
            }
        });

        mBinding.btnBook.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                try
                {
                    List<Book> bookList = mBookManager.getBookList();
                    String dec = "";
                    for (Book book : bookList)
                    {
                        dec += book.toString();
                    }
                    mBinding.tvBookDec.setText(dec);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }
}
