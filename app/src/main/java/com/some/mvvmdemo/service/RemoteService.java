package com.some.mvvmdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.some.mvvmdemo.BookManager;
import com.some.mvvmdemo.entity.Author;
import com.some.mvvmdemo.entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangxing
 * 另外的进程服务，初始化IBinder
 */
public class RemoteService extends Service {

    private ArrayList<Book> mBooks;


    private IBinder binder = new BookManager.Stub() {
        @Override
        public void addBookIn(Book book) throws RemoteException {
            LogUtils.d("addBookIn before book =" + book.toString());
            book.setName(book.getName() + " modify");
            book.setPrice(200f);
            mBooks.add(book);
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            LogUtils.d("addBookOut before book =" + book.toString());
            book.setName(book.getName() + " modify");
            book.setPrice(200f);
            mBooks.add(book);
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            LogUtils.d("addBookInOut book =" + book.toString());
            book.setName(book.getName() + " modify");
            book.setPrice(200f);
            mBooks.add(book);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return mBooks;
        }

        @Override
        public void addAuthor(Author author) throws RemoteException {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mBooks = new ArrayList<>();
        return binder;
    }
}
