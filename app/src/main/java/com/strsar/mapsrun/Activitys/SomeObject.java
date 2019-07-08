package com.strsar.mapsrun.Activitys;

import android.os.Parcel;
import android.os.Parcelable;

public class

SomeObject implements Parcelable
{
    public String someString = "";
    public int someInt = 0;
    public double someDouble = 0.0;

    public static String TYPE = "someObject";

    public SomeObject(String s, int i, double d)
    {
        someString = s;
        someInt    = i;
        someDouble = d;
    }

    public SomeObject(Parcel in)
    {
        //read in same order that you wrote in writeToParcel
        someString = in.readString();
        someInt    = in.readInt();
        someDouble = in.readDouble();
//      reading in a list custom objects: in.readTypedList(someCustomObjectArrayList, someCustomObject.CREATOR )

    }

    @Override
    public void writeToParcel(Parcel out, int i)
    {
        //this is the order you should read in your contructor
        out.writeString(someString);
        out.writeInt(someInt);
        out.writeDouble(someDouble);
//      writing some custom object: out.writeTypedList(someCustomObjectArrayList);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    //for arrays and creating from a parcel
    public static final Parcelable.Creator<SomeObject> CREATOR = new Parcelable.Creator<SomeObject>() {
        public SomeObject createFromParcel(Parcel in) {
            return new SomeObject(in);
        }
        public SomeObject[] newArray(int size) {
            return new SomeObject[size];
        }
    };
}
