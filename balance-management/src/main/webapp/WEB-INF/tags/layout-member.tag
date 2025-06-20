<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags"%>
<html>
<head>
<meta charset="UTF-8">
<title>BALANCE | ${title.toUpperCase()}</title>
<c:set var="root" value="${pageContext.request.contextPath}"
	scope="request" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
<link rel="stylesheet" href="${root}/resources/css/common.css" />

</head>
<body>
	<nav
		class="navbar navbar-expand navbar-light bg-light shadow sticky-top">
		<div class="container">
			<a href="${root}/member/home" class="navbar-brand"> <i
				class="bi-house"></i> Balance Management
			</a>

			<ul class="navbar-nav">
				<li class="nav-item"><a href="${root}/member/balance"
					class="nav-link ${title eq 'BALANCE' ? 'active' : ''}"> <i
						class="bi-bar-chart"></i> Balances
				</a></li>
				<li class="nav-item"><a href="${root}/member/entry/incomes"
					class="nav-link ${title eq 'INCOMES' ? 'active' : ''}"> <i
						class="bi-flag"></i> Incomes
				</a></li>
				<li class="nav-item"><a href="${root}/member/entry/expenses"
					class="nav-link ${title eq 'EXPENSES' ? 'active' : ''}"> <i
						class="bi-cart"></i> Expenses
				</a></li>
				<li class="nav-item"><a href="${root}/member/ledger"
					class="nav-link ${title eq 'LEDGERS' ? 'active' : ''}"> <i
						class="bi-tags"></i> Ledgers
				</a></li>
				<li class="nav-item"><a href="#" class="nav-link"
					id="signOutMenu"> <i class="bi-lock me-1"></i>Signout
				</a></li>
			</ul>
		</div>
	</nav>
	<main class="container mt-4">
		<jsp:doBody />
	</main>
	<app:sign-out />

</body>
</html>