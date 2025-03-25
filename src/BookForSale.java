public class BookForSale extends Resource {
    private String publisher;
    private int copy;
    private String Price;
    private String discountRate;

    public BookForSale(String id, String name, String author, String publisher, String datePublication, int copy, String price, String discountRate, String category, String library) {
        super(id, name, author, datePublication, category, library);
        this.publisher = publisher;
        this.copy = copy;
        Price = price;
        this.discountRate = discountRate;
    }
    public int getCopy() {return copy;}
}
