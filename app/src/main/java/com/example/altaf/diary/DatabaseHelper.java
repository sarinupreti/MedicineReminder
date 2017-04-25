package com.example.altaf.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by HAWONG on 23-Aug-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="med_data.db";
    private static final int DATABASE_VERSION=1;

    Context context;

    private static final String CREATE_TABLE="" +
            "CREATE TABLE MEDDATA" +
            "(" +
            "med_name TEXT PRIMARY KEY," +
            "med_days TEXT," +
            "med_times TEXT" +
            ")";



    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MEDDATA");
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    public long addMedicine(String med_name,String med_days,String med_times)
    {
        long returnValue=0;
        SQLiteDatabase db=null;
        try {
            db = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("med_name", med_name);
            contentValues.put("med_days", med_days);
            contentValues.put("med_times", med_times);

            returnValue = db.insert("MEDDATA", null, contentValues);
        }
        catch (Exception e){
            Log.d("DATABASE",e.getMessage());
        }
        finally {
            db.close();
        }
        return returnValue;
    }

    public long deleteMedicine(String med_name)
    {
        long returnValue=-1;
        SQLiteDatabase db=null;
        try {
            db=this.getWritableDatabase();

            Cursor cursor=db.query("MEDDATA",new String[]{"rowid"},"med_name=?",new String[]{med_name},null,null,null);

            long rowid=-1;
            if(cursor.moveToFirst()){
                    rowid=cursor.getLong(0);
            }


            if(rowid!=-1){
                db.delete("MEDDATA","med_name='"+med_name+"'",null);
                returnValue=rowid;
            }
        }
        catch (Exception e)
        {
            Log.d("DATABASE",e.getMessage());
        }
        finally {
            db.close();
        }

        return returnValue;
    }

    public long getRowIdFromName(String med_name){
        long returnValue=-1;
        SQLiteDatabase db=null;
        try {
            db=this.getWritableDatabase();

            Cursor cursor=db.query("MEDDATA",new String[]{"rowid"},"med_name=?",new String[]{med_name},null,null,null);


            if(cursor.moveToFirst()){
                returnValue=cursor.getLong(0);
            }

        }
        catch (Exception e)
        {
            Log.d("DATABASE",e.getMessage());
        }
        finally {
            db.close();
        }

        return returnValue;
    }
    public ArrayList<Medicine> getAllMeds(){
        ArrayList<Medicine> arrayList=new ArrayList<>();

        SQLiteDatabase db=null;
        try{
            db=this.getWritableDatabase();

            Cursor cursor=db.query("MEDDATA",new String[]{"med_name","med_days","med_times"},null,null,null,null,"med_name ASC");

            if(cursor.moveToFirst()){
                do{
                    Medicine med=new Medicine();
                    med.setMed_name(cursor.getString(0));
                    med.setMed_days(cursor.getString(1));
                    med.setMed_times(cursor.getString(2));

                    arrayList.add(med);

                }while (cursor.moveToNext());
            }

        }
        catch(Exception e){

        }
        finally {
            db.close();
        }


        return arrayList;
    }

    public Medicine getMedFromName(String med_name){
        Medicine med=null;
        SQLiteDatabase db=null;
        try{
            db=this.getWritableDatabase();

            Cursor cursor=db.query("MEDDATA",new String[]{"med_name","med_days","med_times"},"med_name=?",new String[]{med_name},null,null,null);


            if(cursor.moveToFirst()){
                do{
                    med=new Medicine();
                    med.setMed_name(cursor.getString(0));
                    med.setMed_days(cursor.getString(1));
                    med.setMed_times(cursor.getString(2));

                }while (cursor.moveToNext());
            }

        }
        catch(Exception e){
           Log.d("Error",e.getMessage());
        }
        finally {
            db.close();
        }
        return med;
    }

}
