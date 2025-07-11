<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="false"%>
<%@ attribute name="value" required="false"%>
<%@ attribute name="icon" required="false"%>
<%@ attribute name="color" required="false"%>
<div class="card ${color}">
	<div class="card-body">
		<div class="text-end">
			<span class="">${title}</span>
		</div>
		<h4 class="d-flex justify-content-between">
			<i class="${icon}"></i> <span id="${title.toLowerCase() }">${value}</span>
		</h4>
	</div>
</div>