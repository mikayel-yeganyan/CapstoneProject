USE otters;

DROP PROCEDURE IF EXISTS AssignResourceTypes;

DELIMITER ;;
    
CREATE PROCEDURE AssignResourceTypes()
BEGIN
DECLARE n INT DEFAULT 0;
DECLARE i INT DEFAULT 0;
DECLARE j INT DEFAULT 0;
DECLARE	k INT DEFAULT 0;
DECLARE type_name VARCHAR(200);
DECLARE type_id INT;
DECLARE resource_id INT;
SELECT COUNT(*) FROM resource_types INTO n;
SET i=0;
WHILE i<n DO 
	SELECT name, id FROM resource_types LIMIT i,1 INTO type_name, type_id;
    DROP TABLE IF EXISTS TempTable;
	CREATE TEMPORARY TABLE TempTable (id INT);
    INSERT INTO TempTable SELECT id FROM resources WHERE keywords LIKE CONCAT('%', type_name, '%');
    SET j = 0;
    SELECT COUNT(*) FROM TempTable INTO k;
    WHILE j<k DO
		SELECT id FROM TempTable LIMIT j, 1 INTO resource_id;
		-- INSERT INTO resource_has_type(resource_id, type_id) VALUE (resource_id, type_id);
        UPDATE resources SET type = type_id WHERE id = resource_id;
        SET j = j + 1;
		UPDATE resources SET keywords = REPLACE(keywords, CONCAT(', ', type_name), '') WHERE id = resource_id;
    END WHILE;
	SET i = i + 1;
END WHILE;
End;
;;

DELIMITER ;


DROP PROCEDURE IF EXISTS AssignResourceDomains;

DELIMITER ;;
    
CREATE PROCEDURE AssignResourceDomains()
BEGIN
DECLARE n INT DEFAULT 0;
DECLARE i INT DEFAULT 0;
DECLARE j INT DEFAULT 0;
DECLARE	k INT DEFAULT 0;
DECLARE domain_name VARCHAR(200);
DECLARE domain_id INT;
DECLARE resource_id INT;
SELECT COUNT(*) FROM domains INTO n;
SET i=0;
WHILE i<n DO 
	SELECT name, id FROM domains LIMIT i,1 INTO domain_name, domain_id;
    DROP TABLE IF EXISTS TempTable;
	CREATE TEMPORARY TABLE TempTable (id INT);
    INSERT INTO TempTable SELECT id FROM resources WHERE keywords LIKE CONCAT('%', domain_name, '%');
    SET j = 0;
    SELECT COUNT(*) FROM TempTable INTO k;
    WHILE j<k DO
		SELECT id FROM TempTable LIMIT j, 1 INTO resource_id;
		INSERT INTO resource_has_domain(resource_id, domain_id) VALUE (resource_id, domain_id);
        SET j = j + 1;
		UPDATE resources SET keywords = REPLACE(keywords, CONCAT(', ', domain_name), '') WHERE id = resource_id;
    END WHILE;
	SET i = i + 1;
END WHILE;
End;
;;

DELIMITER ;


DROP FUNCTION IF EXISTS levenshtein;
DELIMITER $$
CREATE FUNCTION levenshtein( s1 VARCHAR(255), s2 VARCHAR(255) )
    RETURNS INT
    DETERMINISTIC
    BEGIN
        DECLARE s1_len, s2_len, i, j, c, c_temp, cost INT;
        DECLARE s1_char CHAR;
        -- max strlen=255
        DECLARE cv0, cv1 VARBINARY(256);

        SET s1_len = CHAR_LENGTH(s1), s2_len = CHAR_LENGTH(s2), cv1 = 0x00, j = 1, i = 1, c = 0;

        IF s1 = s2 THEN
            RETURN 0;
        ELSEIF s1_len = 0 THEN
            RETURN s2_len;
        ELSEIF s2_len = 0 THEN
            RETURN s1_len;
        ELSE
            WHILE j <= s2_len DO
                SET cv1 = CONCAT(cv1, UNHEX(HEX(j))), j = j + 1;
            END WHILE;
            WHILE i <= s1_len DO
                SET s1_char = SUBSTRING(s1, i, 1), c = i, cv0 = UNHEX(HEX(i)), j = 1;
                WHILE j <= s2_len DO
                    SET c = c + 1;
                    IF s1_char = SUBSTRING(s2, j, 1) THEN
                        SET cost = 0; ELSE SET cost = 1;
                    END IF;
                    SET c_temp = CONV(HEX(SUBSTRING(cv1, j, 1)), 16, 10) + cost;
                    IF c > c_temp THEN SET c = c_temp; END IF;
                    SET c_temp = CONV(HEX(SUBSTRING(cv1, j+1, 1)), 16, 10) + 1;
                    IF c > c_temp THEN
                        SET c = c_temp;
                    END IF;
                    SET cv0 = CONCAT(cv0, UNHEX(HEX(c))), j = j + 1;
                END WHILE;
                SET cv1 = cv0, i = i + 1;
            END WHILE;
        END IF;
        RETURN c;
    END$$
DELIMITER ;


DROP FUNCTION IF EXISTS isLevenshteinMatch;
DELIMITER $$
CREATE FUNCTION isLevenshteinMatch( s1 VARCHAR(255), s2 VARCHAR(255), threshold FLOAT )
	RETURNS BOOLEAN DETERMINISTIC
    BEGIN
		DECLARE  dist, normDist FLOAT;
        
        SET dist = levenshtein(s1, s2);
        SET normDist = dist/GREATEST(LENGTH(s1), LENGTH(s2));
	
		RETURN normDist <= threshold;
    END$$
DELIMITER ;

SELECT levenshtein ("litter", "lyttar");
SELECT isLevenshteinMatch("litter", "lytter", 0.2);
SELECT isLevenshteinMatch("aabaaaaa", "aabaaaaaaa", 0.2);
