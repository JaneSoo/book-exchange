#!/usr/bin/env bash

cd api-gateway
./mvnw package -DskipTests
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/api-gateway-jvm .
cd -

cd book-service
./mvnw package -DskipTests
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/book-service-jvm .
cd -

cd borrow-service
./mvnw package -DskipTests
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/borrow-service-jvm .
cd -

cd user-service
./mvnw package -DskipTests
docker build -f src/main/docker/Dockerfile.jvm -t quarkus/user-service-jvm .
cd -
