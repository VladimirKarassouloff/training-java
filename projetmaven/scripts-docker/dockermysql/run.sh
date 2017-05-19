#!/bin/sh

#default values
IMAGE="vkarassouloff/mysql"
NAME="computer-database"
PASSWORD="root"
UP=$(pgrep mysql | wc -l)

if [ "$UP" -eq 1 ]; then
    sudo service mysql stop
fi

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
    docker run -d --name=$NAME -e MYSQL_PASSWORD=$PASSWORD -p 3306:3306 $IMAGE
    sleep 5
    mysql -u root -proot -h 127.0.0.1 < 1-SCHEMA.sql
    mysql -u root -proot -h 127.0.0.1 < 2-PRIVILEGES.sql
    mysql -u root -proot -h 127.0.0.1 < 3-ENTRIES.sql
fi
