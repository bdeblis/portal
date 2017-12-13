#!/bin/bash

sudo /etc/init.d/tomcat6 stop
sudo  rm /usr/share/tomcat6/webapps/gwprod1portal/ROOT.war
sudo  rm -rf /usr/share/tomcat6/webapps/gwprod1portal/ROOT 
sudo  rm -rf /usr/share/tomcat6/work/ 
sudo  rm /usr/share/tomcat6/conf/Catalina/gwprod1portal/ROOT.xml
 
sudo mkdir -v -p /usr/share/tomcat6/webapps/gwprod1portal
sudo chmod 755 /usr/share/tomcat6/webapps/gwprod1portal
sudo cp ROOT.war /usr/share/tomcat6/webapps/gwprod1portal/ROOT.war

sudo mkdir -v -p /usr/share/tomcat6/conf/Catalina/gwprod1portal
sudo chmod 755 /usr/share/tomcat6/conf/Catalina/gwprod1portal
sudo cp ROOT.xml /usr/share/tomcat6/conf/Catalina/gwprod1portal/ROOT.xml

sudo chmod 644 /usr/share/tomcat6/webapps/gwprod1portal/ROOT.war
sudo chmod 644 /usr/share/tomcat6/conf/Catalina/gwprod1portal/ROOT.xml
sudo /etc/init.d/tomcat6 start
#tail -f -n 150 /usr/share/tomcat6/logs/catalina.out  
