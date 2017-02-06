package com.jiahua.memory.aidldemo.serivce;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.jiahua.memory.aidldemo.IBookManager;
import com.jiahua.memory.aidldemo.moudle.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jhhuang on 2017/2/6.
 * QQ:781913268
 * Description：BookService
 */
public class BookService extends Service
{
    private List<Book> mBooks;
    private final IBookManager.Stub mBinder = new IBookManager.Stub()
    {

        @Override
        public List<Book> getBookList() throws RemoteException
        {
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException
        {
            mBooks.add(book);
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
        mBooks = new ArrayList<>();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}