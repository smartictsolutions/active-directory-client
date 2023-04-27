FROM openjdk:8-jdk-alpine
VOLUME /tmp
# Add the application's jar to the container
COPY /*.jar active-directory.jar

RUN mkdir liquibase
COPY  /active_directory_changelog-v1.0.xml /liquibase/active_directory_changelog-v1.0.xml

# openjdk:8-jdk-alpine'da bash yok sh var. docker-compose.yml'da CMD'de bash'a ihtiyacımız var.
RUN apk add --no-cache bash

# İlk başta bu container'ı ENTRYPOINT ile çalıştırıyorduk ancak veritabanının hazır olmasını bekleyebilmek için artık docker-compose.yml'da CMD ile çalıştırıyoruz.
#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/active-directory.jar"]
