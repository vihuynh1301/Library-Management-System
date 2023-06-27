# Library-Management-System
A library management using Java, JDBC, AWS and SQL. Demo with app view and terminal interaction.

# Author: Vi Huynh
# Professors: Dianna Foreback, Harold Connamacher, Yinghui Wu.
Spring 2023

Information: 

Name of Virtual Machines: csdswinlab032
Username: student
Password: vNc47TjRGmFPPbJH 
Instructions: Please Look at CSDS 341 - Final Project - Library Management System Database Instructions that is attached under
ZIP file name: LibraryManagementSystem.zip
Database’s related information:
Username: team5
Password: csds341!
Server: (AWS RDS) team5csds341fp.cagsdrstrnzv.us-east-2.rds.amazonaws.com
Port Number: 1433

Database description and design (updated):
Description and Relationships:
Description:

Book relation: This table stores information about books, including the book ID, title, publisherID, publication date, and number of copies available, categoryID, locationID.

Author relation: This table stores information about authors, including authorID, name, status.

Publisher relation: This table stores information about publishers, including their publisherID, name and contact information.

User relation: This table stores information about library users, including userID, their name, address, type of user (admin/user) information.

Category relation: This table stores information about the categories of books available in the library, including their category ID, name, days of borrowing.

Reservation relation: This table stores information about reservations made by users, including the userID, bookID, reservationID, categoryID, reservation date, and expiration date.

Location relation: This table stores information about the location of the book, including locationID, categoryID, floor, shelf.


Relationship (updated): 
Aside from the relationship that our group decided in the First Report, we decided to add more relationships between Reservation and Category. Regarding the relationships between tables, we have the following relationships between relations:

Reservation has [((zero or many) to one] relationships with Category. One reservation only has 1 Category, but one category can be included in many reservations.


ER-diagram (updated)

NULL Data:  BOOK: publisherID, publication date,
 	 	USER: address 
		Author: status
		Publisher: Contact

Queries overview (updated)
Each member creates 2 (or more) use cases and queries to accommodate this use case. Your use cases should cover insert, update, delete, and select actions.
Added cases involving at least 2-tables queries

Use Case 1: Insert a new book record into the database 
Input required: (Title, Author, Publisher, PublicationDate, CopiesAvailable, Category, LocationID)
In this use case, we are inserting a new book record into the database. Upon insertion, a new record will be added to the Book table containing information about the book such as the book title, author, publisher, publication date, and number of copies available. Additionally, a new record for the book's author will be automatically added to the Author table and a connection between the book and author will be added to the Author_Book table. Furthermore, a new publisher record will be created and added to the Publisher table. The procedure will also find the location associated with the new book's category. If the book's category is new, a new location ID will be created and output. Otherwise, the location ID associated with the book's category will be used.
Stored procedure syntax: 
DECLARE @LocationID INT
EXEC InsertNewBook
    @Title = 'inp_Title',
    @Author = 'inp_Author',
    @Publisher = 'inp_Publisher',
    @PublicationDate = 'inp_PublicationDate',
    @CopiesAvailable = inp_CopiesAvailable,
    @Category = 'inp_Science Fiction',
    @LocationID = @LocationID OUTPUT	

Use Case 2: Select all books in the Books table with a specific category
Input required: Category
In this use case, we are retrieving a list of books that belong to a specific category. The stored procedure takes in the categoryID as input and selects all the books that belong to that category from the Books table. The output includes relevant information about each book, such as the title, author, publisher, publication date, and the number of copies available.

Stored procedure syntax example:
EXEC select_books_by_category 10; 

User Case 3: Retrieve a list of books that are available for borrowing and their respective categories.
Input required: None
In this use case, we want to retrieve a list of all books that are currently available for borrowing and their respective categories. To accomplish this, we join the Book and Category tables using the categoryID. The query filters the results based on the number of copies available, ensuring we only include books with at least one copy available for borrowing.

Stored procedure syntax: 
	EXEC available

User Case 4: Reserve a book in the database
Input required: bookID, username
In this use case, we are reserving a book for a specific user. The procedure will first check if the user exists in the UserInfo database. If the user does not exist, the procedure will return an error message 'User not found'. If the user exists, the procedure will check if the book has any available copies before inserting a new reservation record. 
If there are no available copies, the procedure will output an error message 'No available copies'. If there are available copies, the procedure will reduce the number of available copies of the book and insert a new reservation record with the user's information and set the reservedate to be the current datetime.

Stored procedure syntax 
	EXEC reserve_a_book book_id, ‘username’

Use Case 5: Create a new table to track the popularity of books in the library based on the number of times they have been borrowed
Input required: None
This stored procedure creates a new table to track the popularity of books in the library based on the number of times these books have been borrowed. The procedure will return the top 3 most borrowed books along with their respective title, category, number of copies left, floor, and shelf information.

Stored Procedure Syntax
	EXEC popular_reservation

User Case 6: Find all books written by a specific author 
Input required: author name
In this user case, we are interested in finding all the books written by a specific author along with the respective publishers' names. The query joins the Book, Author, and Publisher tables using their IDs to fetch the required information. We filter the results by the desired author name in the WHERE clause.

Stored Procedures Syntax
EXEC findbooksbyauthor 'douglas'

User Case 7 : Retrieve a list of all reserved books for a specific user.
Input required: Username
This user case aims to provide a list of all the books reserved by a particular user along with their locations in the library (floor and shelf). We join the Reservation, Book, and Location tables using their respective IDs. The results are filtered by the specific userID in the WHERE clause.

Stored Procedures Syntax
	EXEC user_reservation 'Derrick Nguyen'
User case 8: Delete a book out of the database
Input required: Book title
In this use case, we are deleting a book and its associated information from the database, including the author, author_book records, and any reservations made for the book. The stored procedure will take the book name as input and perform the necessary deletion steps. Before deleting the book, the procedure will first check if the book exists in the Book table and if there are any reservations associated with it. If the book does not exist or has reservations, an appropriate error message will be returned. Otherwise, the procedure will delete the book record, along with any associated author_book records and reservations.

Stored Procedures Syntax
	DECLARE @result_message NVARCHAR(100);
EXECUTE delete_book 'Book Title', @result_message OUTPUT;
SELECT @result_message AS Result;

User case 9: Update a book
Input required: book title, copyNumber, categoryID, locationID
In this use case, we are updating the book information with the input parameters of copyNumber, categoryID, and locationID. This will be useful when there are changes in book arrangements in the library or when the library is receiving new books.

Stored Procedure Syntax
	EXEC updatebook 'Trung dep zai', 20, 1,1

User Case 10 : Find the most popular category based on the number of reservations.
Input required: None
Here, we want to identify the top 3 popular book categories by calculating the number of reservations for each category. The Category and Reservation tables are joined using the categoryID. The query groups the results by the category name and counts the reservationID to obtain the reservation count for each category. The ORDER BY clause sorts the results in descending order, and the TOP 3 clause ensures we only get the most popular category.\
Stored Procedure Syntax
	EXEC findmostpopularcategory

User Case 11: Find all library users who have borrowed books from a specific author.
Input required: author name

This user case is designed to provide a list of all library users who have borrowed books published by a specific author. We join the User, Reservation, Book, Author_Book, Author tables using their name. The DISTINCT keyword ensures that each user appears only once in the list. The results are filtered by the specific publisher name in the WHERE clause.
Stored Procedure Syntax
	EXEC findusersbyauthor 'Douglas A'


Physical Database Design


Indexes
idx_author_to_books 
This index would allow for faster retrieval of all the books written by a particular author, as the database engine can use the index to quickly locate all the book IDs associated with a given author ID. 
idx_categoryid_to_shelf
This index would allow for faster retrieval of the shelf location associated with a particular category ID, as the database engine can use the index to quickly locate the relevant row(s) in the "location" table and retrieve the corresponding shelf information. 
idx_user_to_reservation
This index would allow for faster retrieval of the book IDs associated with a particular user ID, as the database engine can use the index to quickly locate the relevant rows in the "reservation" table and retrieve the corresponding book IDs

Triggers
Integrity Constraint Trigger:

Trigger name: PreventNegativeCopies
Trigger type: After Insert, Update
Table: Book
Condition: CopiesAvailable < 0

Description: The PreventNegativeCopies trigger is an integrity constraint trigger that prevents users from inserting or updating a book record with a negative value for the number of available copies. If the trigger condition is met, the trigger rolls back the transaction and displays the message "Cannot insert a book with a negative number of copies available" to the user.

Notification Trigger:

Trigger name: PreventDeletingPublisher
Trigger type: Instead Of Delete
Table: Publisher
Condition: EXISTS (SELECT * FROM Book WHERE Book.PublisherID = Publisher.ID)

Description: The PreventDeletingPublisher trigger is a notification trigger that prevents a user from deleting a publisher record if any books are associated with it. If the trigger condition is met, the trigger rolls back the transaction and displays the message "Cannot delete publisher record because associated books exist" to the user. This trigger is useful for maintaining referential integrity between the Publisher and Book tables.

Delete trigger:
Trigger name: DeleteBookReservation Trigger type: After Delete Table: Book
Description: The DeleteBookReservation trigger is an after delete trigger that deletes all reservations associated with a book when it is deleted from the database. The trigger searches for any reservation records that reference the deleted book by its ID and deletes them. This ensures that the database maintains referential integrity and prevents orphaned records. This trigger is useful for ensuring that reservations are removed when the associated book is no longer available for borrowing.







Functional Dependencies and Normalization 


R(Book): BCNF
ID -> Title
ID -> PublisherID
ID -> PublicationDate
ID -> Copy
ID -> CategoryID
ID -> LocationID 

R(User): BCNF
because Contact is a transitive attribute so the table is in 2NF. If we want it to be BCNF then we have to decompose it into 2 tables: User_name(ID, Name) and User_contact(Contact,Name). Suggestions: change contact into something that is not unique like the number of books reserved
ID -> Name, 
ID -> Address
ID -> Type

R(Reservation): 2NF
ReservationID -> UserID
ReservationID -> BookID
ReservationID -> CategoryID
ReservationID -> Reserve Day; 
ReservationID -> ExpirationDay,
BookID} -> CategoryID (Transitive)

R(Author): BCNF
ID -> Name
ID -> Status

R(Author_Book): 1NF
No corresponding functional dependencies, this table is used to match the author and the book and contains only two attributes ( Book_ID, Author_ID)

R(Publisher): BCNF
ID -> Name
ID -> Contact

R(Category): 2NF because name is transitive 
ID -> Name
Name ->ID
ID -> Days of Borrowing
Name -> Days of Borrowing

R(Location): BCNF
LocationID -> Floor
LocationID -> Shelf
LocationID -> CategoryID



Generated ER Diagram on SQLExpress




Desired Application:



Instructions

NOTES: 
This Instruction is specifically designed to be used in the Virtual Machine of Case Western Reserve University. Username and Password can be found in CSDS341 - Final Report that submitted with this Instructions file.
We will leave Intellij application running in the background of the VM so you don’t have to set up the environment. 

Retrieving Data using Terminal of Intellij in Virtual Machine
Run Driver that can be found in classpath Downloads/LibraryManagementSystem/src/main/java/terminal.
Look at the Terminal, you should see 12 options shown similar to attached picture below:

Retrieving Data from Local Machine
After downloading LibraryManagementSystem.zip and unzip. Go to Intellj and open the folder that you have just unzipped. 
Download Driver for SQL Server with Google (Firefox, Safari, etc) here. And download file .zip. Unzip.
Go to Project Structure > Modules > Dependencies > + > JARs or Directories > (go to the driver unzipped folder, select mssql-jdbc-12.2.0.jre11.jar) > Change Module SDK to 
SDK19. > Apply > OK
Go to Project Structure > Project > Set SDK 19, Language Level 11.
To run, you can follow instructions from part II and III as above.
(Since we host databases on AWS RDS, which is the online database tool, we can run the application without the help of Virtual Machine. Since we develop this application using Intellij, we will give out instructions to run on Intellij.)


# [STORED PROCEDURE ENCODING]
InsertNewBook procedure
ALTER PROCEDURE InsertNewBook
	@Title NVARCHAR(50),
	@Author NVARCHAR(50),
	@Publisher NVARCHAR(50),
	@PublicationDate DATE,
	@CopiesAvailable INT,
	@Category NVARCHAR(50),
	@LocationID INT OUTPUT
AS
BEGIN
	SET NOCOUNT ON;
	-- Check if author exists
	DECLARE @AuthorID INT
	SELECT @AuthorID = ID FROM Author WHERE Name = @Author

	IF @AuthorID IS NULL
	BEGIN
    	-- Insert new author
    	INSERT INTO Author (Name) VALUES (@Author)
    	SET @AuthorID = SCOPE_IDENTITY()
	END

	-- Check if category exists
	DECLARE @CategoryID INT
	SELECT @CategoryID = ID FROM Category WHERE Name = @Category

	IF @CategoryID IS NULL
	BEGIN
    	-- Insert new category
    	INSERT INTO Category (Name, DaysOfBorrowing) VALUES (@Category, 14)
    	SET @CategoryID = SCOPE_IDENTITY()
	END

   	 -- Check if publisher exists
	DECLARE @publisherID INT
	SELECT @PublisherID = ID FROM publisher WHERE Name = @publisher

	IF @publisherID IS NULL
	BEGIN
    	-- Insert new publisher
    	INSERT INTO publisher (Name) VALUES (@Publisher)
    	SET @publisherID = SCOPE_IDENTITY()
	END
	-- Find available location
	SELECT TOP 1 @LocationID = LocationID
	FROM Location
	WHERE CategoryID = @CategoryID
	AND NOT EXISTS (
    	SELECT *
    	FROM Book
    	WHERE LocationID = Location.LocationID
	)

	-- If no available location found, insert new location
	IF @LocationID IS NULL
	BEGIN
    	INSERT INTO Location (Floor, Shelf, CategoryID) VALUES ((SELECT MAX(Floor) + 1 FROM Location), 'A1', @CategoryID)
    	SET @LocationID = SCOPE_IDENTITY()
	END

	-- Insert new book
	INSERT INTO Book (Title, PublisherID, PublicationDate, CopyNumber, CategoryID, LocationID)
	VALUES (@Title, @PublisherID, @PublicationDate, @CopiesAvailable, @CategoryID, @LocationID)

	SELECT b.ID, b.Title, p.Name AS Publisher, b.PublicationDate, b.CopyNumber, c.Name AS Category, l.Floor, l.Shelf, l.LocationID
    FROM Book AS b
    JOIN Publisher AS p ON b.PublisherID = p.ID
    JOIN Category AS c ON b.CategoryID = c.ID
    JOIN Location AS l ON b.LocationID = l.LocationID
    WHERE b.ID = SCOPE_IDENTITY();

	-- Update author_book
	INSERT INTO Author_Book (Author_ID, Book_ID) VALUES (@AuthorID, SCOPE_IDENTITY())
END

select_books_by_category
alter PROCEDURE select_books_by_category
    @categoryid varchar(255)
AS	
BEGIN
    SELECT b.*, c.id, c.Name
    FROM Book b
    JOIN Category c ON b.categoryID = c.ID
    WHERE c.id = @categoryid;
END
available
CREATE or alter PROCEDURE available
AS
BEGIN
    SET NOCOUNT ON;

    SELECT Book.Title, Category.Name AS Category
    FROM Book
    INNER JOIN Category ON Book.CategoryID = Category.ID
    WHERE Book.CopyNumber > 0;
END

reserve_a_book
CREATE or alter PROCEDURE reserve_a_book
	@book_id int,
	@username nvarchar(50)	
AS
BEGIN
	DECLARE @category_id int, @copy_number int, @num_of_borrowing_days int, @expiration_day date, @user_id int;

	-- Check if book exists
	IF NOT EXISTS (SELECT 1 FROM Book WHERE ID = @book_id)
	BEGIN
    	SELECT 'Book is not available' AS Message;
    	RETURN;
	END

	-- Get the user ID from the username
	SELECT @user_id = ID FROM [UserInfo] WHERE name = @username;
	IF @user_id IS NULL
	BEGIN
    	SELECT 'User does not exist' AS Message;
    	RETURN;
	END

	-- Check if book has copies available
	SELECT @copy_number = CopyNumber FROM Book WHERE ID = @book_id;
	IF @copy_number = 0
	BEGIN
    	SELECT 'Run out of copies' AS Message;
    	RETURN;
	END

-- Reserve the book
	UPDATE Book SET CopyNumber = @copy_number - 1 WHERE ID = @book_id;
    
-- Determine the category ID and number of borrowing days
	SELECT @category_id = CategoryID FROM Book WHERE ID = @book_id;
	SELECT @num_of_borrowing_days = daysofborrowing FROM Category WHERE ID = @category_id;

-- Calculate the expiration day and insert the reservation record
	SET @expiration_day = DATEADD(day, @num_of_borrowing_days, GETDATE());
	INSERT INTO Reservation (UserID, BookID, CategoryID, ReserveDay, ExpirationDay) VALUES (@user_id, @book_id, @category_id, GETDATE(), @expiration_day);
END

popular_reservation
Create or alter procedure popular_reservation
AS
BEGIN
    select top 3 book.Title, count(bookid) as Borrowed_times, c.name as Category, book.CopyNumber as Number_of_copies_left, l.Floor, l.Shelf
    from reservation
    join book
    on Reservation.BookID = Book.ID
    join category as c
    on Reservation.CategoryID = c.id
    join Location as l
    on Book.LocationID = l.LocationID
    group by BookID, book.Title, c.Name, Book.CopyNumber, l.Floor,l.Shelf
    order by borrowed_times desc
End

findbooksbyauthor
CREATE PROCEDURE FindBooksByAuthor
    @AuthorName NVARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;

    SELECT b.Title, p.Name AS Publisher
    FROM Book AS b
    INNER JOIN Author_Book AS ab ON b.ID = ab.Book_ID
    INNER JOIN Author AS a ON ab.Author_ID = a.ID
    INNER JOIN Publisher AS p ON b.PublisherID = p.ID
    WHERE a.Name = @AuthorName
END


user_reservation
create procedure user_reservation 
	@username nvarchar(50)
as
begin
	set nocount on;

SELECT UserInfo.name, book.Title, ReserveDay, ExpirationDay, FLOOR, shelf 
FROM reservation 
inner join book on reservation.BookID = book.ID 
inner join location on location.LocationID = book.LocationID
join UserInfo on UserInfo.ID = Reservation.UserID
where UserInfo.Name = @username

End

findmostpopularcategory
CREATE or alter PROCEDURE FindMostPopularCategory
AS
BEGIN
    SET NOCOUNT ON;

    SELECT TOP 1 c.Name as Category_Name, COUNT(*) AS Number_Of_Reservations
    FROM Category c
    JOIN Book b ON b.CategoryID = c.ID
    JOIN Reservation r ON r.BookID = b.ID
    GROUP BY c.Name
    ORDER BY Number_Of_Reservations DESC
END


delete_book
CREATE OR ALTER PROCEDURE delete_book
    @book_title NVARCHAR(50),
    @message NVARCHAR(50) OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    BEGIN TRY
   	 BEGIN TRANSACTION;

   	 -- Get the book ID for the given title
   	 DECLARE @book_id INT;
   	 SELECT @book_id = ID FROM Book WHERE Title = @book_title;

   	 IF @book_id IS NULL
   	 BEGIN
   		 SET @message = 'Book not found.';
   	 END
   	 ELSE
   	 BEGIN
   		 -- Delete any associated records from Author_Book table
   		 DELETE FROM Author_Book WHERE Book_ID = @book_id;

   		 -- Delete any associated records from Reservation table
   		 DELETE FROM Reservation WHERE BookID = @book_id;

   		 -- Delete the record from Book table
   		 DELETE FROM Book WHERE ID = @book_id;

   		 COMMIT TRANSACTION;
   		 SET @message = 'Deletion Success';
   	 END
    END TRY
    BEGIN CATCH
   	 IF @@TRANCOUNT > 0 ROLLBACK TRANSACTION;
   	 SET @message = ERROR_MESSAGE();
    END CATCH
    SELECT @message AS 'Result';
END

trigger_delete_book_reservations
CREATE TRIGGER tr_delete_book_reservations
ON Book
AFTER DELETE
AS
BEGIN
  DELETE FROM Reservation
  WHERE BookID IN (SELECT ID FROM DELETED)
END

findmostpopularcategory
CREATE or alter PROCEDURE FindMostPopularCategory
AS
BEGIN
    SET NOCOUNT ON;

    SELECT TOP 1 c.Name as Category_Name, COUNT(*) AS Number_Of_Reservations
    FROM Category c
    JOIN Book b ON b.CategoryID = c.ID
    JOIN Reservation r ON r.BookID = b.ID
    GROUP BY c.Name
    ORDER BY Number_Of_Reservations DESC
END

findusersbyauthor
CREATE or alter PROCEDURE FindUsersByAuthor
	@AuthorName NVARCHAR(50)
AS
BEGIN
	SET NOCOUNT ON;
	-- Find the author ID for the given name
	DECLARE @AuthorID INT;
	SELECT @AuthorID = ID FROM Author WHERE Name = @AuthorName;

	-- Find all the books written by the author
	DECLARE @BooksWrittenByAuthor TABLE (ID INT);
	INSERT INTO @BooksWrittenByAuthor (ID)
	SELECT Book_ID FROM Author_Book WHERE Author_ID = @AuthorID;

	-- Find all the users who have borrowed the author's books
	SELECT Userinfo.*
	FROM UserInfo
	JOIN Reservation ON Userinfo.ID = Reservation.UserID
	JOIN Book ON Reservation.BookID = Book.ID
	WHERE Book.ID IN (SELECT ID FROM @BooksWrittenByAuthor)
END
Update Book
CREATE or alter PROCEDURE UpdateBook
    @Title NVARCHAR(50),
    @CopyNumber INT,
    @CategoryID INT,
    @LocationID INT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @BookID INT

    -- Check if the book exists in the Book relation
    IF NOT EXISTS(SELECT ID FROM Book WHERE Title = @Title)
    BEGIN
        SELECT 'BookNotFound'
        RETURN
    END

    -- Get the BookID of the book to update
    SET @BookID = (SELECT ID FROM Book WHERE Title = @Title)

    -- Update the Book relation with the new values
    UPDATE Book
    SET CategoryID = @CategoryID, LocationID = @LocationID
    WHERE ID = @BookID

    -- Return a success message
    SELECT 'Updated Successful'
END


