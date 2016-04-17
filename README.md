jare
====

Version 0.77

Java Rule Engine source files.

The Rule Engine allows to implement business rules in a central location and outside the code - be it a web application, a standalone application, an ETL tool or any other java based tool - so that, if the rules change the code does not have to change - only the rules change.

Version 0.73 comes with redesigned classes to setup the rule engine as a client/server application. The rule engine runs in server mode and waits for connections from clients to process data. If you want to output the results of the ruleengine you need to add the apache velocity jar file to the classpath. If you want to store the results in mongodb then you will need the mongodb jdbc jar file and the jongo and jackson jar files.

There is a web application available, which allows to define rules in an easy and quick way. yet very complex scenarios are possible by combining rules with "and" and "or" conditions and by grouping them together.

Additionally, there is also a Plugin available for the popular and highly sophisticated ETL tool from Pentaho called PDI (previously called Kettle). This plugin enables the ETL developer to get rid of the business rules in the ETL process. If the rules change, the code does not need to be touched. This enhances quality and also transparency: the business user usually has no understanding of ETL processes; with the Web application for the maintenance of the rules and the complex rule logic the user has a central place to work with the rules without the need for coding rules and without the need for IT to chenge the ETL process.

You may download the jar library which contains the sources compiled with Java 1.6

Please send your feedback and help to enhance the tool.

    Copyright (C) 2008-2016  Uwe Geercken
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.
    
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    
    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses

uwe geercken
uwe.geercken@web.de

last update: 2016-04-17
