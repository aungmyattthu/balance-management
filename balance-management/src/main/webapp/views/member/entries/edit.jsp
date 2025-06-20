<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<app:layout-member title="${type.name().toUpperCase()}">
	<div class="d-flex justify-content-between align-items-start">
		<app:page-title title="${form.id eq null ? 'Add New' : 'Edit' } Incomes" />

	</div>


	<sf:form id="editForm" method="post" action="${root}/member/entry/${urlType}/save" modelAttribute="form" class="row">
	    <sf:hidden path="id"/>
		<!-- Summary -->
		<div class="col-3">
			<!-- Ledger -->
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-flag"></i> Ledger Entry
					</h5>

					<app:form-group label="Ledger" cssClass="mb-3">
						<sf:select path="ledgerId" class="form-select">
							
							<option value="">Select Ledger</option>
							<c:forEach items="${ledgers}" var="item">
								<option value="${item.id() }" ${item.id() eq form.ledgerId ? 'selected' : '' }>${item.name() }</option>
							</c:forEach>
						</sf:select>
						<sf:errors path="ledgerId" cssClass="text-sm text-danger"></sf:errors>
					</app:form-group>
					<!-- Particular -->
					<app:form-group label="Particular" cssClass="mb-3">
						<sf:textarea path="particular" cols="40" rows="3" class="form-control"
							placeholder="Please Enter Particular"></sf:textarea>
						<sf:errors path="particular" cssClass="text-sm text-danger"></sf:errors>
					</app:form-group>
					<!-- Total -->
					<app:form-group label="Total Amount">
						<span id="allTotal" class="form-control">10,000</span>
					</app:form-group>
				</div>
			</div>
		</div>

		<!-- Entry Items -->
		<div class="col">
			<div class="col">
				<div class="card">
					<div class="card-body">
						<h5 class="card-title">
							<i class="bi-list"></i> Entry Items
						</h5>

						<div class="row">
							<div class="col">Item Name</div>
							<div class="col-3">Unit Price</div>
							<div class="col-3">Quantity</div>
							<div class="col-2 text-end">Amount</div>
						</div>

						<div id="entryItemsContainer">
							<c:forEach var="item" varStatus="sts" items="${form.items }">
								
								<div class="row mt-2 ${item.deleted ? 'd-none' : '' }">
									<sf:hidden path="items[${sts.index}].deleted"/>
									<div class="col">
										<div class="input-group">
											<button data-delete-input-id="items${sts.index}.deleted" 
												data-delete-url="${root}/member/entry/${urlType}/item/remove" 
												type="button" class="deleteBtn btn btn-outline-danger input-group-text">
												<i class="bi-trash"></i>
											</button>
											<sf:input path="items[${sts.index}].itemName" type="text" placeholder="Enter Item Name"
												class="form-control" />
											
										</div>
											<sf:errors path="items[${sts.index}].itemName" cssClass="text-danger" />
									</div>
	
									<div class="col-3">
										<sf:input path="items[${sts.index}].unitPrice" type="number" class="form-control changesInput" />
										<sf:errors path="items[${sts.index}].unitPrice" cssClass="text-danger" />
									</div>
									<div class="col-3">
										<sf:input path="items[${sts.index}].quantity" type="number" class="form-control changesInput" />
										<sf:errors path="items[${sts.index}].quantity" cssClass="text-danger" />
									</div>
									<div class="col-2">
										<span id="row${sts.index}Total" class="form-control text-end">0</span>
									</div>
								</div>
							</c:forEach>
						</div>

						<div class="mt-3">
							<button id="addItemBtn" data-add-url="${root}/member/entry/${urlType}/item/add" type="button" class="btn btn-outline-primary">
								<i class="bi-plus"></i> Add Item
							</button>

							<button type="submit" class="btn btn-outline-danger">
								<i class="bi-save"></i> Save Entry
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</sf:form>
	<script src="${root }/resources/js/ledger-entry-edit.js" type="text/javascript"></script>
</app:layout-member>
