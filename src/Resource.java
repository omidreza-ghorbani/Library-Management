import java.util.HashMap;
import java.util.Iterator;
public class Resource {
    private String id;
    private String name;
    private String author;
    private String datePublication;
    private String category;
    private String library;
    final static HashMap<String, Resource> resources = new HashMap<>( );
    public Resource(String id, String name, String author, String datePublication, String category, String library) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.datePublication = datePublication;
        this.category = category;
        this.library = library;
    }

    public String getId() {return id;}

    static void addResource(String data, String type) {
        String[] detail = data.split("\\|");
        String adminName = detail[0];
        String adminPassword = detail[1];
        String id = detail[2];
        String title = detail[3];
        String author = detail[4];
        String datePublication = detail[6];
        String category;
        String library;
        if(type.equals("treasureTrove")) {
            category = detail[8];
            library = detail[9];
        } else if(type.equals("forSale")) {
            category = detail[10];
            library = detail[11];
        } else if(type.equals("book")) {
            category = detail[8];
            library = detail[9];
        } else {
            category = detail[7];
            library = detail[8];
        }

        if (!User.userExists(adminName)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!CategoryManager.categories.containsKey(category) && !category.equals("null")) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!LibraryManager.libraries.containsKey(library)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!(User.users.get(adminName) instanceof Manager)) {
            System.out.print(Main.PERMISSION);return;}

        if (!managerInLibrary(adminName, library)) {
            System.out.print(Main.PERMISSION);return;}

        if (!User.checkPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        if (resourceExists(library,id)) {
            System.out.print(Main.DUPLICATE);return;}

        Resource newResource = null;
        if (type.equals("book")) {
            newResource = new Book(id, title, author, detail[5], datePublication, Integer.parseInt(detail[7]), category, library);
        } else if (type.equals("forSale")) {
            newResource = new BookForSale(id, title, author, detail[5], datePublication, Integer.parseInt(detail[7]), detail[8], detail[9], category, library);
        } else if (type.equals("thesis")) {
            newResource = new Thesis(id, title, author, detail[5], datePublication, category, library);
        } else if (type.equals("treasureTrove")) {
            newResource = new TreasureTrove(id, title, author, detail[5], datePublication, detail[7], category, library);
        }

        String key = getCompositeKey(library, id);
        resources.put(key, newResource);
        System.out.print(Main.SUCCESS);
    }

    public static boolean resourceExists(String libraryId, String bookId) {
        String key = getCompositeKey(libraryId, bookId);
        return resources.containsKey(key);
    }

    static void removeResource(String data) {
        String[] detail = data.split("\\|");
        String adminName = detail[0];
        String adminPassword = detail[1];
        String id = detail[2];
        String library = detail[3];

        if (!User.userExists(adminName)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!LibraryManager.libraries.containsKey(library)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!managerInLibrary(adminName, library)) {
            System.out.print(Main.PERMISSION);return;}

        if (!(User.users.get(adminName) instanceof Manager)) {
            System.out.print(Main.PERMISSION);return;}

        if (!resourceExists(library,id)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!User.checkPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        removeHelper(id, library);
        System.out.print(Main.SUCCESS);
    }

    // متدی برای تولید کلید ترکیبی:
    public static String getCompositeKey(String libraryId, String bookId) {
        return libraryId + "_" + bookId;
    }

    static boolean managerInLibrary(String id, String library) {
        User user = User.users.get(id);
        if (user instanceof Manager) {
            Manager manager = (Manager) user;
            return manager.getLibraryId().equals(library);
        }
        return false;
    }

    static void removeHelper(String id, String library) {
        Iterator<Resource> iterator = resources.values().iterator();
        while (iterator.hasNext()) {
            Resource resource = iterator.next();
            if (resource.id.equals(id) && resource.library.equals(library)) {
                iterator.remove();
            }
        }
    }
}
