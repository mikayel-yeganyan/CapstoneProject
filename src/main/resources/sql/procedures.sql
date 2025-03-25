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
SELECT COUNT(*) FROM resource_type INTO n;
SET i=0;
WHILE i<n DO 
	SELECT name, id FROM resource_type LIMIT i,1 INTO type_name, type_id;
    DROP TABLE IF EXISTS TempTable;
	CREATE TEMPORARY TABLE TempTable (id INT);
    INSERT INTO TempTable SELECT id FROM resources WHERE keywords LIKE CONCAT('%', type_name, '%');
    SET j = 0;
    SELECT COUNT(*) FROM TempTable INTO k;
    WHILE j<k DO
		SELECT id FROM TempTable LIMIT j, 1 INTO resource_id;
		INSERT INTO resource_has_type(resource_id, type_id) VALUE (resource_id, type_id);
        SET j = j + 1;
		UPDATE resources SET keywords = REPLACE(keywords, CONCAT(', ', type_name), '') WHERE id = resource_id;
    END WHILE;
	SET i = i + 1;
END WHILE;
End;
;;

DELIMITER ;


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
SELECT COUNT(*) FROM resource_type INTO n;
SET i=0;
WHILE i<n DO 
	SELECT name, id FROM resource_type LIMIT i,1 INTO type_name, type_id;
    DROP TABLE IF EXISTS TempTable;
	CREATE TEMPORARY TABLE TempTable (id INT);
    INSERT INTO TempTable SELECT id FROM resources WHERE keywords LIKE CONCAT('%', type_name, '%');
    SET j = 0;
    SELECT COUNT(*) FROM TempTable INTO k;
    WHILE j<k DO
		SELECT id FROM TempTable LIMIT j, 1 INTO resource_id;
		INSERT INTO resource_has_type(resource_id, type_id) VALUE (resource_id, type_id);
        SET j = j + 1;
		UPDATE resources SET keywords = REPLACE(keywords, CONCAT(', ', type_name), '') WHERE id = resource_id;
    END WHILE;
	SET i = i + 1;
END WHILE;
End;
;;

DELIMITER ;