#!/bin/bash

javac --module-path com.adv.java.database \
	com.adv.java.database/module-info.java \
	com.adv.java.database/com/adv/java/database/Database.java \
	com.adv.java.database/com/adv/java/database/dbs/SqlLite.java

javac --module-path com.adv.java.networking \
	com.adv.java.networking/module-info.java \
	com.adv.java.networking/com/adv/java/networking/Library.java \
	com.adv.java.networking/com/adv/java/networking/apis/NYCPLibrary.java

javac --module-path com.adv.java.xml \
	com.adv.java.xml/module-info.java \
	com.adv.java.xml/com/adv/java/xml/XML.java

javac --module-path com.adv.java.application:com.adv.java.database:com.adv.java.networking:com.adv.java.xml \
	com.adv.java.application/module-info.java \
	com.adv.java.application/com/adv/java/application/FinalApp.java 


