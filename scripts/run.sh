#!/bin/bash

# List of service names
services=("api-gateway" "ms-auth" "ms-user" "ms-order")

# Gradle build
echo "Building Spring Boot Application with Gradle"
./gradlew clean build

# Check if gradle build was successful
if [ $? -eq 0 ]
then
  echo "Gradle build successful, proceeding to Docker image validation and removal"
else
  echo "Gradle build failed, stopping script"
  exit 1
fi

# Check and remove Docker images for each service
for service in "${services[@]}"
do
  if [[ $(docker image ls | grep $service) ]]
  then
    echo "Docker image for '$service' found, removing it"
    docker rmi -f $(docker image ls | grep $service | awk '{print $3}')

    # Check if docker rmi command was successful
    if [ $? -eq 0 ]
    then
      echo "Docker image for '$service' removed successfully"
    else
      echo "Failed to remove Docker image for '$service', stopping script"
      exit 1
    fi
  else
    echo "Docker image for '$service' not found"
  fi
done

echo "Docker image validation and removal complete, proceeding to Docker Compose validation"

# Docker Compose file validation
echo "Validating docker-compose.yml file"
docker-compose config

# Check if docker-compose config was successful
if [ $? -eq 0 ]
then
  echo "Docker Compose file validation successful, proceeding to start Docker containers"
else
  echo "Docker Compose file validation failed, stopping script"
  exit 1
fi

# Docker compose up
echo "Starting Docker containers with Docker Compose"
docker-compose up --build
