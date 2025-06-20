document.addEventListener('DOMContentLoaded', () => {
	const profilePhotoForm = document.getElementById("profilePhotoForm");
	const profilePhotoInput = document.getElementById("profilePhotoInput");
	const profilePhotoBtn = document.getElementById("profilePhotoBtn");

	if (profilePhotoForm && profilePhotoInput && profilePhotoBtn) {
		profilePhotoBtn.addEventListener('click', () => {
			profilePhotoInput.click();
		})
		profilePhotoInput.addEventListener('change', () => {
			profilePhotoForm.submit();
		})
	}

})