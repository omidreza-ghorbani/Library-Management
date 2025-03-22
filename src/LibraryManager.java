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
            System.out.println(Main.NOT_FOUND);}

        if (!adminName.equals(Main.ADMIN_STR)) {
           System.out.println(Main.PERMISSION);}

        if (!User.checkPassword(adminName, adminPassword)) {
            System.out.println(Main.INVALID_PASS);}

        if (libraries.containsKey(id)) {
            System.out.print(Main.DUPLICATE);
            return;}

        Library library = new Library(id, name, date, desk_number, address);
        libraries.put(id, library);

        System.out.print(Main.SUCCESS);
    }
}
