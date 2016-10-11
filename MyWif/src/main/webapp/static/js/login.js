/**
 * 
 */
function validateEmail() {
		$('#paragraf-emails').show();
		var email = $('#email').val();

		//var passRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
		var passRegex =/[a-zA-Z0-9_.+-]+@.+\..+/;

		if(passRegex.test(email) == true){
			$('#paragraf-emails').css({
				color: "white"
			})
			$('#paragraf-emails').html('Valid')
			return true;
		}else {
			$('#paragraf-emails').css({
				color: "red"
			})
			$('#paragraf-emails').html('Please Enter a valid email');
			return false;
		}
}
function validatePasword (){
	$('#par-login-pass').show();
	var pass = $('#password').val();

	//var passRegex = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/
	var passRegex =/.{6,12}/;

	if(passRegex.test(pass) == true){
		$('#par-login-pass').css({
			color: "white"
		})
		$('#par-login-pass').html('Valid');
		return true;
	}else {
		$('#par-login-pass').css({
			color: "red"
		})
		$('#par-login-pass').html('Please Enter a valid password');
		return false;
	}
}

$(function (){
	debugger;
	validateEmail();
	validatePasword();
	$(".login-form").on("click", function(e){
		e.preventDefault();
	});
	$('#par-login-pass').hide();
	$('#paragraf-emails').hide();
	$("#login").on("click", function(){

		var email = $("#email").val();
		var password = $("#password").val();
		if(validateEmail() == false && validatePasword() == false){
			return;
		}else {
		$.ajax({
			  method:"POST",
			  url: "login",
			  dataType: 'json',
			  data: {email :email , password : password},
			})
			.done(function(response) {

			  console.log(response);
			  window.location.replace("main");
			})
		}
	})
})