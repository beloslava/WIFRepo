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
<title>My Wif | <%=UsersManager.getInstance().getUser(request.getAttribute("email").toString()).getName() %></title>
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
        <li>
						<form class="searchform" method="get" action="SearchServlet">
							<input type="text" name="input" value="type and hit enter"
								onFocus="this.value=''" onBlur="this.value='type and hit enter'" />
								<label>Search in </label>
			                <select name="type">
							       <option value="users">users
							       <option value="posts">posts
							</select>
						</form>
					</li>
          <li><a href="main.jsp">Home</a>
		  <li ><a href="myProfile.jsp">My profile</a>
		  <li><a href="myAlbums.jsp">My Albums</a>
          </li>
          <li><a>Categories</a>
            <ul>
              <li><a href="CategoryServlet?category=abstract">Abstract</a></li>
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
          <li><a href="LogOutServlet">Log out</a></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</div>
<div class="wrapper">
 <%
    User user=UsersManager.getInstance().getUser(request.getAttribute("email").toString());
    %>
<div class="intro"><%=user.getName() %>'s profile</div>
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
<!--     <button onclick="document.getElementById('myImage').src='style/images/following.png'">Turn on the light</button> -->

<!-- <img id="myImage" src="style/images/follow.png" style="width:100px"> -->
    
    	<a href="FollowServlet?emaiToFollow=<%=user.getEmail()%>"><img src="style/images/follow.png" width="100" alt="" /></a><br>
      <div class="outer none"><span class="inset"><img src="PictureServlet?email=<%=user.getEmail()%>" alt=""></span></div>
      <h1>Followers</h1>
      <%for(String followerEmail: UsersManager.getInstance().getFollowersByUser(user.getEmail())){ %>
      <h4><a href="ProfileServlet?email=<%=followerEmail%>"><%=UsersManager.getInstance().getUser(followerEmail).getName() %></a></h4>
      <%} %>
      <h1>Following</h1>
      <%for(String followedEmail: UsersManager.getInstance().getFollowedByUser(user.getEmail())){ %>
      	<h4><a href="ProfileServlet?email=<%=followedEmail%>"><%=UsersManager.getInstance().getUser(followedEmail).getName() %></a></h4>
      <%} %>
    </div>
    
    <div id="first" class="widget-area"><br><br>
	    <h2><%=user.getName() %></h2>
		<h1><b>Email: </b><%=user.getEmail()%></h1>
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
	<div class="two-third last">
		<%
		for (Post post : PostDAO.getInstance().getAllPostsByUser(user.getEmail())) {
	%>
	 <div class="post format-image box" >
        <div class="frame"> <a href="DetailsServlet?postId=<%=post.getId()%>"><img src="PostPictureServlet?postId=<%=post.getId() %>"/></a> </div>
        <div class="details"> 
	       <span class="icon-artist"><a href="DetailsServlet?postId=<%=post.getId()%>"><%=post.getCreatedOn()%></a></span> 
	       <span class="likes"><a href="LikesServlet?postId=<%=post.getId()%>" class="likeThis" title="likes"> <%=PostDAO.getInstance().getNumberOfPostLikes(post.getId())%></a></span> 
	       <span class="likes"><a href="DislikeServlet?postId=<%=post.getId()%>" class="likeThis" title="dislikes" ><%=PostDAO.getInstance().getNumberOfPostDislikes(post.getId())%></a></span> 
	       <span class="comments"><a href="DetailsServlet?postId=<%=post.getId()%>"><%=post.getComments().size()%></a></span> 
        </div>
      </div>
      <%} %>
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
							<input type="text" name="input" value="type and hit enter"
								onFocus="this.value=''" onBlur="this.value='type and hit enter'" />
								<label>Search in </label>
			                <select name="type">
							       <option value="users">users
							       <option value="posts">posts
							</select>
						</form>
				</div>
				<div class="widget widget_archive">
					<h3 class="widget-title">Categories</h3>
					<ul>
						<li><a href="asbtract.jsp">Abstract</a>(<%=PostDAO.getInstance().getAllPostsByCategory("asbtract").size()%>)</li>
						<li><a href="animals.jsp">Animals</a>(<%=PostDAO.getInstance().getAllPostsByCategory("animals").size()%>)</li>
						<li><a href="family.jsp">Family</a>(<%=PostDAO.getInstance().getAllPostsByCategory("family").size()%>)</li>
						<li><a href="food.jsp">Food</a>(<%=PostDAO.getInstance().getAllPostsByCategory("food").size()%>)</li>
						<li><a href="nature.jsp">Nature</a>(<%=PostDAO.getInstance().getAllPostsByCategory("nature").size()%>)</li>
						<li><a href="people.jsp">People</a>(<%=PostDAO.getInstance().getAllPostsByCategory("people").size()%>)</li>
						<li><a href="sport.jsp">Sport</a>(<%=PostDAO.getInstance().getAllPostsByCategory("sport").size()%>)</li>
						<li><a href="travel.jsp">Travel</a>(<%=PostDAO.getInstance().getAllPostsByCategory("travel").size()%>)</li>
						<li><a href="urban.jsp">Urban</a>(<%=PostDAO.getInstance().getAllPostsByCategory("urban").size()%>)</li>
						<li><a href="uncategorized.jsp">Uncategorized</a>(<%=PostDAO.getInstance().getAllPostsByCategory("uncategorized").size()%>)</li>
					</ul>
				</div>
			</div>
			<div id="second" class="widget-area">
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">My followors</h3>
					<%for(String userEmail:UsersManager.getInstance().getFollowersByUser(session.getAttribute("USER").toString())){ %>
					<a href="ProfileServlet?email=<%=userEmail%>"><%=UsersManager.getInstance().getUser(userEmail).getName() %></a>
					<%} %>
				</div>
			</div>
			<div id="third" class="widget-area">
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">Users who follow</h3>
					<%for(String userEmail:UsersManager.getInstance().getFollowedByUser(session.getAttribute("USER").toString())) {%>
					<a href="ProfileServlet?email=<%=userEmail%>"><%=UsersManager.getInstance().getUser(userEmail).getName() %></a>
					<%} %>
				</div>
			</div>
			<div id="fourth" class="widget-area">
				<div id="example-widget-3" class="widget example">
				<h3 class="widget-title">Popular Posts</h3>
					<ul class="post-list">
						<li>
							<div class="frame">
								<a href="DetailsServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(0).getId()%>"><img
									src="PostPictureServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(0).getId()%>"
									alt="" width="80px"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="DetailsServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(0).getId()%>"><%=PostDAO.getInstance().getTopTenPosts().get(0).getName()%></a>
								</h6>
								<em><%=PostDAO.getInstance().getTopTenPosts().get(0).getCreatedOn()%></em>
							</div>
							</li>
							
						<li>
							<div class="frame">
								<a href="DetailsServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(1).getId()%>"><img
									src="PostPictureServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(1).getId()%>"
									alt="" width="80px"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="DetailsServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(1).getId()%>"><%=PostDAO.getInstance().getTopTenPosts().get(1).getName()%></a>
								</h6>
								<em><%=PostDAO.getInstance().getTopTenPosts().get(1).getCreatedOn()%></em>
							</div>
							</li>
						<li>
							<div class="frame">
								<a href="DetailsServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(2).getId()%>"><img
									src="PostPictureServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(2).getId()%>"
									alt="" width="80px"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="DetailsServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(2).getId()%>"></a>
								</h6>
								<h6>
									<a
										href="DetailsServlet?postId=<%=PostDAO.getInstance().getTopTenPosts().get(2).getId()%>"><%=PostDAO.getInstance().getTopTenPosts().get(2).getName()%></a>
								</h6>
								<em><%=PostDAO.getInstance().getTopTenPosts().get(2).getCreatedOn()%></em>
							</div>
				
				</li>
				</ul>
				</div>
			</div>
		</div>
	</div>
<script src="style/js/scripts.js"></script>
</body>
</html>