#!/bin/bash

set -e
docker-compose down
mvn clean install

docker-compose build
if [ "$1" = "prod" ]; then
    docker-compose -f docker-compose.yml -f docker-compose.prod.yml up
else
    docker-compose up
fi
