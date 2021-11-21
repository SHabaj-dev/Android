package com.asi.sqlimplementation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dataBaseHelper extends SQLiteOpenHelper {
    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String ID_CUSTOMER = "ID";
    public static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";

    public dataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableCustomer = "CREATE TABLE " + CUSTOMER_TABLE + " (" + ID_CUSTOMER + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CUSTOMER_NAME + " TEXT, " + CUSTOMER_AGE + " AGE, " + ACTIVE_CUSTOMER + " BOOL)";

        db.execSQL(createTableCustomer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(CUSTOMER_NAME, customerModel.getName());
        cv.put(CUSTOMER_AGE, customerModel.getAge());
        cv.put(ACTIVE_CUSTOMER, customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public List<CustomerModel> getData(){
        List<CustomerModel> listAll = new ArrayList<CustomerModel>();

        String query = "SELECT * FROM " + CUSTOMER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){

            do{
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean isActive = (cursor.getInt(3) == 1) ? true : false;

                CustomerModel newCustomer = new CustomerModel(customerId, customerName, customerAge, isActive);
                listAll.add(newCustomer);
            }while (cursor.moveToNext());



        }else{

        }
        cursor.close();
        db.close();
        return listAll;
    }
}
