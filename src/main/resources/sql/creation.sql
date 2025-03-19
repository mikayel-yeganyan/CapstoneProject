CREATE DATABASE IF NOT EXISTS otters;
USE otters;

DROP TABLE IF EXISTS resources;
CREATE TABLE resources(
	id INT(11) NOT NULL,
    resource_type  VARCHAR(20),
    title VARCHAR(500),
    developer VARCHAR(100),
    target VARCHAR(500),
    region VARCHAR(20),
    resource_language VARCHAR(20),
    keywords VARCHAR(100),
    link VARCHAR(500)
);

LOAD DATA INFILE 'C:\\ProgramData\\\MySQL\\\MySQL Server 8.0\\Uploads\\initialResourceSheet.csv' -- Change the path to the output of the query at line 25 (SHOW VARIABLES LIKE "secure_file_priv";)
   INTO TABLE resources 
   FIELDS TERMINATED BY ',' 
   ENCLOSED BY '"' 
   LINES TERMINATED BY '\n' 
   IGNORE 3 ROWS; -- to skip the header
   
SHOW VARIABLES LIKE "secure_file_priv"; -- paste the csv file into the output directory of this query