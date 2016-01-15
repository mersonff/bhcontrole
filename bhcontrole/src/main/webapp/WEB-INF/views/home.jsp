<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="container">
	<div class="row">
		<div>
			<c:if test="${!empty error}">
				<div class="alert alert-danger alert-dismissable fade in">
					<button type="button" class="close" aria-label="Close"
						data-dismiss="alert">
						<span aria-hidden="true">&times;</span>
					</button>
					${error}
				</div>
			</c:if>
			<c:if test="${!empty success}">
				<div class="alert alert-success alert-dismissable fade in">
					<button type="button" class="close" aria-label="Close"
						data-dismiss="alert">
						<span aria-hidden="true">&times;</span>
					</button>
					${success}
				</div>
			</c:if>
		</div>
		<div class="col-lg-7 col-md-7 col-sm-7 col-xs-12">

			<div class="home-apartamentos">
				<div class="row">
					<h3 class="text-center">Apartamentos</h3>
					<div class="legenda-estados">
						<p>
							<b class="bg-success"><spring:message
									code="legenda.disponivel" /></b> <b class="bg-warning"><spring:message
									code="legenda.sujo" /></b> <b class="bg-danger"><spring:message
									code="legenda.ocupado" /></b>
						</p>
					</div>
				</div>
				<div class="row">
					<c:forEach items="${apartamentos}" var="apartamento">
						<div class="col-lg-2 com-md-3 col-sm-2 col-xs-4">
							<div class="home-apartamento-hover">
								<div class="thumbnail text-center">
									<a
										href="${spring:mvcUrl('IC#abreHospedagem').arg(0,apartamento.id).build()}">
										<i
										class="glyphicon glyphicon-bed gi-4x ${apartamento.estado=='Disponível' ? 'text-success':'' ||
									apartamento.estado=='Ocupado' ? 'text-danger':'' || 
									apartamento.estado=='Sujo' ? 'text-warning':''}">
									</i>

										<p
											class="home-apartamento-numero text-center ${apartamento.estado=='Disponível' ? 'text-success':'' ||
									apartamento.estado=='Ocupado' ? 'text-danger':'' || 
									apartamento.estado=='Sujo' ? 'text-warning':''}">${apartamento.numero}
											<span>- ${apartamento.estado}</span>
										</p>
									</a>
								</div>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
		<div class="col-lg-5 col-md-5 col-sm-5 col-xs-12">
			<div class="row">
				<h3 class="text-center home-reservas">Reservas em aberto</h3>
			</div>
			<div class="table-responsive table-home">
				<table class="table table-hover table-striped table-bordered">
					<thead>
						<tr>
							<th><spring:message code="informacoes.label" /></th>
							<th><spring:message code="acoes.label" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${reservas}" var="reserva">
							<tr>
								<td><i class="fa fa-user fa-1x fa-border"></i> <b>${reserva.nome}</b>
									<br> <i class="fa fa-phone fa-1x fa-border"></i><b>
									${reserva.telefones.telefoneCelular}</b> <br>
									<i class="fa fa-envelope fa-1x fa-border"></i><b>
									${reserva.email}</b><br> <i
										class="fa fa-calendar fa-1x fa-border"></i> <b><fmt:formatDate
										value="${reserva.dataEntrada.time}"
										pattern="dd/MM/yyyy HH:mm:ss" /></b></td>
								<td>
									<ul class="icones-home-reservas">
										<li><a title="${detalhesReservaAlt}"
											href="${spring:mvcUrl('RC#detalhes').arg(0,reserva.id).build()}"
											class="btn btn-info btn-sm"><span
												class="glyphicon glyphicon-eye-open"></span> </a></li>
										<li><a title="${atualizarReservaAlt}"
											href="${spring:mvcUrl('RC#atualizar').arg(0,reserva.id).build()}"
											type="button" class="btn btn-warning btn-sm"><span
												class="glyphicon glyphicon-edit"></span> </a></li>
										<li><a
											href="${spring:mvcUrl('RC#deleta').arg(0,reserva.id).build()}"
											class="btn btn-danger btn-sm popconfirm"><span
												class="glyphicon glyphicon-trash"></span> </a></li>
									</ul>

								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script>
	$(".popconfirm")
			.popConfirm(
					{
						yesBtn : '<spring:message code="popconfirm.btn.ok"/>',
						noBtn : '<spring:message code="popconfirm.btn.cancelar"/>',
						content : '<p class="text-center"><spring:message code="popconfirm.btn.conteudo"/></p>',
						placement : 'left',
						title : '<p class="text-center"><spring:message code="popconfirm.btn.titulo"/></p>'
					});

	$(document)
			.ready(
					function() {
						$('#tabelas')
								.DataTable(
										{
											language : {
												processing : '<spring:message code="datatable.processing"/>',
												search : '<span class="glyphicon glyphicon-search"></span> <spring:message code="datatable.search"/>',
												lengthMenu : '<p class="datatable-lenght-menu"><spring:message code="datatable.lengthMenu"/></p>',
												info : '<spring:message code="datatable.info"/>',
												infoEmpty : '<spring:message code="datatable.infoEmpty"/>',
												infoFiltered : '<spring:message code="datatable.infoFiltered"/>',
												infoPostFix : "",
												loadingRecords : '<spring:message code="datatable.loadingRecords"/>',
												zeroRecords : '<spring:message code="datatable.zeroRecords"/>',
												emptyTable : '<spring:message code="datatable.emptyTable"/>',
												paginate : {
													first : '<spring:message code="datatable.paginate.first"/>',
													last : '<spring:message code="datatable.paginate.last"/>',
													next : '<spring:message code="datatable.paginate.next"/>',
													previous : '<spring:message code="datatable.paginate.previous"/>',
												},
												aria : {
													sortAscending : '<spring:message code="datatable.aria.sortAscending"/>',
													sortDescending : '<spring:message code="datatable.aria.sortDescending"/>'
												}
											}

										});
					});
</script>