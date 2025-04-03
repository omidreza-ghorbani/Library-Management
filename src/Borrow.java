import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

class Borrow {
    final static HashMap<String, String> borrows = new HashMap<>();

    public static void borrowHandler(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];
        String bookId = details[3];
        String dateBorrow = details[4];
        String timeBorrow = details[5];

        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }
        if (!Library.libraries.contains(libraryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }
        if (!Resource.resourceExists(libraryId, bookId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }
        if (isTreasureTrove(bookId) || isForSell(bookId)) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }

        String resourceKey = Resource.getCompositeKey(libraryId, bookId);
        Resource res = Resource.resources.get(resourceKey);
        User user = User.users.get(userId);

        String borrowKey = userId + "_" + libraryId + "_" + bookId;
        long userBorrowCount = borrows.keySet().stream().filter(k -> k.startsWith(userId + "_")).count();
        if (user instanceof Student && userBorrowCount >= 3) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }
        if ((user instanceof Staff || user instanceof Manager || user instanceof Professor) && userBorrowCount >= 5) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }

        if (res instanceof Book book) {
            if (book.getAvailableCopy() <= 0) {
                System.out.print(ResponseMessage.NOT_ALLOWED);
                LogEntry.addLogEntry(data, "borrow", "failed", "none");
                return;
            }
        }

        if (user.getPenalized()) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }

        if (res instanceof Book book) {
            if (book.getAvailableCopy() <= 0) {
                System.out.print(ResponseMessage.NOT_ALLOWED);
                LogEntry.addLogEntry(data, "borrow", "failed", "none");
                return;
            }
        } else if (res instanceof Thesis) {
            boolean isAllowed = ((Thesis) res).isActive;
            if (!isAllowed) {
                System.out.print(ResponseMessage.NOT_ALLOWED);
                LogEntry.addLogEntry(data, "borrow", "failed", "none");
                return;
            }
        }

        if (borrows.containsKey(borrowKey)) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "borrow", "failed", "none");
            return;
        }

        borrows.put(borrowKey, userId + "|" + dateBorrow + "|" + timeBorrow);
        if (res instanceof Book book) {
            book.setAvailableCopy(book.getAvailableCopy() - 1);
        } else if (res instanceof Thesis thesis) {
            thesis.setActive(false);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
        LocalDateTime ts = LocalDateTime.parse(dateBorrow + " " + timeBorrow, formatter);
        LogEntry.addLogEntry(data, "borrow", "success", "none");
        System.out.print(ResponseMessage.SUCCESS);
    }


    public static boolean isTreasureTrove(String id) {
        for (Resource resource : Resource.resources.values()) {
            if (resource.getId().equals(id) && resource instanceof TreasureTrove) {
                return true;
            }
        }
        return false;
    }

    public static boolean isForSell(String id) {
        for (Resource resource : Resource.resources.values()) {
            if (resource.getId().equals(id) && resource instanceof BookForSale) {
                return true;
            }
        }
        return false;
    }
}