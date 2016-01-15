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
		            <c:if test="${!empty success}">
						<div class="alert alert-success alert-dismissable fade in">
							<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
							${success}, <a href="${spring:mvcUrl('LC#loginPage').build() }">Log in</a>
						</div>
					</c:if>
					<c:if test="${!empty error}">
						<div class="alert alert-danger alert-dismissable fade in">
							<button type="button" class="close" aria-label="Close" data-dismiss="alert"><span aria-hidden="true">&times;</span></button>
							${error}
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
							<h3><spring:message code="resetarSenha.title"/> </h3>
							<p><spring:message code="resetarSenha.texto"/></p>
						</div>
						<div class="form-top-right">
							<i class="fa fa-envelope"></i>
						</div>
					</div>
					<div class="form-bottom">
						<form:form action="${spring:mvcUrl('LC#resetar').build()}" method="post" commandName="userInfo">
							<div class="form-group">
								<div class="input-group">
							        <label class="input-group-addon" for="login"><i class="glyphicon glyphicon-user"></i></label>
							        <spring:message code="login.placeholder" var="loginPlaceHolder" />
							        <input type="text" class="form-control" id="login" name="login" placeholder="${loginPlaceHolder}" required/>
							    </div>
							</div>
							<div class="form-group">			
								<div class="input-group">
							        <label class="input-group-addon" for="email"><i class="glyphicon glyphicon-envelope"></i></label>
							        <spring:message code="emailRecuperacao.placeholder" var="emailPlaceHolder" /> 
							        <input type="email" class="form-control" id="email" name="email" placeholder="${emailPlaceHolder}" required>
							    </div>
							</div>
							<div class="form-group login-botao text-center">
						       <input type="submit" class="btn btn-block btn-primary btn-default " value="Enviar"/>
						  	</div>
						</form:form>
					</div>
				</div>
			</div>
		</div>  	
	</div>
</div>