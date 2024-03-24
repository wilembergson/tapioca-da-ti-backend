FROM tomcat:10-jdk17-openjdk-slim

RUN apt-get update &&  apt-get install -y maven

WORKDIR /usr/local/tomcat/webapps

COPY . .
#COPY pom.xml .
#COPY src ./src
RUN mvn package -DskipTests

# Verifica se o arquivo WAR foi gerado corretamente
RUN ls -l /usr/local/tomcat/webapps/target/*.war

# Copia o arquivo WAR gerado para o diretório do Tomcat
RUN cp /usr/local/tomcat/webapps/target/*.war tapiocadati.war

#COPY target/*.war /usr/local/tomcat/webapps/tapiocadati.war

EXPOSE 8080

CMD ["catalina.sh", "run"]