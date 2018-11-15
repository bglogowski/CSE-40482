#!/bin/bash

javac --module-path ch15.sec03 \
	ch15.sec03/module-info.java \
	ch15.sec03/com/horstmann/hello/*.java 

javac --module-path ch15.sec04 \
	ch15.sec04/module-info.java \
	ch15.sec04/com/horstmann/hello/*.java 

javac --module-path com.horstmann.greet \
	com.horstmann.greet/module-info.java \
	com.horstmann.greet/com/horstmann/greet/*.java \
	com.horstmann.greet/com/horstmann/greet/internal/*.java

javac --module-path ch15.sec05:com.horstmann.greet \
	ch15.sec05/module-info.java \
	ch15.sec05/com/horstmann/hello/*.java 

