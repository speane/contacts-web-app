package com.evgenyshilov.web.contacts.tasks;

import com.evgenyshilov.web.contacts.database.model.Contact;
import com.evgenyshilov.web.contacts.email.EmailSender;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.exceptions.PropertyNotFoundException;
import com.evgenyshilov.web.contacts.help.LogHelper;
import com.evgenyshilov.web.contacts.help.database.DBHelper;
import com.evgenyshilov.web.contacts.help.email.EmailTemplateElementsFactory;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.ArrayList;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class SendEmailJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            ArrayList<Contact> contactsWithBirthday = getContactsWithBirthday();
            sendEmail(contactsWithBirthday);
        } catch (Exception e) {
            LogHelper.error("Can't execute send email job: ", e);
        }
    }

    private ArrayList<Contact> getContactsWithBirthday() throws CustomException {
        DBHelper helper = new DBHelper();
        ArrayList<Contact> todayBirthdayContacts = new ArrayList<>();
        try {
            for (Contact contact : helper.getContactsFromDAO()) {
                if (contact.getBirthday() != null) {
                    DateTime birthDay = new DateTime(contact.getBirthday().getTime());
                    DateTime today = DateTime.now();
                    if (birthDay.getMonthOfYear() == today.getMonthOfYear() &&
                            birthDay.getDayOfMonth() == today.getDayOfMonth()) {
                        todayBirthdayContacts.add(contact);
                    }
                }
            }
        } catch (CustomException e) {
            throw new CustomException("Can't get contacts with birthday today: ", e);
        }
        return todayBirthdayContacts;
    }

    private void sendEmail(ArrayList<Contact> contactsWithBirthday) throws CustomException {
        try {
            String EMAIL_ADDRESS = ApplicationConfig.getProperty("SCHEDULE_EMAIL_ADDRESS");
            String SUBJECT = ApplicationConfig.getProperty("SCHEDULE_EMAIL_SUBJECT");
            String mailText = getMailText(contactsWithBirthday);

            sendBirthdayEmail(mailText, EMAIL_ADDRESS, SUBJECT);
        } catch (CustomException e) {
            throw new CustomException("Cannot send email: ", e);
        }
    }

    private void sendBirthdayEmail(String mailText,
                                   String address, String subject) throws CustomException {
        EmailSender sender = new EmailSender();
        try {
            LogHelper.info(String.format("Send email on schedule Subject: %s Text: %s", subject, mailText));

            sender.sendEmail(address, mailText, subject);
        } catch (CustomException e) {
            throw new CustomException("Can't send email: ", e);
        }
    }

    private String getMailText(ArrayList<Contact> contactsWithBirthday) throws CustomException {
        EmailTemplateElementsFactory elementsFactory = new EmailTemplateElementsFactory();

        String mailText;
        ST template;
        try {
            if (!contactsWithBirthday.isEmpty()) {
                mailText = getBirthdayTemplate("startLine").render() + "\n";
                for (Contact contact : contactsWithBirthday) {
                    template = getBirthdayTemplate("tempContact");
                    mailText += elementsFactory.buildTemplateWithContactFields(template, contact) + "\n";
                }
            } else {
                mailText = getBirthdayTemplate("noBirthday").render();
            }
            return mailText;
        } catch (PropertyNotFoundException e) {
            throw new CustomException("Cannot generate mail text: ", e);
        }
    }

    private ST getBirthdayTemplate(String name) throws PropertyNotFoundException {
        String ST_GROUP_FILENAME = ApplicationConfig.getProperty("SCHEDULE_TEMPLATE_GROUP_FILENAME");
        STGroup stGroup;
        if (!StringUtils.isEmpty(ST_GROUP_FILENAME)) {
            stGroup = new STGroupFile(ST_GROUP_FILENAME);
        } else {
            throw new PropertyNotFoundException("Can't find schedule template file path property");
        }
        return stGroup.getInstanceOf(name);
    }
}
