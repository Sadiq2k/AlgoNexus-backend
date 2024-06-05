FROM openjdk:17
EXPOSE 8085

ADD target/config-server-algonexus.jar config-server-algonexus.jar
ENTRYPOINT ["java","-jar","/config-server-algonexus.jar"]