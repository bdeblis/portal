ssh root@gwdevportal '/etc/init.d/tomcat6 stop; \
 rm /usr/share/tomcat6/webapps/gwdevportal/ROOT.war; \
 rm -rf /usr/share/tomcat6/webapps/gwdevportal/ROOT ; \
 rm -rf /usr/share/tomcat6/work/ ; \
 rm /usr/share/tomcat6/conf/Catalina/gwdevportal/ROOT.xml; \
 exit'

ssh root@gwdevportal 'mkdir -v -p /usr/share/tomcat6/webapps/gwdevportal'
ssh root@gwdevportal 'chmod 755 /usr/share/tomcat6/webapps/gwdevportal'
ssh root@gwdevportal 'chown -R tomcat:root /usr/share/tomcat6/webapps/gwdevportal'
scp WebStripes/target/compsource-portal-webui-stripes.war root@gwdevportal:/usr/share/tomcat6/webapps/gwdevportal/ROOT.war

ssh root@gwdevportal 'mkdir -v -p /usr/share/tomcat6/conf/Catalina/gwdevportal'
ssh root@gwdevportal 'chmod 755 /usr/share/tomcat6/conf/Catalina/gwdevportal'
scp Tomcat_JNDI_Context_Files/dev1/compsource-portal-webui-stripes.xml root@gwdevportal:/usr/share/tomcat6/conf/Catalina/gwdevportal/ROOT.xml

ssh root@gwdevportal 'chmod 644 /usr/share/tomcat6/webapps/gwdevportal/ROOT.war; \
chmod 644 /usr/share/tomcat6/conf/Catalina/gwdevportal/ROOT.xml; \
/etc/init.d/tomcat6 start; \
tail -f -n 150 /usr/share/tomcat6/logs/catalina.out'    
