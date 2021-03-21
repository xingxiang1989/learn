package com.some.mvvmdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xiangxing
 */
public class Author implements Parcelable {

    String name;


    public Author(String name){
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
    }

    public Author() {
    }

    protected Author(Parcel in) {
        this.name = in.readString();
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
