FROM openjdk:17
WORKDIR /CreateDirectory
COPY output/directory-project.jar directory-project.jar
ENTRYPOINT ["java","-jar","directory-project.jar"]