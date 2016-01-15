<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${spring:mvcUrl('IC#home').build()}">
				<span><img src="<c:url value="/resources/imgs/brand.png" />" class="img-responsive" alt="Logo"></span>
				 <span class="navbar-brand-text"><spring:message code="app.nome"/> </span>
			</a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a href="${spring:mvcUrl('IC#home').build()}"><span class=""></span> Home</a></li>
				<li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"><span class="dropdown-toggle"></span> <spring:message code="menu.hospedagens"/><span class="caret"></span> </a>
					<ul class="dropdown-menu">
						<li><a href="${spring:mvcUrl('HC#adiciona').build()}"> 
							<span class=""></span> <spring:message code="menu.hospedagens.adicionar"/> </a></li>
						<li role="separator" class="divider"></li>
						<li><a href="${spring:mvcUrl('HC#listar').build()}">
							<span class=""></span> <spring:message code="menu.hospedagens.listar"/></a></li>
					</ul>
				</li>
				<li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"><span class="dropdown-toggle"></span> <spring:message code="menu.reservas"/><span class="caret"></span> </a>
					<ul class="dropdown-menu">
						<li><a href="${spring:mvcUrl('RC#adiciona').build()}"> 
							<span class=""></span> <spring:message code="menu.reservas.adicionar"/> </a></li>
						<li role="separator" class="divider"></li>
						<li><a href="${spring:mvcUrl('RC#listar').build()}">
							<span class=""></span> <spring:message code="menu.reservas.listar"/></a></li>
					</ul>
				</li>
				<li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"><span class="dropdown-toggle"></span> <spring:message code="menu.clientes"/><span class="caret"></span> </a>
					<ul class="dropdown-menu">
						<li><a href="${spring:mvcUrl('CC#adiciona').build()}"> 
							<span class=""></span> <spring:message code="menu.clientes.adicionar"/> </a></li>
						<li role="separator" class="divider"></li>
						<li><a href="${spring:mvcUrl('CC#listar').build()}">
							<span class=""></span> <spring:message code="menu.clientes.listar"/></a></li>
					</ul>
				</li>
				<li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expanded="false"><span class="dropdown-toggle"></span> <spring:message code="menu.apartamentos"/><span class="caret"></span> </a>
					<ul class="dropdown-menu">
						<li><a href="${spring:mvcUrl('AC#adiciona').build()}"> 
							<span class=""></span> <spring:message code="menu.apartamentos.adicionar"/> </a></li>
						<li role="separator" class="divider"></li>
						<li><a href="${spring:mvcUrl('AC#listar').build()}">
							<span class=""></span> <spring:message code="menu.apartamentos.listar"/></a></li>
					</ul>
				</li>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"><span class="dropdown-toggle"></span> <spring:message code="menu.relatorios"/><span class="caret"></span> </a>
						<ul class="dropdown-menu">
							<li><a href="${spring:mvcUrl('JC#form1').build()}"> 
								<span class="glyphicon glyphicon-file"></span> <spring:message code="menu.relatorios.clienteHospedagens"/> </a></li>
							<li role="separator" class="divider"></li>
							<li><a href="${spring:mvcUrl('JC#form2').build()}">
								<span class="glyphicon glyphicon-file"></span> <spring:message code="menu.relatorios.hospedagens"/></a></li>
						</ul>
					</li>
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="dropdown"><a href="#" data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="false"><span class="dropdown-toggle"></span> <spring:message code="menu.usuarios"/><span class="caret"></span> </a>
						<ul class="dropdown-menu">
							<li><a href="${spring:mvcUrl('UC#adiciona').build()}"> 
								<span class=""></span> <spring:message code="menu.usuarios.adicionar"/> </a></li>
							<li role="separator" class="divider"></li>
							<li><a href="${spring:mvcUrl('UC#listar').build()}">
								<span class=""></span> <spring:message code="menu.usuarios.listar"/></a></li>
						</ul>
					</li>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<sec:authentication property="principal" var="user"/>
					<li>
						<p class="navbar-text"><spring:message code="boas.vindas.msg"/>
						<a href="${spring:mvcUrl('UC#minhaInfo').build()}" class="navbar-link"> ${user.username}</a>,
						<a href="<c:url value="/logout" />" class="navbar-link"> Logout 
						<span class="glyphicon glyphicon-log-out"></span> </a></p>
					</li>
				</sec:authorize>
				
				
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>