package com.example.simplecontactdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simplecontactdb.Constants;
import com.example.simplecontactdb.DbHelper;
import com.example.simplecontactdb.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ContactDetails extends AppCompatActivity {
    private TextView nameTv,phoneTv,emailTv,DoBTv;
    private ImageView profileIv;
    private String id;
    private DbHelper dbHelper;
    private ActionBar actionBar;
    private RecyclerView notesRv;
    private AdapterNote adapternote;
    private Button BtnAddNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        //init db
        dbHelper = new DbHelper(this);
        //get data from intent
        Intent intent = getIntent();
        id = intent.getStringExtra("contactId");
        //init action bar
        actionBar = getSupportActionBar();
        //back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("User Details");

        map();
        loadDataById();
        loadNotes();
        //Display popup
        BtnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new popup
                PopupAddNote popup = new PopupAddNote();
                //Set context cho DbHelper, DbHelper can't null
                popup.setDbHelper(new DbHelper(ContactDetails.this));
                // Display popup when BtnAddNote button clicked
                popup.showPopup(ContactDetails.this, Integer.parseInt(id));
            }
        });
    }
    private void loadDataById() {
        String selectQuery =  "SELECT * FROM "+ Constants.TABLE_NAME + " WHERE " + Constants.C_ID + " =\"" + id + "\"";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor.moveToFirst()){
            do {
                //get data
                String name =  ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String image = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
                String phone = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                String email = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String dob = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DOB));
                //set data
                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                DoBTv.setText(dob);
                if (image.equals("null")){
                    profileIv.setImageResource(R.drawable.ic_person);
                }else {
                    profileIv.setImageURI(Uri.parse(image));
                }
            }while (cursor.moveToNext());
        }
        db.close();
    }

    public void loadNotes(){
        adapternote = new AdapterNote(this, dbHelper.getNotesByContactId(Integer.parseInt(id)),this);
        notesRv.setAdapter(adapternote);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void map(){
        //init view
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        DoBTv = findViewById(R.id.DoBTv);
        profileIv = findViewById(R.id.profileIv);
        notesRv = findViewById(R.id.notesRv);
        BtnAddNote = findViewById(R.id.BtnAddNote);
    }

}