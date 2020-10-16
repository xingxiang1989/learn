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

import com.some.mvvmdemo.BookManager;
import com.some.mvvmdemo.R;
import com.some.mvvmdemo.databinding.ActivityAidlBinding;
import com.some.mvvmdemo.entity.Book;
import com.some.mvvmdemo.service.RemoteService;

import java.util.List;
import java.util.Random;

/**
 * @author xiangxing
 */
public class AIDLActivity extends Activity {

    private ActivityAidlBinding mBinding;
    private BookManager manager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_aidl);

        ServiceConnection connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                manager = BookManager.Stub.asInterface(service);

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                manager = null;
            }
        };

        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent,connection, BIND_AUTO_CREATE);

        mBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                Book book = new Book("mask bOOk- " + random.nextInt(), 100.0f);

                try {
                    manager.addBook(book);
                    List<Book> bookList = manager.getBooks();
                    mBinding.tv.setText(bookList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
