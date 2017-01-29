DROP TABLE personAllView
CREATE VIEW personAllView AS SELECT id as id ,firstname as firstname, contact_id as contact_id FROM person UNION select id as id,firstname2 as firstname, contact_id as contact_id from person2
DROP TABLE person2View
CREATE VIEW person2View AS SELECT id as id ,lastname2 as firstname, contact_id as contact_id FROM person2
DROP TABLE person1View
CREATE VIEW person1View AS SELECT id as id ,lastname as firstname, contact_id as contact_id FROM person