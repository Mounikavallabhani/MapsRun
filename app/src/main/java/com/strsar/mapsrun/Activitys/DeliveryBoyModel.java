package com.strsar.mapsrun.Activitys;

public class DeliveryBoyModel {
     String book_id;
     String sellers;
     String  users;


    public DeliveryBoyModel(String book_id, String sellers, String users) {
        this.book_id = book_id;
        this.sellers = sellers;
        this.users = users;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getSellers() {
        return sellers;
    }

    public void setSellers(String sellers) {
        this.sellers = sellers;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }
}
