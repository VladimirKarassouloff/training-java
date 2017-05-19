#!/bin/sh
IMAGE="vkarassouloff/maven"
NAME="maven"
NETWORK="mynetwork"

PATHPROJECT="/home/vkarassouloff/.jenkins/workspace/cdb/projetmaven"

if [ "$(docker images -q $IMAGE 2> /dev/null)" = "" ]; then
    echo "pulling the image..."
    docker pull $IMAGE
fi

docker run --net=$NETWORK -it --rm --name=$NAME -v "$PATHPROJECT:/usr/src/mymaven" -w /usr/src/mymaven $IMAGE mvn clean package
