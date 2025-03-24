import java.util.HashMap;
public class Borrow {
    final static HashMap<String,String> borrows = new HashMap<String,String>();

    public static void borrow_handler(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String library = details[2];
        String bookId = details[3];

        if (!User.userExists(userId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!Resource.resourceExists(library,bookId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!LibraryManager.libraries.containsKey(library)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!User.checkPassword(userId, userPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        if (isGanjineh(bookId) || isForSell(bookId)) {
            System.out.print(Main.ALLOW);return;}



        borrows.put(userId,library);
        System.out.print(Main.SUCCESS);
    }


    public static void return_handler(String date) {

    }

    public static boolean isGanjineh(String id) {
        for (Resource resource : Resource.resources.values()) {
            if (resource.getId().equals(id) && resource instanceof TreasureTrove) {
                return true;
            }
        }return false;
    }

    public static boolean isForSell(String id) {
        for (Resource resource : Resource.resources.values()) {
            if (resource.getId().equals(id) && resource instanceof BookForSale) {
                return true;
            }
        }return false;
    }
}
