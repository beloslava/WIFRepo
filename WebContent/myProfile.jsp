<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.pojo.UsersManager"%>
<%@ page import="model.pojo.User"%>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Obscura | Full Width</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<link rel="stylesheet" type="text/css" href="style/css/style.css" media="all">
<link rel="stylesheet" type="text/css" href="style/css/media-queries.css">
<link rel="stylesheet" type="text/css" href="style/js/player/mediaelementplayer.css">
<link rel="stylesheet" type='text/css' href='http://fonts.googleapis.com/css?family=Open+Sans:400,400italic,300italic,300,700,700italic|Open+Sans+Condensed:300,700'>
<!--[if IE 8]>
<link rel="stylesheet" type="text/css" href="style/css/ie8.css" media="all">
<![endif]-->
<!--[if IE 9]>
<link rel="stylesheet" type="text/css" href="style/css/ie9.css" media="all">
<![endif]-->
<script src="style/js/jquery-1.7.2.min.js"></script>
<script src="style/js/ddsmoothmenu.js"></script>
<script src="style/js/retina.js"></script>
<script src="style/js/selectnav.js"></script>
<script src="style/js/jquery.masonry.min.js"></script>
<script src="style/js/jquery.fitvids.js"></script>
<script src="style/js/jquery.backstretch.min.js"></script>
<script src="style/js/mediaelement.min.js"></script>
<script src="style/js/mediaelementplayer.min.js"></script>
<script src="style/js/jquery.dcflickr.1.0.js"></script>
<script src="style/js/twitter.min.js"></script>
<script type="text/javascript">
 function showhide(id) {
    var e = document.getElementById(id);
    e.style.display = (e.style.display == 'block') ? 'none' : 'block';
 }
</script>
<script>$.backstretch("style/images/bg/1.jpg");</script>
</head>
<body>
<div class="scanlines"></div>
<div class="header-wrapper opacity">
  <div class="header">
 <div class="logo"> <a href="main.jsp"> <img src="style/images/logo.png" alt=""> </a> </div>
    <div id="menu-wrapper">
      <div id="menu" class="menu">
        <ul id="tiny">
          <li><a href="main.jsp">Home</a>
		  <li class="active"><a href="myProfile.jsp">My profile</a>
		  <li><a href="myPhotos.jsp">My photos</a>
          </li>
          <li><a>Categories</a>
            <ul>
              <li class="active"><a href="CategoryServlet?category=abstract">Abstract</a></li>
              <li><a href="CategoryServlet?category=animals">Animals</a></li>
			  <li><a href="CategoryServlet?category=email">Family</a></li>
			  <li><a href="CategoryServlet?category=food">Food</a></li>
              <li><a href="CategoryServlet?category=nature">Nature</a></li>
			  <li><a href="CategoryServlet?category=people">People</a></li>
			  <li><a href="CategoryServlet?category=sport">Sport</a></li>
			  <li><a href="CategoryServlet?category=travel">Travel</a></li>
			  <li><a href="CategoryServlet?category=urban">Urban</a></li>
			  <li><a href="CategoryServlet?category=uncategorized">Uncategorized</a></li>
            </ul>
          </li>
          <li><a href="topTen.jsp">Top 10</a>
          </li>
          <li><a href="upload.jsp">Upload</a></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</div>
<div class="wrapper">
  <div class="intro">Your profile...</div>
<ul class="social">
    <li><a class="rss" href="https://www.rss.com/"></a></li>
    <li><a class="facebook" href="https://www.facebook.com/"></a></li>
    <li><a class="twitter" href="https://twitter.com/"></a></li>
    <li><a class="pinterest" href="https://www.pinterest.com/"></a></li>
    <li><a class="dribbble" href="https://dribbble.com/"></a></li>
    <li><a class="flickr" href="https://www.flickr.com/"></a></li>
    <li><a class="linkedin" href="https://www.linkedin.com/"></a></li>
</ul>
  <div class="box">
    <div class="one-third">
      <div class="outer none"><span class="inset"><img src="PictureServlet?email=<%=request.getSession().getAttribute("USER")%>" alt=""></span></div>
      <a class="comment-reply-link" href="javascript:showhide('changePicture')">Change profile picture</a>
      <div id="changePicture" style="display:none;">	
		<fieldset>
				<form action="ChangeProfilePictureServlet" method="post" enctype="multipart/form-data">
					Upload your new profile picture<input type="file" name="fileField" value="" class="text-input required"><br>
					<input type="submit" value="Save">
				</form>
		</fieldset>
		</div>
    </div>
    <%
    User user=UsersManager.getInstance().getUser(request.getSession().getAttribute("USER").toString());
    %>
    <div class="two-third last">
	    <h2><%=user.getName() %></h2>
		<h1><b>Email: </b><%=request.getSession().getAttribute("USER").toString()%></h1>
		<%if(user.getGender()!=null) {%>
	    <h1><b>Gender: </b><%=user.getGender()%></h1>
	    <%} else
	    	{%>
	    	<h1><b>Gender: </b><i>Not specified</i></h1>
	    	<%}
		if(user.getAbout()!=null){%>
		<h1><b>About: </b><%=user.getAbout()%></h1>
		<%} else
	    	{%>
	    			<h1><b>About: </b><i>Not specified</i></h1>
	    	<%} %>  	
	</div>
	<div class="tree-third last">
		<a class="comment-reply-link" href="javascript:showhide('changeProfile')">Change profile</a>
		<div id="changeProfile" style="display:none;">	
		<fieldset>
				<form action="ChangeProfileServlet" method="post">
					Enter new name<input type="text" name="newName" value="<%=user.getName() %>" class="text-input required">
					Enter old password<input type="password" name="oldPass" value="" class="text-input required">
					Enter new password<input type="password" name="newPass" value="" class="text-input required">
					Repeat new password<input type="password" name="newPass2" value="" class="text-input required">
					Gender <span><input type="radio" name="gender" value="male">Male <input type="radio" name="gender" value="female">Female </span>
					Enter your description<input type="text" name="newDescription" value="<%=user.getAbout() %>" class="text-input required"><br>
					<input type="submit" value="Save profile settings">
				</form>
		</fieldset>
		</div>
	</div>
    <div class="clear"></div>
  </div>
</div>
<div class="footer-wrapper">
  <div id="footer" class="four">
    <div id="first" class="widget-area">
      <div class="widget widget_search">
        <h3 class="widget-title">Search</h3>
         <form class="searchform" method="get" action="SearchServlet">
          <input type="text" name="userName" value="type and hit enter" onFocus="this.value=''" onBlur="this.value='type and hit enter'"/>
        </form>
      </div>
      <div class="widget widget_archive">
        <h3 class="widget-title">Archives</h3>
        <ul>
          <li><a href="#">September 2045</a> (6)</li>
          <li><a href="#">August 2045</a> (2)</li>
          <li><a href="#">July 2045</a> (2)</li>
          <li><a href="#">June 2045</a> (4)</li>
          <li><a href="#">May 2045</a> (3)</li>
          <li><a href="#">January 2045</a> (1)</li>
        </ul>
      </div>
    </div>
    <div id="second" class="widget-area">
      <div id="twitter-2" class="widget widget_twitter">
        <h3 class="widget-title">Twitter</h3>
        <div id="twitter-wrapper">
          <div id="twitter"></div>
          <span class="username"><a href="#">&rarr; Follow @elemisdesign</a></span> </div>
      </div>
    </div>
    <div id="third" class="widget-area">
      <div id="example-widget-3" class="widget example">
        <h3 class="widget-title">Popular Posts</h3>
        <ul class="post-list">
          <li>
            <div class="frame"> <a href="#"><img src="style/images/art/s1.jpg" alt=""></a> </div>
            <div class="meta">
              <h6><a href="#">Charming Winter</a></h6>
              <em>28th Sep 2045</em> </div>
          </li>
          <li>
            <div class="frame"> <a href="#"><img src="style/images/art/s2.jpg" alt=""></a> </div>
            <div class="meta">
              <h6><a href="#">Trickling Stream</a></h6>
              <em>5th Sep 2045</em> </div>
          </li>
          <li>
            <div class="frame"> <a href="#"><img src="style/images/art/s3.jpg" alt=""></a> </div>
            <div class="meta">
              <h6><a href="#">Morning Glory</a></h6>
              <em>26th Sep 2045</em> </div>
          </li>
        </ul>
      </div>
    </div>
    <div id="fourth" class="widget-area">
      <div class="widget">
        <h3 class="widget-title">Flickr</h3>
        <ul class="flickr-feed">
        </ul>
      </div>
    </div>
  </div>
</div>
<script src="style/js/scripts.js"></script>
</body>
</html>