<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="pageResult" required="true"
	type="com.amt.online.balance_management.model.PageResult"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<div class="d-flex justify-content-between">

	<c:if test="${pageResult ne null }">
		<div class="d-flex">
			<c:if test="${not empty pageResult.pageLinks }">
			<!-- Page Select -->
				<div class="input-group me-2">
					<span class="input-group-text">Size</span> <select name=""
						id="sizeSelect" class="form-select">
						<option value="10" ${pageResult.size() eq '10' ? 'selected' : ''}>10</option>
						<option value="25" ${pageResult.size() eq '25' ? 'selected' : ''}>25</option>
						<option value="50" ${pageResult.size() eq '50' ? 'selected' : ''}>50</option>
					</select>
				</div>
				<!-- Page Link -->
				<div class="d-flex page-links">
					
						<a href="#" data-page-number="0"
							class="btn btn-outline-primary me-1 d-flex align-items-center pageLink">
							<i class="bi-arrow-left"></i>
						</a>
						<c:forEach items="${pageResult.pageLinks }" var="item">
							<a href="#" data-page-number="${item }"
								class="btn ${pageResult.page() eq item ?'btn-primary':'btn-outline-primary' } me-1 pageLink">
								${item + 1 } </a>
						</c:forEach>
						<a href="#" data-page-number="${pageResult.totalPages - 1 }"
							class="btn btn-outline-primary me-1 d-flex align-items-center pageLink">
							<i class="bi-arrow-right"></i>
						</a>
					
				</div>
			</c:if>
		</div>
		
		<!-- Page Result Info -->
		<div class="d-flex ">
			<div class="input-group me-2">
				<span class="input-group-text">Total Pages</span> <span
					class="form-control">${pageResult.totalPages }</span>
			</div>
			<div class="input-group me-2">
				<span class="input-group-text">Total Result</span> <span
					class="form-control">${pageResult.count() }</span>
			</div>

		</div>
		<script type="text/javascript"
			src="${root}/resources/js/pagination.js"></script>
	</c:if>


</div>
