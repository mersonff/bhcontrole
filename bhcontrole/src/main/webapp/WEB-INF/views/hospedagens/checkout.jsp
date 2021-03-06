<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<spring:message code="salvar.tooltip" var="salvarTooltip"/>
<spring:message code="limpar.tooltip" var="limparTooltip"/>
<spring:message code="voltar.tooltip" var="voltarTooltip"/>

<section id="formApartamento">
	<div class="container">
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2">
				<form:form action="${spring:mvcUrl('HC#doCheckout').build()}" method="post"
					commandName="hospedagem">
				
					<c:if test="${!empty error}">
						<div class="alert alert-danger alert-dismissable fade in">
							<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
						</div>
					</c:if>
				
					<spring:hasBindErrors name="hospedagem">
						<div class="alert alert-danger" role="alert">
							<strong><spring:message code="erro.no.formulario" /></strong>			
							<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
							   <!--<ul>
									<c:forEach var="error" items="${errors.allErrors}">
										<li>${error.field}: ${error.defaultMessage}</li>
									</c:forEach>
								</ul>-->
						</div>
					</spring:hasBindErrors>
				
				
					<div class="panel panel-primary">
						<div class="panel-heading cabecalho-painel">
							<spring:message code="dados.checkout" />
						</div>
						<div class="panel-body">
							<div class="form-horizontal">
								<spring:bind path="dataSaida">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<form:label path="dataSaida" cssClass="col-sm-2 control-label">
											<spring:message code="dataSaida.label" />:
										</form:label>
										<div class="col-sm-4">
											<form:input path="dataSaida" cssClass="form-control"
												placeholder="dd/MM/aaaa" type="datetime-local" value="${dataSaida}"/>
											<form:errors path="dataSaida" cssClass="erro" />
										</div>
									</div>
								</spring:bind>
							</div>
						</div>
					</div>
				
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
					
					<form:hidden path="id" />
					<form:hidden path="dataEntrada" />
					<form:hidden path="cliente.id" />
					<form:hidden path="apartamento.id" />
					<form:hidden path="valorDiaria" />
					
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