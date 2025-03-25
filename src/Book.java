public class Book extends Resource {
    private final String publisher;
    private final int copy;
    public Book(String id, String name, String author, String publisher, String datePublication, int copy, String category, String library) {
        super(id, name, author, datePublication, category, library);
        this.publisher = publisher;
        this.copy = copy;}
    public int getCopy() {return copy;}
}
