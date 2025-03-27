FROM bitnami/wildfly:33.0.2
EXPOSE 8080
COPY target/javalab_docker-1.0-SNAPSHOT.war /opt/bitnami/wildfly/standalone/deployments