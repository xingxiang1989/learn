package com.some.mvvmdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xiangxing
 */
public class Book implements Parcelable {
    String name;
    Float price;


    public Book(String name , Float price){
        this.name = name;
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.price);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.name = in.readString();
        this.price = (Float) in.readValue(Float.class.getClassLoader());
    }

    public void readFromParcel(Parcel in){
        this.name = in.readString();
        this.price = (Float) in.readValue(Float.class.getClassLoader());
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };




    @Override
    public String toString() {
        return "Book{" + "name='" + name + '\'' + ", price=" + price + '}';
    }
}
