#!/bin/bash

# path and name of the properties file for the ruleengine server
propertiesfile="server.properties"

# required jar libraries
jarfiles="lib/*"

# run the rule engine socket server
java -classpath "${jarfiles}" com.datamelt.server.RuleEngineServer "${propertiesfile}"
