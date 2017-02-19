#!/bin/bash

##
## Setup
##
#echo Setting up Maven
#mkdir -p ~/.m2
#[ -f ~/.m2/settings.xml.backup ] || cp ~/.m2/settings.xml ~/.m2/settings.xml.backup
#cp sandbox/settings.xml ~/.m2/settings.xml

##
## Build plugin
##
echo Building plugin
mvn -q package
