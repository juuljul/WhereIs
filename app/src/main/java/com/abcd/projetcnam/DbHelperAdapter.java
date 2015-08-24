package com.abcd.projetcnam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by julien on 01/05/2015.
 */
public class DbHelperAdapter {
    DbHelper helper;


    public void insertData(String codeMatiere, String acces, String date){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.MATIERE,codeMatiere);
        contentValues.put(DbHelper.ROOMNUMBER,acces);
        contentValues.put(DbHelper.DATEEXAM,date);
        db.insert(DbHelper.TABLE_NAME,null, contentValues);
    }


    /*public String getData(String name){
        SQLiteDatabase db = helper.getWritableDatabase();
        String [] columns = {DbHelper.LOGIN,DbHelper.SCORE};
        Cursor cursor = db.query(DbHelper.OTHER_TABLE, columns, DbHelper.LOGIN+" ='"+name+"'",null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DbHelper.LOGIN);
            String personName= cursor.getString(index1);
            int index2 = cursor.getColumnIndex(DbHelper.SCORE);
            int score = cursor.getInt(index2);
            buffer.append(personName+" "+score+"\n");
        }
        return buffer.toString();
    }*/


    public String getAllData(){
        SQLiteDatabase db = helper.getWritableDatabase();
        String [] columns = {DbHelper.MATIERE,DbHelper.ROOMNUMBER,DbHelper.DATEEXAM};
        Cursor cursor = db.query(DbHelper.TABLE_NAME, columns, null,null, null, null, null);
        StringBuffer buffer = new StringBuffer();
        while(cursor.moveToNext()){
            int index1 = cursor.getColumnIndex(DbHelper.MATIERE);
            String matiere= cursor.getString(index1);
            int index2 = cursor.getColumnIndex(DbHelper.ROOMNUMBER);
            String accesNumber = cursor.getString(index2);
            int index3= cursor.getColumnIndex(DbHelper.DATEEXAM);
            String dateEx = cursor.getString(index3);
            buffer.append(matiere+" "+accesNumber+" "+dateEx+"\n");
        }
        return buffer.toString();
    }

    /*public void updateScore (String name, int newScore){
        SQLiteDatabase db = helper.getWritableDatabase();
        String [] whereArgs = {name};
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbHelper.SCORE,newScore);
        db.update(DbHelper.OTHER_TABLE, contentValues,DbHelper.LOGIN+" =?",whereArgs);
    }*/

    public DbHelperAdapter(Context context) {
        helper = new DbHelper(context);
    }


    class DbHelper extends SQLiteOpenHelper {

        private final static String DATABASE_NAME = "MYDATABASE";
        private final static String TABLE_NAME = "EXAMEN";
        //private final static String OTHER_TABLE = "GAMER";
        private final static String UID = "_id";
        private final static String MATIERE = "matiere";
        private final static String ROOMNUMBER = "roomNumber";
        private final static String DATEEXAM = "dateExam";
        //private final static String LOGIN = "Login";
        //private final static String SCORE = "Score";
        // private final static String CREATE_TABLE = "CREATE TABLE " +TABLE_NAME+"("+LOGIN+ "VARCHAR(255) PRIMARY KEY, "+SCORE+ " INTEGER);";
        //private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+LOGIN+" VARCHAR(255), "+SCORE+" INTEGER);";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +MATIERE+" VARCHAR(255), "+ROOMNUMBER+" VARCHAR(255), "+DATEEXAM+" VARCHAR(255));";
        //private static final String CREATE_OTHER = "CREATE TABLE "+OTHER_TABLE+"("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+LOGIN+" VARCHAR(255) UNIQUE, "+SCORE+" INTEGER);";
        private final static String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
        private final static int DATABASE_VERSION = 1;
        Context context;

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null,DATABASE_VERSION);
            this.context = context;
            Message.message(context, "Constructor called");
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE);
                Message.message(context,"OnCreate called");
            } catch (SQLException e) {
                Message.message(context, ""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DROP_TABLE);
                Message.message(context,"OnUpgrade called");
                onCreate(db);
            } catch (SQLException e) {
                Message.message(context, ""+e);
            }
        }
    }
}

