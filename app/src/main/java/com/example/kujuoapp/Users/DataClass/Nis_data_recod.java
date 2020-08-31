package com.example.kujuoapp.Users.DataClass;

public class Nis_data_recod
{
  /*  $temp=[
        'trans_nis_id'=>$row['trans_nis_id'],
        'send_amount'=>$row['send_amount'],
        'transaction_charges'=>$row['transaction_charges'],
        'receiver_phoneno'=>$row['receiver_phoneno'],
        'receiver_nis'=>$row['receiver_nis'],
        'receiver_code'=>$row['receiver_code'],
        'receive_status'=>$row['receive_status'],
        'datetime'=>$row['datetime'],
        ];*/
  String trans_nis_id,send_amount,transaction_charges,receiver_phoneno,receiver_nis,receiver_code,receive_status,datetime;

    public Nis_data_recod(String trans_nis_id, String send_amount, String transaction_charges, String receiver_phoneno, String receiver_nis, String receiver_code, String receive_status, String datetime) {
        this.trans_nis_id = trans_nis_id;
        this.send_amount = send_amount;
        this.transaction_charges = transaction_charges;
        this.receiver_phoneno = receiver_phoneno;
        this.receiver_nis = receiver_nis;
        this.receiver_code = receiver_code;
        this.receive_status = receive_status;
        this.datetime = datetime;
    }

    public String getTrans_nis_id() {
        return trans_nis_id;
    }

    public void setTrans_nis_id(String trans_nis_id) {
        this.trans_nis_id = trans_nis_id;
    }

    public String getSend_amount() {
        return send_amount;
    }

    public void setSend_amount(String send_amount) {
        this.send_amount = send_amount;
    }

    public String getTransaction_charges() {
        return transaction_charges;
    }

    public void setTransaction_charges(String transaction_charges) {
        this.transaction_charges = transaction_charges;
    }

    public String getReceiver_phoneno() {
        return receiver_phoneno;
    }

    public void setReceiver_phoneno(String receiver_phoneno) {
        this.receiver_phoneno = receiver_phoneno;
    }

    public String getReceiver_nis() {
        return receiver_nis;
    }

    public void setReceiver_nis(String receiver_nis) {
        this.receiver_nis = receiver_nis;
    }

    public String getReceiver_code() {
        return receiver_code;
    }

    public void setReceiver_code(String receiver_code) {
        this.receiver_code = receiver_code;
    }

    public String getReceive_status() {
        return receive_status;
    }

    public void setReceive_status(String receive_status) {
        this.receive_status = receive_status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
