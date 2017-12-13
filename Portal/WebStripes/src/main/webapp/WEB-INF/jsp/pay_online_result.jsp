<%-- 
    Document   : pay_online_result
    Created on : Feb 7, 2014, 2:09:41 PM
    Author     : Roger
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage title="Pay Online" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="Pay Online" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <shiro:authenticated>
            <shiro:hasAnyRoles name="policyholder, cashier">
                <div id="sem-message" >
                    <s:errors />
                    <s:messages />
                </div>
            </shiro:hasAnyRoles>
        </shiro:authenticated>
    </jsp:body>
</t:genericpage>
