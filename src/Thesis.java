public class Thesis extends Resource {
    private String Advisor;
    public boolean isActive;
    public Thesis(String id, String name, String author, String advisor, String datePublication, String category, String library) {
        super(id, name, author, datePublication, category, library);
        Advisor = advisor;
        isActive = true;
    }
}
