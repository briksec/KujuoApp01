package com.example.kujuoapp.Users;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.widget.Toast;

import com.example.kujuoapp.R;

public class BaseClass {

    public static  String domain="http://app.briksec.com/Kujuo_App/api/";
   // public static  String domain="http://192.168.10.7/kujuoApp/";
    //public static  String domain="http://kujofinancials.com/kujuo_official/";
  //  public static  String domain="https://expired-domain.000webhostapp.com/KujuoApp/";

    public static ProgressDialog progressDialog;

    public  static void progress(Context context)
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    public static void toast(Context context,String msg)
    {
        Toast.makeText(context,msg+"",Toast.LENGTH_SHORT).show();
    }
}
