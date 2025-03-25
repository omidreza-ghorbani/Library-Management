import java.util.HashMap;
public class Borrow {
    final static HashMap<String, String> borrows = new HashMap<>();

    public static void borrow_handler(String data) {
        String[] details = data.split("\\|");
        String userId       = details[0];
        String userPassword = details[1];
        String libraryId    = details[2];
        String bookId       = details[3];
        String dateBorrow   = details[4];
        String timeBorrow   = details[5];

        if (!User.userExists(userId)) {
            System.out.print(Main.NOT_FOUND);return;}
        if (!LibraryManager.libraries.containsKey(libraryId)) {
            System.out.print(Main.NOT_FOUND);return;}
        if (!Resource.resourceExists(libraryId, bookId)) {
            System.out.print(Main.NOT_FOUND);return;}
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(Main.INVALID_PASS);return;}
        if (isTreasureTrove(bookId) || isForSell(bookId)) {
            System.out.print(Main.NOT_ALLOWED);return;}

        String resourceKey = Resource.getCompositeKey(libraryId, bookId);
        Resource res = Resource.resources.get(resourceKey);

        String borrowKey = userId + "_" + libraryId + "_" + bookId;
        long userBorrowCount = borrows.keySet().stream()
                .filter(k -> k.startsWith(userId + "_"))
                .count();

        User user = User.users.get(userId);
        if (user instanceof Student && userBorrowCount >= 3) {
            System.out.print(Main.NOT_ALLOWED); return;}
        if (user instanceof Staff && userBorrowCount >= 5) {
            System.out.print(Main.NOT_ALLOWED); return;}

        if (res instanceof Book book){
            int totalCopies = book.getCopy();
            long currentlyBorrowed = borrows.keySet().stream()
                    .filter(k -> k.startsWith(resourceKey + "_"))
                    .count();
            if (currentlyBorrowed >= totalCopies) {
                System.out.print(Main.NOT_ALLOWED);
                return;
            }
        }

        if (res instanceof BookForSale bookForSale){
            int totalCopies = bookForSale.getCopy();
            long currentlyBorrowed = borrows.keySet().stream()
                    .filter(k -> k.startsWith(resourceKey + "_"))
                    .count();
            if (currentlyBorrowed >= totalCopies) {
                System.out.print(Main.NOT_ALLOWED);
                return;
            }
        }

        if(res instanceof Book || res instanceof BookForSale){
            long currentlyBorrowed = borrows.keySet().stream()
                    .filter(k -> k.endsWith("_" + libraryId + "_" + bookId))
                    .count();

            int totalCopies = (res instanceof Book) ? ((Book)res).getCopy() : ((BookForSale)res).getCopy();
            if (currentlyBorrowed >= totalCopies) {
                System.out.print(Main.NOT_ALLOWED); return;
            }
        }else if (res instanceof Thesis){
            boolean isAllowed = ((Thesis)res).isActive;
            if (!isAllowed) {
                System.out.print(Main.NOT_ALLOWED); return;
            }
        }

        // بررسی اینکه همین کاربر قبلاً همین منبع را قرض نگرفته باشد
        if (borrows.containsKey(borrowKey)) {
            System.out.print(Main.NOT_ALLOWED);
            return;
        }

        borrows.put(borrowKey, userId + "|" + dateBorrow + "|" + timeBorrow);
        System.out.print(Main.SUCCESS);
    }



    public static boolean isTreasureTrove(String id) {
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
