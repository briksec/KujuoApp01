package com.example.kujuoapp.Users.DataClass;

public class PopupData {

    String heading,paragraph;

    public PopupData(String heading, String paragraph) {
        this.heading = heading;
        this.paragraph = paragraph;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }
}
