public class Library {
    private String id;
    private String name;
    private String date;
    private String address;
    private final int desk_number;

    public Library(String id, String name, String date, int desk_number, String address) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.address = address;
        this.desk_number = desk_number;
        System.out.print(Main.SUCCESS_STR);
    }
}