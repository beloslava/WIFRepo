/**
 * 
 */

function reg(){
	var form = $('#registerForm');
	form.ajaxSubmit({
		type : 'post',
		url : 'registerValidate',
		async : true,
		success : function(result) {
			showMessagee(result);
		}
	})
}

function showMessagee(result){
	if(result == "success"){
		 $('#message').html("<font color='#4dff88'><b>You have successfully registered. </b></font>")
         setTimeout(function(){
					window.location.href = "";
				}, 500)
	}else{
		 $('#message').html("<font color='#ff9999'><b>An error occured, try again. </b></font>")
	}
}