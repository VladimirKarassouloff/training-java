#!/bin/bash

IMAGE="vkarassouloff/tomcat"
NAME="tomcat"
PASSWORD="root"
PORT="8080"
NETWORK="mynetwork"


if [ "$(docker images -q $IMAGE 2> /dev/null)" = "" ]; then
    echo "pulling the image..."
    docker pull $IMAGE
fi

if [ "$(docker ps -a -f "name=$NAME" --format '{{.Names}}' 2> /dev/null)" = $NAME ]; then
    if [ "$(docker inspect --format="{{.State.Running}}" $NAME 2> /dev/null)" = "false" ]; then
        echo "Starting the container..." 
        docker start $NAME
    else
        echo "Container already running..."
    fi
else
    echo "Creating container..."
    docker run --net=$NETWORK -d --name=$NAME -p 8000:8000 -p $PORT:8080 $IMAGE
fi


