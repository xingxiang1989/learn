package com.some.mvvmdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.some.mvvmdemo.BookManager;
import com.some.mvvmdemo.entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangxing
 */
public class RemoteService extends Service {

    private ArrayList<Book> mBooks;


    private IBinder binder = new BookManager.Stub() {
        @Override
        public void addBook(Book book) throws RemoteException {
            mBooks.add(book);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return mBooks;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBooks = new ArrayList<>();
        return binder;
    }
}
