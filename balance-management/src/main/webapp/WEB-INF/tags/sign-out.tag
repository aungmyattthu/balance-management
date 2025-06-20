<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<form id="signOutForm" action="${root}/signout" class="d-none"
	method="post">
	<sec:csrfInput />
</form>

<script type="text/javascript" src="${root}/resources/js/signout.js"></script>
