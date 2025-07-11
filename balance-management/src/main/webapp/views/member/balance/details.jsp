<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<app:layout-member title="BALANCE">
	<div class="d-flex justify-content-between align-items-start">
		<app:page-title title="Entry Details" />
		<c:url var="editUrl" value="${root}/member/entry/${details.type().name().toLowerCase()}/edit">
			<c:param name="id" value="${details.code()}"></c:param>
		</c:url>
		<c:if test="${editEnable}">
			<a href="${editUrl }" class="btn btn-danger"> <i class="bi-pencil"></i> Edit
				Entry
			</a>
		</c:if>
		
	</div>


	<div class="row">

		<!-- Entry Items -->
		<div class="col">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-list"></i> Entry Items
					</h5>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>No.</th>
								<th>Item Name</th>
								<th class="text-end">Price</th>
								<th class="text-end">Quantity</th>
								<th class="text-end">Total</th>
							</tr>
						</thead>

						<tbody>
						
						<c:forEach items="${details.items()}" var="item" varStatus="sts">
							<tr>
								<td>${sts.index + 1 }</td>
								<td>${item.itemName() }</td>
								<td class="text-end">${item.unitPrice() }</td>
								<td class="text-end">${item.quantity() }</td>
								<td class="text-end">${item.getTotal() }</td>

							</tr>
						</c:forEach>							
							<tr>
								<td colspan=4>All Total</td>
								<td class="text-end">${details.total}</td>

							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<!-- Header -->
		<div class="col-4">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-flag"></i> Income Summary
					</h5>

					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<div>Code</div>
							<div>${details.code()}</div>
						</div>
						<div class="list-group-item">
							<div>Ledger</div>
							<div>${details.ledgerName()}</div>
						</div>
						<div class="list-group-item">
							<div>Amount</div>
							<div>${details.amount()}</div>
						</div>
						<div class="list-group-item">
							<div>Issue At</div>
							<div>${dtf.formatDateTime(details.issueAt())}</div>
						</div>
						<div class="list-group-item">
							<div>Particular</div>
							<div>${details.particular()}</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</app:layout-member>