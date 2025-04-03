class BookForSale extends Resource {
    private final String publisher;
    private int copy;
    private final long price;
    private final int discountPercentage;
    private long finalPrice;

    public BookForSale(String id, String name, String author, String publisher, int copy, String category, String library, long price, int discountPercentage) {
        super(id, name, author, category, library);
        this.publisher = publisher;
        this.copy = copy;
        this.price = price;
        this.discountPercentage = discountPercentage;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getCopy() {
        return copy;
    }

    public void setCopy(int newCopy) {
        this.copy = newCopy;
    }

    public long getFinalPrice() {
        finalPrice = price - (price * discountPercentage) / 100;
        return finalPrice;
    }
}