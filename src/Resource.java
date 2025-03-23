import java.util.HashMap;

public class Resource {
    private String id;
    private String name;
    private String author;
    private String datePublication;
    private String category;
    private String library;
    final static HashMap<String, Resource> resources = new HashMap<String, Resource>();

    public Resource(String id, String name, String author, String datePublication, String category, String library) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.datePublication = datePublication;
        this.category = category;
        this.library = library;
    }

    static void addBook(String data, String type) {
        String[] detail = data.split("\\|");
        String adminName = detail[0];
        String adminPassword = detail[1];
        String id = detail[3];
        String author = detail[4];
        String datePublication = detail[6];
        String category = detail[8];
        String library = detail[9];

        if (!User.userExists(adminName)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!CategoryManager.categories.containsKey(id)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!LibraryManager.libraries.containsKey(library)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!adminName.equals(Main.ADMIN_STR)) {
            System.out.print(Main.PERMISSION);return;}

        if (!User.checkPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        if (resourceExists(id)) {
            System.out.print(Main.DUPLICATE);return;}




        System.out.println(Main.SUCCESS);
    }

    static boolean resourceExists(String id) {
        return resources.containsKey(id);
    }
}
