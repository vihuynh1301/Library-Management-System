package test.librarymanagementsystem;

public class PublisherViewModel {
    private Integer ID;
    private String Name, Contact;

    public PublisherViewModel(Integer id, String name, String contact) {
        this.ID = id;
        this.Name = name;
        this.Contact = contact;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public String getContact() {
        return this.Contact;
    }
}
