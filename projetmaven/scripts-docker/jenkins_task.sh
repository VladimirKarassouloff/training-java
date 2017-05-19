#!/bin/bash
PATH_PROJECT="/home/vkarassouloff/.jenkins/workspace/cdb/projetmaven"
PATH_WAR=$PATH_PROJECT"/target/war/"
M2="/home/vkarassouloff/.m2"
BUILDER="maven:3.3-jdk-8"



if docker run --net=mynetwork -i --rm -v "$PATH_PROJECT:/usr/src/cdb" -v "$M2:/root/.m2" -w /usr/src/cdb $BUILDER mvn clean test -Denv=test; 
then
    echo "Test succeeded"
    docker run --net=mynetworkprod -i --rm -v "$PATH_PROJECT:/usr/src/cdb" -v "$M2:/root/.m2" -w /usr/src/cdb $BUILDER mvn clean package -Denv=prod
    docker cp $PATH_WAR/* tomcat:/opt/apache-tomcat-8.0.43/webapps/
    exit 0
else    
    echo "Test failed"
    exit 1
fi



