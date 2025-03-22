public class Category {
    private String admin_name2;
    private String admin_password2;
    private String id;
    private String category_name;
    private String parentsCategory;

    public Category(String admin_name2, String admin_password2, String id, String category_name, String parentsCategory) {
        this.admin_name2 = admin_name2;
        this.admin_password2 = admin_password2;
        this.id = id;
        this.category_name = category_name;
        this.parentsCategory = parentsCategory;
        System.out.print(Main.SUCCESS_STR);
    }
}