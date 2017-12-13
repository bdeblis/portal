<%-- 
    Document   : pay_online
    Created on : Jan 21, 2014, 9:04:45 AM
    Author     : rturnau001
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@page contentType="text/html" pageEncoding="UTF-8" language="java" %>
<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<t:genericpage title="Pay Online" includePortalStyles="true">
    <jsp:attribute name="header">
        <t:header title="Pay Online" csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer csoMainUrl="http://www.compsourceok.com" />
    </jsp:attribute>
    <jsp:body>
        <shiro:authenticated>
            <shiro:hasRole name="cashier">
                <script type="text/javascript">
                   window.onload=function(){
                   var elem = document.getElementById("cashAccount");
                    elem.value = "";
                   };
                </script>
            </shiro:hasRole>
            <shiro:hasAnyRoles name="policyholder, cashier">
                <script type="text/javascript">
                      function OnMoneyInput () {
                             radiobtn = document.getElementById("selectedPayAmtOther");
                             radiobtn.checked = true;
                        }
                    
                    $(document).ready(function() {
                        var pastDue = parseFloat($('#pastDueBalanceBilled').val()).toMoney(),
                                currentDue = parseFloat($('#currentBalanceBilled').val()).toMoney(),
                                totalAmtDue = parseFloat($('#totalAmountBilled').val()).toMoney(),
                                unpaidAmt = parseFloat($('#unpaidAmount').val()).toMoney();
                        $('#unpaidAmount').val("$"+unpaidAmt);
                        $('#pastDueBalanceBilled').val("$" + pastDue);
                        $('#currentBalanceBilled').val("$" + currentDue);
                        $('#totalAmountBilled').val("$" + totalAmtDue);
                        $('#otherAmount').maskMoney({thousands: ',', decimal: '.', allowZero: true, prefix: '$'});
                        $('input[name=selectedPayAmt]').on('click', function(event) {
                            var amt = 0, amtToPay = 0;
                            if ($(event.currentTarget).val() === 'pastDue') {
                                amt = $('#pastDueBalanceBilled').val();
                            } else if ($(event.currentTarget).val() === 'currentDue') {
                                amt = $('#currentBalanceBilled').val();
                            } else if ($(event.currentTarget).val() === 'totalDue') {
                                amt = $('#totalAmountBilled').val();
                            } else if ($(event.currentTarget).val() === 'unpaidAmt') {
                                amt = $('#unpaidAmount').val();
                            } else { // we let other amount be handled in the normal way.
                                amt = '-1';
                            }
                            amtToPay = parseFloat(amt.replace(/[^0-9-.]/g, ''));
                            $('#amountToPay').val(amtToPay);
                            console.log('amtToPay: ' + amtToPay);
                        });

                        $("#submit").on('click', function(event) {
                            if (window.confirm('You will now be redirected to our payment site to enter your credit card details. You will be returned here when the payment is complete.')) {
                                submitProcessing();
                            }
                        });
                        
                        $("#cancel").on('click', function(event) {
                            $("#payOnlineForm").validate().cancelSubmit = true;
                        });

                    });
                </script>
                <div class="portal-error-top"><s:errors globalErrorsOnly="true" /></div>

                <s:form id="payOnlineForm" beanclass="com.pwc.us.webui.stripes.action.PayOnlineActionBean">
                    <div class="portal-form">
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="PayOnline.PolicyHolderName" />
                           <!--     <s:errors field="policyHolderName" /> -->
                            </div>
                            <div class="portal-col-25">
                                <shiro:hasRole name = "policyholder">
                                     <s:text value="${user.firstName} ${user.lastName}" name="policyHolderName" disabled="true" />
                                </shiro:hasRole>
                                <shiro:hasRole name = "cashier">
                                     <s:text value="" name="policyHolderName" />
                                </shiro:hasRole>
                                <div class="portal-error">
                                    <s:errors field="policyHolderName" />                    
                                </div>
                            </div>
                            <div class="portal-col-20">
                                <s:label class="portal-label-33" for="PayOnline.Date" />
                            </div>
                            <div class="portal-col-20">
                                <s:text class="datepick" name="dateToday" formatPattern="MM-dd-yyyy" disabled="true"/>
                                <div class="portal-error">
                                    <s:errors field="dateToday" />                    
                                </div>
                            </div>
                        </div>
                        <div class="portal-row portal-spacer-10">
                            <div class="portal-col-33">
                                <s:label class="portal-label-33" for="PayOnline.AccountNumber" />
                       <!--         <s:errors field="accountNumber" /> -->
                            </div>
                            <div class="portal-col-25">
                                <shiro:hasRole name = "policyholder">
                                     <s:text value="${user.policyCenterAccountId}" name="user.policyCenterAccountId" disabled="true" />
                                </shiro:hasRole>
                                <shiro:hasRole name = "cashier">
                                     <s:text id="cashAccount"  name="accountNumber" />
                                </shiro:hasRole>
                                <div class="portal-error">
                                    <s:errors field="user.policyCenterAccountId" />
                                </div>
                            </div>
                        </div>                    
                       <shiro:hasRole name = "policyholder">
                        <!-- Amounts Due -->
                        <div class="sectionTitle">
                            <hr>
                        </div>
                        <div class="portal-col-100">
                            <div class="portal-error">
                                <label for="selectedPayAmt" class="error" style="color:#ff0000"></label>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <label class="portal-radio-button-label">
                                    <s:radio class="portal-radio vertical-radio" id="selectedPayAmtPastDue" name="selectedPayAmt" value="pastDue"/>
                                    <s:label class="portal-label-33" for="PayOnline.PastDueAmount" />
                                </label>
                            </div>
                            <div class="portal-col-20">
                                <s:text class="money-text" id="pastDueBalanceBilled" value="${billingInfo.pastDueBalanceBilled}" name="billingInfo.pastDueBalanceBilled" disabled="true" />
                                <div class="portal-error">

                                </div>
                            </div>
                            <div class="portal-col-20">
                                <s:label class="portal-label-33" for="PayOnline.DueDate" />
                            </div>
                            <div class="portal-col-20">
                                <s:text class="datepick" name="billingInfo.dueDate" value="${billingInfo.dueDate}" formatPattern="MM-dd-yyyy" disabled="true"/>
                                <div class="portal-error">
                                    <s:errors field="billingInfo.dueDate" />                    
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <label class="portal-radio-button-label">
                                    <s:radio class="portal-radio vertical-radio" id="selectedPayAmtCurrent" name="selectedPayAmt" value="currentDue"/>
                                    <s:label class="portal-label-33" for="PayOnline.CurrentDueAmount" /></label>
                            </div>
                            <div class="portal-col-20">
                                <s:text class="money-text" id="currentBalanceBilled" value="${billingInfo.currentBalanceBilled}" name="billingInfo.currentBalanceBilled" disabled="true" />
                                <div class="portal-error">
                                    <s:errors field="billingInfo.currentBalanceBilled"/>
                                </div>
                            </div>
                            <div class="portal-col-20">
                                <s:label class="portal-label-33" for="PayOnline.DueDate"/>
                            </div>
                            <div class="portal-col-20">
                                <s:text class="datepick" name="billingInfo.currentAccountBalanceTimestamp" value="${billingInfo.currentAccountBalanceTimestamp}" formatPattern="MM-dd-yyyy" disabled="true"/>
                                <div class="portal-error">
                                    <s:errors field="billingInfo.currentAccountBalanceTimestamp" />                    
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <label class="portal-radio-button-label">
                                    <s:radio class="portal-radio vertical-radio" id="selectedPayAmtTotal" name="selectedPayAmt" value="totalDue"/>
                                    <s:label class="portal-label-33" for="PayOnline.TotalAmountDue" /></label>
                            </div>
                            <div class="portal-col-20">
                                <s:text class="money-text" id="totalAmountBilled" value="${billingInfo.totalAmountBilled}" name="billingInfo.totalAmountBilled" disabled="true" />
                                <div class="portal-error">

                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <label class="portal-radio-button-label">
                                    <s:radio class="portal-radio vertical-radio" id="unpaidAmt" name="selectedPayAmt" value="unpaidAmt"/>
                                    <font color="red">*</font><s:label class="portal-label-33" for="PayOnline.CurrentAccountBalance" /></label>
                            </div>
                            <div class="portal-col-20">
                                <s:text class="money-text" id="unpaidAmount" value="${billingInfo.unpaidAmount}" name="billingInfo.unpaidAmount" disabled="true" />
                                <div class="portal-error">

                                </div>
                            </div>
                        </div>                                
                       </shiro:hasRole>        
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <label class="portal-radio-button-label">
                                    <shiro:hasRole name = "policyholder">
                                       <s:radio class="portal-radio vertical-radio" id="selectedPayAmtOther" name="selectedPayAmt" value="otherAmt"/>
                                       <s:label class="portal-label-33" for="PayOnline.OtherAmount" /></label>
                                    </shiro:hasRole>
                                    <shiro:hasRole name = "cashier">
                                       <s:radio class="portal-radio vertical-radio" id="selectedPayAmtOther" name="selectedPayAmt" value="otherAmt" checked="checked"  style="display:none;"/>
                                       <s:label class="portal-label-33" for="PayOnline.Amount" /></label>
                                    </shiro:hasRole>
                                    
                            </div>
                            <div class="portal-col-20">
                                <s:text class="money-text" id="otherAmount" name="otherAmount" onmouseout="OnMoneyInput()"/>
                            </div>
                            <div class="portal-col-40">
                                <div class="portal-error">
                                    <label for="otherAmount" class="error" style="color:#ff0000"></label>
                                    <s:errors field="otherAmount" />
                                </div>
                            </div>
                        </div>
                        <div class="portal-row">
                            <div class="portal-col-33">
                                <div class="portal-error">
                                    &nbsp;
                                </div>
                            </div>
                            <div class="portal-col-66">
                                <div class="portal-processing">
                                    <img src="img/throbber_white_32.gif" /> Processing
                                </div>
                                <div class="portal-width-100">
                                    <div class="portal-button">
                                        <s:submit name="submit" value="Submit" class="portal-submit" id="submit" />
                                    </div>
                                    <div class="portal-button">
                                        <s:submit name="cancel" value="Cancel" class="portal-submit cancel" id="cancel"/>
                                    </div>
                                </div>
                            </div>
                        </div>                
                    </div>
                    <div style="display: none">
                        <s:hidden id="amountToPay" name="amountToPay" />
                    </div>
                </s:form>

                <div class="sectionTitle">
                    <hr>
                </div>
                <div class="portal-form">
                    <h3>Important Information</h3>
                    <p>
                    <ul>
                        <li><font color="red">*The current account balance may include amounts not yet billed.</font></li>
                        <li>If the Past Due Amount is not paid by the due date indicated above, the Policy will be canceled.</li>
                        <li>If the payment is made after 11:00 p.m. CST, the amount will be posted the following business day.</li>
                        <li>If you have any questions, please call us at (405)232-7663 ext. 5105.</li>
                        <li>If you experience difficulty when making an online payment, please call our business office during regular business hours, Monday through Friday 8 a.m. to 5 p.m., at (405) 232-7663 or (800) 347-3863.</li>
                    </ul>
                </p>
            </div>
            <div class="portal-form" id="question" style="display:none; cursor: default"> 
                <p>You will now be redirected to our payment site to enter your credit card details. You will 
                    be returned to the CompSource Portal after your payment is complete.</p> 
                <input type="button" id="ok" value="OK"  /> 
                <input type="button" id="cancel" class="cancel" value="Cancel"  /> 
            </div> 
                		<!-- modal content -->
		<div id='confirm'>
			<div class='header'><span>Confirm</span></div>
			<div class='message'></div>
			<div class='buttons'>
				<div class='yes'>Yes</div><div class='no simplemodal-close'>No</div>
			</div>
		</div>
        </shiro:hasAnyRoles>
    </shiro:authenticated>
</jsp:body>
</t:genericpage>