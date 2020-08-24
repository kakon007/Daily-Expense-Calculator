package com.jahid.earncostcal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "dbName.db";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(
                "CREATE TABLE table_earn" +
                        "(id INTEGER PRIMARY KEY,amount INTEGER, purpose TEXT, date TEXT)"
        );




        db.execSQL(
                "CREATE TABLE table_cost" +
                        "(id INTEGER PRIMARY KEY,amount INTEGER, purpose TEXT, date TEXT)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

        db.execSQL("DROP TABLE IF EXISTS table_earn");
        db.execSQL("DROP TABLE IF EXISTS table_cost");
        onCreate(db);

    }


    public void addEarn(int amount, String purpose, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("amount", amount);
        conval.put("purpose", purpose);
        conval.put("date", date);
        db.insert("table_earn", null, conval);

    }



    public void addCost(int amount, String purpose, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues conval = new ContentValues();
        conval.put("amount", amount);
        conval.put("purpose", purpose);
        conval.put("date", date);
        db.insert("table_cost", null, conval);

    }


    ///////////////////////////////////////////
    public Cursor getAllData()
    {//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from my_table"+ " ORDER BY id"+" DESC", null );



        return cursor;
    }


    ///////////////////////////////////////////
    public int checkName(String my_table)
    {//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from my_table ", null );
        int count = res.getCount();


        return count;
    }
    //////////////////////



    ///////////////////////////////////////////
    public int mainBalance()
    {//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cEarn =  db.rawQuery( "select * from table_earn ", null );
        int totalEarn =0;
        while (cEarn.moveToNext())
        {
            int earn = cEarn.getInt(1);
            totalEarn = totalEarn+earn;
        }

        Cursor cCost =  db.rawQuery( "select * from table_cost ", null );
        int totalCost =0;
        while (cCost.moveToNext())
        {
            int cost = cCost.getInt(1);
            totalCost = totalCost+cost;
        }





        return totalEarn-totalCost;
    }
    //////////////////////




    ///////////////////////////////////////////
    public int Total(String table_name)
    {//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cEarn =  db.rawQuery( "select * from "+table_name, null );
        int totalEarn =0;
        while (cEarn.moveToNext())
        {
            int earn = cEarn.getInt(1);
            totalEarn = totalEarn+earn;
        }


        return  totalEarn;



    }
    //////////////////////



    public Cursor getCost()
    {//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from table_cost"+ " ORDER BY id"+" DESC", null );



        return cursor;
    }

    public Cursor getEarn()
    {//hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from table_earn"+ " ORDER BY id"+" DESC", null );



        return cursor;
    }

    ///////////////////////////////////////////
    public void deleteFromTable(String table_name, String id)
    {

        //int myID = Integer.parseInt(id);
        SQLiteDatabase db = this.getWritableDatabase();
        //db.rawQuery( "DELETE from "+table_name+" where id = "+myID, null );

        db.delete(table_name,  "id =" + id, null);
    }
    //////////////////////







}
