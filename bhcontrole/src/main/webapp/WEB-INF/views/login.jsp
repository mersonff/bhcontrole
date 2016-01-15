<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:message code="login.placeholder" var="loginPlaceHolder"/>
<spring:message code="senha.placeholder" var="senhaPlaceHolder"/>

<div class="top-content">
	<div class="inner-bg">
		<div class="container">
			 <div class="row">
	             <div class="col-sm-8 col-sm-offset-2 text">
		            <c:if test="${param.error != null}">
					    <div class="alert alert-danger">
					   		<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					        <p class="text-center" ><spring:message code="login.erro"/> </p>
					    </div>
					</c:if>
					<c:if test="${param.logout != null}">
					    <div class="alert alert-success">
					   		<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					        <p class="text-center" ><spring:message code="logout.sucesso"/> </p>
					    </div>
					</c:if>
					<c:if test="${param.expired != null}">
					    <div class="alert alert-danger">
					   		<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					        <p class="text-center" ><spring:message code="login.expirado"/> </p>
					    </div>
					</c:if>
					<c:if test="${param.update != null}">
					    <div class="alert alert-success">
					   		<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
					        <p class="text-center" ><spring:message code="update.sucesso"/> </p>
					    </div>
					</c:if>
	                 <img src="<c:url value="/resources/imgs/brand.png" />" class="img-logo img-responsive" alt="Logo">
	                 <spring:message code="app.nome"/>
	             </div>
	         </div>
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3 form-box">
					<div class="form-top">
						<div class="form-top-left">	
							<h3><spring:message code="login.title"/> </h3>	
							<p><spring:message code="login.texto"/></p>
						</div>
						<div class="form-top-right">
							<i class="fa fa-lock"></i>
						</div>
					</div>
					<div class="form-bottom">
						<form:form servletRelativeAction="/login">
							<div class="form-group">
								<div class="input-group">
							        <label class="input-group-addon" for="login"><i class="glyphicon glyphicon-user"></i></label>
							        <spring:message code="login.placeholder" var="loginPlaceHolder" />
							        <input type="text" class="form-control" id="login" name="login" placeholder="${loginPlaceHolder}" required>
							    </div>
							</div>
							<div class="form-group">
								<div class="input-group">
							        <label class="input-group-addon" for="password"><i class="glyphicon glyphicon-lock"></i></label>
							        <spring:message code="senha.placeholder" var="senhaPlaceHolder" /> 
							        <input type="password" class="form-control" id="senha" name="senha" placeholder="${senhaPlaceHolder}" required>
							    </div>
							</div>
							<div class="form-actions login-botao text-center">
						       <input type="submit" class="btn btn-block btn-primary btn-default " value="Log in">
						       <spring:message code="esqueci.senha.texto"/><a href="${spring:mvcUrl('LC#resetarSenha').build()}"><spring:message code="esqueci.senha.link"/>. </a>
						  	</div>
						</form:form> 
					</div>
				</div> 
			</div>
		</div>
	</div>
</div>