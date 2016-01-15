<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="atualizar.usuario.alt" var="atualizarUsuarioAlt"/>
<spring:message code="voltar.tooltip" var="voltarTooltip"/>

<div class="detalhes">
	<div class="container">
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.pessoais"/>
					</div>
					<div class="panel-body">
						<p><spring:message code="nome.label"/>: <b>${usuario.nome}</b></p>
						<p><spring:message code="login.label"/>: <b>${usuario.login}</b></p>
						<p><spring:message code="estado.label"/>: <b>${usuario.estado}</b></p>
						<p><spring:message code="funcoes.label"/>: <b>${usuario.funcoes}</b></p>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.contato"/></h3>
					</div>
					<div class="panel-body">
						<div>
							<p><spring:message code="fixo.label"/>: <b>${usuario.telefones.telefoneFixo}</b></p>
							<p><spring:message code="celular.label"/>: <b>${usuario.telefones.telefoneCelular}</b></p>
						</div>
					
						<div>
							<p><spring:message code="email.label"/>: <b>${usuario.email}</b></p>
						</div>
				
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group botoes">
		<a title="${atualizarUsuarioAlt}" href="${spring:mvcUrl('UC#atualizar').arg(0,usuario.id).build()}"
					type="button" class="btn btn-warning" data-toggle="tooltip" data-placement="bottom">
					<span class="glyphicon glyphicon-edit"></span> <spring:message code="botao.atualizar" /></a> 
		<a href="${spring:mvcUrl('UC#deleta').arg(0,usuario.id).build()}"
			class="btn btn-danger popconfirm"><span class="glyphicon glyphicon-trash"></span> 
			<spring:message code="botao.deletar" />
		</a>
		<button class="btn btn-default" onclick="history.go(-1)" title="${voltarTooltip}" data-toggle="tooltip" data-placement="bottom">
			<span class="glyphicon glyphicon-chevron-left"></span> <spring:message code="botao.voltar" />
		</button>
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
