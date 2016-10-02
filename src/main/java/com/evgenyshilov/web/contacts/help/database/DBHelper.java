package com.evgenyshilov.web.contacts.help.database;

import com.evgenyshilov.web.contacts.database.dao.*;
import com.evgenyshilov.web.contacts.database.model.*;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.LogHelper;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 29.09.2016.
 */
public class DBHelper {
    public void removeContact(long contactId) throws CustomException {
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
                LogHelper.error("Unable to close contact dao: ", e);
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
                LogHelper.error("Unable to close attachment dao: ", e);
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
                LogHelper.error("Unable to close contact dao: ", e);
            }
        }
    }

    public void insertContactPhones(ArrayList<Phone> phones, long id) throws CustomException {
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
                LogHelper.error("Unable to close phone dao: ", e);
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
                LogHelper.error("Unable to close contact dao: ", e);
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
                LogHelper.error("Unable to close marital status dao: ", e);
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
                LogHelper.error("Unable to close phone type dao: ", e);
            }
        }
    }

    public void updatePhone(long id, Phone phone) throws CustomException {
        PhoneDAO phoneDAO = null;
        try {
            phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            phoneDAO.update(id, phone);
        } catch (CustomException e) {
            throw new CustomException("Can't update phone with dao: ", e);
        } finally {
            try {
                if (phoneDAO != null) {
                    phoneDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Unable to close phone dao: ", e);
            }
        }
    }

    public void updateAttachment(long id, Attachment attachment) throws CustomException {
        AttachmentDAO attachmentDAO = null;
        try {
            attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            attachmentDAO.update(id, attachment);
        } catch (CustomException e) {
            throw new CustomException("Can't update attachment: ", e);
        } finally {
            try {
                if (attachmentDAO != null) {
                    attachmentDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Unable to close attachment dao: ", e);
            }
        }
    }

    public void removePhone(long id) throws CustomException {
        PhoneDAO phoneDAO = null;
        try {
            phoneDAO = (PhoneDAO) DAOFactory.getDAO(Phone.class);
            phoneDAO.delete(id);
        } catch (CustomException e) {
            throw new CustomException("Can't remove contact phone: ", e);
        } finally {
            try {
                if (phoneDAO != null) {
                    phoneDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Unable to close phone dao: ", e);
            }
        }
    }

    public void removeAttachment(long id) throws CustomException {
        AttachmentDAO attachmentDAO = null;
        try {
            attachmentDAO = (AttachmentDAO) DAOFactory.getDAO(Attachment.class);
            attachmentDAO.delete(id);
        } catch (CustomException e) {
            throw new CustomException("Can't remove attachment: ", e);
        } finally {
            try {
                if (attachmentDAO != null) {
                    attachmentDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Unable to close attachment dao: ", e);
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
                LogHelper.error("Unable to close attachment dao: ", e);
            }
        }
    }

    public Contact getContactFromDAO(long id) throws CustomException {
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
                LogHelper.error("Unable to close contact dao: ", e);
            }
        }
    }

    public void updateContact(long id, Contact contact) throws CustomException {
        ContactDAO contactDAO = null;
        try {
            contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            contactDAO.update(id, contact);
        } catch (CustomException e) {
            throw new CustomException("Can't update contact with dao: ", e);
        } finally {
            try {
                if (contactDAO != null) {
                    contactDAO.close();
                }
            } catch (SQLException e) {
                LogHelper.error("Unable to close contact dao: ", e);
            }
        }
    }
}
