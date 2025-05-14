USE otters;

INSERT INTO resources (title, developer, region, resource_language, keywords, link) VALUES
	('Toolkit for river and lake pollution', 'OTTERS', 'Armenia', 'English', 'Ethical issues, freshwater, marine, workshop', 'https://www.youtube.com/watch?v=E8arru9BJi0'),
	('Toolkit for floating litter', 'OTTERS', 'Italy', 'English', 'Litter, freshwater, toolkit', 'https://otters-eu.aua.am/wp-content/uploads/2024/07/Toolkit_for_Italy_Floating_Litter.pdf'),
	('Toolkit for clean beaches', 'OTTERS', 'Portugal', 'English', 'Beaches, marine, toolkit', 'https://otters-eu.aua.am/wp-content/uploads/2024/07/Toolkit_for_Portugal_Clean_Beaches.pdf'),
	('Legal Issues Regarding Citizen Generated Data for The Water Domain', 'OTTERS', 'Europe', 'English', 'Legal issues, freshwater, marine, workshop', 'https://www.youtube.com/watch?v=Qj3-kd15-X8'),
	('Ethical Issues and Water Stewardship', 'OTTERS', 'Europe', 'English', 'Legal issues, freshwater, marine, workshop', 'https://www.youtube.com/watch?v=Qj3-kd15-X8'),
	('Legal and Ethical issues outlook', 'OTTERS', 'Europe', 'English', 'Legal issues, ethical issues, freshwater, marine, deliverable', 'https://docs.google.com/document/d/1iQVGHCMSSjzs_p4K0JCUA_DomLPUdNp4/edit?usp=sharing&ouid=107424245814211760165&rtpof=true&sd=true'),
	('The role of citizen science within WFD and SDGs, and how to incentivize the collaboration with environmental regulators', 'OTTERS', 'Europe', 'English', 'WFD, SDG, environmental agencies, deliverable', 'https://doi.org/10.5281/zenodo.10868040'),
	('Riparian Ecotone citizen science', 'OTTERS', 'Europe', 'English', 'riparian ecosystem, freshwater, deliverable', 'https://doi.org/10.5281/zenodo.10868269'),
	('Citizen science as a contributor to the Marine Strategy Framework Directive implementation', 'OTTERS', 'Europe', 'English', 'MSFD, jellyfish, litter, deliverable', 'https://doi.org/10.5281/zenodo.10868308'),
	('Inventory of water-related cs projects in Europe', 'OTTERS', 'Europe', 'English', 'WFD, SDG, MSFD, repository', 'https://drive.google.com/file/d/1ikQXCz3b9yhzEQXMyBG3zmzFBrhSLdub/view?usp=sharing'),
	('Engaging Citizens with Mission Ocean and Waters: A toolbox of approaches', 'Prep4Blue', 'Europe', 'English', 'Citizens engagement, toolkit', 'https://prep4blue.eu/wp-content/uploads/2023/05/PREP4BLUE-Toolbox-for-Citizen-Engagement_V1.pdf'),
	('10 principles of citizen science', 'ECSA', 'Europe', 'English', 'education, publication', 'https://www.ecsa.ngo/10-principles/'),
	('Rubrics for green competences assessment', 'NUCLIO', 'Europe', 'English', 'education, assessment tools, publication', 'https://assess.nuclio.org/wp-content/uploads/2024/03/GreenComp_Rubrics-EN-Final.pdf'),
	('The ASSESS student assessment framework', 'NUCLIO', 'Europe', 'English', 'education, assessment tools, publication', 'https://assess.nuclio.org/the-assess-book/');

INSERT INTO resource_types (name) VALUES
	('publication'),
    ('report'),
	('toolkit'),
	('policypaper'),
	('whitepaper'),
	('patent'),
    ('repository'),
	('dataset'),
	('maps'),
	('recording'),
	('tutorial'),
	('online course'),
	('deliverable'),
	('catalogue'),
	('digital application'),
	('community platform'),
	('workshop');
    
INSERT INTO target_audience (name) VALUES
	('students'),
	('teachers'),
	('policymakers'),
	('CS practitioners'),
	('legal'),
	('researchers'),
    ('stakeholders'),
    ('academia');
    
INSERT INTO resource_has_target (resource_id, target_id) VALUES
	(1, 2),
    (2, 7),
    (2, 2),
    (2, 6),
    (2, 8),
    (3, 7),
    (3, 2),
    (3, 6),
    (3, 8),
    (4, 4),
    (5, 4),
    (6, 4),
    (7, 4),
    (7, 6),
    (7, 8),
    (8, 6),
    (8, 4),
    (9, 6),
    (9, 4),
    (9, 8),
    (10, 6),
    (11, 6),
    (11, 4),
    (12, 4),
    (13, 1),
    (13, 2),
    (14, 1),
    (14, 2);
    
    
INSERT INTO domains (name) VALUES
	('freshwater'),
    ('marine');

INSERT INTO admin_user(username, pass) VALUES
    ("admin", "admin123");

CALL AssignResourceTypes;
CALL AssignResourceDomains;


-- testing
SELECT * FROM resources;

SELECT r.*, t.name AS type, a.name AS target, d.name AS domain FROM resources as r
LEFT JOIN resource_types as t ON r.type = t.id
LEFT JOIN resource_has_target as res_tar ON r.id = res_tar.resource_id
LEFT JOIN target_audience as a ON res_tar.target_id = a.id
LEFT JOIN resource_has_domain as rd ON r.id = rd.resource_id
LEFT JOIN domains as d ON rd.domain_id = d.id;

SELECT * FROM resources JOIN resource_types ON resources.type = resource_types.id;

SELECT r.*, d.name as domain, t.name as type, a.name, MATCH(title, developer, description, region, keywords)
	AGAINST ('litter' IN NATURAL LANGUAGE MODE) AS score
FROM resources as r LEFT JOIN resource_types as t ON r.type = t.id 
LEFT JOIN resource_has_domain as rd ON r.id = rd.resource_id 
LEFT JOIN domains as d ON rd.domain_id = d.id
LEFT JOIN resource_has_target as rt ON r.id = rt.resource_id
LEFT JOIN target_audience as a ON rt.target_id = a.id
WHERE MATCH(title, description, developer, region, keywords) 
	AGAINST ('litter' IN NATURAL LANGUAGE MODE);
-- AND d.name in ('freshwater') 
-- AND t.name IN ('toolkit', 'workshop') 
-- AND a.name in ('teachers', 'researchers', 'somethingElse')
-- GROUP BY r.id
-- ORDER BY score DESC LIMIT 20 OFFSET 0;
SELECT r.*, MATCH(title, developer, region, keywords, description)
		AGAINST ('' IN NATURAL LANGUAGE MODE) AS score 
    FROM resources AS r LEFT JOIN resource_types AS t ON r.type = t.id
    LEFT JOIN resource_has_domain AS rd ON r.id = rd.resource_id 
    LEFT JOIN domains AS d ON rd.domain_id = d.id 
    LEFT JOIN resource_has_target as rt ON r.id = rt.resource_id
    LEFT JOIN target_audience as a ON rt.target_id = a.id 
    WHERE ('' = '' OR MATCH (title, developer, region, keywords, description)    
		AGAINST ('science' IN NATURAL LANGUAGE MODE)) 
	AND (t.name = 'report')
	GROUP BY r.id 
    ORDER BY score DESC LIMIT 20 OFFSET 0;


