#!/bin/bash
if [[ $# -ne 1 ]] ; then
     "Invalid number of arguments supplied: deploy-stagin-portal.sh buildnumber \
	 for example deploy-staging-portal.sh  234"
    exit 1
fi

ssh root@gwportale2e.s-i-f.local "/etc/init.d/tomcat6 stop; \
  rm /usr/share/tomcat6/webapps/gwportale2e.s-i-f.local/ROOT.war; \
  rm -rf /usr/share/tomcat6/webapps/gwportale2e.s-i-f.local/ROOT ; \
  rm -rf /usr/share/tomcat6/work/ ; \
  rm /usr/share/tomcat6/conf/Catalina/gwportale2e.s-i-f.local/ROOT.xml; \
  exit"
 
 ssh root@gwportale2e.s-i-f.local "mkdir -v -p /usr/share/tomcat6/webapps/gwportale2e.s-i-f.local"
 ssh root@gwportale2e.s-i-f.local "chmod 755 /usr/share/tomcat6/webapps/gwportale2e.s-i-f.local"
 ssh root@gwportale2e.s-i-f.local "chown tomcat /usr/share/tomcat6/webapps/gwportale2e.s-i-f.local"
 scp /srv/Jenkins/jobs/Portal-Release-Build/modules/com.pwc.us\$compsource-portal-webui-stripes/builds/"${1}"/archive/com.pwc.us/compsource-portal-webui-stripes/1.0-SNAPSHOT/compsource-portal-webui-stripes-1.0-SNAPSHOT.war  root@gwportale2e.s-i-f.local:/usr/share/tomcat6/webapps/gwportale2e.s-i-f.local/ROOT.war

 ssh root@gwportale2e.s-i-f.local "mkdir -v -p /usr/share/tomcat6/conf/Catalina/gwportale2e.s-i-f.local"
 ssh root@gwportale2e.s-i-f.local "chmod 755 /usr/share/tomcat6/conf/Catalina/gwportale2e.s-i-f.local"
 scp /srv/Jenkins/workspace/Portal-Release-Build/Portal/Tomcat_JNDI_Context_Files/e2e/compsource-portal-webui-stripes.xml root@gwportale2e.s-i-f.local:/usr/share/tomcat6/conf/Catalina/gwportale2e.s-i-f.local/ROOT.xml

 ssh root@gwportale2e.s-i-f.local "chmod 644 /usr/share/tomcat6/webapps/gwportale2e.s-i-f.local/ROOT.war; \
 chmod 644 /usr/share/tomcat6/conf/Catalina/gwportale2e.s-i-f.local/ROOT.xml; \
 /etc/init.d/tomcat6 start; \
 tail -f -n 150 /usr/share/tomcat6/logs/catalina.out"    
