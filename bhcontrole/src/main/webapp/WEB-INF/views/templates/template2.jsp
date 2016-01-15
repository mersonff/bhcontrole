<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!doctype html>

<c:set var="titleKey">
	<tiles:getAsString name="title" />
</c:set>

<html>
	<head>
		<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
		<title><spring:message code="${titleKey}" /></title>
		<link href="<c:url value='/resources/css/bootstrap.min.css'  />"
			rel="stylesheet" />
		<link href="<c:url value='/resources/css/font-awesome.min.css' />" rel="stylesheet" />
		<link href="<c:url value='/resources/css/site.css'  />" rel="stylesheet" />
		
		<script src="<c:url value='/resources/js/jquery-1.11.3.min.js' />"></script>
		<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
		
		<meta name="viewport" content="width=device-width,initial-scale=1">
		
		<meta charset="UTF-8">
	</head>
	<body>
		
		
				<tiles:insertAttribute name="body" />
	
	
		<!--[if IE]>
	            <script src="<c:url value='/resources/js/bootstrap.min.ie.js' />"></script>
	        <![endif]-->
		<!--[if !IE]><!-->
	
		<!--<![endif]-->
	</body>
</html>