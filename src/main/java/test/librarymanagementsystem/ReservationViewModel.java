package test.librarymanagementsystem;
import java.sql.Date;
public class ReservationViewModel {
    private Integer ReservationID, UserID, BookID, CategoryID;
    private Date ReservationDay, ExpirationDay;

    public ReservationViewModel() {}
    public ReservationViewModel(Integer reservationID, Integer userID, Integer bookID, Integer categoryID, Date reservationDay, Date expirationDay) {
        this.ReservationID = reservationID;
        this.UserID = userID;
        this.BookID = bookID;
        this.CategoryID = categoryID;
        this.ReservationID = reservationID;
        this.ExpirationDay = expirationDay;
    }

    public Integer getReservationID() {
        return this.ReservationID;
    }

    public Integer getUserID() {
        return this.UserID;
    }

    public Integer getBookID() {
        return this.BookID;
    }

    public Integer getCategoryID() {
        return this.CategoryID;
    }

    public Date getReservationDay() {
        return this.ReservationDay;
    }

    public Date getExpirationDay() {
        return this.ExpirationDay;
    }
}
