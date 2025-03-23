public class Book extends Resource {
    private String publisher;
    private int copy;
    public Book(String id, String name, String author, String publisher, String datePublication, int copy, String category, String library) {
        super(id, name, author, datePublication, category, library);
        this.publisher = publisher;
        this.copy = copy;
    }
}
