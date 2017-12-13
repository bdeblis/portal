<%-- 
    Document   : hello
    Created on : Sep 17, 2013, 11:00:49 AM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<shiro:authenticated>
    <c:set var="title" value="Hello ${user.firstName} ${user.lastName}" />
</shiro:authenticated>
<shiro:notAuthenticated>
    <c:set var="title" value="Hello Guest" />
</shiro:notAuthenticated>

<t:genericpage title="${title}" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="Welcome to CompSource" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <shiro:hasRole name="policyholder">
            <div id="sem-message" ><s:errors /></div>
            <div id="sem-message" ><s:messages /></div>
        </shiro:hasRole>
        <shiro:notAuthenticated>
            <jsp:forward page="Login.action" />
        </shiro:notAuthenticated>
    </jsp:body>
</t:genericpage>
