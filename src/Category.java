public class Category {
    private String adminName;
    private String adminPassword;
    private String id;
    private String categoryName;
    private String parentsCategory;

    public Category(String adminName, String adminPassword, String id, String categoryName, String parentsCategory) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.id = id;
        this.categoryName = categoryName;
        this.parentsCategory = parentsCategory;
        System.out.print(Main.SUCCESS);
    }
}