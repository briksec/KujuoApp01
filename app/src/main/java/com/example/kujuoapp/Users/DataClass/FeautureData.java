package com.example.kujuoapp.Users.DataClass;

import android.widget.ImageView;

public class FeautureData {

    String id,text;
    int bgimage,logo;

    public FeautureData(String id, String text, int bgimage, int logo) {
        this.id = id;
        this.text = text;
        this.bgimage = bgimage;
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBgimage() {
        return bgimage;
    }

    public void setBgimage(int bgimage) {
        this.bgimage = bgimage;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
