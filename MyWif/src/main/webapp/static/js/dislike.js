///**
// * 
// */
//$(function (){
//	$(".dislike").on("click", function(){
//		var postId = $("#dislike").val();
//		$.ajax({
//			  method:"POST",
//			  url: "postdislike",
//			  dataType: 'text/json',
//			  data: {postId :postId},
//			})
//			.done(function(response) {
//				if(results!=null && results != ""){
//					showMessage(results);	
//				}
//			  console.log(response);
//			})
//	})
//})
//function showMessage(results){		
//        if(results == 'successfull!'){
//        	alert("You liked this post");
//        }else if(results == 'Adding destination failed!'){
//        	alert("You have liked this post alredy");
//        }
//    }
////<form id="contactForm1" action="/your_url" method="post">
////...
////</form>
////
////<script type="text/javascript">
////var frm = $('#contactForm1');
////frm.submit(function (ev) {
////    $.ajax({
////        type: frm.attr('method'),
////        url: frm.attr('action'),
////        data: frm.serialize(),
////        success: function (data) {
////            alert('ok');
////        }
////    });
////
////    ev.preventDefault();
////});
////</script>