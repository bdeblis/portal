<%-- 
    Document   : header
    Created on : Oct 16, 2013, 1:21:12 PM
    Author     : Roger Turnau - <roger.turnau@us.pwc.com>
--%>

<%@tag description="Header Page Fragment for CompSource Portal" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title" %>
<%@attribute name="csoMainUrl" required="true" %>


<div class="header">
    <div class="header_left">
        <div class="logo"><a href="/"><img src="img/CMlogo-Web.png" alt="" width="230" height="67" /></a></div>
    </div>

    <div class="header_right">
        <div class="header_right_box">

            <div class="search_box">
                <div class="search_images">
                    <!--<input type="text" name="textfield" id="textfield" class="search_text_field" />-->
                </div>
            </div>
        </div>

        <div class="menu_box">
            <script src="js/jquery.js" type="text/javascript"></script>
            <script type="text/javascript" src="js/am_menu.js"></script>
            <link type="text/css" rel="stylesheet" href="css/am_style.css" />
            <ul class="minu_ul">
                <shiro:authenticated>
                    <li class="minu_li"> ${user.login} <a href="Logout.action" >Logout</a></li>
                </shiro:authenticated>
                <shiro:notAuthenticated>
                    <li class="minu_li"><a href="Login.action">Login</a></li>    
                </shiro:notAuthenticated>
                <li class="minu_li"><a href="${csoMainUrl}/cs/contact/"  >Contact Us</a></li><li class="minu_li"><a href="${csoMainUrl}/cs/about/"  >About CompSource Mutual</a></li><li class="minu_li"><a href="${csoMainUrl}/cs/home/"  >Home</a></li></ul> 
        </div>
    </div>

</div>

