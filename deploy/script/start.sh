#!/bin/bash

######################## EAP Container Start ############################
# This file is a default configuration example documenting the
# suggested JDK baslines and AIOps intragtion configurations.

#==========================  EAP JDK configuration =============================
CLOUD_HOME=/usr/local/cloud

#EAP JAVA_OPTS baseline configuration including:
# Diagnostic Options (JFR, OOM, Fatal Error Log)
source ${CLOUD_HOME}/bin/jdk_baseline_config.sh

#==========================  AIOps integration =============================
#EAP Configuration for adding related iAPM component and settings.
source ${CLOUD_HOME}/bin/apm.sh

#EAP configuration for Springboot embedded Tomcat (backport from EAP Standalone Tomcat)
# Logging - Tomcat access log path and pattern for CLAP
# Misc - Compression and connection-timeout settings
export JAVA_OPTS="${JAVA_OPTS} -Dspring.config.additional-location=/eap/config/eap_application.properties"

#==========================  Application start =============================
# Application can append start script and configuration here


#Start JAVA application
echo "Using JAVA_OPTS:"
for arg in $JAVA_OPTS
do
    echo ">> " $arg
done

echo "_______________________________________________"
echo ""

export UMASK=0022

echo "Using UMASK: $UMASK"


#-------Conjur export(Start)-------

echo "Conjur 20240426V2"

echo "CyberArkSafe_secret1: $CyberArkSafe_secret1"
echo "CyberArkAccount_secret1: $CyberArkAccount_secret1"

export conjur_CloudDB_UAM_INTERFACE_RO_password=$(summon-conjur Vault/$CyberArk_LobAccount/$CyberArkSafe_secret1/$CyberArkAccount_secret1/password)

java ${JAVA_OPTS} -jar ${JAR_OPTS} webapps/application.jar