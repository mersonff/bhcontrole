<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="nova.reserva" var="novaReserva"/>
<spring:message code="nova.reserva.alt" var="novaReservaAlt"/>
<spring:message code="detalhes.reserva.alt" var="detalhesReservaAlt"/>
<spring:message code="atualizar.reserva.alt" var="atualizarReservaAlt"/>
<spring:message code="deletar.reserva.alt" var="deletarReservaAlt"/>

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
		<div class="botao-adiciona" >
			<a title="${novaReservaAlt}" href="${spring:mvcUrl('RC#adiciona').build()}" data-toggle="tooltip" data-placement="bottom"
				class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> ${novaReserva}</a>
		</div>
		
		<div class="legenda-estados" >
			<p> 
				<b class="bg-success"><spring:message code="legenda.concluido"/></b> 
				<b class="bg-warning"><spring:message code="legenda.emAberto"/></b> 
				<b class="bg-danger"><spring:message code="legenda.cancelada"/></b>
			</p> 
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="table-responsive table-reservas">
			<table class="table table-hover table-striped table-bordered" id="tabelas">
				<thead>
					<tr>
						<th>#ID</th>
						<th><spring:message code="cliente1.label"/> </th>
						<th><spring:message code="dataSolicitacao.label"/> </th>
						<th><spring:message code="dataEntrada.label"/> </th>
						<th><spring:message code="celular.label"/> </th>
						<th><spring:message code="estado.label"/> </th>
						<th><spring:message code="acoes.label"/> </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${reservas}" var="reserva">
						<tr>
							<td>${reserva.id}</td>
							<td>${reserva.nome}</td>
							<td><fmt:formatDate value="${reserva.dataSolicitacao.time}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
							<td><fmt:formatDate value="${reserva.dataEntrada.time}" pattern="dd/MM/yyyy HH:mm:ss"/></td>
							<td>${reserva.telefones.telefoneCelular}</td>
							<td class="${reserva.estado == 'Em aberto' ? 'warning' : '' ||
							reserva.estado == 'Concluído' ? 'success' : '' ||
							reserva.estado == 'Cancelada' ? 'danger' : ''}">${reserva.estado}</td>
							<td>
								<a title="${detalhesReservaAlt}" href="${spring:mvcUrl('RC#detalhes').arg(0,reserva.id).build()}"
								 class="btn btn-info" data-toggle="tooltip" data-placement="bottom"><span class="glyphicon glyphicon-eye-open"></span> </a> 
								
								<a title="${atualizarReservaAlt}" href="${spring:mvcUrl('RC#atualizar').arg(0,reserva.id).build()}"
								type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="bottom"><span class="glyphicon glyphicon-edit"></span> </a> 
							
							<a href="${spring:mvcUrl('RC#deleta').arg(0,reserva.id).build()}" data-toggle="tooltip" data-placement="bottom"
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
