<%-- 
    Document   : quoteRequestSubmitted
    Created on : Nov 18, 2013, 5:17:38 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="includePortalStyles" value="false" />

<t:genericpage title="Quote Request Submitted" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="Quote Request Submitted" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <div id="sem-message" ><s:errors /></div>
        <div id="sem-message" ><s:messages /></div>
    </jsp:body>
</t:genericpage>