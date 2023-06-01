FROM openjdk
COPY . app/management
WORKDIR app/management
ENTRYPOINT ["java","-jar","target/management-1.0.jar"]
