FROM openjdk:11-jre
COPY build/libs/account-calculator.jar ./usr/account-calculator.jar
RUN sh -c 'touch /usr/customer-manager.jar'
ENTRYPOINT ["java", "-Dspring.data.mongodb.uri=mongodb://mongo/local","-Djava.security.egd=file:/dev/./urandom","-jar","/usr/account-calculator.jar"]