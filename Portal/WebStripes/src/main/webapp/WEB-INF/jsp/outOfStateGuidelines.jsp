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

<c:set var="title" value="Other States' Coverage Program Guidelines" />

<t:genericpage title="${title}" includePortalStyles="false">
    <jsp:attribute name="header">
        <t:header title="${title}" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <p><span class="text12"><strong>&nbsp;</strong></span><span class="text10"><strong>General Account Eligibility Requirements</strong><br>For employers based in Oklahoma who meet each of the following criteria: </span></p>
        <div id="Intern">
            <ul>
                <li>The employer’s principal operation is within the state of Oklahoma. Typically this means an employer’s primary location is in Oklahoma. </li>
                <li>The employer’s operations outside of Oklahoma are permanent. </li>
                <li>The employer must have a minimum of three years in business or provide a written verification of management experience in similar business: i.e. resume. </li>
                <li>The employer’s uninsured subcontracted exposure must be below 20 percent by state (confirmed with audit or loss prevention). </li>
                <li>The employer’s Oklahoma operation is insured through CompSource. </li>
                <li>The employer is in good standing with CompSource.<strong>&nbsp;</strong></li>
            </ul>
        </div>
        <div>&nbsp;&nbsp;&nbsp;</div>
        <div>&nbsp;&nbsp;&nbsp;</div>
        <div><strong>Eligible States</strong></div>
        <div>All states are eligible for our other states' coverage program except the “monopolistic states” of North Dakota, Ohio, Washington and Wyoming.</div>
        <p>&nbsp;</p>
        <p><strong>Program Exclusions</strong><br>Operations that do not qualify for coverage under this program:</p>
        <div id="Intern">
            <ul>
                <li>Temporary Employment Agencies </li>
                <li>Administrative Payroll Service Organization </li>
                <li>Professional Employer Organizations (PEO) </li>
                <li>Any business with operations falling under the jurisdiction of the following Federal Acts: Defense Base Act, Outer Continental Shelf Act, Jones Act, Admiralty and Maritime Act, Federal Employers’ Liability Act and Federal Coal Mine Health &amp; Safety Act </li>
                <li>United States Longshore and Harbor Act (incidental exposure may qualify) </li>
                <li>Manufacturing, packaging, handling, shipping or storage of explosives, substances intended for use as an explosive, ammunition, fuses, arms, magnesium, propellant charges, detonating devices, fireworks, nitroglycerin, celluloid, or pyroxylin </li>
                <li>Aviation operations including pilots or flying personnel </li>
                <li>Nuclear Regulatory Commission projects or operations conducted under license from the Nuclear Regulatory Commission </li>
                <li>Sport teams </li>
                <li>Asbestos removal </li>
                <li>Underground mining or strip mining </li>
                <li>Wrecking or demolition of buildings, structures or vessels </li>
                <li>Chemical manufacturing </li>
                <li>Sewer, subway or water main construction, shaft sinking or tunneling </li>
                <li>Caisson or coffer dam work; dam, dike, lock or revetment construction </li>
                <li>Subaqueous work </li>
                <li>Primarily railroad operation or construction </li>
            </ul>
        </div></jsp:body>
</t:genericpage>