<%-- 
    Document   : changePassword
    Created on : Nov 23, 2013, 5:45:53 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage title="Change Password" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="Change Password" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <shiro:notAuthenticated>
            <div id="sem-message" ><s:errors /></div>
            <div id="sem-message" ><s:messages /></div>

            <s:form class="sem-form" id="changePassword" method="post" 
                    beanclass="com.pwc.us.webui.stripes.action.PasswordRecoverActionBean">
                <div class="sem-field-container " id="sem-field-container-48"  >
                    Please enter your your new password below.
                </div>
                <div class="sem-field-container " id="testPassword1"  >
                    <s:label class="sem-label" for="registration.password" />
                    <s:password name="testPassword1" class="sem-field sem-text "  />
                    <s:errors field="testPassword1" />
                </div><!--/.sem-field-container -->
                <div class="sem-field-container " id="testPassword2"  >
                    <s:label class="sem-label" for="registration.passwordAgain" />
                    <s:password id="sem-field-48" name="testPassword2" class="sem-field sem-text "  />
                    <s:errors field="testPassword2" />
                </div><!--/.sem-field-container -->
                <div class="sem-field-container" id="sem-submit-426-container">
                    <s:submit name="changePassword" class="sem-submit" value="Submit" />
                </div>

            </s:form>                                   
        </shiro:notAuthenticated>
    </jsp:body>
</t:genericpage>