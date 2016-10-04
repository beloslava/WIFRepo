CREATE SCHEMA  my_wif  COLLATE = utf8_general_ci;

USE my_wif;

CREATE TABLE users (
email VARCHAR(50) NOT NULL PRIMARY KEY,
user_password VARCHAR(50) NOT NULL,
user_name VARCHAR(50) NOT NULL,
gender VARCHAR(10) ,
about VARCHAR(500),
avatar VARCHAR(500)
);

CREATE TABLE followers ( 
user_email VARCHAR(50) NOT NULL , 
follower_email VARCHAR(50) NOT NULL ,
CONSTRAINT followers_users_user_email FOREIGN KEY (user_email) REFERENCES users(email),
CONSTRAINT followers_users_follower_email FOREIGN KEY (follower_email) REFERENCES users(email),
PRIMARY KEY (user_email, follower_email)
);

CREATE TABLE categories (
category_name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE albums (
album_id INT (11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
album_name VARCHAR(50) NOT NULL,
user_email VARCHAR(50) NOT NULL,
CONSTRAINT albums_users_user_email FOREIGN KEY (user_email) REFERENCES users(email)
);

CREATE TABLE posts (
post_id INT (11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_email VARCHAR(50) NOT NULL,
album_id INT (11) UNSIGNED DEFAULT NULL,
category_name VARCHAR(20) NOT NULL,
picture VARCHAR(500) NOT NULL,
post_name VARCHAR(50) NOT NULL,
key_words VARCHAR(200),
post_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
CONSTRAINT posts_users_user_email FOREIGN KEY (user_email) REFERENCES users(email),
CONSTRAINT posts_categories_category_name FOREIGN KEY (category_name) REFERENCES categories(category_name),
CONSTRAINT posts_albums_album_id FOREIGN KEY (album_id) REFERENCES albums(album_id)
);

CREATE TABLE post_likes (
post_id INT (11) UNSIGNED NOT NULL,
user_email VARCHAR(50) NOT NULL,
CONSTRAINT likes_users_user_email FOREIGN KEY (user_email) REFERENCES users(email),
CONSTRAINT likes_posts_post_id FOREIGN KEY (post_id) REFERENCES posts(post_id),
PRIMARY KEY (post_id, user_email)
);

CREATE TABLE post_dislikes (
post_id INT (11) UNSIGNED NOT NULL,
user_email VARCHAR(50) NOT NULL,
CONSTRAINT dislikes_users_user_email FOREIGN KEY (user_email) REFERENCES users(email),
CONSTRAINT dislikes_posts_post_id FOREIGN KEY (post_id) REFERENCES posts(post_id),
PRIMARY KEY (post_id, user_email)
);

CREATE TABLE post_comments (
comment_id INT (11) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
post_id INT (11) UNSIGNED NOT NULL,
user_email VARCHAR(50) NOT NULL,
parent_comment_id INT(11) UNSIGNED DEFAULT NULL,
comment_text VARCHAR(500) NOT NULL,
comment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
CONSTRAINT comments_post_id FOREIGN KEY (post_id) REFERENCES posts(post_id),
CONSTRAINT comments_user_email FOREIGN KEY (user_email) REFERENCES users(email),
CONSTRAINT comments_parent_id FOREIGN KEY(parent_comment_id) REFERENCES post_comments(comment_id) 
);

CREATE TABLE comments_likes (
comment_id INT (11) UNSIGNED NOT NULL,
user_email VARCHAR(50) NOT NULL,
CONSTRAINT comment_likes_users_user_email FOREIGN KEY (user_email) REFERENCES users(email),
CONSTRAINT comment_likes_comments_comment_id FOREIGN KEY (comment_id) REFERENCES post_comments(comment_id),
PRIMARY KEY (comment_id, user_email)
);