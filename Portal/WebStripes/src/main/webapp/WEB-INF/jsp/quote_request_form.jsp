<%-- 
    Document   : quote_request_form
    Created on : Dec 16, 2013, 2:13:48 PM
    Author     : Roger
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage title="Request a Quote" includePortalStyles="true">
    <jsp:attribute name="header">
        <t:header title="Request a Quote" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <script type="text/javascript">
            $(document).ready(function() {
                hideShowPrevCoverage();
                hideShowTaxId();
                hideShowQuoteAllOKBusiness();
                hideShowEmployeesOutsideOK();
                hideShowListOutsideOKStates();
                hideShowExplainLiquidation();
                hideShowSubcontractors();
                hideShowDeniedInsurance();
                hideShowRequiredIfOtherOKBus();
                $('input:radio').prop("checked", false);
            });
            
                var rad = document.quoteRequestForm.hadPreviousCoverage;
    var prev = null;
    for(var i = 0; i < rad.length; i++) {
        rad[i].onclick = function() {
            (prev)? console.log(prev.value):null;
            if(this !== prev) {
                prev = this;
            }
            console.log(this.value)
        };
    }
            
        </script>
        <div class="portal-error-top" ><s:errors globalErrorsOnly="true" /><span id="globalError" style="color:#ff0000"></span></div>

        <s:form id="quoteRequestForm" beanclass="com.pwc.us.webui.stripes.action.QuoteRequestActionBean">
            <div class="portal-form">

                <div class="sectionSubTitle">
                    To apply for a workers’ compensation quote with CompSource Oklahoma, please provide the following information, 
                    then click the “Submit” button. An Underwriter will contact you for additional information if needed, 
                    provide you with a premium quote, and mail an application for coverage if desired. <br/><br/>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="isTempStaffing.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="isTempStaffingYes" name="isTempStaffing.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label" >
                            <s:radio class="portal-radio" id="isTempStaffingNo" name="isTempStaffing.booleanAnswer" value="false" />no</label>
                        <div class="portal-error">
                            <s:errors field="isTempStaffing.booleanAnswer" />
                            <label for="isTempStaffing.booleanAnswer" class="error"></label>                                
                        </div>
                    </fieldset>

                </div>      
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="opsOutsideOk.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="opsOutsideOkYes" name="opsOutsideOk.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="opsOutsideOkNo" name="opsOutsideOk.booleanAnswer" value="false" />no</label>
                        <div class="portal-error">
                            <s:errors field="opsOutsideOk.booleanAnswer" />
                            <label for="opsOutsideOk.booleanAnswer" class="error"></label>                             
                        </div>
                    </fieldset>  
                </div>  
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="totalAnnualPayroll.integerAnswer" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-40" name="totalAnnualPayroll.integerAnswer" />
                    </div>
                    <label for="totalAnnualPayroll.integerAnswer" class="error"></label>
                    <s:errors field="totalAnnualPayroll.integerAnswer" />
                </div>  

                <!-- Basic Business Info -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="qr.Section.BasicBusinessInfo" />
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderContact.name" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="accountHolderContact.name"  />
                        <div class="portal-error">
                            <s:errors field="accountHolderContact.name" />
                            <label for="accountHolderContact.name" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="account.busOpsDesc" />
                    </div>
                    <div class="portal-col-66">
                        <s:textarea name="account.busOpsDesc"  />
                        <div class="portal-error">
                            <s:errors field="account.busOpsDesc" />
                            <label for="account.busOpsDesc" class="error"></label>
                        </div>
                    </div>

                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderPrimaryAddress.addressLine1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="accountHolderPrimaryAddress.addressLine1"  />
                        <div class="portal-error">
                            <s:errors field="accountHolderPrimaryAddress.addressLine1" />
                            <label for="accountHolderPrimaryAddress.addressLine1" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderPrimaryAddress.county" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="accountHolderPrimaryAddress.county">
                            <s:option value="">Select a county...</s:option>
                            <s:options-enumeration enum="com.pwc.us.common.model.OklahomaCounty" />
                        </s:select>
                        <div class="portal-error">
                            <s:errors field="accountHolderPrimaryAddress.county" />
                            <label for="accountHolderPrimaryAddress.county" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderPrimaryAddress.city" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="accountHolderPrimaryAddress.city"  />
                        <div class="portal-error">
                            <s:errors field="accountHolderPrimaryAddress.city" />
                            <label for="accountHolderPrimaryAddress.city" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderPrimaryAddress.state" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="accountHolderPrimaryAddress.state">
                            <s:option value="">Select a state...</s:option>
                            <s:options-enumeration enum="com.pwc.us.common.model.UsState" />
                        </s:select>
                        <div class="portal-error">
                            <s:errors field="accountHolderPrimaryAddress.state" />
                            <label for="accountHolderPrimaryAddress.state" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderPrimaryAddress.postalCode" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="accountHolderPrimaryAddress.postalCode"  />
                        <div class="portal-error">
                            <s:errors field="accountHolderPrimaryAddress.postalCode" />
                            <label for="accountHolderPrimaryAddress.postalCode" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderContact.workPhone" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="accountHolderContact.workPhone"  />
                        <div class="portal-error">
                            <s:errors field="accountHolderContact.workPhone" />
                            <label for="accountHolderContact.workPhone" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                            <s:label for="Format: xxx-xxx-xxxx" />
                    </div>
                    <div class="portal-col-66">
                        <s:label for="Enter with dashes; example: 405-555-5555" />
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="accountHolderPrimaryAddress.description" />
                    </div>
                    <div class="portal-col-66">
                        <s:textarea name="accountHolderPrimaryAddress.description"  />
                        <div class="portal-error">
                            <s:errors field="accountHolderPrimaryAddress.description" />
                            <label for="accountHolderPrimaryAddress.description" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="primaryNamedInsured.orgTypeExt" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="primaryNamedInsured.orgTypeExt">
                            <s:option value="">Select...</s:option>
                            <s:options-collection collection="${actionBean.orgTypeExt}" 
                                                  value="code" label="name" />
                        </s:select>
                        <div class="portal-error">
                            <s:errors field="primaryNamedInsured.orgTypeExt" />
                            <label for="primaryNamedInsured.orgTypeExt" class="error"></label>
                        </div>
                    </div>

                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-33 portal-legend portal-required"><s:label for="hasFederalTaxId" /></div>
                        <div class="portal-col-66">
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" id="hasFederalTaxIdYes" name="hasFederalTaxId" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" id="hasFederalTaxIdNo" name="hasFederalTaxId" value="false" />no</label>
                            <div class="portal-error">
                                <label for="hasFederalTaxId" class="error"></label>
                                <s:errors field="hasFederalTaxId" />
                            </div>
                        </div>
                    </fieldset>


                </div>
                <div class="portal-row" id="taxId" style="display:none">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="accountHolderContact.taxID" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="accountHolderContact.taxID"  />
                        <div class="portal-error">
                            <s:errors field="accountHolderContact.taxID" />
                            <label for="accountHolderContact.taxID" class="error"></label>
                        </div>
                    </div>
                </div>

                <!-- Audit Info Section -->
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.Section.AuditInfo" />
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorEntityPerson.firstName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="auditorEntityPerson.firstName"  />
                        <div class="portal-error">
                            <s:errors field="auditorEntityPerson.firstName" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorEntityPerson.middleName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="auditorEntityPerson.middleName"  />
                        <div class="portal-error">
                            <s:errors field="auditorEntityPerson.middleName" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorEntityPerson.lastName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="auditorEntityPerson.lastName"  />
                        <div class="portal-error">
                            <s:errors field="auditorEntityPerson.lastName" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditor.workPhone" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="auditor.workPhone"  />
                        <div class="portal-error">
                            <s:errors field="auditor.workPhone" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                            <s:label for="Format: xxx-xxx-xxxx" />
                    </div>
                    <div class="portal-col-66">
                        <s:label for="Enter with dashes; example: 405-555-5555" />
                    </div>
                </div>                        
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorAddress.addressLine1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="auditorAddress.addressLine1"  />
                        <div class="portal-error">
                            <s:errors field="auditorAddress.addressLine1" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorAddress.county" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="auditorAddress.county">
                            <s:option value="">Select a county...</s:option>
                            <s:options-enumeration enum="com.pwc.us.common.model.OklahomaCounty" />
                        </s:select>
                        <div class="portal-error">
                            <s:errors field="auditorAddress.county" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorAddress.city" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="auditorAddress.city"  />
                        <div class="portal-error">
                            <s:errors field="auditorAddress.city" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorAddress.state" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="auditorAddress.state">
                            <s:option value="">Select a state...</s:option>
                            <s:options-enumeration enum="com.pwc.us.common.model.UsState" />
                        </s:select>
                        <div class="portal-error">
                            <s:errors field="auditorAddress.state" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="auditorAddress.postalCode" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="auditorAddress.postalCode"  />
                        <div class="portal-error">
                            <s:errors field="auditorAddress.postalCode" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.Section.PreviousCoverage" />
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="hadPreviousCoverage" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="hadPreviousCoverageYes" name="hadPreviousCoverage" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="hadPreviousCoverageNo" name="hadPreviousCoverage" value="false" />no</label>
                        <div class="portal-error">
                            <label for="hadPreviousCoverage" class="error"></label>
                            <s:errors field="hadPreviousCoverage" />
                        </div>
                    </fieldset>
                </div> 
                <div id="previous_coverage" style="display:none">
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.carrierName" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[0].carrier"  />
                        </div>
                        <s:errors field="priorPolicies[0].carrier" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.policyNumber" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[0].policyNumber"  />
                        </div>
                        <s:errors field="priorPolicies[0].policyNumber" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.dateCancelledOrExpired" />
                        </div>
                        <div class="portal-col-66">
                            <s:text class="portal-width-25" name="priorPolicies[0].expirationDate"  formatPattern="MM-dd-yyyy"/> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <s:errors field="priorPolicies[0].expirationDate" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.anniversaryDate" />
                        </div>
                        <div class="portal-col-66">
                            <s:text class="portal-width-25" name="priorPolicies[0].anniversaryDateExt" formatPattern="MM-dd-yyyy"/> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <s:errors field="priorPolicies[0].anniversaryDateExt" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="common.state" />
                        </div>
                        <div class="portal-col-66">
                            <s:select name="priorPolicies[0].carriersStateExt">
                                <s:option value="">Select a state...</s:option>
                                <s:options-enumeration enum="com.pwc.us.common.model.UsState" />
                            </s:select>
                        </div>
                        <s:errors field="priorPolicies[0].carriersStateExt" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.emod" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[0].expMod"  />
                        </div>
                        <s:errors field="priorPolicies[0].expMod" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.emodEffectiveDate" />
                        </div>
                        <div class="portal-col-66">
                            <s:text class="portal-width-25" name="priorPolicies[0].expModEffDateExt" formatPattern="MM-dd-yyyy" />  <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <s:errors field="priorPolicies[0].expModEffDateExt" />
                            </div>
                        </div>

                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.ratingIdNumber" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[0].ratingIdExt"  />
                        </div>
                        <s:errors field="priorPolicies[0].ratingIdExt" />
                    </div>
                    <div class="portal-row">
                        <hr>
                        <s:label class="sectionTitle" for="qr.Section.CarrierInfo" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.carrierName1" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[1].carrier"  />
                        </div>
                        <s:errors field="priorPolicies[1].carrier" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.policyNumber1" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[1].policyNumber"  />
                        </div>
                        <s:errors field="priorPolicies[1].policyNumber" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.policyPeriod1" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[1].policyPeriodExt"  />
                        </div>
                        <s:errors field="priorPolicies[1].policyPeriodExt" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.carrierName2" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[2].carrier"  />
                        </div>
                        <s:errors field="priorPolicies[2].carrier" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.policyNumber2" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[2].policyNumber"  />
                        </div>
                        <s:errors field="priorPolicies[2].policyNumber" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.policyPeriod2" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[2].policyPeriodExt"  />
                        </div>
                        <s:errors field="priorPolicies[2].policyPeriodExt" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.carrierName3" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[3].carrier"  />
                        </div>
                        <s:errors field="priorPolicies[3].carrier" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.policyNumber3" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[3].policyNumber"  />
                        </div>
                        <s:errors field="priorPolicies[3].policyNumber" />
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.policyPeriod3" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="priorPolicies[3].policyPeriodExt"  />
                        </div>
                        <s:errors field="priorPolicies[3].policyPeriodExt" />
                    </div>
                </div>
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.Section.CsoSupplemental" />
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="ownAnotherBusiness.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="ownAnotherBusinessYes" name="ownAnotherBusiness.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="ownAnotherBusinessNo" name="ownAnotherBusiness.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <s:errors field="ownAnotherBusiness.booleanAnswer" />
                        <label for="ownAnotherBusiness.booleanAnswer" class="error"></label>
                    </div>
                </div> 
                <div id="requiredIfOtherOKBus" style="display: none">
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-66 portal-legend portal-required"><s:label for="needWCAndLiability.booleanAnswer" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" id="needWCAndLiabilityYes" name="needWCAndLiability.booleanAnswer" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" id="needWCAndLiabilityNo" name="needWCAndLiability.booleanAnswer" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="needWCAndLiability.booleanAnswer" class="error"></label>
                            <s:errors field="needWCAndLiability.booleanAnswer" />
                        </div>
                    </div> 
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-66 portal-legend portal-required"><s:label for="quoteAllOKBusiness.booleanAnswer" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" id="quoteAllOKBusinessYes" name="quoteAllOKBusiness.booleanAnswer" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" id="quoteAllOKBusinessNo" name="quoteAllOKBusiness.booleanAnswer" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="quoteAllOKBusiness.booleanAnswer" class="error"></label>
                            <s:errors field="quoteAllOKBusiness.booleanAnswer" />
                        </div>
                    </div> 
                    <div class="portal-row" id="quoteAllOKBusinessExplain" style="display:none">
                        <div class="portal-col-pix-110 portal-required">
                            <s:label class="portal-legend" for="quoteAllOKBusinessExplain.textAnswer" />
                        </div>
                        <div class="portal-col-pix-450">
                            <s:textarea name="quoteAllOKBusinessExplain.textAnswer" />
                            <div class="portal-error">
                                <s:errors field="quoteAllOKBusinessExplain.textAnswer" />
                                <label for="quoteAllOKBusinessExplain.textAnswer" class="error"></label>                                
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="employeesOutsideOK.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="employeesOutsideOKYes" name="employeesOutsideOK.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="employeesOutsideOKNo" name="employeesOutsideOK.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="employeesOutsideOK.booleanAnswer" class="error"></label>
                        <s:errors field="employeesOutsideOK.booleanAnswer" />
                    </div>
                </div>  
                <div class="portal-row" id="employeesOutsideOKText" style="display:none">
                    <div class="portal-col-33 portal-required">
                        <s:label class="portal-legend" for="employeesOutsideOKText.textAnswer" />
                    </div>
                    <div class="portal-col-66">
                        <s:textarea name="employeesOutsideOKText.textAnswer" />
                        <div class="portal-error">
                            <s:errors field="employeesOutsideOKText.textAnswer" />
                            <label for="employeesOutsideOKText.textAnswer" class="error"></label>  
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="businessOperateOutsideOK.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="businessOperateOutsideOKYes" name="businessOperateOutsideOK.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="businessOperateOutsideOKNo" name="businessOperateOutsideOK.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="businessOperateOutsideOK.booleanAnswer" class="error"></label>
                        <s:errors field="businessOperateOutsideOK.booleanAnswer" />
                    </div>
                </div>  
                <div class="portal-row" id="listOutsideOKStates" style="display: none">
                    <div class="portal-col-pix-110 portal-required">
                        <s:label class="portal-legend" for="businessOperateOutsideOKText.textAnswer" />
                    </div>
                    <div class="portal-col-pix-450">
                        <s:textarea name="businessOperateOutsideOKText.textAnswer" />
                        <div class="portal-error">
                            <s:errors field="businessOperateOutsideOKText.textAnswer" />
                            <label for="businessOperateOutsideOKText.textAnswer" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="terminatingBusiness.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="terminatingBusinessYes" name="terminatingBusiness.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="terminatingBusinessNo" name="terminatingBusiness.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="terminatingBusiness.booleanAnswer" class="error"></label>
                        <s:errors field="terminatingBusiness.booleanAnswer" />
                    </div>
                </div> 
                <div class="portal-row" id="terminatingBusinessText" style="display:none">
                    <div class="portal-col-pix-110 portal-required">
                        <s:label class="portal-legend" for="terminatingBusinessText.textAnswer" />
                    </div>
                    <div class="portal-col-pix-450">
                        <s:textarea name="terminatingBusinessText.textAnswer" />
                        <div class="portal-error">
                            <s:errors field="terminatingBusinessText.textAnswer" />
                            <label for="terminatingBusinessText.textAnswer" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="associationDeniedInsurance.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="associationDeniedInsuranceYes" name="associationDeniedInsurance.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="associationDeniedInsuranceNo" name="associationDeniedInsurance.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="associationDeniedInsurance.booleanAnswer" class="error"></label>
                        <s:errors field="associationDeniedInsurance.booleanAnswer" />
                    </div>
                </div> 
                <div class="portal-row" id="associationDeniedInsuranceText" style="display:none">
                    <div class="portal-col-pix-110 portal-required">
                        <s:label class="portal-legend" for="associationDeniedInsuranceText.textAnswer" />
                    </div>
                    <div class="portal-col-pix-450">
                        <s:textarea name="associationDeniedInsuranceText.textAnswer" />
                        <div class="portal-error">
                            <s:errors field="associationDeniedInsuranceText.textAnswer" />
                            <label for="associationDeniedInsuranceText.textAnswer" class="error"></label>  
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="employDomestics.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="employDomesticsYes" name="employDomestics.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="employDomesticsNo" name="employDomestics.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="employDomestics.booleanAnswer" class="error"></label>
                        <s:errors field="employDomestics.booleanAnswer" />
                    </div>
                </div> 
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="farmEmployees.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="farmEmployeesYes" name="farmEmployees.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="farmEmployeesNo" name="farmEmployees.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="farmEmployees.booleanAnswer" class="error"></label>
                        <s:errors field="farmEmployees.booleanAnswer" />
                    </div>
                </div> 
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="familyMembers.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="familyMembersYes" name="familyMembers.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="familyMembersNo" name="familyMembers.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="familyMembers.booleanAnswer" class="error"></label>
                        <s:errors field="familyMembers.booleanAnswer" />
                    </div>
                </div> 
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="hqOutsideOK.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="hqOutsideOKYes" name="hqOutsideOK.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="hqOutsideOKNo" name="hqOutsideOK.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="hqOutsideOK.booleanAnswer" class="error"></label>
                        <s:errors field="hqOutsideOK.booleanAnswer" />
                    </div>
                </div>  
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="oosEmployees.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="oosEmployeesYes" name="oosEmployees.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="oosEmployeesNo" name="oosEmployees.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="oosEmployees.booleanAnswer" class="error"></label>
                        <s:errors field="oosEmployees.booleanAnswer" />
                    </div>
                </div> 
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.Section.Supplemental" />
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="boatsPlanes.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="boatsPlanesYes" name="boatsPlanes.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="boatsPlanesNo" name="boatsPlanes.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <s:errors field="boatsPlanes.booleanAnswer" />
                        <label for="boatsPlanes.booleanAnswer" class="error"></label>
                    </div>
                </div> 
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="hazmat.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="hazmatYes" name="hazmat.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="hazmatNo" name="hazmat.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="hazmat.booleanAnswer" class="error"></label>
                    </div>
                </div> 
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="aboveOrBelowGround.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="aboveOrBelowGroundYes" name="aboveOrBelowGround.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="aboveOrBelowGroundNo" name="aboveOrBelowGround.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="aboveOrBelowGround.booleanAnswer" class="error"></label>
                        <s:errors field="aboveOrBelowGround.booleanAnswer" />
                    </div>
                </div> 
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="onOrOverWater.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="onOrOverWaterYes" name="onOrOverWater.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="onOrOverWaterNo" name="onOrOverWater.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="onOrOverWater.booleanAnswer" class="error"></label>
                        <s:errors field="onOrOverWater.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="engagedInOtherBusiness.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="engagedInOtherBusinessYes" name="engagedInOtherBusiness.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="engagedInOtherBusinessNo" name="engagedInOtherBusiness.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="engagedInOtherBusiness.booleanAnswer" class="error"></label>
                        <s:errors field="engagedInOtherBusiness.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="subcontractorsUsed.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="subcontractorsUsedYes" name="subcontractorsUsed.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="subcontractorsUsedNo" name="subcontractorsUsed.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="subcontractorsUsed.booleanAnswer" class="error"></label>
                        <s:errors field="subcontractorsUsed.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row" id="subcontractorsPercent" style="display:none">
                    <div class="portal-col-33 portal-required">
                        <s:label class="portal-legend" for="subcontractorsPercent.integerAnswer" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="subcontractorsPercent.integerAnswer" />
                        <div class="portal-error">
                            <s:errors field="subcontractorsPercent.integerAnswer" />
                            <label for="subcontractorsPercent.integerAnswer" class="error"></label>                            
                        </div>
                    </div>
                </div> 
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="anyWorkSublet.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="anyWorkSubletYes" name="anyWorkSublet.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="anyWorkSubletNo" name="anyWorkSublet.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="anyWorkSublet.booleanAnswer" class="error"></label>
                        <s:errors field="anyWorkSublet.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="safetyProgram.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="safetyProgramYes" name="safetyProgram.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="safetyProgramNo" name="safetyProgram.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="safetyProgram.booleanAnswer" class="error"></label>
                        <s:errors field="safetyProgram.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="groupTransportation.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="groupTransportationYes" name="groupTransportation.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="groupTransportationNo" name="groupTransportation.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="groupTransportation.booleanAnswer" class="error"></label>
                        <s:errors field="groupTransportation.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="ageRange.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="ageRangeYes" name="ageRange.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="ageRangeNo" name="ageRange.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="ageRange.booleanAnswer" class="error"></label>
                        <s:errors field="ageRange.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="seasonalEmployees.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="seasonalEmployeesYes" name="seasonalEmployees.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="seasonalEmployeesNo" name="seasonalEmployees.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="seasonalEmployees.booleanAnswer" class="error"></label>
                        <s:errors field="seasonalEmployees.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="volunteer.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="volunteerYes" name="volunteer.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="volunteerNo" name="volunteer.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="volunteer.booleanAnswer" class="error"></label>
                        <s:errors field="volunteer.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="disabledEmployees.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="disabledEmployeesYes" name="disabledEmployees.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="disabledEmployeesNo" name="disabledEmployees.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="disabledEmployees.booleanAnswer" class="error"></label>
                        <s:errors field="disabledEmployees.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="oosTravel.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="oosTravelYes" name="oosTravel.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="oosTravelNo" name="oosTravel.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="oosTravel.booleanAnswer" class="error"></label>
                        <s:errors field="oosTravel.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="athleticSponsorship.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="athleticSponsorshipYes" name="athleticSponsorship.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="athleticSponsorshipNo" name="athleticSponsorship.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="athleticSponsorship.booleanAnswer" class="error"></label>
                        <s:errors field="athleticSponsorship.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="physicalsRequired.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="physicalsRequiredYes" name="physicalsRequired.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="physicalsRequiredNo" name="physicalsRequired.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="physicalsRequired.booleanAnswer" class="error"></label>
                        <s:errors field="physicalsRequired.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="otherInsurance.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="otherInsuranceYes" name="otherInsurance.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="otherInsuranceNo" name="otherInsurance.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="otherInsurance.booleanAnswer" class="error"></label>
                        <s:errors field="otherInsurance.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="priorDeclined.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="priorDeclinedYes" name="priorDeclined.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="priorDeclinedNo" name="priorDeclined.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="priorDeclined.booleanAnswer" class="error"></label>
                        <s:errors field="priorDeclined.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="employeeHealthPlans.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="employeeHealthPlansYes" name="employeeHealthPlans.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="employeeHealthPlansNo" name="employeeHealthPlans.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="employeeHealthPlans.booleanAnswer" class="error"></label>
                        <s:errors field="employeeHealthPlans.booleanAnswer" />
                    </div>

                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="laborInterchange.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="laborInterchangeYes" name="laborInterchange.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="laborInterchangeNo" name="laborInterchange.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="laborInterchange.booleanAnswer" class="error"></label>
                        <s:errors field="laborInterchange.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="leaseEmployees.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="leaseEmployeesYes" name="leaseEmployees.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="leaseEmployeesNo" name="leaseEmployees.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="leaseEmployees.booleanAnswer" class="error"></label>
                        <s:errors field="leaseEmployees.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="workAtHome.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="workAtHomeYes" name="workAtHome.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="workAtHomeNo" name="workAtHome.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="workAtHome.booleanAnswer" class="error"></label>
                        <s:errors field="workAtHome.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="taxLiens.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="taxLiensYes" name="taxLiens.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="taxLiensNo" name="taxLiens.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="taxLiens.booleanAnswer" class="error"></label>
                        <s:errors field="taxLiens.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <fieldset class="portal-fieldset">
                        <div class="portal-col-66 portal-legend portal-required"><s:label for="unpaidPremium.booleanAnswer" /></div>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="unpaidPremiumYes" name="unpaidPremium.booleanAnswer" value="true"/>yes</label>
                        <label class="portal-radio-button-label">
                            <s:radio class="portal-radio" id="unpaidPremiumNo" name="unpaidPremium.booleanAnswer" value="false" />no</label>
                    </fieldset>
                    <div class="portal-error">
                        <label for="unpaidPremium.booleanAnswer" class="error"></label>
                        <s:errors field="unpaidPremium.booleanAnswer" />
                    </div>
                </div>
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.Section.OwnersOfficers" />
                </div>
                <div id="owner_officer" class="ownerOfficer">
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.title" />
                        </div>
                        <div class="portal-col-66">
                            <s:select name="ownerOfficers[0].relationship">
                                <s:option value="">Select...</s:option>
                                <s:options-collection collection="${actionBean.ownerOfficerRelationships}" value="code" label="name"/>
                            </s:select>
                            <div class="portal-error">
                                <label for="ownerOfficers[0].relationship" class="error"></label>
                                <s:errors field="ownerOfficers[0].relationship" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="common.firstName" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[0].entityPerson.firstName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[0].entityPerson.firstName" class="error"></label>
                                <s:errors field="ownerOfficers[0].entityPerson.firstName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="common.middleInitial" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[0].entityPerson.middleName" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[0].entityPerson.middleName" class="error"></label>
                                <s:errors field="ownerOfficers[0].entityPerson.middleName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="common.lastName"  />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[0].entityPerson.lastName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[0].entityPerson.lastName" class="error"></label>
                                <s:errors field="ownerOfficers[0].entityPerson.lastName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.ssn" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[0].taxID" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[0].taxID" class="error"></label>
                                <s:errors field="ownerOfficers[0].taxID" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.activeInBusiness" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[0].activeInBusiness" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[0].activeInBusiness" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[0].activeInBusiness" class="error"></label>
                            <s:errors field="ownerOfficers[0].activeInBusiness" />
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.coverageDesired" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[0].coverageDesired" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[0].coverageDesired" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[0].coverageDesired" class="error"></label>
                            <s:errors field="ownerOfficers[0].coverageDesired" />
                        </div>
                    </div>
                </div>
                <div id="owner_officer_1" class="addlOwnerOfficer ownerOfficer" style="display:none">
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.title1" />
                        </div>
                        <div class="portal-col-66">
                            <s:select name="ownerOfficers[1].relationship">
                                <s:option value="">Select...</s:option>
                                <s:options-collection collection="${actionBean.ownerOfficerRelationships}" value="code" label="name"/>
                            </s:select>
                            <div class="portal-error">
                                <label for="ownerOfficers[1].relationship" class="error"></label>
                                <s:errors field="ownerOfficers[1].relationship" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.firstName1" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[1].entityPerson.firstName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[1].entityPerson.firstName" class="error"></label>
                                <s:errors field="ownerOfficers[1].entityPerson.firstName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.middleName1" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[1].entityPerson.middleName" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[1].entityPerson.middleName" class="error"></label>
                                <s:errors field="ownerOfficers[1].entityPerson.middleName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.lastName1"  />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[1].entityPerson.lastName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[1].entityPerson.lastName" class="error"></label>
                                <s:errors field="ownerOfficers[1].entityPerson.lastName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.ssn1" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[1].taxID" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[1].taxID" class="error"></label>
                                <s:errors field="ownerOfficers[1].taxID" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.activeInBusiness" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[1].activeInBusiness" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[1].activeInBusiness" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[1].activeInBusiness" class="error"></label>
                            <s:errors field="ownerOfficers[1].activeInBusiness" />
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.coverageDesired" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[1].coverageDesired" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[1].coverageDesired" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[1].coverageDesired" class="error"></label>
                            <s:errors field="ownerOfficers[1].coverageDesired" />
                        </div>
                    </div>
                </div>
                <div id="owner_officer_2" class="addlOwnerOfficer ownerOfficer" style="display:none">
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.title2" />
                        </div>
                        <div class="portal-col-66">
                            <s:select name="ownerOfficers[2].relationship">
                                <s:option value="">Select...</s:option>
                                <s:options-collection collection="${actionBean.ownerOfficerRelationships}" value="code" label="name"/>
                            </s:select>
                            <div class="portal-error">
                                <label for="ownerOfficers[2].relationship" class="error"></label>
                                <s:errors field="ownerOfficers[2].relationship" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.firstName2" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[2].entityPerson.firstName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[2].entityPerson.firstName" class="error"></label>
                                <s:errors field="ownerOfficers[2].entityPerson.firstName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.middleName2" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[2].entityPerson.middleName" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[2].entityPerson.middleName" class="error"></label>
                                <s:errors field="ownerOfficers[2].entityPerson.middleName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.lastName2"  />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[2].entityPerson.lastName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[2].entityPerson.lastName" class="error"></label>
                                <s:errors field="ownerOfficers[2].entityPerson.lastName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.ssn2" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[2].taxID" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[2].taxID" class="error"></label>
                                <s:errors field="ownerOfficers[2].taxID" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.activeInBusiness" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[2].activeInBusiness" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[2].activeInBusiness" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[2].activeInBusiness" class="error"></label>
                            <s:errors field="ownerOfficers[2].activeInBusiness" />
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.coverageDesired" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[2].coverageDesired" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[2].coverageDesired" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[2].coverageDesired" class="error"></label>
                            <s:errors field="ownerOfficers[2].coverageDesired" />
                        </div>
                    </div>
                </div>
                <div id="owner_officer_3" class="addlOwnerOfficer ownerOfficer" style="display:none">
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.title3" />
                        </div>
                        <div class="portal-col-66">
                            <s:select name="ownerOfficers[3].relationship">
                                <s:option value="">Select...</s:option>
                                <s:options-collection collection="${actionBean.ownerOfficerRelationships}" value="code" label="name"/>
                            </s:select>
                            <div class="portal-error">
                                <label for="ownerOfficers[3].relationship" class="error"></label>
                                <s:errors field="ownerOfficers[3].relationship" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.firstName3" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[3].entityPerson.firstName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[3].entityPerson.firstName" class="error"></label>
                                <s:errors field="ownerOfficers[3].entityPerson.firstName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.middleName3" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[3].entityPerson.middleName" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[3].entityPerson.middleName" class="error"></label>
                                <s:errors field="ownerOfficers[3].entityPerson.middleName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.lastName3"  />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[3].entityPerson.lastName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[3].entityPerson.lastName" class="error"></label>
                                <s:errors field="ownerOfficers[3].entityPerson.lastName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.ssn3" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[3].taxID" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[3].taxID" class="error"></label>
                                <s:errors field="ownerOfficers[3].taxID" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.activeInBusiness" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[3].activeInBusiness" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[3].activeInBusiness" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[3].activeInBusiness" class="error"></label>
                            <s:errors field="ownerOfficers[3].activeInBusiness" />
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.coverageDesired" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[3].coverageDesired" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[3].coverageDesired" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[3].coverageDesired" class="error"></label>
                            <s:errors field="ownerOfficers[3].coverageDesired" />
                        </div>
                    </div>
                </div>
                <div id="owner_officer_4" class="addlOwnerOfficer ownerOfficer" style="display:none">
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.title4" />
                        </div>
                        <div class="portal-col-66">
                            <s:select name="ownerOfficers[4].relationship">
                                <s:option value="">Select...</s:option>
                                <s:options-collection collection="${actionBean.ownerOfficerRelationships}" value="code" label="name"/>
                            </s:select>
                            <div class="portal-error">
                                <label for="ownerOfficers[4].relationship" class="error"></label>
                                <s:errors field="ownerOfficers[4].relationship" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.firstName4" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[4].entityPerson.firstName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[4].entityPerson.firstName" class="error"></label>
                                <s:errors field="ownerOfficers[4].entityPerson.firstName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.middleName4" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[4].entityPerson.middleName" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[4].entityPerson.middleName" class="error"></label>
                                <s:errors field="ownerOfficers[4].entityPerson.middleName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.lastName4"  />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[4].entityPerson.lastName" class="ownerOfficerContact" />
                            <div class="portal-error">
                                <label for="ownerOfficers[4].entityPerson.lastName" class="error"></label>
                                <s:errors field="ownerOfficers[4].entityPerson.lastName" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="qr.ssn4" />
                        </div>
                        <div class="portal-col-66">
                            <s:text name="ownerOfficers[4].taxID" class="ownerOfficerContact"  />
                            <div class="portal-error">
                                <label for="ownerOfficers[4].taxID" class="error"></label>
                                <s:errors field="ownerOfficers[4].taxID" />
                            </div>
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.activeInBusiness" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[4].activeInBusiness" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[4].activeInBusiness" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[4].activeInBusiness" class="error"></label>
                            <s:errors field="ownerOfficers[4].activeInBusiness" />
                        </div>
                    </div>
                    <div class="portal-row">
                        <fieldset class="portal-fieldset">
                            <div class="portal-col-33 portal-legend"><s:label for="qr.coverageDesired" /></div>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[4].coverageDesired" value="true"/>yes</label>
                            <label class="portal-radio-button-label">
                                <s:radio class="portal-radio" name="ownerOfficers[4].coverageDesired" value="false" />no</label>
                        </fieldset>
                        <div class="portal-error">
                            <label for="ownerOfficers[4].coverageDesired" class="error"></label>
                            <s:errors field="ownerOfficers[4].coverageDesired" />
                        </div>
                    </div>
                </div>              
                <div class="portal-row" >
                    <a href="#" id="addOwnerOfficer">Add Owner/Officer</a> <a href="#" id="removeOwnerOfficer" style='display: none; padding-left: 50px'>Remove Owner/Officer</a>
                </div>
                <div class="sectionSubTitle onlyFiveOwnerOfficers" style="display:none">
                    You can add only five owner officers. If you need to add more, please contact CompSource directly.<br/><br/>
                </div>
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.Section.AdditionalLocations" />
                </div>
                <div id="additionalLocs">
                    <div id="addl_location_0" class="addlLocation" style="display:none">
                    <div class="portal-row"><hr><label class="sectionTitle">Additional Location 1</label></div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="qr.mailingAddress" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[0].accountLocation.addressLine1"/></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.county" class="portal-label-33" /></div>
                            <div class="portal-col-66">
                                <s:select name="additionalLocations[0].accountLocation.county">
                                    <s:option value="">Select a county...</s:option>
                                    <s:options-enumeration enum="com.pwc.us.common.model.OklahomaCounty" />
                                </s:select>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.city" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[0].accountLocation.city" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.state" class="portal-label-33" /></div>
                            <div class="portal-col-66">
                                <s:select name="additionalLocations[0].accountLocation.state">
                                    <s:option value="">Select a state...</s:option>
                                    <s:options-enumeration enum="com.pwc.us.common.model.UsState" />
                                </s:select>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.postalCode" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[0].accountLocation.postalCode" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.telephone" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[0].accountLocation.phone" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="qr.locationName" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[0].accountLocation.locationName" /></div>
                        </div>
                    </div>
                    <div id="addl_location_1" class="addlLocation" style="display:none">
                        <div><div class="portal-row"><hr><label class="sectionTitle">Additional Location 2</label></div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="qr.mailingAddress" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[1].accountLocation.addressLine1"/></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.county" class="portal-label-33" /></div>
                            <div class="portal-col-66">
                                <s:select name="additionalLocations[1].accountLocation.county">
                                    <s:option value="">Select a county...</s:option>
                                    <s:options-enumeration enum="com.pwc.us.common.model.OklahomaCounty" />
                                </s:select>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.city" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[1].accountLocation.city" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.state" class="portal-label-33" /></div>
                            <div class="portal-col-66">
                                <s:select name="additionalLocations[1].accountLocation.state">
                                    <s:option value="">Select a state...</s:option>
                                    <s:options-enumeration enum="com.pwc.us.common.model.UsState" />
                                </s:select>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.postalCode" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[1].accountLocation.postalCode" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.telephone" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[1].accountLocation.phone" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="qr.locationName" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[1].accountLocation.locationName" /></div>
                        </div>
                    </div>
                    <div id="addl_location_2" class="addlLocation" style="display:none">
                        <div class="portal-row"><hr><label class="sectionTitle">Additional Location 3</label></div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="qr.mailingAddress" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[2].accountLocation.addressLine1"/></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.county" class="portal-label-33" /></div>
                            <div class="portal-col-66">
                                <s:select name="additionalLocations[2].accountLocation.county">
                                    <s:option value="">Select a county...</s:option>
                                    <s:options-enumeration enum="com.pwc.us.common.model.OklahomaCounty" />
                                </s:select>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.city" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[2].accountLocation.city" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.state" class="portal-label-33" /></div>
                            <div class="portal-col-66">
                                <s:select name="additionalLocations[2].accountLocation.state">
                                    <s:option value="">Select a state...</s:option>
                                    <s:options-enumeration enum="com.pwc.us.common.model.UsState" />
                                </s:select>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.postalCode" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[2].accountLocation.postalCode" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="common.telephone" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[2].accountLocation.phone" /></div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33"><s:label for="qr.locationName" class="portal-label-33" /></div>
                            <div class="portal-col-66"><s:text name="additionalLocations[2].accountLocation.locationName" /></div>
                        </div>
                    </div>
                </div>
                <div class="portal-row" >
                    <a href="#" id="addAddlLocation">Add Additional Location</a> <a href="#" id="removeAddlLocation" style='display: none; padding-left: 50px'>Remove Additional Location</a>
                </div>
                <div class="sectionSubTitle onlyThreeAddlLocations" style="display:none">
                    You can add only three additional locations. If you need to add more, please contact CompSource directly.<br/><br/>
                </div>
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.Section.ContactInfo" />
                </div>
                <div class="portal-row">
                    <hr>
                    <s:label class="sectionTitle" for="qr.yourName" />
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="contactEntityPerson.firstName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="contactEntityPerson.firstName"  />
                        <div class="portal-error">
                            <s:errors field="contactEntityPerson.firstName" />
                            <label for="contactEntityPerson.firstName" class="error"></label>                        
                        </div>
                    </div>

                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="contactEntityPerson.middleName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="contactEntityPerson.middleName"  />
                        <div class="portal-error">
                            <s:errors field="contactEntityPerson.middleName" />
                            <label for="contactEntityPerson.middleName" class="error"></label>  
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="contactEntityPerson.lastName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="contactEntityPerson.lastName"  />
                        <div class="portal-error">
                            <s:errors field="contactEntityPerson.lastName" />
                            <label for="contactEntityPerson.lastName" class="error"></label>  
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="contactInfo.workPhone" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="contactInfo.workPhone"  />
                        <div class="portal-error">
                            <s:errors field="contactInfo.workPhone" />
                            <label for="contactInfo.workPhone" class="error"></label>                        
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="contactInfo.emailAddress1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="contactInfo.emailAddress1"  />
                        <div class="portal-error">
                            <s:errors field="contactInfo.emailAddress1" />
                            <label for="contactInfo.emailAddress1" class="error"></label>                       
                        </div>
                    </div>

                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="contactInfo.faxPhone" ></s:label>
                    </div>
                    <div class="portal-col-66">
                        <s:text name="contactInfo.faxPhone"  />
                        <div class="portal-error">
                            <s:errors field="contactInfo.faxPhone" />
                            <label for="contactInfo.faxPhone" class="error"></label>
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="contactInfo.relationship" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="contactInfo.relationship">
                            <s:option value="">Select...</s:option>
                            <s:options-collection collection="${actionBean.addlIntRelationships}" 
                                                  value="code" label="name" />
                        </s:select>
                    </div>
                    <s:errors field="contactInfo.relationship" />
                    <label for="contactInfo.relationship" class="error"></label>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">&nbsp;</div>
                    <div class="portal-col-66">
                        <div class="portal-processing">
                            <img src="img/throbber_white_32.gif" /> Processing
                        </div>
                        <div class="portal-width-100">
                            <div class="portal-button">
                                <s:submit name="submit" value="Submit" class="portal-submit" id="submitRequestButton" onclick="submitProcessing()" />
                            </div>
                            <div class="portal-button">
                                <s:submit name="cancel" value="Cancel" class="portal-submit cancel" id="cancelRequestButton" />
                            </div>                            
                        </div>
                    </div>                    
                </div>
            </div>
        </s:form>
    </jsp:body>
</t:genericpage>
