<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="model.pojo.UsersManager"%>
<%@ page import="model.db.UserDAO"%>
<%@ page import="model.pojo.User"%>
<%@ page import="model.pojo.Post"%>
<%@ page import="model.db.PostDAO"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MyWif | Fun</title>
<link rel="shortcut icon" href="images/logo.png" type="image/x-icon">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link rel="stylesheet" href="css/grids.css" type="text/css" media="all" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<!------ Light Box ------>
<link rel="stylesheet" href="css/swipebox.css">
<script src="js/ios-orientationchange-fix.js"></script>
<script src="js/jquery.swipebox.min.js"></script>
<script type="text/javascript">
	jQuery(function($) {
		$(".swipebox").swipebox();
	});
</script>
<!------ Eng Light Box ------>
</head>
<body>
	<div class="main">
		<div class="wrap">
			<div class="left-content">
				<div class="logo">
					<h1>
						<a href="Main.jsp"><img src="images/logo.png" alt="" /></a>
					</h1>
				</div>
				<div class="menu">
					<ol id="filters">
						<li class="home"><a href="main.jsp">Home</a></li>
						<li class="video"><a href="myProfile.jsp">My Profile</a></li>
						<li class="photo"><a href="myPhotos.jsp">My Photos</a></li>
						<li class="photo"><a href="topTen.jsp">Top 10</a></li>
						<li class="photo"><a href="people.jsp">People</a></li>
						<li class="photo"><a href="nature.jsp">Nature</a></li>
						<li class="photo"><a href="fun.jsp">Fun</a></li>
						<li class="photo"><a href="pets.jsp">Pets</a></li>
					</ol>
				</div>
			</div>
			<div class="right-content">
				<div class="header">
					<div class="social-icons">
						<ul>
							<li><a class="upload" href="upload.html"></a>
							<li>
							<li><a class="facebook" href="https://www.facebook.com/"
								target="_blank"> </a></li>
							<li><a class="twitter" href="https://twitter.com/"
								target="_blank"></a></li>
							<li><a class="googleplus" href="https://plus.google.com/"
								target="_blank"></a></li>
							<li><a class="pinterest" href="https://www.pinterest.com/"
								target="_blank"></a></li>
							<li><a class="dribbble" href="https://dribbble.com/"
								target="_blank"></a></li>
							<li><a class="vimeo" href="https://dribbble.com/"
								target="_blank"></a></li>
							<li><form action="LogOutServlet" method="POST">
									<input type="image" src="images/settings_icon.png" />
								</form></li>
						</ul>
						<div class="clear"></div>
					</div>
					<div class="search_box">
						<form>
							<input type="text" class="text-box"
								placeholder="Search............."><input type="submit"
								value="">
						</form>
					</div>
					<div class="clear"></div>
				</div>

				<div id="content">
					<div id="main" role="main">
						<ul id="tiles">
							<%
								for (Post post : PostDAO.getInstance().getAllPostsByTag("fun")) {
							%>
								<a href="PostPictureServlet?postId=<%=post.getId() %>" class="swipebox" title="Image Name">
								<li data-filter-class='["photos", "blog"]'><img
									src="PostPictureServlet?postId=<%=post.getId() %>"width="300" alt="" />
									<p>
										<a href="DetailsServlet?postId=<%=post.getId()%>">
										<img src="images/blog-icon1.png" title="<%=post.getCreatedOn()%>" alt="" /> 
										<img src="images/blog-icon2.png" title="<%=post.getLike()%>" alt="" /> 
										<img src="images/blog-icon3.png" title="<%=post.getComments().size()%>" alt="" /> 
										<span><%=UsersManager.getInstance().getUser(post.getUserEmail()).getName()%></span>
											<div class="clear"></div> </a>
									</p></li>
							</a>
							<%
								}
							%>
						</ul>

					</div>


					<!-- Include the imagesLoaded plug-in -->
					<script src="js/jquery.imagesloaded.js"></script>
					<script src="js/jquery.wookmark.js"></script>
					<!-- Once the page is loaded, initalize the plug-in. -->
					<script type="text/javascript">
						(function($) {
							$('#tiles')
									.imagesLoaded(
											function() {
												// Prepare layout options.
												var options = {
													autoResize : true, // This will auto-update the layout when the browser window is resized.
													container : $('#main'), // Optional, used for some extra CSS styling
													offset : 2, // Optional, the distance between grid items
													itemWidth : 310
												// Optional, the width of a grid item
												};

												// Get a reference to your grid items.
												var handler = $('#tiles li'), filters = $('#filters li');

												// Call the layout function.
												handler.wookmark(options);

												/**
												 * When a filter is clicked, toggle it's active state and refresh.
												 */
												var onClickFilter = function(
														event) {
													var item = $(event.currentTarget), activeFilters = [];
													item.toggleClass('active');

													// Collect active filter strings
													filters
															.filter('.active')
															.each(
																	function() {
																		activeFilters
																				.push($(
																						this)
																						.data(
																								'filter'));
																	});

													handler.wookmarkInstance
															.filter(
																	activeFilters,
																	'or');
												}

												// Capture filter click events.
												filters.click(onClickFilter);
											});
						})(jQuery);
					</script>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</body>
</html>
