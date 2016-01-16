<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:message code="atualizar.reserva.alt" var="atualizarReservaAlt"/>
<spring:message code="voltar.tooltip" var="voltarTooltip"/>

<div class="detalhes">
	<div class="container">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<div class="panel panel-info">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.reserva"/>
					</div>
					<div class="panel-body">
						<p><spring:message code="cliente1.label"/>: <b>${reserva.nome}</b></p>
						<p><spring:message code="dataSolicitacao.label"/>: <b><fmt:formatDate value="${reserva.dataSolicitacao.time}" pattern="dd/MM/yyyy HH:mm:ss"/></b></p>
						<p><spring:message code="dataEntrada.label"/>: <b><fmt:formatDate value="${reserva.dataEntrada.time}" pattern="dd/MM/yyyy HH:mm:ss"/></b></p>
						<p><spring:message code="quantidadeDeQuartos.label"/>: <b>${reserva.quantidadeDeQuartos}</b></p>
						<p><spring:message code="quantidadeDeClientes.label"/>: <b>${reserva.quantidadeDeHospedes}</b></p>
						<div>
							<p><spring:message code="fixo.label"/>: <b>${reserva.telefones.telefoneFixo}</b></p>
							<p><spring:message code="celular.label"/>: <b>${reserva.telefones.telefoneCelular}</b></p>
						</div>
						<div>
							<p><spring:message code="email.label"/>: <b>${reserva.email}</b></p>
						</div>
						<p><spring:message code="comentario.label"/>:<b> <textarea  class="form-control" disabled
										placeholder="${comentarioPlaceHolder}" rows="4">${reserva.comentario}</textarea></b></p>
					</div>
				</div>
				
				<div class="form-group botoes">
					<a title="${atualizarReservaAlt}" href="${spring:mvcUrl('RC#atualizar').arg(0,reserva.id).build()}"
								type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="bottom">
								<span class="glyphicon glyphicon-edit"></span> <spring:message code="botao.atualizar" /></a> 
					<a href="${spring:mvcUrl('RC#deleta').arg(0,reserva.id).build()}" data-toggle="tooltip" data-placement="bottom"
						class="btn btn-danger popconfirm"><span class="glyphicon glyphicon-trash"></span> 
						<spring:message code="botao.deletar" />
					</a>
					<button class="btn btn-default" onclick="history.go(-1)" title="${voltarTooltip}" data-toggle="tooltip" data-placement="bottom">
						<span class="glyphicon glyphicon-chevron-left"></span> <spring:message code="botao.voltar" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>
<script>
	$(".popconfirm").popConfirm({
		yesBtn: '<spring:message code="popconfirm.btn.ok"/>',
		noBtn: '<spring:message code="popconfirm.btn.cancelar"/>',
		content: '<p class="text-center"><spring:message code="popconfirm.btn.conteudo"/></p>',
		placement: 'top',
		title: '<p class="text-center"><spring:message code="popconfirm.btn.titulo"/></p>'
	});
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})	
</script>
