package test.librarymanagementsystem;

public class AuthorViewModel {
    Integer ID;
    String Name, Status;

    public AuthorViewModel() {}
    public AuthorViewModel(Integer id, String name, String status) {
        this.ID = id;
        this.Name = name;
        this.Status = status;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public String getStatus() {
        return this.Status;
    }

    public void setID(Integer id) {
        this.ID = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setStatus(String status) {
        this.Status = status;
    }
}
