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
			<form:form action="${spring:mvcUrl('CC#atualiza').build()}" modelAttribute="cliente" 
				method="post">
					
				<c:if test="${!empty error}">
					<div class="alert alert-danger alert-dismissable fade in">
						<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					</div>
				</c:if>
			
				<spring:hasBindErrors name="cliente">
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
						<spring:message code="dados.pessoais" />
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
											placeholder="${nomePlaceHolder}" />
										<form:errors path="nome" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="cpf">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="cpf" cssClass="col-sm-2 control-label">
										<spring:message code="cpf.label" />:
									</form:label>
									<div class="col-sm-4">
										<form:input path="cpf" cssClass="form-control"
											placeholder="###.###.###-##" />
										<form:errors path="cpf" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="empresa">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="empresa" cssClass="col-sm-2 control-label">
										<spring:message code="empresa.label" />:
									</form:label>
									<div class="col-sm-8">
										<spring:message code="empresa.placeholder" var="empresaPlaceHolder" />
										<form:input path="empresa" cssClass="form-control"
											placeholder="${empresaPlaceHolder}" />
										<form:errors path="empresa" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="cnpj">
								<div class="form-group">
									<form:label path="cnpj" cssClass="col-sm-2 control-label">
										<spring:message code="cnpj.label" />:
									</form:label>
									<div class="col-sm-4">
										<form:input path="cnpj" cssClass="form-control"
											placeholder="##.###.###/####-##" />
										<form:errors path="cnpj" cssClass="erro" />	
									</div>
								</div>
							</spring:bind>
						</div>
					</div>
				</div>
			
					<div class="panel panel-warning">
					<div class="panel-heading cabecalho-painel">
						<spring:message code="dados.contato" />
					</div>
					<div class="panel-body">
						<div class="form-horizontal">
							<div class="form-group">
								<form:label path="endereco.logradouro" cssClass="col-sm-2 control-label">
									<spring:message code="logradouro.label" />:
								</form:label>
								<div class="col-sm-8">
									<spring:message code="logradouro.placeholder" var="logradouroPlaceHolder" />
									<form:input path="endereco.logradouro" cssClass="form-control"
										placeholder="${logradouroPlaceHolder}" />
								</div>
							</div>
							<div class="form-group">
								<form:label path="endereco.numero"
									cssClass="col-sm-2 control-label">
									<spring:message code="numero.label" />:
								</form:label>
								<div class="col-sm-2">
									<input type="number" id="endereco.numero" name="endereco.numero" value="${cliente.endereco.numero}" class="form-control"
										placeholder="" />
								</div>
							</div>
							<div class="form-group">
								<form:label path="endereco.bairro"
									cssClass="col-sm-2 control-label">
									<spring:message code="bairro.label" />:
								</form:label>
								<div class="col-sm-4">
									<spring:message code="bairro.placeholder" var="bairroPlaceHolder" />
									<form:input path="endereco.bairro" cssClass="form-control"
										placeholder="${bairroPlaceHolder}" />
								</div>
								<form:label path="endereco.cep" cssClass="col-sm-1 control-label">
									<spring:message code="cep.label" />:
								</form:label>
								<div class="col-sm-3">
									<form:input path="endereco.cep" cssClass="form-control cep"
										placeholder="#####-###" />
								</div>
							</div>
							<div class="form-group">
								<form:label path="endereco.estado"
									cssClass="col-sm-2 control-label">
									<spring:message code="estado.label" />:
								</form:label>
								<div class="col-sm-4">
									<form:select path="endereco.estado" cssClass="form-control" value="${estado}"/>
								</div>
							</div>
							<div class="form-group">
								<form:label path="endereco.cidade"
									cssClass="col-sm-2 control-label">
									<spring:message code="cidade.label" />:
								</form:label>
								<div class="col-sm-4">
									<form:select path="endereco.cidade" cssClass="form-control" value="${cidade}"/>
								</div>
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
							<spring:bind path="email">
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
							</spring:bind>
						</div>
					</div>
				</div>
			
				<form:hidden path="id" />
				<div class="form-group botoes">
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
	window.onload = function() {
		new dgCidadesEstados({
			estado : document.getElementById('endereco.estado'),
			cidade : document.getElementById('endereco.cidade'),
		});
	}
	
	jQuery(function($) {
		$("#cpf").mask("999.999.999-99");
		$("#cnpj").mask("99.999.999/9999-99");
		$(".fixo").mask("(99) 9999-9999");
		$(".celular").mask("(99) 99999-9999");
		$(".cep").mask("99999-999");

	});
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
</script>