package com.evgenyshilov.web.contacts.tasks;

import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;


/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class SchedulerListener implements ServletContextListener {
    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        SchedulerFactory factory = new StdSchedulerFactory();
        String JOB_NAME = "emailJob";
        String JOB_GROUP = "emailJobGroup";
        try {
            scheduler = factory.getScheduler();
            JobDetail job = JobBuilder.newJob(SendEmailJob.class)
                    .withIdentity(JOB_NAME, JOB_GROUP) // name "myJob", group "group1"
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(JOB_NAME, JOB_GROUP)
                    .withSchedule(dailyAtHourAndMinute(getIntProperty("EMAIL_SEND_HOUR"),
                            getIntProperty("EMAIL_SEND_MINUTE")))
                    .build();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            // TODO log exception
        }
    }

    private int getIntProperty(String name) {
        try {
            String value = ApplicationConfig.getProperty(name);
            return value != null ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            // TODO log exception
        }
        return 0;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            // TODO log exception
        }
    }
}
