ssh root@gwprod2portal 'rm -rf /usr/share/tomcat6/work/'
scp "/cygdrive/c/apache-tomcat-6.0.37/webapps/ROOT/WEB-INF/jsp/user/login_live.jsp" "root@gwprod2portal:/var/lib/tomcat6/webapps/gwprod2portal/ROOT/WEB-INF/jsp/user/login.jsp"
