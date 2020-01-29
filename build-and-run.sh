#!/bin/bash

set -e
docker-compose down
mvn clean install

docker-compose -f docker-compose.prod.yml build
docker-compose -f docker-compose.prod.yml up
