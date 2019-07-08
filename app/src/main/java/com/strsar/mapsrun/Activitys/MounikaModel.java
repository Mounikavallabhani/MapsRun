package com.strsar.mapsrun.Activitys;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Property;

public class MounikaModel implements Parcelable {

    String orderid;
    String seller_id;
    String user_id;
    String seller_lat;
    String seller_log;
    String user_lat;
    String user_log;
    String booking_id;
    String order_paymentid;
    String seller_phone_number;
    String celler_phone_number;
    String order_prepaed;
    String payment_type;
    String pat_amount;

    public MounikaModel(String orderid, String seller_id, String user_id, String seller_lat, String seller_log, String user_lat, String user_log, String booking_id, String order_paymentid, String seller_phone_number, String celler_phone_number, String order_prepaed, String payment_type, String pat_amount) {
        this.orderid = orderid;
        this.seller_id = seller_id;
        this.user_id = user_id;
        this.seller_lat = seller_lat;
        this.seller_log = seller_log;
        this.user_lat = user_lat;
        this.user_log = user_log;
        this.booking_id = booking_id;
        this.order_paymentid = order_paymentid;
        this.seller_phone_number = seller_phone_number;
        this.celler_phone_number = celler_phone_number;
        this.order_prepaed = order_prepaed;
        this.payment_type = payment_type;
        this.pat_amount = pat_amount;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPat_amount() {
        return pat_amount;
    }

    public void setPat_amount(String pat_amount) {
        this.pat_amount = pat_amount;
    }

    public String getOrder_prepaed() {
        return order_prepaed;
    }

    public void setOrder_prepaed(String order_prepaed) {
        this.order_prepaed = order_prepaed;
    }

    public String getSeller_phone_number() {
        return seller_phone_number;
    }

    public void setSeller_phone_number(String seller_phone_number) {
        this.seller_phone_number = seller_phone_number;
    }

    public String getCeller_phone_number() {
        return celler_phone_number;
    }

    public void setCeller_phone_number(String celler_phone_number) {
        this.celler_phone_number = celler_phone_number;
    }

    public static Creator<MounikaModel> getCREATOR() {
        return CREATOR;
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

    //write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags){
        //write all properties to the parcle
        dest.writeString(orderid);
        dest.writeString(seller_id);
        dest.writeString(user_id);
        dest.writeString(seller_lat);
        dest.writeString(seller_log);
        dest.writeString(user_lat);
        dest.writeString(user_log);
        dest.writeString(booking_id);
        dest.writeString(order_paymentid);

    }

    //constructor used for parcel
    public MounikaModel(Parcel parcel){
        //read and set saved values from parcel
        orderid = parcel.readString();
        seller_id = parcel.readString();
        user_id = parcel.readString();
        seller_lat = parcel.readString();
        seller_log = parcel.readString();
        user_lat = parcel.readString();
        user_log = parcel.readString();
        booking_id = parcel.readString();
        order_paymentid = parcel.readString();

    }

    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<MounikaModel> CREATOR = new Parcelable.Creator<MounikaModel>(){

        @Override
        public MounikaModel createFromParcel(Parcel parcel) {
            return new MounikaModel(parcel);
        }

        @Override
        public MounikaModel[] newArray(int size) {
            return new MounikaModel[size];
        }
    };

    //return hashcode of object
    public int describeContents() {
        return hashCode();
    }
}
