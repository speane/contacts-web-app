package com.evgenyshilov.web.contacts.help;

import com.evgenyshilov.web.contacts.database.dao.*;
import com.evgenyshilov.web.contacts.database.model.*;
import com.evgenyshilov.web.contacts.exceptions.CustomException;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class DBHelper {
    public void removeContact(int contactId) throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            contactDAO.delete(contactId);
        } catch (CustomException e) {
            throw new CustomException("Can't remove contact from dao: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public int insertContactAttachment(Attachment attachment) throws CustomException {
        AttachmentDAO attachmentDAO = null;
        try {
            attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            attachmentDAO.insert(attachment);
            return attachmentDAO.getLastInsertId();
        } catch (CustomException e) {
            throw new CustomException("Can't insert contact attachment: ", e);
        } finally {
            try {
                if (attachmentDAO != null) {
                    attachmentDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public int insertContact(Contact contact) throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            contactDAO.insert(contact);
            return contactDAO.getLastInsertedId();
        } catch (CustomException e) {
            throw new CustomException("Can't insert contact: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public void insertContactPhones(ArrayList<Phone> phones, int id) throws CustomException {
        PhoneDAO phoneDAO = null;
        try {
            phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            for (Phone phone : phones) {
                phone.setContactId(id);
                phoneDAO.insert(phone);
            }
        } catch (CustomException e) {
            throw new CustomException("Can't insert contact phones: ", e);
        } finally {
            try {
                if (phoneDAO != null) {
                    phoneDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public ArrayList<Contact> getContactsFromDAO() throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            return contactDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get contacts from DAO: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public ArrayList<MaritalStatus> getMaritalStatuses() throws CustomException {
        MaritalStatusDAO maritalStatusDAO = null;
        try {
            maritalStatusDAO = (MaritalStatusDAO) DAOFactory.getDAO(MaritalStatus.class);
            return maritalStatusDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get all marital statuses from dao: ", e);
        } finally {
            try {
                if (maritalStatusDAO != null) {
                    maritalStatusDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public ArrayList<PhoneType> getPhoneTypes() throws CustomException {
        PhoneTypeDAO phoneTypeDAO = null;
        try {
            phoneTypeDAO = (PhoneTypeDAO) DAOFactory.getDAO(PhoneType.class);
            return phoneTypeDAO.getAll();
        } catch (CustomException e) {
            throw new CustomException("Can't get all phone types from dao: ", e);
        } finally {
            try {
                if (phoneTypeDAO != null) {
                    phoneTypeDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public int insertAttachment(Attachment attachment) throws CustomException {
        AttachmentDAO attachmentDAO = null;
        try {
            attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            attachmentDAO.insert(attachment);
            return attachmentDAO.getLastInsertId();
        } catch (CustomException e) {
            throw new CustomException("Can't insert attachment with dao: ", e);
        } finally {
            try {
                if (attachmentDAO != null) {
                    attachmentDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }

    public Contact getContactFromDAO(int id) throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            return contactDAO.get(id);
        } catch (CustomException e) {
            throw new CustomException("Can't get contact from dao: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                // TODO log exception
            }
        }
    }
}
