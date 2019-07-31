package com.example.book_inbeta1;

import android.provider.ContactsContract;

import java.util.Date;

public class Reservation {
    private long id;
    private long id_r;
    private String email;
    private String date;

    public Reservation(long id, long id_r, String email, String date) {
        this.id = id;
        this.id_r = id_r;
        this.email = email;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_r() {
        return id_r;
    }

    public void setId_r(long id_r) {
        this.id_r = id_r;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
