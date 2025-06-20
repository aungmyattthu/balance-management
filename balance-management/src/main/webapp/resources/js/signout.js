document.addEventListener("DOMContentLoaded", function() {
	const signoutForm = document.getElementById('signOutForm');
	const signoutMenu = document.getElementById('signOutMenu');

	signoutMenu.addEventListener('click', (event) => {
		event.preventDefault();
		signoutForm.submit();
	})
})