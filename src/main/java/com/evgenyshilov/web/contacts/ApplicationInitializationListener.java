package com.evgenyshilov.web.contacts;

import com.evgenyshilov.web.contacts.database.dao.DAOFactory;
import com.evgenyshilov.web.contacts.exceptions.CustomException;
import com.evgenyshilov.web.contacts.help.PropertyFileParser;
import com.evgenyshilov.web.contacts.resources.ApplicationConfig;
import com.evgenyshilov.web.contacts.help.RussianEnglishTranslator;
import com.evgenyshilov.web.contacts.tasks.SendEmailJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;

/**
 * Created by Evgeny Shilov on 28.09.2016.
 */
public class ApplicationInitializationListener implements ServletContextListener {
    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext context = servletContextEvent.getServletContext();
        try {
            setApplicationProperties(context);
            loadRussianLocalization(context);
            initDAOFactory();
            initScheduler();
        } catch (CustomException e) {
            // TODO log exception
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            shutDownScheduler();
        } catch (CustomException e) {
            // TODO log exception
        }
    }

    private void shutDownScheduler() throws CustomException {
        try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            throw new CustomException("Can't shutdown scheduler: ", e);
        }
    }

    private void initScheduler() throws CustomException {
        String JOB_NAME = "EmailJob";
        String JOB_GROUP = "EmailJobGroup";

        try {
            SchedulerFactory factory = new StdSchedulerFactory();
            scheduler = factory.getScheduler();

            JobDetail job = JobBuilder.newJob(SendEmailJob.class)
                    .withIdentity(JOB_NAME, JOB_GROUP)
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(JOB_NAME, JOB_GROUP)
                    .withSchedule(dailyAtHourAndMinute(1, 2))
                    .build();

            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            throw new CustomException("Can't init scheduler: ", e);
        }
    }

    private void loadRussianLocalization(ServletContext context) throws CustomException {
        String LOCALIZATION_PATH = "/WEB-INF/properties/russian.localization";
        try {
            String realPath = context.getRealPath(LOCALIZATION_PATH);
            RussianEnglishTranslator.loadDictionary(new PropertyFileParser().parse(realPath));
        } catch (CustomException e) {
            throw new CustomException("Can't load russian localization: ", e);
        }
    }

    private void initDAOFactory() throws CustomException {
        String CONNECTION_POOL_DATA_SOURCE_URI = "java:comp/env/jdbc/TestDB";
        try {
            DataSource dataSource = (DataSource) new InitialContext().lookup(CONNECTION_POOL_DATA_SOURCE_URI);
            DAOFactory.init(dataSource);
        } catch (NamingException e) {
            throw new CustomException("Can't init DAO factory: ", e);
        }
    }

    private void setApplicationProperties(ServletContext context) throws CustomException {
        String PROPERTY_FILE_NAME = "/WEB-INF/properties/config.properties";
        Properties applicationProperties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(context.getRealPath(PROPERTY_FILE_NAME));
            applicationProperties.load(fileInputStream);
            applicationProperties.setProperty("ROOT_PATH", context.getRealPath("/"));
            ApplicationConfig.initConfig(applicationProperties);
        } catch (FileNotFoundException e) {
            throw new CustomException("Can't create input stream for properties file: ", e);
        } catch (IOException e) {
            throw new CustomException("Can't load properties from file input stream: ", e);
        }
    }
}
