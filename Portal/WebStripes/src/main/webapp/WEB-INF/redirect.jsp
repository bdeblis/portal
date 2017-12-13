
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<t:genericpage title="This service is temporarily unavailable" includePortalStyles="true" >
    <jsp:attribute name="header">
        <t:header title="Login" csoMainUrl="http://www.compsourcemutual.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourcemutual.com" />
    </jsp:attribute>
    <jsp:body>
<br/><br/>
<center>
    <h1>This website is down for maintenance!!</h1>
</center>
    </jsp:body>
</t:genericpage>