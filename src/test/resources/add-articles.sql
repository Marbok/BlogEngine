-- password test
insert into AUTHOR(nickname, password) values
('marbok', '$2a$10$Dx.D7a240ySvk9.K9i0wJOKUboNdZzh9aK6NOKkvXRxYS3AjELZpe'),
('sun micro', '$2a$10$Dx.D7a240ySvk9.K9i0wJOKUboNdZzh9aK6NOKkvXRxYS3AjELZpe'),
('Till Linderman', '$2a$10$Dx.D7a240ySvk9.K9i0wJOKUboNdZzh9aK6NOKkvXRxYS3AjELZpe');

insert into TOPIC(topic_id, name) values
(1, 'java'),
(2, 'python');

insert into ARTICLE(article_id, topic_id, title, description, content, nickname) values
(1, 1, 'java', 'java art', 'content article java', 'marbok'),
(2, 1, 'java sun', 'java sun art', 'content article java sun', 'sun micro'),
(3, 2, 'python', 'python art', 'content article python', 'Till Linderman');
