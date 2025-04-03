class Thesis extends Resource {
    private final String advisor;
    public boolean isActive;

    public Thesis(String id, String name, String author, String advisor, String category, String library) {
        super(id, name, author, category, library);
        this.advisor = advisor;
        isActive = true;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}