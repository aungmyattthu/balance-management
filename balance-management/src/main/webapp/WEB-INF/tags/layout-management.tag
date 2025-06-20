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
	<!-- Navigation -->
	<nav class="navbar navbar-expand navbar-dark bg-dark sticky-top">
		<div class="container">
			<a href="${root}/admin/home" class="navbar-brand"> <i
				class="bi-house me-1"></i>Balance Admin
			</a>

			<ul class="navbar-nav">
				<li class="nav-item"><a href="${root}/admin/access"
					class="nav-link ${title eq 'ACCESS' ? 'active' : ''}"> <i
						class="bi-calendar me-1"></i>Access History
				</a></li>
				<li class="nav-item"><a href="${root}/admin/member"
					class="nav-link ${title eq 'MEMBERS' ? 'active' : ''}"> <i
						class="bi-people me-1"></i>Members
				</a></li>
				<li class="nav-item"><a href="#" id="signOutMenu"
					class="nav-link"> <i class="bi-lock me-1"></i>Signout
				</a></li>
			</ul>
		</div>
	</nav>
	<main class="container my-4">
		<jsp:doBody />
	</main>
	<app:sign-out />
</body>
</html>