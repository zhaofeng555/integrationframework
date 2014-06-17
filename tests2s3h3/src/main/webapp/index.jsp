<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page contentType="text/html; charset=utf-8" %>
<html>
<head>
<title>test s2s3h3</title>
</head>
<body>
<h2>Hello World!</h2>
<a href="user/list.action">遍历用户</a>
<a href="user/error.action">测试错误</a>
<hr>
<form method="post" action="user/save.action">
用户名：<input type="text" name="user.username"/><br/>
密码：<input type="text" name="user.password"/><br/>
<input type="submit" value="提交"/>
</form>
</body>
</html>
