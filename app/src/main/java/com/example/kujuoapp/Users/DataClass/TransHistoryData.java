package com.example.kujuoapp.Users.DataClass;

public class TransHistoryData {

    String id , profile , name , contact , date, time, trasaction;

    public TransHistoryData(String id, String profile
            , String name, String contact, String date, String time, String trasaction) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.trasaction = trasaction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrasaction() {
        return trasaction;
    }

    public void setTrasaction(String trasaction) {
        this.trasaction = trasaction;
    }
}
