import java.util.HashMap;

public class CategoryManager {
    final static private HashMap<String, Category> categories = new HashMap<>();
    static public void addCategory(String data) {
        String[] details = data.split("\\|");
        String adminName = details[0];
        String adminPassword = details[1];
        String id = details[2];
        String categoryName = details[3];
        String parentCategoryId = details[4];

        if (categories.containsKey(id)) {
            System.out.print(Main.DUPLICATE);
            return;}
        if (!parentCategoryId.equals("null") && !categories.containsKey(parentCategoryId)) {
            System.out.print(Main.NOT_FOUND);
            return;}
        Category category = new Category(adminName, adminPassword, id, categoryName, parentCategoryId);
        categories.put(id, category);
        System.out.print(Main.SUCCESS);
    }
}
