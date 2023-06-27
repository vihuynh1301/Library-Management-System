package terminal;

import test.librarymanagementsystem.DatabaseConnection;

import java.sql.*;
import java.util.Scanner;

public class OperationHandler {
    private final static Scanner scanner;
    static {
        scanner = new Scanner(System.in);
    }

    private static String getLine(){
        String input = "";
        while (input.equals("") && scanner.hasNextLine()) {
            input = scanner.nextLine();
        }
        return input;
    }

    //Case 1
    public static void newBook() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query =  "DECLARE @LocationID INT\n" +
                        "EXEC InsertNewBook\n" +
                        "    @Title = ?,\n" +
                        "    @Author = ?,\n" +
                        "    @Publisher = ?,\n" +
                        "    @PublicationDate = ?,\n" +
                        "    @CopiesAvailable = ?,\n" +
                        "    @Category = ?,\n" +
                        "    @LocationID = @LocationID OUTPUT";

        System.out.print("Title: ");
        String title = getLine();

        System.out.print("Author: ");
        String author = getLine();

        System.out.print("Publisher: ");
        String publisher = getLine();

        System.out.print("Publication Date (YYYY-MM-DD): ");
        String date = getLine();

        System.out.print("Copy Number: ");
        int no = scanner.nextInt();

        System.out.print("Category: ");
        int catID = scanner.nextInt();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, publisher);
        preparedStatement.setDate(4, Date.valueOf(date));
        preparedStatement.setInt(5, no);
        preparedStatement.setInt(6, catID);

        ResultSet resultSet = preparedStatement.executeQuery();
        connection.commit();
        while (resultSet.next()) {
            System.out.println("Book ID: " + resultSet.getString(1)
                    + ", Title: " + resultSet.getString(2)
                    + ", Publisher: " + resultSet.getString(3)
                    + ", Publication Date: " + resultSet.getString(4)
                    + ", Copy Number: " + resultSet.getString(5)
                    + ", Category: " + resultSet.getString(6)
                    + ", Floor: " + resultSet.getString(7)
                    + ", Shelf: " + resultSet.getString(8)
                    + ", LocationID: " + resultSet.getString(9)
            );
        }
        System.out.println();
    };

    //Case 2
    public static void bookFromCategory() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query = "EXEC select_books_by_category ";

        System.out.print("Category ID: ");
        String aptId = getLine();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query + aptId);
        connection.commit();
        while (resultSet.next()) {
            System.out.println("Category ID: " + resultSet.getString(1)
                    + ", Name: " + resultSet.getString(2)
                    + ", Book ID: " + resultSet.getString(3)
                    + ", Book Title: " + resultSet.getString(4)
                    + ", Copy Number: "  + resultSet.getString(7));
        }
        System.out.println("Successfully generated Data");

    };

    //Case 3
    public static void availableBook() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("EXEC available");
        System.out.println();

        connection.commit();
        while (resultSet.next()) {
            System.out.println("Title: " + resultSet.getString(1)
                    + ", Category: " + resultSet.getString(2));
        }
    };

    //Case 4
    public static void  reserveBook() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query = "EXEC reserve_a_book ?, ?";

        //Show books before make reservation, delete, update,
        Statement s = connection.createStatement();
        ResultSet bookSet = s.executeQuery("select * from book");
        while (bookSet.next()) {
            System.out.println("Book ID: " + bookSet.getString(1)
                    + ", Title: " + bookSet.getString(2)
                    + ", Publisher: " + bookSet.getString(3)
                    + ", Publish Date: "  + bookSet.getString(4)
                    + ", Number of Copies: " + bookSet.getString(5)
                    + ", Category: " + bookSet.getString(6)
                    + ", Location: " + bookSet.getString(7)
            );
        }

        System.out.print("Book ID to reserve: ");
        int ID = scanner.nextInt();

        System.out.print("Username Confirmation: ");
        String user = getLine();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ID);
        preparedStatement.setString(2, user);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println( resultSet.getString(1));
        }
        connection.commit();
        System.out.println("Reserve successful");

    };

    //Case 5
    public static void topBooks() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("EXEC popular_reservation");

        connection.commit();
        while (resultSet.next()) {
            System.out.println("Title: " + resultSet.getString(1)
                    + ", Borrowed Times: " + resultSet.getString(2)
                    + ", Category: " + resultSet.getString(3)
                    + ", Copy Number Left: "  + resultSet.getString(4)
                    + ", Floor: " + resultSet.getString(5)
                    + ", Shelf: " + resultSet.getString(6)
            );
        }
    };

    //Case 6
    public static void bookFromAuthor() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query = "EXEC findbooksbyauthor ";

        System.out.print("Author Name: ");
        String name = getLine();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query + name );
        connection.commit();
        while (resultSet.next()) {
            System.out.println("Title: " + resultSet.getString(1)
                    + ", Name: " + resultSet.getString(2)
                    + ", Publisher: " + resultSet.getString(3)
            );
        }

        System.out.println("Successfully generated Data");
    };

    //Case 7
    public static void reserveHistory() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query = "EXEC user_reservation ";

        System.out.print("Username: ");
        String name = getLine();
        System.out.println(name);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query + "'"+ name + "'");
        connection.commit();
        while (resultSet.next()) {
            System.out.println("Name: " + resultSet.getString(1)
                    + ", Title: " + resultSet.getString(2)
                    + ", Reserve Day: " + resultSet.getString(3)
                    + ", Expiration Day: " + resultSet.getString(4)
                    + ", Floor: " + resultSet.getString(5)
                    + ", Shelf: " + resultSet.getString(6)
            );
        }
        System.out.println("Successfully generated Data");
    };

    //Case 8
    public static void deleteBook() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query =  "DECLARE @result_message NVARCHAR(100);\n" +
                        "EXECUTE delete_book ?, @result_message OUTPUT;\n" +
                        "SELECT @result_message AS Result;\n ";

        System.out.print("Book Title: ");
        String title = getLine();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, title);

        ResultSet resultSet = preparedStatement.executeQuery();

        connection.commit();
        while (resultSet.next()) {
            System.out.println( resultSet.getString(1));
        }

    };

    //Case 9
    public static void updateBook() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query = "EXEC updateBook ?, ?, ?, ?";

        System.out.print("Book Title: ");
        String title = getLine();

        System.out.print("Copy Number: ");
        int no = scanner.nextInt();

        System.out.print("Category ID: ");
        int catID = scanner.nextInt();

        System.out.print("Location ID: ");
        int locationID = scanner.nextInt();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, no);
        preparedStatement.setInt(3, catID);
        preparedStatement.setInt(4, locationID);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println( resultSet.getString(1));
        }
        connection.commit();
    };

    //Case 10
    public static void topCategory() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("EXEC findmostpopularcategory");

        connection.commit();
        while (resultSet.next()) {
            System.out.println("Category Name: " + resultSet.getString(1)
                    + ", Borrowed Times: " + resultSet.getString(2)
            );
        }
    };

    //Case 11
    public static void reserveFromPublisher() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();
        String query = "EXEC findusersbyauthor ";

        System.out.print("Author Name: ");
        String name = getLine();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query + name);
        connection.commit();
        while (resultSet.next()) {
            System.out.println("User ID: " + resultSet.getString(1)
                    + ", Username: " + resultSet.getString(2)
                    + ", Address: " + resultSet.getString(3)
                    + ", User Type: " + resultSet.getString(4)
            );
        }
        //connection.commit();
        System.out.println("Successfully generated Data");
    };
}
