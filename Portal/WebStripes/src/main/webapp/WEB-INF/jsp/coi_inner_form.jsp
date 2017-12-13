<%-- 
    Document   : hello
    Created on : Sep 17, 2013, 11:00:49 AM
    Author     : Robert Snelling <robert.snelling@us.pwc.com>
--%>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<shiro:authenticated>
    <shiro:hasRole name="policyholder">
        <s:form partial="true" beanclass="com.pwc.us.webui.stripes.action.COIFormActionBean">
            <s:select name="certificateOfInsuranceRequest.termNumber">
                <s:option value="">Select a term...</s:option>
                <s:options-collection collection="${terms}" value="termNumber" label="termDescription" />
            </s:select>
        </s:form>
    </shiro:hasRole>
</shiro:authenticated>


