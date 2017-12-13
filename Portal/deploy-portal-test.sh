ssh root@gwportaltest '/etc/init.d/tomcat6 stop; \
 rm /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war; \
 rm -rf /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT ; \
 rm -rf /usr/share/tomcat6/work/ ; \
 rm /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml; \
 exit'
 
scp WebStripes/target/compsource-portal-webui-stripes.war root@gwportaltest:/usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war

scp Tomcat_JNDI_Context_Files/TEST/compsource-portal-webui-stripes.xml root@gwportaltest:/usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml

ssh root@gwportaltest 'chmod 644 /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war; \
chmod 644 /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml; \
/etc/init.d/tomcat6 start; \
tail -f -n 150 /usr/share/tomcat6/logs/catalina.out'    
