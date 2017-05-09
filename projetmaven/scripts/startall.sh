#!/bin/bash

docker start tomcat
docker start computer-database
docker start computer-database-prod
./catalina-9080.sh