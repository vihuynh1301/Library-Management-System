/**NOTE: HOW TO DETERMINE WHICH USER IS THE ADMIN?*/

package test.librarymanagementsystem;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.transform.Result;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookController implements Initializable {
    @FXML
    private TableView<BookViewModel> bookTableView;
    @FXML
    private TableColumn<BookViewModel, Integer> bookIDTableColumn;
    @FXML
    private TableColumn<BookViewModel, String> titleTableColumn;
    @FXML
    private TableColumn<BookViewModel, String> publishDateTableColumn;
    @FXML
    private TableColumn<BookViewModel, Integer> publisherIDTableColumn;
    @FXML
    private TableColumn<BookViewModel, Integer> copyTableColumn;
    @FXML
    private TableColumn<BookViewModel, Integer> categoryTableColumn;
    @FXML
    private TableColumn<BookViewModel, Integer> locationTableColumn;

    ObservableList<BookViewModel> bookViewModelObservableList = FXCollections.observableArrayList();
    @FXML
    private TextField keywordTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField categoryTextField;
    @FXML
    private TextField updateTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private Label status;
    @FXML
    private Button reserveButton;
    @FXML
    private Button submitButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button historyButton;
    private String usernameString;
    private String updateString;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getDBConnection();

        String bookViewQuery = "select ID, Title, PublisherID, PublicationDate, CopyNumber, CategoryID, LocationID from book";

        try{
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(bookViewQuery);

            while(queryOutput.next()) {
                bookViewModelObservableList.add(new BookViewModel(queryOutput.getInt("ID"),
                        queryOutput.getString("Title"),
                        queryOutput.getInt("PublisherID"),
                        queryOutput.getDate("PublicationDate"),
                        queryOutput.getInt("CopyNumber"),
                        queryOutput.getInt("CategoryID"),
                        queryOutput.getInt("LocationID")
                ));
            }

            bookIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
            publisherIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("PublisherID"));
            publishDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("PublicationDate"));
            copyTableColumn.setCellValueFactory(new PropertyValueFactory<>("CopyNumber"));
            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryID"));
            locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("LocationID"));

            bookTableView.setItems(bookViewModelObservableList);

            FilteredList<BookViewModel> filteredData = new FilteredList<>(bookViewModelObservableList, b-> true);
            keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(BookViewModel -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (BookViewModel.getID().toString().indexOf(searchKeyword) > -1) {
                        return true;
                    } else if (BookViewModel.getTitle().toLowerCase().indexOf(searchKeyword) > -1) {
                        return true;
                    } else return false;
                });
            });

            categoryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(BookViewModel -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                        return true;
                    }
                    String categoryKeyWord = newValue.toLowerCase();
                    if (BookViewModel.getCategoryID().toString().equals(categoryKeyWord)) return true;
//                    else if (new CategoryViewModel().getName().toLowerCase().indexOf(categoryKeyWord) >-1) return true;
                    else return false;
                });
            });
//            authorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//                filteredData.setPredicate(BookViewModel -> {
//                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
//                        return true;
//                    }
//                    try {
//                        String authorKeyWord = newValue.toLowerCase();
//                        Statement s = connectDB.createStatement();
//                        ResultSet r = s.executeQuery("Select BookViewModel.ID from Book LEFT OUTER JOIN AuthorBookViewModel ON AuthorBookViewModel.Book_ID = BookViewModel.ID LEFT Outer JOIn AuthorViewModel ON AuthorViewModel.ID = AuthorBookViewModel.Author_ID");
//                    }catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//
//                    r.
//
//                    if (CategoryViewModel.getName().toString().indexOf(authorKeyWord) > -1) return true;
//                    else return false;
//                });
//            });

            SortedList<BookViewModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(bookTableView.comparatorProperty());
            bookTableView.setItems(sortedData);

            //Set username, have to enter to store the name to usernameString
            usernameTextField.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    usernameString = usernameTextField.getText();
                }
            });

            updateTextField.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    updateString = updateTextField.getText();
                }
            });

            //Hit the reserve Button and change status label
            reserveButton.setOnAction(new EventHandler<ActionEvent>() { // check user in user table
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (sortedData.size() == 1 && usernameString != "") {
                        System.out.println("RESERVE BUTTON TEST");
                        String reserveBook = "EXEC reserve_a_book " + sortedData.get(0).getID() + ", '" + usernameString +"'";
                        try {
                            Statement statement = connectDB.createStatement();
                            statement.execute(reserveBook);
                            System.out.println("TEst 2" + sortedData.get(0).getCopyNumber());
                            if (sortedData.get(0).getCopyNumber() > 0) {
                                status.setText("Reserve Successfully");
                            }
                            else {
                                System.out.println("TEst 3");
                                status.setText("Reserve Failed");
                            }

                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else status.setText("Failed");
                }
            });



            /**Delete a book, not perfect since cannot check if user is admin */
            deleteButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    if (sortedData.size() == 1 && usernameString != "") {
                        System.out.println("HERE?");
                        try {
                            Statement statement = connectDB.createStatement();
                            statement.execute("EXEC delete_book '" + sortedData.get(0).getTitle() +"'");
                            status.setText("Delete Successfully");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else status.setText("Unable to Delete");
                }
            });

            /**Update book, including title, copynum, catID, locID. Not perfect because cannot know who is admin */
            updateButton.setOnAction(new EventHandler<ActionEvent>() { // instruction for update
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (usernameString != "" && updateString != "") {
                        try {
                            Statement statement = connectDB.createStatement();
                            statement.execute("EXEC update_book " + updateString);
                        }catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

//            submitButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("Reload book view");
//                reload(connectDB);
//            }
//        });

//            reserveButtonClick(status.getOnMouseClicked(), sortedData, connectDB);
//        }catch(SQLException e){
//                Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, e);
//                System.out.println(e.getMessage());
//                e.printStackTrace();
//            }
//        }
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }



//    /** Reload by hitting submit
//     *
//     * @param connectDB
//     */
//    public void reload(Connection connectDB) {
//        System.out.println("HERE");
//        App.main(null);





//        bookTableView.setItems(bookViewModelObservableList.remove(BookViewModel));
//        System.out.println("TEST AFTER CLEAR");
//
//        String bookView = "select ID, Title, PublisherID, PublicationDate, CopyNumber, CategoryID, LocationID from book";
//
//        try{
//            Statement statement = connectDB.createStatement();
//            ResultSet qoutput = statement.executeQuery(bookView);
//            System.out.println("Into try catch");
//            while(qoutput.next()) {
//                bookViewModelObservableList.add(new BookViewModel(qoutput.getInt("ID"),
//                        qoutput.getString("Title"),
//                        qoutput.getInt("PublisherID"),
//                        qoutput.getDate("PublicationDate"),
//                        qoutput.getInt("CopyNumber"),
//                        qoutput.getInt("CategoryID"),
//                        qoutput.getInt("LocationID")
//                ));
//            }
//            System.out.println("ADDED column");
//            bookIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
//            titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
//            publisherIDTableColumn.setCellValueFactory(new PropertyValueFactory<>("PublisherID"));
//            publishDateTableColumn.setCellValueFactory(new PropertyValueFactory<>("PublicationDate"));
//            copyTableColumn.setCellValueFactory(new PropertyValueFactory<>("CopyNumber"));
//            categoryTableColumn.setCellValueFactory(new PropertyValueFactory<>("CategoryID"));
//            locationTableColumn.setCellValueFactory(new PropertyValueFactory<>("LocationID"));
//
//            bookTableView.setItems(bookViewModelObservableList);
//
//            FilteredList<BookViewModel> filteredData = new FilteredList<>(bookViewModelObservableList, b-> true);
//            keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//                filteredData.setPredicate(BookViewModel -> {
//                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null) {
//                        return true;
//                    }
//                    String searchKeyword = newValue.toLowerCase();
//                    if (BookViewModel.getID().toString().indexOf(searchKeyword) > -1) {
//                        return true;
//                    } else if (BookViewModel.getTitle().toLowerCase().indexOf(searchKeyword) > -1) {
//                        return true;
//                    } else return false;
//                });
//            });
//
//            SortedList<BookViewModel> sortedData = new SortedList<>(filteredData);
//            sortedData.comparatorProperty().bind(bookTableView.comparatorProperty());
//            bookTableView.setItems(sortedData);
//
//            usernameTextField.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    usernameString = usernameTextField.getText();
//                    System.out.println("Username: " + usernameString);
//                }
//            });
//
//            //Hit the reserve Button and change status label
//            reserveButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    if (sortedData.size() == 1 && usernameString != "") {
//                        System.out.println("RESERVE BUTTON TEST");
//                        String reserveBook = "EXEC reserve_a_book " + sortedData.get(0).getID() + ", '" + usernameString +"'";
//                        try {
//                            Statement statement = connectDB.createStatement();
//                            statement.execute(reserveBook);
//                            System.out.println("TEst 2" + sortedData.get(0).getCopyNumber());
//                            if (sortedData.get(0).getCopyNumber() > 0) {
//                                status.setText("Success");
//                            }
//                            else {
//                                System.out.println("TEst 3");
//                                status.setText("Failed");
//                            }
//
//                        } catch (SQLException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else status.setText("Failed");
//                }
//            });
//
//            submitButton.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent actionEvent) {
//                    System.out.println("Reload book view");
//                    reload(connectDB);
//                }
//            });
//
////            reserveButtonClick(status.getOnMouseClicked(), sortedData, connectDB);
//        }catch(SQLException e){
//            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, e);
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//
//        }
    }
