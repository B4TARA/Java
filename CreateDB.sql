use master;

CREATE DATABASE Hotels;
use Hotels;
DROP DATABASE Hotels;

go

CREATE TABLE Users
(
Id int constraint PK_USERS primary key (Id) identity(1,1),
IsAdmin bit NOT NULL default '0',
UserLogin varchar(25) NOT NULL UNIQUE,
UserPassword varchar(60)  NOT NULL UNIQUE,
EMail nvarchar(254)  NOT NULL UNIQUE
)

CREATE TABLE Hotels
(
Id int constraint PK_HOTELS primary key (Id) identity(1,1),
Rooms int NOT NULL,
Rating decimal(2,1) NOT NULL,
Description varchar(500) NOT NULL
)

--drop table Feedbacks;
CREATE TABLE Feedbacks
(
Id int constraint PK_FEEDBACKS primary key(Id) identity(1,1),
UserId int NOT NULL  constraint FK_FEEDBACKS_USERS foreign key (UserId) references Users(Id) ON DELETE CASCADE,
HotelId int NOT NULL  constraint FK_FEEDBACKS_HOTELS foreign key (HotelId) references Hotels(Id) ON DELETE CASCADE,
FeedbackDate date NOT NULL,
Comment varchar(500) NOT NULL
)

