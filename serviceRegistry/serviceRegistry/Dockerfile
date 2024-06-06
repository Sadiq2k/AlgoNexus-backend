FROM openjdk:17

EXPOSE 8761
ADD target/service-registry-algonexus.jar service-registry-algonexus.jar
ENTRYPOINT ["java","-jar","/service-registry-algonexus.jar"]