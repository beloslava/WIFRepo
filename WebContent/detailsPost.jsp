<%@page import="model.db.CommentDAO"%>
<%@page import="model.pojo.Comment"%>
<%@page import="model.db.PostDAO"%>
<%@page import="model.pojo.UsersManager"%>
<%@page import="model.pojo.Post"%>
<%@page import="model.pojo.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>My Wif</title>
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
<script type="text/javascript">
 function showhide(id) {
    var e = document.getElementById(id);
    e.style.display = (e.style.display == 'block') ? 'none' : 'block';
 }
</script>
</head>
<body class="single">
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
		  <li><a href="myProfile.jsp">My profile</a>
		  <li><a href="myAlbums.jsp">My Albums</a>
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
          <li><a href="LogOutServlet">Log out</a></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</div>
<div class="wrapper">
<%
    Post post = PostDAO.getInstance().getPost(Integer.parseInt(request.getAttribute("postId").toString()));
	User postUser = UsersManager.getInstance().getUser(post.getUserEmail());

	%>
  <div class="intro">
  <h1>"<%=post.getName()%>" created by <a href="ProfileServlet?email=<%=post.getUserEmail()%>"><%=postUser.getName() %></a></h1>
  </div>
  <div class="main-image">
    <div class="outer"> <span class="inset"><img src="PostPictureServlet?postId=<%=post.getId()%>"></span> </div>
  </div>
  <div class="content">

    <div class="post format-image box">
      <div class="details"> 
	      <span class="icon-date"><%=post.getCreatedOn()%></span> 
	      <span class="dislikes"><a href="DislikeServlet?postId=<%=post.getId()%>" class="likeThis"><%=PostDAO.getInstance().getNumberOfPostDislikes(post.getId())%></a></span> 
	      <span class="likes"><a href="LikesServlet?postId=<%=post.getId()%>" class="likeThis"><%=PostDAO.getInstance().getNumberOfPostLikes(post.getId())%></a></span>
	      <span class="comments"><%=post.getComments().size()%></span>
      </div>
      <div class="tags"><a href="#"><%=post.getKeyWords() %></a></div>
    </div>
    <div id="comment-wrapper" class="box">
      <div id="comments">
        <h3 id="comments-title"><%=post.getComments().size() %> Responses </h3>
        <ol id="singlecomments" class="commentlist">
          <%
          int x=0;
								for (Comment comment : CommentDAO.getInstance().takeAllCommentsByPost(post.getId())) {
									User user = UsersManager.getInstance().getUser(comment.getUserEmail());
									x++;
		%>
             
          <li class="comment">
            <div class="comment">
              <div class="comment-author vcard user frame"> 
                  <a href="ProfileServlet?email=<%=user.getEmail()%>">
                  <img src="PictureServlet?email=<%=user.getEmail()%>" class="avatar avatar-70 photo" height="70" width="70" alt="">
                  </a>
                  </div>
              <div class="message"> 
              	<span class="reply-link">
              		<a class="comment-reply-link" href="javascript:showhide('reply<%=x%>')">Reply</a>
              		<a class="comment-reply-link" href="LikeCommentServlet?commentId=<%=comment.getCommentId()%>&postId=<%=post.getId()%>">Like <%=CommentDAO.getInstance().getCommentLikes(comment.getCommentId())%></a>
              	</span>
                <div class="info">
                  <a href="ProfileServlet?email=<%=post.getUserEmail()%>"><h2><%=user.getName() %></h2></a>
                  <span class="meta"><%=comment.getCreatedOn() %></span> </div>
                <div class="comment-body ">
                  <p><%=comment.getText() %></p>
                </div>
              </div>
              
              <div class="clear"></div>
            </div>
            <div id="reply<%=x%>" style="display:none;">
        				<div id="comment-form" class="comment-form">
					        <div id="respond">
					          <h3 id="reply-title">Leave a Reply to <%=user.getName() %> comment</h3>
					          <form action="WriteCommentServlet" method="get" id="commentform">
					            <p class="comment-form-author">
					              <input id="author" name="postId" type="hidden" value="<%=post.getId() %>" size="30" aria-required="true">
					            </p>
					            <p class="comment-form-author">
					              <input id="author" name="parentCommentId" type="hidden" value="<%=comment.getCommentId()%>" size="30" aria-required="true">
					            </p>
					            <p class="comment-form-email">
					              <input id="email" name="email" type="hidden" value="<%=session.getAttribute("USER").toString() %>" size="30" aria-required="true">
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
 <%
								for (Comment reply : CommentDAO.getInstance().takeAllCommentsByComment(comment.getCommentId())) {
									User replyUser = UsersManager.getInstance().getUser(reply.getUserEmail());
		%>
            <ul class='children'>
              <li class="comment even depth-2" id="li-comment-5">
                <div id="comment-5" class="com-wrap">
                  <div class="comment-author vcard user frame"> 
                  <a href="ProfileServlet?email=<%=replyUser.getEmail()%>">
                  <img src="PictureServlet?email=<%=replyUser.getEmail()%>" class="avatar avatar-70 photo" height="70" width="70" alt="">
                  </a>
                  </div>
                  <div class="message"> 
                  <span class="reply-link">
              		<a class="comment-reply-link" href="LikeCommentServlet?commentId=<%=reply.getCommentId()%>&postId=<%=post.getId()%>">Like <%=CommentDAO.getInstance().getCommentLikes(reply.getCommentId())%></a>
              	  </span>
                    <div class="info">
                      <a href="ProfileServlet?email=<%=post.getUserEmail()%>"><h2><%=replyUser.getName() %></h2></a>
                      <span class="meta"><%=reply.getCreatedOn() %></span> </div>
                    <div class="comment-body ">
                      <p><%=reply.getText() %></p>
                    </div>
                    <span class="edit-link"></span> </div>
                  <div class="clear"></div>
                </div>
                <div class="clear"></div>
              </li>
            </ul>
          <%} %>
             <%} %>
          </li>
        </ol>
      </div>
      <div id="comment-form" class="comment-form">
        <div id="respond">
          <h3 id="reply-title">Leave a Reply</h3>
          <form action="WriteCommentServlet" method="get" id="commentform">
            <p class="comment-form-author">
              <input id="author" name="postId" type="hidden" value="<%=post.getId() %>" size="30" aria-required="true">
            </p>
            <p class="comment-form-author">
				<input id="author" name="parentCommentId" type="hidden" value="parent" size="30" aria-required="true">
			</p>
            <p class="comment-form-email">
              <input id="email" name="email" type="hidden" value="<%=session.getAttribute("USER").toString() %>" size="30" aria-required="true">
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
    <div class="sidebox widget">
      <h3 class="widget-title">likes <%=post.getLikes().size() %></h3>
      <%for(String user:PostDAO.getInstance().getAllLikesForPost(post.getId())){ %>
      <a href="ProfileServlet?email=<%=user%>"><%=UsersManager.getInstance().getUser(user).getName() %></a>
      <%} %>
    </div>
    <div class="sidebox widget">
      <h3 class="widget-title">dislikes <%=post.getDislikes().size() %></h3>
      <%for(String user:PostDAO.getInstance().getAllLikesForPost(post.getId())){ %>
      <a href="ProfileServlet?email=<%=user%>"><%=UsersManager.getInstance().getUser(user).getName() %></a>
      <%} %>
    </div>
  </div>
  <div class="clear"></div>
</div>
<div class="footer-wrapper">
		<div id="footer" class="four">
			<div id="first" class="widget-area">
				<div class="widget widget_search">
					<h3 class="widget-title">Search</h3>
					<form class="searchform" method="get" action="SearchServlet">
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
						<li><a href="CategoryServlet?category=animals">Animals</a>(<%=PostDAO.getInstance().getAllPostsByCategory("asbtract").size()%>)</li>
						<li><a href="CategoryServlet?category=email">Family</a>(<%=PostDAO.getInstance().getAllPostsByCategory("animals").size()%>)</li>
						<li><a href="CategoryServlet?category=food">Food</a>(<%=PostDAO.getInstance().getAllPostsByCategory("family").size()%>)</li>
						<li><a href="CategoryServlet?category=food">Food</a>(<%=PostDAO.getInstance().getAllPostsByCategory("food").size()%>)</li>
						<li><a href="CategoryServlet?category=nature">Nature</a>(<%=PostDAO.getInstance().getAllPostsByCategory("nature").size()%>)</li>
						<li><a href="CategoryServlet?category=people">People</a>(<%=PostDAO.getInstance().getAllPostsByCategory("people").size()%>)</li>
						<li><a href="CategoryServlet?category=sport">Sport</a>(<%=PostDAO.getInstance().getAllPostsByCategory("sport").size()%>)</li>
						<li><a href="CategoryServlet?category=travel">Travel</a>(<%=PostDAO.getInstance().getAllPostsByCategory("travel").size()%>)</li>
						<li><a href="CategoryServlet?category=urban">Urban</a>(<%=PostDAO.getInstance().getAllPostsByCategory("urban").size()%>)</li>
						<li><a href="CategoryServlet?category=uncategorized">Uncategorized</a>(<%=PostDAO.getInstance().getAllPostsByCategory("uncategorized").size()%>)</li>
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
							href="ProfileServlet?email=<c:out value="${followerEmail}"></c:out>>"
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
							href="ProfileServlet?email=<c:out value="${followedEmail}"></c:out>>"
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
									href="DetailsServlet?postId=<c:out value="${post.id}"></c:out>"><img
									src="PostPictureServlet?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="DetailsServlet?postId=<c:out value="${post.id}"></c:out>"><c:out
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
									href="DetailsServlet?postId=<c:out value="${post.id}"></c:out>"><img
									src="PostPictureServlet?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="DetailsServlet?postId=<c:out value="${post.id}"></c:out>"><c:out
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
									href="DetailsServlet?postId=<c:out value="${post.id}"></c:out>"><img
									src="PostPictureServlet?postId=<c:out value="${post.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="DetailsServlet?postId=<c:out value="${post.id}"></c:out>"><c:out
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
<script src="style/js/scripts.js"></script>
</body>
</html>