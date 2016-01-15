<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
						<p><spring:message code="nome.label"/>: <b>${hospedagem.cliente.nome}</b></p>
						<p><spring:message code="cpf.label"/>: <b>${hospedagem.cliente.cpf}</b></p>
						<p><spring:message code="empresa.label"/>: <b>${hospedagem.cliente.empresa}</b></p>
						<p><spring:message code="cnpj.label"/>: <b>${hospedagem.cliente.cnpj}</b></p>
					</div>
				</div>
			</div>
			<div class="col-sm-6">	
				<div class="panel panel-info">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.contato"/>
					</div>
					<div class="panel-body">
						<p><spring:message code="logradouro.label"/>: <b>${hospedagem.cliente.endereco.logradouro}</b><span class="tab"></span> 
						<spring:message code="numero.label"/>: <b>${hospedagem.cliente.endereco.numero}</b></p>
						<p><spring:message code="bairro.label"/>: <b>${hospedagem.cliente.endereco.bairro}</b><span class="tab"></span>
						<spring:message code="estado.label"/>: <b>${hospedagem.cliente.endereco.estado}</b></p>
						<p><spring:message code="cidade.label"/>: <b>${hospedagem.cliente.endereco.cidade}</b></p>
				
						<div>
							<p><spring:message code="fixo.label"/>: <b>${hospedagem.cliente.telefones.telefoneFixo}</b></p>
							<p><spring:message code="celular.label"/>: <b>${hospedagem.cliente.telefones.telefoneCelular}</b></p>
						</div>
					
						<div>
							<p><spring:message code="email.label"/>: <b>${hospedagem.cliente.email}</b></p>
						</div>
				
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.despesas"/>
					</div>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover table-striped" id="tabelas" width="100%">
								<thead>
									<tr>
										<th><spring:message code="dataDaDespesa.label"/> </th>
										<th><spring:message code="descricao.label"/> </th>
										<th><spring:message code="tipo.label"/> </th>
										<th><spring:message code="valor.label"/> </th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${hosp_despesas}" var="despesa">
										<tr>
											<td><fmt:formatDate value="${despesa.dataDaDespesa.time}" pattern="dd/MM/yyyy"/></td>
											<td>${despesa.descricao}</td>
											<td>${despesa.tipo}</td>
											<td>R$ ${despesa.valor}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>	
						<div class="total-despesas-div text-right">
								<p><spring:message code="totalDespesas.label"/><b>: R$ ${hospedagem.somaDespesas()}</b></p>
						</div>			
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="panel panel-info">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.hospedagem2"/>
					</div>
					<div class="panel-body">
						<p><spring:message code="apartamento.label"/>: <b>${hospedagem.apartamento.numero}</b></p>
						<p><spring:message code="dataEntrada.label"/>: <b><fmt:formatDate value="${hospedagem.dataEntrada.time}" pattern="dd/MM/yyyy HH:mm"/></b></p>
						<p><spring:message code="dataSaida.label"/>: 
							<b>
								<c:choose> 
									<c:when test="${empty hospedagem.dataSaida}">
										<spring:message code="legenda.emAberto"/>  
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${hospedagem.dataSaida.time}" pattern="dd/MM/yyyy HH:mm"/>
									</c:otherwise>
								</c:choose>
							</b>
						</p>
						<div>
							<p><spring:message code="valorDiaria.label"/>: <b>R$ ${hospedagem.valorDiaria}</b><span class="tab">
							</span> <spring:message code="totalDiarias.label"/>: <b>R$ ${hospedagem.calculaTotalDiarias()}</b></p>
						</div>
						<p><spring:message code="despesas.label"/>: <b>R$ ${hospedagem.somaDespesas()}</b></p>
						<p><spring:message code="total.label"/>: <b>R$ ${hospedagem.calculaTotal()}</b></p>				
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="form-group botoes">
		<div class="container">
			<div class="row">
				<div class="col-sm-6 col-xs-12">
					<a href="${spring:mvcUrl('HC#checkout').arg(0,hospedagem.id).build()}" data-toggle="tooltip" data-placement="bottom"
						class="btn btn-primary botao-espaco" title="${checkoutHospedagemAlt}"><span class="fa fa-money"></span> 
						<spring:message code="botao.checkout" />
					</a>
					<a href="${spring:mvcUrl('HC#despesas').arg(0,hospedagem.id).build()}" data-toggle="tooltip" data-placement="bottom"
						class="btn btn-primary botao-espaco" title="${despesaHospedagemAlt}"><span class="fa fa-credit-card"></span> 
						<spring:message code="botao.despesas" />
					</a>
				</div>
				<div class="col-sm-6 col-xs-12">
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
					  <i class="fa fa-file"></i> <spring:message code="botao.exportacao"/>
					</button>
					<a href="${spring:mvcUrl('HC#atualizar').arg(0,hospedagem.id).build()}" data-toggle="tooltip" data-placement="bottom"
						class="btn btn-warning botao-espaco" title="${atualizarHospedagemAlt}"><span class="glyphicon glyphicon-edit"></span> 
						<spring:message code="botao.atualizar" />
					</a> 
					<sec:authorize access="hasRole('ROLE_ADMIN')">
						<a href="${spring:mvcUrl('HC#deleta').arg(0,hospedagem.id).build()}" data-toggle="tooltip" data-placement="bottom"
							class="btn btn-danger botao-espaco popconfirm"><span class="glyphicon glyphicon-trash"></span> 
							<spring:message code="botao.deletar" />
						</a>
					</sec:authorize>
					<a type="button" class="btn btn-default botao-espaco" onclick="history.go(-1)" title="${voltarTooltip}" data-toggle="tooltip" data-placement="bottom">
						<span class="glyphicon glyphicon-chevron-left"></span> <spring:message code="botao.voltar" />
					</a>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header text-center">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel"><spring:message code="titulo.modal.exportacao"/> </h4>
      </div>
      <div class="modal-body text-center">
        <a target="_blank" href="${spring:mvcUrl('JC#relatorioHospedagemPDF').arg(0,hospedagem.id).build()}" 
         	type="button" class="btn btn-info"><i class="fa fa-file-pdf-o"></i> PDF</a>
        <a target="_blank" href="${spring:mvcUrl('JC#relatorioHospedagemHTML').arg(0,hospedagem.id).build()}" 
        	type="button" class="btn btn-info"><i class="fa fa-code"></i> HTML</a>
        <a href="${spring:mvcUrl('JC#relatorioHospedagemDOCX').arg(0,hospedagem.id).build()}" 
        	type="button" class="btn btn-info"> <i class="fa fa-file-word-o"></i> DOCX</a>
        <a href="${spring:mvcUrl('JC#relatorioHospedagemXLS').arg(0,hospedagem.id).build()}" 
        	type="button" class="btn btn-info"><i class="fa fa-file-excel-o"></i> XLS</a>
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
