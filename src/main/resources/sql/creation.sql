CREATE DATABASE IF NOT EXISTS otters;
USE otters;

DROP TABLE IF EXISTS resource_has_type;
DROP TABLE IF EXISTS resource_has_target;
DROP TABLE IF EXISTS resource_has_domain;
DROP TABLE IF EXISTS domain;
DROP TABLE IF EXISTS target_audience;
DROP TABLE IF EXISTS resource_type;
DROP TABLE IF EXISTS resources;

CREATE TABLE resources(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    -- resource_type  VARCHAR(20),
    title VARCHAR(255),
    developer VARCHAR(255),
    -- target VARCHAR(500),
    region VARCHAR(255),
    resource_language VARCHAR(255),
    keywords VARCHAR(255),
    link VARCHAR(255)
);


CREATE TABLE resource_type(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE resource_has_type(
	resource_id INT NOT NULL,
    type_id INT NOT NULL,
    PRIMARY KEY(resource_id, type_id),
    FOREIGN KEY(resource_id) REFERENCES resources(id),    
	FOREIGN KEY(type_id) REFERENCES resource_type(id)
);


CREATE TABLE target_audience(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE resource_has_target(
	resource_id INT NOT NULL,
    target_id INT NOT NULL,
    PRIMARY KEY(resource_id, target_id),
    FOREIGN KEY(resource_id) REFERENCES resources(id),
    FOREIGN KEY(target_id) REFERENCES target_audience(id)
);


CREATE TABLE domain(
	id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL
);

CREATE TABLE resource_has_domain(
	resource_id INT NOT NULL,
    domain_id INT NOT NULL,
    PRIMARY KEY(resource_id, domain_id),
    FOREIGN KEY(resource_id) REFERENCES resources(id),
    FOREIGN KEY(domain_id) REFERENCES domain(id)
);

-- LOAD DATA INFILE 'C:\\ProgramData\\\MySQL\\\MySQL Server 8.0\\Uploads\\initialResourceSheet.csv' -- Change the path to the output of the query at line 25 (SHOW VARIABLES LIKE "secure_file_priv";)
--    INTO TABLE resources 
--    FIELDS TERMINATED BY ',' 
--    ENCLOSED BY '"' 
--    LINES TERMINATED BY '\n' 
--    IGNORE 3 ROWS; -- to skip the header
   
-- SHOW VARIABLES LIKE "secure_file_priv"; -- paste the csv file into the output directory of this query