/**
 * 
 */
function validateLogin(){
	var form = $('#loginForm');
	$.post("loginValidate", form.serialize()).then(showMessage);
}

function showMessage(results){
	if(results == 'success'){
		$('#messages').html("<font color='green'>Login Successful</font>");
		setTimeout(function(){
			window.location.href = "main";
		}, 1000)
	}else{
		$('#messages').html("<font color='red'>Login Failed Try Again</font>");
	}
}