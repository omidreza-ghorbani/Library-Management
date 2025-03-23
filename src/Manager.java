public class Manager extends User {
    String libraryId;
    public Manager(String id, String password, String firstName, String lastName, String nationalCode, String birthDate, String address, String libraryId) {
        super(id, password, firstName, lastName, nationalCode, birthDate, address);
        this.libraryId = libraryId;

    }
    public String getLibraryId() {return libraryId;}
}
