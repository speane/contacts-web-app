package com.evgenyshilov.web.contacts.database.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class Attachment implements Serializable {

    private int id;
    private String filename;
    private Date uploadDate;
    private String commentary;
    private int contactId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
