CREATE DATABASE javaassignmentdb;

CREATE TABLE quizcatagory (
  quizCatagoryID int NOT NULL AUTO_INCREMENT,
  name varchar(30) NOT NULL,
  PRIMARY KEY (quizCatagoryID)
);

CREATE TABLE quizQuestion (
    quizQuestionID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    quizCatagoryID INT NOT NULL,
    question VARCHAR(30) NOT NULL,
    answer VARCHAR(30) NOT NULL,
    FOREIGN KEY (quizCatagoryID) REFERENCES quizCatagory(quizCatagoryID)
);

INSERT INTO javaassignmentdb.quizcatagory
(quizCatagoryID, name)
VALUES(0, 'Arts and Literature');
INSERT INTO javaassignmentdb.quizcatagory
(quizCatagoryID, name)
VALUES(1, 'Entertainment');
INSERT INTO javaassignmentdb.quizcatagory
(quizCatagoryID, name)
VALUES(2, 'Famous People');
INSERT INTO javaassignmentdb.quizcatagory
(quizCatagoryID, name)
VALUES(3, 'Geography');
INSERT INTO javaassignmentdb.quizcatagory
(quizCatagoryID, name)
VALUES(4, 'History');
INSERT INTO javaassignmentdb.quizcatagory
(quizCatagoryID, name)
VALUES(5, 'Science and Nature');
