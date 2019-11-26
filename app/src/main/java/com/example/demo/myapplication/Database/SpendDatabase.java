package com.example.demo.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.demo.myapplication.Class.Spend;

import java.util.ArrayList;

public class SpendDatabase extends SQLiteOpenHelper {
    public static final  String DATABASE_NAME = "todospenddata";
    public static final  String TABLE_NAME = "todo_table";
    public static final  String ID = "id";
    public static final  String SPEND = "spend";
    public static final  String MONEY = "money";
    static final int DB_VERSION = 1;
    private Context context;
    public SpendDatabase(Context context) {
        super(context, DATABASE_NAME,null, DB_VERSION);
        this.context =  context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE "+TABLE_NAME +" (" +
                ID +" integer primary key , "+
                SPEND + " TEXT, "+
                MONEY +" TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addSpend(Spend spend){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MONEY,spend.getMoney());
        values.put(SPEND,spend.getSpend());
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public ArrayList<Spend> getAllSpend(){
        ArrayList<Spend> listSpend = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Spend spend = new Spend();
                spend.setId(cursor.getInt(0));
                spend.setSpend(cursor.getString(1));
                spend.setMoney(cursor.getString(2));
                listSpend.add(spend);
            }while (cursor.moveToNext());
        }
        return listSpend;
    }
}
