<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<spring:message code="enviar.tooltip" var="enviarTooltip"/>
<spring:message code="limpar.tooltip" var="limparTooltip"/>
<spring:message code="voltar.tooltip" var="voltarTooltip"/>

<section id="formApartamento">
	<div class="container">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<form:form action="${spring:mvcUrl('JC#clienteHospedagemPeriodo').build()}" 
					method="post" commandName="dateForm" target="_blank">

					<spring:hasBindErrors name="dateForm">
						<div class="alert alert-danger" role="alert">
							<strong><spring:message code="erro.no.formulario" /></strong>			
							<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
							  <!--  <ul>
									<c:forEach var="error" items="${errors.allErrors}">
										<li>${error.field}: ${error.defaultMessage}</li>
									</c:forEach>
								</ul> -->
						</div>
					</spring:hasBindErrors>	
				
					<div class="panel panel-primary">
						<div class="panel-heading cabecalho-painel">
							<spring:message code="relatorio.cliente.hospedagem.datas" />
						</div>
						<div class="panel-body">
							<div class="form-horizontal">
								<spring:bind path="cliente">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<form:label path="cliente" cssClass="col-sm-2 control-label">
											<spring:message code="cliente1.label" />:
										</form:label>
										<div class="col-sm-8">
											<select id="cliente" name="cliente" class="col-sm-8 selectpicker form-control" data-size="5" data-live-search="true">
												<option data-icon="fa fa-search" value="0"><spring:message code="select.hospede"/></option>
												<c:forEach items="${clientes}" var="cliente">
													<option value="${cliente.id}" data-icon="fa fa-user" data-subtext="${cliente.cpf}"
														<c:if test="${cliente.id eq dateForm.cliente.id}">selected="selected"</c:if> >${cliente.nome}</option>
												</c:forEach>
											</select>
										</div>
										<div class="col-sm-10 col-sm-offset-2">
											<form:errors path="cliente" cssClass="erro" />	
										</div>										
									</div>
								</spring:bind>
								<spring:bind path="dataInicial">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<form:label path="dataInicial" cssClass="col-sm-2 control-label">
											<spring:message code="dataInicial.label" />:
										</form:label>
										<div class="col-sm-3">
											<form:input path="dataInicial" cssClass="form-control" type="date"/>
										</div>
										<div class="col-sm-7 col-sm-offset-2">
											<form:errors path="dataInicial" cssClass="erro" />												
										</div>
									</div>
								</spring:bind>
									<spring:bind path="dataFinal">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<form:label path="dataFinal" cssClass="col-sm-2 control-label">
											<spring:message code="dataFinal.label" />:
										</form:label>
										<div class="col-sm-3">
											<form:input path="dataFinal" cssClass="form-control" type="date" />
										</div>
										<div class="col-sm-7 col-sm-offset-2">
											<form:errors path="dataFinal" cssClass="erro" />												
										</div>
									</div>
								</spring:bind>
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="formato" cssClass="col-sm-2 control-label">
										<spring:message code="formato.label" />:
									</form:label>
									<div class="col-sm-4">
										<ul class="verticalRadios">							
						            		<form:radiobuttons path="formato" items="${formatos}" element="li"/>
						            	</ul>
					            	</div>
					            </div>
							</div>
						</div>
					</div>
				
					<div class="form-actions botoes">
						<button type="submit" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" 
						title="${enviarTooltip}">
							<span class="fa fa-paper-plane-o"></span> <spring:message code="botao.enviar" />
						</button>
						<button type="reset" class="btn btn-warning" data-toggle="tooltip" data-placement="bottom"
						title="${limparTooltip}">
							<span class="glyphicon glyphicon-erase"></span> <spring:message code="botao.limpar" />
						</button>
						<button class="btn btn-default" onclick="history.go(-1)" data-toggle="tooltip" data-placement="bottom"
						title="${voltarTooltip}">
							<span class="glyphicon glyphicon-chevron-left"></span> <spring:message code="botao.voltar" />
						</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>
<script>

	jQuery(function($) {
		$("#cpf").mask("999.999.999-99");
		$("#cnpj").mask("99.999.999/9999-99");
		$(".fixo").mask("(99) 9999-9999");
		$(".celular").mask("(99) 99999-9999");
		$(".cep").mask("99999-999");
	});

	(function ($) {
		  $.fn.selectpicker.defaults = {
		    noneSelectedText: '<spring:message code="selectpicker.noneSelectedText"/> ',
		    noneResultsText: '<spring:message code="selectpicker.noneResultsText"/>',
		    countSelectedText: '<spring:message code="selectpicker.countSelectedText"/>',
		    maxOptionsText: <spring:message code="selectpicker.maxOptionsText"/>,
		    multipleSeparator: '<spring:message code="selectpicker.multipleSeparator"/> '
		  };
	})(jQuery);
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
</script>