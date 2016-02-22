package com.abcd.projetcnam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by julien on 01/05/2015.
 */
public class DbHelperAdapter {
    DbHelper helper;

    ArrayList <String> matiereArray = new ArrayList<>();
    ArrayList <String> heureArray = new ArrayList<>();
    ArrayList <String> accessArray = new ArrayList<>();


    public void insertData(String codeMatiere, String acces, String date){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.MATIERE,codeMatiere);
        contentValues.put(DbHelper.ROOMNUMBER,acces);
        contentValues.put(DbHelper.DATEEXAM,date);
        db.insert(DbHelper.TABLE_NAME,null, contentValues);
    }


    public void updateArrays(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String [] columns = {DbHelper.MATIERE,DbHelper.ROOMNUMBER,DbHelper.DATEEXAM};
        Cursor cursor = db.query(DbHelper.TABLE_NAME, columns, null,null, null, null, null);
        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DbHelper.MATIERE);
            String matiere= cursor.getString(index1);
            matiereArray.add(matiere);
            int index2 = cursor.getColumnIndex(DbHelper.ROOMNUMBER);
            String accesNumber = cursor.getString(index2);
            accessArray.add(accesNumber);
            int index3= cursor.getColumnIndex(DbHelper.DATEEXAM);
            String dateEx = cursor.getString(index3);
            heureArray.add(dateEx);
        }
    }


    public ArrayList<String> getMatiereArray() {
        return matiereArray;
    }

    public ArrayList<String> getHeureArray() {
        return heureArray;
    }

    public ArrayList<String> getAccessArray() {
        return accessArray;
    }

    public DbHelperAdapter(Context context) {
        helper = new DbHelper(context);
    }


    class DbHelper extends SQLiteOpenHelper {

        private final static String DATABASE_NAME = "MYDATABASE";
        private final static String TABLE_NAME = "EXAMEN";
        private final static String UID = "_id";
        private final static String MATIERE = "matiere";
        private final static String ROOMNUMBER = "roomNumber";
        private final static String DATEEXAM = "dateExam";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +MATIERE+" VARCHAR(255), "+ROOMNUMBER+" VARCHAR(255), "+DATEEXAM+" VARCHAR(255));";
        private final static String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
        private final static int DATABASE_VERSION = 1;
        Context context;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null,DATABASE_VERSION);
            this.context = context;
            //Message.message(context, "Constructor called");
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                //Message.message(context,"OnCreate called");
            } catch (SQLException e) {
                //Message.message(context, ""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                //Message.message(context,"OnUpgrade called");
                onCreate(db);
            } catch (SQLException e) {
               // Message.message(context, ""+e);
            }
        }
    }
}

