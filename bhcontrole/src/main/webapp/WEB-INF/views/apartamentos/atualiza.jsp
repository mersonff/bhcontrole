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
				<form:form action="${spring:mvcUrl('AC#atualiza').build()}" method="post"
					commandName="apartamento" data-toggle="validator">
				
					<c:if test="${!empty error}">
						<div class="alert alert-danger alert-dismissable fade in">
							<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
						</div>
					</c:if>
				
					<spring:hasBindErrors name="usuario">
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
				
					<div class="row">
						<div class="col-sm-12">
							<div class="panel panel-warning">
								<div class="panel-heading cabecalho-painel">
									<spring:message code="dados.apartamento" />
								</div>
								<div class="panel-body">
									<div class="form-horizontal">
										<spring:bind path="numero">
											<div class="form-group ${status.error ? 'has-error' : ''}">
												<form:label path="numero" cssClass="col-sm-2 control-label">
													<spring:message code="numero.label" />:
												</form:label>
												<div class="col-sm-2">
													<spring:message code="numero.placeholder" var="numeroPlaceHolder" />
													<input type="number" id="numero" name="numero" value="${apartamento.numero}" class="form-control"
														placeholder=" "/>
												</div>
												<div class="col-sm-10 col-sm-offset-2">
													<form:errors path="numero" cssClass="erro" />
												</div>
											</div>
										</spring:bind>
										<spring:bind path="estado">
											<div class="form-group ${status.error ? 'has-error' : ''}">
												<form:label path="estado" cssClass="col-sm-2 control-label">
													<spring:message code="estado.label" />:
												</form:label>
												<div class="col-sm-3">
													<form:select path="estado" cssClass="form-control">
													    <form:options items="${estados}" itemValue="estado" itemLabel="estado"/>
													</form:select>
												</div>
											</div>
										</spring:bind>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<form:hidden path="id" />
					
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
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
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</section>
<script>
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
</script>