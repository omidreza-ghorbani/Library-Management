import java.util.HashMap;

public class LibraryManager {
    final static private HashMap<String, Library> libraries = new HashMap<>();
    static public void addLibrary(String data) {
        String[] details = data.split("\\|");
        String id = details[0];
        String name = details[1];
        String date = details[2];
        final int desk_number = Integer.parseInt(details[3]);
        String address = details[4];

        if (libraries.containsKey(id)) {
            System.out.print(Main.DUPLICATE_STR);
            return;}
        Library library = new Library(id, name, date, desk_number, address);
        libraries.put(id, library);
        System.out.print(Main.SUCCESS_STR);
    }
}
