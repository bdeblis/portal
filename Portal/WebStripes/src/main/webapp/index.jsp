<%--
Views should be stored under the WEB-INF folder so that
they are not accessible except through controller process.

This JSP is here to provide a redirect to the dispatcher
servlet but should be the only JSP outside of WEB-INF.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<shiro:authenticated>
    <jsp:forward page="/Hello.action"/>
</shiro:authenticated>

<shiro:notAuthenticated>
    <jsp:forward page="/Login.action"/>
</shiro:notAuthenticated>

