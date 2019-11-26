package com.example.demo.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.demo.myapplication.Class.NoteLearn;

import java.util.ArrayList;

public class NoteDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DB_NAME = "notedb";
    private static final String TB_NAME = "tablenote";
    private static final int VERSION = 1;
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";
    public NoteDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlQuery = "CREATE TABLE "+ TB_NAME +"(" +
                ID +" integer primary key , "+
                TITLE + " TEXT, "+
                CONTENT +" TEXT)";
        sqLiteDatabase.execSQL(sqlQuery);
        Toast.makeText(context, "Create successfylly", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +TB_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addNote(NoteLearn note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TITLE,note.getTitle());
        values.put(CONTENT,note.getContent());
        db.insert(TB_NAME,null,values);
        db.close();
    }
    public ArrayList<NoteLearn> getAllNote(){
        ArrayList<NoteLearn> arrayNote = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM "+TB_NAME;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do {
                NoteLearn note = new NoteLearn(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                arrayNote.add(note);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayNote;
    }
    public void deleteNote(NoteLearn note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_NAME, ID + " = ?",
                new String[] { String.valueOf(note.getId()) });
        db.close();
    }
}
