package com.example.kujuoapp.Users.DataClass;

public class AllTrans {
    	/*'trans_id'=>$row['trans_id'],
            'send_amount'=>$row['send_amount'],
            'sender_message'=>$row['sender_message'],
            'receiver_id'=>$row['receiver_id'],
            'transaction_type'=>$row['transaction_type'],
            'transaction_charges'=>$row['transaction_charges'],
            'trsacted_amount'=>$row['trsacted_amount'],
            'date_time'=>$row['date_time'],
            'rec_name'=>$row['user_name'],
            'rec_pic'=>$row['profile_pic'],
            'rec_phone'=>$row['user_phoneno'],*/
        String id , profile , name , contact , date, time, trasaction,senderMsg,charges,transaction_type,send_amount,recid;

    public AllTrans(String id, String profile, String name, String contact, String date, String time, String trasaction, String senderMsg, String charges, String transaction_type, String send_amount, String recid) {
        this.id = id;
        this.profile = profile;
        this.name = name;
        this.contact = contact;
        this.date = date;
        this.time = time;
        this.trasaction = trasaction;
        this.senderMsg = senderMsg;
        this.charges = charges;
        this.transaction_type = transaction_type;
        this.send_amount = send_amount;
        this.recid = recid;
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

    public String getSenderMsg() {
        return senderMsg;
    }

    public void setSenderMsg(String senderMsg) {
        this.senderMsg = senderMsg;
    }

    public String getCharges() {
        return charges;
    }

    public void setCharges(String charges) {
        this.charges = charges;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getSend_amount() {
        return send_amount;
    }

    public void setSend_amount(String send_amount) {
        this.send_amount = send_amount;
    }

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }
}
