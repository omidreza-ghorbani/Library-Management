public class Category {
    private String adminName;
    private String adminPassword;
    private String id;
    private String categoryName;
    private String parentsCategory;

    public Category(String adminName2, String adminPassword2, String id, String categoryName, String parentsCategory) {
        this.adminName = adminName2;
        this.adminPassword = adminPassword2;
        this.id = id;
        this.categoryName = categoryName;
        this.parentsCategory = parentsCategory;
        System.out.print(Main.SUCCESS);
    }
}