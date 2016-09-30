package com.evgenyshilov.web.contacts.tasks;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class SendEmailJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        /*EmailSender emailSender = new EmailSender();
        try {
            ContactDAO contactDAO = (ContactDAO) DAOFactory.getDAO(Contact.class);
            ArrayList<Contact> todayBirthDayContacts = new ArrayList<>();
            for (Contact contact : contactDAO.getAll()) {
                DateTime birthDay = new DateTime(contact.getBirthday().getTime());
                DateTime today = DateTime.now();
                System.out.println(birthDay + " " + today);
                if (birthDay.getMonthOfYear() == today.getMonthOfYear() &&
                        birthDay.getDayOfMonth() == today.getDayOfMonth()) {
                    todayBirthDayContacts.add(contact);
                }
            }
            String mailText;
            System.out.println("228");
            if (todayBirthDayContacts.size() == 0) {
                mailText = "Сегодня никто не празднует день рождения =(";
            } else {
                mailText = "Сегодня день рождения у \n";
                for (Contact contact : todayBirthDayContacts) {
                    mailText += String.format("%s %s\n", contact.getLastName(), contact.getFirstName());

                }
            }
            System.out.println(mailText);
            emailSender.sendEmail("avtolubitel225@gmail.com", mailText);
        } catch (IllegalAccessException | InstantiationException | SQLException | NoSuchMethodException | InvocationTargetException | MessagingException e) {
            e.printStackTrace();
        }*/
    }
}
