<%-- 
    Document   : hello
    Created on : Sep 17, 2013, 11:00:49 AM
    Author     : Robert Snelling <robert.snelling@us.pwc.com>
--%>
<%@include file="/WEB-INF/jsp/common/taglibs.jsp"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>


<script type="text/javascript">
    
    $(document).ready(function() {

        //$('.numberFmt').number(true);
        calculateSum();


        $(".payAmt").each(function() {
            if ($(this).val() !== "") {
                $(this).formatNumber({format: "#,###", locale: "us"});
            }else{
                $(this).val(0);
            }
        });

        //iterate through each textboxes and add keyup
        //handler to trigger sum event
        $(".payAmt").each(function() {
            $(this).focusout(function() {
                calculateSum();
                $(".payAmt").each(function() {
                    if ($(this).val() !== "") {
                        var amt = $(this).parseNumber({format: "#,###", locale: "us"});
                        amt = Math.abs(amt);
                        $(this).val(amt.toMoney(0));
                    }
                });
                return false;
            });

        });

        //$('.portal-fCol3').textAlign('.');

        //var prem = $('#premium').text().parseNumber({format: "#,###", locale: "us"});
        
    });

</script>

<fmt:setLocale value="en_US"/>
<shiro:authenticated>
    <shiro:hasRole name="policyholder">
        <div class="portal-legend"><s:messages /></div>
        <!--
        <div class="portal-error-top">
            <s:errors globalErrorsOnly="true" />
        </div>
        -->
        <s:form partial="true" beanclass="com.pwc.us.webui.stripes.action.PayrollReportActionBean">
            <div class="portal-form">
                <div class="portal-row">
                    <div class="portal-reportRow">
                        <div class="portal-reportTitle">Payroll Report Entry</div>
                        <div class="portal-reportInst">Payroll reports submitted after 5:00 pm central time may not be processed until the next business day.</div>
                    </div>
                    <hr class="portal-hr">
                    <div class="portal-reportRow">
                        <div class="portal-content-40">
                            <div class="portal-content-30">                        
                                <s:label for="policyPeriod.policyTerm" />
                            </div>
                            <div class="portal-content-70">
                                ${actionBean.policyPeriod.policyTerm}
                            </div>
                        </div>
                        <div class="portal-content-60">
                            <div class="portal-content-60">
                                <s:label for="policyPeriod.period">Reporting Period Start Date:</s:label>
                            </div>
                            <div class="portal-content-40">
                                ${actionBean.policyPeriod.periodStartDisplay}
                            </div>
                        </div>
                            
                     <div class="portal-reportRow">
                        <div class="portal-content-40">
                            <div class="portal-content-30">                        
                                &nbsp;
                            </div>
                            <div class="portal-content-70">
                                &nbsp;
                            </div>
                        </div>
                        <div class="portal-content-60">
                            <div class="portal-content-60">
                                <s:label for="policyPeriod.period">Reporting Period End Date:</s:label>
                            </div>
                            <div class="portal-content-40">
                                ${actionBean.policyPeriod.periodEndDisplay}
                            </div>
                        </div>
                    </div>
                            
                            
                    </div>
                    <div class="portal-reportRow">
                        <div class="portal-content-60">
                            <div class="portal-content-30">
                                <s:label for="prPolicyHolder.companyName" />
                            </div>
                            <div class="portal-content-70">
                                ${user.companyName}
                            </div>
                        </div>
                        <div class="portal-content-40">
                            <div class="portal-content-50">&nbsp;</div>
                            <div class="portal-content-50">&nbsp;</div>
                        </div>
                    </div>
                    <div class="portal-reportRow portal-spacer-20">
                        <div class="portal-content-100">
                            <div class="portal-error-internal">
                                <s:errors globalErrorsOnly="true" />
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Table -->
                <div>&nbsp;</div>
                <div>&nbsp;</div>  
                <div class="portal-row portal-spacer-5">
                    <table class="portal-table">
                        <thead>
                            <tr>
                                <th class="portal-hCol1">Class Code</th>
                                <th class="portal-hCol2">Description of Work</th>
                   <!--             <th class="portal-hCol3" colspan="2">Location</th>  -->
                                <th class="portal-hCol3">Gross Payroll for each code</th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td class="portal-fCol1" colspan="2"><span class="portal-left">* The amount included in this line is to be included 
                                        as the payroll amount for this owner/officer along with the covered employees payroll on the line above.</span></td>
                                <td class="portal-fCol2">Total:</td>
                                <td class="portal-fCol3" id="total"><span id="sum" class="numberFmt"></span></td>
                            </tr>
                            <tr>
                                <td class="portal-fCol1">&nbsp;</td>
                                <td class="portal-fCol2" colspan="2">&nbsp;</td>
                                <td class="portal-fCol3">&nbsp;</td>
                            </tr>
                            <tr>
                                <td class="portal-fCol1"></td>
                                <td class="portal-fCol2" colspan="2">Premium:</td>
                                <td class="portal-fCol3"><span class="numberFmt"><fmt:formatNumber type="number" minFractionDigits="2" groupingUsed="true" value="${actionBean.premium}"/></span></td>
                            </tr>
                        </tfoot>
                        <tbody class="portal-tbody">
                            <c:forEach var="record" items="${actionBean.payrollReportTable}" varStatus="loop">
                                <s:hidden name="payrollReportTable[${loop.index}].fixedId" value="${record.fixedId}"/>
                                <s:hidden name="payrollReportTable[${loop.index}].basisType" value="${record.basisType}"/>
                                <s:hidden name="payrollReportTable[${loop.index}].classCodeDescInd" value="${record.classCodeDescInd}"/>
                                <s:hidden name="payrollReportTable[${loop.index}].ownerOfficer" value="${record.ownerOfficer}"/>
                                <s:hidden name="payrollReportTable[${loop.index}].auditAmountIsZero" id="payrollReportTable[${loop.index}].auditAmountIsZero" value="${record.auditAmountIsZero}"/>
                                <c:choose>
                                    <c:when test="${record.ownerOfficer}">
                                        <tr>
                                            <td class="portal-dCol1">
                                                ${record.classCode}*
                                                <s:hidden name="payrollReportTable[${loop.index}].classCode" value="${record.classCode}"/>
                                            </td>
                                            <td class="portal-dCol2">
                                                ${record.displayName} - ${record.printAuditedAmount}
                                                <s:hidden name="payrollReportTable[${loop.index}].classCodeShortDesc" value="${record.classCodeShortDesc}"/>
                                            </td>
                                            <!--
                                            <td class="portal-dCol3"  style="text-align:left">
                                               ${record.location}
                                               <s:hidden name="payrollReportTable[${loop.index}].location" value="${record.location}"/>
                                            </td>
                                            -->
                                            <td class="portal-dCol4">
                                                &nbsp;
                                                <s:hidden name="auditedAmount" value="${record.auditedAmount}"/>                                                
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td class="portal-dCol1">
                                                ${record.classCode}
                                                <s:hidden name="payrollReportTable[${loop.index}].classCode" value="${record.classCode}"/>
                                            </td>
                                            <td class="portal-dCol2">
                                                ${record.classCodeShortDesc}
                                                <s:hidden name="payrollReportTable[${loop.index}].classCodeShortDesc" value="${record.classCodeShortDesc}"/>
                                            </td>
                                            <!--
                                            <td class="portal-dCol3"  style="text-align:left">
                                                ${record.location}
                                               <s:hidden name="payrollReportTable[${loop.index}].location" value="${record.location}"/>
                                            </td>
                                            -->
                                            <td class="portal-dCol4">
                                                <c:choose>
                                                    <c:when test="${actionBean.policyPeriod.editable}">
                                                        <s:text  class="payAmt numberFmt"  name="payrollReportTable[${loop.index}].auditedAmount" value="${record.auditedAmount}"/>
                                                        <script type="text/javascript">
                                                            // Enable Complete Button
                                                            $('#complete').prop('disabled', false);
                                                        </script>
                                                    </c:when>
                                                    <c:otherwise>
                                                        
                                                        <s:text class="payAmt numberFmt" name="payrollReportTable[${loop.index}].auditedAmount" value="${record.auditedAmount}" disabled="true"/>
                                                        <script type="text/javascript">
                                                            // Disable Complete Button
                                                            $('#complete').prop('disabled', true);
                                                        </script>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>                                       
                                    </c:otherwise>
                                </c:choose>


                            </c:forEach>

                            <tr>
                                <td class="portal-dCol1">&nbsp;</td>
                                <td class="portal-dCol2">&nbsp;</td>
                       <!--         <td class="portal-dCol3">&nbsp;</td> -->
                                <td class="portal-dCol4">&nbsp;</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="portal-row">
                <div class="portal-col-33">&nbsp;
                </div>
                <div class="portal-col-66">
                    <div class="portal-content-100"><span id="statusMsg" style="display: block;word-wrap: break-word">${actionBean.statusMsg}</span></div>
                </div>
            </div>            
            <%-- 
                This is the printable form to be displayed in another window    
            --%>
            <div id="payroll_report_print" style="display: none" >
			    <div class="header_left" style="text-align: center">
			        <div class="img">
			        	<a href="img/CMlogo-Web.png">
			        		<img src="img/CMlogo-Web.png" alt="CompSource Mutual logo" border="0"/>
			        	</a>
			        </div>
			    </div>
                <div style="padding-bottom: 100px;">
                    <hr >
                    <div style="text-align: center;font-weight: bold;"><h3>CompSource Mutual</h3></div>
                    <div style="text-align: center;font-weight: bold;"><h3>Website Payroll Report Submission</h3></div>
                    <div style="text-align: center;font-weight: bold; padding-bottom: 20px;">${actionBean.today}</div>

                    <div style="display: block;width: 670px;margin-left: auto;margin-right: auto;line-height: 25px;">
                        <div style="width: 300px;float: left;text-align: right;">                        
                            <s:label for="prPolicyHolder.companyName" />
                        </div>
                        <div style="width:70px;float:left;">&nbsp;</div>
                        <div style="width: 300px;float: left;text-align: left;">${user.companyName}</div>
                    </div>
                    <div style="display: block;width: 670px;margin-left: auto;margin-right: auto;line-height: 25px;">
                        <div style="width: 300px;float: left;text-align: right;">
                            <s:label for="policyPeriod.policyTerm" />
                        </div>
                        <div style="width: 70px;float:left;">&nbsp;</div>
                        <div style="width: 300px;float: left;text-align: left;">${actionBean.policyPeriod.policyTerm}</div>
                    </div>
                    <div style="display: block;width: 670px;margin-left: auto;margin-right: auto;line-height: 25px;">
                        <div style="width: 300px;float: left; text-align: right;">
                            <s:label for="policyPeriod.periodf" />
                        </div><div style="width: 70px;float:left;">&nbsp;</div>
                        <div style="width: 300px;  float: left;  text-align: left;">
                            ${actionBean.policyPeriod.period}
                        </div>
                    </div>
                </div>
                <!-- Table -->
                <div>&nbsp;</div>
                <div>&nbsp;</div>  
                <div style="width: 500px;margin-left: auto;margin-right: auto;">
                    <table class="print-table">
                        <thead>
                            <tr>
                                <th style="width: 80px;vertical-align: bottom;text-align: center;padding: 5px;font-weight: bold;">
                                    Class Code
                                </th>
                                <th style="width: 240px; vertical-align: bottom; text-align: left; font-weight: bold; padding: 5px;">
                                    Description of Work
                                </th>
                                <th  colspan="2" style="width: 180px;vertical-align: bottom;text-align: left;font-weight: bold;padding: 5px;">
                                    Gross Payroll for each code
                                </th>
                            </tr>
                        </thead>
                        <tfoot>
                            <tr>
                                <td  style="width: 80px; vertical-align: bottom; text-align: center; padding: 5px;">
                                    &nbsp; 
                                </td>
                                <td colspan="2" style="width: 345px; vertical-align: bottom; padding: 5px 10px 5px 5px; text-align: right;">
                                    Total:
                                </td>
                                <td class="pay-col" style="width: 75px; vertical-align: bottom; text-align: right; padding: 5px;">
                                    <span class="print-payAmt" id="printSum" ></span>
                                </td>
                            </tr>
                            <tr>
                                <td style="width: 80px; vertical-align: bottom; text-align: center; padding: 5px;">&nbsp;</td>
                                <td colspan="2" style="width: 345px; vertical-align: bottom; padding: 5px 10px 5px 5px; text-align: right;">&nbsp;</td>
                                <td style="width: 75px; vertical-align: bottom; padding: 5px;">&nbsp;</td>
                            </tr>
                            <tr>
                                <td style="width: 80px; vertical-align: bottom; text-align: center; padding: 5px;"></td>
                                <td  colspan="2" style="width: 345px; vertical-align: bottom; padding: 5px 10px 5px 5px; text-align: right;">Premium:</td>
                                <td class="pay-col" style="width: 75px; vertical-align: bottom; padding: 5px;"><span class="print-payAmt">${actionBean.printPremium}</span></td>
                            </tr>
                        </tfoot>
                        <tbody style="display:table-row-group !important;
                               border-top: 2px solid black !important;
                               border-left: 2px solid black !important;
                               border-collapse: separate !important;
                               border-spacing: 4px !important;
                               margin-bottom: 10px !important;">
                            <c:forEach var="record" items="${actionBean.payrollReportTable}" varStatus="loop">
                                <c:choose>
                                    <c:when test="${record.ownerOfficer}">
                                        <tr>
                                            <td style="width: 80px; vertical-align: middle; text-align: center; padding: 5px;">
                                                ${record.classCode}*
                                                <s:hidden name="payrollReportTable[${loop.index}].classCode" value="${record.classCode}"/>
                                            </td>
                                            <td style="width: 240px; vertical-align: middle; padding: 5px;">
                                                ${record.displayName} - ${record.printAuditedAmount}
                                                <s:hidden name="payrollReportTable[${loop.index}].classCodeShortDesc" value="${record.classCodeShortDesc}"/>
                                            </td>
                                            <td style="width: 105px; vertical-align: middle; text-align: right; padding: 5px;">
                                                &nbsp;
                                            </td>
                                            <td class="pay-col" style="width: 75px; vertical-align: middle; text-align: right; padding: 5px;">
                                                &nbsp;
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr>
                                            <td style="width: 80px; vertical-align: middle; text-align: center; padding: 5px;">
                                                ${record.classCode}
                                                <s:hidden name="payrollReportTable[${loop.index}].classCode" value="${record.classCode}"/>
                                            </td>
                                            <td style="width: 240px; vertical-align: middle; padding: 5px;">
                                                ${record.classCodeShortDesc}
                                                <s:hidden name="payrollReportTable[${loop.index}].classCodeShortDesc" value="${record.classCodeShortDesc}"/>
                                            </td>
                                            <td style="width: 105px; vertical-align: middle; text-align: right; padding: 5px;">
                                                &nbsp;
                                            </td>
                                            <td class="pay-col" style="width: 75px; vertical-align: middle; text-align: right; padding: 5px;">
                                                <span class="print-payAmt">${record.printAuditedAmount}</span>
                                            </td>
                                        </tr>                                       
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <tr>
                                <td style="width: 80px; vertical-align: middle; text-align: center; padding: 5px;">&nbsp;</td>
                                <td style="width: 240px; vertical-align: middle; padding: 5px;">&nbsp;</td>
                                <td style="width: 105px; vertical-align: middle; text-align: right; padding: 5px;">&nbsp;</td>
                                <td style="width: 75px; vertical-align: middle; padding: 5px;">&nbsp;</td>
                            </tr>
                        </tbody>
                    </table>
                    <span >* The amount included in this line is to be included as the payroll amount for this 
                        owner/officer along with the covered employees payroll on the line above.</span>
                </div>
            </div>
            <c:forEach var="record" items="${actionBean.ownerOfficersTable}" varStatus="loop">
                <s:hidden name="ownerOfficersTable[${loop.index}].fixedId" value="${record.fixedId}"/>
                <s:hidden name="ownerOfficersTable[${loop.index}].basisType" value="${record.basisType}"/>
                <s:hidden name="ownerOfficersTable[${loop.index}].classCodeDescInd" value="${record.classCodeDescInd}"/>
                <s:hidden name="ownerOfficersTable[${loop.index}].classCode" value="${record.classCode}"/>
                <s:hidden name="ownerOfficersTable[${loop.index}].classCodeShortDesc" value="${record.classCodeShortDesc}"/>
                <s:hidden name="ownerOfficersTable[${loop.index}].auditedAmount" value="${record.auditedAmount}"/>      
            </c:forEach>
        </s:form>
        <script></script>
    </shiro:hasRole>
</shiro:authenticated>  
