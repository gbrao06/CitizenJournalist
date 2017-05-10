<!DOCTYPE html>

<!--  The plugin is instanciated when you call fileupload from a jQuery file upload widget. By building our own UI, as we have done here, we can use the basic plugin version with only a few options. At a minimum, you need to and pass it a options object that contains the server component url, some files, and perhaps a function to run once the upload has completed. To convert any HTML element into a jQuery widget, call it with any jQuery DOM function that returns a jQuery widget. The elementid function is usually the simplest. Here's a very simple call to fileupload which adds the uploaded file's name to the document once it's done:->
<!--$('#elementid')-->

<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>

<%@page contentType="text/html;charset=UTF-8"%>

<%@page pageEncoding="UTF-8"%>

<%@ page session="true"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ page isELIgnored ="false" %>

<%
java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy");
request.setAttribute("year", sdf.format(new java.util.Date()));
%>

<html lang="en">
<head>
		<title><%=request.getServletContext().getServerInfo() %></title>
	    <link rel="stylesheet" type="text/css" href="shareki.css"/>
    	
    	<link href="${pageContext.servletContext.contextPath}/images/Icon_Shareki.ico" rel="icon" type="image/x-icon"/>
        <link href="${pageContext.servletContext.contextPath}/images/Icon_Shareki.ico" rel="shortcut icon" type="image/x-icon"/>

        <style type="text/css">
/* <![CDATA[ */
    body {
    margin: 10px 20px;
    text-align: center;
    font-family: Arial, sans-serif;
}

  .thumb {
    height: 75px;
    border: 1px solid #000;
    margin: 10px 5px 0 0;
  }

h1, h2, h3, h4, h5, h6, p, ul, ol {
    margin: 0 0 0.5em;
}
h1 {
    font-size: 18pt;
    margin: 0.5em 0 0;
}
h2 {
    font-size: 16pt;
}
h3 {
    font-size: 13pt;
}
h4 {
    font-size: 12pt;
}
h5 {
    font-size: 11pt;
}
p {
    font-size: 11pt
}

ul {
    margin: 0;
    padding: 0 0 0 0.25em;
    text-indent: 0;
    list-style: none;
}
li {
    margin: 0;
    padding: 0 0 0.25em;
    text-indent: 0;
    font-size: 80%;
}

pre {
    text-indent: 0.25em;
    width: 90%;
    font-size: 90%;
}

br.separator {
    margin: 0;
    padding: 0;
    clear: both;
}

a img {
    border: 0 none;
}

.container {
    padding: 10px;
    margin: 0 0 10px;
}


.col20 {
    float: left;
    width: 20%;
}

.col25 {
    float: left;
    width: 25%;
}

.col25Right {
    float: right;
    width: 25%;
}

.col50 {
    float: left;
    width: 50%;
}

.col100 {
    float: left;
    width: 100%;
}

#wrapper {
    display: block;
    margin: 0 auto;
    text-align: left;
    min-width: 720px;
    max-width: 1000px;
}
.curved {
    border-radius: 10px;
    -moz-border-radius: 10px;
    -webkit-border-radius: 10px;
    -khtml-border-radius: 10px;
}

#navigation {
    background: #eee (bg-nav.png) repeat-x top left;
    margin: 0 0 10px;
    padding: 0;
}
#navigation span {
    float: left;
}
#navigation span a {
    display: block;
    padding: 10px;
    font-weight: bold;
    text-shadow: 1px 1px 1px #fff;
}
#navigation span a:link,
#navigation span a:visited,
#navigation span a:hover,
#navigation span a:active {
    color: #666;
    text-decoration: none;
}
#navigation span#nav-help {
    float: right;
    margin-right: 0;
}

#asf-box {
    height: 40px;
    background: #fff (asf-logo.png) no-repeat top right;}
#asf-box h1 {
    padding: 0;
    margin: 0;
}

#upper {
    background: #fff (bg-upper.png) repeat-x top left;
}

#congrats {
    text-align: center;
    padding: 10px;
    margin: 0 40px 20px;
    background-color: #9c9;
}
#congrats h2 {
    font-size: 14pt;
    padding: 0;
    margin: 0;
    color: #fff;
}

#notice {
    float: left;
    width: 560px;
    color: #696;
}
#notice a:link,
#notice a:visited,
#notice a:hover,
#notice a:active {
    color: #090;
    text-decoration: none;
}
#notice img,
#notice #tasks {
    float: left;
}
#tasks a:link,
#tasks a:visited,
#tasks a:hover,
#tasks a:active {
    text-decoration: underline;
}
#notice img {
    margin-right: 20px;
}

#actions {
    float: right;
    width: 140px;
}

#actions .button {
    display: block;
    padding: 0;
    height: 36px;
    background: (bg-button.png) no-repeat top left;
}

#actions .button a {
    display: block;
    padding: 0;
}

#actions .button a:link,
#actions .button a:visited,
#actions .button a:hover,
#actions .button a:active {
    color: #696;
    text-decoration: none;
}

#actions .button a span {
    display: block;
    padding: 6px 10px;
    color: #666;
    text-shadow: 1px 1px 1px #fff;
    font-size: 10pt;
    font-weight: bold;
}

#middle {
    background: #eef (bg-middle.png) repeat-x top left;
    margin: 20px 0;
    padding: 1px 10px;
}
#middle h3 {
    margin: 0 0 10px;
    color: #033;
}
#middle p {
    font-size: 10pt;
}
#middle a:link,
#middle a:visited,
#middle a:hover,
#middle a:active {
    color: #366;
    font-weight: bold;
}
#middle .col25 .container {
    padding: 0 0 1px;
}

#developers {
    float: left;
    width: 40%;
}
#security {
    float: right;
    width: 50%;
}

#lower {
    padding: 0;
}

#lower a:link,
#lower a:visited,
#lower a:hover,
#lower a:active {
    color: #600;
}

#lower strong a:link,
#lower strong a:visited,
#lower strong a:hover,
#lower strong a:active {
    color: #c00;
}

#lower h3 {
    color: #963;
    font-size: 14pt;
}
#lower h4 {
    font-size: 12pt;
}
#lower ul {
    padding: 0;
    margin: 0.5em 0;
}
#lower p,
#lower li {
    font-size: 9pt;
    color: #753;
    margin: 0 0 0.1em;
}
#lower li {
    padding: 3px 5px;
}
#lower li strong {
    color: #a53;
}
#lower li#list-announce {
    border: 1px solid #f90;
    background-color: #ffe8c8;
}
#lower p {
    font-size: 10.5pt;
}

#low-manage,
#low-docs,
#low-help {
    float: left;
    width: 32%;
}
#low-docs {
    margin: 0 0 0 2.2%;
}
#low-help {
    float: right;
}

#low-manage div,
#low-docs div,
#low-help div {
    min-height: 280px;
    border: 3px solid #ffdc75;
    background-color: #fff1c8;
    padding: 10px;
}

#footer {
    padding: 0;
    margin: 20px 0;
    color: #999;
    background-color: #eee;
}
#footer h4 {
    margin: 0 0 10px;
    font-size: 10pt;
}
#footer p {
    margin: 0 0 10px;
    font-size: 10pt;
}
#footer ul {
    margin: 6px 0 1px;
    padding: 0;
}
#footer li {
    margin: 0;
    font-size: 9pt;
}

#footer a:link,
#footer a:visited,
#footer a:hover,
#footer a:active {
    color: #666;
}

.copyright {
    font-size: 10pt;
    color: #666;
}
/* ]]> */
</style>

</head>
<body>
	
	 <div id="wrapper">
            <div id="navigation" class="curved container">
                <span id="nav-home"><a href="http://tomcat.apache.org/">AskForHelp</a></span>
                <span id="nav-hosts"><a href="liveshares">Search</a></span>
                <span id="nav-examples"><a href="myshares">MyShares</a></span>
               
                <span id="nav-help" style="background-color:orange;color:blue"><a href="http://tomcat.apache.org/findhelp.html">Emergency Help</a></span>
                <br class="separator" />
            </div>
			<div id="asf-box">
                <img src="${pageContext.servletContext.contextPath}/images/Icon_Shareki.png" width="120" height="120"/>
                <h1>It is My Social Responsibility. I am an InforHand</h1>
                    
            </div>
				
			<div id="upper" class="curved container">
                <div id="congrats" class="curved container">
                    <h2>Life is more than just living. Help Communities by extending <p style="color:blue;font:bold;font-size:20px">Inforhand</p></h2>
                     
                </div>
                       <div class="curved container">
                        <h3 style="color: black;background-color:orange;">Emergency Help</h3>
                        <p style="color: blue"> <h4> Reach out for or Extend the Help <a href="emergency" style="color: green">Emergency Helper</a> in your region: </h4></p>
                        <pre style="color: blue; font:bold">Post your Personal Emergency</pre>
                        <pre style="color: red; font:bold">Regional Natural Calamities?:</pre>
                        
                        <pre style="color: blue; font:bold">Let the world know and Help</pre>
                        
                        <pre style="color: blue; font:bold">Volunteer For Community Help</pre>
                        
                        <pre style="color: blue; font:bold">Share Regional Safety Concerns</pre>
                        
                        <p style="color: blue; font:bold">Organizations such as blood banks,NGOs that help Societies, Communities kindly register here. &nbsp; <a href="register_org" style="color: green">Register NGOs</a></p>
                        <p style="color: blue; font:bold">Individuals and InforHands kindly register here&nbsp; <a href="register_inforhand" style="color: green">Register InforHand</a></p>
                        
                        <br/>
                        <h3 style="color: black;background-color:orange;">Share Your Observation Of Community Interest</h3>
                        <br/>
                    </div>
        
                <div id="notice">
                    
                </div>
				
			</div>
			
			<div id="upper2" class="curved container">
                <div id="congrats" class="curved container">
                
                    <h2>Whats News <p style="color:blue;font:bold;font-size:20px">Feed Your Observation</p></h2>

  <form:form method="POST" modelAttribute="dataSelector"
		enctype="multipart/form-data" action="shareki">
 
		<form:errors path="*" cssClass="errorblock" element="div" />
 
		<form:input path="fileData" type="file" name="file" style="background-color:orange;"/>
		<input type="submit" value="Upload" style="background-color:orange;"/>
				
		<span><form:errors path="fileData" cssClass="error" />	
		</span>

		<div class="col20">
		<img src="<%=session.getAttribute("uploadedFileName")%>" width="80%" height="80%" align="right" alt="Upload Image" />	
		</div>


<output style="background-color:orange"> Country Selected:</output>

<output>${dataSelector.strSelectedCountry}</output>

<form:select path="" name="strSelectedCity">
<form:option value="" label="--Choose The Nearest City--"></form:option>

<c:forEach var="item" items="${userCityList}">

	<c:forEach var="kit" items="${item}">
	   <form:option value="hi">${kit}</form:option>
	   
	</c:forEach>
	
</c:forEach>

</form:select>

<div class="curved container">

<textarea rows="5" cols="60" name="namedesc" id="namedesc"> </textarea> 
<output style="background-color:orange">Description</output>

<input type="submit" name="submit" value="Submit" style="background-color:blue;color:white;">
</div>


</form:form>

</div>											                    
			<div id="lower">
                <div id="low-manage" class="">
                </div>
      
                <br class="separator" />
            </div>
            
            
			<div id="middle" class="curved container">
                <h3>Inforhand Teams</h3>
                <div class="col25">
                    <div class="container">
                        <p><a href="/docs/setup.html">NGOs</a></p>
                        <p><a href="/docs/appdev/">BloodBanks</a></p>
                        <p><a href="/docs/appdev/">Societies</a></p>
                        
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="/docs/realm-howto.html">Social Metrics</a></p>
                        <p><a href="/docs/jndi-datasource-examples-howto.html">Search</a></p>
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="/examples/">InforHands</a></p>
                        <p><a href="/examples/">Photographers</a></p>
                        <p><a href="/examples/">News Agents</a></p>
                        
                    </div>
                </div>
                <div class="col25">
                    <div class="container">
                        <p><a href="http://wiki.apache.org/tomcat/Specifications">My Village</a></p>
                        <p><a href="http://wiki.apache.org/tomcat/TomcatVersions">My City</a></p>
                    </div>
                </div>
                <br class="separator" />
            </div>
				
		</div>
</body>
</html>
