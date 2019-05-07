package com.example.finalprojectnrl4;


import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable {
    public String msg;
    public String tag;

    public Message(String m, String t) {
        msg = m;
        tag = t;
    }

    protected Message(Parcel in) {
        msg = in.readString();
        tag = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(msg);
        dest.writeString(tag);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
