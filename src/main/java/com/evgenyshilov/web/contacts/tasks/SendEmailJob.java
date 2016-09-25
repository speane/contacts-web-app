package com.evgenyshilov.web.contacts.tasks;

import com.evgenyshilov.web.contacts.email.EmailSender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class SendEmailJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        EmailSender emailSender = new EmailSender();
    }
}
