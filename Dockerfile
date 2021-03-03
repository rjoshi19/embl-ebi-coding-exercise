FROM java:8-jdk-alpine
MAINTAINER https://www.linkedin.com/in/joshiravi9/
COPY target/techtest-persons-0.0.1.jar techtest-persons-0.0.1.jar
ENTRYPOINT ["java","-jar","/techtest-persons-0.0.1.jar"]