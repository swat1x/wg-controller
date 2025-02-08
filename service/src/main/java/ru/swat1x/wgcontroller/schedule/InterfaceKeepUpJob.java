package ru.swat1x.wgcontroller.schedule;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.swat1x.wgcontroller.service.WireGuardNativeService;
import ru.swat1x.wgcontroller.service.WireGuardInterfaceService;

import java.util.stream.Collectors;

@Slf4j
//@Component
//@Scope("prototype")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InterfaceKeepUpJob implements Job {

    WireGuardNativeService nativeService;
    WireGuardInterfaceService interfaceService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        var currentInterfaces = nativeService.getWorkingInterfaces();
        var activeInterfaces = interfaceService.getActiveInterfaces();

        var activeInterfacesMap = activeInterfaces.stream().collect(Collectors.toMap(v -> v.getSystemName(), v -> v));

        var interfacesToDisable = currentInterfaces
                .stream()
                .filter(in -> !activeInterfacesMap.containsKey(in))
                .toList();

        var interfacesToEnable = activeInterfacesMap.keySet()
                .stream()
                .filter(in -> !currentInterfaces.contains(in))
                .toList();

//        log.info("to disable: {}", interfacesToDisable);
//        log.info("to enable: {}", interfacesToEnable);

        interfacesToDisable.forEach(in -> nativeService.downInterface(in));
        interfacesToEnable.forEach(in -> nativeService.upInterface(in));

    }
}
