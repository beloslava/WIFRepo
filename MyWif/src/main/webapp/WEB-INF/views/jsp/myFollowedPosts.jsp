<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.mywif.model.pojo.UsersManager"%>
<%@ page import="com.mywif.model.db.UserDAO"%>
<%@ page import="com.mywif.model.pojo.User"%>
<%@ page import="com.mywif.model.pojo.Post"%>
<%@ page import="com.mywif.model.db.PostDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
<%
response.addHeader("Cache-Control", "no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0"); 
response.addHeader("Pragma", "no-cache"); 
response.addDateHeader ("Expires", 0);
%>
<title>My Wif</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<link rel="stylesheet" type="text/css" href="css/style.css" media="all">
<link rel="stylesheet" type="text/css" href="css/media-queries.css">
<link rel="stylesheet" type="text/css" href="js/player/mediaelementplayer.css">
<link rel="stylesheet" type='text/css' href='http://fonts.googleapis.com/css?family=Open+Sans:400,400italic,300italic,300,700,700italic|Open+Sans+Condensed:300,700'>
<!--[if IE 8]>
<link rel="stylesheet" type="text/css" href="css/ie8.css" media="all">
<![endif]-->
<!--[if IE 9]>
<link rel="stylesheet" type="text/css" href="css/ie9.css" media="all">
<![endif]-->
<script src="js/jquery-1.7.2.min.js"></script>
<script src="js/ddsmoothmenu.js"></script>
<script src="js/retina.js"></script>
<script src="js/selectnav.js"></script>
<script src="js/jquery.masonry.min.js"></script>
<script src="js/jquery.fitvids.js"></script>
<script src="js/jquery.backstretch.min.js"></script>
<script src="js/mediaelement.min.js"></script>
<script src="js/mediaelementplayer.min.js"></script>
<script src="js/jquery.dcflickr.1.0.js"></script>
<script src="js/twitter.min.js"></script>
<script>$.backstretch("img/bg/1.jpg");</script>
</head>
<body>
<div class="scanlines"></div>
<div class="header-wrapper opacity">
  <div class="header">
 <div class="logo"> <a href="main"> <img src="img/logo.png" alt=""> </a> </div>
    <div id="menu-wrapper">
      <div id="menu" class="menu">
        <ul id="tiny">
        <li>
						<form class="searchform" method="get" action="search">
							<input type="text" name="input" value="type and hit enter" maxlength="50"
								onFocus="this.value=''" />
								<label>Search in </label>
			                <select name="type">
							       <option value="posts">posts
							       <option value="users">users
							</select>
						</form>
					</li>
          <li><a href="main">Home</a>
          <li class="active"><a href="myFollowedPosts">Feed</a>
          </li>
		  <li><a href="myProfile">My profile</a>
		  <li><a href="myAlbums">My Albums</a>
          </li>
          <li><a>Categories</a>
            <ul>
              <li><a href="category?category=abstract">Abstract</a></li>
								<li><a href="category?category=animals">Animals</a></li>
								<li><a href="category?category=family">Family</a></li>
								<li><a href="category?category=food">Food</a></li>
								<li><a href="category?category=nature">Nature</a></li>
								<li><a href="category?category=people">People</a></li>
								<li><a href="category?category=sport">Sport</a></li>
								<li><a href="category?category=travel">Travel</a></li>
								<li><a href="category?category=urban">Urban</a></li>
								<li><a href="category?category=uncategorized">Uncategorized</a></li>
            </ul>
          </li>
          <li><a href="topTen">Top 10</a>
          </li>
          <li><a href="logOut">Log out</a></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</div>
<div class="wrapper">
  <div class="intro">Your followed users posts... </div>
  <div class="blog-wrap">
    <div class="blog-grid">
    	<c:choose>
				<c:when test= "${ not empty UsersManager.getInstance().getFollowedPosts(sessionScope.USER)}">
					<c:forEach var="post" items="${UsersManager.getInstance().getFollowedPosts(sessionScope.USER)}">   			
     <div class="post format-image box">
        <div class="frame"> <a href="detailspost?postId=<c:out value="${post.id}"></c:out>"><img src="picturepost?postId=<c:out value="${post.id}"></c:out>"/></a> </div>
        <div class="details">       
        	<c:set var="userName" value="${UsersManager.getInstance().getUser(post.userEmail).name}"/>
	        <span class="icon-artist"><a href="detailsprofile?email=<c:out value="${post.userEmail}"></c:out>" title="author name"><c:out value= "${userName}"></c:out></a></span> 
	        <span class="dislikes"><a href="detailspost?postId=<c:out value="${post.id}"></c:out>" class="likeThis" title="dislikes" ><c:out value="${fn:length(post.dislikes)}"></c:out></a></span> 
	        <span class="likes"><a href="detailspost?postId=<c:out value="${post.id}"></c:out>" class="likeThis" title="likes"> <c:out value="${fn:length(post.likes)}"></c:out></a></span> 
	        <span class="comments"><a href="detailspost?postId=<c:out value="${post.id}"></c:out>" title="comments"></a><c:out value="${fn:length(post.comments)}"></c:out></span>    
	    </div>
      </div>
   					 </c:forEach> 										
				</c:when>
		
		</c:choose> 
      
    </div>
  </div>
</div>
<div class="footer-wrapper">
		<div id="footer" class="four">
			<div id="first" class="widget-area">
				<div class="widget widget_search">
					<h3 class="widget-title">Search</h3>
					<form class="searchform" method="get" action="search">
							<input type="text" name="input" value="type and hit enter" maxlength="50"
								onFocus="this.value=''" required/>
								<label>Search in </label>
			                <select name="type">
							       <option value="posts">posts
							       <option value="users">users
							</select>
						</form>
				</div>
				<div class="widget widget_search">
				<h3 class="widget-title"></h3>
				<ul class="social">
					<li><a class="facebook" href="https://www.facebook.com/"></a></li>
					<li><a class="twitter" href="https://twitter.com/"></a></li>
					<li><a class="pinterest" href="https://www.pinterest.com/"></a></li>
					<li><a class="linkedin" href="https://www.linkedin.com/"></a></li>
				</ul>
				</div>

			</div>
			<div id="second" class="widget-area">
				<div class="widget widget_archive">
					<h3 class="widget-title">Categories</h3>
					<ul>
						<li class="active"><a 
							   	    href="category?category=abstract">Abstract</a>(<c:out value="${abstractPosts}"></c:out>)</li>					
							<li><a href="category?category=animals">Animals</a>(<c:out value="${animalsPosts}"></c:out>)</li>
							<li><a href="category?category=family">Family</a>(<c:out value="${familyPosts}"></c:out>)</li>
							<li><a href="category?category=food">Food</a>(<c:out value="${foodPosts}"></c:out>)</li>
							<li><a href="category?category=nature">Nature</a>(<c:out value="${naturePosts}"></c:out>)</li>
							<li><a href="category?category=people">People</a>(<c:out value="${peoplePosts}"></c:out>)</li>
							<li><a href="category?category=sport">Sport</a>(<c:out value="${sportPosts}"></c:out>)</li>
							<li><a href="category?category=travel">Travel</a>(<c:out value="${travelPosts}"></c:out>)</li>
							<li><a href="category?category=urban">Urban</a>(<c:out value="${urbanPosts}"></c:out>)</li>
							<li><a href="category?category=uncategorized">Uncategorized</a>(<c:out value="${uncategorizedPosts}"></c:out>)</li>
					</ul>
				</div>

			</div>
			<div id="third" class="widget-area">
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">Followers</h3>
					<c:set var="user" value="${sessionScope.USER}" />									
					<c:forEach var='followerEmail'
						items='${UsersManager.getInstance().getFollowersByUser(user)}'
						end="5">
  		        		<c:set var="userName"
							value="${UsersManager.getInstance().getUser(followerEmail).name}" />
				       	<a
							href="detailsprofile?email=<c:out value="${followerEmail}"></c:out>"
							title="author name"><c:out value="${userName}"></c:out></a>	     
   					</c:forEach>	
				</div>
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">Following</h3>
					<c:forEach var='followedEmail'
						items='${UsersManager.getInstance().getFollowedByUser(user)}'
						end="5">
  		        		<c:set var="userName"
							value="${UsersManager.getInstance().getUser(followedEmail).name}" />
				       	<a
							href="detailsprofile?email=<c:out value="${followedEmail}"></c:out>"
							title="author name"><c:out value="${userName}"></c:out></a>
   					</c:forEach>
				</div>
			</div>
			<div id="fourth" class="widget-area">
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">Popular Posts</h3>
					<ul class="post-list">
						<li>
							<div class="frame">
								<c:set var="post"
									value="${PostDAO.getInstance().getTopTenPosts()[0]}"
									scope="session" />							
								<a
									href="detailspost?postId=<c:out value="${post.id}"></c:out>"><img
									src="picturepost?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="detailspost?postId=<c:out value="${post.id}"></c:out>"><c:out
											value="${post.name}"></c:out></a>
								</h6>
								<em><c:out value="${post.createdOn}"></c:out></em>
							</div>
						</li>

						<li>
							<div class="frame">
							
								<c:set var="post"
									value="${PostDAO.getInstance().getTopTenPosts()[1]}"
									scope="session" />
								<a
									href="detailspost?postId=<c:out value="${post.id}"></c:out>"><img
									src="picturepost?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="detailspost?postId=<c:out value="${post.id}"></c:out>"><c:out
											value="${post.name}"></c:out></a>
								</h6>
								<em><c:out value="${post.createdOn}"></c:out></em>
							</div>
						</li>
						<li>
							<div class="frame">
							
								<c:set var="post"
									value="${PostDAO.getInstance().getTopTenPosts()[2]}"
									scope="session" />
								<a
									href="detailspost?postId=<c:out value="${post.id}"></c:out>"><img
									src="picturepost?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="detailspost?postId=<c:out value="${post.id}"></c:out>"><c:out
											value="${post.name}"></c:out></a>
								</h6>			
								<em><c:out value="${post.createdOn}"></c:out></em>
							</div>

						</li>
					</ul>
				</div>
			</div>
		</div>
		</div>
<div class="site-generator-wrapper">
</div>
<script src="js/scripts.js"></script>
</body>
</html>