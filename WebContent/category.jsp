<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.pojo.UsersManager"%>
<%@ page import="model.db.UserDAO"%>
<%@ page import="model.pojo.User"%>
<%@ page import="model.pojo.Post"%>
<%@ page import="model.db.PostDAO"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Obscura</title>
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
<script>$.backstretch("style/images/bg/1.jpg");</script>
</head>
<body>
<div class="scanlines"></div>
<div class="header-wrapper opacity">
  <div class="header">
    <div class="logo"> <a href="main.jsp"> <img src="images/logo.png" width="30%" alt=""> </a> </div>
    <div id="menu-wrapper">
      <div id="menu" class="menu">
       <ul id="tiny">
          <li><a href="main.jsp">Home</a>
		  <li><a href="myProfile.jsp">My profile</a>
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
<%String category=request.getAttribute("category").toString(); %>
  <div class="intro"><%=category.substring(0,1).toUpperCase()+category.substring(1) %></div>
  <ul class="social">
    <li><a class="rss" href="#"></a></li>
    <li><a class="facebook" href="#"></a></li>
    <li><a class="twitter" href="#"></a></li>
    <li><a class="pinterest" href="#"></a></li>
    <li><a class="dribbble" href="#"></a></li>
    <li><a class="flickr" href="#"></a></li>
    <li><a class="linkedin" href="#"></a></li>
  </ul>
  <div class="blog-wrap">
    <div class="blog-grid">
   <%   
	for (Post post : PostDAO.getInstance().getAllPostsByCategory(category)) {
	%>
      <div class="post format-image box">
        <div class="frame"> <a href="DetailsServlet?postId=<%=post.getId()%>"><img src="PostPictureServlet?postId=<%=post.getId() %>"/></a> </div>
        <div class="details"> 
	      <span class="icon-artist"><a href="ProfileServlet?email=<%=post.getUserEmail()%>" title="author name"><%=UsersManager.getInstance().getUser(post.getUserEmail()).getName() %></a></span> 
	        <span class="likes"><a href="LikesServlet?postId=<%=post.getId()%>" class="likeThis" title="likes"> <%=PostDAO.getInstance().getNumberOfPostLikes(post.getId())%></a></span> 
	        <span class="likes"><a href="DislikeServlet?postId=<%=post.getId()%>" class="likeThis" title="dislikes" ><%=PostDAO.getInstance().getNumberOfPostDislikes(post.getId())%></a></span> 
	       <span class="comments"><a href="DetailsServlet?postId=<%=post.getId()%>" title="comments"></a><%=post.getComments().size()%></span> 
	    </div>
      </div>
      <%} %>
      
    </div>
  </div>
  <div id="navigation">
    <div class="nav-previous"><a href="#" ><span class="meta-nav-prev">&larr; Older posts</span></a></div>
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
<div class="site-generator-wrapper">
</div>
<script src="style/js/scripts.js"></script>
</body>
</html>