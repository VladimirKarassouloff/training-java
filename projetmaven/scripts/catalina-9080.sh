#!/bin/bash
echo "Using tomcat from /home/vkarassouloff/apache-tomcat-8.0.43-9080/bin"
TOMCAT_8=/home/vkarassouloff/apache-tomcat-8.0.43-9080;
$TOMCAT_8"/bin/shutdown.sh";
sleep 1;
echo "Trying to run server ";
$TOMCAT_8"/bin/catalina.sh" "run";
echo "End run catalina";
