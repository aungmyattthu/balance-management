document.addEventListener("DOMContentLoaded", function() {
	const statusChangeDialog = new bootstrap.Modal('#statusChangeDialog')
	const statusChangeBtn = document.getElementById('statusChangeBtn')
	
	if(statusChangeBtn && statusChangeDialog){
		statusChangeBtn.addEventListener('click', ()=>{
			statusChangeDialog.show()
		})
	}
		
});