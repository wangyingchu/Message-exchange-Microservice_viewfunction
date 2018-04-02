use messagedb;
CREATE TABLE Notification (groupId VARCHAR(100) NOT NULL, notificationContent LONGTEXT NOT NULL, notificationHandleable BOOL NOT NULL, notificationId VARCHAR(50) NOT NULL, notificationReadStatus BOOL NOT NULL, notificationReceiverId VARCHAR(100) NOT NULL, notificationReceiversId LONGTEXT NOT NULL, notificationReceiversName LONGTEXT NOT NULL, notificationReceiversType LONGTEXT NOT NULL, notificationScope VARCHAR(100) NOT NULL, notificationSenderId VARCHAR(100) NOT NULL, notificationSenderName VARCHAR(100) NOT NULL, notificationSentDate DATETIME NOT NULL, notificationStatus VARCHAR(100) NOT NULL, notificationTitle LONGTEXT NOT NULL, notificationType VARCHAR(100) NOT NULL, PRIMARY KEY (notificationId)) ENGINE=InnoDB
;

CREATE TABLE Message (groupId VARCHAR(100) NOT NULL, messageContent LONGTEXT NOT NULL, messageId VARCHAR(50) NOT NULL, messageReadStatus BOOL NOT NULL, messageReceiverId VARCHAR(100) NOT NULL, messageReceiversId LONGTEXT NOT NULL, messageReceiversName LONGTEXT NOT NULL, messageReceiversType LONGTEXT NOT NULL, messageScope VARCHAR(100) NOT NULL, messageSenderId VARCHAR(100) NOT NULL, messageSenderName VARCHAR(100) NOT NULL, messageSentDate DATETIME NOT NULL, messageTitle LONGTEXT NOT NULL, PRIMARY KEY (messageId)) ENGINE=InnoDB
;

CREATE TABLE ActivityTaskNotificationInfo (activityId VARCHAR(100) NOT NULL, activityName VARCHAR(500) NOT NULL, infoId VARCHAR(100) NOT NULL, notificationId VARCHAR(50) NOT NULL, taskDescription LONGTEXT NULL, taskDueDate DATETIME NULL, taskDueStatus VARCHAR(100) NULL, taskName VARCHAR(500) NOT NULL, taskRole VARCHAR(200) NULL, PRIMARY KEY (infoId)) ENGINE=InnoDB
;

CREATE TABLE ExternalResourceNotificationInfo (infoId VARCHAR(100) NOT NULL, notificationId VARCHAR(50) NOT NULL, resourceName VARCHAR(1000) NOT NULL, resourceURL VARCHAR(1000) NOT NULL, PRIMARY KEY (infoId)) ENGINE=InnoDB
;

ALTER TABLE ActivityTaskNotificationInfo ADD UNIQUE (notificationId)
;

ALTER TABLE ActivityTaskNotificationInfo ADD FOREIGN KEY (notificationId) REFERENCES Notification (notificationId)
;

ALTER TABLE ExternalResourceNotificationInfo ADD UNIQUE (notificationId)
;

ALTER TABLE ExternalResourceNotificationInfo ADD FOREIGN KEY (notificationId) REFERENCES Notification (notificationId)
;

CREATE TABLE AUTO_PK_SUPPORT (  TABLE_NAME CHAR(100) NOT NULL,  NEXT_ID BIGINT NOT NULL, UNIQUE (TABLE_NAME))
;

DELETE FROM AUTO_PK_SUPPORT WHERE TABLE_NAME IN ('ActivityTaskNotificationInfo', 'ExternalResourceNotificationInfo', 'Message', 'Notification')
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('ActivityTaskNotificationInfo', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('ExternalResourceNotificationInfo', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('Message', 200)
;

INSERT INTO AUTO_PK_SUPPORT (TABLE_NAME, NEXT_ID) VALUES ('Notification', 200)
;

