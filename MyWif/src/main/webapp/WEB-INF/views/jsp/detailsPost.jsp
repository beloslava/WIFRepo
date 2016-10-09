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
<body class="single">
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
								onFocus="this.value=''" onBlur="this.value='type and hit enter'" />
								<label>Search in </label>
			                <select name="type">
							       <option value="users">users
							       <option value="posts">posts
							</select>
						</form>
					</li>
          <li><a href="main">Home</a>
		  <li><a href="myProfile">My profile</a>
		  <li><a href="myAlbums">My Albums</a>
          </li>
          <li><a>Categories</a>
           <ul>
              <li class="active"><a href="category?category=abstract">Abstract</a></li>
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
 <c:set var="post" value="${PostDAO.getInstance().getPost(param.postId)}" scope="page"/>   
   <c:set var="postUser" value="${UsersManager.getInstance().getUser(post.userEmail)}"/>
  <div class="intro">
  <h1>"<c:out value="${post.name}"></c:out>" created by <a href="picture/profile?email=<c:out value="${post.userEmail}"></c:out>"><c:out value="${postUser.name}"></c:out></a></h1>
  </div>
  <div class="main-image">
    <div class="outer"> <span class="inset"><img src="picture/post?postId=<c:out value="${post.id}"></c:out>"></span> </div>
  </div>
  <div class="content">

    <div class="post format-image box">
      <div class="details"> 
	      <span class="icon-date"><c:out value="${post.createdOn}"></c:out></span> 
	      <span class="dislikes"><a href="post/dislike?postId=<c:out value="${post.id}"></c:out>" class="likeThis"><c:out value="${fn:length(post.dislikes)}"></c:out></a></span> 
	      <span class="likes"><a href="post/like?postId=<c:out value="${post.id}"></c:out>" class="likeThis"><c:out value="${fn:length(post.likes)}"></c:out></a></span>
	      <span class="comments"><c:out value="${fn:length(post.comments)}"></c:out></span>
      </div>
      <div class="tags"><a href="#"><c:out value="${post.keyWords}"></c:out></a></div>
    </div>
    <div id="comment-wrapper" class="box">
      <div id="comments">
        <h3 id="comments-title"><c:out value="${fn:length(post.comments)}"></c:out> Responses </h3>
        <ol id="singlecomments" class="commentlist">
           <c:set var="x" value="0"/>
		 <c:forEach var='comment' items='${CommentDAO.getInstance().getAllCommentsByPost(post.id)}'>		 
		 	   <c:set var="user" value="${UsersManager.getInstance().getUser(comment.userEmail)}"/>		
			   <c:set var="x" value="${1+x}"/>
          <li class="comment">
            <div class="comment">
              <div class="comment-author vcard user frame"> 
                  <a href="picture/profile?email=<c:out value="${user.email}"></c:out>">
                  <img src="picture/profile?email=<c:out value="${user.email}"></c:out>" class="avatar avatar-70 photo" height="70" width="70" alt="">
                  </a>
                  </div>
              <div class="message"> 
              	<span class="reply-link">
              		<a class="comment-reply-link" href="javascript:showhide('reply<c:out value="${x}"></c:out>')">Reply</a>
              		<a class="comment-reply-link" href="comment/like?commentId=<c:out value="${comment.commentId}"></c:out>&postId=<c:out value="${post.id}"></c:out>">Like <c:out value="${fn:length(comment.commentLikes)}"></c:out></a>
              	</span>
                <div class="info">
                  <a href="picture/profile?email=<c:out value="${post.userEmail}"></c:out>"><h2><c:out value="${user.name}"></c:out></h2></a>
                  <span class="meta"><c:out value="${comment.createdOn}"></c:out></span> </div>
                <div class="comment-body ">
                  <p><c:out value="${comment.text}"></c:out></p>
                </div>
              </div>
              
              <div class="clear"></div>
            </div>
            <div id="reply<c:out value="${x}"></c:out>" style="display:none;">
        				<div id="comment-form" class="comment-form">
					        <div id="respond">
					          <h3 id="reply-title">Leave a Reply to <c:out value="${user.name}"></c:out> comment</h3>
					          <form action="comment/write" method="get" id="commentform">
					            <p class="comment-form-author">
					              <input id="author" name="postId" type="hidden" value="<c:out value="${post.id}"></c:out>" size="30" aria-required="true">
					            </p>
					            <p class="comment-form-author">
					              <input id="author" name="parentCommentId" type="hidden" value="<c:out value="${comment.commentId}"></c:out>" size="30" aria-required="true">
					            </p>
					            <p class="comment-form-email">
					              <input id="email" name="email" type="hidden" value="<c:out value="${sessionScope.USER}"></c:out>" size="30" aria-required="true">
					            </p>
					            <p class="comment-form-comment">
					              <label for="comment">Comment</label>
					              <textarea id="comment" name="comment" cols="45" rows="8" aria-required="true"></textarea>
					            </p>
					            <p class="form-submit">
					              <input name="submit" type="submit" id="submit" value="Post Comment">
					            </p>
					          </form>
					        </div>
					      </div>
    				</div>
            <div class="clear"></div>
<c:forEach var='reply' items='${CommentDAO.getInstance().takeAllCommentsByComment(comment.commentId)}'>
		 	   <c:set var="replyUser" value="${UsersManager.getInstance().getUser(reply.userEmail)}"/>
		 	
                       <ul class='children'>
              <li class="comment even depth-2" id="li-comment-5">
                <div id="comment-5" class="com-wrap">
                  <div class="comment-author vcard user frame"> 
                  <a href="picture/profile?email=<c:out value="${replyUser.email}"></c:out>">
                  <img src="picture/profile?email=<c:out value="${replyUser.email}"></c:out>" class="avatar avatar-70 photo" height="70" width="70" alt="">
                  </a>
                  </div>
                  <div class="message"> 
                  <span class="reply-link">
              		<a class="comment-reply-link" href="comment/like?commentId=<c:out value="${reply.commentId}"></c:out>&postId=<c:out value="${post.id}"></c:out>">Like <c:out value="${fn:length(comment.commentLikes)}"></c:out></a>
              	  </span>
                    <div class="info">
                      <a href="picture/profile?email=<c:out value="${post.userEmail}"></c:out>"><h2><c:out value="${replyUser.name}"></c:out></h2></a>
                      <span class="meta"><c:out value="${reply.createdOn}"></c:out></span> </div>
                    <div class="comment-body ">
                      <p><c:out value="${reply.text}"></c:out></p>
                    </div>
                    <span class="edit-link"></span> </div>
                  <div class="clear"></div>
                </div>
                <div class="clear"></div>
              </li>
            </ul>
            </c:forEach>
          
          </c:forEach>
          </li>
        </ol>
      </div>
      <div id="comment-form" class="comment-form">
        <div id="respond">
          <h3 id="reply-title">Leave a Reply</h3>
          <form action="comment/write" method="get" id="commentform">
            <p class="comment-form-author">
              <input id="author" name="postId" type="hidden" value="<c:out value="${post.id}"></c:out>" size="30" aria-required="true">
            </p>
            <p class="comment-form-author">
				<input id="author" name="parentCommentId" type="hidden" value="parent" size="30" aria-required="true">
			</p>
            <p class="comment-form-email">
<%--               <input id="email" name="email" type="hidden" value="<%=session.getAttribute("USER").toString() %>" size="30" aria-required="true"> --%>
            </p>
            <p class="comment-form-comment">
              <label for="comment">Comment</label>
              <textarea id="comment" name="comment" cols="45" rows="8" aria-required="true"></textarea>
            </p>
            <p class="form-submit">
              <input name="submit" type="submit" id="submit" value="Post Comment">
            </p>
          </form>
        </div>
      </div>
    </div>
  </div>
  <div class="sidebar box">
  <div class="sidebox widget">
      <h3 class="widget-title">Search</h3>
       <form class="searchform" method="get" action="search">
							<input type="text" name="input" value="type and hit enter"
								onFocus="this.value=''" onBlur="this.value='type and hit enter'" />
								<label>Search in </label>
			                <select name="type">
							       <option value="users">users
							       <option value="posts">posts
							</select>
						</form>
    </div>
   <div class="sidebox widget">
      <h3 class="widget-title">likes <c:out value="${fn:length(post.likes)}"></c:out></h3>  
    </div>
    <div class="sidebox widget">
      <h3 class="widget-title">dislikes <c:out value="${fn:length(post.dislikes)}"></c:out></h3>
    </div>
  </div>
  <div class="clear"></div>
</div>
<div class="footer-wrapper">
		<div id="footer" class="four">
			<div id="first" class="widget-area">
				<div class="widget widget_search">
					<h3 class="widget-title">Search</h3>
					<form class="searchform" method="get" action="search">
						<input type="text" name="input" value="type and hit enter"
							onFocus="this.value=''" onBlur="this.value='type and hit enter'" />
						<label>Search in </label> <select name="type">
							<option value="users">users

								<option value="posts">posts
						
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
							href="picture/profile?email=<c:out value="${followerEmail}"></c:out>>"
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
							href="picture/profile?email=<c:out value="${followedEmail}"></c:out>>"
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
									href="details/post?postId=<c:out value="${post.id}"></c:out>"><img
									src="picture/post?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="details/post?postId=<c:out value="${post.id}"></c:out>"><c:out
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
									href="details/post?postId=<c:out value="${post.id}"></c:out>"><img
									src="picture/post?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="details/post?postId=<c:out value="${post.id}"></c:out>"><c:out
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
									href="details/post?postId=<c:out value="${post.id}"></c:out>"><img
									src="picture/post?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="details/post?postId=<c:out value="${post.id}"></c:out>"><c:out
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
<div class="site-generator-wrapper"></div>
<script src="js/scripts.js"></script>
</body>
</html>