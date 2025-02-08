package ru.swat1x.wgcontroller.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wg-server")

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerConfiguration {

    String serverEndpoint = "localhost";

    int listenPort = 51820;

    String address = "10.0.0.1/24";

    String privateKey;

}
