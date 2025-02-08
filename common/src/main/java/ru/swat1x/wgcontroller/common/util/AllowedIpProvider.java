package ru.swat1x.wgcontroller.common.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public interface AllowedIpProvider {

    // "10.0.0.2, 10.0.0.3"
    String getAllowedIPs();

    default String[] getAllowedIpsArray() {
        Objects.requireNonNull(getAllowedIPs(), "allowedIps is null");
        return getAllowedIPs().replace(" ", "").split(",");
    }

    default String formatIPsWithMask(int mask) {
        return Arrays.stream(getAllowedIpsArray())
                .map(("%s/" + mask)::formatted)
                .collect(Collectors.joining(", "));
    }

}
