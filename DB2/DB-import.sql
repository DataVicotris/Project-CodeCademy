DROP DATABASE IF EXISTS CodeCademyDB;
CREATE DATABASE CodeCademyDB;
GO
USE CodeCademyDB;

CREATE TABLE Employee (
    EmployeeName nvarchar(50),

   PRIMARY KEY (EmployeeName) 
)

CREATE TABLE Certificate (
    CertificateID int IDENTITY(1,1),
    Grade float ,
    EmployeeName nvarchar(40),

    PRIMARY KEY (CertificateID),

    CONSTRAINT CHK_CertificateGrade CHECK (Grade BETWEEN 0 AND 10)
)

CREATE TABLE Student (
    Email nvarchar(50),
    Name nvarchar(50) NOT NULL,
    DateOfBirth date NOT NULL,
    Gender nchar(1) NOT NULL,
    Street nvarchar(70) NOT NULL,
    HouseNumber nvarchar(5) NOT NULL,
    PostalCode nvarchar(7) NOT NULL,
    City nvarchar(50) NOT NULL,
    Country nvarchar(50) NOT NULL,

    PRIMARY KEY (Email),

    CONSTRAINT CHK_StudentEmail CHECK (Email LIKE ('%_@_%._%')),
    CONSTRAINT CHK_StudentDateOfBirth CHECK (DateOfBirth LIKE ('[0-9][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]')),
    CONSTRAINT CHK_StudentGender CHECK (Gender IN ('M','F','O')),
    CONSTRAINT CHK_StudentPostalCode CHECK (PostalCode LIKE('[0-9][0-9][0-9][0-9]_[A-Z][A-Z]')),
    CONSTRAINT CHK_StudentHouseNumber CHECK (HouseNumber LIKE ('[1-9]') OR HouseNumber LIKE ('[1-9][0-9]') OR HouseNumber LIKE ('[1-9][0-9][0-9]') OR HouseNumber LIKE ('[1-9][a-zA-Z]') OR HouseNumber LIKE ('[1-9][0-9][a-zA-Z]') OR HouseNumber LIKE ('[1-9][0-9][0-9][a-zA-Z]'))
)

CREATE TABLE Subject (
    Subject nvarchar(50) NOT NULL,

    PRIMARY KEY (Subject)

)

CREATE TABLE Course (
    CourseName nvarchar(50),
    Subject nvarchar(50) NOT NULL,
    IntroText nvarchar(255) NOT NULL,
    Level nvarchar(12) NOT NULL,

    PRIMARY KEY (CourseName),

    CONSTRAINT CHK_CourseLevel CHECK (Level IN ('beginner', 'intermediate', 'expert')),
    CONSTRAINT FK_Course_Subject FOREIGN KEY (Subject) REFERENCES Subject(Subject) ON UPDATE CASCADE ON DELETE CASCADE

)

CREATE TABLE Enrollment (
    EnrollmentDate datetime DEFAULT CURRENT_TIMESTAMP, --YYYY-MM-DDBHH
    CourseName nvarchar(50), -- LINK Course table
    Email nvarchar(50), -- LINK Student table
    CertificateID int NULL, -- LINK Certificate table

    PRIMARY KEY (EnrollmentDate, CourseName, Email),

    CONSTRAINT FK_Enrollment_Course FOREIGN KEY (CourseName) REFERENCES Course(CourseName) ON UPDATE CASCADE,
    CONSTRAINT FK_Enrollment_Student FOREIGN KEY (Email) REFERENCES Student(Email) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Enrollment_Certificate FOREIGN KEY (CertificateID) REFERENCES Certificate(CertificateID),
)

CREATE TABLE Percentage (
    Percentage int DEFAULT 0 NOT NULL, 
    EnrollmentDate datetime, -- LINK enrollment
    CourseName nvarchar(50),  -- LINK course
    Email nvarchar(50), -- LINK student
    
    PRIMARY KEY (EnrollmentDate, CourseName, Email),
    
    CONSTRAINT FK_Percentage_Enrollment FOREIGN KEY (EnrollmentDate, CourseName, Email) REFERENCES Enrollment(EnrollmentDate, CourseName, Email),
    CONSTRAINT FK_Percentage_Course FOREIGN KEY (CourseName) REFERENCES Course(Coursename) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_Percentage_Student FOREIGN KEY (Email) REFERENCES Student(Email),

	CONSTRAINT CHK_PercentagePercentage CHECK (Percentage BETWEEN 0 AND 100)
)


CREATE TABLE ContactPerson (
    ContactPerson nvarchar(50),
    Email nvarchar(50) NOT NULL UNIQUE,

    PRIMARY KEY (ContactPerson),

    CONSTRAINT CHK_ContactpersonEmail CHECK (Email LIKE ('%_@_%._%'))
)

CREATE TABLE Module (
    ContentItemID int IDENTITY(0,2) UNIQUE, 
    Title nvarchar(50) NOT NULL,
    Version float NOT NULL,
    Description nvarchar(150) NOT NULL,
    ContactPerson nvarchar(50) NULL, -- LINK ContactPerson table
	PublicationDate datetime DEFAULT CURRENT_TIMESTAMP,
	Status nvarchar(12) NOT NULL DEFAULT 'active',

    PRIMARY KEY (Title, Version),

	CONSTRAINT CHK_ModuleStatus CHECK (Status IN ('concept', 'active','archived')),

    CONSTRAINT FK_Module_ContactPerson FOREIGN KEY (ContactPerson) REFERENCES ContactPerson(ContactPerson) ON UPDATE CASCADE
)

CREATE TABLE Course_Module (
    CourseName nvarchar(50), -- LINK Course
    TrackingNumber int NOT NULL,
	ContentItemID int, -- LINK Module

    PRIMARY KEY (CourseName, TrackingNumber, ContentItemId),

    CONSTRAINT FK_CourseModule_Course FOREIGN KEY (CourseName) REFERENCES Course(CourseName) ON UPDATE CASCADE,
    CONSTRAINT FK_CourseModule_Module FOREIGN KEY (ContentItemID) REFERENCES Module(ContentItemID)
)

CREATE TABLE Speaker (
    SpeakerName nvarchar(50), -- LINK Webcast table
    Organisation nvarchar(50) NULL,

    PRIMARY KEY (SpeakerName)
)

CREATE TABLE Webcast (
    ContentItemID int IDENTITY(1,2) UNIQUE, 
    Title nvarchar(255),
    Description nvarchar(255) NOT NULL,
    Duration int NOT NULL,
    SpeakerName nvarchar(50) NOT NULL, -- LINK Speaker table
    URL nvarchar(50) NOT NULL,
    Views int NOT NULL DEFAULT 0,
	PublicationDate datetime DEFAULT CURRENT_TIMESTAMP,
	Status nvarchar(12) NOT NULL DEFAULT 'active',

    PRIMARY KEY (ContentItemID, Title),
	CONSTRAINT CHK_WebcastStatus CHECK (Status IN ('concept', 'active','archived')),
    CONSTRAINT FK_Webcast_Speaker FOREIGN KEY (SpeakerName) REFERENCES Speaker(SpeakerName) ON UPDATE CASCADE,

	CONSTRAINT CHK_WebcastURL CHECK (URL LIKE 'https://_%._%._%' OR URL LIKE 'https://_%._%._%')
)

CREATE TABLE Student_Content (
    Email nvarchar(50), -- LINK Student
	-- ContentItemID int, -- LINK Module, Webcast
    ModuleItemID int NULL,
    WebcastItemID int NULL,
    Progress int DEFAULT 0,

    CONSTRAINT CHK_Progress CHECK (Progress BETWEEN 0 AND 100),
    CONSTRAINT CHK_ModuleItemWebcastItem CHECK ((ModuleItemID IS NULL AND WebcastItemID IS NOT NULL) OR (ModuleItemID IS NOT NULL AND WebcastItemID IS NULL)),
    
    CONSTRAINT FK_StudentContent_Student FOREIGN KEY (Email) REFERENCES Student(Email) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT FK_StudentContent_Webcast FOREIGN KEY (WebcastItemID) REFERENCES Webcast(ContentItemID),
    CONSTRAINT FK_StudentContent_Module FOREIGN KEY (ModuleItemID) REFERENCES Module(ContentItemID)
)

/* ---------------------------------- */
/*                                    */
/*             DUMMY DATA             */
/*                                    */
/* ---------------------------------- */

-- ContactPerson
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Ruud', 'rlm.hermans@avans.nl')
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Arno', 'Mikevandercaaij@gmail.com')
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Remo', 'rma@avans.nl')
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Davide', 'd.ambesi@avans.nl')
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Jan', 'jt.montizaan@avans.nl')
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Jantje', 'jantje@dirksen.com')
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Pietje', 'pietje@klomp.com')
INSERT INTO ContactPerson(ContactPerson, Email) VALUES ('Dirk', 'dirk@gmail.com')


-- Module
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 1 Milestone 1', 1.1, 'Here is the first milestone, good luck.', 'Henry')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 1 Milestone 2', 1.2, 'Here is the second milestone, good luck.', 'Henry')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 1 Milestone 3', 1.3, 'Here is the third milestone, good luck.', 'Henry')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 1 Milestone 4', 1.4, 'Here is the fourth milestone, good luck.', 'Henry')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 1 Milestone 5', 1.5, 'Here is the fifth milestone, good luck.', 'Henry')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 1 Milestone 6', 1.6, 'Here is the sixth milestone, good luck.', 'Henry')

INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 2 Milestone 1', 1.1, 'Composition of your group + github repository', 'Arno')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 2 Milestone 2', 1.2, 'Hand in your ERD and class diagram', 'Arno')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 2 Milestone 3', 1.3, 'Hand in your package diagram', 'Arno')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 2 Milestone 4', 1.4, 'Hand in your relationeel ontwerp', 'Remo')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 2 Milestone 5', 1.5, 'Hand in your vertical slice', 'Remo')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 2 Milestone 6', 1.6, 'Hand in your application', 'Remo')

INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 3 Milestone 1',1.1,'Android: App design','Arno')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 3 Milestone 2',1.2,'Android: Proof of concept','Arno')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 3 Milestone 3',1.3,'Android: First version of the application','Arno')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 3 Milestone 4',1.4,'Android: Final product','Arno')

INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 4 Milestone 1',1.1,'Apple: App design','Ruud')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 4 Milestone 2',1.2,'Apple: Proof of concept ','Ruud')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 4 Milestone 3',1.3,'Apple: First version of the application','Ruud')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Programming 4 Milestone 4',1.4,'Apple: Final product','Ruud')

INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 1 part 1', 1.1, 'Data Manipulation Language', 'Davide')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 1 part 2', 1.2, 'Data Definition Language', 'Davide')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 1 part 3', 1.3, 'Sleutels: Primary Key, Alternate Key, Foreign Key, Candidate Key', 'Davide')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 1 part 4', 1.4, 'Constraints: UNIQUE, NOT NULL, CHECK', 'Davide')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 1 part 5', 1.5, 'Referentiële integriteit: ON DELETE NO ACTION, ON UPDATE CASCADE, SET NULL', 'Davide')

INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 2 part 1', 1.1, 'Gegevensmoddelering met Entity-Relationship Diagrams', 'Davide')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 2 part 2', 1.2, 'Relationeel Database Ontwerp', 'Davide')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Database 2 part 3', 1.3, 'Normaliseren', 'Davide')

INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('English',1.1,'Present and past time','Dirk')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('English',1.2,'Present simple and present continuous','Dirk')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('English',1.3,'Present perfect and passive voice','Jantje')

INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Nederlands',1.1,'Zinnen ontleden','Pietje')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Nederlands',1.2,'Begrijpend lezen','Pietje')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Nederlands',1.3,'Betoog schrijven','Pietje')
INSERT INTO Module(Title, Version, Description, ContactPerson) VALUES ('Nederlands',1.4,'Motivatiebreif schrijven','Pietje')

-- Subject
INSERT INTO Subject (Subject) VALUES('Programming')
INSERT INTO Subject (Subject) VALUES('Databases')
INSERT INTO Subject (Subject) VALUES('Language')

-- Course
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('Programming 1', 'Programming', 'We start from 0 and we are going to work to a Full Stack Developer!', 'beginner')
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('Programming 2', 'Programming', 'More Programming', 'intermediate')
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('Programming 3', 'Programming', 'More Programming', 'intermediate')
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('Programming 4', 'Programming', 'We start from 0 and we are going to work to a Full Stack Developer!', 'expert')
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('Databases 1', 'Databases', 'Sla gegevens op in een database, Save data in a database, learn connections between tables, and retrieve data from tables with use of a query', 'beginner')
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('Databases 2', 'Databases', 'Maak duidelijke overzichten voor het opbouwen van een database en kortere tabellen van een groot excel sheet om redundantie te voorkomen', 'intermediate')
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('English', 'Language', 'We expect a minumun level of B+ for English to start this course.', 'intermediate')
INSERT INTO Course(CourseName, Subject, IntroText, Level) VALUES ('Nederlands', 'Language', 'Excellent planning skills is needed for this course. You will be teaching yourself without the help of a teacher.', 'intermediate')


-- Course_Module
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 1', 1, 0)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 1', 2, 2)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 1', 3, 4)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 1', 4, 6)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 1', 5, 8)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 1', 6, 10)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Databases 1', 1, 40)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Databases 2', 1, 50)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('English', 1, 56)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Nederlands', 1, 62)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 2', 1, 12)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 3', 1, 24)
INSERT INTO Course_Module (CourseName, TrackingNumber, ContentItemID) VALUES ('Programming 4', 1, 32)

-- Student
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('dhanitaelman@gmail.com', 'Dhani Taelman', '2002-02-14', 'M', 'Gareelweg', 11, '4726 SW', 'Heerle', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('bertajumper@gmail.com', 'Berta de jumper', '2003-12-19', 'M', 'Burgemeester Beelaertspark', 12, '3319 AV', 'Dordrecht', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('martinbosma@gmail.com', 'martinbosma', '1996-12-06', 'M', 'Bergseweg', 45, '4567 SB', 'Breda', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('nielsalsema@gmail.com', 'Nielsalsema', '2010-03-20', 'M', 'JagersWeg', 86, '4213 FW', 'Dordrecht', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('t.canal@student.nl', 'Taryn Canal', '1988-05-31', 'M', 'Barnevelderstraat', 10, '1564 HF', 'Gouda', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('s.cautero@student.nl', 'Suanne Cautero', '1990-11-28', 'F', 'Schweigmannstraat', 34, '2485 KS', 'Utrecht', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('s.chang@student.nl', 'Stephanie Chang', '1991-04-05', 'F', 'Egelskoog', 1, '1822 EK', 'Ede', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('s.chen@student.nl', 'Se Chen', '1999-08-25', 'O', 'Korenbleomstraat', 34, '6234 LP', 'Venlo', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('s.chin@student.nl', 'Sarah Chin', '1990-10-25', 'F', 'Legmeerplein', 11, '8421 GV', 'Maastricht', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('s.chua@student.nl', 'Samaneh Chua', '1992-04-21', 'F', 'Lange Zandstraat', 21, '7412 MF', 'Rotterdam', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('r.cockle@student.nl', 'Rui Cockle', '1995-09-18', 'O', 'Bredeweg', 22, '3496 MR', 'Den Haag', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('r.cusnir@student.nl', 'Robert Cusnir', '1996-03-02', 'M', 'Ameidepark', 4, '5701 DA', 'Vlissingen', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('r.del@student.nl', 'Richard Del', '1993-03-22', 'M', 'Leerinkbeek', 5, '8033 RT', 'Leeuwarden', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('p.druckman@student.nl', 'Philip Druckman', '1993-03-29', 'M', 'Heijermansweg', 28, '6413 WT', 'Leiden', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('r.crabtree@student.nl', 'Rodriguez Crabtree', '2001-02-04', 'M', 'Zaanenlaan', 17, '7234 DA', 'Haarlem', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('r.davidowitz@student.nl', 'Robert Davidowitz', '2002-02-24', 'O', 'Molensingel', 1, '9138 JS', 'Maastricht', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('r.donahue@student.nl', 'Radu Donahue', '2002-04-29', 'M', 'Valepoort', 24, '2348 KL', 'Bergen op Zoom', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('p.erickson@student.nl', 'Peter Erickson', '2003-05-14', 'M', 'Kamp', 15, '3118 PA', 'Lelystad', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('p.forsyth@student.nl', 'Patino Forsyth', '2003-05-25', 'M', 'Chopinlaan', 24, '1667 ME', 'Groningen', 'The Netherlands')
INSERT INTO Student(Email, Name, DateOfBirth, Gender, Street, HouseNumber, PostalCode, City, Country) VALUES ('n.gambino@student.nl', 'Noel Gambino', '2004-12-21', 'F', 'Radarstraat', 8, '5286 NU', 'Roosendaal','The Netherlands')



-- Employee
INSERT INTO Employee (EmployeeName) VALUES ('Berke Hoogenkamp');
INSERT INTO Employee (EmployeeName) VALUES ('Dhani Taelman');
INSERT INTO Employee (EmployeeName) VALUES ('Kasper van den Enden');
INSERT INTO Employee (EmployeeName) VALUES ('Mick Holster');
INSERT INTO Employee (EmployeeName) VALUES ('Jens de Vlaming');
INSERT INTO Employee (EmployeeName) VALUES ('Arno Broeders');
INSERT INTO Employee (EmployeeName) VALUES ('Remo van der Heiden');
INSERT INTO Employee (EmployeeName) VALUES ('Jochem de Bondt');
INSERT INTO Employee (EmployeeName) VALUES ('Lars Visser');
INSERT INTO Employee (EmployeeName) VALUES ('Jorn van Bommel');

-- Certificates
INSERT INTO Certificate (Grade, EmployeeName) VALUES (7.5,'Lars Visser')
INSERT INTO Certificate (Grade, EmployeeName) VALUES (6.3,'Arno Broeders')
INSERT INTO Certificate (Grade, EmployeeName) VALUES (7.2,'Remo van der Heiden')
INSERT INTO Certificate (Grade, EmployeeName) VALUES (NULL,NULL)
INSERT INTO Certificate (Grade, EmployeeName) VALUES (NULL,NULL)
INSERT INTO Certificate (Grade, EmployeeName) VALUES (NULL,NULL)


-- Enrollment
INSERT INTO Enrollment (EnrollmentDate, CourseName, Email, CertificateID) VALUES ('2024-01-04 13:51:10.067', 'Programming 1', 'dhanitaelman@gmail.com', 1)
INSERT INTO Enrollment (EnrollmentDate, CourseName, Email, CertificateID) VALUES ('2024-01-02 16:01:10.037','Programming 1', 'bertajumper@gmail.com', 3)
INSERT INTO Enrollment (EnrollmentDate, CourseName, Email, CertificateID) VALUES ('2024-11-21 20:23:58.767','Programming 1', 'martinbosma@gmail.com', 2)
INSERT INTO Enrollment (EnrollmentDate, CourseName, Email, CertificateID) VALUES ('2042-11-23 12:41:10.727', 'Programming 1', 'n.gambino@student.nl', 4)
INSERT INTO Enrollment (EnrollmentDate, CourseName, Email, CertificateID) VALUES ('2024-01-02 08:26:55.543','Programming 1', 'r.crabtree@student.nl', 5)
INSERT INTO Enrollment (EnrollmentDate, CourseName, Email, CertificateID) VALUES ('2024-11-21 14:11:43.678','Programming 1', 'r.cusnir@student.nl', 6)
INSERT INTO Enrollment (EnrollmentDate, CourseName, Email) VALUES ('2022-11-23 13:51:10.067','Programming 1', 'kaspervandenenden@gmail.com')

-- Speaker
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Floran Gale','Java inc')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Calum Vliet','Java inc')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Boyd can Mechelen','Java inc')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Jeltse Wolter','PythonCorp')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Koert Boudewijns','PythonCorp')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Gustaaf Elders','PythonCorp')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Lou Mensing','Codecademy')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Manus Renes','Codecademy')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('David Zweverink','Codecademy')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Jelco Westhuis','Github')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Desley Pepers','Github')
INSERT INTO Speaker (SpeakerName, Organisation) VALUES ('Feiko Balemans','Github')

-- Webcast
INSERT INTO Webcast (Title, Description, Duration, SpeakerName, URL) VALUES ('Learn programming using this exercise 1 hour a day', 'In this webcast we will show you that you can be an advanced programmer using this exercise 1 hour a day.', 9000, 'Jeltse Wolter', 'https://www.learnpgrogramming.com')
INSERT INTO Webcast (Title, Description, Duration, SpeakerName, URL) VALUES ('Learn working with databases using this exercise 1 hour a day', 'In this webcast we will show you that you can understand databases like a pro using this exercise 1 hour a day.', 6000, 'Lou Mensing', 'https://www.learndatabases.com')
INSERT INTO Webcast (Title, Description, Duration, SpeakerName, URL) VALUES ('Speak fluent english using this exercise 1 hour a day', 'In deze webcast laten we zien hoe het mogelijk is om met 1 uur oefening per dag een zeer ervaren Engels spreker te worden.', 3000, 'Manus Renes', 'https://www.learnenglsh.com')
INSERT INTO Webcast (Title, Description, Duration, SpeakerName, URL) VALUES ('Learn Dutch from home', 'In this webcast we will show you how the Dutch language works.',1800,'David Zweverink','https://www.learnenglish.com')

-- Percentages
INSERT INTO Percentage (EnrollmentDate,CourseName,Email, Percentage) VALUES ('2024-01-04 13:51:10.067','Programming 1', 'jensdevlaming@gmail.com', 100)
INSERT INTO Percentage (EnrollmentDate,CourseName,Email, Percentage) VALUES ('2024-01-02 16:01:10.037','Programming 1', 'mikevandercaaij@gmail.com', 100)
INSERT INTO Percentage (EnrollmentDate,CourseName,Email, Percentage) VALUES ('2024-11-21 20:23:58.767','Programming 1', 'mickjholster@gmail.com', 100)
INSERT INTO Percentage (EnrollmentDate,CourseName,Email, Percentage) VALUES ('2024-11-23 12:41:10.727','Programming 1', 'n.gambino@student.nl', 100)
INSERT INTO Percentage (EnrollmentDate,CourseName,Email, Percentage) VALUES ('2024-01-02 08:26:55.543','Programming 1', 'r.crabtree@student.nl', 100)
INSERT INTO Percentage (EnrollmentDate,CourseName,Email, Percentage) VALUES ('2024-11-21 14:11:43.678','Programming 1', 'r.cusnir@student.nl', 100)
INSERT INTO Percentage (EnrollmentDate,CourseName,Email, Percentage) VALUES ('2024-11-23 13:51:10.067','Programming 1', 'kaspervandenenden@gmail.com', 0)

-- Student_Content
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('dhanitaelman@gmail.com', 0, 100) 
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('bertajumper@gmail.com', 0, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 0, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('mickjholster@gmail.com', 0, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.crabtree@student.nl', 0, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('n.gambino@student.nl', 0, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.cusnir@student.nl', 0, 100)

INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('dhanitaelman@gmail.com', 2, 100) 
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('bertajumper@gmail.com', 2, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 2, 57)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('mickjholster@gmail.com', 2, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.crabtree@student.nl', 2, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('n.gambino@student.nl', 2, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.cusnir@student.nl', 2, 100)

INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('dhanitaelman@gmail.com', 4, 100) 
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('bertajumper@gmail.com', 4, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 4, 0)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('mickjholster@gmail.com', 4, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.crabtree@student.nl', 4, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('n.gambino@student.nl', 4, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.cusnir@student.nl', 4, 100)

INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('dhanitaelman@gmail.com', 6, 100) 
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('bertajumper@gmail.com', 6, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 6, 0)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('mickjholster@gmail.com', 6, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.crabtree@student.nl', 6, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('n.gambino@student.nl', 6, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.cusnir@student.nl', 6, 100)

INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('dhanitaelman@gmail.com', 8, 100) 
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('bertajumper@gmail.com', 8, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 8, 0)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('mickjholster@gmail.com', 8, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.crabtree@student.nl', 8, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('n.gambino@student.nl', 8, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 10, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 10, 0)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('martinbosma@gmail.com', 10, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.crabtree@student.nl', 10, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('n.gambino@student.nl', 10, 100)
INSERT INTO Student_Content (Email, ModuleItemID, Progress) VALUES ('r.cusnir@student.nl', 10, 100)

INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('dhanitaelman@gmail.com', 1, 17) 
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('bertajumper@gmail.com', 1, 68)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('martinbosma@gmail.com', 1, 80)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('n.gambino@student.nl', 1, 26)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.davidowitz@student.nl', 1, 83)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('s.chua@student.nl', 1, 38)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cockle@student.nl', 1, 27)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('p.forsyth@student.nl', 1, 93)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cusnir@student.nl', 1, 83)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.donahue@student.nl', 1, 62)

INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('jensdevlaming@gmail.com', 3, 72) 
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('bertajumper@gmail.com', 3, 15)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('kaspervandenenden@gmail.com', 3, 53)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('martinbosma@gmail.com', 3, 36)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('n.gambino@student.nl', 3, 26)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.davidowitz@student.nl', 3, 83)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('s.chua@student.nl', 3, 34)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cockle@student.nl', 3, 97)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('p.forsyth@student.nl', 3, 67)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cusnir@student.nl', 3, 41)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.donahue@student.nl', 3, 11)

INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('jensdevlaming@gmail.com', 5, 11) 
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('bertajumper@gmail.com', 5, 23)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('martinbosma@gmail.com', 5, 57)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('martinbosma@gmail.com', 5, 48)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('n.gambino@student.nl', 5, 92)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.davidowitz@student.nl', 5, 30)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('s.chua@student.nl', 5, 18)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cockle@student.nl', 5, 74)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('p.forsyth@student.nl', 5, 49)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cusnir@student.nl', 5, 84)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.donahue@student.nl', 5, 100)

INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('jensdevlaming@gmail.com', 7, 12) 
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('bertajumper@gmail.com', 7, 26)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('martinbosma@gmail.com', 7, 81)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('mickjholster@gmail.com', 7, 21)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('n.gambino@student.nl', 7, 21)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.davidowitz@student.nl', 7, 5)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('s.chua@student.nl', 7, 18)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cockle@student.nl', 7, 74)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('p.forsyth@student.nl', 7, 12)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.cusnir@student.nl', 7, 23)
INSERT INTO Student_Content (Email, WebcastItemID, Progress) VALUES ('r.donahue@student.nl', 7, 58)

-- Query's tables
SELECT * FROM Certificate
SELECT * FROM ContactPerson
SELECT * FROM Course
SELECT * FROM Course_Module
SELECT * FROM Enrollment
SELECT * FROM Module
SELECT * FROM Subject
SELECT * FROM Percentage
SELECT * FROM Speaker
SELECT * FROM Student
SELECT * FROM Student_Content
SELECT * FROM Webcast