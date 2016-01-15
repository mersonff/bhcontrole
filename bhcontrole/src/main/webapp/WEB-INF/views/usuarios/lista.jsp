<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="novo.usuario" var="novoUsuario"/>
<spring:message code="novo.usuario.alt" var="novoUsuarioAlt"/>
<spring:message code="detalhes.usuario.alt" var="detalhesUsuarioAlt"/>
<spring:message code="atualizar.usuario.alt" var="atualizarUsuarioAlt"/>
<spring:message code="atualizar.senha.alt" var="atualizarSenhaAlt"/>

<div class="container">
	<div class="row">
		<c:if test="${!empty success}">
			<div class="alert alert-success alert-dismissable fade in">
				<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
				${success}
			</div>
		</c:if>
	</div>
</div>

<div class="container">
	<div class="row">
		<a title="${novoUsuarioAlt}" href="${spring:mvcUrl('UC#adiciona').build()}" data-toggle="tooltip" data-placement="bottom"
			class="btn btn-primary botao-table-adiciona"><span class="glyphicon glyphicon-plus"></span> ${novoUsuario}</a>
	</div>
</div>
<br>
<div class="container">
	<div class="row">
		<div class="table-responsive table-usuarios">
			<table class="table table-hover table-striped table-bordered" id="tabelas">
				<thead>
					<tr>
						<th>#ID</th>
						<th><spring:message code="nome.label"/> </th>
						<th><spring:message code="login.label"/> </th>
						<th><spring:message code="funcoes.label"/> </th>
						<th><spring:message code="email.label"/> </th>
						<th><spring:message code="celular.label"/> </th>
						<th><spring:message code="acoes.label"/> </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${usuarios}" var="usuario">
						<tr>
							<td>${usuario.id}</td>
							<td>${usuario.nome}</td>
							<td>${usuario.login}</td>
							<td>${usuario.funcoes}</td>
							<td>${usuario.email}</td>
							<td>${usuario.telefones.telefoneCelular}</td>
							<td>
								<a title="${detalhesUsuarioAlt}" href="${spring:mvcUrl('UC#detalhes').arg(0,usuario.id).build()}"
								 class="btn btn-info" data-toggle="tooltip" data-placement="bottom"><span class="glyphicon glyphicon-eye-open"></span> </a> 
								
								<a title="${atualizarUsuarioAlt}" href="${spring:mvcUrl('UC#atualizar').arg(0,usuario.id).build()}"
								type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="bottom"><span class="glyphicon glyphicon-edit"></span> </a> 
							
								<a href="${spring:mvcUrl('UC#deleta').arg(0,usuario.id).build()}"
								 class="btn btn-danger popconfirm"><span class="glyphicon glyphicon-trash"></span> </a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<script>
	$(".popconfirm").popConfirm({
		yesBtn: '<spring:message code="popconfirm.btn.ok"/>',
		noBtn: '<spring:message code="popconfirm.btn.cancelar"/>',
		content: '<p class="text-center"><spring:message code="popconfirm.btn.conteudo"/></p>',
		placement: 'left',
		title: '<p class="text-center"><spring:message code="popconfirm.btn.titulo"/></p>'
	});		
	
	$(document).ready( function () {
	    $('#tabelas').DataTable({
			language: {
				processing: '<spring:message code="datatable.processing"/>',
				search: '<span class="glyphicon glyphicon-search"></span> <spring:message code="datatable.search"/>',
				lengthMenu: '<p class="datatable-lenght-menu"><spring:message code="datatable.lengthMenu"/></p>',
				info: '<spring:message code="datatable.info"/>',
				infoEmpty: '<spring:message code="datatable.infoEmpty"/>',
				infoFiltered: '<spring:message code="datatable.infoFiltered"/>',
				infoPostFix: "",
				loadingRecords: '<spring:message code="datatable.loadingRecords"/>',
				zeroRecords: '<spring:message code="datatable.zeroRecords"/>',
				emptyTable: '<spring:message code="datatable.emptyTable"/>',
				paginate: {
					first: '<spring:message code="datatable.paginate.first"/>',
					last: '<spring:message code="datatable.paginate.last"/>',
					next: '<spring:message code="datatable.paginate.next"/>',
					previous: '<spring:message code="datatable.paginate.previous"/>',
					},
				aria: {
					sortAscending: '<spring:message code="datatable.aria.sortAscending"/>',
					sortDescending: '<spring:message code="datatable.aria.sortDescending"/>'
					}
				}

		    });
	} );
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
</script>
