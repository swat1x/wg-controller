package ru.swat1x.wgcontroller.service;

import io.prometheus.metrics.core.metrics.Gauge;
import io.prometheus.metrics.exporter.servlet.jakarta.PrometheusMetricsServlet;
import io.prometheus.metrics.instrumentation.jvm.JvmMetrics;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ru.swat1x.wgcontroller.schedule.MetricsUpdateJob;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Service
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MetricService {

    // Статы
    Gauge totalInterfacesCount = Gauge.builder()
            .name("wg_total_interfaces_count")
            .register();

    Gauge enabledInterfacesCount = Gauge.builder()
            .name("wg_enabled_interfaces_count")
            .register();

    public MetricService() {
        JvmMetrics.builder().register();
    }

    @Bean
    public ServletRegistrationBean<PrometheusMetricsServlet> createPrometheusMetricsEndpoint() {
        return new ServletRegistrationBean<>(new PrometheusMetricsServlet(), "/metrics/*");
    }
}
