package com.example.simplecontactdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table on database
        db.execSQL(Constants.CREATE_TABLE);//create table users
        db.execSQL(Constants.CREATE_TABLE_NOTES);//create table NOTES
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //upgrade db when change
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NAME);//upgrade users table
        db.execSQL("DROP TABLE IF EXISTS "+Constants.TABLE_NOTE_NAME);//upgrade NOTES table
        onCreate(db);
    }

    // Insert Function to insert CONTACT data in database
    public long insertContact(String image,String name,String phone,String email,String dob){
        //get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();
        // create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();
        // id will save automatically as we write query
        contentValues.put(Constants.C_IMAGE,image);
        contentValues.put(Constants.C_NAME,name);
        contentValues.put(Constants.C_PHONE,phone);
        contentValues.put(Constants.C_EMAIL,email);
        contentValues.put(Constants.C_DOB,dob);
        //insert data in row, It will return id of record
        long id = db.insert(Constants.TABLE_NAME,null,contentValues);
        // close db
        db.close();
        //return id
        return id;
    }
//get all data of CONTACT
    public ArrayList<ModelContact> getAllData(){
        //create arrayList
        ArrayList<ModelContact> arrayList = new ArrayList<>();
        //sql command query
        String selectQuery = "SELECT * FROM "+Constants.TABLE_NAME;
        //get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        // looping through all record and add to list
        if (cursor.moveToFirst()){
            do {
                ModelContact modelContact = new ModelContact(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DOB))
                );
                arrayList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }
    // Insert Function to insert NOTE data in database
    public long insertNote(int contactId, String content){
        //get writable database to write data on db
        SQLiteDatabase db = this.getWritableDatabase();
        // create ContentValue class object to save data
        ContentValues contentValues = new ContentValues();
        //id auto
        contentValues.put(Constants.N_CONTACT_ID, contactId);
        contentValues.put(Constants.N_CONTENT, content);
        //insert data in row, It will return id of record
        long id = db.insert(Constants.TABLE_NOTE_NAME,null,contentValues);
        //close db
        db.close();
        // return id
        return id;
    }
    // Update note
    public int updateNote(int noteId, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.N_CONTENT, content);
        // Updating row
        return db.update(Constants.TABLE_NOTE_NAME, values, Constants.N_ID + " = ?",
                new String[]{String.valueOf(noteId)});
    }
    // Delete a note
    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NOTE_NAME, Constants.N_ID + " = ?",
                new String[]{String.valueOf(noteId)});
        db.close();
    }
    // Delete note by contactId
    public void deleteNoteByContactId(int contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NOTE_NAME, Constants.N_CONTACT_ID + " = ?",
                new String[]{String.valueOf(contactId)});
        db.close();
    }
    //get notes by contactId
    public ArrayList<ModelNotes> getNotesByContactId(int ContactId){
        ArrayList<ModelNotes> notes = new ArrayList<>();
        //sql select query
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NOTE_NAME +
                " WHERE " + Constants.N_CONTACT_ID + " = ?";
        //get readable db to read data on db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(ContactId)});
        // looping through all record and add to list
        if(cursor.moveToFirst()){
            do{
                //create new note obj
                ModelNotes note = new ModelNotes();
                //set note properties
                note.setId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(Constants.N_ID))));
                note.setContactId(String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow(Constants.N_CONTACT_ID))));
                note.setContent(cursor.getString(cursor.getColumnIndexOrThrow(Constants.N_CONTENT)));
                //add to list
                notes.add(note);
            }while (cursor.moveToNext());
        }
        //remember that always close db
        //close db
        db.close();
        //debug
//        System.out.println("note size: " + notes.size());
        //return notes
        return notes;
    }
}
