package com.example.kujuoapp.Users.DataClass;

public class DayData
{
    String id,asub_name,asub_cover,asub_totalmembers,asub_leftmember,asubStartDate,asubEnddate,asub_description,asub_amount,asun_duration;

    public DayData(String id, String asub_name, String asub_cover, String asub_totalmembers, String asub_leftmember, String asubStartDate, String asubEnddate, String asub_description, String asub_amount, String asun_duration) {
        this.id = id;
        this.asub_name = asub_name;
        this.asub_cover = asub_cover;
        this.asub_totalmembers = asub_totalmembers;
        this.asub_leftmember = asub_leftmember;
        this.asubStartDate = asubStartDate;
        this.asubEnddate = asubEnddate;
        this.asub_description = asub_description;
        this.asub_amount = asub_amount;
        this.asun_duration = asun_duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAsub_name() {
        return asub_name;
    }

    public void setAsub_name(String asub_name) {
        this.asub_name = asub_name;
    }

    public String getAsub_cover() {
        return asub_cover;
    }

    public void setAsub_cover(String asub_cover) {
        this.asub_cover = asub_cover;
    }

    public String getAsub_totalmembers() {
        return asub_totalmembers;
    }

    public void setAsub_totalmembers(String asub_totalmembers) {
        this.asub_totalmembers = asub_totalmembers;
    }

    public String getAsub_leftmember() {
        return asub_leftmember;
    }

    public void setAsub_leftmember(String asub_leftmember) {
        this.asub_leftmember = asub_leftmember;
    }

    public String getAsubStartDate() {
        return asubStartDate;
    }

    public void setAsubStartDate(String asubStartDate) {
        this.asubStartDate = asubStartDate;
    }

    public String getAsubEnddate() {
        return asubEnddate;
    }

    public void setAsubEnddate(String asubEnddate) {
        this.asubEnddate = asubEnddate;
    }

    public String getAsub_description() {
        return asub_description;
    }

    public void setAsub_description(String asub_description) {
        this.asub_description = asub_description;
    }

    public String getAsub_amount() {
        return asub_amount;
    }

    public void setAsub_amount(String asub_amount) {
        this.asub_amount = asub_amount;
    }

    public String getAsun_duration() {
        return asun_duration;
    }

    public void setAsun_duration(String asun_duration) {
        this.asun_duration = asun_duration;
    }
}
