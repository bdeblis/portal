<%-- 
    Document   : leftnav
    Created on : Nov 16, 2013, 3:22:49 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@tag description="The left navigation of the CompSource Portal" pageEncoding="UTF-8"%>

<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>

<%-- any content can be specified here e.g.: --%>
<div id="col1">
    <!-- Column one start -->
    <div class="content_left_top_bg_sub"><img src="img/left_bg_top.png" alt="" width="273" height="23" /></div>
    <div class="content_left_box_bg_sub">
        <div class="content_left_text_box">

            <ul id="am_menu" class="nav">

                        <li>
                        </li>


                        <li><a href="">Claims</a>
                            <ul>

                                <li><s:link beanclass="com.pwc.us.webui.stripes.action.FNOIPolicyCheck">File a Claim</s:link></li>
                                </ul>
                        </li>


                        <li><s:link beanclass="com.pwc.us.webui.stripes.action.QuoteRequestActionBean">Request A Quote</s:link></li>
                        <li><a href="">Other States' Coverage</a>
                            <ul>

                                <li><s:link beanclass="com.pwc.us.webui.stripes.action.OutOfStateActionBean">Other States' Coverage Application Process</s:link></li>
                                <li><s:link beanclass="com.pwc.us.webui.stripes.action.OutOfStateGuidelines">Other States' Coverage Program Guidelines</s:link></li>
                            </ul>
                        </li>



                <shiro:authenticated>
                    <shiro:hasRole name="policyholder">
                        <li><s:link beanclass="com.pwc.us.webui.stripes.action.COIFormActionBean">Certificate of Insurance Request</s:link></li>
                        <li><s:link beanclass="com.pwc.us.webui.stripes.action.PayrollReportActionBean">Payroll Reporting</s:link></li>
                        <li><s:link beanclass="com.pwc.us.webui.stripes.action.PayOnlineActionBean">Pay Online</s:link></li>
                    </shiro:hasRole>
                    <shiro:hasRole name="cashier">
                        <li><s:link beanclass="com.pwc.us.webui.stripes.action.PayOnlineActionBean">Pay Online</s:link></li>
                    </shiro:hasRole>
                </shiro:authenticated>
            </ul>                   	     
        </div>

    </div> 

    <!-- Column one end --> 
</div> <!-- col1 end -->
