#!/bin/bash
./mvnw versions:update-properties
./mvnw release:prepare release:perform -B || exit 1
./mvnw package
git commit -a --amend --no-edit
git push -f
