ssh root@gwprod1portal 'rm  /usr/share/tomcat6/work/Catalina/gwprod1portal/_/org/apache/jsp/index_jsp.*'
scp "WebStripes/src/main/webapp/WEB-INF/jsp/user/login_live.jsp" "root@gwprod1portal:/var/lib/tomcat6/webapps/gwprod1portal/ROOT/WEB-INF/jsp/user/login.jsp"
