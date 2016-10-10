<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.mywif.model.pojo.UsersManager"%>
<%@ page import="com.mywif.model.db.UserDAO"%>
<%@ page import="com.mywif.model.pojo.User"%>
<%@ page import="com.mywif.model.pojo.Post"%>
<%@ page import="com.mywif.model.db.PostDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE HTML>
<html lang="en">
<head> 
	<c:set var="userEmail" value="${requestScope.email}" />
	<c:set var="user" value="${UsersManager.getInstance().getUser(userEmail)}" />    
<title>My Wif | <c:out value="${user.name}"></c:out></title>
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
<script type="text/javascript">
 function showhide(id) {
    var e = document.getElementById(id);
    e.style.display = (e.style.display == 'block') ? 'none' : 'block';
 }
 
 function followUser(followedEmail){
	 alert(followedEmail);
	 $.post("follow", { email: followedEmail }  ,function(data, status){
	        alert("yei");
	    });
 }
</script>
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
							<input type="text" name="input" value="type and hit enter"
								onFocus="this.value=''" />
								<label>Search in </label>
			                <select name="type">
							       <option value="posts">posts
							       <option value="users">users
							</select>
						</form>
					</li>
          <li><a href="main">Home</a>
		  <li ><a href="myProfile">My profile</a>
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

<div class="intro"><c:out value="${user.name}"></c:out>'s profile</div>
  <div class="box">
   
    <div class="one-third">
<!--     <button onclick="document.getElementById('myImage').src='img/following.png'">Turn on the light</button> -->

<!-- <img id="myImage" src="img/follow.png" style="width:100px"> -->   
    <c:choose>
			<c:when test= "${not isFollowed}">
				<form action="follow" method="post" >
				 <input type="image" src="img/follow.png" width="100" alt="" />
			     <input type="hidden" value="<c:out value="${sessionScope.USER}"></c:out>">
				 <input name="email" type="hidden" value="<c:out value="${user.email}"></c:out>" >       
				 <input type="submit" value="">					           
				</form>
			</c:when>
			<c:otherwise>
				<form action="unfollow" method="post" >
				 <input type="image" src="img/following.png" width="100" alt="" />
			     <input type="hidden" value="<c:out value="${sessionScope.USER}"></c:out>">
				 <input name="email" type="hidden" value="<c:out value="${user.email}"></c:out>" >       
				 <input type="submit" value="">					           
				</form>
			</c:otherwise>
		</c:choose>
    
  <!--      <img src="img/follow.png" onclick="followUser('<c:out value="${user.email}"></c:out>')" width="100" alt="" /><br>-->
      <div class="outer none"><span class="inset"><img src="pictureprofile?email=<c:out value="${user.email}"></c:out>" alt=""></span></div>
      <h1>Followers</h1>
       <c:set var="userEmail" value="${sessionScope.USER}" />
		 
		 <c:forEach var='followerEmail'	items='${UsersManager.getInstance().getFollowersByUser(user.email)}' end="5">
  		    <c:set var="userName" value="${UsersManager.getInstance().getUser(followerEmail).name}" />	        		
  		    <h4><a href="pictureprofile?email=<c:out value="${followerEmail}"></c:out>"><c:out value="${UsersManager.getInstance().getUser(followerEmail).name}"></c:out></a></h4> 		        				    
   		</c:forEach>

      <h1>Following</h1>
      
       <c:forEach var='followedEmail'	items='${UsersManager.getInstance().getFollowedByUser(user.email)}' end="5">
  		    <c:set var="userName" value="${UsersManager.getInstance().getUser(followedEmail).name}" />	        		
  		    <h4><a href="pictureprofile?email=<c:out value="${followedEmail}"></c:out>"><c:out value="${UsersManager.getInstance().getUser(followedEmail).name}"></c:out></a></h4> 		        				    
   		</c:forEach>
    </div>
    
    <div id="first" class="widget-area"><br><br>
	    <h2><c:out value="${user.name}"></c:out></h2>
		<h1><b>Email: </b><c:out value="${user.email}"></c:out></h1>
		<c:choose>
			<c:when test= "${user.gender != null}">
			<h1><b>Gender: </b><c:out value="${user.gender}"></c:out></h1>
									
			</c:when>
			<c:otherwise>
				<h1><b>Gender: </b><i>Not specified</i></h1>
			</c:otherwise>
		</c:choose>
		
			<c:choose>
			<c:when test= "${user.about != null}">
			<h1><b>Gender: </b><c:out value="${user.about}"></c:out></h1>
									
			</c:when>
			<c:otherwise>
				<h1><b>About: </b><i>Not specified</i></h1>
			</c:otherwise>
		</c:choose> 		
	</div>
	<div class="two-third last">
  
	<c:forEach var='post' items='${PostDAO.getInstance().getAllPostsByUser(param.email)}'>
    
     <div class="post format-image box">
        <div class="frame"> <a href="detailspost?postId=<c:out value="${post.id}"></c:out>"><img src="picturepost?postId=<c:out value="${post.id}"></c:out>"/></a> </div>
        <div class="details"> 
        
        	<c:set var="userName" value="${UsersManager.getInstance().getUser(post.userEmail).name}"/>
	       <span class="icon-artist"><a href="detailspost?postId=<c:out value="${post.id}"></c:out>>" ><c:out value= "${post.createdOn}"></c:out></a></span> 
	       <span class="dislikes"><a href="post/dislike?postId=<c:out value="${post.id}"></c:out>>" class="likeThis" title="dislikes" ><c:out value="${fn:length(post.dislikes)}"></c:out></a></span> 
	       <span class="likes"><a href="post/like?postId=<c:out value="${post.id}"></c:out>>" class="likeThis" title="likes"> <c:out value="${fn:length(post.likes)}"></c:out></a></span> 
	       <span class="comments"><a href="detailspost?postId=<c:out value="${post.id}"></c:out>>" title="comments"></a><c:out value="${fn:length(post.comments)}"></c:out></span>    
	    </div>
      </div>
    
    </c:forEach> 	
	
	</div>
    <div class="clear"></div>
  </div>
</div>
<div class="footer-wrapper">
		<div id="footer" class="four">
			<div id="first" class="widget-area">
				<div class="widget widget_search">
					<h3 class="widget-title">Search</h3>
					<form class="searchform" method="get" action="search">
							<input type="text" name="input" value="type and hit enter"
								onFocus="this.value=''" />
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
							   	   href="category?category=abstract">Abstract</a>(<c:out value="${applicationScope.abstractPosts}"></c:out>)</li>					
							<li><a href="category?category=animals">Animals</a>(<c:out value="${applicationScope.animalsPosts}"></c:out>)</li>
							<li><a href="category?category=family">Family</a>(<c:out value="${applicationScope.familyPosts}"></c:out>)</li>
							<li><a href="category?category=food">Food</a>(<c:out value="${applicationScope.foodPosts}"></c:out>)</li>
							<li><a href="category?category=nature">Nature</a>(<c:out value="${applicationScope.naturePosts}"></c:out>)</li>
							<li><a href="category?category=people">People</a>(<c:out value="${applicationScope.peoplePosts}"></c:out>)</li>
							<li><a href="category?category=sport">Sport</a>(<c:out value="${applicationScope.sportPosts}"></c:out>)</li>
							<li><a href="category?category=travel">Travel</a>(<c:out value="${applicationScope.travelPosts}"></c:out>)</li>
							<li><a href="category?category=urban">Urban</a>(<c:out value="${applicationScope.urbanPosts}"></c:out>)</li>
							<li><a href="category?category=uncategorized">Uncategorized</a>(<c:out value="${applicationScope.uncategorizedPosts}"></c:out>)</li>
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
							href="detailsprofile?email=<c:out value="${followerEmail}"></c:out>>"
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
							href="detailsprofile?email=<c:out value="${followedEmail}"></c:out>>"
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
<script src="js/scripts.js"></script>
</body>
</html>