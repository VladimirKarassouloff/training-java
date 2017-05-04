#!/bin/bash
docker stop tomcat
docker rm tomcat
docker run -d --net=mynetwork -p 8000:8000 -p 8080:8080 --name="tomcat" tomcat 
