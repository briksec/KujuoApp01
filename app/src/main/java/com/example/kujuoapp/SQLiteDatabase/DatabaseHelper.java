package com.example.kujuoapp.SQLiteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "kujuo.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL("Create table userdata(userid text primary key, walletid text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("Drop table if exists userdata");
    }

    public boolean insertNewUser(String userid , String walletid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        c.put("userid",userid);
        c.put("walletid",walletid);
        long insert = db.insert("userdata",null,c);
        if (insert == -1){
            return false;
        }else {
            return true;
        }

    }

    public boolean updateUser(String userid , String walletid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c  = new ContentValues();
        c.put("userid",userid);
        c.put("walletid",walletid);
        long update = db.update("userdata",c,"userid=? AND walletid=?",new String[]{userid,walletid});
        if (update == -1){
            return false;
        }else {
            return true;
        }
    }
}
