CREATE TABLE `city` (
  `city_id` bigint NOT NULL AUTO_INCREMENT,
  `city_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `city`(`city_name`) VALUES
    ("서귀포"),
    ("남원"),
    ("표선"),
    ("성산"),
    ("구좌"),
    ("조천"),
    ("제주시"),
    ("애월"),
    ("한림"),
    ("한경"),
    ("대정"),
    ("안덕"),
    ("중문");


CREATE TABLE `day_cal` (
  `day_id` bigint NOT NULL AUTO_INCREMENT,
  `day_date` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`day_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DELIMITER $$
CREATE PROCEDURE loopInsert()
BEGIN
DECLARE i INT DEFAULT 1;
WHILE i <= 31 DO
INSERT INTO `day_cal`(`day_id`, `day_date`)
VALUES(i, concat("", i));
SET i = i + 1;
END WHILE;
END$$
DELIMITER;
CALL loopInsert;


CREATE TABLE `member` (
  `member_id` varchar(255) NOT NULL,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `member_birth` varchar(255) DEFAULT NULL,
  `member_email` varchar(255) DEFAULT NULL,
  `member_image_url` varchar(255) DEFAULT NULL,
  `member_nationality` varchar(255) DEFAULT NULL,
  `member_nickname` varchar(255) DEFAULT NULL,
  `member_phone` varchar(255) DEFAULT NULL,
  `member_register_kind` varchar(255) DEFAULT NULL,
  `member_status` varchar(255) DEFAULT NULL,
  `member_tags` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `guest_house` (
  `guest_house_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `guest_house_address` varchar(255) DEFAULT NULL,
  `guest_house_info` varchar(255) DEFAULT NULL,
  `guest_house_location` varchar(255) DEFAULT NULL,
  `guest_house_name` varchar(255) DEFAULT NULL,
  `guest_house_phone` varchar(255) DEFAULT NULL,
  `guest_house_review_count` bigint DEFAULT NULL,
  `guest_house_star` float DEFAULT NULL,
  `guest_house_status` varchar(255) DEFAULT NULL,
  `guest_house_tag` varchar(255) DEFAULT NULL,
  `hearts` int DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  `member_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`guest_house_id`),
  KEY `FK99bkgq28w4mf6jcm25vk8r3ui` (`city_id`),
  KEY `FK6eabvtg7n612rky4jkq2umfpm` (`member_id`),
  CONSTRAINT `FK6eabvtg7n612rky4jkq2umfpm` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FK99bkgq28w4mf6jcm25vk8r3ui` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `guest_house_details` (
  `guest_house_details_id` bigint NOT NULL AUTO_INCREMENT,
  `guest_house_board` bit(1) DEFAULT NULL,
  `guest_house_cook` bit(1) DEFAULT NULL,
  `guest_house_essential` bit(1) DEFAULT NULL,
  `guest_house_kitchen` bit(1) DEFAULT NULL,
  `guest_house_ocean` bit(1) DEFAULT NULL,
  `guest_house_parking` bit(1) DEFAULT NULL,
  `guest_house_party` bit(1) DEFAULT NULL,
  `guest_house_task` bit(1) DEFAULT NULL,
  `guest_house_washing` bit(1) DEFAULT NULL,
  `guest_house_wifi` bit(1) DEFAULT NULL,
  `guest_house_id` bigint DEFAULT NULL,
  PRIMARY KEY (`guest_house_details_id`),
  KEY `FK86grrq5aldboowhtitm2f3ohx` (`guest_house_id`),
  CONSTRAINT `FK86grrq5aldboowhtitm2f3ohx` FOREIGN KEY (`guest_house_id`) REFERENCES `guest_house` (`guest_house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `guest_house_image` (
  `guest_house_image_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `guest_house_image_url` varchar(255) DEFAULT NULL,
  `guest_house_id` bigint DEFAULT NULL,
  PRIMARY KEY (`guest_house_image_id`),
  KEY `FKela8su6deppdsh23qfjesjfxy` (`guest_house_id`),
  CONSTRAINT `FKela8su6deppdsh23qfjesjfxy` FOREIGN KEY (`guest_house_id`) REFERENCES `guest_house` (`guest_house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `heart` (
  `heart_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `heart_status` bit(1) DEFAULT NULL,
  `guest_house_id` bigint DEFAULT NULL,
  `member_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`heart_id`),
  KEY `FKg44e4ytyucwgolj8hk9skxlyp` (`guest_house_id`),
  KEY `FKiqbtbunbl2h0r928gnlg7ncta` (`member_id`),
  CONSTRAINT `FKg44e4ytyucwgolj8hk9skxlyp` FOREIGN KEY (`guest_house_id`) REFERENCES `guest_house` (`guest_house_id`),
  CONSTRAINT `FKiqbtbunbl2h0r928gnlg7ncta` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `member_member_roles` (
  `member_member_id` varchar(255) NOT NULL,
  `member_roles` varchar(255) DEFAULT NULL,
  KEY `FKm8kcdrpdccty9qix5f7onhena` (`member_member_id`),
  CONSTRAINT `FKm8kcdrpdccty9qix5f7onhena` FOREIGN KEY (`member_member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `refresh_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `refresh_token` varchar(255) DEFAULT NULL,
  `member_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5gdbafb2i76hk1ai18ah6an4w` (`member_id`),
  CONSTRAINT `FK5gdbafb2i76hk1ai18ah6an4w` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `room` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `room_image_url` varchar(255) DEFAULT NULL,
  `room_info` varchar(255) DEFAULT NULL,
  `room_name` varchar(255) DEFAULT NULL,
  `room_price` int DEFAULT NULL,
  `room_status` varchar(255) DEFAULT NULL,
  `guest_house_id` bigint DEFAULT NULL,
  PRIMARY KEY (`room_id`),
  KEY `FKf4rv3cmwqbrdvmorqwrwa4qws` (`guest_house_id`),
  CONSTRAINT `FKf4rv3cmwqbrdvmorqwrwa4qws` FOREIGN KEY (`guest_house_id`) REFERENCES `guest_house` (`guest_house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `room_reservation` (
  `room_reservation_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `room_reservation_end` date DEFAULT NULL,
  `room_reservation_start` date DEFAULT NULL,
  `room_reservation_status` varchar(255) DEFAULT NULL,
  `guest_house_id` bigint DEFAULT NULL,
  `member_id` varchar(255) DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  PRIMARY KEY (`room_reservation_id`),
  KEY `FK783ps3ddu9p1ib0fra3iyk6bf` (`guest_house_id`),
  KEY `FK5lr97u58bhtkr4e06xcxi15ga` (`member_id`),
  KEY `FK19p6c3un3mbs7b7bxkcxk8xn2` (`room_id`),
  CONSTRAINT `FK19p6c3un3mbs7b7bxkcxk8xn2` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  CONSTRAINT `FK5lr97u58bhtkr4e06xcxi15ga` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`),
  CONSTRAINT `FK783ps3ddu9p1ib0fra3iyk6bf` FOREIGN KEY (`guest_house_id`) REFERENCES `guest_house` (`guest_house_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `comment` text,
  `star` float DEFAULT NULL,
  `guest_house_id` bigint DEFAULT NULL,
  `member_id` varchar(255) DEFAULT NULL,
  `room_reservation_id` bigint DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `FKa5rvbt6dp7bygc2luyp07hsoi` (`guest_house_id`),
  KEY `FKk0ccx5i4ci2wd70vegug074w1` (`member_id`),
  KEY `FK3c9snb7s1e2jgpqy9838fbbyw` (`room_reservation_id`),
  CONSTRAINT `FK3c9snb7s1e2jgpqy9838fbbyw` FOREIGN KEY (`room_reservation_id`) REFERENCES `room_reservation` (`room_reservation_id`),
  CONSTRAINT `FKa5rvbt6dp7bygc2luyp07hsoi` FOREIGN KEY (`guest_house_id`) REFERENCES `guest_house` (`guest_house_id`),
  CONSTRAINT `FKk0ccx5i4ci2wd70vegug074w1` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `review_comment` (
  `review_comment_id` bigint NOT NULL AUTO_INCREMENT,
  `create_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  `review_comment` text,
  `member_id` varchar(255) DEFAULT NULL,
  `review_id` bigint DEFAULT NULL,
  PRIMARY KEY (`review_comment_id`),
  KEY `FKqwqh8uijvu4mvspob99au0tmh` (`member_id`),
  KEY `FK9j7pkcpuestrwjre1a1902biu` (`review_id`),
  CONSTRAINT `FK9j7pkcpuestrwjre1a1902biu` FOREIGN KEY (`review_id`) REFERENCES `review` (`review_id`),
  CONSTRAINT `FKqwqh8uijvu4mvspob99au0tmh` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





