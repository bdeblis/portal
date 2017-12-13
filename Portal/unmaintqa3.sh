ssh root@vm-gwqa3 'rm  /usr/share/tomcat6/work/Catalina/compsource-portal-webui-stripes/_/org/apache/jsp/index_jsp.*'
scp "WebStripes/src/main/webapp/WEB-INF/jsp/user/login_live.jsp" "root@vm-gwqa3:/var/lib/tomcat6/webapps/compsource-portal-webui-stripes/ROOT/WEB-INF/jsp/user/login.jsp"
