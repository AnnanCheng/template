#!/bin/bash

set -e
cd migration
mvn clean install -P flyway
