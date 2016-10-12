package com.mywif.model.db;

import java.sql.Timestamp;
import java.util.List;
import java.util.TreeMap;

import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Album;
import com.mywif.model.pojo.Post;

public interface IAlbumDAO {

	TreeMap<Integer, Album> getAllAlbumsByUser(String userEmail);
	
	void addAlbum(String albumName, String userEmail, Timestamp time, List<Post> posts) throws DBException;
	
}
