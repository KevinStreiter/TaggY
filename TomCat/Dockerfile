From tomcat:9.0.6-jre8

MAINTAINER Joel Zimmerli

# Update Apt and then install Nano editor (RUN can be removed)
RUN apt-get update && apt-get install -y \
    nano \
&& mkdir -p /usr/local/tomcat/conf

# Copy to images tomcat path 
ADD web.xml /usr/local/tomcat/conf/ 
ADD obclient.properties /etc/ 
ADD WebClient.properties /etc/ 
ADD hello-world.war /usr/local/tomcat/webapps/
ADD taggy-web-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ 

COPY tomcat-users.xml /usr/local/tomcat/conf/
COPY manager.xml /usr/local/tomcat/conf/Catalina/localhost/