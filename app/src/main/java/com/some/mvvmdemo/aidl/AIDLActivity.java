package com.some.mvvmdemo.aidl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.blankj.utilcode.util.LogUtils;
import com.some.mvvmdemo.BookManager;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.databinding.ActivityAidlBinding;
import com.some.mvvmdemo.entity.Book;
import com.some.mvvmdemo.service.RemoteService;

import java.util.List;
import java.util.Random;

/**
 * @author xiangxing
 * 去开启一个服务，使用bindService，bindService这样是可以进行activity与Service的通信
 * 而StartService 则没有达到这个效果。
 */
public class AIDLActivity extends Activity {

    private ActivityAidlBinding mBinding;
    private BookManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_aidl);

        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent,connection, BIND_AUTO_CREATE);

        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Book book = new Book("book in " + random.nextInt(), 100.0f);

                try {
                    manager.addBookIn(book);
                    List<Book> bookList = manager.getBooks();
//                    mBinding.tv.setText(bookList.toString());
                    mBinding.tv.setText(book.toString());


                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mBinding.btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Book book = new Book("book out " + random.nextInt(), 100.0f);

                try {
                    manager.addBookOut(book);
                    List<Book> bookList = manager.getBooks();
//                    mBinding.tv.setText(bookList.toString());
                    mBinding.tv.setText(book.toString());

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        mBinding.btninout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Book book = new Book("book inout " + random.nextInt(), 100.0f);

                try {
                    manager.addBookInOut(book);
                    List<Book> bookList = manager.getBooks();
//                    mBinding.tv.setText(bookList.toString());

                    mBinding.tv.setText(book.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LogUtils.d("onServiceConnected -- >");
            manager = BookManager.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.d("onServiceDisconnected -- >");
            manager = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //必须要解绑，否则有内存泄漏的问题
        unbindService(connection);
    }
}
