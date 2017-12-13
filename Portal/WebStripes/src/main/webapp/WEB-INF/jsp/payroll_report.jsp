<%-- 
    Document   : hello
    Created on : Sep 17, 2013, 11:00:49 AM
    Author     : Robert Snelling <robert.snelling@us.pwc.com>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage title="Payroll Report Entry" includePortalStyles="true">
    <jsp:attribute name="header">
        <t:header title="Payroll Report Entry" csoMainUrl="http://www.compsourcemutual.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourcemutual.com" />
    </jsp:attribute>
    <jsp:body>
        <script type="text/javascript">
            $(document).ready(function() {
                $('#payroll_report_submit').hide();

                $('#print').on('click', function() {
                    PrintPayroll();
                });
            });

        </script>
        <shiro:authenticated>
            <shiro:hasRole name="policyholder">
                <div class="portal-error-top">
                    <s:messages />
                    <s:errors globalErrorsOnly="true" />
                </div>
                <!-- stashing the print logo in cache to ensure it prints later -->
                <div><img src="img/CMlogo-Web.png" alt="CompSource Mutual logo" border="0" style="display:none;"/></div>
                <s:url var="url" beanclass="com.pwc.us.webui.stripes.action.PayrollReportActionBean" />
                <s:form id="payrollReportForm" beanclass="com.pwc.us.webui.stripes.action.PayrollReportActionBean">
                    <div class="portal-row portal-spacer-20">
                        <div class="portal-col-50">
                            <s:label class="portal-label-100 portal-required" for="prPolicyNumber" />
                            <s:select id="prPolicyNumber" name="prPolicyNumber" onchange="updatePolicyPeriods(this, '${url}');">
                                <s:option value="">Select...</s:option>
                                <s:options-collection collection="${policyNumbers}" sort="label"/>
                            </s:select>
                            <div class="portal-error">
                                <s:errors field="prPolicyNumber" />
                            </div>
                        </div>
                        <div class="portal-col-50">
                            <s:label class="portal-label-100 portal-required" for="policyPeriod" />
                            <%-- policyPeriodSelect is populated from payroll_report_periods.jsp --%>
                            <span id="policyPeriodSelect">
                                <s:select name="policyPeriod" id="my_select1">
                                    <s:option value="">Select...</s:option>
                                </s:select>
                            </span>
                            <div class="portal-error">
                                <s:errors field="policyPeriod" />
                            </div>
                        </div>
                    </div>
                    <div class="sectionTitle">
                        <hr>
                    </div>
                    <div id="payroll_report_form"></div>
                    <div id="payroll_report_submit" class="portal-row">
                        <div class="portal-col-33">&nbsp;
                        </div>
                        <div class="portal-col-66">
                            <div class="portal-error">
                                <s:errors field="payrollReportButtons" />
                            </div>
                            <div class="portal-width-100">
                                <div class="portal-button">
                                    <s:submit name="complete" value="Finalize" class="portal-submit" id="complete" onclick="return completePayroll(this);" />
                                </div>
                                <div class="portal-button">
                                    <s:button name="print" value="Print" class="portal-submit" id="print" />
                                </div>
                                <div class="portal-button">
                                    <s:submit name="cancel" value="Cancel" class="portal-submit cancel" id="cancel" />
                                </div>
                            </div>
                        </div>
                    </div>
                </s:form>
            </shiro:hasRole>
        </shiro:authenticated>          
    </jsp:body>
</t:genericpage>