<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="name" required="true"%>
<%@ attribute name="dob" type="java.time.LocalDate"%>
<%@ attribute name="gender" type="com.amt.online.balance_management.model.entity.consts.Gender"%>

<div class="card  text-bg-light h-100">
	<div class="card-body">
		<h5>
			<i class="bi-person"></i> Personal Info
		</h5>
		<div class="mb-3">
			<span class="text-secondary">Name</span>
			<div>${name }</div>
		</div>
		<div class="mb-3">
			<span class="text-secondary">Date of Birth</span>
			<div>${dob ne null ? dtf.formatDate(dob) : 'Undefined ' }</div>
		</div>
		<div>
			<span class="text-secondary">Gender</span>
			<div>${gender ne null ? gender : 'Undefined' }</div>
			<br />
		</div>
	</div>
</div>