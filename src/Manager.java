class Manager extends User {
    String libraryId;

    public Manager(String id, String password, String firstName, String lastName, String libraryId) {
        super(id, password, firstName, lastName);
        this.libraryId = libraryId;
    }

    public String getLibraryId() {
        return libraryId;
    }
}