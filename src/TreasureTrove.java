class TreasureTrove extends Resource {
    private final String publisher;

    public TreasureTrove(String id, String name, String author, String publisher, String category, String library) {
        super(id, name, author, category, library);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }
}
