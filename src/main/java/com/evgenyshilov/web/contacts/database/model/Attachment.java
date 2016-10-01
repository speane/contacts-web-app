package com.evgenyshilov.web.contacts.database.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by Evgeny Shilov on 18.09.2016.
 */
public class Attachment implements Serializable {

    private Long id;
    private String filename;
    private Date uploadDate;
    private String commentary;
    private Long contactId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }
}
