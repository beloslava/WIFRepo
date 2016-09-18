<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.pojo.UsersManager"%>
<%@ page import="model.pojo.User"%>
<!DOCTYPE HTML>
<html>
<head>
<title>My WIF | My profile</title>
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/grids.css" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<!------ Light Box ------>
<link rel="stylesheet" href="css/swipebox.css">
<script src="js/ios-orientationchange-fix.js"></script> 
<script src="js/jquery.swipebox.min.js"></script> 
<script type="text/javascript">
		jQuery(function($) {
			$(".swipebox").swipebox();
		});
	</script>
	<!------ Eng Light Box ------>
</head>
<body>
	<div class="main">
		<div class="wrap">
			<div class="left-content">
				<div class="logo">
					<h1>
						<a href="main.html"><img src="images/logo.png" alt="" /></a>
					</h1>
				</div>
				<div class="menu">
					<ol id="filters">
						<li class="home"><a href="main.jsp">Home</a></li>
						<li class="video"><a href="myProfile.jsp">My Profile</a></li>
						<li class="photo"><a href="myPhotos.jsp">My Photos</a></li>
						<li class="photo"><a href="topTen.jsp">Top 10</a></li>
						<li class="photo"><a href="people.jsp">People</a></li>
						<li class="photo"><a href="nature.jsp">Nature</a></li>
						<li class="photo"><a href="fun.jsp">Fun</a></li>
						<li class="photo"><a href="pets.jsp">Pets</a></li>
					</ol>
				</div>
			</div>
			<div class="right-content">
				<div class="header">
					<div class="social-icons">
						<ul>
							<li><a class="facebook" href="https://www.facebook.com/"
								target="_blank"> </a></li>
							<li><a class="twitter" href="https://twitter.com/"
								target="_blank"></a></li>
							<li><a class="googleplus" href="https://plus.google.com/"
								target="_blank"></a></li>
							<li><a class="pinterest" href="https://www.pinterest.com/"
								target="_blank"></a></li>
							<li><a class="dribbble" href="https://dribbble.com/"
								target="_blank"></a></li>
							<li><a class="vimeo" href="https://dribbble.com/"
								target="_blank"></a></li>
							<li><a class="logoutbtn" href="LogOutServlet"></a></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div class="search_box">
						<form>
							<input type="text" class="text-box" placeholder="Search"><input
								type="submit" value="">
						</form>
					</div>
					<div class="clear"></div>
					<form action="ViewProfileServlet" method="GET"></form>
				</div>
				<div id="content">
					<div id="main" role="main">
					<img alt="" src="PictureServlet?email=<%= request.getSession().getAttribute("USER") %>">
					<p>Name:<%= UsersManager.getInstance().getUser(request.getSession().getAttribute("USER").toString()).getName() %></p>
					<p>Email:<%= request.getSession().getAttribute("USER").toString() %></p>
					<p>Age:<%= UsersManager.getInstance().getUser(request.getSession().getAttribute("USER").toString()).getAge()%></p>
					<p>Gender:<%= UsersManager.getInstance().getUser(request.getSession().getAttribute("USER").toString()).getGender() %></p>
					<p>About:<%= UsersManager.getInstance().getUser(request.getSession().getAttribute("USER").toString()).getAbout() %></p>	
							
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>
