<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message code="novo.apartamento" var="novoApartamento"/>
<spring:message code="novo.apartamento.alt" var="novoApartamentoAlt"/>
<spring:message code="atualizar.apartamento.alt" var="atualizarApartamentoAlt"/>

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
		<div class="botao-adiciona" >
			<a title="${novoApartamentoAlt}" href="${spring:mvcUrl('AC#adiciona').build()}" data-toggle="tooltip" data-placement="bottom"
				class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> ${novoApartamento}</a>
		</div>
			
		<div class="legenda-estados" >
			<p> 
				<b class="bg-success"><spring:message code="legenda.disponivel"/></b> 
				<b class="bg-warning"><spring:message code="legenda.sujo"/></b> 
				<b class="bg-danger"><spring:message code="legenda.ocupado"/></b>
			</p> 
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="table-responsive">
			<table class="table table-hover table-striped table-bordered" id="tabelas">
				<thead>
					<tr>
						<th><spring:message code="numero.label"/> </th>
						<th><spring:message code="estado.label"/> </th>
						<th><spring:message code="acoes.label"/> </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${apartamentos}" var="apartamento">
						<tr>
							<td>${apartamento.numero}</td>
							<td class="${apartamento.estado == 'Disponível' ? 'success' : '' ||
							apartamento.estado == 'Sujo' ? 'warning' : '' ||
							apartamento.estado == 'Ocupado' ? 'danger' : ''}">${apartamento.estado}</td>
							<td> 
								<a title="${atualizarApartamentoAlt}" href="${spring:mvcUrl('AC#atualizar').arg(0,apartamento.id).build()}" data-toggle="tooltip" data-placement="bottom"
								type="button" class="btn btn-warning"><span class="glyphicon glyphicon-edit"></span> </a> 
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<a href="${spring:mvcUrl('AC#deleta').arg(0,apartamento.id).build()}" data-toggle="tooltip" data-placement="bottom"
									 class="btn btn-danger popconfirm"><span class="glyphicon glyphicon-trash"></span> </a>
								</sec:authorize>
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
				search: '<b><span class="glyphicon glyphicon-search"></span> <spring:message code="datatable.search"/></b>',
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
