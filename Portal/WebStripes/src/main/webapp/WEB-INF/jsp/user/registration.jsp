<%-- 
    Document   : registration
    Created on : Oct 28, 2013, 3:23:41 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:genericpage title="Registration" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="Registration" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <script type="text/javascript">
            function hideUserTypeFields() {
                if ($("#typeOfUser").val() === "Agent") {
                    $('#policyholder').hide();
                    $('#agent').fadeIn();
                    $('#temporaryPassword').fadeIn();
                } else if ($("#typeOfUser").val() === "Policyholder") {
                    $('#temporaryPassword').hide();
                    $('#agent').hide();
                    $('#policyholder').fadeIn();
                } else {
                    $('#temporaryPassword').hide();
                    $('#policyholder').hide();
                    $('#agent').hide();
                }
            }

            $(document).ready(function() {
                hideUserTypeFields();
                $("#typeOfUser").change(function() {
                    hideUserTypeFields();
                });

                $('#regButton').onclick = function(event) {
                    event.preventDefault();
                    this.disabled = true;
                    this.form.submit();
                };
            });


        </script>
        
        <style>
            .error {
                color: #ff0000
            }
        </style>

        <div class="portal-error error" ><s:errors globalErrorsOnly="true" /></div>
        <s:form class="sem-form" id="registrationForm" beanclass="com.pwc.us.webui.stripes.action.Registration" method="post" >
            <div class="sem-form-section sem-form-section-1 sem-form-9-section">
                <div class="sem-field-container sem-form-section-1">
                    <s:label class="sem-label infield " for="registration.typeOfUser" />
                    <div class="combo sexy">
                        <s:select id="typeOfUser" class="sem-field sem-field-1 sem-select" name="registration.typeOfUser">
                            <s:option class="sem-option"/>
                            <s:options-enumeration class="sem-option"
                                                   enum="com.pwc.us.common.model.RegistrationInfo.RegistrationType" />
                        </s:select>   
                        <div class="portal-error error">
                            <label for="registration.typeOfUser" class="error"></label>
                            <s:errors field="registration.typeOfUser" />
                        </div>
                    </div>

                </div>


                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.userName" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:text name="registration.userName" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.userName" class="error"></label>
                        <s:errors field="registration.userName" />
                    </div>
                </div><!--/.sem-field-container -->
                <div id="temporaryPassword">
                    <div class="sem-field-container">
                        You should have been given a token to enter when you register. Please enter that token here. 
                    </div>
                    <div class="sem-field-container" >
                        <s:label class="sem-label" for="registration.agentRegistrationToken" />
                        <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                        <s:text name="registration.agentRegistrationToken" class="sem-field sem-text "  />
                        <div class="portal-error error">
                            <label for="registration.agentRegistrationToken" class="error"></label>
                            <s:errors field="registration.agentRegistrationToken" />
                        </div>
                    </div><!--/.sem-field-container -->
                    <div class="sem-field-container">
                        Please enter your your new password below.
                    </div>
                </div>
                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.password" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:password name="registration.password" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.password" class="error"></label>
                        <s:errors field="registration.password" />
                    </div>
                </div><!--/.sem-field-container -->
                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.passwordAgain" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:password name="registration.passwordAgain" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.passwordAgain" class="error"></label>
                        <s:errors field="registration.passwordAgain" />
                    </div>
                </div><!--/.sem-field-container -->
                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.email" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:text name="registration.email" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.email" class="error"></label>
                        <s:errors field="registration.email" />
                    </div>
                </div><!--/.sem-field-container -->
                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.firstName" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:text name="registration.firstName" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.firstName" class="error"></label>
                        <s:errors field="registration.firstName" />
                    </div>
                </div><!--/.sem-field-container -->
                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.lastName" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:text name="registration.lastName" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.lastName" class="error"></label>
                        <s:errors field="registration.lastName" />
                    </div>
                </div><!--/.sem-field-container -->
                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.phoneNumber" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:text name="registration.phoneNumber" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.phoneNumber" class="error"></label>
                        <s:errors field="registration.phoneNumber" />
                    </div>
                </div><!--/.sem-field-container -->
                <div class="userType" id="policyholder" >
                    <div class="sem-field-container">
                        <s:label class="sem-label" for="registration.phPolicyNumber" />
                        <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                        <s:text name="registration.phPolicyNumber" class="sem-field sem-text "  />
                        <div class="portal-error error">
                            <label for="registration.phPolicyNumber" class="error"></label>
                            <s:errors field="registration.phPolicyNumber" />
                        </div>
                    </div><!--/.sem-field-container -->
                    <div class="sem-field-container">
                        <s:label class="sem-label" for="registration.phCompanyName" />
                        <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                        <s:text name="registration.phCompanyName" class="sem-field sem-text "  />
                        <div class="portal-error error">
                            <label for="registration.phCompanyName" class="error"></label>
                            <s:errors field="registration.phCompanyName" />
                        </div>
                    </div><!--/.sem-field-container -->
                </div> <!-- policyholder -->
                <div class="userType" id="agent" >
                    <div class="sem-field-container">
                        <s:label class="sem-label" for="registration.agentAgencyNumber" />
                        <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                        <s:text name="registration.agentAgencyNumber" class="sem-field sem-text "  />
                        <div class="portal-error error">
                            <label for="registration.agentAgencyNumber" class="error"></label>
                            <s:errors field="registration.agentAgencyNumber" />
                        </div>
                    </div><!--/.sem-field-container -->
                    <div class="sem-field-container">
                        <s:label class="sem-label" for="registration.agentAgencyName" />
                        <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                        <s:text name="registration.agentAgencyName" class="sem-field sem-text "  />
                        <div class="portal-error error">
                            <label for="registration.agentAgencyName" class="error"></label>
                            <s:errors field="registration.agentAgencyName" />
                        </div>
                    </div><!--/.sem-field-container -->

                </div>
                <div class="sem-field-container">
                    Enter a Challenge Question, such as 'What is your mother's maiden name?' 
                    or 'What is the name of the street where you grew up?' Be sure 
                    to keep a record of your answer. If you forget your password, 
                    you will need to enter the correct answer in order to recover it.   
                </div>
                <div class="sem-field-container">
                    <s:label class="sem-label" for="registration.challengeQuestion" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:text name="registration.challengeQuestion" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.challengeQuestion" class="error"></label>
                        <s:errors field="registration.challengeQuestion" />
                    </div>
                </div><!--/.sem-field-container -->
                <div class="sem-field-container " id="sem-field-container-48"  >
                    <s:label class="sem-label" for="registration.challengeAnswer" />
                    <span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:text name="registration.challengeAnswer" class="sem-field sem-text "  />
                    <div class="portal-error error">
                        <label for="registration.challengeAnswer" class="error"></label>
                        <s:errors field="registration.challengeAnswer" />
                    </div>
                </div><!--/.sem-field-container -->

                <div class="sem-field-container" id="sem-submit-426-container">
                    <s:submit id='regButton' name="register" class="sem-submit" value="Register" />
                    <div class="sem-error" style="display:none"></div>
                </div>
            </div><!-- /#sem-form-section -->
            <div class="sem-field-container" style="display:none">
                <img src="img/throbber_white_16.gif" /> Processing
            </div>
        </s:form>


    </jsp:body>
</t:genericpage>
