<%-- 
    Document   : passwordRecoverChallenge
    Created on : Nov 15, 2013, 7:30:49 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage title="Recover your Password" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="Recover Password" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <shiro:notAuthenticated>
            <div id="sem-message" ><s:errors /></div>
            <div id="sem-message" ><s:messages /></div>

            <c:choose>
                <c:when test="${empty actionBean.recover.username}">
                    <s:form class="sem-form" id="passwordRecoverQuestion" method="post" 
                            beanclass="com.pwc.us.webui.stripes.action.PasswordRecoverActionBean">
                        <div class="sem-form-section sem-form-section-1 sem-form-9-section" id="sem-form-9-section-1">
                            <div class="sem-field-container " id="sem-field-container-48"  >
                                <s:label class="sem-label" for="recover.username">
                                    Username: 
                                </s:label>
                                <s:text id="sem-field-48" name="recover.username" class="sem-field sem-text "  />
                            </div><!--/.sem-field-container -->            
                            <div class="sem-field-container" id="sem-submit-426-message" style="display:none">
                                <img src="img/throbber_white_16.gif" /> Processing
                            </div>
                            <div class="sem-field-container" id="sem-submit-426-container">
                                <s:submit name="setUsername" class="sem-submit" value="Submit" />
                            </div>
                        </div><!-- /#sem-form-section -->

                    </s:form>                
                </c:when>
                <c:otherwise>
                    <s:form class="sem-form" id="passwordRecoverQuestion" method="post" 
                            beanclass="com.pwc.us.webui.stripes.action.PasswordRecoverActionBean">
                        <div class="sem-form-section sem-form-section-1 sem-form-9-section" id="sem-form-9-section-1">
                            <div class="sem-field-container " id="sem-field-container-47"  >
                                <p>To recover your password, please answer the following question:</p>
                                <p>${actionBean.recover.question}</p>
                            </div><!--/.sem-field-container -->
                            <div class="sem-field-container " id="sem-field-container-48"  >
                                <s:label class="sem-label" for="recover.answer">
                                    Answer: 
                                </s:label>
                                <s:text id="sem-field-48" name="testAnswer" class="sem-field sem-text "  />
                            </div><!--/.sem-field-container -->            
                            <div class="sem-field-container" id="sem-submit-426-message" style="display:none">
                                <img src="img/throbber_white_16.gif" /> Processing
                            </div>
                            <div class="sem-field-container" id="sem-submit-426-container">
                                <s:submit name="testAnswer" class="sem-submit" value="Submit" />
                            </div>
                        </div><!-- /#sem-form-section -->

                    </s:form>                    
                </c:otherwise>
            </c:choose>
        </shiro:notAuthenticated>
    </jsp:body>
</t:genericpage>