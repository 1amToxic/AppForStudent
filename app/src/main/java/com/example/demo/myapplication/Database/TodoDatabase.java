package com.example.demo.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.demo.myapplication.Class.Work;

import java.util.ArrayList;

public class TodoDatabase extends SQLiteOpenHelper {
    public static final  String DATABASE_NAME = "tododata";
    public static final  String TABLE_NAME = "todo_table";
    public static final  String ID = "id";
    public static final  String TASK = "task";
    public static final  String DATE = "date";
    public static final  String TIME = "time";
    static final int DB_VERSION = 1;
    private Context context;
    public TodoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                ID +" integer primary key , "+
                TASK + " TEXT, "+
                DATE +" TEXT, "+
                TIME+" TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addWork(Work work){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK,work.getWork());
        values.put(DATE,work.getDateDL());
        values.put(TIME,work.getTimeDL());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public ArrayList<Work>  getAllWork(){
        ArrayList<Work> listWork = new ArrayList<>();
        String selectQuery ="SELECT * FROM "+TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                Work work = new Work();
                work.setId(cursor.getInt(0));
                work.setWork(cursor.getString(1));
                work.setDateDL(cursor.getString(2));
                work.setTimeDL(cursor.getString(3));
                listWork.add(work);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listWork;
    }
    public void deleteWork(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, ID + " = ?",
                new String[] { String.valueOf(work.getId()) });
        db.close();
    }
}
