///**
// * 
// */
//function printComment(){
//	$("#singlecomments").append(<c:set var="user"
//		value="${UsersManager.getInstance().getUser(comment.userEmail)}" />
//			<c:set var="x" value="${x+1}" />
//			<li class="comment">
//				<div class="comment">
//					<div class="comment-author vcard user frame">
//						<a
//							href="pictureprofile?email=<c:out value="${user.email}"></c:out>">
//							<img
//							src="pictureprofile?email=<c:out value="${user.email}"></c:out>"
//							class="avatar avatar-70 photo" height="70" width="70" alt="">
//						</a>
//					</div>
//					<div class="message">
//						<span class="reply-link"> <a class="comment-reply-link"
//							href="javascript:showhide('reply<c:out value="${x}"></c:out>')">Reply</a>
//							<a class="comment-reply-link"
//							href="commentlike?commentId=<c:out value="${comment.commentId}"></c:out>&postId=<c:out value="${post.id}"></c:out>">Like
//								<c:out value="${fn:length(comment.commentLikes)}"></c:out>
//						</a>
//						</span>
//						<div class="info">
//							<a
//								href="pictureprofile?email=<c:out value="${post.userEmail}"></c:out>"><h2>
//									<c:out value="${user.name}"></c:out>
//								</h2></a> <span class="meta"><c:out
//									value="${comment.createdOn}"></c:out></span>
//						</div>
//						<div class="comment-body ">
//							<p>
//								<c:out value="${comment.text}"></c:out>
//							</p>
//						</div>
//					</div>
//
//					<div class="clear"></div>
//				</div>
//				<div id="reply<c:out value="${x}"></c:out>"
//					style="display: none;">
//					<div id="comment-form" class="comment-form">
//						<div id="respond">
//							<h3 id="reply-title">
//								Leave a Reply to
//								<c:out value="${user.name}"></c:out>
//								comment
//							</h3>
//							<form action="commentwrite" method="post" id="commentform">
//								<p class="comment-form-author">
//									<input id="author" name="postId" type="hidden"
//										value="<c:out value="${post.id}"></c:out>" size="30"
//										aria-required="true">
//								</p>
//								<p class="comment-form-author">
//									<input id="author" name="parentCommentId" type="hidden"
//										value="<c:out value="${comment.commentId}"></c:out>"
//										size="30" aria-required="true">
//								</p>
//								<p class="comment-form-email">
//									<input id="email" name="email" type="hidden"
//										value="<c:out value="${sessionScope.USER}"></c:out>"
//										size="30" aria-required="true">
//								</p>
//								<p class="comment-form-comment">
//									<label for="comment">Comment</label>
//									<textarea id="comment" name="comment" cols="45" rows="8"
//										aria-required="true"></textarea>
//								</p>
//								<p class="form-submit">
//									<input name="submit" type="submit" id="submit"
//										value="Post Reply">
//								</p>
//							</form>
//						</div>
//					</div>
//				</div>
//				<div class="clear"></div> <c:forEach var="reply"
//					items="${CommentDAO.getInstance().takeAllCommentsByComment(comment.commentId)}">
//					<c:set var="replyUser"
//						value="${UsersManager.getInstance().getUser(reply.userEmail)}" />
//
//					<ul class='children'>
//						<li class="comment even depth-2" id="li-comment-5">
//							<div id="comment-5" class="com-wrap">
//								<div class="comment-author vcard user frame">
//									<a
//										href="pictureprofile?email=<c:out value="${replyUser.email}"></c:out>">
//										<img
//										src="pictureprofile?email=<c:out value="${replyUser.email}"></c:out>"
//										class="avatar avatar-70 photo" height="70" width="70"
//										alt="">
//									</a>
//								</div>
//								<div class="message">
//									<span class="reply-link"> <a
//										class="comment-reply-link"
//										href="commentlike?commentId=<c:out value="${reply.commentId}"></c:out>&postId=<c:out value="${post.id}"></c:out>">Like
//											<c:out value="${fn:length(reply.commentLikes)}"></c:out>
//									</a>
//									</span>
//									<div class="info">
//										<a
//											href="pictureprofile?email=<c:out value="${post.userEmail}"></c:out>"><h2>
//												<c:out value="${replyUser.name}"></c:out>
//											</h2></a> <span class="meta"><c:out
//												value="${reply.createdOn}"></c:out></span>
//									</div>
//									<div class="comment-body ">
//										<p>
//											<c:out value="${reply.text}"></c:out>
//										</p>
//									</div>
//									<span class="edit-link"></span>
//								</div>
//								<div class="clear"></div>
//							</div>
//							<div class="clear"></div>
//						</li>
//					</ul>
//				</c:forEach>)
//}
