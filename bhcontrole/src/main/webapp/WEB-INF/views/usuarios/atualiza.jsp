<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<spring:message code="salvar.tooltip" var="salvarTooltip"/>
<spring:message code="limpar.tooltip" var="limparTooltip"/>
<spring:message code="voltar.tooltip" var="voltarTooltip"/>

<div class="container">
	<div class="row">
		<div class="col-sm-8 col-sm-offset-2">
			<form:form action="${spring:mvcUrl('UC#atualiza').build()}" method="post"
				modelAttribute="usuario" data-toggle="validator" id="formUsuario">
			
				<c:if test="${!empty error}">
					<div class="alert alert-danger alert-dismissable fade in">
						<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					</div>
				</c:if>
			
				<spring:hasBindErrors name="usuario">
					<div class="alert alert-danger" role="alert">
						<strong><spring:message code="erro.no.formulario" /></strong>			
						<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
						 <ul>
								<c:forEach var="error" items="${errors.allErrors}">
									<li>${error.field}: ${error.defaultMessage}</li>
								</c:forEach>
							</ul>
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
											placeholder="${nomePlaceHolder}"/>
										<form:errors path="nome" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="login">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<form:label path="login" cssClass="col-sm-2 control-label">
										<spring:message code="login.label" />:
									</form:label>
									<div class="col-sm-8">
										<spring:message code="login.placeholder" var="loginPlaceHolder" />
										<form:input path="login" cssClass="form-control"
											placeholder="${loginPlaceHolder}"/>
										<form:errors path="login" cssClass="erro" />
									</div>
								</div>
							</spring:bind>
							<spring:bind path="senha">
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label for="senha" class="col-sm-2 control-label">
										<spring:message code="senha.label" />:
									</label>
									<div class="col-sm-8">
										<spring:message code="senha.placeholder" var="senhaPlaceHolder" />
										<spring:message code="senha.nao.combina" var="senhaNaoCombina" />
										<input type="password" id="senha" name="senha" class="form-control has-feedback"
											placeholder="${senhaPlaceHolder}"
											data-minlength=5 required/>
											<span class="glyphicon form-control-feedback"></span>
										<form:errors path="senha" cssClass="erro" />
										<div class="help-block with-errors"></div>
									</div>
								</div>
							</spring:bind>
							<spring:bind path="senhaConfirmacao">				
								<div class="form-group ${status.error ? 'has-error' : ''}">
									<label for="senhaConfirmacao" class="col-sm-2 control-label">
										<spring:message code="senhaConfirmacao.label" />:
									</label>
									<div class="col-sm-8">
										<spring:message code="senhaConfirmacao.placeholder" var="senhaConfirmacaoPlaceHolder" />
										<input type="password" id="senhaConfirmacao" name="senhaConfirmacao" class="form-control has-feedback"
											placeholder="${senhaConfirmacaoPlaceHolder}" data-minlength=5 data-match="#senha"
											data-match-error="${senhaNaoCombina}" required/>
										<span class="glyphicon form-control-feedback"></span>
										<form:errors path="senhaConfirmacao" cssClass="erro" />
										<div class="help-block with-errors"></div>								
									</div>
								</div>
							</spring:bind>
							<sec:authorize access="hasRole('ROLE_ADMIN')">
								<spring:bind path="funcoes">
									<div class="form-group ${status.error ? 'has-error' : ''}">
										<form:label path="funcoes" cssClass="col-sm-2 control-label">
											<spring:message code="funcoes.label" />:
										</form:label>				
										<div class="col-sm-4">
											<form:select path="funcoes" items="${roles}" multiple="true" itemValue="id" itemLabel="tipo" class="form-control"/>
											<form:errors path="funcoes" cssClass="erro" />						
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
							</sec:authorize>
							<c:forEach var="funcao" items="${usuario.funcoes}" varStatus="status">
       							<input type="hidden" id="funcoes" name="funcoes" value="${funcao.id}" />
    						</c:forEach>
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
	$('#formUsuario').validator({
		errors:{
				minlength: '<spring:message code="senha.error.tamanho"/>'
				
			}
	});
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
		})
</script>