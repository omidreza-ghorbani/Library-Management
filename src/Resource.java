import java.util.HashMap;
class Resource {
    private final String id;
    private final String name;
    private final String author;
    private final String datePublication;
    private final String category;
    private final String library;
    final static HashMap<String, Resource> resources = new HashMap<>( );
    public Resource(String id, String name, String author, String datePublication, String category, String library) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.datePublication = datePublication;
        this.category = category;
        this.library = library;}
    public String getId() {return id;}
    public String getName() {return name;}
    public String getAuthor() {return author;}

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
        switch (type) {
            case "treasureTrove", "book":
                category = detail[8];
                library = detail[9];
                break;
            case "forSale":
                category = detail[10];
                library = detail[11];
                break;
            default:
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
        if (isNotManagerInLibrary(adminName, library)) {
            System.out.print(Main.PERMISSION);return;}
        if (User.isInvalidPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);return;}
        if (resourceExists(library,id)) {
            System.out.print(Main.DUPLICATE);return;}

        Resource newResource = null;
        switch (type) {
            case "book":
                newResource = new Book(id, title, author, detail[5], datePublication, Integer.parseInt(detail[7]), category, library);
                break;
            case "forSale":
                newResource = new BookForSale(id, title, author, detail[5], datePublication, Integer.parseInt(detail[7]), detail[8], detail[9], category, library);
                break;
            case "thesis":
                newResource = new Thesis(id, title, author, detail[5], datePublication, category, library);
                break;
            case "treasureTrove":
                newResource = new TreasureTrove(id, title, author, detail[5], datePublication, detail[7], category, library);
                break;
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
            System.out.print(Main.NOT_FOUND);
            return;
        }
        if (!LibraryManager.libraries.containsKey(library)) {
            System.out.print(Main.NOT_FOUND);
            return;
        }
        if (isNotManagerInLibrary(adminName, library)) {
            System.out.print(Main.PERMISSION);
            return;
        }
        if (!(User.users.get(adminName) instanceof Manager)) {
            System.out.print(Main.PERMISSION);
            return;
        }
        if (!resourceExists(library, id)) {
            System.out.print(Main.NOT_FOUND);
            return;
        }
        if (User.isInvalidPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);
            return;
        }

        String resourceKey = getCompositeKey(library, id);
        boolean isBorrowed = Borrow.borrows.keySet().stream()
                .anyMatch(k -> k.endsWith("_" + resourceKey));
        if (isBorrowed) {
            System.out.print(Main.NOT_ALLOWED);
            return;
        }

        removeHelper(id, library);
        System.out.print(Main.SUCCESS);
    }

    public static String getCompositeKey(String libraryId, String bookId) {
        return libraryId + "_" + bookId;}

    static boolean isNotManagerInLibrary(String id, String library) {
        User user = User.users.get(id);
        if (user instanceof Manager manager) {
            return !manager.getLibraryId().equals(library);
        }return true;
    }

    static void removeHelper(String id, String library) {
        String key = getCompositeKey(library, id);
        resources.remove(key);
    }

}