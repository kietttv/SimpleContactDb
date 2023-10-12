package com.example.simplecontactdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.NotesViewHolder>{
    private Context context;
    private ArrayList<ModelNotes> notes;
    private DbHelper dbHelper;

    public AdapterNote(Context context, ArrayList<ModelNotes> notes) {
        this.context = context;
        this.notes = notes;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public AdapterNote.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false);
        AdapterNote.NotesViewHolder vh = new AdapterNote.NotesViewHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterNote.NotesViewHolder holder, int position) {
        ModelNotes modelNotes = notes.get(position);
        String id = modelNotes.getId();
        String contactId = modelNotes.getContactId();
        String content = modelNotes.getContent();

        holder.noteTextView.setText(content);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView noteTextView;
        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTextView = itemView.findViewById(R.id.noteTextView);
        }
    }
}
