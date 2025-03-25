public class Library {
    private String id;
    private String name;
    private String date;
    private String address;
    private final int deskNumber;
    public Library(String id, String name, String date, int deskNumber, String address) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.address = address;
        this.deskNumber = deskNumber;
    }
}