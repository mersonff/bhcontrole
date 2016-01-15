<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="atualizar.cliente.alt" var="atualizarHospedeAlt"/>
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
						<p><spring:message code="nome.label"/>: <b>${cliente.nome}</b></p>
						<p><spring:message code="cpf.label"/>: <b>${cliente.cpf}</b></p>
						<p><spring:message code="empresa.label"/>: <b>${cliente.empresa}</b></p>
						<p><spring:message code="cnpj.label"/>: <b>${cliente.cnpj}</b></p>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.contato"/>
					</div>
					<div class="panel-body">
						<p><spring:message code="logradouro.label"/>: <b>${cliente.endereco.logradouro}</b><span class="tab"></span> 
						<spring:message code="numero.label"/>: <b>${cliente.endereco.numero}</b></p>
						<p><spring:message code="bairro.label"/>: <b>${cliente.endereco.bairro}</b><span class="tab"></span>
						<spring:message code="estado.label"/>: <b>${cliente.endereco.estado}</b></p>
						<p><spring:message code="cidade.label"/>: <b>${cliente.endereco.cidade}</b></p>
				
						<div>
							<p><spring:message code="fixo.label"/>: <b>${cliente.telefones.telefoneFixo}</b></p>
							<p><spring:message code="celular.label"/>: <b>${cliente.telefones.telefoneCelular}</b></p>
						</div>
					
						<div>
							<p><spring:message code="email.label"/>: <b>${cliente.email}</b></p>
						</div>
				
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="form-group botoes">
		<a title="${atualizarHospedeAlt}" href="${spring:mvcUrl('CC#atualizar').arg(0,cliente.id).build()}" data-toggle="tooltip" data-placement="bottom"
					type="button" class="btn btn-warning">
					<span class="glyphicon glyphicon-edit"></span> <spring:message code="botao.atualizar" /></a> 
		<a href="${spring:mvcUrl('CC#deleta').arg(0,cliente.id).build()}" data-toggle="tooltip" data-placement="bottom"
			class="btn btn-danger popconfirm"><span class="glyphicon glyphicon-trash"></span> 
			<spring:message code="botao.deletar" />
		</a>
		<button class="btn btn-default" onclick="history.go(-1)" data-toggle="tooltip" data-placement="bottom" title="${voltarTooltip}">
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
