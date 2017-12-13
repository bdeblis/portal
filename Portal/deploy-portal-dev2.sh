ssh root@gwdev2.s-i-f.local '/etc/init.d/tomcat6 stop; \
 rm /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war; \
 rm -rf /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT ; \
 rm -rf /usr/share/tomcat6/work/ ; \
 rm /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml; \
 exit'
 
ssh root@gwdev2.s-i-f.local 'mkdir -v -p /usr/share/tomcat6/webapps/compsource-portal-webui-stripes'
ssh root@gwdev2.s-i-f.local 'chmod 755 /usr/share/tomcat6/webapps/compsource-portal-webui-stripes'
scp WebStripes/target/compsource-portal-webui-stripes.war root@gwdev2.s-i-f.local:/usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war

ssh root@gwdev2.s-i-f.local 'mkdir -v -p /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes'
ssh root@gwdev2.s-i-f.local 'chmod 755 /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes'
scp Tomcat_JNDI_Context_Files/dev2/compsource-portal-webui-stripes.xml root@gwdev2.s-i-f.local:/usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml

ssh root@gwdev2.s-i-f.local 'chmod 644 /usr/share/tomcat6/webapps/compsource-portal-webui-stripes/ROOT.war; \
chmod 644 /usr/share/tomcat6/conf/Catalina/compsource-portal-webui-stripes/ROOT.xml; \
/etc/init.d/tomcat6 start; \
tail -f -n 150 /usr/share/tomcat6/logs/catalina.out'    
