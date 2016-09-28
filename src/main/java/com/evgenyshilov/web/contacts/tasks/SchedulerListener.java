package com.evgenyshilov.web.contacts.tasks;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * Created by Evgeny Shilov on 25.09.2016.
 */
public class SchedulerListener implements ServletContextListener {
    //private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        /*SchedulerFactory factory = new StdSchedulerFactory();
        try {
            scheduler = factory.getScheduler();
            JobDetail job = JobBuilder.newJob(SendEmailJob.class)
                    .withIdentity("myJob", "group1") // name "myJob", group "group1"
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("myJob", "group1")
                    .withSchedule(dailyAtHourAndMinute(1, 2))
                    .build();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        /*try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }*/
    }
}
