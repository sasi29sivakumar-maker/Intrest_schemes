package org.example.scheduler;

import org.example.Exception.DataException;
import org.quartz.JobDetail;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;

@WebListener
public class InterestScheduler implements ServletContextListener {

    private static final Logger log =
            LoggerFactory.getLogger(InterestScheduler.class);

    private Scheduler scheduler;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("InterestScheduler listener loaded");

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            JobDetail job = JobBuilder.newJob(Jobs.class)
                    .withIdentity("InterestJob", "interest-group")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("InterestTrigger", "interest-group")
                    .withSchedule(
                            CronScheduleBuilder.cronSchedule("0 10 * * * ?")
                    )
                    .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();

            log.info("Scheduler started at {}", LocalDateTime.now());

        } catch (SchedulerException e) {
            log.error("Scheduler failed to start", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            if (scheduler != null) {
                scheduler.shutdown(true);
                log.info("Scheduler stopped");
            }
        } catch (SchedulerException e) {
            log.error("Scheduler shutdown failed", e);
        }
    }
}