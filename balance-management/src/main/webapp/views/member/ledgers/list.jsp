<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<app:layout-member title="LEDGERS">
	<app:page-title title="Ledger Management" />

	<!-- Search -->
	<form action=""  class="row" id="searchForm">
		<input type="hidden" name="page" id="pageInput"/>
		<input type="hidden" name="size" id="sizeInput"/>
		<app:form-group label="Type" cssClass="col-auto">
			<select name="type" id="" class="form-select">
				<option value="">Search All</option>
				<c:forEach items="${balanceTypes}" var="item">
					<option value="${item}" ${param.type eq item ? 'selected' : '' }>${item}</option>
				</c:forEach>
			</select>

		</app:form-group>
		<app:form-group label="Keyword" cssClass="col-auto">
			<input type="text" value="${param.keyword }" name="keyword" placeholder="Search Keyword" class="form-control" />
		</app:form-group>
		<div class="col btn-wrapper">
			<button class="btn btn-primary">
				<i class="bi-search"></i> Search
			</button>
			<button type="button" id="addNewBtn" class="btn btn-danger">
				<i class="bi-plus"></i> New Ledger
			</button>
		</div>
	</form>
	<table class="table table-striped table-bordered table-hover mt-3">
		<thead>
			<tr>
				<th>ID</th>
				<th>Type</th>
				<th>Name</th>				
				<th>Status</th>
				<th>Created At</th>
				<th>Modified At</th>
				<th class="text-end">Total</th>
				<th></th>
			</tr>

		</thead>
		<tbody>
			<c:forEach var="item" items="${result.contents()}" varStatus="sts">
				<tr>
					<td>${sts.index + 1}</td>
					<td>${item.type()}</td>
					<td>${item.name()}</td>
					<td>${item.status() eq true ? 'Deleted' : 'Active'}</td>
					<td>${dtf.formatDateTime(item.createdAt())}</td>
					<td>${dtf.formatDateTime(item.modifiedAt())}</td>
					<td class="text-end">${item.total()}</td>
					<td class="text-center"><a href="#" data-edit-id="${item.id()}" 
										data-edit-type="${item.type()}"
										data-edit-name="${item.name()}"
										data-edit-status="${item.status()}"
										class="edit-link icon-link">
							 <i class="bi-pencil"></i>
					</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<app:pagination pageResult="${result}"/>
	<div class="modal fade" id="editDialog">
		<div class="modal-dialog">
			<sf:form modelAttribute="form" method="POST" id="editForm" class="modal-content">
				<sf:hidden path="id"/>
				<div class="modal-header">
					<h5 class="modal-title">
						<i class="bi-pencil"></i>
						 <span id="editFormTitle">Add New Category</span>
					</h5>

				</div>
				<div class="modal-body">
					<app:form-group label="Type" cssClass="mb-3">
						<sf:select path="type" class="form-select" required="required">
							<option value="">Select One</option>
							<c:forEach var="item" items="${balanceTypes}">
								<option value="${item}">${item}</option>
							</c:forEach>
						</sf:select>

					</app:form-group>

					<app:form-group cssClass="mb-3" label="Ledger Name">
						<sf:input path="name" type="text" required="required" class="form-control"
							placeholder="Enter Ledger Name" />
					</app:form-group>

					<div class="form-check">
						<sf:checkbox path="status" class="form-check-input" id="status" />
						 <label id="statusLabel"
							 class="form-check-label">Active</label>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary">
						<i class="bi-save"></i> Save Category
					</button>
				</div>
			</sf:form>
		</div>
	</div>
	
	<script src="${root}/resources/js/ledger-management.js"></script>
</app:layout-member>
