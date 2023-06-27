package terminal;

import test.librarymanagementsystem.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import static terminal.OperationHandler.*;

public class Driver {
    private static final int EXIT = 12;
    private static boolean isContinued = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int operation = -1;
        while (isContinued) {
            printMenu();
            System.out.print("Type in the number of operation that you want to execute: ");
            try {
                operation = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input Type");
                continue;
            }

            try {
                handleOperation(operation);
            } catch (SQLException e) {
                System.out.println("Operation Denied, Try again!");
            }
            if (!isContinued) break;

            System.out.println();
            System.out.print("Continue? Y/N: ");
            String answer = scanner.next();
            if (answer.toLowerCase().indexOf("n") == 0) {
                isContinued = false;
            } else if (answer.toLowerCase().indexOf("y") == 0) {
                isContinued = true;
            }
            else {
                System.out.println("Wrong Input Type");
                isContinued = false;
            }
            System.out.println();
        }
        System.out.println("Exit successfully");
    }

    private static void printMenu() {
        System.out.println("OPERATION AVAILABILITY:");
        System.out.println("1. Insert A New Book");
        System.out.println("2. List Books from Category");
        System.out.println("3. List available books for Reservation and their categories");
        System.out.println("4. Reserve A Book");
        System.out.println("5. Top 3 most polular books reserved");
        System.out.println("6. List Books from Author");
        System.out.println("7. Reservation History of a User");
        System.out.println("8. Remove a Book from Library Database");
        System.out.println("9. Update a Book's attributes");
        System.out.println("10. The most popular catergory based on books reserved");
        System.out.println("11. List Users reserving books from an Author");
        System.out.println("12. Exit");
    }

    private static void handleOperation(int operation) throws SQLException {
        if (operation <= 0 || operation > EXIT ) {
            System.out.println("Invalid Input Type");
            return;
        }

        if (operation == EXIT) {
            isContinued = false;
            return;
        }

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connection = connectNow.getDBConnection();

        try {
            connection.setAutoCommit(false);
            switch (operation){
                case 1 -> newBook();
                case 2 -> bookFromCategory();
                case 3 -> availableBook();
                case 4 -> reserveBook();
                case 5 -> topBooks();
                case 6 -> bookFromAuthor();
                case 7 -> reserveHistory();
                case 8 -> deleteBook();
                case 9 -> updateBook();
                case 10 -> topCategory();
                case 11 -> reserveFromPublisher();
            }
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("Operation Failed");
            System.out.println(e);
        }

    }
}
