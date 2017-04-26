#!/bin/bash

if [ -z "TOMCAT_8" ]
then
    echo 'Need to set path variable $TOMCAT_8';
    exit 1;
fi

$TOMCAT_8"/bin/shutdown.sh";
sleep 1;
$TOMCAT_8"/bin/catalina.sh" "jpda" "run";

echo "End run catalina";
