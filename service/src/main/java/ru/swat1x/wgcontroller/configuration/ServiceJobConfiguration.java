package ru.swat1x.wgcontroller.configuration;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.swat1x.wgcontroller.schedule.InterfaceKeepUpJob;
import ru.swat1x.wgcontroller.schedule.MetricsUpdateJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class ServiceJobConfiguration {

    @Bean
    public JobDetail metricsUpdateJob() {
        return JobBuilder.newJob()
                .ofType(MetricsUpdateJob.class)
                .storeDurably()
                .withIdentity("metrics_update_job")
                .build();
    }

    @Bean
    public Trigger metricsUpdateTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(metricsUpdateJob())
                .withIdentity("metrics_update_job_trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(15))
                .build();
    }

    @Bean
    @Scope("prototype")
    public JobDetail interfaceKeepUpJob() {
        return JobBuilder.newJob()
                .ofType(InterfaceKeepUpJob.class)
                .storeDurably()
                .withIdentity("interface_keep_up_job")
                .build();
    }

    @Bean
    public Trigger interfaceKeepUpTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(interfaceKeepUpJob())
                .withIdentity("interface_keep_up_trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(5))
                .build();
    }

}
