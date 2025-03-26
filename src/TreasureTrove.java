public class TreasureTrove extends Resource {
    private String publisher;
    private String bookDonor;
    public TreasureTrove(String id, String name, String author, String publisher, String datePublication, String bookDonor, String category, String library) {
        super(id, name, author, datePublication, category, library);
        this.publisher = publisher;
        this.bookDonor = bookDonor;}
    public String getPublisher() {return publisher;}
}
