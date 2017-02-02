<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
	<head>
		<title>AddUI</title>
	</head>
	<body>
		
		<s:form action="role_add">
			<s:textfield name="name"></s:textfield>
			<s:textarea name="description"></s:textarea>
			<s:submit vlaue="提交"></s:submit>
		</s:form>
		
	
	</body>
</html>
