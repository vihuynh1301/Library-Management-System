package test.librarymanagementsystem;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Date;

public class BookViewModel {
    Integer ID, CopyNumber, CategoryID, LocationID, PublisherID;
    String Title;
    Date PublicationDate;
    public BookViewModel(){}

    public BookViewModel(Integer bookID, String titleName, Integer pubID, Date date, Integer copyNum, Integer categoryID, Integer locationID) {
        this.ID = bookID;
        this.Title = titleName;
        this.CopyNumber = copyNum;
        this.CategoryID = categoryID;
        this.LocationID = locationID;
        this.PublisherID = pubID;
        this.PublicationDate = date;
    }

    public Integer getID() {
        return this.ID;
    }

    public Integer getCopyNumber() {
        return this.CopyNumber;
    }

    public Integer getCategoryID() {
        return this.CategoryID;
    }

    public Integer getLocationID() {
        return this.LocationID;
    }

    public Integer getPublisherID() {
        return this.PublisherID;
    }
    public Date getPublishDate() {
        return this.PublicationDate;
    }

    public String getTitle() {
        return this.Title;
    }

    public void setBookID(Integer bookID) {
        this.ID = bookID;
    }

    public void setCopy(Integer copy) {
        this.CopyNumber = copy;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public void setPublisher(Integer publisher) {
        this.PublisherID = publisher;
    }

    public void setCategory(Integer category) {
        this.CategoryID = category;
    }

    public void setLocation(Integer location) {
        this.LocationID = location;
    }

    public void setPublishDate(Date publishDate) {
        this.PublicationDate = publishDate;
    }
}
