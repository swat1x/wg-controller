FROM alpine:latest
FROM amazoncorretto:23-alpine
LABEL authors="swat1x"

RUN apk update \
  && apk add --no-cache bash git tzdata \
  iptables ip6tables openrc curl wireguard-tools \
  sudo \
  && apk upgrade

SHELL ["/bin/bash", "-o", "pipefail", "-c"]

WORKDIR /app

ARG wg_net="10.0.0.1"
ARG wg_port="51820"

#ENV DATABASE_USER="root"
#ENV DATABASE_PASSWORD="my_super_secret_passowrd"
#ENV DATABASE_URL="jdbc:mariadb://localhost:3306/wgserver"

#ADD . /src
#ADD . /gradle
#ADD . ./

RUN #mkdir app

#RUN ./gradlew bootJar
COPY ./service/build/libs/service-0.0.1-SNAPSHOT.jar ./app.jar
COPY ./startup.sh ./startup.sh
COPY ./logo.jpg ./logo.jpg


EXPOSE 8080

ENTRYPOINT ["/bin/bash", "startup.sh"]
#ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]