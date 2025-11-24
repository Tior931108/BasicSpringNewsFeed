-- 개발시 필요한 더미데이터 모음

insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('mgarry0@imgur.com', 'gB0!(ehE,Xj039p', 'efeatherstone0', 'http://dummyimage.com/132x100.png/cc0000/ffffff', '반갑습니다', 'N', '2025-11-17 13:48:24', '2025-09-30 17:35:46');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('cryan1@photobucket.com', 'qK8KWt\L)//zut', 'cclifft1', 'http://dummyimage.com/191x100.png/cc0000/ffffff', '정말로요', 'N', '2025-04-09 03:27:31', '2025-01-07 14:29:13');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('swhittam2@purevolume.com', 'iM0S=Jq07a3', 'sblakeslee2', 'http://dummyimage.com/114x100.png/dddddd/000000', '반갑습니다', 'N', '2025-04-25 19:12:06', '2025-10-16 05:25:36');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('gdrane3@kickstarter.com', 'sT6Ch1u%mG\*W4', 'mpedrollo3', 'http://dummyimage.com/171x100.png/dddddd/000000', '반갑습니다', 'N', '2025-09-03 16:27:54', '2025-11-15 02:22:29');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('screasey4@cyberchimps.com', 'oJ0?MR|&u=N', 'tolden4', 'http://dummyimage.com/168x100.png/dddddd/000000', '반갑습니다', 'N', '2025-01-25 14:01:01', '2024-12-08 09:12:59');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('tonthank5@shareasale.com', 'cC9*t&6I!{Qff7', 'cmorrott5', 'http://dummyimage.com/221x100.png/cc0000/ffffff', '안녕하세요', 'N', '2025-09-29 13:03:29', '2025-04-19 19:33:41');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('wquarless6@pen.io', 'hT9p<3#FjSrZlwa', 'fcampes6', 'http://dummyimage.com/224x100.png/5fa2dd/ffffff', '반갑습니다', 'N', '2025-05-10 09:48:03', '2025-04-05 15:02:09');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('mlenoire7@forbes.com', 'zZ6O2p''Z', 'chuebner7', 'http://dummyimage.com/226x100.png/dddddd/000000', '정말로요', 'N', '2024-11-28 00:51:15', '2025-10-29 00:40:38');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('tstile8@elpais.com', 'wH3eX3@@p', 'thillan8', 'http://dummyimage.com/204x100.png/5fa2dd/ffffff', '정말로요', 'N', '2025-01-18 14:02:42', '2025-11-17 02:01:16');
insert into users (email, password, nickname, profile_image_url, introduce, is_delete, created_at, updated_at) values ('jgildroy9@ucoz.com', 'cZ4YhD~,<', 'gfontin9', 'http://dummyimage.com/210x100.png/ff4444/ffffff', '반갑습니다', 'N', '2024-12-30 12:44:19', '2025-08-29 01:01:23');

insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (1,'테스트1', '게시글1', 3, 9, 'N', '2025-08-14 01:37:32', '2025-03-22 16:34:23');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (2,'테스트1', '게시글3', 2, 1, 'N', '2025-10-20 11:27:35', '2025-02-13 02:05:01');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (3,'테스트2', '게시글1', 0, 4, 'N', '2025-07-15 23:12:37', '2025-11-08 02:00:26');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (4,'테스트1', '게시글2', 0, 6, 'N', '2025-09-09 21:52:16', '2025-08-17 18:05:00');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (5,'테스트2', '게시글2', 0, 0, 'N', '2025-02-18 10:19:29', '2025-07-17 03:01:18');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (6,'테스트1', '게시글1', 0, 0, 'N', '2025-03-17 11:16:54', '2025-05-07 22:23:50');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (7,'테스트1', '게시글3', 0, 0, 'N', '2024-12-19 16:10:20', '2025-08-22 21:05:08');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (8,'테스트1', '게시글1', 0, 0, 'N', '2025-10-13 07:26:25', '2025-08-04 23:06:02');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (9,'테스트2', '게시글1', 0, 0, 'N', '2025-06-07 12:45:36', '2025-06-10 00:26:23');
insert into posts (user_id, title, content, liked_count, comment_count, is_delete, created_at, updated_at) values (10,'테스트2', '게시글3', 0, 0, 'N', '2025-04-26 18:07:27', '2024-11-29 01:07:23');

insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (3, '스토리2', 'http://dummyimage.com/128x100.png/ff4444/ffffff', 'N', '2025-09-02 09:24:31', '2025-07-06 23:52:30');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (8, '스토리2', 'http://dummyimage.com/167x100.png/dddddd/000000', 'N', '2025-05-05 03:56:18', '2025-06-14 05:25:30');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (6, '스토리2', 'http://dummyimage.com/103x100.png/dddddd/000000', 'N', '2025-08-28 15:50:41', '2024-12-12 10:17:33');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (2, '스토리1', 'http://dummyimage.com/199x100.png/5fa2dd/ffffff', 'N', '2025-03-19 20:47:27', '2025-03-04 21:16:37');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (9, '스토리1', 'http://dummyimage.com/123x100.png/ff4444/ffffff', 'N', '2025-01-04 16:18:25', '2025-07-27 21:01:50');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (5, '스토리1', 'http://dummyimage.com/114x100.png/ff4444/ffffff', 'N', '2025-04-04 11:06:39', '2025-04-09 18:01:47');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (1, '스토리2', 'http://dummyimage.com/250x100.png/ff4444/ffffff', 'N', '2025-04-13 03:34:14', '2025-11-08 14:58:18');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (8, '스토리2', 'http://dummyimage.com/243x100.png/5fa2dd/ffffff', 'N', '2025-09-03 15:31:54', '2025-02-10 06:52:56');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (3, '스토리2', 'http://dummyimage.com/112x100.png/cc0000/ffffff', 'N', '2025-02-11 23:10:47', '2025-05-13 22:57:33');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (8, '스토리1', 'http://dummyimage.com/146x100.png/ff4444/ffffff', 'N', '2025-02-16 08:50:40', '2025-07-26 21:39:46');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (2, '스토리3', 'http://dummyimage.com/144x100.png/5fa2dd/ffffff', 'N', '2025-04-29 10:51:15', '2025-02-15 02:38:57');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (6, '스토리1', 'http://dummyimage.com/233x100.png/ff4444/ffffff', 'N', '2025-02-17 11:36:37', '2025-07-28 04:03:12');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (5, '스토리2', 'http://dummyimage.com/113x100.png/dddddd/000000', 'N', '2025-11-04 02:50:41', '2025-03-12 14:19:45');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (4, '스토리1', 'http://dummyimage.com/243x100.png/ff4444/ffffff', 'N', '2025-01-30 16:56:57', '2025-08-24 12:12:09');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (9, '스토리3', 'http://dummyimage.com/129x100.png/5fa2dd/ffffff', 'N', '2025-01-02 08:52:54', '2025-02-01 10:03:56');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (6, '스토리3', 'http://dummyimage.com/210x100.png/ff4444/ffffff', 'N', '2025-04-21 20:59:45', '2025-07-13 07:08:56');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (6, '스토리3', 'http://dummyimage.com/213x100.png/ff4444/ffffff', 'N', '2024-12-29 16:24:44', '2025-04-22 15:56:24');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (4, '스토리3', 'http://dummyimage.com/132x100.png/dddddd/000000', 'N', '2024-12-06 19:53:50', '2025-03-27 21:17:34');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (2, '스토리1', 'http://dummyimage.com/102x100.png/dddddd/000000', 'N', '2025-01-14 20:33:28', '2024-12-28 01:38:51');
insert into stories (user_id, content, story_image_url, is_delete, created_at, updated_at) values (2, '스토리2', 'http://dummyimage.com/154x100.png/cc0000/ffffff', 'N', '2025-04-11 05:33:26', '2025-10-07 01:01:27');

insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (2, 1, null, '댓글테스트3', 'N', '2025-03-24 21:22:13', '2025-08-13 23:36:42');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 2, null, '댓글테스트2', 'N', '2025-04-16 04:57:47', '2025-08-18 00:44:37');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (3, 2, null, '댓글테스트2', 'N', '2025-05-10 04:46:19', '2025-08-31 03:30:05');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (4, 2, null, '댓글테스트1', 'N', '2025-01-05 10:28:17', '2025-11-03 22:39:05');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (4, 3, null, '댓글테스트2', 'N', '2024-11-23 12:10:43', '2025-10-05 02:46:34');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 2, null, '댓글테스트2', 'N', '2025-01-15 20:33:15', '2025-06-19 03:32:05');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (3, 4, null, '댓글테스트2', 'N', '2025-03-25 22:53:43', '2025-07-05 13:10:34');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 4, null, '댓글테스트3', 'N', '2025-02-07 14:53:40', '2025-08-21 13:13:16');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (3, 1, null, '댓글테스트1', 'N', '2025-02-24 13:23:26', '2025-10-28 12:59:03');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 1, null, '댓글테스트2', 'N', '2025-01-12 07:09:08', '2025-09-05 05:27:53');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 1, null, '댓글테스트2', 'N', '2025-03-02 07:50:45', '2025-10-03 10:08:21');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (4, 4, null, '댓글테스트1', 'N', '2025-05-30 12:52:40', '2025-09-05 02:52:23');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 2, null, '댓글테스트3', 'N', '2024-12-14 21:42:52', '2025-07-26 03:39:56');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (4, 1, null, '댓글테스트2', 'N', '2024-11-25 14:36:28', '2025-08-03 20:45:44');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 2, null, '댓글테스트1', 'N', '2025-01-27 19:24:09', '2025-10-26 22:45:56');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (4, 2, null, '댓글테스트2', 'N', '2025-02-10 13:01:58', '2025-07-20 19:13:49');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 4, null, '댓글테스트3', 'N', '2025-03-02 07:12:53', '2025-07-31 11:31:34');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (4, 1, null, '댓글테스트1', 'N', '2024-12-23 08:29:58', '2025-10-11 06:57:22');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (3, 2, null, '댓글테스트3', 'N', '2024-12-01 23:31:07', '2025-11-22 03:16:34');
insert into comments (post_id, user_id, parent_id, content, is_delete, created_at, updated_at) values (1, 4, null, '댓글테스트2', 'N', '2025-04-26 11:13:12', '2025-06-25 07:40:25');

insert into blocks (block_id, user_id, blocked_user_id) values (1, 1, 9);
insert into blocks (block_id, user_id, blocked_user_id) values (2, 2, 8);
insert into blocks (block_id, user_id, blocked_user_id) values (3, 2, 7);
insert into blocks (block_id, user_id, blocked_user_id) values (4, 3, 6);
insert into blocks (block_id, user_id, blocked_user_id) values (5, 3, 5);

insert into follows (follow_id, follower_id, following_id, is_follow) values (1, 1, 2, 'WAITING');
insert into follows (follow_id, follower_id, following_id, is_follow) values (2, 2, 3, 'WAITING');
insert into follows (follow_id, follower_id, following_id, is_follow) values (3, 3, 4, 'WAITING');
insert into follows (follow_id, follower_id, following_id, is_follow) values (4, 4, 5, 'WAITING');
insert into follows (follow_id, follower_id, following_id, is_follow) values (5, 5, 6, 'WAITING');

insert into hashtags (hashtag_id, hashtag_name, hashtag_count) values (1, '해시태그1', 1);
insert into hashtags (hashtag_id, hashtag_name, hashtag_count) values (2, '해시태그2', 2);
insert into hashtags (hashtag_id, hashtag_name, hashtag_count) values (3, '해시태그3', 3);
insert into hashtags (hashtag_id, hashtag_name, hashtag_count) values (4, '해시태그4', 2);
insert into hashtags (hashtag_id, hashtag_name, hashtag_count) values (5, '해시태그5', 2);

insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (1, 1, 1);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (2, 2, 2);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (3, 2, 2);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (4, 3, 1);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (5, 3, 2);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (6, 3, 3);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (7, 4, 2);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (8, 4, 2);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (9, 5, 3);
insert into hashtags_posts (hashtag_post_id, hashtag_id, post_id) values (10, 5, 3);

insert into images (image_id, post_id, file_name, image_url) values (1, 1, 'Subin', 'http://dummyimage.com/152x100.png/5fa2dd/ffffff');
insert into images (image_id, post_id, file_name, image_url) values (2, 2, 'Domainer', 'http://dummyimage.com/219x100.png/dddddd/000000');
insert into images (image_id, post_id, file_name, image_url) values (3, 2, 'Zoolab', 'http://dummyimage.com/162x100.png/cc0000/ffffff');
insert into images (image_id, post_id, file_name, image_url) values (4, 3, 'Kanlam', 'http://dummyimage.com/195x100.png/dddddd/000000');
insert into images (image_id, post_id, file_name, image_url) values (5, 3, 'Greenlam', 'http://dummyimage.com/159x100.png/cc0000/ffffff');

insert into user_post_likes (user_post_like_id, user_id, post_id) values (1, 1, 1);
insert into user_post_likes (user_post_like_id, user_id, post_id) values (2, 1, 2);
insert into user_post_likes (user_post_like_id, user_id, post_id) values (3, 2, 1);
insert into user_post_likes (user_post_like_id, user_id, post_id) values (4, 3, 1);
insert into user_post_likes (user_post_like_id, user_id, post_id) values (5, 3, 2);


