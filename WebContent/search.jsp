<!DOCTYPE HTML>
<%@page import="model.pojo.UsersManager"%>
<%@ page import="model.pojo.User"%>
<%@page import="java.util.ArrayList" %>
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
          <li><a href="index.html">Blog</a>
            <ul>
              <li><a href="post.html">Blog Post</a></li>
            </ul>
          </li>
          <li class="active"><a href="page-with-sidebar.html">Pages</a>
            <ul>
              <li><a href="page-with-sidebar.html">Page With Sidebar</a></li>
              <li><a href="full-width.html">Full Width</a></li>
            </ul>
          </li>
          <li><a href="typography.html">Styles</a>
            <ul>
              <li><a href="typography.html">Typography</a></li>
              <li><a href="columns.html">Columns</a></li>
            </ul>
          </li>
          <li><a href="contact.html">Contact</a></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</div>
<div class="wrapper">
  <div class="intro">Search results for "<%=request.getAttribute("userName") %>" <%=request.getAttribute("count") %> :</div>
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
    <div class="two-third last">
    <%
    ArrayList<User> users=(ArrayList<User>)request.getAttribute("users");
    for(User user: users){
    %>
   <a href="ProfileServlet?email=<%=user.getEmail()%>"><img alt="" src="PictureServlet?email=<%=user.getEmail()%>" width="100px"><%=user.getName() %></a>

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
  <div class="site-generator">Copyright Obscura 2045. All rights reserved // Design by <a href="http://elemisfreebies.com">elemis</a>.</div>
</div>
<script src="style/js/scripts.js"></script>
</body>
</html>