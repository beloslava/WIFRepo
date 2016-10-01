package model.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.pojo.Album;
import model.pojo.Comment;
import model.pojo.Post;
import model.pojo.User;
import model.pojo.UsersManager;

public class AlbumDAO {

	private static final String INSERT_INTO_ALBUMS = "INSERT INTO albums (album_name, user_email) VALUES (?,?);";
	private static final String SELECT_ALBUMS = "SELECT album_id, album_name, user_email, album_date FROM albums ORDER BY album_date DESC;";
	
	private HashMap<String, TreeMap<Integer, ArrayList<Post>>> albumsByUser; // user email -> album id -> list of posts
	private TreeMap<Integer, Album> allAlbums; //album id -> album
	
	private static AlbumDAO instance;

	private AlbumDAO() {
		
		allAlbums = (TreeMap<Integer, Album>) getAllAlbums();
	}

	public synchronized static AlbumDAO getInstance() {
		if (instance == null) {
			instance = new AlbumDAO();
		}
		return instance;
	}
	
	public void addAlbum(String albumName, String userEmail, Timestamp time, List<Post> posts){
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().prepareStatement(INSERT_INTO_ALBUMS, 
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, albumName);
			statement.setString(2, userEmail);			
			statement.executeUpdate();

			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			long id = resultSet.getLong(1);
			
			
			Album album = new Album((int)id, albumName, userEmail, time, new ArrayList<Post>());
			User user = UsersManager.getInstance().getUser(userEmail);
			TreeMap<Integer, ArrayList<Post>> albums = new TreeMap<>();
			albums.put((int)id, new ArrayList<>());

			user.setAlbums(albums);
			allAlbums.put((int)id, album);
	
		} catch (SQLException e) {
			System.out.println("Cannot add album right now");
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	
	public Map<Integer, Album> getAllAlbums(){
		TreeMap<Integer, Album> allAlbums = new TreeMap<Integer, Album>((albumId1, albumId2) -> albumId2 - albumId1);
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = DBManager.getInstance().getConnection().createStatement();
			// album_id, album_name, user_email, album_date
			resultSet = statement.executeQuery(SELECT_ALBUMS);
			while (resultSet.next()) {
				List<Post> albumPosts = (List<Post>) PostDAO.getInstance()
						.getPostsByAlbum(resultSet.getInt("album_id"));


				allAlbums.put(resultSet.getInt("album_id"), new Album( resultSet.getInt("album_id"), 
																	resultSet.getString("album_name"),	
																	resultSet.getString("user_email"),
																	resultSet.getTimestamp("album_date"),
																	albumPosts 
																	

														));

			}
		} catch (SQLException e) {
			System.out.println("Cannot get posts right now");
			e.printStackTrace();
			return allAlbums;

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (resultSet != null) {
					resultSet.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return allAlbums;
	}
	
	public TreeMap<Integer, ArrayList<Post>> getAllAlbumsByUser(String userEmail){
		TreeMap<Integer, ArrayList<Post>> albums = new TreeMap<>((albumId1, albumId2) -> albumId2 - albumId1); //album id->list from posts
		for(Album album : allAlbums.values()){
			int id = album.getAlbumId();
			ArrayList<Post> postsByAlbum = (ArrayList<Post>) album.getPosts();
			albums.put(id, postsByAlbum);
		}
		return albums;
	}
	
}
