<%@page import="model.db.CommentDAO"%>
<%@page import="model.pojo.Comment"%>
<%@page import="model.db.PostDAO"%>
<%@page import="model.pojo.UsersManager"%>
<%@page import="model.pojo.Post"%>
<%@page import="model.pojo.User"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>Obscura | Blog Post</title>
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
    <div class="logo"> <a href="index.html"> <img src="style/images/logo.png" alt=""> </a> </div>
    <div id="menu-wrapper">
      <div id="menu" class="menu">
       <ul id="tiny">
          <li><a href="index.html">Home</a>
		  <li><a href="myProfile.jsp">My profile</a>
		  <li><a href="myPhotos.jsp">My photos</a>
          </li>
          <li><a>Categories</a>
            <ul>
              <li><a href="abstract.jsp">Abstract</a></li>
              <li><a href="animals.jsp">Animals</a></li>
			  <li><a href="family.jsp">Family</a></li>
			  <li><a href="food.jsp">Food</a></li>
              <li><a href="nature.jsp">Nature</a></li>
			  <li><a href="people.jsp">People</a></li>
			  <li><a href="sport.jsp">Sport</a></li>
			  <li><a href="travel.jsp">Travel</a></li>
			  <li><a href="urban.jsp">Urban</a></li>
			  <li><a href="uncategorized.jsp">Uncategorized</a></li>
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
  <div class="intro">Nulla vitae elit libero, a pharetra augue. Vivamus sagittis lacus augue laoreet rutrum faucibus dolor auctor. Cras mattis consectetur purus sit amet fermentum, Vestibulum id ligula porta. </div>
  <ul class="social">
    <li><a class="rss" href="#"></a></li>
    <li><a class="facebook" href="#"></a></li>
    <li><a class="twitter" href="#"></a></li>
    <li><a class="pinterest" href="#"></a></li>
    <li><a class="dribbble" href="#"></a></li>
    <li><a class="flickr" href="#"></a></li>
    <li><a class="linkedin" href="#"></a></li>
  </ul>
  <div class="main-image">
  
    <%
    Post post = PostDAO.getInstance().getPost(Integer.parseInt(request.getAttribute("postId").toString()));
	User user = UsersManager.getInstance().getUser(post.getUserEmail());

	%>
    <div class="outer"> <span class="inset"><img src="PostPictureServlet?postId=<%=post.getId()%>"></span> </div>
  </div>
  <div class="content">

    <div class="post format-image box">
      <div class="details"> 
	      <span class="icon-image"><%=post.getCreatedOn()%></span> 
	      <span class="likes">
<%-- 	      	<a href="#" class="likeThis"><%=post.get %></a> --%>
	      </span> 
	      <span class="comments">
	      	<a href="#"><%=post.getComments().size()%></a>
	      </span>
      </div>
      <h1 class="title"><%=post.getName() %></h1>
      <div class="tags"><a href="#"><%=post.getKeyWords() %></a></div>
    </div>
    <div id="comment-wrapper" class="box">
      <div id="comments">
        <h3 id="comments-title"><%=post.getComments().size() %> Responses </h3>
        <ol id="singlecomments" class="commentlist">
          <%
								for (Comment c : CommentDAO.getInstance().takeAllCommentsByPost(post.getId())) {
									User u = UsersManager.getInstance().getUser(c.getUserEmail());
		%>
             
          <li class="comment">
            <div class="comment">
              <div class="comment-author vcard user frame"> 
              	<img src="PictureServlet?email=<%=u.getEmail()%>" class="avatar avatar-70 photo" height="70" width="70" alt="">
              </div>
              <div class="message"> 
              	<span class="reply-link">
              		<a class="comment-reply-link" href="javascript:showhide('uniquename')">Reply</a>
              	</span>
                <div class="info">
                  <h2><%=u.getName() %></h2>
                  <span class="meta"><%=c.getCreatedOn() %></span> </div>
                <div class="comment-body ">
                  <p><%=c.getText() %></p>
                </div>
                <span class="edit-link"></span> </div>
              <div class="clear"></div>
            </div>
            <div id="uniquename" style="display:none;">
        				<div id="comment-form" class="comment-form">
					        <div id="respond">
					          <h3 id="reply-title">Leave a Reply to <%=u.getName() %> comment</h3>
					          <form action="WriteCommentServlet" method="get" id="commentform">
					            <p class="comment-form-author">
					              <input id="author" name="postId" type="hidden" value="<%=post.getId() %>" size="30" aria-required="true">
					            </p>
					            <p class="comment-form-author">
					              <input id="author" name="parentCommentId" type="hidden" value="<%=c.getCommentId()%>" size="30" aria-required="true">
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

<!--             <ul class='children'> -->
<!--               <li class="comment even depth-2" id="li-comment-5"> -->
<!--                 <div id="comment-5" class="com-wrap"> -->
<!--                   <div class="comment-author vcard user frame"> <img src="style/images/avatar.jpg" class="avatar avatar-70 photo" height="70" width="70" alt=""></div> -->
<!--                   <div class="message"> <span class="reply-link"><a class="comment-reply-link" href="#">Reply</a></span> -->
<!--                     <div class="info"> -->
<!--                       <h2>Jason</h2> -->
<!--                       <span class="meta"> September 13, 2045 at 1:27 pm </span> </div> -->
<!--                     <div class="comment-body "> -->
<!--                       <p>Duis mollis, est non commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem nec elit. Aenean eu leo quam. Pellentesque ornare sem lacinia quam venenatis vestibulum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh, ut fermentum massa justo sit amet risus. Curabitur blandit tempus porttitor.</p> -->
<!--                     </div> -->
<!--                     <span class="edit-link"></span> </div> -->
<!--                   <div class="clear"></div> -->
<!--                 </div> -->
<!--                 <div class="clear"></div> -->
<!--               </li> -->
<!--             </ul> -->
         
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
    <div class="sidebox widget">
      <h3 class="widget-title">Search</h3>
      <form class="searchform" method="get" action="#">
        <input type="text" name="s" value="type and hit enter" onFocus="this.value=''" onBlur="this.value='type and hit enter'"/>
      </form>
    </div>
    <div class="sidebox widget">
      <h3 class="widget-title">Custom Text</h3>
      <div>Suspendisse eu odio quis elit ultrice commodo tempor eget arcu. Sedur aliquet posuere lectus aliquam iaculi. Curabitur a risus metus. In ut lorem nisl, et adipiscing sapien. Donec sed risus tristiq scelerisque. </div>
    </div>
    <div class="sidebox widget">
      <h3 class="widget-title">Categories</h3>
      <ul>
        <li><a href="#">Detektivbyrån</a></li>
        <li><a href="#">Flowers</a></li>
        <li><a href="#">Funny</a></li>
        <li><a href="#">Journal</a></li>
        <li><a href="#">Landscape</a></li>
        <li><a href="#">Nature</a></li>
        <li><a href="#">Photography</a></li>
        <li><a href="#">Video</a> </li>
      </ul>
    </div>
  </div>
  <div class="clear"></div>
</div>
<div class="footer-wrapper">
  <div id="footer" class="four">
    <div id="first" class="widget-area">
      <div class="widget widget_search">
        <h3 class="widget-title">Search</h3>
        <form class="searchform" method="get" action="#">
          <input type="text" name="s" value="type and hit enter" onFocus="this.value=''" onBlur="this.value='type and hit enter'"/>
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
<div class="site-generator-wrapper"></div>
<script src="style/js/scripts.js"></script>
</body>
</html>