package com.example.simplecontactdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PopupAddNote{
    DbHelper dbHelper;
    private  EditText editTextNote;
    private Button buttonSaveNote;

    public void setDbHelper(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void showPopup(final Activity activity, final int contactId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_add_note, null);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        editTextNote = dialogView.findViewById(R.id.editTextNote);
        buttonSaveNote = dialogView.findViewById(R.id.buttonSaveNote);
        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle saving the note here
                String noteContent = editTextNote.getText().toString();
                // Save the note to your database or wherever you need to save it
                if(dbHelper != null){
                    dbHelper.insertNote(contactId, " " + noteContent);
                }
                // Close the popup
                alertDialog.dismiss();
                //refresh ContactDetails
                ((ContactDetails) activity).loadNotes();
            }
        });
    }
    public void showUpdatePopup(final Activity activity, final int noteId, final String currentNote) {
        // Use the existing layout and components, just change the title and note content
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.popup_add_note, null);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        // Assign current note content to EditText
        editTextNote = dialogView.findViewById(R.id.editTextNote);
        editTextNote.setText(currentNote);

        buttonSaveNote = dialogView.findViewById(R.id.buttonSaveNote);
        buttonSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedNoteContent = editTextNote.getText().toString();
                if(dbHelper != null){
                    // Call the note update function in dbHelper
                    dbHelper.updateNote(noteId, updatedNoteContent);
                }
                alertDialog.dismiss();
                ((ContactDetails) activity).loadNotes();
            }
        });
    }
}
