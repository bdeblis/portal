<%-- 
    Document   : fnoi_form.jsp
    Created on : Sep 17, 2013, 11:00:49 AM
    Author     : Robert Snelling <robert.snelling@us.pwc.com>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
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
        <script type="text/javascript">
            var doi;
            $(document).ready(function() {
                hideShowInjuryDivs();
                hideShowReturnToWorkDiv();
                hideShowDeathDateDiv();
//                hideBodyPartDivs();
//                $('#cumulativeTraumaList').show();
                var txtbox = document.getElementById('doac');
                doi = txtbox.value;
            });


        function checkValue(sender) {
          if (doi !== sender.value) {
             alert("Changing the Date of Loss could impact the claim");
          }
        }
            
        </script>
        <div class="portal-error-top"><s:errors globalErrorsOnly="false" /><span id="globalError" style="color:#ff0000"></span></div>

        <s:form id="fnoiForm" beanclass="com.pwc.us.webui.stripes.action.FNOIFormActionBean">
            <div class="portal-form">

                <div class="sectionSubTitle">
                    To report an injury, please complete the form below.
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="policy.policyNumber" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="policy.policyNumber" value="${fnoiPolicyNumber}" readonly="readonly"/>
                        <div class="portal-error">
                            <s:errors field="policy.policyNumber" />
                        </div>
                    </div>
                </div>                    

                <!-- Claimant Information -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="FNOI.Section.Claimant" />
                </div>
                <div class="portal-row">
                    <div class="portal-col-100">
                        <s:label class="portal-label-100 portal-required" for="claim.incidentOnly" />
                        <s:select name="claim.incidentOnly" tabindex="48" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.YesNo" />
                        </s:select>
                        <div class="portal-error">
                            <label for="claim.incidentOnly" class="error"></label>
                            <s:errors field="claim.incidentOnly" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="claimantEP.firstName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="claimantEP.firstName" tabindex="1"/>
                        <div class="portal-error">
                            <label for="claimantEP.firstName" class="error"></label>
                            <s:errors field="claimantEP.firstName" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="claimantEP.middleName" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text class="portal-width-25" name="claimantEP.middleName" tabindex="2"/>
                        <div class="portal-error">
                            <label for="claimantEP.middleName" class="error"></label>
                            <s:errors field="claimantEP.middleName" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="claimantEP.lastName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="claimantEP.lastName" tabindex="3"/>
                        <div class="portal-error">
                            <label for="claimantEP.lastName" class="error"></label>
                            <s:errors field="claimantEP.lastName" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="claimant.emailAddress1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="claimant.emailAddress1" tabindex="4" />
                        <div class="portal-error">
                            <label for="claimant.emailAddress1" class="error"></label>
                            <s:errors field="claimant.emailAddress1" />                    
                        </div>
                    </div>
                </div>

                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="claimantAddress.addressLine1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="claimantAddress.addressLine1" tabindex="5" />
                        <div class="portal-error">
                            <label for="claimantAddress.addressLine1" class="error"></label>
                            <s:errors field="claimantAddress.addressLine1" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="claimantAddress.addressLine2" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="claimantAddress.addressLine2" tabindex="6" />
                        <div class="portal-error">
                            <label for="claimantAddress.addressLine2" class="error"></label>
                            <s:errors field="claimantAddress.addressLine2" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="claimantAddress.city"  />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-80" name="claimantAddress.city" tabindex="7"/>
                        <div class="portal-error">
                            <label for="claimantAddress.city" class="error"></label>
                            <s:errors field="claimantAddress.city" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">&nbsp;
                    </div>
                    <div class="portal-col-66">
                        <s:select name="claimantAddress.county" tabindex="8" >
                            <s:option value="">County...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.OklahomaCounty" />
                        </s:select>
                        <s:select name="claimantAddress.state" tabindex="9" >
                            <s:option value="">State...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.UsState" />
                        </s:select>
                        <d:text class="portal-width-25" name="claimantAddress.postalCode" placeholder="* Zip" tabindex="10" />
                        <div class="portal-error">
                            <label for="claimantAddress.county" class="error"></label>
                            <label for="claimantAddress.state" class="error"></label>
                            <label for="claimantAddress.postalCode" class="error"></label>
                            <s:errors field="claimantAddress.county" />                    
                            <s:errors field="claimantAddress.state" />                    
                            <s:errors field="claimantAddress.postalCode" />
                        </div>
                    </div>
                </div>
                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="claimant.homePhone" ></s:label>
                        </div>
                        <div class="portal-col-66">                    
                        <d:text class="portal-width-40" name="claimant.homePhone" placeholder="" tabindex="11" />
                        <div class="portal-error">   
                            <label for="claimant.homePhone" class="error"></label>
                            <s:errors field="claimant.homePhone" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="claimant.taxID" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <d:text class="portal-width-40" name="claimant.taxID" placeholder="" tabindex="12" />
                        <div class="portal-error">
                            <label for="claimant.taxID" class="error"></label>
                            <s:errors field="claimant.taxID" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="claimantEP.dateOfBirth" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-25" name="dateOfBirth" formatPattern="MM-dd-yyyy" tabindex="13" /> 
                        <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <label for="dateOfBirth" class="error"></label>
                            <s:errors field="dateOfBirth" /> 
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="claimantEP.gender" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="claimantEP.gender" tabindex="14" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.Gender" />
                        </s:select>
                        <div class="portal-error">
                            <label for="claimantEP.gender" class="error"></label>
                            <s:errors field="claimantEP.gender" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="employmentData.hireDateLabel" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text class="portal-width-25" name="dateOfHire" formatPattern="MM-dd-yyyy" tabindex="15" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <label for="dateOfHire" class="error"></label>
                            <s:errors field="dateOfHire" />
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="employmentData.wageAmount" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text class="portal-width-25" size="8" maxlength="8" name="employmentData.wageAmount" tabindex="16" />
                        <div class="portal-error">
                            <label for="employmentData.wageAmount" class="error"></label>
                            <s:errors field="employmentData.wageAmount" />
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="claimantEP.occupation" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="claimantEP.occupation" tabindex="17" />
                        <div class="portal-error">
                            <label for="claimantEP.occupation" class="error"></label>
                            <s:errors field="claimantEP.occupation" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row portal-spacer-10">
                    <div class="portal-col-100">
                        <s:label class="portal-label-100" for="employmentData.hireState" />
                        <s:select name="hireStateQuestion" tabindex="18" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.YesNo" />
                        </s:select>
                        <div class="portal-error">
                            <label for="employmentData.hireState" class="error"></label>
                            <s:errors field="employmentData.hireState" />
                        </div>
                    </div>
                </div>

                <!-- Injury Information -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="FNOI.Section.InjuryInformation" />
                </div>                
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="dateOfAccident.label" />
                    </div>
                    <div class="portal-col-66">    
                        <s:text class="portal-width-25" onblur="checkValue(this)" id="doac" name="dateOfAccident"  value="${fnoiDateOfAccident}" formatPattern="MM-dd-yyyy" tabindex="19" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                        <div class="portal-error">
                            <label for="dateOfAccident.valueFailedExpression" class="error"></label>
                            <s:errors field="dateOfAccident" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="timeOfAccident" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="timepick portal-width-25" name="timeOfAccident" tabindex="20" />
                        <div class="portal-error">
                            <label for="timeOfAccident" class="error"></label>
                            <s:errors field="timeOfAccident" />                    
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="dateReported.label" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-25" name="dateReported" formatPattern="MM-dd-yyyy" tabindex="21" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <label for="dateReported.valueFailedExpression" class="error"></label>
                            <s:errors field="dateReported" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="timeReported" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="timepick portal-width-25" name="timeReported" tabindex="20" />
                        <div class="portal-error">
                            <label for="timeReported" class="error"></label>
                            <s:errors field="timeReported" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="workDayTime" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="timepick portal-width-25" name="workDayTime" tabindex="22" />
                        <div class="portal-error">
                            <label for="workDayTime" class="error"></label>
                            <s:errors field="workDayTime" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="lastWorkedDate" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-25" name="lastWorkedDate" formatPattern="MM-dd-yyyy" tabindex="23" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <label for="lastWorkedDate" class="error"></label>
                            <s:errors field="lastWorkedDate" />                    
                        </div>
                    </div>
                </div>

                <div class="portal-row portal-spacer-10">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="returnToWorkQuestion" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="returnToWorkQuestion" id="returnToWorkQuestion" tabindex="24" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.YesNo" />
                        </s:select>
                        <div class="portal-error">
                            <label for="returnToWorkQuestion" class="error"></label>
                            <s:errors field="returnToWorkQuestion" />
                        </div>
                    </div>
                </div>
                <div id="returnToWorkDiv" class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="returnToWorkDate" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-25" name="returnToWorkDate" formatPattern="MM-dd-yyyy" tabindex="25" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <label for="returnToWorkDate" class="error"></label>
                            <s:errors field="returnToWorkDate" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row portal-spacer-10">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="deathDateQuestion" />
                    </div>
                    <div class="portal-col-66">    
                        <s:select name="deathDateQuestion" id="deathDateQuestion" tabindex="26" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.YesNo" />
                        </s:select>
                        <div class="portal-error">
                            <label for="deathDateQuestion" class="error"></label>
                            <s:errors field="deathDateQuestion" />
                        </div>
                    </div>
                </div>

                <div class="portal-row" id="deathDateDiv"  >
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="deathDate" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-25" name="deathDate" formatPattern="MM-dd-yyyy" tabindex="27" /> 
                        <div class="portal-error">
                            <label for="deathDate" class="error"></label>
                            <s:errors field="deathDate" />                    
                        </div>
                    </div>
                </div>

                <!-- Place of Accident -->
                <div class="sectionSubTitle">
                    <s:label for="FNOI.Section.InjuryInformation.LossLocation" />
                </div>                
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="lossLocation.addressLine1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="lossLocation.addressLine1" tabindex="28" />
                        <div class="portal-error">
                            <label for="lossLocation.addressLine1" class="error"></label>
                            <s:errors field="lossLocation-addressLine1" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="lossLocation.addressLine2" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="lossLocation.addressLine2" tabindex="29" />
                        <div class="portal-error">
                            <label for="lossLocation.addressLine2" class="error"></label>
                            <s:errors field="lossLocation-addressLine2" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="lossLocation.city" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-80" name="lossLocation.city" tabindex="30" />
                        <div class="portal-error">
                            <label for="lossLocation.city" class="error"></label>
                            <s:errors field="lossLocation-city" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">&nbsp;
                    </div>
                    <div class="portal-col-66">
                        <s:select name="lossLocation.county" tabindex="31" >
                            <s:option value="">County...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.OklahomaCounty" />
                        </s:select>
                        <s:select name="lossLocation.state" tabindex="32" >
                            <s:option value="">State...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.UsState" />
                        </s:select>
                        <d:text class="portal-width-25" name="lossLocation.postalCode" placeholder="Zip" tabindex="33" />
                        <div class="portal-error">
                            <label for="lossLocation.county" class="error"></label>
                            <label for="lossLocation.state" class="error"></label>
                            <label for="lossLocation.postalCode" class="error"></label>
                            <s:errors field="lossLocation-county" />                    
                            <s:errors field="lossLocation-state" />                    
                            <s:errors field="lossLocation-postalCode" />
                        </div>
                    </div>
                </div>

                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33  portal-required" for="injury.generalInjuryType" />
                    </div>
                    <div class="portal-col-66">   
                        <s:select name="injury.generalInjuryType" id="generalInjury" tabindex="34" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.fnoi.InjuryTypeEnum" />
                        </s:select>
                        <div class="portal-error">
                            <label for="generalInjuryType" class="error"></label>
                            <s:errors field="generalInjuryType" />
                        </div>
                    </div>
                </div>
                        
		<div class="portal-col-66r">   
                        <s:select name="injury.cumulativeInjuryType" id="cumulative" tabindex="34" >
                            <s:options-collection  collection="${cumulativeDetailedInjuryType}"  value="code"  label="name"/>
                        </s:select>
                        <div class="portal-error">
                            <label for="cumulativeTraumaError" class="error"></label>
                            <s:errors field="cumulativeTraumaError" />
                        </div>
                    </div>

		<div class="portal-col-66r">   
                        <s:select name="injury.detailedInjuryType" id="det" tabindex="34" >
                            <s:options-collection  collection="${specificDetailedInjuryType}" value="code"  label="name"/>
                        </s:select>
                        <div class="portal-error">
                            <label for="singleIncidentError" class="error"></label>
                            <s:errors field="singleIncidentError" />
                        </div>
                    </div>

      		<div class="portal-col-66r">   
                        <s:select name="injury.multipleInjuryType" id="multiple" tabindex="34" >
                            <s:options-collection  collection="${multipleInjuryType}"  value="code" label="name"/>
                        </s:select>
                        <div class="portal-error">
                            <label for="multipleInjuryError" class="error"></label>
                            <s:errors field="multipleInjuryError" />
                        </div>
                    </div>

   
		<div class="portal-col-66r">   
                        <s:select name="injury.occupationalInjuryType" id="occupational" tabindex="34"  >
                            <s:options-collection  collection="${occupationalInjuryType}"  value="code"  label="name"/>
                        </s:select>
                        <div class="portal-error">
                            <label for="occupationalError" class="error"></label>
                            <s:errors field="occupationalError" />
                        </div>
                    </div>

                <!-- body parts were here -->


                <!-- Sub-Section Physician Information -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="FNOI.Section.InjuryInformation.TreatingPhysician" />
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="physicianEP.firstName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="physicianEP.firstName" tabindex="38" />
                        <div class="portal-error">
                            <label for="physicianEP.firstName" class="error"></label>
                            <s:errors field="physicianEP.firstName" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="physicianEP.middleName" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text class="portal-width-25" name="physicianEP.middleName" tabindex="39" />
                        <div class="portal-error">
                            <label for="physicianEP.middleName" class="error"></label>
                            <s:errors field="physicianEP.middleName" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="physicianEP.lastName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="physicianEP.lastName" tabindex="40" />
                        <div class="portal-error">
                            <label for="physicianEP.lastName" class="error"></label>
                            <s:errors field="physicianEP.lastName" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="physicianAddress.addressLine1" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text name="physicianAddress.addressLine1" tabindex="41" />
                        <div class="portal-error">
                            <label for="physicianAddress.addressLine1" class="error"></label>
                            <s:errors field="physicianAddress.addressLine1" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="physicianAddress.addressLine2" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text name="physicianAddress.addressLine2" tabindex="42" />
                        <div class="portal-error">
                            <label for="physicianAddress.addressLine2" class="error"></label>
                            <s:errors field="physicianAddress.addressLine2" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="physicianAddress.city" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text class="portal-width-80" name="physicianAddress.city" tabindex="43" />
                        <div class="portal-error">
                            <label for="physicianAddress.city" class="error"></label>
                            <s:errors field="physicianAddress.city" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">&nbsp;
                    </div>
                    <div class="portal-col-66">
                        <s:select name="physicianAddress.county" tabindex="44" >
                            <s:option value="">County...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.OklahomaCounty" />
                        </s:select>
                        <s:select name="physicianAddress.state" tabindex="45" >
                            <s:option value="">State...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.UsState" />
                        </s:select>
                        <d:text class="portal-width-25" name="physicianAddress.postalCode" placeholder="Zip" tabindex="46" />
                        <div class="portal-error">
                            <label for="physicianAddress.county" class="error"></label>
                            <label for="physicianAddress.state" class="error"></label>
                            <label for="physicianAddress.postalCode" class="error"></label>
                            <s:errors field="physicianAddress.county" />                    
                            <s:errors field="physicianAddress.state" />                    
                            <s:errors field="physicianAddress.postalCode" />
                        </div>
                    </div>
                </div>

                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required portal-legend" for="claim.description" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:textarea name="claim.description" cols="60" rows="6" tabindex="47" />
                        <div class="portal-error">
                            <label for="claim.description" class="error"></label>
                            <s:errors field="claim.description" />
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-100">
                        <s:label class="portal-label-100 portal-required" for="claim.empQusValidity" />
                        <s:select name="claim.empQusValidity" tabindex="48" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.YesNo" />
                        </s:select>
                        <div class="portal-error">
                            <label for="claim.empQusValidity" class="error"></label>
                            <s:errors field="claim.empQusValidity" />
                        </div>
                    </div>
                </div>

                <!-- Sub-Section Supervisor Information -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="FNOI.Section.InjuryInformation.Supervisor" />
                </div>                

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="supervisorEP.heading" />
                    </div>
                    <div class="portal-col-66">
                        <d:text class="portal-width-40" name="supervisorEP.firstName" placeholder="First Name" tabindex="49"  />
                        <d:text class="portal-width-40" name="supervisorEP.lastName" placeholder="Last Name" tabindex="50" />
                        <div class="portal-error">
                            <label for="supervisorEP.firstName" class="error"></label>
                            <label for="supervisorEP.lastName" class="error"></label>
                            <s:errors field="supervisorEP.firstName" />
                            <s:errors field="supervisorEP.lastName" />                    
                        </div>
                    </div>
                </div>
                <!-- Sub-Section Witness Information -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="FNOI.Section.InjuryInformation.Witnesses" />
                </div>              

                <div id="witnessRows">
                    <div class="portal-row">
                        <div class="portal-col-33">
                            <s:label class="portal-label-33" for="witnessEP.heading" />
                        </div>
                        <div class="portal-col-66">
                            <d:text class="portal-width-40" name="witnessList[0].firstName" placeholder="First Name" tabindex="51" />
                            <d:text class="portal-width-40" name="witnessList[0].lastName" placeholder="Last Name" tabindex="52" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33" id="witnessRows">&nbsp;
                    </div>
                    <div class="portal-col-66">
                        <a href="#" id="addWitness">Add Witness</a>
                    </div>
                </div>

                <!-- Employer Information -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="FNOI.Section.EmployerInformation" />
                </div>                
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="policy.effectiveDate" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-25" name="policyFromDate" formatPattern="MM-dd-yyyy" tabindex="53" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <label for="policyFromDate" class="error"></label>
                            <s:errors field="policyFromDate" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="policy.expirationDate" />
                    </div>
                    <div class="portal-col-66">
                        <s:text class="portal-width-25" name="policyToDate" formatPattern="MM-dd-yyyy" tabindex="54" /> <s:label for="MM-DD-YYYY">MM-DD-YYYY</s:label>
                            <div class="portal-error">
                                <label for="policyToDate" class="error"></label>
                            <s:errors field="policyToDate" />                    
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="policyHolder.name" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="policyHolder.name" value="${empname}" tabindex="55" />
                        <div class="portal-error">
                            <label for="policyHolder.name" class="error"></label>
                            <s:errors field="policyHolder.name" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="policyLocation.locationNumber" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="policyLocation.locationNumber" tabindex="56" />
                        <div class="portal-error">
                            <label for="policyLocation.locationNumber" class="error"></label>
                            <s:errors field="policyLocation.locationNumber" />                    
                        </div>
                    </div>
                </div>                    
                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="policyLocationAddress.addressLine1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="policyLocationAddress.addressLine1" value="${empadd1}" tabindex="57" />
                        <div class="portal-error">
                            <label for="policyLocationAddress.addressLine1" class="error"></label>
                            <s:errors field="policyLocationAddress.addressLine1" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="policyLocationAddress.addressLine2" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="policyLocationAddress.addressLine2" tabindex="58" />
                        <div class="portal-error">
                            <label for="policyLocationAddress.addressLine2" class="error"></label>
                            <s:errors field="policyLocationAddress.addressLine2" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="policyLocationAddress.city" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text class="portal-width-80" name="policyLocationAddress.city"  value="${empcity}"  tabindex="59" />
                        <div class="portal-error">  
                            <label for="policyLocationAddress.city" class="error"></label>
                            <s:errors field="policyLocationAddress.city" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">&nbsp;
                    </div>
                    <div class="portal-col-66">
                        <s:select name="policyLocationAddress.county" tabindex="60" >
                            <s:option value="">County...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.OklahomaCounty" />
                        </s:select>
                        <s:select name="policyLocationAddress.state" tabindex="61" value="${empstate}" >
                            <s:option>* State...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.UsState" />
                        </s:select>
                        <d:text class="portal-width-25" name="policyLocationAddress.postalCode"  value="${empzip}" placeholder="* Zip" tabindex="62" />
                        <div class="portal-error">
                            <label for="policyLocationAddress.county" class="error"></label>
                            <label for="policyLocationAddress.state" class="error"></label>
                            <label for="policyLocationAddress.postalCode" class="error"></label>
                            <s:errors field="policyLocationAddress.county" />                    
                            <s:errors field="policyLocationAddress.state" />                    
                            <s:errors field="policyLocationAddress.postalCode" />
                        </div>
                    </div>
                </div>

                <div class="portal-row portal-spacer-5">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="policyHolder.workPhone" />
                    </div>
                    <div class="portal-col-66">
                        <d:text class="portal-width-40" name="policyHolder.workPhone"  value="${empphone}"  placeholder="xxx-xxx-xxxx" tabindex="63" />
                        <div class="portal-error">
                            <label for="policyHolder.workPhone" class="error"></label>
                            <s:errors field="policyHolder.workPhone" />                    
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="policy.accountOrgTypeExt" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="policy.accountOrgTypeExt" tabindex="64" >
                            <s:option value="">Select...</s:option>
                            <s:options-collection collection="${accountOrgTypeExt}" value="code" label="name" />
                        </s:select>
                        <div class="portal-error">
                            <label for="policy.accountOrgTypeExt" class="error"></label>
                            <s:errors field="policy.accountOrgTypeExt" />
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-legend" for="policy.accountTypeOfBusinessExt" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:textarea name="policy.accountTypeOfBusinessExt" cols="60" rows="6" tabindex="65" />
                        <div class="portal-error">
                            <label for="policy.accountTypeOfBusinessExt" class="error"></label>
                            <s:errors field="policy.accountTypeOfBusinessExt" />                            
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="policy.insuredSICCode" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:text class="portal-width-25" name="policy.insuredSICCode" tabindex="66" />
                        <div class="portal-error">
                            <label for="policy.insuredSICCode" class="error"></label>
                            <s:errors field="policy.insuredSICCode" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="claim.notesString" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <s:textarea name="claim.notesString" cols="60" rows="6" tabindex="67" />
                        <div class="portal-error">
                            <label for="claim.notesString" class="error"></label>
                            <s:errors field="claim.notesString" />                            
                        </div>
                    </div>
                </div>

                <!-- Reporter Information -->
                <div class="sectionTitle">
                    <hr>
                    <s:label for="FNOI.Section.ReporterInformation" />
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="reporterEP.firstName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="reporterEP.firstName" tabindex="68" />
                        <div class="portal-error">
                            <label for="reporterEP.firstName" class="error"></label>
                            <s:errors field="reporterEP.firstName" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required"  for="reporterEP.lastName" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="reporterEP.lastName" tabindex="69" />
                        <div class="portal-error">
                            <label for="reporterEP.lastName" class="error"></label>
                            <s:errors field="reporterEP.lastName" />                    
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33" for="reporter.emailAddress1" />
                    </div>
                    <div class="portal-col-66">
                        <s:text name="reporter.emailAddress1" tabindex="70" />
                        <div class="portal-error">
                            <label for="reporter.emailAddress1" class="error"></label>
                            <s:errors field="reporter.emailAddress1" />                    
                        </div>
                    </div>
                </div>    
                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required" for="reporter.workPhone" ></s:label>
                        </div>
                        <div class="portal-col-66">
                        <d:text class="portal-width-40" name="reporter.workPhone" placeholder="xxx-xxx-xxxx" tabindex="71" />
                        <div class="portal-error">
                            <label for="reporter.homePhone" class="error"></label>
                            <s:errors field="reporter.homePhone" />
                        </div>
                    </div>
                </div>

                <div class="portal-row">
                    <div class="portal-col-33">
                        <s:label class="portal-label-33 portal-required portal-legend" for="claim.reportedByType" />
                    </div>
                    <div class="portal-col-66">
                        <s:select name="claim.reportedByType" tabindex="72" >
                            <s:option value="">Select...</s:option>
                            <s:options-enumeration  enum="com.pwc.us.common.model.fnoi.ReporterCategoryEnum" />
                        </s:select>
                        <div class="portal-error">
                            <label for="claim.reportedByType" class="error"></label>
                            <s:errors field="claim.reportedByType" />
                        </div>
                    </div>
                </div>
                <div class="portal-row">
                    <div class="portal-col-33">
                        &nbsp;
                    </div>
                    <div class="portal-col-66">
                        <div class="portal-width-100">
                            <label for="claim.pressSubmitOnce" class="portal-label-66 portal-required portal-legend">Please click only once to avoid transaction duplication.</label>
                        </div>
                    </div>
                </div>        

                <div class="portal-row">
                    <div class="portal-col-33">&nbsp;
                    </div>
                    <div class="portal-col-66">
                        <div class="portal-width-100">
                            <div class="portal-button">
                                <s:submit name="submit" value="Submit" class="portal-submit" id="submit" onclick="submitProcessing()" tabindex="73" />
                            </div>
                            <div class="portal-button">
                                <s:submit name="cancel" value="Cancel" class="portal-submit cancel" id="cancel" tabindex="74" />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </s:form>
    </jsp:body>
</t:genericpage>