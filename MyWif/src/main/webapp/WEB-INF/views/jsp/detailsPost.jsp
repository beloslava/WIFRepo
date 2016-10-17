<%@page import="org.springframework.ui.Model"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.mywif.model.pojo.UsersManager"%>
<%@ page import="com.mywif.model.db.UserDAO"%>
<%@ page import="com.mywif.model.pojo.User"%>
<%@ page import="com.mywif.model.pojo.Post"%>
<%@ page import="com.mywif.model.db.PostDAO"%>
<%@ page import="com.mywif.model.db.CommentDAO"%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<%
	response.addHeader("Cache-Control",
			"no-cache,no-store,private,must-revalidate,max-stale=0,post-check=0,pre-check=0");
	response.addHeader("Pragma", "no-cache");
	response.addDateHeader("Expires", 0);
%>
<title>My Wif</title>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<link rel="stylesheet" type="text/css" href="css/style.css" media="all">
<link rel="stylesheet" type="text/css" href="css/media-queries.css">
<link rel="stylesheet" type="text/css"
	href="js/player/mediaelementplayer.css">
<link rel="stylesheet" type='text/css'
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,400italic,300italic,300,700,700italic|Open+Sans+Condensed:300,700'>
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
<script>
	$.backstretch("img/bg/1.jpg");
</script>
<script type="text/javascript">
	function showhide(id) {
		var e = document.getElementById(id);
		e.style.display = (e.style.display == 'block') ? 'none' : 'block';
	}
</script>
<script>
	function byId(e) {
		return document.getElementById(e);
	}
	window.addEventListener('load', mInit, false);
	function mInit() {
		var tgt = byId('image');
		tgt.secondSource = 'img/icon-liked.png';
	}
	function byId(e) {
		return document.getElementById(e);
	}
	function action() {
		var tgt = byId('image');
		var tmp = tgt.src;
		tgt.src = tgt.secondSource;
		tgt.secondSource = tmp;
	};
</script>

<script>
	function byId(e) {
		return document.getElementById(e);
	}
	window.addEventListener('load', mInit, false);
	function mInit() {
		var tgt = byId('image2');
		tgt.secondSource = 'img/icon-disliked.png';
	}
	function byId(e) {
		return document.getElementById(e);
	}
	function action2() {
		var tgt = byId('image2');
		var tmp = tgt.src;
		tgt.src = tgt.secondSource;
		tgt.secondSource = tmp;
	};
</script>

<script>
	function byId(e) {
		return document.getElementById(e);
	}
	window.addEventListener('load', mInit, false);
	function mInit() {
		var tgt = byId('image3');
		tgt.secondSource = 'img/icon-liked.png';
	}
	function byId(e) {
		return document.getElementById(e);
	}
	function action3() {
		var tgt = byId('image3');
		var tmp = tgt.src;
		tgt.src = tgt.secondSource;
		tgt.secondSource = tmp;
	};
</script>
</head>
<body class="single">
	<div class="scanlines"></div>
	<div class="header-wrapper opacity">
		<div class="header">
			<div class="logo">
				<a href="main"> <img src="img/logo.png" alt="">
				</a>
			</div>
			<div id="menu-wrapper">
				<div id="menu" class="menu">
					<ul id="tiny">
						<li>
							<form class="searchform" method="get" action="search">
								<input type="text" name="input" value="type and hit enter"
									onFocus="this.value=''" maxlength="50" required /> <label>Search
									in </label> <select name="type">
									<option value="posts">posts
									<option value="users">users
								</select>
							</form>
						</li>
						<li><a href="main">Home</a>
						<li><a href="myFollowedPosts">Feed</a>
						<li><a href="myProfile">My profile</a>
						<li><a href="myAlbums">My Albums</a></li>
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
							</ul></li>
						<li><a href="topTen">Top 10</a></li>
						<li><a href="logOut">Log out</a></li>
					</ul>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<div class="wrapper">
		<div class="intro">
			<h1>
				"
				<c:out value="${post.name}"></c:out>
				" created by <a
					href="detailsprofile?email=<c:out value="${post.userEmail}"></c:out>"><c:out
						value="${postUser.name}"></c:out></a>
			</h1>
		</div>
		<div class="main-image">
			<div class="outer">
				<span class="inset"><img
					src="picturepost?postId=<c:out value="${post.id}"></c:out>"></span>
			</div>
		</div>
		<div class="content">
			<div class="post format-image box">
				<div class="details">
					<span class="icon-date"><c:out value="${post.createdOn}"></c:out></span>
					<c:choose>
						<c:when
							test="${PostDAO.getInstance().isItDisliked(post.id,sessionScope.USER)}">
							<form action="postundislike" method="post">
								<input type="hidden" name="postId" value="${post.id}">
								<div class="likecomments">
									<input type="image" id="image3" src="img/icon-disliked.png"
										class="likepost" onclick="action2();" alt="Like"><span
										class="likecommentcount">&nbsp;<c:out
											value="${fn:length(post.dislikes)}"></c:out>&nbsp;&nbsp;
									</span>
								</div>
							</form>
						</c:when>
						<c:otherwise>
							<form action="postdislike" method="post">
								<input type="hidden" name="postId" value="${post.id}">
								<div class="likecomments">
									<input type="image" id="image3" src="img/icon-dislike.png"
										class="likepost" onclick="action2();" alt="Like"><span
										class="likecommentcount">&nbsp;<c:out
											value="${fn:length(post.dislikes)}"></c:out>&nbsp;&nbsp;
									</span>
								</div>
							</form>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when
							test="${PostDAO.getInstance().isItLiked(post.id,sessionScope.USER)}">
							<form action="postunlike" method="post">
								<input type="hidden" name="postId" value="${post.id}">
								<div class="likecomments">
									<input type="image" id="image" src="img/icon-liked.png"
										class="likepost" onclick="action2();" alt="Like"><span
										class="likecommentcount">&nbsp;<c:out
											value="${fn:length(post.likes)}"></c:out>&nbsp;&nbsp;
									</span>
								</div>
							</form>
						</c:when>
						<c:otherwise>
							<form action="postlike" method="post">
								<input type="hidden" name="postId" value="${post.id}">
								<div class="likecomments">
									<input type="image" id="image" src="img/icon-like.png"
										class="likepost" onclick="action2();" alt="Like"><span
										class="likecommentcount">&nbsp;<c:out
											value="${fn:length(post.likes)}"></c:out>&nbsp;&nbsp;
									</span>
								</div>
							</form>
						</c:otherwise>
					</c:choose>


					<span class="comments"><a href="javascript: void(0)"
						title="comments"><c:out value="${fn:length(post.comments)}"></c:out></a></span>



				</div>
				<div class="tags">
					<a><c:out value="${post.keyWords}"></c:out></a>
				</div>
			</div>
			<div id="comment-wrapper" class="box">
				<div id="comments">
					<h3 id="comments-title">
						<c:out value="${fn:length(post.comments)}"></c:out>
						Responses
					</h3>
					<ol id="singlecomments" class="commentlist">
						<c:set var="x" value="0" />
						<c:forEach var='comment' items='${comments}'>
							<c:set var="user"
								value="${UsersManager.getInstance().getUser(comment.userEmail)}" />
							<c:set var="x" value="${x+1}" />
							<c:set var="y" value="${y+1}" />
							<li class="comment">
								<div class="comment">
									<div class="comment-author vcard user frame">
										<a
											href="detailsprofile?email=<c:out value="${user.email}"></c:out>">
											<img
											src="pictureprofile?email=<c:out value="${user.email}"></c:out>"
											class="avatar avatar-70 photo" height="70" width="70" alt="">
										</a>
									</div>
									<div class="message">
										<div class="details">

											<span> <a class="comment-reply-link"
												href="javascript:showhide('reply<c:out value="${x}"></c:out>')">Reply</a>




												<c:choose>
													<c:when
														test="${CommentDAO.getInstance().isItLiked(comment.commentId,sessionScope.USER)}">
														<form action="commentunlike" method="post">
															<input type="hidden" name="commentId"
																value="${comment.commentId}"> <input
																type="hidden" name="postId" value="${post.id}">
															<div class="likecomments">
																<input type="image" id="image3" class="likecomment"
																	src="img/icon-liked.png" onclick="action3();"
																	alt="Like"> <span class="likecommentcount"><c:out
																		value="${CommentDAO.getInstance().getComment(comment.commentId).getCommentLikes().size()}"></c:out></span>
															</div>
														</form>
													</c:when>
													<c:otherwise>
														<form action="commentlike" method="post">
															<input type="hidden" name="commentId"
																value="${comment.commentId}"> <input
																type="hidden" name="postId" value="${post.id}">
															<div class="likecomments">
																<input type="image" id="image3" class="likecomment"
																	src="img/icon-like.png" onclick="action3();" alt="Like">
																<span class="likecommentcount"><c:out
																		value="${CommentDAO.getInstance().getComment(comment.commentId).getCommentLikes().size()}"></c:out></span>
															</div>
														</form>
													</c:otherwise>
												</c:choose>
											</span>
											<div class="info">
												<a
													href="detailsprofile?email=<c:out value="${user.email}"></c:out>"><h2>
														<c:out value="${user.name}"></c:out>
													</h2></a> <span class="meta"><c:out
														value="${comment.createdOn}"></c:out></span>
											</div>
											<div class="comment-body ">
												<p>
													<c:out value="${comment.text}"></c:out>
												</p>
											</div>
										</div>

										<div class="clear"></div>
									</div>
									<div id="reply<c:out value="${x}"></c:out>"
										style="display: none;">
										<div id="comment-form" class="comment-form">
											<div id="respond">
												<h3 id="reply-title">
													Leave a Reply to
													<c:out value="${user.name}"></c:out>
													comment
												</h3>
												<form action="commentwrite" method="post" id="commentform">
													<p class="comment-form-author">
														<input id="author" name="postId" type="hidden"
															value="<c:out value="${post.id}"></c:out>" size="30"
															aria-required="true">
													</p>
													<p class="comment-form-author">
														<input id="author" name="parentCommentId" type="hidden"
															value="<c:out value="${comment.commentId}"></c:out>"
															size="30" aria-required="true">
													</p>
													<p class="comment-form-email">
														<input id="email" name="email" type="hidden"
															value="<c:out value="${sessionScope.USER}"></c:out>"
															size="30" aria-required="true">
													</p>
													<p class="comment-form-comment">
														<label for="comment">Comment</label>
														<textarea id="comment" name="comment" cols="45" rows="8"
															aria-required="true" maxlength="450" required
															placeholder=" "></textarea>
													</p>
													<p class="form-submit">
														<input name="submit" type="submit" id="submit"
															value="Post Reply" class="btn-black">
													</p>
												</form>
											</div>
										</div>
									</div>
									<div class="clear"></div>
									<c:forEach var="reply"
										items="${CommentDAO.getInstance().takeAllCommentsByComment(comment.commentId)}">
										<c:set var="replyUser"
											value="${UsersManager.getInstance().getUser(reply.userEmail)}" />

										<ul class='children'>
											<li class="comment even depth-2" id="li-comment-5">
												<div id="comment-5" class="com-wrap">
													<div class="comment-author vcard user frame">
														<a
															href="detailsprofile?email=<c:out value="${replyUser.email}"></c:out>">
															<img
															src="pictureprofile?email=<c:out value="${replyUser.email}"></c:out>"
															class="avatar avatar-70 photo" height="70" width="70"
															alt="">
														</a>
													</div>
													<div class="message">
														<div class="details">
															<c:choose>
																<c:when
																	test="${CommentDAO.getInstance().isItLiked(reply.commentId,sessionScope.USER)}">
																	<form action="commentunlike" method="post">
																		<input type="hidden" name="commentId"
																			value="${reply.commentId}"> <input
																			type="hidden" name="postId" value="${post.id}">
																		<div class="likecomments">
																			<input type="image" id="image3" class="likecomment"
																				src="img/icon-liked.png" onclick="action3();"
																				alt="Like"> <span class="likecommentcount"><c:out
																					value="${CommentDAO.getInstance().getComment(reply.commentId).getCommentLikes().size()}"></c:out></span>
																		</div>
																	</form>
																</c:when>
																<c:otherwise>
																	<form action="commentlike" method="post">
																		<input type="hidden" name="commentId"
																			value="${reply.commentId}"> <input
																			type="hidden" name="postId" value="${postId}">
																		<div class="likecomments">
																			<input type="image" id="image3" class="likecomment"
																				src="img/icon-like.png" onclick="action3();"
																				alt="Like"> <span class="likecommentcount"><c:out
																					value="${CommentDAO.getInstance().getComment(reply.commentId).getCommentLikes().size()}"></c:out></span>
																		</div>
																	</form>
																</c:otherwise>
															</c:choose>
														</div>

														<div class="info">
															<a
																href="detailsprofile?email=<c:out value="${replyUser.email}"></c:out>"><h2>
																	<c:out value="${replyUser.name}"></c:out>
																</h2></a> <span class="meta"><c:out
																	value="${reply.createdOn}"></c:out></span>
														</div>
														<div class="comment-body ">
															<p>
																<c:out value="${reply.text}"></c:out>
															</p>
														</div>
														<span class="edit-link"></span>
													</div>
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
						<h3 id="reply-title">Leave a Comment</h3>
						<form action="commentwrite" method="post" id="commentform">
							<p class="comment-form-author">
								<input id="author" name="postId" type="hidden"
									value="<c:out value="${post.id}"></c:out>" size="30"
									aria-required="true">
							</p>
							<p class="comment-form-author">
								<input id="author" name="parentCommentId" type="hidden"
									value="parent" size="30" aria-required="true">
							</p>
							<p class="comment-form-email">
								<input id="email" name="email" type="hidden"
									value="<c:out value="${sessionScope.USER}"></c:out>" size="30"
									aria-required="true">
							</p>
							<p class="comment-form-comment">
								<label for="comment">Comment</label>
								<textarea id="comment" name="comment" cols="45" rows="8"
									aria-required="true" maxlength="450" required placeholder=" "></textarea>
							</p>
							<p class="form-submit">
								<input name="submit" type="submit" id="submit"
									value="Post Comment">
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
						onFocus="this.value=''" maxlength="50" required /> <label>Search
						in </label> <select name="type">
						<option value="posts">posts
						<option value="users">users
					</select>
				</form>
			</div>
			<div class="sidebox widget">
				<h3 class="widget-title">
					People who like this
					<c:out value="${fn:length(post.likes)}"></c:out>
				</h3>
				<ul>
					<c:forEach var="userEmail"
						items="${PostDAO.getInstance().getLikesForPost(post.id)}">
						<c:set var="userName"
							value="${UsersManager.getInstance().getUser(userEmail).name}" />
						<li><a href="detailsprofile?email=<c:out value="${userEmail}"></c:out>"
							title="author name"><c:out value="${userName}"></c:out></a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="sidebox widget">
				<h3 class="widget-title">
					People who dislike this
					<c:out value="${fn:length(post.dislikes)}"></c:out>
				</h3>
				<ul>
					<c:forEach var="userEmail"
						items="${PostDAO.getInstance().getDislikesForPost(post.id)}">
						<c:set var="userName"
							value="${UsersManager.getInstance().getUser(userEmail).name}" />
						<li><a
							href="detailsprofile?email=<c:out value="${userEmail}"></c:out>"
							title="author name"><c:out value="${userName}"></c:out></a></li>
					</c:forEach>
				</ul>
			</div>
			<div class="sidebox widget">
				<c:choose>
					<c:when test="${sessionScope.USER==post.userEmail}">
						<span>
							<form class="forms" action="deletepost" method="post">
								<fieldset>
									<ol>
										<li class="button-row"><input type="hidden" name="postId"
											value="<c:out value="${post.id}"></c:out>"> <input
											type="submit" name="deletePost" value="delete"
											class="btn-black"></li>
									</ol>
								</fieldset>
							</form>
						</span>
					</c:when>
				</c:choose>
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
							onFocus="this.value=''" maxlength="50" required /> <label>Search
							in </label> <select name="type">
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
						<li class="active"><a href="category?category=abstract">Abstract</a>(<c:out
								value="${abstractPosts}"></c:out>)</li>
						<li><a href="category?category=animals">Animals</a>(<c:out
								value="${animalsPosts}"></c:out>)</li>
						<li><a href="category?category=family">Family</a>(<c:out
								value="${familyPosts}"></c:out>)</li>
						<li><a href="category?category=food">Food</a>(<c:out
								value="${foodPosts}"></c:out>)</li>
						<li><a href="category?category=nature">Nature</a>(<c:out
								value="${naturePosts}"></c:out>)</li>
						<li><a href="category?category=people">People</a>(<c:out
								value="${peoplePosts}"></c:out>)</li>
						<li><a href="category?category=sport">Sport</a>(<c:out
								value="${sportPosts}"></c:out>)</li>
						<li><a href="category?category=travel">Travel</a>(<c:out
								value="${travelPosts}"></c:out>)</li>
						<li><a href="category?category=urban">Urban</a>(<c:out
								value="${urbanPosts}"></c:out>)</li>
						<li><a href="category?category=uncategorized">Uncategorized</a>(<c:out
								value="${uncategorizedPosts}"></c:out>)</li>
					</ul>
				</div>

			</div>
			<div id="third" class="widget-area">
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">Followers</h3>
					<c:set var="user" value="${sessionScope.USER}" />
					<ul>
						<c:forEach var='followerEmail'
							items='${UsersManager.getInstance().getFollowersByUser(user)}'
							end="5">
							<c:set var="userName"
								value="${UsersManager.getInstance().getUser(followerEmail).name}" />
							<li><a
								href="detailsprofile?email=<c:out value="${followerEmail}"></c:out>"
								title="author name"><c:out value="${userName}"></c:out></a></li>
						</c:forEach>
					</ul>
				</div>
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">Following</h3>
					<ul>
						<c:forEach var='followedEmail'
							items='${UsersManager.getInstance().getFollowedByUser(user)}'
							end="5">
							<c:set var="userName"
								value="${UsersManager.getInstance().getUser(followedEmail).name}" />
							<li><a
								href="detailsprofile?email=<c:out value="${followedEmail}"></c:out>"
								title="author name"><c:out value="${userName}"></c:out></a></li>
						</c:forEach>
					</ul>
				</div>
			</div>
			<div id="fourth" class="widget-area">
				<div id="example-widget-3" class="widget example">
					<h3 class="widget-title">Popular Posts</h3>
					<ul class="post-list">
						<li>
							<div class="frame">
								<c:set var="topPost"
									value="${PostDAO.getInstance().getTopTenPosts()[0]}"
									scope="session" />
								<a
									href="detailspost?postId=<c:out value="${topPost.id}"></c:out>"><img
									src="picturepost?postId=<c:out value="${topPost.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="detailspost?postId=<c:out value="${topPost.id}"></c:out>"><c:out
											value="${topPost.name}"></c:out></a>
								</h6>
								<em><c:out value="${topPost.createdOn}"></c:out></em>
							</div>
						</li>

						<li>
							<div class="frame">

								<c:set var="topPost"
									value="${PostDAO.getInstance().getTopTenPosts()[1]}"
									scope="session" />
								<a
									href="detailspost?postId=<c:out value="${topPost.id}"></c:out>"><img
									src="picturepost?postId=<c:out value="${topPost.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="detailspost?postId=<c:out value="${topPost.id}"></c:out>"><c:out
											value="${topPost.name}"></c:out></a>
								</h6>
								<em><c:out value="${topPost.createdOn}"></c:out></em>
							</div>
						</li>
						<li>
							<div class="frame">

								<c:set var="topPost"
									value="${PostDAO.getInstance().getTopTenPosts()[2]}"
									scope="session" />
								<a
									href="detailspost?postId=<c:out value="${topPost.id}"></c:out>"><img
									src="picturepost?postId=<c:out value="${topPost.id}"></c:out>"
									alt="" height="60"></a>
							</div>
							<div class="meta">
								<h6>
									<a
										href="detailspost?postId=<c:out value="${topPost.id}"></c:out>"><c:out
											value="${topPost.name}"></c:out></a>
								</h6>
								<em><c:out value="${topPost.createdOn}"></c:out></em>
							</div>

						</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="site-generator-wrapper"></div>
	<script src="js/scripts.js"></script>
	<script type="text/javascript">
		$('.likes').click(function() {
			$('#likepost').submit();
		})
	</script>
	<script type="text/javascript">
		$('.dislikes').click(function() {
			$('#dislikepost').submit();
		})
	</script>
	<script type="text/javascript">
		$('#likereply').click(function() {
			$('#replyreply').submit();
		})
	</script>
	<script type="text/javascript">
		// 	 $('#likecomment').click(function(e){
		// 		 debugger;
		// 		 $('#commentcomment').submit();
		// 	 })
		function likeComment(e) {
			console.log(e);
			// 			$('#commentcomment').submit();

		}
	</script>
</body>
</html>