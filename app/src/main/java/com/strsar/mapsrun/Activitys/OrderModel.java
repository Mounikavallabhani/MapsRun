package com.strsar.mapsrun.Activitys;

import android.os.Parcel;
import android.os.Parcelable;

public class OrderModel implements Parcelable
{
    public String someString = "";
    public int someInt = 0;
    public double someDouble = 0.0;

    public static String TYPE = "someObject";

    public OrderModel(String s, int i, double d)
    {
        someString = s;
        someInt    = i;
        someDouble = d;
    }

    public OrderModel(Parcel in)
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

    String orderid;
    String seller_id;
    String user_id;
    String seller_lat;
    String seller_log;
    String user_lat;
    String user_log;
    String booking_id;
    String order_paymentid;

    public OrderModel() {

    }

    public OrderModel(String orderid, String seller_id) {
        this.orderid = orderid;
        this.seller_id = seller_id;
    }

    public OrderModel(String orderid, String seller_id, String user_id, String seller_lat, String seller_log, String user_lat, String user_log, String booking_id, String order_paymentid) {
        this.orderid = orderid;
        this.seller_id = seller_id;
        this.user_id = user_id;
        this.seller_lat = seller_lat;
        this.seller_log = seller_log;
        this.user_lat = user_lat;
        this.user_log = user_log;
        this.booking_id = booking_id;
        this.order_paymentid = order_paymentid;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSeller_lat() {
        return seller_lat;
    }

    public void setSeller_lat(String seller_lat) {
        this.seller_lat = seller_lat;
    }

    public String getSeller_log() {
        return seller_log;
    }

    public void setSeller_log(String seller_log) {
        this.seller_log = seller_log;
    }

    public String getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(String user_lat) {
        this.user_lat = user_lat;
    }

    public String getUser_log() {
        return user_log;
    }

    public void setUser_log(String user_log) {
        this.user_log = user_log;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getOrder_paymentid() {
        return order_paymentid;
    }

    public void setOrder_paymentid(String order_paymentid) {
        this.order_paymentid = order_paymentid;
    }
}
