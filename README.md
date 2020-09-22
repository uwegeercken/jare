jare
====

Version 0.95

Java Rule Engine source files.

The Rule Engine allows to implement business rules in a central location and outside the code - be it a web application, a standalone application, an ETL tool or any other java based tool - so that, if the rules change, the code does not have to be changed - the rules are managed externally. That establishes a proper division of responsibilities between business users and IT. Because now IT can focus on designing code and flows and the business user can maintain the business rules (logic) separately. The business logic is maintained centrally so that duplication is avoided, which is a quality factor.

Basically what happens is that some data is run through the business rules engine applying the business rules and the results are compared to what an expected result. The outcome is always a true or false if the data passes or fails the defined business logic. It is also possible to update the data (execute actions) based on the results of the business logic. Two modes are possible: client mode and server mode. In client mode the ruleengine jar library is embedded in your tool or application and the ruleengine is run from within your code. In server mode the ruleengine runs on a server (or in a container) and waits for connections from clients to process data. You application or tool sends data to the server and receives back a result from the server.

A web application is available, which allows to compose the business rules and actions in an easy and user friendly way. Very complex logic is possible by combining rules with "and" and "or" conditions and by grouping them together in groups and subgroups. Groups can also be dependent on other groups and groups may become active or inactive based on a defined date. The web application allows you to run tests against the defined logic you check it.

There is a plugin available for the popular and highly sophisticated Pentaho ETL tool (PDI). This plugin enables the ETL developer to get rid of the business logic in the ETL process.

Another plugin is available for the Tweakstreet Data Integration tool. It is a modern tool which can seemlessly work with nested data structures and handle data as well as functions as values: everything is a value. It has modern concepts, is lean and performant and runs everywhere. Check it out at: https://tweakstreet.io.

The ruleengine itself has been tested to also work with other technologies such as Hadoop and Kafka. There is a separate project available here on GitHub which integrates the ruleengine with Kafka and allows to apply logic in realtime to your data: https://github.com/uwegeercken/kafka-ruleengine.

The maven project generates the ruleengine library. You can also generate a container image using buildah and pushing it to a registry/artifactory. The resulting image runs a ruleengine server using e.g. podman or docker.

example: sudo podman run --name "testserver" --rm -v ./rules/:/opt/jare-server/rules:Z silent1:8082/jare-server:latest

Documentation for the ruleengine, the Business Rules maintenance Web tool and for all available checks and actions
is available at: https://github.com/uwegeercken/rule_maintenance_documentation. There are also presentations and other documentation.


Please send your feedback and help to enhance the tool.

    Copyright (C) 2008-2020  Uwe Geercken


 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.


uwe geercken
uwe.geercken@web.de

last update: 2020-04-17
