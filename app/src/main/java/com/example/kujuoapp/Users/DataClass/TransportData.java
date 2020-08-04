package com.example.kujuoapp.Users.DataClass;

public class TransportData {

    String trans_id,trans_image,trans_name,trans_description,tran_reserverd_day,tran_charges;


    public TransportData(String trans_id, String trans_image, String trans_name, String trans_description, String tran_reserverd_day, String tran_charges) {
        this.trans_id = trans_id;
        this.trans_image = trans_image;
        this.trans_name = trans_name;
        this.trans_description = trans_description;
        this.tran_reserverd_day = tran_reserverd_day;
        this.tran_charges = tran_charges;
    }

    public String getTrans_id() {
        return trans_id;
    }

    public void setTrans_id(String trans_id) {
        this.trans_id = trans_id;
    }

    public String getTrans_image() {
        return trans_image;
    }

    public void setTrans_image(String trans_image) {
        this.trans_image = trans_image;
    }

    public String getTrans_name() {
        return trans_name;
    }

    public void setTrans_name(String trans_name) {
        this.trans_name = trans_name;
    }

    public String getTrans_description() {
        return trans_description;
    }

    public void setTrans_description(String trans_description) {
        this.trans_description = trans_description;
    }

    public String getTran_reserverd_day() {
        return tran_reserverd_day;
    }

    public void setTran_reserverd_day(String tran_reserverd_day) {
        this.tran_reserverd_day = tran_reserverd_day;
    }

    public String getTran_charges() {
        return tran_charges;
    }

    public void setTran_charges(String tran_charges) {
        this.tran_charges = tran_charges;
    }
}
