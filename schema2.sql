CREATE SCHEMA  project  COLLATE = utf8_general_ci;

USE project;

CREATE TABLE users (
email VARCHAR(50) NOT NULL,
user_password VARCHAR(50) NOT NULL,
user_name VARCHAR(50) NOT NULL,
age INT NOT NULL,  
gender VARCHAR(10) ,
about VARCHAR(100),
PRIMARY KEY (email)

);

CREATE TABLE tags (
tag_name VARCHAR(20),
PRIMARY KEY (tag_name)
);

CREATE TABLE posts (
post_id INT (11) UNSIGNED NOT NULL AUTO_INCREMENT,
user_email VARCHAR(50) NOT NULL,
tag_name VARCHAR(20),
picture BLOB NOT NULL,
post_like INT,
post_dislike INT,
post_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (post_id),
CONSTRAINT posts_users_user_email FOREIGN KEY (user_email) REFERENCES users(email),
CONSTRAINT posts_tags_tag_name FOREIGN KEY (tag_name) REFERENCES tags(tag_name)

);

CREATE TABLE post_comments (
comment_id INT (11) UNSIGNED NOT NULL AUTO_INCREMENT,
post_id INT (11) UNSIGNED NOT NULL,
user_email VARCHAR(50) NOT NULL,
comment_text VARCHAR(100),
comment_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (comment_id),
CONSTRAINT comments_posts_post_id FOREIGN KEY (post_id) REFERENCES posts(post_id),
CONSTRAINT comments_users_user_email FOREIGN KEY (user_email) REFERENCES users(email)

);
