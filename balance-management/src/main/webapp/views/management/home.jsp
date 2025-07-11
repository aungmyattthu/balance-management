<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"  %>

<app:layout-management title="HOME">
	<div class="d-flex justify-content-between align-items-start">

		<app:page-title title="Admin Home" />

		<div class="btn-group">
			<c:url var="monthlyApi" value="${root}/admin/home/load">
				<c:param name="type" value="Monthly" />
			</c:url>			
			
			<input data-rest-api="${monthlyApi}" 
				type="radio" class="btn-check" id="monthly" checked="checked"
				name="display" /> <label for="monthly"
				class="btn btn-outline-secondary">Monthly</label>
				
			<c:url var="yearlyApi" value="${root}/admin/home/load">
				<c:param name="type" value="Yearly" />
			</c:url>
				
			<input data-rest-api="${yearlyApi}"
				type="radio" class="btn-check" id="yearly" name="display" /> <label
				for="yearly" class="btn btn-outline-secondary">Yearly</label>
		</div>
	</div>

	<div class="row">
		<div class="col-3">
			<!-- New Member for this month -->
			<div class="card bg-light mb-4">

				<div class="card-header">
					<h5 class="card-title">Last Month</h5>
				</div>
				<div class="card-body">
					<h4>
						<i class="bi-people"></i> ${vo.lastMonth() }
					</h4>
				</div>
			</div>
			<!-- New Member for this year -->
			<div class="card text-white bg-secondary mb-4">

				<div class="card-header">
					<h5 class="card-title">Last Year</h5>
				</div>
				<div class="card-body">
					<h4>
						<i class="bi-people"></i> ${vo.lastYear() }
					</h4>
				</div>
			</div>
			<!-- Total Members -->
			<div class="card text-white bg-dark mb-4">

				<div class="card-header">
					<h5 class="card-title">Total Members</h5>
				</div>
				<div class="card-body">
					<h4>
						<i class="bi-people"></i> ${vo.totalMembers() }
					</h4>
				</div>
			</div>
		</div>
		<div class="col">
			<div class="card">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-people"></i> Member Access
					</h5>

					<div id="adminChart"></div>
				</div>
			</div>
		</div>
	</div>
	<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/xy.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
	<script src="${root}/resources/js/management-home.js"></script>
</app:layout-management>
