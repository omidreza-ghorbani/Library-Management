import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Return {
    public static void returnHandler(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];
        String resourceId = details[3];
        String resourceReturnDate = details[4];
        String resourceReturnTime = details[5];

        if (!User.userExists(userId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!LibraryManager.libraries.containsKey(libraryId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!Resource.resourceExists(libraryId, resourceId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        String borrowKey = userId + "_" + libraryId + "_" + resourceId;
        if (!Borrow.borrows.containsKey(borrowKey)) {
            System.out.print(Main.NOT_FOUND);return;}

        String BorrowDateAndTime = Borrow.borrows.get(borrowKey);
        String[] borrowDateComponents = BorrowDateAndTime.split("\\|");
        String dateBorrow = borrowDateComponents[1];
        String timeBorrow = borrowDateComponents[2];

        LocalDateTime borrowDateAndTime;
        LocalDateTime returnDateAndTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
            borrowDateAndTime = LocalDateTime.parse(dateBorrow + " " + timeBorrow, formatter);
            returnDateAndTime = LocalDateTime.parse(resourceReturnDate + " " + resourceReturnTime, formatter);
        } catch (Exception e) {
            return;
        }

        long elapsedHours = ChronoUnit.HOURS.between(borrowDateAndTime, returnDateAndTime);

        User user = User.users.get(userId);
        Resource resource = Resource.resources.get(Resource.getCompositeKey(libraryId, resourceId));
        long freeHours = 0;
        long latePenalty = 0;
        if (resource instanceof Book) {
            if (user instanceof Student) {
                freeHours = 10L * 24;
                latePenalty = computeTimeDifference(elapsedHours,freeHours)*50;
            } else if (user instanceof Staff || user instanceof Professor || user instanceof Manager) {
                freeHours = 14L * 24;
                latePenalty = computeTimeDifference(elapsedHours,freeHours)*100;
            }
        } else if (resource instanceof Thesis) {
            if (user instanceof Student) {
                freeHours = 7L * 24;
                latePenalty = computeTimeDifference(elapsedHours,freeHours)*50;
            } else if (user instanceof Staff || user instanceof Professor || user instanceof Manager) {
                freeHours = 10L * 24;
                latePenalty = computeTimeDifference(elapsedHours,freeHours)*100;
            }
        }

        Borrow.borrows.remove(borrowKey);
        if (latePenalty > 0) {
            user.setPenalized(true);
            System.out.println(latePenalty);return;}
        System.out.print(Main.SUCCESS);
    }

    public static long computeTimeDifference(long date1, long date2) {
        long timeDifference = date1 - date2;
        if (timeDifference < 0) {
            timeDifference = 0;
        }
        return timeDifference ;
    }

}
