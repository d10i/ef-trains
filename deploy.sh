#!/bin/bash
scp -i ~/.ssh/eftrains.pem configuration.yml ec2-user@ec2-54-77-127-14.eu-west-1.compute.amazonaws.com:/etc/ef-trains/configuration.yml
scp -i ~/.ssh/eftrains.pem target/ef-trains-1.0-SNAPSHOT.jar  ec2-user@ec2-54-77-127-14.eu-west-1.compute.amazonaws.com:/etc/ef-trains/ef-trains-1.0-SNAPSHOT.jar