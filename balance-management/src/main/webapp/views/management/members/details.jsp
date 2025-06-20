<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<app:layout-management title="MEMBERS">

	<div class="d-flex justify-content-between align-items-start">
		<app:page-title title="Member Management"></app:page-title>
		<button id="statusChangeBtn" class="btn ${result.status() eq 'Active' ? 'btn-danger' : 'btn-primary'}">
			<i class="${result.status() eq 'Active' ? 'bi-x' : 'bi-check'}"></i> ${result.status() eq 'Active' ? 'Deny' : 'Activate' }
		</button>
	</div>

	<div class="row">

		<div class="col-3">
			<!-- Profile Information -->

			<img src="${root}/resources/photos/${result.profileImage()}"
				alt="Profile Photo" class="img-fluid img-thumbnail profile-image" />
		</div>
		<div class="col">

			<div class="row">
				<div class="col">
					<app:information-card label=" Registered At" icon="bi-person-plus"
						bgColor="text-white bg-info" value="${dtf.formatDateTime(result.registeredAt()) }"></app:information-card>
				</div>
				<div class="col">
					<app:information-card label=" Last Access" icon="bi-calendar-check"
						bgColor="text-white bg-secondary" value="${dtf.formatDateTime(result.lastAccessAt()) }"></app:information-card>

				</div>
				<div class="col">
					<app:information-card label="Status" icon="bi-shield"
						bgColor="text-white bg-primary" value="${result.status() }"></app:information-card>

				</div>
			</div>

			<div class="row mt-4 d-flex align-items-stretch">
				<div class="col">
					<app:personal-info
						 gender="${result.gender() }" 
						 name="${result.name() }" 
						 dob="${result.dateOfBirth()}"/>
				</div>
				<div class="col">
					<app:contact-info 
						phone="${result.phone() }"
						email="${result.email() }"
						address="${result.displayAddress }"
					 />
				</div>
			</div>
		</div>


	</div>

	<div id= "statusChangeDialog" class="modal">
		<div class="modal-dialog">
		
			<form action="${root}/admin/member/${result.id()}/update" method="post" class="modal-content">
				<sec:csrfInput/>
				<input type="hidden" name="status" value="${result.status() ne 'Active'}" />
				<div class="modal-header">
					<h5 class="modal-title">${result.status() eq 'Active' ? 'Deny' : 'Activate' } Status</h5>
				</div>
				<div class="modal-body">
					<app:form-group label="Reason">
						<textarea name="reason" class="form-control" required="required"></textarea>
					</app:form-group>
				</div>
				<div class="modal-footer">
					<button class="btn btn-outline-primary">
					
						<i class="bi-save"></i> Change Status
					</button>
				</div>
			</form>
		</div>
	</div>

	<script src="${root }/resources/js/member-details.js"></script>
</app:layout-management>
