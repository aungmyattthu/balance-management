<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<app:layout-member title="HOME">

	<div class="d-flex justify-content-between align-items-start">

		<app:page-title title="Member Home" />

		<div class="btn-group">
			<c:url var="summaryMonthly" value="${root }/member/chart/summary">
				<c:param name="type" value="Monthly"></c:param>
			</c:url>
			<c:url var="balanceMonthly" value="${root }/member/chart/balance">
				<c:param name="type" value="Monthly"></c:param>
			</c:url>
			<c:url var="chartMonthly" value="${root }/member/chart/ledger">
				<c:param name="type" value="Monthly"></c:param>
			</c:url>
			<input type="radio"  data-summary-url="${summaryMonthly }"
				data-balance-url="${balanceMonthly }"
				data-ledger-url="${chartMonthly }"
				class="btn-check" id="monthly" checked="checked"
				name="display" />
			 <label for="monthly" class="btn btn-outline-primary">Monthly</label> 
			
			<c:url var="summaryYearly"  value="${root }/member/chart/summary">
				<c:param name="type" value="Yearly"></c:param>
			</c:url>
			<c:url var="balanceYearly"  value="${root }/member/chart/balance">
				<c:param name="type" value="Yearly"></c:param>
			</c:url>
			<c:url var="chartYearly" value="${root }/member/chart/ledger">
				<c:param name="type" value="Yearly"></c:param>
			</c:url>
			<input type="radio" data-summary-url="${summaryYearly }"
				data-balance-url="${balanceYearly }"
				data-ledger-url="${chartYearly }"
				class="btn-check" id="yearly" name="display" /> 
			<label for="yearly"	class="btn btn-outline-primary">Yearly</label>
		</div>
	</div>
	<div class="row">
		<div class="col-3">
			<!-- Profile -->
			<div class="card">
				<div class="card-body">
					<div class="d-flex justify-content-between align-items-start">
						<h5 class="card-title">
							<i class="bi-person"></i> Profile
						</h5>
						<a href="${root}/member/profile" class="btn-link"> <i
							class="bi-pencil"></i>
						</a>
					</div>
				</div>
				<img class="profile-image"
					src="${root}/resources/photos/${profile.profileImage() }"
					alt="Profile Image" />

				<div class="card-body">
					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<i class="bi-person"></i> ${profile.name()}
						</div>
						<div class="list-group-item">
							<i class="bi-telephone"></i> ${profile.phone() ne null ? profile.phone():'Data Not Provided'}
						</div>
						<div class="list-group-item">
							<i class="bi-envelope"></i> ${profile.email()}
						</div>
						<div class="list-group-item">
							<i class="bi-map"></i> ${profile.address() ne '' && profile.address() ne null ? profile.address():'Data Not Provided'}
						</div>
					</div>
				</div>
			</div>
			<!-- Access History -->
			<div class="card mt-4">
				<div class="card-body">
					<div class="d-flex justify-content-between align-items-start">
						<h5 class="card-title">
							<i class="bi-shield"></i> Access
						</h5>
						<a href="${root}/member/access" class="btn-link"> <i
							class="bi-send"></i>
						</a>
					</div>
					<div class="list-group list-group-flush">
						<div class="list-group-item">
							<div class="fw-bold">Registered At</div>
							<span> ${dtf.formatDateTime(profile.registeredAt())} </span>
						</div>
						<div class="list-group-item">
							<div class="fw-bold">Last Access</div>
							<span> ${dtf.formatDateTime(profile.lastAccessAt())} </span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col">
			<!-- Summary -->
			<div class="row">
				<div class="col">

					<app:summary-info color="text-white bg-warning" title="Expenses"
						value="100,000" icon="bi-cart"></app:summary-info>
				</div>
				<div class="col">
					<app:summary-info color="text-white bg-danger" title="Incomes"
						value="500,000" icon="bi-flag"></app:summary-info>

				</div>
				<div class="col">
					<app:summary-info color="text-white bg-primary" title="Balances"
						value="400,000" icon="bi-bar-chart"></app:summary-info>

				</div>
			</div>
			<!-- Charts -->
			<div class="card mt-4">
				<div class="card-body">
					<h5 class="card-title">
						<i class="bi-bar-chart"></i> Balances
					</h5>

					<div id="balanceChart"></div>
				</div>
			</div>
			<div class="row mt-4">
				<!-- Expenses Pie chart -->
				<div class="col">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">
								<i class="bi-cart"></i> Expenses
							</h5>

							<div id="expensesChart" class="pieChartRoot"></div>
						</div>
					</div>
				</div>
				<!-- Income Pie chart -->
				<div class="col">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">
								<i class="bi-flag"></i> Incomes
							</h5>

							<div id="incomesChart" class="pieChartRoot"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Resources -->
	<script src="https://cdn.amcharts.com/lib/5/index.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/xy.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/percent.js"></script>
	<script src="https://cdn.amcharts.com/lib/5/themes/Animated.js"></script>
	<script src="${root}/resources/js/member-home.js"></script>

</app:layout-member>
