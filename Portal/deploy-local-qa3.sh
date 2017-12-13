#!/bin/bash

sudo /etc/init.d/tomcat6 stop
sudo  rm /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war
sudo  rm -rf /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT 
sudo  rm -rf /usr/share/tomcat6/work/ 
sudo  rm /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml
 
sudo mkdir -v -p /usr/share/tomcat6/webapps/compsource-portal-webui-stripes
sudo chmod 755 /usr/share/tomcat6/webapps/compsource-portal-webui-stripes
sudo cp ROOT.war /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war

sudo mkdir -v -p /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes
sudo chmod 755 /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes
sudo cp ROOT.xml /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml

sudo chmod 644 /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war
sudo chmod 644 /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml
sudo /etc/init.d/tomcat6 start
#tail -f -n 150 /usr/share/tomcat6/logs/catalina.out  
