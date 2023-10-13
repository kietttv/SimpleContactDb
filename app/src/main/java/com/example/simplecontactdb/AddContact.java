package com.example.simplecontactdb;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simplecontactdb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddContact extends AppCompatActivity {

    private ImageView profileIv;
    private EditText nameEt,phoneEt,emailEt,dobEt;
    private FloatingActionButton fab;

    private String id,image,name,phone,email,dob;
    private DbHelper dbHelper;
    private ActionBar actionBar;

    private static final int STORAGE_PERMISSION_CODE = 200;
    private static final int IMAGE_FROM_GALLERY_CODE = 300;

    private String[] storagePermission;

    private Uri imageUri = Uri.parse("android.resource://com.example.simplecontactdb/" + R.drawable.ic_person); //storage/emulated/0/Pictures


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        map();

        storagePermission = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Add User");


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });
    }

    private void pickFromGallery() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), IMAGE_FROM_GALLERY_CODE);
    }

    private void saveData() {
        name = nameEt.getText().toString();
        phone = phoneEt.getText().toString();
        email = emailEt.getText().toString();
        //cần sửa
        dob = dobEt.getText().toString();

        if (!name.isEmpty() || !phone.isEmpty() || !email.isEmpty() || !dob.isEmpty()){
            long id =  dbHelper.insertContact(
                    ""+imageUri,
                    ""+name,
                    ""+phone,
                    ""+email,
                    ""+dob
                );
                toast("Inserted Successfully.... "+id);
            Intent intent = new Intent(AddContact.this, MainActivity.class);
            startActivity(intent);
        }else {
            toast("Please enter full fill before save");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == IMAGE_FROM_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1,1)
                        .start(AddContact.this);
            }else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                imageUri = result.getUri();
                profileIv.setImageURI(imageUri);
            }
        }
    }

    public void map(){
        profileIv = (ImageView) findViewById(R.id.profileIv);
        nameEt = (EditText) findViewById(R.id.nameEt);
        phoneEt = (EditText) findViewById(R.id.phoneEt);
        emailEt = (EditText) findViewById(R.id.emailEt);
        dobEt = (EditText) findViewById(R.id.dobEt);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        dbHelper = new DbHelper(this);
    }

    public void toast(String mess){
        Toast.makeText(getApplicationContext(), mess, Toast.LENGTH_SHORT).show();
    }
}