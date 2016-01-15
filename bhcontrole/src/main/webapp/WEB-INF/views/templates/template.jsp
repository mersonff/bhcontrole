<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>

<c:set var="titleKey">
	<tiles:getAsString name="title" />
</c:set>

<html>
	<head>
		<title><spring:message code="${titleKey}" /></title>
		
		<link rel="apple-touch-icon" sizes="57x57" href="<c:url value='/resources/imgs/favicons/apple-icon-57x57.png'/>">
		<link rel="apple-touch-icon" sizes="60x60" href="<c:url value='/resources/imgs/favicons/apple-icon-60x60.png'/>">
		<link rel="apple-touch-icon" sizes="72x72" href="<c:url value='/resources/imgs/favicons/apple-icon-72x72.png'/>">
		<link rel="apple-touch-icon" sizes="76x76" href="<c:url value='/resources/imgs/favicons/apple-icon-76x76.png'/>">
		<link rel="apple-touch-icon" sizes="114x114" href="<c:url value='/resources/imgs/favicons/apple-icon-114x114.png'/>">
		<link rel="apple-touch-icon" sizes="120x120" href="<c:url value='/resources/imgs/favicons/apple-icon-120x120.png'/>">
		<link rel="apple-touch-icon" sizes="144x144" href="<c:url value='/resources/imgs/favicons/apple-icon-144x144.png'/>">
		<link rel="apple-touch-icon" sizes="152x152" href="<c:url value='/resources/imgs/favicons/apple-icon-152x152.png'/>">
		<link rel="apple-touch-icon" sizes="180x180" href="<c:url value='/resources/imgs/favicons/apple-icon-180x180.png'/>">
		<link rel="icon" type="image/png" sizes="192x192"  href="<c:url value='/resources/imgs/favicons/android-icon-192x192.png'/>">
		<link rel="icon" type="image/png" sizes="32x32" href="<c:url value='/resources/imgs/favicons/favicon-32x32.png'/>">
		<link rel="icon" type="image/png" sizes="96x96" href="<c:url value='/resources/imgs/favicons/favicon-96x96.png'/>">
		<link rel="icon" type="image/png" sizes="16x16" href="<c:url value='/resources/imgs/favicons/favicon-16x16.png'/>">
		<link rel="manifest" href="<c:url value='/resources/imgs/favicons/manifest.json'/>">
		<meta name="msapplication-TileColor" content="#ffffff">
		<meta name="msapplication-TileImage" content="<c:url value='/resources/imgs/favicons/ms-icon-144x144.png'/>">
		<meta name="theme-color" content="#ffffff">
		
		<link href="<c:url value='/resources/css/bootstrap.min.css'  />"
			rel="stylesheet" />
		<link href="<c:url value='/resources/css/site.css'  />" rel="stylesheet" />
		<link href="<c:url value='/resources/css/dataTables.bootstrap.min.css' />" rel="stylesheet" />
		<link href="<c:url value='/resources/css/font-awesome.min.css' />" rel="stylesheet" />
		<link href="<c:url value='/resources/css/bootstrap-select.min.css' />" rel="stylesheet" />
		
		<script src="<c:url value='/resources/js/jquery-1.11.3.min.js' />"></script>
		<script src="<c:url value='/resources/js/bootstrap.min.js' />"></script>
		<script src="<c:url value='/resources/js/jquery.popconfirm.min.js' />"></script>
		<script src="<c:url value='/resources/js/datatables.min.js' />"></script>
		<script src="<c:url value='/resources/js/jquery.maskedinput.min.js' />"></script>
		<script src="<c:url value='/resources/js/cidades-estados-1.4-utf8.min.js' />"></script>
		<script src="<c:url value='/resources/js/validator.js' />"></script>
		<script src="<c:url value='/resources/js/bootstrap-select.js' />"></script>
		<script src="<c:url value='/resources/js/charCount.js' />"></script>
		
		<meta name="viewport" content="width=device-width,initial-scale=1">
		
		<meta charset="UTF-8">
	</head>
	<body>
		<div class="tudo">
			<div class="topo">
				<tiles:insertAttribute name="header" />
			</div>
			<div class="conteudo">
			  	<h3 class="titulo"><spring:message code="${titleKey}" /></h3>
				<br>
				<tiles:insertAttribute name="body" />
			</div>
			<div class="rodape">
				<footer>
			      <div class="container">
			        <div class="row">
			          <div class="col-lg-6 col-md-6 col-sm-6 col-sm-12">
			            <ul>
			              <li><a href="https://www.fb.com/bacanahotel" class=""><i class="fa fa-facebook-square fa-3x"></i></a></li>
			              <li><a href="https://plus.google.com/+bacanahotelbanabuiu"><i class="fa fa-google-plus-square fa-3x"></i></a></li>
			              <li><a href="https://www.tripadvisor.com.br/Hotel_Review-g3249622-d3224440-Reviews-Bacana_Hotel-Banabuiu_State_of_Ceara.html"><i class="fa fa-tripadvisor fa-3x"></i></a></li>
			              <li><a href="https://twitter.com/bacana_hotel"><i class="fa fa-twitter-square fa-3x"></i></a></li>
			              <li><a href="https://www.instagram.com/bacanahotel/"><i class="fa fa-instagram fa-3x"></i></a></li>	            	     
			            </ul>
			          </div>
			          <div class="col-lg-6 col-md-6 col-sm-6 col-sm-12">
			            <p>Copyright &copy; 2015, All Rights Reserved</p>
			          </div>
			        </div>
			      </div>
			    </footer>
		    </div>
			<!--[if IE]>
		            <script src="<c:url value='/resources/js/bootstrap.min.ie.js' />"></script>
		        <![endif]-->
			<!--[if !IE]><!-->
		
			<!--<![endif]-->
		</div>
	</body>
</html>