<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page contentType="text/html; charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h2>user</h2>
	${name }
	<c:out value="name">ss</c:out>
	${list}
	<c:forEach var="x" begin="0" end="10" step="2">
		${x }
	</c:forEach>
	<hr/>
	<c:forEach var="item" items="${list}" varStatus="status">   
	  ${item} 
	  ${status.count%2==0} <br/>
	</c:forEach>   
	
</body>
</html>
