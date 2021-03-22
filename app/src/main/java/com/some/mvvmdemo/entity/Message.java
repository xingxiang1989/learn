package com.some.mvvmdemo.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author xiangxing
 */
public class Message implements Parcelable {

    String msgId;

    public Message(){

    }

    protected Message(Parcel in) {
        msgId = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msgId);
    }
}
