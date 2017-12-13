<%-- 
    Document   : genericpage
    Created on : Oct 16, 2013, 12:12:41 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@attribute name="header" fragment="true" %>
<%@attribute name="footer" fragment="true" %>
<%@attribute name="leftnav" fragment="true" %>
<%@attribute name="title" %>
<%@attribute name="includePortalStyles" type="java.lang.Boolean" required="true" %>

<%@taglib prefix="s" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@taglib prefix="d" uri="http://stripes.sourceforge.net/stripes-dynattr.tld" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%-- Short hand for the context root. --%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
        <meta http-equiv="cache-control" content="max-age=0" />
        <meta http-equiv="cache-control" content="no-cache" />
        <meta http-equiv="expires" content="0" />
        <meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
        <meta http-equiv="pragma" content="no-cache" />
        <script src="js/modernizr.custom.04887.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="css/policyholders.css" />
        <c:if test="${includePortalStyles}">
            <link rel="stylesheet" type="text/css" href="css/portal.css" />
        </c:if>
        <link rel="stylesheet" type="text/css" href="css/main.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery.ui.css" />
        <link rel="stylesheet" type="text/css" href="css/jquery.timepicker.css" />        
        <link rel="stylesheet" type="text/css" href="css/reset.css" />
        <link rel="stylesheet" type="text/css" href="css/sexy-combo.css" />
        <link rel="stylesheet" type="text/css" href="css/sexy.css" />
        <link rel="stylesheet" type="text/css" href="css/typography.css" />
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
        <title>${title}</title>

        <style>
            body {
                background:url(img/body_images.png);	
                background-position: top;
                background-repeat: no-repeat;
                background-color: #DAE1EB;
            }
        </style>
    </head>
    <body>
        <div class="man">

            <div id="pageheader">
                <jsp:invoke fragment="header"/>
            </div>
            <div id="body">
                <div id="container2">
                    <div id="container1">
                        <t:leftnav />
                        <div id="col2">
                            <div class="sub">
                                <c:choose>
                                    <c:when test="${includePortalStyles}">
                                        <div class="portal-headline">
                                            <h1>${title}</h1>
                                        </div>
                                        <div class="portal">
                                            <jsp:doBody/>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="headline">
                                            <h1>${title}</h1>
                                        </div>
                                        <div class="inner">
                                            <jsp:doBody/>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <jsp:invoke fragment="footer"/>
        </div>
        <script src="js/jquery.js" type="text/javascript"></script>
        <script src="js/jquery.ui.js" type="text/javascript"></script>
        <script src="js/jquery.timepicker.js" type="text/javascript"></script>
        <script src="js/jquery.blockUI.js" type="text/javascript"></script>
        <script src="js/jquery.number.js" type="text/javascript"></script>
        <script src="js/menu.js" type="text/javascript"></script>
        <script src="js/slider.js" type="text/javascript"></script> 
        <script src="js/faqs.js" type="text/javascript"></script> 
        <script src="js/jquery.infieldlabel.js" type="text/javascript"></script> 
        <script src="js/jquery.infieldlabelTwo.js" type="text/javascript"></script> 
        <script src="js/jquery.sexy-combo.js" type="text/javascript"></script>
        <script src="js/jquery.maskMoney.min.js" type="text/javascript"></script>
        <script src="js/jshashtable-3.0.js" type="text/javascript"></script>
        <script src="js/jquery.numberformatter-1.2.4.js" type="text/javascript"></script>
        <script src="js/validate/jquery.validate.js" type="text/javascript"></script>
        <script src="js/validate/additional-methods.js" type="text/javascript"></script>
        <script src="js/jquery.simplemodal.js" type="text/javascript"></script>
        <script src="js/jquery.column.js" type="text/javascript"></script>
        <script src="js/jquery.text-align.js" type="text/javascript"></script>
        <script src="js/portal.js" type="text/javascript"></script>

        <script>
            $(document).ready(placeholderCheck());
        </script>

    </body>

</html>