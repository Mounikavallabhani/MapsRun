package com.strsar.mapsrun.Activitys;

public class CardModel {

    String orderid;
    String seller_id;
    String user_id;
    String seller_lat;
    String seller_log;
    String user_lat;
    String user_log;
    String booking_id;
    String order_paymentid;
    String order_prepaed;

    public CardModel(String orderid, String seller_id, String user_id, String seller_lat, String seller_log, String user_lat, String user_log, String booking_id, String order_paymentid, String order_prepaed) {
        this.orderid = orderid;
        this.seller_id = seller_id;
        this.user_id = user_id;
        this.seller_lat = seller_lat;
        this.seller_log = seller_log;
        this.user_lat = user_lat;
        this.user_log = user_log;
        this.booking_id = booking_id;
        this.order_paymentid = order_paymentid;
        this.order_prepaed = order_prepaed;
    }

    public String getOrder_prepaed() {
        return order_prepaed;
    }

    public void setOrder_prepaed(String order_prepaed) {
        this.order_prepaed = order_prepaed;
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
