package test.librarymanagementsystem;

public class LocationViewModel {
    private Integer LocationID, Floor, CategoryID;
    private String Shelf;

    public LocationViewModel(Integer locationID, Integer floor, String shelf, Integer categoryID) {
        this.LocationID = locationID;
        this.Floor = floor;
        this.Shelf = shelf;
        this.CategoryID = categoryID;
    }

    public Integer getLocationID() {
        return this.LocationID;
    }

    public Integer getFloor() {
        return this.Floor;
    }

    public String getShelf() {
        return this.Shelf;
    }

    public Integer getCategoryID() {
        return this.CategoryID;
    }
}
