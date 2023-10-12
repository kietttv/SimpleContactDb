package com.example.simplecontactdb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder>{

    private Context context;
    private ArrayList<ModelContact> contactList;
    private DbHelper dbHelper;

    public AdapterContact(Context context, ArrayList<ModelContact> contactList) {
        this.context = context;
        this.contactList = contactList;
        dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_contact_item,parent,false);
        ContactViewHolder vh = new ContactViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ModelContact modelContact = contactList.get(position);
        //get data
        String id = modelContact.getId();
        String image = modelContact.getImage();
        String name = modelContact.getName();
        String phone= modelContact.getPhone();
        String email = modelContact.getEmail();
        String dob = modelContact.getDob();

        //set data in view
        holder.contactName.setText(name);
        if (image.equals("")){
            holder.contactImage.setImageResource(R.drawable.ic_person);
        }else {
            holder.contactImage.setImageURI(Uri.parse(image));
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ContactDetails.class);
                intent.putExtra("contactId",id);
                context.startActivity(intent); // now get data from details Activity
                Toast.makeText(context, "Hello " + modelContact.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        ImageView contactImage;
        TextView contactName;
        RelativeLayout relativeLayout;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            //init view
            contactImage = itemView.findViewById(R.id.contact_image);
            contactName = itemView.findViewById(R.id.contact_name);
            relativeLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
