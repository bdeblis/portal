ssh root@eeportal.s-i-f.local '/etc/init.d/tomcat6 stop; \
 rm /usr/share/tomcat6/webapps/eeportal.s-i-f.local/ROOT.war; \
 rm -rf /usr/share/tomcat6/webapps/eeportal.s-i-f.local/ROOT/ ; \
 rm -rf /usr/share/tomcat6/work/ ; \
 rm /usr/share/tomcat6/conf/Catalina/eeportal.s-i-f.local/ROOT.xml; \
 exit'

scp WebStripes/target/compsource-portal-webui-stripes.war root@eeportal.s-i-f.local:/usr/share/tomcat6/webapps/eeportal.s-i-f.local/ROOT.war

scp Tomcat_JNDI_Context_Files/E2E/compsource-portal-webui-stripes.xml root@eeportal.s-i-f.local:/usr/share/tomcat6/conf/Catalina/eeportal.s-i-f.local/ROOT.xml

ssh root@eeportal.s-i-f.local 'chmod 644 /usr/share/tomcat6/webapps/eeportal.s-i-f.local/ROOT.war; \
chmod 644 /usr/share/tomcat6/conf/Catalina/eeportal.s-i-f.local/ROOT.xml; \
/etc/init.d/tomcat6 start; \
tail -f -n 150 /usr/share/tomcat6/logs/catalina.out'  