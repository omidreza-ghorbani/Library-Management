import java.util.HashMap;
public class LibraryManager {
    final static HashMap<String, Library> libraries = new HashMap<>();

    static public void addLibrary(String data) {
        String[] details = data.split("\\|");
        String adminName = details[0];
        String adminPassword = details[1];
        String id = details[2];
        String name = details[3];
        String date = details[4];
        final int desk_number = Integer.parseInt(details[5]);
        String address = details[6];

        if(!User.userExists(adminName)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!adminName.equals(Main.ADMIN_STR)) {
           System.out.print(Main.PERMISSION);return;}

        if (!User.checkPassword(adminName, adminPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        if (libraries.containsKey(id)) {
            System.out.print(Main.DUPLICATE);return;}

        Library library = new Library(id, name, date, desk_number, address);
        libraries.put(id, library);

        System.out.print(Main.SUCCESS);return;
    }
}
