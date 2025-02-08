package ru.swat1x.wgcontroller.schedule;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;
import ru.swat1x.wgcontroller.service.MetricService;
import ru.swat1x.wgcontroller.service.WireGuardNativeService;
import ru.swat1x.wgcontroller.service.WireGuardInterfaceService;

@Slf4j
//@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MetricsUpdateJob implements Job {

    MetricService metricService;
    WireGuardNativeService nativeService;
    WireGuardInterfaceService interfaceService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        metricService.getTotalInterfacesCount().set(
                interfaceService.getInterfaceRepository()
                        .count()
        );

        metricService.getEnabledInterfacesCount().set(
                interfaceService.getInterfaceRepository()
                        .countAllByEnabled(true)
        );

    }
}
