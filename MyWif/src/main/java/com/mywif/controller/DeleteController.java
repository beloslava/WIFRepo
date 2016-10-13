package com.mywif.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mywif.model.db.AlbumDAO;
import com.mywif.model.db.PostDAO;
import com.mywif.model.exception.DBException;

@Controller
@SessionAttributes({ "animalsPosts", "abstractPosts", "foodPosts", "peoplePosts", "naturePosts", "urbanPosts",
	"uncategorizedPosts", "familyPosts", "sportPosts", "travelPosts" })
public class DeleteController {

	@RequestMapping(value = "/deletepost", method = RequestMethod.POST)
	public String deletePost(@RequestParam(value = "postId") String postId, HttpSession session,
			HttpServletRequest request,Model model) {
		if (UserController.isUserInSession(request)) {
			System.out.println(Integer.parseInt(postId));
			System.out.println(Integer.parseInt(postId) + session.getAttribute("USER").toString());
			try {
				int albumId=PostDAO.getInstance().getPost(Integer.parseInt(postId)).getAlbumId();
				PostDAO.getInstance().deletePost(Integer.parseInt(postId));
				model.addAttribute("albumId",albumId);
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "myPhotos";
		} else {
			return "index";
		}
	}
}
