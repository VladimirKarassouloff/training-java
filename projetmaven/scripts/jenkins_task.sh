#!/bin/bash
PATH_PROJECT="/home/vkarassouloff/.jenkins/workspace/cdb/projetmaven"
PATH_WAR=$PATH_PROJECT"/target/war/"
docker run --net=mynetwork -i --rm --name=maven -v "$PATH_PROJECT:/usr/src/mymaven" -w /usr/src/mymaven vkarassouloff/maven mvn clean package -Denv=prod
docker cp $PATH_WAR/* tomcat:/opt/apache-tomcat-8.0.43/webapps/
exit 0