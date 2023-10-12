package com.example.simplecontactdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView contactRv;
    private AdapterContact adapterContact;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map();
        contactRv.setHasFixedSize(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });
        loadData();
    }

    private void loadData() {
        adapterContact = new AdapterContact(this,dbHelper.getAllData());
        contactRv.setAdapter(adapterContact);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    public void map(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        contactRv = findViewById(R.id.contactRv);
        dbHelper = new DbHelper(this);
    }
}