<!DOCTYPE HTML>
<html lang="en">
<head>
<title>My Wif</title>
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
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
    <div class="logo"> <a href="index.html"> <img src="images/logo.png" width="30%" alt=""> </a> </div>
    <div id="menu-wrapper">
      <div id="menu" class="menu">
        <ul id="tiny">
          <li><a href="index.html">Home</a>
		  <li><a href="myProfile.jsp">My profile</a>
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
          <li class="active"><a href="upload.jsp">Upload</a></li>
        </ul>
      </div>
    </div>
    <div class="clear"></div>
  </div>
</div>
<div class="wrapper">
  <ul class="social">
    <li><a class="rss" href="#"></a></li>
    <li><a class="facebook" href="#"></a></li>
    <li><a class="twitter" href="#"></a></li>
    <li><a class="pinterest" href="#"></a></li>
    <li><a class="dribbble" href="#"></a></li>
    <li><a class="flickr" href="#"></a></li>
    <li><a class="linkedin" href="#"></a></li>
  </ul>
  <div class="content box">
    <h1 class="title">Upload</h1>
    <h3>Feel Free to Drop Me a Line</h3>
    <div class="form-container">
      <form class="forms" action="UploadPostServlet" method="post" enctype="multipart/form-data">
        <fieldset>
          <ol>
            <li class="form-row text-input-row">
              <label>Say smth about your post</label>
              <input type="text" name="nameOfPost" value="" class="text-input required">
            </li>
            <li class="form-row text-input-row">
              <input type="hidden" name="email" value="<%=session.getAttribute("USER").toString() %>" class="text-input required">
            </li>
            <li class="form-row text-input-row">
              <label>Category</label>
              <input list="categories" name="category">
			  <datalist id="categories">
			    <option value="abstract">
			    <option value="animals">
			    <option value="family">
			    <option value="food">
			    <option value="nature">
			    <option value="people">
			    <option value="sport">
			    <option value="travel">
			    <option value="urban">
			    <option value="uncategorized">
			  </datalist>
            </li>
            <li class="form-row text-input-row">
              <label>Key words</label>
              <input type="text" name="keyWords" value="" class="text-input required">
            </li>
            <li class="form-row text-area-row">
              <label>Upload your photo</label>
              <input type="file" name="fileField" class="text-input required"></textarea>
            </li>
            <li class="button-row">
              <input type="submit" value="Upload your post" name="submit" class="btn-submit">
            </li>
          </ol>
        </fieldset>
      </form>
    </div>
  </div>
  <div class="sidebar box">
    <div class="sidebox widget">
      <p>"Nothing happens when you sit at home. I always make it a point to carry a camera with me at all times... I just shoot at whta interests me at that moment." Elliott Erwitt</p>
    </div>
    <div class="sidebox widget">
      <p>"Beauty can be seen in all things, seeing and composing the beaty is what separates the snapshot from the photograph." Matt Hardy</p>
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
<script src="style/js/scripts.js"></script>
</body>
</html>