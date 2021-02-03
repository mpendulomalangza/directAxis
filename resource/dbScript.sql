CREATE Database `race`;
use `race`;
CREATE TABLE `track` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `track` varchar(10000) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8;

CREATE TABLE `race` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `track_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `track_id` (`track_id`),
  CONSTRAINT `race_ibfk_1` FOREIGN KEY (`track_id`) REFERENCES `track` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

CREATE TABLE `car` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acceleration` int(11) NOT NULL,
  `top_speed` int(11) NOT NULL DEFAULT '0',
  `braking_ability` int(11) NOT NULL DEFAULT '0',
  `cornering_ability` int(11) NOT NULL DEFAULT '0',
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;


CREATE TABLE `race_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `race_id` int(11) NOT NULL,
  `car_id` int(11) NOT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `race_id` (`race_id`),
  KEY `car_id` (`car_id`),
  CONSTRAINT `race_result_ibfk_1` FOREIGN KEY (`race_id`) REFERENCES `race` (`id`),
  CONSTRAINT `race_result_ibfk_2` FOREIGN KEY (`car_id`) REFERENCES `car` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

INSERT INTO `race`.`track`
(`track`)
VALUES
('11110011100011001110011101'),('11111111001111110111100001');

