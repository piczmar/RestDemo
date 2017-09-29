Simple SpringBoot demo with different profiles

The app contains properties per profiles:
- default (if no profile specified): application.properties
- dev : application-dev.properties

It's built as a WAR file which is to be deployed on Tomcat.

To allow profile specification on Tomcat you need to modify $TOMCAT_HOME/bin/catalina.sh (or .bat if you use Windows) and add JVM parameter like this in the top of the file:


```
JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=dev"
```

This will run Tomcat with Spring Boot profile DEV enabled.

How to verify it in the project?

Just invoke the rest endpoint /demo

Without profile specified it will response:

```
{"message":"Hello there!!"}
```

in `dev` profile it will response

```
{"message":"Hello there, DEVs!!"}
```

This example also illustrates how to inject a property from file to Java class (DemoController)

