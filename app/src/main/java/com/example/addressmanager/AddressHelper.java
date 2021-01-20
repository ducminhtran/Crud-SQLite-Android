package com.example.addressmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AddressHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "address.db";
    private static final int SCHEMA_VERSION = 1;

    public AddressHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE address (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, address TEXT, type TEXT, zip TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Boolean insert(String name, String address, String type, String zip) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("address", address);
        cv.put("type", type);
        cv.put("zip", zip);
        long result = db.insert("address",null,cv);
        if(result>0)
            return true;
        else
            return false;
    }

    public Boolean update(String name, String address, String type, String zip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("address", address);
        cv.put("type", type);
        cv.put("zip", zip);
        long result = db.update("address",cv, "name=?", new String[] {name});
        if(result>0)
            return true;
        else
            return false;
    }

    public Boolean delete(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        long result = db.delete("address", "name=?", new String[] {name});
        if(result>0)
            return true;
        else
            return false;
    }

    public Cursor getAll() {
        Cursor cur;
        cur = getReadableDatabase().rawQuery("SELECT _id, name, address, type, zip FROM address ORDER BY name", null);
        return (cur);
    }

    public String getName(Cursor c) {
        return (c.getString(1));
    }

    public String getAddress(Cursor c) {
        return (c.getString(2));
    }

    public String getType(Cursor c) {
        return (c.getString(3));
    }

    public String getZip(Cursor c) {
        return (c.getString(4));
    }
}
