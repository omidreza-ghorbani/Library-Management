public class Thesis extends Resource {
    private String advisor;
    public boolean isActive;
    public Thesis(String id, String name, String author, String advisor, String datePublication, String category, String library) {
        super(id, name, author, datePublication, category, library);
        this.advisor = advisor;
        isActive = true;}
    public String getAdvisor() {return advisor;}
}
