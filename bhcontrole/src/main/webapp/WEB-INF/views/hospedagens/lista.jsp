<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message code="nova.hospedagem" var="novaHospedagem"/>
<spring:message code="nova.hospedagem.alt" var="novaHospedagemAlt"/>
<spring:message code="detalhes.hospedagem.alt" var="detalhesHospedagemAlt"/>
<spring:message code="atualizar.hospedagem.alt" var="atualizarHospedagemAlt"/>
<spring:message code="deletar.hospedagem.alt" var="deletarHospedagemAlt"/>
<spring:message code="despesa.hospedagem.alt" var="despesaHospedagemAlt"/>
<spring:message code="checkout.hospedagem.alt" var="checkoutHospedagemAlt"/>

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
			<a title="${novaHospedagemAlt}" href="${spring:mvcUrl('HC#adiciona').build()}" data-toggle="tooltip" data-placement="bottom"
				class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> ${novaHospedagem}</a>
		</div>
	</div>
</div>
<br>
<div class="container">
	<div class="row">
		<div class="table-responsive table-hospedagem">
			<table class="table table-hover table-striped table-bordered" id="tabelas">
				<thead>
					<tr>
						<th>#ID</th>
						<th><spring:message code="nome.label"/> </th>
						<th><spring:message code="AP"/> </th>
						<th><spring:message code="dataEntrada.label"/> </th>
						<th><spring:message code="dataSaida.label"/> </th>
						<th><spring:message code="valorDiaria.label"/> </th>
						<th><spring:message code="totalDiarias.label"/></th>
						<th><spring:message code="despesas.label"/></th>
						<th><spring:message code="total.label"/></th>
						<th><spring:message code="acoes.label"/> </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${hospedagens}" var="hospedagem">
						<tr>
							<td>${hospedagem.id}</td>
							<td>${hospedagem.cliente.nome}</td>
							<td>${hospedagem.apartamento.numero}</td>
							<td><fmt:formatDate value="${hospedagem.dataEntrada.time}" pattern="dd/MM/yyyy"/></td>
							<td>
								<c:choose> 
									<c:when test="${empty hospedagem.dataSaida}">
										<spring:message code="legenda.emAberto"/>  
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${hospedagem.dataSaida.time}" pattern="dd/MM/yyyy"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>R$ ${hospedagem.valorDiaria}</td>
							<td>R$ ${hospedagem.calculaTotalDiarias()}</td>
							<td>R$ ${hospedagem.somaDespesas()}</td>
							<td>R$ ${hospedagem.calculaTotal()}</td>
							<td>
								<c:if test="${hospedagem.dataSaida == null}">
									<a title="${checkoutHospedagemAlt}" href="${spring:mvcUrl('HC#checkout').arg(0,hospedagem.id).build()}"
									 class="btn btn-primary btn-sm" data-toggle="tooltip" data-placement="bottom"><span class="fa fa-money"></span></a>
									
									<a title="${despesaHospedagemAlt}" href="${spring:mvcUrl('HC#despesas').arg(0,hospedagem.id).build()}"
									 class="btn btn-primary btn-sm" data-toggle="tooltip" data-placement="bottom"><span class="fa fa-credit-card"></span></a>
								
									<a title="${atualizarHospedagemAlt}" href="${spring:mvcUrl('HC#atualizar').arg(0,hospedagem.id).build()}"
										type="button" class="btn btn-warning btn-sm" data-toggle="tooltip" data-placement="bottom"><span class="glyphicon glyphicon-edit"></span> </a> 
								</c:if>
								
								<a title="${detalhesHospedagemAlt}" href="${spring:mvcUrl('HC#detalhes').arg(0,hospedagem.id).build()}" data-toggle="tooltip" data-placement="bottom"
								 class="btn btn-info btn-sm"><span class="glyphicon glyphicon-eye-open"></span> </a> 	
								
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<a href="${spring:mvcUrl('HC#deleta').arg(0,hospedagem.id).build()}" data-toggle="tooltip" data-placement="bottom"
									 class="btn btn-danger popconfirm btn-sm"><span class="glyphicon glyphicon-trash"></span> </a>
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