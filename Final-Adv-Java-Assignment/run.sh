#!/bin/bash

java \
	--class-path .:lib/sqlite-jdbc-3.23.1.jar \
	--module-path com.adv.java.application:com.adv.java.database:com.adv.java.networking:com.adv.java.xml \
	--module com.adv.java.application/com.adv.java.application.FinalApp

