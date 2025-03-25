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

INSERT INTO resource_type (name) VALUES
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
	('project deliverable'),
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
    ('stakeholders');
    
INSERT INTO domain (name) VALUES
	('freshwater'),
    ('marine');

CALL AssignResourceTypes;


-- testing
SELECT * FROM resource_has_type;

SELECT * FROM resources as r
JOIN resource_has_type as rt ON r.id = rt.resource_id
JOIN resource_type as t ON rt.type_id = t.id;