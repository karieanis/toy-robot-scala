Toy Robot Simulator
===================

Requirements
------------
- Java 1.8

Testing
-------
Tests are executed via gradle, and can be invoked as follows:
   
### Linux / Mac Terminal

    ./gradlew test
    
### Windows

    gradlew test
    

Building
--------
Building is done using gradle. This project is configured to utilise a provided gradle wrapper for gradle commands, 
irrespective of the OS. The shadow jar plugin is used for building uber jars, similar to the functionality provided by
maven's fat jar implementation.

### Linux / Mac Terminal

    ./gradlew shadowJar
    
### Windows

    gradlew shadowJar
    
By default, an uber jar will be built as build/libs/*-all.jar. For this particular project, the manufactured artefact will
be robot-1.0-SNAPSHOT-all.jar.

Running
-------
There are a couple of options for running the simulator - it can be done either via gradlew, or directly invoking java. By
default, the application is interactive.

### Gradle

    ./gradlew run
    
The following assumes that an uber jar artefact has been manufactured using gradle prior to execution.

### Java

    java -jar /path/to/jar/robot-1.0-SNAPSHOT-all.jar
   
As requested, the application will continue to read from stdin until stdin has been closed. Warnings (such as unprocessable
inputs or invalid commands) will be logged to stderr.