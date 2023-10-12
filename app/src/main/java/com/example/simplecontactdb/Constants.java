package com.example.simplecontactdb;

public class Constants {
    // database or db name
    public static final String DATABASE_NAME = "CONTACT_DB_NEW";
    //database version
    public static final int DATABASE_VERSION = 2;
    // table Users
    public static final String TABLE_NAME = "CONTACT";
    // table column or field name of users table
    public static final String C_ID = "ID";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_NAME = "NAME";
    public static final String C_PHONE = "PHONE";
    public static final String C_EMAIL = "EMAIL";
    public static final String C_DOB = "DOB";

    //table order
    public static final String TABLE_NOTE_NAME = "NOTES";
    // table column or field name of users table
    public static final String N_ID = "ID";
    public static final String N_CONTACT_ID = "CONTACT_ID";
    public static final String N_CONTENT = "CONTENT";

    // query for create USERS table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_IMAGE + " TEXT, "
            + C_NAME + " TEXT, "
            + C_PHONE + " TEXT, "
            + C_EMAIL + " TEXT, "
            + C_DOB + " TEXT"
            + " );";
    // query for create NOTES table
    public static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTE_NAME + "( "
            + N_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + N_CONTACT_ID + " INTEGER, "
            + N_CONTENT + " TEXT, "
            + "FOREIGN KEY ("+ N_CONTACT_ID + ") REFERENCES " + TABLE_NAME + "(" + C_ID + ")"
            +")";
    //pay attention to syntax errors, especially SPACES
}
