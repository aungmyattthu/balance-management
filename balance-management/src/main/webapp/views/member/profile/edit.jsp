<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<app:layout-member title="PROFILE">
	<app:page-title title="Edit Profile"></app:page-title>


	<div class="row">
		<!-- Profile Photo -->
		<div class="col-3">
			<img src="${root}/resources/photos/${form.profileImage}"
				alt="Profile Photo" class="img-fluid img-thumbnail profile-image" />
			<form id="profilePhotoForm" action="${root}/member/profile/photo"
				method="post" enctype="multipart/form-data" class="mt-3">
				<sec:csrfInput/>
				<input id="profilePhotoInput" type="file" name="file" class="d-none" />
				<button id="profilePhotoBtn" type="button"
					class="btn btn-primary w-100">
					<i class="bi-camera"></i> Change Profile Photo
				</button>
			</form>
		</div>
		<div class="col">
			<sf:form method="post" action="${root}/member/profile" modelAttribute="form">
				
				<!-- Personal Info Inputs -->
				<!-- Name -->
				<div class="row">
					<app:form-group label="Name" cssClass="mb-3 col-8">
						<sf:input path="name" type="text" placeholder="Enter Name" class="form-control" />
						<sf:errors path="name" cssClass="text-secondary"></sf:errors>
					</app:form-group>
				</div>
	
	
				<div class="row mb-3">
	
					<!-- Gender -->
					<app:form-group label="Gender" cssClass="col-4">
						<sf:select path="gender" id="" class="form-select">
							<option value="">Select One</option>
							<option value="Male" ${form.gender eq 'Male' ? 'selected' : '' }>Male</option>
							<option value="Female" ${form.gender eq 'Female' ? 'selected' : '' }>Female</option>
						</sf:select>
						<sf:errors path="gender" cssClass="text-secondary"></sf:errors>
					</app:form-group>
					<!-- Date of Birth -->
	
					<app:form-group label="Date of Birth" cssClass="col-4">
						<sf:input path="dob" type="date" class="form-control" />
						<sf:errors path="dob" cssClass="text-secondary"></sf:errors>
					</app:form-group>
	
				</div>
	
	
				<!-- Contact Info Inputs -->
				<div class="row mb-3">
					<!-- Phone -->
					<app:form-group label="Phone" cssClass="col-4">
						<sf:input path="phone" type="tel" placeholder="Enter Phone Number"
							class="form-control" />
						<sf:errors path="Phone" cssClass="text-secondary"></sf:errors>
					</app:form-group>
					<!-- Email -->
					<app:form-group label="Email" cssClass="col">
						<sf:input path="email" type="email" readonly="true" placeholder="Enter Email Address"
							class="form-control" />
					</app:form-group>
				</div>
				<div class="row mb-3">
					<!-- Regions -->
					<app:form-group label="Region" cssClass="col-4">
						<select data-fetch-api="${root}/member/location/district" id="region" class="form-select">
							<option value="">Select One</option>
							<c:forEach items="${regions }" var="item">
								<option value="${item.id}" ${item.id eq form.region ? 'selected' : ''}>
										${item.name}
								</option>
							</c:forEach>
							
	
						</select>
					</app:form-group>
					<!-- District -->
					<app:form-group label="District" cssClass="col-4">
						<select data-fetch-api="${root}/member/location/township" id="district" class="form-select">
							<option value="Male">Select One</option>
							<c:forEach items="${districts }" var="item">
								<option value="${item.id }" ${item.id eq form.district ? 'selected' : ''}>
										${item.name}
								</option>
							</c:forEach>
						</select>
					</app:form-group>
					<!-- Townships -->
					<app:form-group label="Townships" cssClass="col-4">
						<sf:select path="township" name="" id="township" class="form-select">
							<option value="">Select One</option>
							<c:forEach items="${townships }" var="item">
								<option value="${item.id }" ${item.id eq form.township ? 'selected' : ''}>
										${item.name}
								</option>
							</c:forEach>
						</sf:select>
						<sf:errors path="township" cssClass="text-secondary"></sf:errors>
					</app:form-group>
	
				</div>
				<!-- Address -->
				<div class="mb-3">
					<app:form-group label="Address">
						<sf:textarea path="address" name="" id="" cols="60" rows="3" class="form-control"
							placeholder="Enter address"></sf:textarea>
						<sf:errors path="Address" cssClass="text-secondary"></sf:errors>
					</app:form-group>
				</div>
				<button class="btn btn-danger">
					<i class="bi-save"></i> Save Profile
				</button>
			</sf:form>
		</div>
		
	</div>
	<script src="${root}/resources/js/profile-edit.js"></script>
	<script src="${root}/resources/js/member-location.js"></script>
</app:layout-member>