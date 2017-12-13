rm -f "${TOMCAT_HOME}/webapps/ROOT.war"
rm -rf "${TOMCAT_HOME}/webapps/ROOT/"
rm -rf "${TOMCAT_HOME}/work/"
rm -f "${TOMCAT_HOME}/conf/Catalina/www.example.com/ROOT.xml"

cp WebStripes/target/compsource-portal-webui-stripes.war "${TOMCAT_HOME}/webapps/ROOT.war"
cp Tomcat_JNDI_Context_Files/LOCAL/compsource-portal-webui-stripes.xml "${TOMCAT_HOME}/conf/catalina/www.example.com/ROOT.xml"
