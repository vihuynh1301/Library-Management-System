package test.librarymanagementsystem;

public class AuthorBookViewModel {
    private Integer Book_ID, Author_ID;

    AuthorBookViewModel(Integer bookID, Integer authorID) {
        this.Book_ID = bookID;
        this.Author_ID = authorID;
    }

    public Integer getBook_ID() {
        return this.Book_ID;
    }

    public Integer getAuthor_ID() {
        return this.Author_ID;
    }
}
