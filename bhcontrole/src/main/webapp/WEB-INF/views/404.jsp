<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true" %>

<div class="top-content">
	<div class="inner-bg">
		<div class="container">
			 <div class="row">
	             <div class="col-sm-8 col-sm-offset-2 text">
	                 <img src="<c:url value="/resources/imgs/brand.png" />" class="img-logo img-responsive" alt="Logo">
	                 <spring:message code="app.nome"/>
	             </div>
	         </div>
			<div class="row">
				<div class="col-sm-6 col-sm-offset-3 form-box">
					<div class="form-top">
						<div class="form-top-left">	
							<h3> ERROR - 404 </h3>	
							<p><spring:message code="404.mensagem"/></p>
						</div>
						<div class="form-top-right">
							<i class="fa fa-exclamation-triangle"></i>
						</div>
					</div>
					<div class="form-bottom">
						<div class="erro-opcoes text-center">
							<a href="<c:url value="/home" />"><spring:message code="404.opcao1"/> </a> 
							<spring:message code="404.opcao2"/> <a href="<c:url value="/logout" />">Logout</a>
						</div>
					</div>
				</div> 
			</div>
		</div>
	</div>
</div>