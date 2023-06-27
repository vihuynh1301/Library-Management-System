package test.librarymanagementsystem;

public class UserInfoViewModel {
    public Integer ID;
    public String Name, Address, Type;

    public UserInfoViewModel(){}
    public UserInfoViewModel(Integer id, String name, String address, String type) {
        this.ID = id;
        this.Name = name;
        this.Address = address;
        this.Type = address;
    }

    public Integer getID() {
        return this.ID;
    }

    public String getName() {
        return this.Name;
    }

    public String getAddress() {
        return this.Address;
    }

    public String getType() {
        return this.Type;
    }

    public void setID(Integer id) {
        this.ID = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public void setType (String type) {
        this.Type = type;
    }
}
