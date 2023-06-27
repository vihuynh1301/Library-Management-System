package test.librarymanagementsystem;

public class CategoryViewModel {
    private Integer ID, DaysOfBorrowing;
    private String Name;

    CategoryViewModel(){}
    CategoryViewModel(Integer id, String name, Integer daysOfBorrowing) {
        this.ID = id;
        this.Name = name;
        this.DaysOfBorrowing = daysOfBorrowing;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public Integer getDaysOfBorrowing() {
        return this.DaysOfBorrowing;
    }

}
