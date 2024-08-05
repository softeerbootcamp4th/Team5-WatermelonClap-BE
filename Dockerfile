FROM  openjdk:17
COPY ./build/libs/server-0.0.1-SNAPSHOT.jar watermelon.jar

ENTRYPOINT ["java","-jar","/watermelon.jar"]