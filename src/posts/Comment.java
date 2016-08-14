package posts;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Comment {

	private Post post;
	private String text;
	private LocalDate date;
	private LocalTime time;

	public Comment(Post post) {
		this.post = post;
	}

	public String createComment() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Plese enter a comment");
		this.text = sc.nextLine();
		this.date = LocalDate.now();
		this.time = LocalTime.now();
		sc.close();
		return text;

	}

	@Override
	public String toString() {
		return "Post: " + post.getTitle() + "\nComment: " + text + "\n date: " + date + " at " + time;
	}

}
