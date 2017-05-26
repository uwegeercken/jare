jare
====

Version 0.82

Java Rule Engine source files.

The Rule Engine allows to implement business rules in a central location and outside the code - be it a web application, a standalone application, an ETL tool or any other java based tool - so that, if the rules change the code does not have to change - only the rules change.
That establishes a proper division of responsibilities between business users and IT.

Version 0.73 comes with redesigned classes to setup the rule engine as a client/server application. The rule engine runs in server mode and waits for connections from clients to process data. If you want to output the results of the ruleengine you need to add the apache velocity jar file to the classpath. If you want to store the results in mongodb then you will need the mongodb jdbc jar file and the jongo and jackson jar files.

A web application is available, which allows to compose the rule and action logic in an easy and user friendly way. Very complex scenarios are possible by combining rules with "and" and "or" conditions and by grouping them together.

Additionally, there is also a Plugin available for the popular and highly sophisticated ETL tool from Pentaho called PDI (previously called Kettle). This plugin enables the ETL developer to get rid of the business logic in the ETL process. If the rules change, the code does not need to be touched. This enhances quality and also transparency: the business user usually has no understanding of ETL processes; with the Web application for the maintenance of the rules and the complex rule logic the user has a central place to work with the rules without the need for coding rules and without the need for IT to change the ETL process.

You may download the jar library which contains the sources compiled with Java 1.6.

Please send your feedback and help to enhance the tool.

    Copyright (C) 2008-2017  Uwe Geercken


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

last update: 2017-05-26
