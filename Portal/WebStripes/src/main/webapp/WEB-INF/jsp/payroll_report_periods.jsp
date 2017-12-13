<%-- 
    Document   : hello
    Created on : Sep 17, 2013, 11:00:49 AM
    Author     : Robert Snelling <robert.snelling@us.pwc.com>
--%>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<shiro:authenticated>
    <shiro:hasRole name="policyholder">
        <s:form partial="true" beanclass="com.pwc.us.webui.stripes.action.PayrollReportActionBean">
            <s:select id="prPublicID" name="prPublicID" onchange="updatePolicyForm(this, '${url}');">
                <s:option value="" label="...">Select...</s:option>
                <s:options-map map="${prReportPeriodsMap}" label="value.period" />
            </s:select>
        </s:form>
    </shiro:hasRole>
</shiro:authenticated>
