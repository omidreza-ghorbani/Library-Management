import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Report {

    public static void categoryReport(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String categoryId = details[2];
        String libraryId = details[3];
        List<String> targetCategories = new ArrayList<>();

        User user = User.users.get(userId);
        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!Library.libraries.contains(libraryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!categoryId.equals("null")) {
            if (!Category.categories.containsKey(categoryId)) {
                System.out.print(ResponseMessage.NOT_FOUND);
                return;
            }
            targetCategories = getAllSubcategories(categoryId);
        }
        if (!(user instanceof Manager)) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (!((Manager) user).getLibraryId().equals(libraryId)) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }

        int bookCount = 0;
        int thesisCount = 0;
        int treasureCount = 0;
        int saleBookCount = 0;

        for (Resource resource : Resource.resources.values()) {
            if (!resource.getLibrary().equals(libraryId)) continue;
            if (categoryId.equals("null")) {
                if (!resource.getCategory().equals("null")) continue;
            } else {
                if (!targetCategories.contains(resource.getCategory())) continue;
            }
            if (resource instanceof Book book) {
                bookCount += book.getAvailableCopy();
            } else if (resource instanceof Thesis thesis) {
                if (thesis.isActive) {
                    thesisCount++;
                }
            } else if (resource instanceof TreasureTrove) {
                treasureCount++;
            } else if (resource instanceof BookForSale bookForSale) {
                saleBookCount += bookForSale.getCopy();
            }

        }
        System.out.println(bookCount + " " + thesisCount + " " + treasureCount + " " + saleBookCount);
    }

    public static void libraryReport(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];

        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!Library.libraries.contains(libraryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }

        User user = User.users.get(userId);
        if (!(user instanceof Manager)) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (!((Manager) user).getLibraryId().equals(libraryId)) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }

        int normalBooks = 0;
        int theses = 0;
        int bookBorrowed = 0;
        int thesisBorrowed = 0;
        int treasureBooks = 0;
        int forSaleRemaining = 0;

        for (Resource resource : Resource.resources.values()) {
            if (!resource.getLibrary().equals(libraryId)) continue;
            if (resource instanceof Book book) {
                normalBooks += book.getAvailableCopy();
                bookBorrowed += (book.getCopy() - book.getAvailableCopy());
            } else if (resource instanceof Thesis thesis) {
                if (thesis.isActive) theses++;
                else {
                    thesisBorrowed++;
                }

            } else if (resource instanceof TreasureTrove) treasureBooks++;
            else if (resource instanceof BookForSale) forSaleRemaining += ((BookForSale) resource).getCopy();
        }
        System.out.println(normalBooks + " " + theses + " " + bookBorrowed + " " +
                thesisBorrowed + " " + treasureBooks + " " + forSaleRemaining);
    }

    public static void passedDeadline(String data) {
        String[] parts = data.split("\\|");
        String userId = parts[0];
        String userPass = parts[1];
        String libraryId = parts[2];
        String dateStr = parts[3];
        String timeStr = parts[4];

        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!Library.libraries.contains(libraryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }

        User user = User.users.get(userId);
        if (!(user instanceof Manager)) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (!((Manager) user).getLibraryId().equals(libraryId)) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (User.isInvalidPassword(userId, userPass)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");

        LocalDateTime currentTime = LocalDateTime.parse(dateStr + " " + timeStr, formatter);

        Set<String> lateResourceSet = new HashSet<>();

        for (String borrowKey : Borrow.borrows.keySet()) {
            String[] keyParts = borrowKey.split("_"); // userId_libraryId_resourceId
            if (keyParts.length != 3 || !keyParts[1].equals(libraryId)) continue;

            String resourceId = keyParts[2];
            String[] borrowInfo = Borrow.borrows.get(borrowKey).split("\\|");
            String borrowerId = borrowInfo[0];
            String borrowDate = borrowInfo[1];
            String borrowTime = borrowInfo[2];

            Resource resource = Resource.resources.get(Resource.getCompositeKey(libraryId, resourceId));
            User user1 = User.users.get(borrowerId);
            if (!(resource instanceof Book || resource instanceof Thesis)) continue;

            LocalDateTime borrowDateTime;
            try {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
                borrowDateTime = LocalDateTime.parse(borrowDate + " " + borrowTime, dtf);
            } catch (Exception e) {
                continue;
            }

            long allowedHours;
            if (resource instanceof Book) {
                allowedHours = (user1 instanceof Student) ? 10L * 24 : 14L * 24;
            } else {
                allowedHours = (user1 instanceof Student) ? 7L * 24 : 10L * 24;
            }

            LocalDateTime dueTime = borrowDateTime.plusHours(allowedHours);
            if (currentTime.isAfter(dueTime)) {
                lateResourceSet.add(resourceId);
            }
        }

        if (lateResourceSet.isEmpty()) {
            System.out.println("none");
            return;
        }
        List<String> lateResources = new ArrayList<>(lateResourceSet);
        lateResources.sort(String::compareTo);
        System.out.println(String.join("|", lateResources));
    }

    public static void penaltiesSumReport(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPass = details[1];

        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!userId.equals(ResponseMessage.ADMIN_STR.toString())) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (User.isInvalidPassword(userId, userPass)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }

        System.out.println(Return.totalPenalties);
    }


    private static List<String> getAllSubcategories(String parentId) {
        List<String> result = new ArrayList<>();
        result.add(parentId);
        for (String id : Category.categories.keySet()) {
            String categoryPr = Category.categories.get(id);
            if (categoryPr.equals(parentId)) {
                result.addAll(getAllSubcategories(id));
            }
        }
        return result;
    }

}
