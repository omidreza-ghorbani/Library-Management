import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

class ReadTreasure {
    public static void readHandler(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];
        String resourceId = details[3];
        String readDate = details[4]; // قالب: yyyy-MM-dd
        String readTime = details[5]; // قالب: HH:mm

        if (!User.userExists(userId)) {
            System.out.print(Main.NOT_FOUND);
            return;
        }
        if (!LibraryManager.libraries.containsKey(libraryId)) {
            System.out.print(Main.NOT_FOUND);
            return;
        }
        if (!Resource.resourceExists(libraryId, resourceId)) {
            System.out.print(Main.NOT_FOUND);
            return;
        }
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(Main.INVALID_PASS);
            return;
        }

        User user = User.users.get(userId);
        Resource resource = Resource.resources.get(Resource.getCompositeKey(libraryId, resourceId));

        if (!(resource instanceof TreasureTrove)) {
            System.out.print(Main.NOT_ALLOWED);
            return;
        }
        if (!(user instanceof Professor)) {
            System.out.print(Main.NOT_ALLOWED);
            return;
        }
        if (user.getPenalized()) {
            System.out.print(Main.NOT_ALLOWED);
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime requestTime = LocalDateTime.parse(readDate + " " + readTime, formatter);

        String resourceKey = Resource.getCompositeKey(libraryId, resourceId);

        if (TreasureLock.isLocked(resourceKey, requestTime)) {
            System.out.print(Main.NOT_ALLOWED);
            return;
        }

        TreasureLock.lock(resourceKey, requestTime);
        System.out.print(Main.SUCCESS);
    }
}

class TreasureLock {
    private static final HashMap<String, LocalDateTime> lockMap = new HashMap<>();

    public static void lock(String resourceKey, LocalDateTime requestTime) {
        lockMap.put(resourceKey, requestTime.plusHours(2));
    }

    public static boolean isLocked(String resourceKey, LocalDateTime requestTime) {
        if (!lockMap.containsKey(resourceKey)) {
            return false;
        }
        LocalDateTime unlockTime = lockMap.get(resourceKey);
        if (requestTime.isBefore(unlockTime)) {
            return true;
        }
        return false;
    }
}
