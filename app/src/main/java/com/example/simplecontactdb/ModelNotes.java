package com.example.simplecontactdb;

import java.util.List;

public class ModelNotes {
    private String id;
    private String contactId;
    private String content;

    public ModelNotes() {
        // Empty constructor
    }
//constructor
    public ModelNotes(String id, String contactId, String content) {
        this.id = id;
        this.contactId = contactId;
        this.content = content;
    }
//getter and setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
