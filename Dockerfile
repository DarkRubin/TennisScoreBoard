FROM tomcat:latest
LABEL authors="Vadim"
ADD TennisScoreBoard-1.0-SNAPSHOT.war /usr/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]