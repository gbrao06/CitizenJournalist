<%@page contentType="text/html;charset=UTF-8"%>

<%@page pageEncoding="UTF-8"%>

<%@ page session="true"%>

<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<html>
<body>
	<h2>Spring MVC file upload example</h2>
 
	FileName : "
	<strong> ${fileName} </strong>" - Uploaded Successful.

<img src="<%=session.getAttribute("fileName")%>" width="140px" height="140px" alt="Upload Image" />
  
</body>
</html>
