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

<c:set var="title" value="Other States' Coverage Application Steps" />

<t:genericpage title="${title}" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="${title}" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <div id="sem-message" ><s:errors globalErrorsOnly="true" /></div>
        <div id="sem-message" ><s:messages /></div>


        <p><span class="text10">If you&nbsp;have operations or employees outside the state of Oklahoma, CompSource’s&nbsp;Other States' Coverage program allows&nbsp;you to secure all workers’ compensation insurance coverage needs through us. </span></p><p><span class="ccm-subhead">Step one:<br></span>Review the <s:link beanclass="com.pwc.us.webui.stripes.action.OutOfStateGuidelines">Other States' Coverage Program Guidelines</s:link> for eligibility requirements and program exclusions.</p>
        <p><span class="ccm-subhead">Step two:</span><br>Complete an <a download="Acord-130-2013-09-fill.pdf" href="pdf/Acord-130-2013-09-fill.pdf" target="_blank">Acord 130 Workers’ Compensation Application form</a>&nbsp;and the <a download="UF-17__CSO_Supplemental_App_11-1-111.pdf" href="pdf/UF-17__CSO_Supplemental_App_11-1-111.pdf" target="_blank">CompSource Supplemental Application form</a>.<br><br><span class="ccm-subhead">Step three:</span><br>Must have a minimum of three years in business or provide a written verification of management experience in similar business: i.e. resume.</p>
        <p><span class="ccm-subhead">Step four:</span><br>Provide currently valued loss information (valued no earlier than 90 days prior to requested effective date). The loss information must be from an insurance carrier for the current year and immediate preceding three years and shall be verifiable. <br><br>An underwriter may contact you after receiving your application if additonal&nbsp;information is needed.</p>
        <s:form class="sem-form" id="outOfState" beanclass="${actionBean.class}">
            <div class="sem-form-section sem-form-section-1 sem-form-9-section" id="sem-form-9-section-1">

                <div class="sem-field-container sem-form-section-1">
                    To submit an out of state insurance request, please complete the form below.<br/><br/>
                </div>
                <div class="sem-field-container " id="sem-field-container-48"  >
                    <s:label class="sem-label" for="outOfStateAttachments.application" /><span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:file name="application"/>
                    <s:errors field="application"/>
                </div>

                <div class="sem-field-container " id="sem-field-container-48"  >
                    <s:label class="sem-label" for="outOfStateAttachments.supplemental" /><span class="sem-required-indicator" style="color:#ff0000" >*</span>
                    <s:file name="supplemental"/>
                    <s:errors field="supplemental"/>
                </div>
                <div class="sem-field-container " id="sem-field-container-48"  >Attachments:</div>
                <div class="sem-field-container " id="sem-field-container-48"  ><s:errors field="documents"/></div>
                <div id="attachmentRows">
                    <div class="sem-field-container" id="sem-field-container-48"  >
                        <s:file class="sem-field sem-text " name="documents[0]" />
                    </div>
                </div>
                <div id="addAttachmentLink" class="sem-field-container" >
                    <a href="#" id="addAttachment">Add Attachment</a>
                </div>
                <div class="clear"></div>
                <div class="sem-field-container">
                    <div class="portal-button">
                        <s:submit name="submit" value="Submit" class="sem-submit" />
                    </div>
                    <div class="portal-button">
                        <s:submit name="cancel" value="Cancel" class="sem-submit" />
                    </div>
                </div>
            </div>
        </s:form>
        <p>&nbsp;</p><p>&nbsp;</p>
        <p><span class="text10">Questions regarding the Other States' Coverage program can be directed to CompSource's underwriting department by calling </span><span class="text10">(405) 232-7663, ext. 5102 or </span><span class="text10"><br>(800) 347-3863, ext. 5102. </span></p>  
    </div>






</jsp:body>
</t:genericpage>