#!/bin/sh
./mvnw versions:update-properties
./mvnw -q hpi:run -Djava.util.logging.config.file=logging.properties -Djenkins.version=2.401.1 -Denforcer.skip=true
