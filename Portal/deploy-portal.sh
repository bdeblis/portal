#!/bin/bash
if [[ $# -ne 5 ]] ; then
     "Invalid number of arguments supplied: deploy-portal-jenkins.sh serverurl jenkinsbuildname jndicontext folder buildnumber|lastSuccessful\
	 for example deploy-portal.sh gwqa3 Portal-Master-Build qa3 compsource-portal-webui-stripes lastSuccessful"
    exit 1
fi
if  echo "${5}" | egrep -q '^[0-9]+$'; then
	BUILD_SUBDIR="modules/com.pwc.us\$compsource-portal-webui-stripes/builds/${5}"
elif [ "${5}" == "lastSuccessful" ]; then
	BUILD_SUBDIR="lastSuccessful/com.pwc.us\$compsource-portal-webui-stripes"
else
	echo "${5} is not a valid argument. Usage ${0}  <serverurl> <jenkinsbuildname> <jndicontext> <folder> <buildnumber|lastSuccessful>"
	exit 2
fi
ssh root"@$1" "/etc/init.d/tomcat6 stop; \
  rm /usr/share/tomcat6/webapps/'$4'/ROOT.war; \
  rm -rf /usr/share/tomcat6/webapps/'$4'/ROOT ; \
  rm -rf /usr/share/tomcat6/work/ ; \
  rm /usr/share/tomcat6/conf/Catalina/'$4'/ROOT.xml; \
  exit"
 
 ssh root"@$1" "mkdir -v -p /usr/share/tomcat6/webapps/'$4'"
 ssh root"@$1" "chmod 755 /usr/share/tomcat6/webapps/'$4'"
 ssh root"@$1" "chown tomcat /usr/share/tomcat6/webapps/'$4'"
 scp /srv/Jenkins/jobs/"$2"/"${BUILD_SUBDIR}"/archive/com.pwc.us/compsource-portal-webui-stripes/1.0-SNAPSHOT/compsource-portal-webui-stripes-1.0-SNAPSHOT.war  root"@$1":/usr/share/tomcat6/webapps/"$4"/ROOT.war

 ssh root"@$1" "mkdir -v -p /usr/share/tomcat6/conf/Catalina/'$4'"
 ssh root"@$1" "chmod 755 /usr/share/tomcat6/conf/Catalina/'$4'"
 scp /srv/Jenkins/workspace/"$2"/Portal/Tomcat_JNDI_Context_Files/"$3"/compsource-portal-webui-stripes.xml root"@$1":/usr/share/tomcat6/conf/Catalina/"$4"/ROOT.xml

 ssh root"@$1" "chmod 644 /usr/share/tomcat6/webapps/"$4"/ROOT.war; \
 chmod 644 /usr/share/tomcat6/conf/Catalina/'$4'/ROOT.xml; \
 /etc/init.d/tomcat6 start; \
 tail -f -n 150 /usr/share/tomcat6/logs/catalina.out"    
