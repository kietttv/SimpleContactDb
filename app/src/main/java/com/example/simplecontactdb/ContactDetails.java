package com.example.simplecontactdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simplecontactdb.Constants;
import com.example.simplecontactdb.DbHelper;
import com.example.simplecontactdb.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ContactDetails extends AppCompatActivity {
    private TextView nameTv,phoneTv,emailTv,DoBTv;
    private Button createNoteButton;
    private ImageView profileIv;
    private RecyclerView notesRecyclerView;
    private NoteAdapter noteAdapter;
    private String id;
    private DbHelper dbHelper;
    private ActionBar actionBar;
    int contactId;

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

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        map();
        loadDataById();
        createNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateNoteDialog();
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
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID));
                String name =  ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME));
                String image = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_IMAGE));
                String phone = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE));
                String email = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL));
                String dob = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_DOB));
                //set data
                contactId = id;
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
        ArrayList<ModelNotes> notes = dbHelper.getNotesByContactId(Integer.parseInt(id));
        List<String> contentLst = new ArrayList<>();
        for (ModelNotes note: notes) {
            contentLst.add(note.getContent());
        }
        noteAdapter = new NoteAdapter(contentLst);
        notesRecyclerView.setAdapter(noteAdapter);
        db.close();
    }
    private void showCreateNoteDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_note, null);
        dialogBuilder.setView(dialogView);
        final EditText noteEditText = dialogView.findViewById(R.id.noteEditText);
        final Button submitNoteButton = dialogView.findViewById(R.id.submitNoteButton);
        final AlertDialog alertDialog = dialogBuilder.create();
        submitNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteContent = noteEditText.getText().toString().trim();
                dbHelper.insertNote(contactId, noteContent);
                alertDialog.dismiss(); // Close the dialog after pressing Submit
            }
        });
        alertDialog.show();
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
        createNoteButton = findViewById(R.id.createNoteButton);
    }
}