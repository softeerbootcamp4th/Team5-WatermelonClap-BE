FROM  openjdk:17
COPY ./build/libs/server-0.0.1-SNAPSHOT.jar watermelon.jar

ENV TZ=Asia/Seoul
ENV SPRING_PROFILES_ACTIVE=deploy
ENTRYPOINT ["java","-jar","/watermelon.jar"]