import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Return {
    static long totalPenalties = 0;

    public static void returnHandler(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];
        String resourceId = details[3];
        String resourceReturnDate = details[4];
        String resourceReturnTime = details[5];

        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "return", "failed", "none");
            return;
        }
        if (!Library.libraries.contains(libraryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "return", "failed", "none");
            return;
        }
        if (!Resource.resourceExists(libraryId, resourceId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "return", "failed", "none");
            return;
        }
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            LogEntry.addLogEntry(data, "return", "failed", "none");
            return;
        }

        String borrowKey = userId + "_" + libraryId + "_" + resourceId;
        if (!Borrow.borrows.containsKey(borrowKey)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "return", "failed", "none");
            return;
        }

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
        long forReportMostPopular = ChronoUnit.MINUTES.between(borrowDateAndTime, returnDateAndTime);
        User user = User.users.get(userId);
        Resource resource = Resource.resources.get(Resource.getCompositeKey(libraryId, resourceId));
        long freeHours;
        long latePenalty = 0;
        if (resource instanceof Book book) {
            book.setAvailableCopy(book.getAvailableCopy() + 1);
            if (user instanceof Student) {
                freeHours = 10L * 24;
                latePenalty = computeTimeDifference(elapsedHours, freeHours) * 50;
            } else if (user instanceof Staff || user instanceof Professor || user instanceof Manager) {
                freeHours = 14L * 24;
                latePenalty = computeTimeDifference(elapsedHours, freeHours) * 100;
            }
        } else if (resource instanceof Thesis thesis) {
            thesis.setActive(true);
            if (user instanceof Student) {
                freeHours = 7L * 24;
                latePenalty = computeTimeDifference(elapsedHours, freeHours) * 50;
            } else if (user instanceof Staff || user instanceof Professor || user instanceof Manager) {
                freeHours = 10L * 24;
                latePenalty = computeTimeDifference(elapsedHours, freeHours) * 100;
            }
        }

        Borrow.borrows.remove(borrowKey);
        if (latePenalty > 0) {
            user.setPenalized(true);
            totalPenalties += latePenalty;
            System.out.println(latePenalty);
            LogEntry.addLogEntry(data, "return", "success", String.valueOf(forReportMostPopular));
            return;
        }
        LogEntry.addLogEntry(data, "return", "success", String.valueOf(forReportMostPopular));
        System.out.print(ResponseMessage.SUCCESS);
    }

    public static long computeTimeDifference(long date1, long date2) {
        long timeDifference = date1 - date2;
        if (timeDifference < 0) {
            timeDifference = 0;
        }
        return timeDifference;
    }
}