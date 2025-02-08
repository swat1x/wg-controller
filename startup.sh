#!/bin/bash

echo "------------------------- START ----------------------------"
echo "Starting the WireGuard Dashboard Docker container."

export TOP_PID=$$
export OUT_ADAPTER=$(ip -o -4 route show to default | awk '{print $5}')

jar_name="app.jar"

_startJar() {
  java -jar $jar_name
}

_checkWireguard(){
    if ! command -v wg > /dev/null 2>&1 || ! command -v wg-quick > /dev/null 2>&1
    then
        case "$OS" in
            ubuntu|debian)
                {
                    sudo apt update && sudo apt-get install -y wireguard;
                    printf "\n[WGDashboard] WireGuard installed on %s.\n\n" "$OS";
                } &>> ./log/install.txt
            ;;
            centos|fedora|redhat|rhel|almalinux|rocky)
                {
                    sudo dnf install -y wireguard-tools;
                    printf "\n[WGDashboard] WireGuard installed on %s.\n\n" "$OS";
                } &>> ./log/install.txt
            ;;
            alpine)
                {
                    sudo apk update && sudo apk add wireguard-tools --no-cache;
                    printf "\n[WGDashboard] WireGuard installed on %s.\n\n" "$OS";
                } &>> ./log/install.txt
            ;;
            *)
                printf "[WGDashboard] %s Sorry, your OS is not supported. Currently, the install script only supports Debian-based, Red Hat-based, and Alpine Linux.\n" "$heavy_crossmark"
#                printf "%s\n" "$helpMsg"
                kill $TOP_PID
            ;;
        esac
    else
        printf "[WGDashboard] WireGuard is already installed.\n"
    fi
}

_checkWireguard
_startJar