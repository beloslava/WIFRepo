<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyWif | Post details</title>
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/grids.css" type="text/css" media="all" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
	
</head>
<body>
   <div class="main">
	<div class="wrap">
		<div class="left-content">
			<div class="logo">
				<h1><a href="index.html"><img src="images/logo.png" alt="" /></a></h1>
			</div>
			<div class="menu">			
			  <ol id="filters">
			  	    <li class="home"><a href="index.html">Home</a></li>
			        <li class="photo"><a href="index.html">Photos</a></li>
			        <li class="video"><a href="index.html">Video</a></li>
			        <li class="music"><a href="index.html">Music</a></li>
			        <li class="blog"><a href="index.html">Blog</a></li>
			  </ol>
		</div>
	</div>
	
		<div class="right-content">
			<div class="header">
	   			<div class="social-icons">						
		                <ul>
		                    <li><a class="facebook" href="#" target="_blank"> </a></li>
		                    <li><a class="twitter" href="#" target="_blank"></a></li>
		                    <li><a class="googleplus" href="#" target="_blank"></a></li>
		                    <li><a class="pinterest" href="#" target="_blank"></a></li>
		                    <li><a class="dribbble" href="#" target="_blank"></a></li>
		                    <li><a class="vimeo" href="#" target="_blank"></a></li>
		                    <div class="clear"></div>
		                </ul>
		 		    </div>  	   
				   			<div class="search_box">
							    <form>
									<input type="text" class="text-box" placeholder="Search............."><input type="submit" value="">
							    </form>
						   </div>
			  		 <div class="clear"></div>
		  		 </div>
			
		<div class="content">
			<div class="box1">
			
   				 <h3><a href="details.html">User name</a></h3>
    				<span>upload date<span class="comments">comments</span></span> 
			   <div class="blog-img">
					<div class="view-back">
						<span class="views" title="views">(566)</span>
						<span class="likes" title="likes">(124)</span>
						<span class="link" title="link">(24)</span>
						<a href="#"> </a>
					</div>
					<img src="images/blog-img.jpg">
				</div>
				<div class="blog-data">
				</div>
			<div class="clear"></div>
		</div>			
			<!----------------  Comment Area -------------------->
		<div class="comments-area">
							<form>
								<p>
									<label>Comment</label>
									<textarea></textarea>
								</p>
								<p>
									<input type="submit" value="Post">
								</p>
							</form>		
						</div>
						<div class="box comment">
	    <h2><span>(0)</span> Comment's</h2>
	    <ul class="list">
	        <li>
	            <div class="preview"><a href="#"><img src="http://lorempixel.com/50/50" alt=""></a></div>
	            <div class="data">
	                <div class="title">user's name <a href="#"> date</a></div>
	                <p>Comment</p>
	            </div>
	            <div class="clear"></div>
	        </li>
	        
	    </ul>
	  <div class="clear"></div>
	</div>
	<div class="clear"> </div>
		<!----------------- End Comment Area ----------------->				
    </div>
  </div>
		<div class="clear"></div>
	</div>
</div>
</body>
</html>
