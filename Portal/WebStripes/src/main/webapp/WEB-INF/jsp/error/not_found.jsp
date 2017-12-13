<%-- 
    Document   : not_found
    Created on : Jan 20, 2014, 12:43:27 PM
    Author     : Roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage title="Page Not Found" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="Page Not Found" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <div id="sem-message" >We're sorry, but that page does not exist.</div>
    </jsp:body>
</t:genericpage>
