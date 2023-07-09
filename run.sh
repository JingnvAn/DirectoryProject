#!/bin/bash

# Compile the Java classes
javac -d output src/*.java

# Create the JAR file with the manifest
jar cfm output/directory-project.jar manifest.MF -C output .

# Run the Java program using the JAR file
java -jar output/directory-project.jar
