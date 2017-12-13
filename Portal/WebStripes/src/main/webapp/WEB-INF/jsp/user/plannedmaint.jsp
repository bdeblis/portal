<%-- 
    Document   : login
    Created on : Oct 15, 2013, 4:40:38 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<t:genericpage title="We will be performing system maintenance and our Online Services will be unavailable from 8 a.m. to Noon on Sunday, February 1." includePortalStyles="false" >
    <jsp:attribute name="header">
        <t:header title="Login" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <div class="portal-error-top"><s:errors /></div>
        <div class="portal-error-top"><s:messages /></div>
        <div class="sem-field-container">
            <p> 
                <s:link beanclass="com.pwc.us.webui.stripes.action.Registration">
                    Register with CompSource Mutual
                </s:link>
            </p>
        </div>

        <s:form class="sem-form" id="loginForm" method="post" beanclass="com.pwc.us.webui.stripes.action.Login">
            <div class="sem-form-section sem-form-section-1 sem-form-9-section" id="sem-form-9-section-1">
                <div class="sem-field-container " id="sem-field-container-47"  >
                    <s:label class="sem-label" for="username">Username: 
                        <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    </s:label>
		<label style="color:#ff0000">We apologize for any inconvenience this may cause. If you need to submit payroll or file a claim during this time, click here.</label><a href="http://www.compsourcemutual.com/policyholders/claims/file-a-claim/">Submit Claim</a>
                    <s:text id="sem-field-48" name="username" class="sem-field sem-text "  />
                </div><!--/.sem-field-container -->
                <div class="sem-field-container " id="sem-field-container-48"  >
                    <s:label class="sem-label" for="password">
                        Password: <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    </s:label>
               
                    <s:password id="sem-field-48" name="password" class="sem-field sem-text "  />
                </div><!--/.sem-field-container -->            
                <div class="sem-field-container" id="sem-submit-426-message" style="display:none">
                    <img src="img/throbber_white_16.gif" /> Processing
                </div>
                <div class="sem-field-container" id="sem-submit-426-container">
                    <s:submit name="login" class="sem-submit" value="Login" onclick="submitProcessing()" />
                    <div class="sem-error" id="sem-error-426" style="display:none"></div>
                </div>
            </div><!-- /#sem-form-section -->
        </s:form>
        <shiro:notAuthenticated>
            <div class=""style="position: relative; top: 50px; left: 120px;">
                <p> 
                    <s:link beanclass="com.pwc.us.webui.stripes.action.PasswordRecoverActionBean">
                        Forgot your password?
                    </s:link>
                </p>
            </div>
        </shiro:notAuthenticated>




    </jsp:body>
</t:genericpage>