public class Book extends Resource {
    private final String publisher;
    private final int copy;
    private int availableCopy;

    public Book(String id, String name, String author, String publisher, int copy, String category, String library) {
        super(id, name, author, category, library);
        this.publisher = publisher;
        this.copy = copy;
        availableCopy = copy;
    }

    public int getCopy() {
        return copy;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getAvailableCopy() {
        return availableCopy;
    }

    public void setAvailableCopy(int availableCopy) {
        this.availableCopy = availableCopy;
    }
}