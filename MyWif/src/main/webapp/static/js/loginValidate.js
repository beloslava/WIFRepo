/**
 * 
 */
function validateLogin(){
	var form = $('#loginForm');
	$.post("loginValidate", form.serialize()).then(showMessage);
}

function showMessage(results){
	if(results == 'success'){
		$('#messages').html("<font color='#4dff88'><b>Login Successful</b></font>");
		setTimeout(function(){
			window.location.href = "main";
		}, 1000)
	}else{
		$('#messages').html("<font color='#ff9999'><b>Login Failed Try Again</b></font>");
	}
}