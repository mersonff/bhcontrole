<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="novo.cliente" var="novoCliente"/>
<spring:message code="novo.cliente.alt" var="novoClienteAlt"/>
<spring:message code="detalhes.cliente.alt" var="detalhesClienteAlt"/>
<spring:message code="atualizar.cliente.alt" var="atualizarClienteAlt"/>
<spring:message code="deletar.cliente.alt" var="deletarClienteAlt"/>

<div class="container">
	<div class="row">
		<c:if test="${!empty success}">
			<div class="alert alert-success alert-dismissable fade in">
				<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
				${success}
			</div>
		</c:if>
		
		<c:if test="${!empty error}">
			<div class="alert alert-danger alert-dismissable fade in">	
				<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
				${error}
			</div>
		</c:if>
	</div>
</div>
<div class="container">
	<div class="row">
		<a title="${novoClienteAlt}" href="${spring:mvcUrl('CC#adiciona').build()}" data-toggle="tooltip" data-placement="bottom"
			class="btn btn-primary botao-table-adiciona"><span class="glyphicon glyphicon-plus"></span> ${novoCliente}</a>
	</div>
</div>
<br>
<div class="container">
	<div class="row">
		<div class="table-responsive table-clientes">
			<table class="table table-hover table-striped table-bordered" id="tabelas">
				<thead>
					<tr>
						<th>#ID</th>
						<th><spring:message code="nome.label"/> </th>
						<th><spring:message code="cpf.label"/> </th>
						<th><spring:message code="empresa.label"/> </th>
						<th><spring:message code="cnpj.label"/> </th>
						<th><spring:message code="celular.label"/> </th>
						<th><spring:message code="acoes.label"/> </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${clientes}" var="cliente">
						<tr>
							<td>${cliente.id}</td>
							<td>${cliente.nome}</td>
							<td>${cliente.cpf}</td>
							<td>${cliente.empresa}</td>
							<td>${cliente.cnpj}</td>
							<td>${cliente.telefones.telefoneCelular}</td>
							<td>
								<a title="${detalhesClienteAlt}" href="${spring:mvcUrl('CC#detalhes').arg(0,cliente.id).build()}" data-toggle="tooltip" data-placement="bottom"
								 class="btn btn-info"><span class="glyphicon glyphicon-eye-open"></span> </a> 
								
								<a title="${atualizarClienteAlt}" href="${spring:mvcUrl('CC#atualizar').arg(0,cliente.id).build()}" data-toggle="tooltip" data-placement="bottom"
								type="button" class="btn btn-warning"><span class="glyphicon glyphicon-edit"></span> </a> 
							
							<a href="${spring:mvcUrl('CC#deleta').arg(0,cliente.id).build()}" data-toggle="tooltip" data-placement="bottom"
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
