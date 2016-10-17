<!DOCTYPE html>
<html lang="en">

<head>
<%
	response.addHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.addHeader("Pragma", "no-cache");
	response.addDateHeader("Expires", 0);
%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>My Wif</title>

<!-- CSS -->
<link rel="stylesheet"
	href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="fonts/font-awesome/css/font-awesome.min.css">
<link rel="stylesheet" href="css/form-elements.css">
<link rel="stylesheet" href="css/form-style.css">

<!-- Favicon and touch icons -->
<link rel="shortcut icon" href="img/logo.png">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="ico/apple-touch-icon-57-precomposed.png">

</head>

<body>
<script src="js/loginValidate.js"></script>
<script src="js/registerValidate.js"></script>
	<!-- Top content -->
	<div class="top-content">

		<div class="inner-bg">
			<div class="container">

				<div class="row">
					<div class="col-sm-8 col-sm-offset-2 text">
						<div class="description"></div>
					</div>
				</div>

				<div class="row">
					<div class="col-sm-5">
						<div class="logo">
							<a> <img src="img/logo@2x.png" alt="">
							</a>
						</div>
						<div class="form-box">
							<div class="form-top">
								<div class="form-top-left">
									<h3>Login to our site</h3>
									<p>Enter email and password to log on:</p>
								</div>
								<div class="form-top-right">
									<i class="fa fa-lock"></i>
								</div>
							</div>
							<div class="form-bottom">
								<form role="form" id="loginForm" action="javasript:validateLogin()" method="POST" class="login-form">
									<div class="form-group">
										<label class="sr-only" for="form-username">Email</label> <input
											type="email" name="email" placeholder="Email..."
											class="form-username form-control" id="form-username" maxlength="50"
											required placeholder=" ">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password">Password</label> <input
											type="password" name="password" placeholder="Password..."
											class="form-password form-control" id="form-password"
											required placeholder=" " maxlength="20">
									</div>
									<div id="messages"></div>
									<button type="submit" class="btn" onclick="validateLogin()">Log in!</button>
									
								</form>
							</div>
						</div>
					</div>

					<div class="col-sm-1 middle-border"></div>
					<div class="col-sm-1"></div>

					<div class="col-sm-5">

						<div class="form-box">
							<div class="form-top">
								<div class="form-top-left">
									<h3>Sign up now</h3>
									<p>Fill in the form below to get instant access:</p>
								</div>
								<div class="form-top-right">
									<i class="fa fa-pencil"></i>
								</div>
							</div>
							<div class="form-bottom">
								<form role="form" action="javascript:reg()" id="registerForm" method="POST" class="registration-form" enctype="multipart/form-data"
									accept="image/*" onsubmit="Validate(this)" >
									<div class="form-group">
										<label class="sr-only" for="form-name">Name</label> <input
											type="text" name="name"  placeholder="Name..."
											class="form-name form-control" maxlength="50" id="form-name" required
											placeholder=" ">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-email">Email</label> <input
											type="email" name="email" placeholder="Email..."
											class="form-email form-control" id="form-email" maxlength="50" required
											placeholder=" ">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password">Password</label> <input
											type="password" name="password" placeholder="Password..."
											class="form-password form-control" maxlength="20" id="password"
											pattern="((?=.*[1-9])(?=.*[a-z])(?=.*[A-Z]).{3,20})" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters" required
											placeholder=" ">
									</div>
									<div class="form-group">
										<label class="sr-only" for="form-password2">Repeat
											password</label> <input type="password" name="password2"
											placeholder="Confirm Password" maxlength="20" id="confirm_password"
											pattern="((?=.*[1-9])(?=.*[a-z])(?=.*[A-Z]).{3,20})" title="Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters"
											class="form-last-name form-control" required placeholder=" ">
									</div>
									<div class="form-group">
										<input type="file" name="fileField" 
											class="form-file form-control" id="form-file" required
											placeholder=" ">
									</div>
                                    <div id="message"></div>
									<button  type="submit" class="btn">Sign me up!</button>
								</form>
							</div>
						</div>

					</div>
				</div>

			</div>
		</div>

	</div>

	<!-- Footer -->
	<footer>
		<div class="container">
			<div class="row"></div>
		</div>
	</footer>

	<!-- Javascript -->
	<script src="js/jquery-1.11.1.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="js/jquery.backstretch.min.js"></script>
	<script src="js/form-scripts.js"></script>
	<script type="text/javascript">
		var _validFileExtensions = [ ".jpg", ".jpeg", ".bmp", ".gif", ".png" ];
		function Validate(oForm) {
			var arrInputs = oForm.getElementsByTagName("input");
			for (var i = 0; i < arrInputs.length; i++) {
				var oInput = arrInputs[i];
				if (oInput.type == "file") {
					var sFileName = oInput.value;
					if (sFileName.length > 0) {
						var blnValid = false;
						for (var j = 0; j < _validFileExtensions.length; j++) {
							var sCurExtension = _validFileExtensions[j];
							if (sFileName.substr(
									sFileName.length - sCurExtension.length,
									sCurExtension.length).toLowerCase() == sCurExtension
									.toLowerCase()) {
								blnValid = true;
								break;
							}
						}

						if (!blnValid) {
							alert("Sorry, " + sFileName
									+ " is invalid, allowed extensions are: "
									+ _validFileExtensions.join(", "));
							return false;
						}
					}
				}
			}

			return true;
		}
	</script>
	<script type="text/javascript">
		var password = document.getElementById("password"), confirm_password = document
				.getElementById("confirm_password");

		function validatePassword() {
			if (password.value != confirm_password.value) {
				confirm_password.setCustomValidity("Passwords Don't Match");
			} else {
				confirm_password.setCustomValidity('');
			}
		}

		password.onchange = validatePassword;
		confirm_password.onkeyup = validatePassword;
	</script>
	

	<!--[if lt IE 10]>
            <script src="js/placeholder.js"></script>
        <![endif]-->

</body>
<script src="http://malsup.github.com/jquery.form.js"></script>
</html>