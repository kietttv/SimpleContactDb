package com.example.simplecontactdb;

public class Constants {
    // database or db name
    public static final String DATABASE_NAME = "CONTACT_DB";
    //database version
    public static final int DATABASE_VERSION = 1;

    // table name
    public static final String TABLE_NAME = "CONTACT_TABLE";

    // table column or field name
    public static final String C_ID = "ID";
    public static final String C_IMAGE = "IMAGE";
    public static final String C_NAME = "NAME";
    public static final String C_PHONE = "PHONE";
    public static final String C_EMAIL = "EMAIL";
    public static final String C_DOB = "DOB";

    // query for create table
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_IMAGE + " TEXT, "
            + C_NAME + " TEXT, "
            + C_PHONE + " TEXT, "
            + C_EMAIL + " TEXT, "
            + C_DOB + " TEXT"
            + " );";
}
