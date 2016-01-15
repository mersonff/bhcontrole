<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="salvar.tooltip" var="salvarTooltip"/>
<spring:message code="limpar.tooltip" var="limparTooltip"/>
<spring:message code="voltar.tooltip" var="voltarTooltip"/>

<div class="container">
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
		
			<form:form action="${spring:mvcUrl('HC#atualiza').build()}" method="post" commandName="hospedagem">
			
				<c:if test="${!empty error}">
					<div class="alert alert-danger alert-dismissable fade in">
						<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					</div>
				</c:if>
			
				<spring:hasBindErrors name="hospedagem">
					<div class="alert alert-danger" role="alert">
						<strong><spring:message code="erro.no.formulario" /></strong>			
						<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
						<%-- <ul>
								<c:forEach var="error" items="${errors.allErrors}">
									<li>${error.field}: ${error.defaultMessage}</li>
								</c:forEach>
							</ul> --%>
					</div>
				</spring:hasBindErrors>
			
			
				<div class="panel panel-warning">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.hospedagem" />
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<spring:bind path="cliente">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="cliente" cssClass="col-sm-2 control-label">
										<spring:message code="cliente.label" />:
									</form:label>
									<div class="col-sm-8">
										<select id="cliente" name="cliente" class="col-sm-8 selectpicker form-control" data-size="5" data-live-search="true">
											<option data-icon="fa fa-search" value="0"><spring:message code="select.hospede"/></option>
											<c:forEach items="${clientes}" var="cliente">
												<option value="${cliente.id}" data-icon="fa fa-user" data-subtext="${cliente.cpf}"
													<c:if test="${cliente.id eq hospedagem.cliente.id}">selected="selected"</c:if> >${cliente.nome}</option>
											</c:forEach>
										</select>
										<form:errors path="cliente" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="apartamento">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="apartamento" cssClass="col-sm-2 control-label">
										<spring:message code="apartamento.label" />:
									</form:label>
									<div class="col-sm-5">
										<form:select path="apartamento" class="form-control selectpicker" data-size="5" data-live-search="true">
											<option data-icon="fa fa-search" value="0"><spring:message code="select.apartamento"/></option>
											<c:forEach items="${apartamentos}" var="apartamento">
												<option value="${apartamento.id}" class="${apartamento.estado=='Disponível' ? 'disponivel':'' || 
													apartamento.estado=='Ocupado' ? 'ocupado':'' || apartamento.estado=='Sujo' ? 'sujo':''}" 
													data-icon="glyphicon glyphicon-bed" data-subtext="${apartamento.estado}"
													<c:if test="${apartamento.id eq hospedagem.apartamento.id}">selected="selected"</c:if> >${apartamento.numero}
												</option>
											</c:forEach>
										</form:select>
										<form:errors path="apartamento" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="dataEntrada">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="dataEntrada" cssClass="col-sm-2 control-label">
										<spring:message code="dataEntrada.label" />:
									</form:label>
									<div class="col-sm-4">
										<form:input path="dataEntrada" cssClass="form-control"
											placeholder="dd/MM/aaaa" type="datetime-local" value="${dataEntrada}"/>
										<form:errors path="dataEntrada" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="valorDiaria">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="valorDiaria" cssClass="col-sm-2 control-label">
										<spring:message code="valorDiaria.label" />:
									</form:label>
									<div class="col-sm-4">
										<div class="input-group">
											<div class="input-group-addon">R$</div>
												<form:input path="valorDiaria" cssClass="form-control" type="number" step="0.01"/>
										     <div class="input-group-addon">,00</div>
										</div>
										<form:errors path="valorDiaria" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
				
				<form:hidden path="id" />
			
				<div class="form-actions botoes">
					<button type="submit" class="btn btn-success" data-toggle="tooltip" data-placement="bottom" 
						title="${salvarTooltip}">
						<span class="glyphicon glyphicon-ok"></span> <spring:message code="botao.salvar" />
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