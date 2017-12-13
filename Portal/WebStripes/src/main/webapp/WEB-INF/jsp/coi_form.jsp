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
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="includePortalStyles" value="true" />

<shiro:authenticated>
    <c:set var="title" value="Certificate of Insurance Request" />
</shiro:authenticated>
<shiro:notAuthenticated>
    <c:set var="title" value="Certificate of Insurance Request" />
</shiro:notAuthenticated>

<t:genericpage title="${title}" includePortalStyles="true">
    <jsp:attribute name="header">
        <t:header title="Certificate of Insurance Request" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <script type="text/javascript">
            $(document).ready(function() {
                hideDivs();
                setQuestionDefaults();
            });
        </script>
        <shiro:authenticated>
            <shiro:hasRole name="policyholder">
                <div id="portal-error-top" ><s:errors globalErrorsOnly="true" /><span id="globalError" style="color:#ff0000"></span></div>

                <s:form id="coiForm" beanclass="com.pwc.us.webui.stripes.action.COIFormActionBean">
                    <div class="portal-form">

                        <div class="sectionSubTitle">
                            To request a certification of insurance, please complete the form below.<br/><br/>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33 portal-required" for="certificateOfInsuranceRequest.policyNumber" />
                            </div>
                            <div class="portal-col-66">
                                <s:select name="coiPolicyNumber" value="coiPolicyNumber" onchange="updateTerms(this, '${url}');">
                                    <s:options-collection collection="${policyNumbers}"/>
                                </s:select>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.policyNumber" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.policyNumber" />
                                </div>
                            </div>
                        </div>

                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="certificateOfInsuranceRequest.policyHolderName" />
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.policyHolderName" value="${user.companyName}" readonly="true" />
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="certificateOfInsuranceRequest.client" />
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.client"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.client" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.client" />                    
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33 portal-required" for="certificateOfInsuranceRequest.policyTerm" />
                            </div>
                            <div class="portal-col-66">
                                <div id="termsDiv">
                                    <s:select name="certificateOfInsuranceRequest.termNumber">
                                        <s:option value="">Select a term...</s:option>
                                        <s:options-collection collection="${terms}" value="termNumber" label="termDescription" />
                                    </s:select>
                                </div>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.termNumber" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.termNumber" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33 portal-required" for="certificateOfInsuranceRequest.holderName" />
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.holderName"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.holderName" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.holderName" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33 portal-required" for="certificateOfInsuranceRequest.holderAddress.addressLine1" />
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.holderAddress.addressLine1"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.holderAddress.addressLine1" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.holderAddress.addressLine1" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="certificateOfInsuranceRequest.holderAddress.addressLine2" />
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.holderAddress.addressLine2"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.holderAddress.addressLine2" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.holderAddress.addressLine2" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33 portal-required" for="certificateOfInsuranceRequest.holderAddress.city" />
                            </div>
                            <div class="portal-col-66">
                                <s:text class="portal-width-80" name="certificateOfInsuranceRequest.holderAddress.city"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.holderAddress.city" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.holderAddress.city" />
                                </div>
                            </div>
                        </div>

                        <div class="portal-row">
                            <div class="portal-col-33">&nbsp;
                            </div>
                            <div class="portal-col-66">
                                <s:select name="certificateOfInsuranceRequest.holderAddress.state">
                                    <s:option value="">* State...</s:option>
                                    <s:options-enumeration  enum="com.pwc.us.common.model.UsState" />
                                </s:select>
                                <d:text class="portal-width-25" name="certificateOfInsuranceRequest.holderAddress.postalCode" placeholder="* Zip"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.holderAddress.state" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.holderAddress.state" /> 
                                    <label for="certificateOfInsuranceRequest.holderAddress.postalCode" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.holderAddress.postalCode" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row portal-spacer-5" id="singleIncidentList">
                            <fieldset class="portal-fieldset">
                                <div class="portal-col-33 portal-legend portal-required">Delivery Method 
                                </div>
                                <div class="portal-col-66">
                                    <div class="portal-error">
                                        <s:errors field="deliveryMethodError" />
                                    </div>
                                    <input type="hidden" name="delivery" id="delivery"/>
                                        <ul class="checkbox"> 
                                            <li>
                                                <s:checkbox name="certificateOfInsuranceRequest.deliverByMail"/>
                                                <s:label for="certificateOfInsuranceRequest.deliverByMail" />
                                                <label for="certificateOfInsuranceRequest.deliverByMail" class="error"></label>
                                            </li> 
                                            <li>
                                                <s:checkbox name="certificateOfInsuranceRequest.deliverByEmail" id="emailCheckbox"/>
                                                <s:label for="certificateOfInsuranceRequest.deliverByEmail" />
                                                <label for="certificateOfInsuranceRequest.deliverByEmail" class="error"></label>
                                            </li> 
                                            <li>
                                                <s:checkbox name="certificateOfInsuranceRequest.deliverByFax" id="faxCheckbox"/>
                                                <s:label for="certificateOfInsuranceRequest.deliverByFax" />
                                                <label for="certificateOfInsuranceRequest.deliverByFax" class="error"></label>
                                            </li> 
                                        </ul> 
                                    </div>
                            </fieldset>
                        </div>
                                
                       <div class="portal-row" id="emailDiv">
                            <div class="portal-col-33">
                                <label class="portal-label-33">Email - (Enter up to 3 Recipients and Email Addresses)</label>
                            </div>
                            <div class="portal-col-66">
                                <div>
                                    <d:text name="certificateOfInsuranceRequest.emailRecipientName1" placeholder="Recipient"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.emailRecipientName1" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.emailRecipientName1" />
                                    </div>
                                    <d:text name="certificateOfInsuranceRequest.emailAddress1" placeholder="Enter Email Address"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.emailAddress1" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.emailAddress1" />
                                    </div>
                                </div>
                                <div class="portal-spacer-5">
                                    <d:text name="certificateOfInsuranceRequest.emailRecipientName2" placeholder="Recipient"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.emailRecipientName2" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.emailRecipientName2" />
                                    </div>
                                    <d:text name="certificateOfInsuranceRequest.emailAddress2" placeholder="Enter Email Address"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.emailAddress2" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.emailAddress2" />
                                    </div>
                                </div>
                                <div class="portal-spacer-5">
                                    <d:text name="certificateOfInsuranceRequest.emailRecipientName3" placeholder="Recipient"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.emailRecipientName3" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.emailRecipientName3" />
                                    </div>
                                    <d:text name="certificateOfInsuranceRequest.emailAddress3" placeholder="Enter Email Address"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.emailAddress3" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.emailAddress3" />
                                    </div>
                                </div>
                            </div>
                        </div>
                                
                       <div class="portal-row portal-spacer-10" id="faxDiv">
                            <div class="portal-col-33">
                                <label class="portal-label-33">Fax - (Enter up to 3 Recipients and Fax Numbers)</label>
                            </div>
                            <div class="portal-col-66">
                                <div class="portal-spacer-5">
                                    <d:text name="certificateOfInsuranceRequest.faxRecipientName1" placeholder="Recipient"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.faxRecipientName1" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.faxRecipientName1" />
                                    </div>
                                    <d:text name="certificateOfInsuranceRequest.faxNumber1" placeholder="Fax Number"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.faxNumber1" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.faxNumber1" />
                                    </div>
                                </div>
                                <div class="portal-spacer-5">
                                    <d:text name="certificateOfInsuranceRequest.faxRecipientName2" placeholder="Recipient"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.faxRecipientName2" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.faxRecipientName2" />
                                    </div>
                                    <d:text name="certificateOfInsuranceRequest.faxNumber2" placeholder="Fax Number"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.faxNumber2" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.faxNumber2" />
                                    </div>
                                </div>
                                <div class="portal-spacer-5">
                                    <d:text name="certificateOfInsuranceRequest.faxRecipientName3" placeholder="Recipient"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.faxRecipientName3" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.faxRecipientName3" />
                                    </div>
                                    <d:text name="certificateOfInsuranceRequest.faxNumber3" placeholder="Fax Number"/>
                                    <div class="portal-error">
                                        <label for="certificateOfInsuranceRequest.faxNumber3" class="error"></label>
                                        <s:errors field="certificateOfInsuranceRequest.faxNumber3" />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="portal-row portal-spacer-10">
                            <div class="portal-col-100">
                                <label class="portal-label-100">Does the certificate holder require:</label>
                            </div>
                        </div>
                        <div class="portal-row portal-spacer-5">
                            <fieldset class="portal-fieldset">
                                <label class="portal-label-100 portal-legend portal-required portal-indent">An alternate employer endorsement</label>
                                <label class="portal-radio-button-label">
                                    <s:radio id="isAlternateEmployerYes" name="certificateOfInsuranceRequest.isAlternateEmployer" value="true"/>yes</label>
                                <label class="portal-radio-button-label">
                                    <s:radio  id="isAlternateEmployerNo" name="certificateOfInsuranceRequest.isAlternateEmployer" value="false" checked="checked" />no</label>
                                <div class="portal-error">
                                    <s:errors field="certificateOfInsuranceRequest.isAlternateEmployer" />
                                </div>
                            </fieldset>
                        </div>
                        <div class="portal-row">
                            <fieldset class="portal-fieldset">
                                <label class="portal-label-100 portal-legend portal-indent">A Waiver of Subrogation</label>
                                <label class="portal-radio-button-label">
                                    <s:radio id="isWaiverOfSubrogationYes" name="certificateOfInsuranceRequest.isWaiverOfSubrogation" value="true"/>yes</label>
                                <label class="portal-radio-button-label">
                                    <s:radio id="isWaiverOfSubrogationNo" name="certificateOfInsuranceRequest.isWaiverOfSubrogation" value="false" checked="checked" />no</label>
                                <div class="portal-error">
                                    <s:errors field="certificateOfInsuranceRequest.isWaiverOfSubrogation" />
                                </div>
                            </fieldset>
                        </div>
                        <div class="portal-row">
                            <fieldset class="portal-fieldset">
                                <label class="portal-label-100 portal-legend portal-indent">30 day's Notice of Cancellation</label>
                                <label class="portal-radio-button-label">
                                    <s:radio id="withThirtyDayNoticeYes" name="certificateOfInsuranceRequest.withThirtyDayNotice" value="true"/>yes</label>
                                <label class="portal-radio-button-label">
                                    <s:radio  id="withThirtyDayNoticeNo" name="certificateOfInsuranceRequest.withThirtyDayNotice" value="false" checked="checked" />no</label>
                                <div class="portal-error">
                                    <s:errors field="certificateOfInsuranceRequest.withThirtyDayNotice" />
                                </div>
                            </fieldset>
                        </div>
                        <div class="portal-row portal-spacer-10">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="certificateOfInsuranceRequest.projectName" ></s:label>
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.projectName"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.projectName" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.projectName" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="certificateOfInsuranceRequest.workPerformed" ></s:label>
                            </div>
                            <div class="portal-col-66">
                                <s:textarea name="certificateOfInsuranceRequest.workPerformed" cols="60" rows="6"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.workPerformed" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.workPerformed" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row portal-spacer-5">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="certificateOfInsuranceRequest.specificInstructions" ></s:label>
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.specificInstructions"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.specificInstructions" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.specificInstructions" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33 portal-required" for="certificateOfInsuranceRequest.requesterName"/>
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.requesterName"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.requesterName" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.requesterName" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33 portal-required" for="certificateOfInsuranceRequest.requesterPhone"/>
                            </div>
                            <div class="portal-col-66">
                                <s:text name="certificateOfInsuranceRequest.requesterPhone"/>
                                <div class="portal-error">
                                    <label for="certificateOfInsuranceRequest.requesterPhone" class="error"></label>
                                    <s:errors field="certificateOfInsuranceRequest.requesterPhone" />
                                </div>
                            </div>
                           <div class="portal-col-66">
                                <s:label for="Enter with dashes; example: 405-555-5555" />
                            </div>
                        </div>

                        <div class="portal-row">
                            <div class="portal-col-33">&nbsp;
                            </div>
                            <div class="portal-col-66">
                                <div class="portal-width-100">
                                    <div class="portal-button">
                                        <s:submit name="submit" value="Submit" class="portal-submit" onclick="submitProcessing()" />
                                    </div>
                                    <div class="portal-button">
                                        <s:submit name="cancel" value="Cancel" class="portal-submit cancel" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </s:form>
            </shiro:hasRole>
        </shiro:authenticated>
    </jsp:body>
</t:genericpage>