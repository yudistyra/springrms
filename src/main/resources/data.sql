INSERT INTO user(username,password,email,name,active) VALUES ('admin', '$2a$10$6TajU85/gVrGUm5fv5Z8beVF37rlENohyLk3BEpZJFi6Av9JNkw9O', 'admin@springinventory.com','administrator',1);
REPLACE INTO `role` VALUES (1,'ROLE_ADMIN');
UPDATE user SET role_id = 1 where username = 'admin';