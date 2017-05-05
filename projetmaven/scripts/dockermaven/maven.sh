#!/bin/sh
IMAGE="vkarassouloff/maven"
NAME="maven"
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
    docker run --net=$NETWORK -d -it --rm --name=$NAME -v "/home/vkarassouloff/training-java/projetmaven:/usr/src/mymaven" -w /usr/src/mymaven $IMAGE mvn clean package
fi
