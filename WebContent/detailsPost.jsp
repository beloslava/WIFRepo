<!--
Author: W3layouts
Author URL: http://w3layouts.com
License: Creative Commons Attribution 3.0 Unported
License URL: http://creativecommons.org/licenses/by/3.0/
-->
<!DOCTYPE HTML>
<%@page import="model.db.PostDAO"%>
<%@page import="model.pojo.UsersManager"%>
<%@page import="model.pojo.Post"%>
<%@page import="model.pojo.User"%>
<html>
<head>
<title>My Wif | Post details</title>
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/grids.css" type="text/css" media="all" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
</head>
<body>
	<div class="main">
		<div class="wrap">
			<div class="left-content">
				<div class="logo">
					<h1>
						<a href="Main.jsp"><img src="images/logo.png" alt="" /></a>
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
							<li><a class="upload" href="upload.html"></a>
							<li>
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
							<li><form action="LogOutServlet" method="POST">
									<input type="image" src="images/settings_icon.png" />
								</form></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div class="search_box">
						<form>
							<input type="text" class="text-box"
								placeholder="Search............."><input type="submit"
								value="">
						</form>
					</div>
					<div class="clear"></div>
				</div>
		<div class="content">
		<%Post post=PostDAO.getInstance().getPost(Integer.parseInt(request.getAttribute("postId").toString()));
                        		User user=UsersManager.getInstance().getUser(post.getUserEmail()); %>
			<div class="box1">
    				<span>By <%=user.getName() %>- <%=post.getCreatedOn() %><span class="comments"><%=post.getId() %> Comments</span></span> 
			   <div class="blog-img">
					<img src="PostPictureServlet?postId=<%=post.getId()%>">
				</div>
			<div class="clear"></div>
		</div>			
		<!----------------  Comment Area -------------------->
		<div class="comments-area">
						<h3>Leave a comment</h3>
							<form>
								<p>
									<label>Name</label>
									<span>*</span>
									<input type="text" value="">
								</p>
								<p>
									<label>Email</label>
									<span>*</span>
									<input type="text" value="">
								</p>
								<p>
									<label>Website</label>
									<input type="text" value="">
								</p>
								<p>
									<label>Subject</label>
									<span>*</span>
									<textarea></textarea>
								</p>
								<p>
									<input type="submit" value="Post">
								</p>
							</form>		
						</div>
						
						<div class="box comment">
	    <h2><span>(0)</span> Comment's</h2>
	    <ul class="list">
	        <li>
	            <div class="preview"><a href="#"><img src="http://lorempixel.com/50/50" alt=""></a></div>
	            <div class="data">
	                <div class="title">Jake Sully <a href="#"> June 20, 2013</a></div>
	                <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
	            </div>
	            <div class="clear"></div>
	        </li>
	        <li>
	            <div class="preview"><a href="#"><img src="http://lorempixel.com/50/50" alt=""></a></div>
	            <div class="data">
	                <div class="title">Jake Sully <a href="#"> June 20, 2013</a></div>
	                <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
	            </div>
	            <div class="clear"></div>
	        </li>
	        <li>
	            <div class="preview"><a href="#"><img src="http://lorempixel.com/50/50" alt=""></a></div>
	            <div class="data">
	                <div class="title">Jake Sully <a href="#"> June 20, 2013</a></div>
	                <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
	            </div>
	            <div class="clear"></div>
	        </li>
	        <li>
	            <div class="preview"><a href="#"><img src="http://lorempixel.com/50/50" alt=""></a></div>
	            <div class="data">
	                <div class="title">Jake Sully <a href="#"> June 20, 2013</a></div>
	                <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
	            </div>
	            <div class="clear"></div>
	        </li>
	        <li>
	            <div class="preview"><a href="#"><img src="http://lorempixel.com/50/50" alt=""></a></div>
	            <div class="data">
	                <div class="title">Jake Sully <a href="#"> June 20, 2013</a></div>
	                <p>Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.</p>
	            </div>
	            <div class="clear"></div>
	        </li>
	    </ul>
	  <div class="clear"></div>
	</div>
		<!----------------- End Comment Area ----------------->				
    </div>
  </div>
		<div class="clear"></div>
	</div>
</div>
</body>
</html>
