package posts;

public interface IPost {

	public void setLike();

	public void setDislike();

	public void setComment(Comment postComment);

	public void reviewComments();

}
