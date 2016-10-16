/**
 * 
 */
//function validateRegister(){
//	var form2 = $('#registerForm');
//	$.post("registerValidate", form2.serialize()).then(showMessagee);
//}
//
//function showMessagee(results){
//	if(results == 'success'){
//		$('#message').html("<font color='green'>Registration Successful</font>");
//		setTimeout(function(){
//			window.location.href = "";
//		}, 1000)
//	}else{
//		$('#message').html("<font color='red'>Registration Failed Try Again</font>");
//	}
//}

//Uploading a file with AJAX
$('#registerForm').on('submit',function(e) {
	e.preventDefault();
	file = $('#fileField')[0].files[0];
	formData = new FormData();
	formData.append('fileField', file);
	formData.append('name', name);
	formData.append('email', email);
	formData.append('password', password);
	formData.append('password2', password2);
		$.ajax({
				url : 'registerValidate',
				type : 'POST',
				dataType : 'JSON',
				data : formData,
				contentType : false,
				processData : false,
				success : function(d) {
					if(results == 'success'){
						$('#message').html("<font color='green'>Registration Successful</font>");
						setTimeout(function(){
							window.location.href = "";
						}, 1000)
					}else{
						$('#message').html("<font color='red'>Registration Failed Try Again</font>");
					}

				}
		});	});