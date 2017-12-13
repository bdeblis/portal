<%-- 
    Document   : login
    Created on : Oct 15, 2013, 4:40:38 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage title="First Notice of Injury" includePortalStyles="true">
    <jsp:attribute name="header">
        <t:header title="First Notice of Injury" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <div class="portal-error-top"><s:errors /></div>
        <div class="portal-fnoi-message"><s:messages /></div>

        <s:form id="fnoiPolicyNumber" beanclass="com.pwc.us.webui.stripes.action.FNOIPolicyCheck">
            <div class="portal-form">
                <div class="sectionSubTitle">
                   To report an injury, please enter the first 8 digits of a valid policy number, and date of accident.<br/><br/>
                </div>
                <div class="portal-row">
                    <div class="portal-col-100">
                        <s:label class="portal-label-100 portal-required" for="policy.policyNumber" />
                        <s:text style="position:absolute; width:150px; right:192px;" name="policyNumber"/>
                        <div class="portal-error">
                            <label for="policyNumber" class="error"></label>                 
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-100">    
                        <s:label class="portal-label-100 portal-required portal-legend" for="dateOfAccident" />
                        <s:text class="portal-width-auto" name="dateOfAccident" formatPattern="MM-dd-yyyy" tabindex="2" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                        <div class="portal-error">
                            <label for="dateOfAccident" class="error"></label>
                            <s:errors field="dateOfAccident" />                    
                        </div>
                    </div>
                </div>                        
                <div class="portal-row">
                    <div class="portal-col-100">                   
                        <div class="portal-processing" id="portal-processing">
                            <img src="img/throbber_white_32.gif" /> Processing
                        </div>
                        <div class="portal-button">
                            <s:submit name="submit" class="portal-submit" value="Submit" id="submit" onclick="submitProcessing()"/>
                        </div>
                    </div>
                </div>
            </div>
        </s:form>
    </jsp:body>
</t:genericpage>