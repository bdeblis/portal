#!/bin/bash
if [[ $# -ne 1 ]] ; then
     "Invalid number of arguments supplied: deploy-prod-portal.sh buildnumber \
	 for example deploy-prod-portal.sh  234"
    exit 1
fi

ssh root@gwprod1portal "/etc/init.d/tomcat6 stop; \
  rm /usr/share/tomcat6/webapps/gwprod1portal/ROOT.war; \
  rm -rf /usr/share/tomcat6/webapps/gwprod1portal/ROOT ; \
  rm -rf /usr/share/tomcat6/work/ ; \
  rm /usr/share/tomcat6/conf/Catalina/gwprod1portal/ROOT.xml; \
  exit"
 
 ssh root@gwprod1portal "mkdir -v -p /usr/share/tomcat6/webapps/gwprod1portal"
 ssh root@gwprod1portal "chmod 755 /usr/share/tomcat6/webapps/gwprod1portal"
 ssh root@gwprod1portal "chown tomcat /usr/share/tomcat6/webapps/gwprod1portal"
 scp /srv/Jenkins/jobs/Portal-Release-Build/modules/com.pwc.us\$compsource-portal-webui-stripes/builds/"${1}"/archive/com.pwc.us/compsource-portal-webui-stripes/1.0-SNAPSHOT/compsource-portal-webui-stripes-1.0-SNAPSHOT.war  root@gwprod1portal:/usr/share/tomcat6/webapps/gwprod1portal/ROOT.war

 ssh root@gwprod1portal "mkdir -v -p /usr/share/tomcat6/conf/Catalina/gwprod1portal"
 ssh root@gwprod1portal "chmod 755 /usr/share/tomcat6/conf/Catalina/gwprod1portal"
 scp /srv/Jenkins/workspace/Portal-Release-Build/Portal/Tomcat_JNDI_Context_Files/PROD/compsource-portal-webui-stripes.xml root@gwprod1portal:/usr/share/tomcat6/conf/Catalina/gwprod1portal/ROOT.xml

 ssh root@gwprod1portal "chmod 644 /usr/share/tomcat6/webapps/gwprod1portal/ROOT.war; \
 chmod 644 /usr/share/tomcat6/conf/Catalina/gwprod1portal/ROOT.xml; \
 /etc/init.d/tomcat6 start; \
 tail -f -n 150 /usr/share/tomcat6/logs/catalina.out"    
