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
			<form:form action="${spring:mvcUrl('RC#salvar').build()}" method="post" commandName="reserva">
			
				<c:if test="${!empty error}">
					<div class="alert alert-danger alert-dismissable fade in">
						<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					</div>
				</c:if>
			
				<spring:hasBindErrors name="reserva">
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
			
			
				<div class="panel panel-primary">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.reserva" />
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<spring:bind path="nome">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="nome" cssClass="col-sm-2 control-label">
										<spring:message code="nome.label" />:
									</form:label>
									<div class="col-sm-8">
										<spring:message code="nome.placeholder" var="nomePlaceHolder" />
										<form:input path="nome" cssClass="form-control"
											placeholder="${nomePlaceHolder}"/>
										<form:errors path="nome" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="dataSolicitacao">
								<div class="form-group">
									<form:label path="dataSolicitacao" cssClass="col-sm-2 control-label">
										<spring:message code="dataSolicitacao.label" />:
									</form:label>
									<div class="col-sm-4">
										<form:input path="dataSolicitacao" cssClass="form-control"
											placeholder="dd/MM/aaaa" type="datetime-local" value="${dataSolicitacao}"/>
									</div>
									<form:errors path="dataSolicitacao" cssClass="erro" />
								</div>
							</spring:bind>
							<spring:bind path="dataEntrada">
								<div class="form-group ${status.error ? 'has-error':'' }">
									<form:label path="dataEntrada" cssClass="col-sm-2 control-label">
										<spring:message code="dataEntrada.label" />:
									</form:label>
									<div class="col-sm-4">
										<form:input path="dataEntrada" type="datetime-local" cssClass="form-control"
											placeholder="dd/MM/aaaa" />
										<form:errors path="dataEntrada" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<div class="form-group">
								<spring:bind path="quantidadeDeQuartos">
									<div class="${status.error ? 'has-error':'' }">
										<form:label path="quantidadeDeQuartos" cssClass="col-sm-2 control-label">
											<spring:message code="quantidadeDeQuartos.label" />:
										</form:label>
										<div class="col-sm-2">
											<spring:message code="quantidadeDeQuartos.placeholder" var="quantidadeDeQuartosPlaceHolder" />
											<input type="number" id="quantidadeDeQuartos" name="quantidadeDeQuartos" value="${reserva.quantidadeDeQuartos}" class="form-control"
												placeholder=" "/>
											<form:errors path="quantidadeDeQuartos" cssClass="erro" />
										</div>
									</div>
								</spring:bind>
								<spring:bind path="quantidadeDeHospedes">
									<div class="${status.error ? 'has-error':'' }">
										<form:label path="quantidadeDeHospedes" cssClass="col-sm-2 control-label">
											<spring:message code="quantidadeDeClientes.label" />:
										</form:label>
										<div class="col-sm-2">
											<spring:message code="quantidadeDeHospedes.placeholder" var="quantidadeDeHospedesPlaceHolder" />
											<input type="number" id="quantidadeDeHospedes" name="quantidadeDeHospedes" value="${reserva.quantidadeDeHospedes}" class="form-control"
												placeholder=" "/>
											<form:errors path="quantidadeDeHospedes" cssClass="erro" />
										</div>
									</div>
								</spring:bind>
							</div>
							
							<div class="form-group">
								<form:label path="telefones.telefoneFixo"
									cssClass="col-sm-2 control-label">
									<spring:message code="fixo.label" />:
								</form:label>
								<div class="col-sm-3">
									<form:input path="telefones.telefoneFixo"
										cssClass="form-control fixo" placeholder="(##) ####-####" />
								</div>
								<form:label path="telefones.telefoneCelular"
									cssClass="col-sm-2 control-label">
									<spring:message code="celular.label" />:
								</form:label>
								<div class="col-sm-4">
									<form:input path="telefones.telefoneCelular"
										cssClass="form-control celular" placeholder="(##) #####-####" />
								</div>
							</div>
							<div class="form-group ${status.error ? 'has-error' : '' }">
								<form:label path="email" cssClass="col-sm-2 control-label">
									<spring:message code="email.label" />:
								</form:label>
								<div class="col-sm-8">
									<spring:message code="email.placeholder" var="emailPlaceHolder" />
									<form:input path="email" cssClass="form-control"
										placeholder="${emailPlaceHolder}" />
									<form:errors path="email" cssClass="erro" />
								</div>
							</div>
							<div class="form-group">
								<form:label path="comentario" cssClass="col-sm-2 control-label">
									<spring:message code="comentario.label" />:
								</form:label>
								<div class="col-sm-8">
									<spring:message code="comentario.placeholder" var="comentarioPlaceHolder" />
									<form:textarea path="comentario" cssClass="form-control"
										placeholder="${comentarioPlaceHolder}" rows="4"></form:textarea>
									<form:errors path="comentario" cssClass="erro" />
								</div>
							</div>
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
			</form:form>
		</div>
	</div>
</div>
<script>
	jQuery(function($) {
		$(".fixo").mask("(99) 9999-9999");
		$(".celular").mask("(99) 99999-9999");

	});
	$("#comentario").charCount({
		allowed: 200,
		warning: 20,
	});
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
</script>