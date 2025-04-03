import java.util.ArrayList;
import java.util.HashMap;

public class LogEntry {
    static final HashMap<String, ArrayList<ArrayList<String>>> logEntries = new HashMap<>();

    public static void addLogEntry(String data, String type, String status, String minutesOrPrice) {
        String[] details = data.split("\\|");
        ArrayList<String> information = new ArrayList<>();
        String[] typeDetails = type.split("-");
        String userId;
        String libraryId;
        String resourceId;

        switch (typeDetails[0]) {
            case "addBook":
                userId = details[0];
                resourceId = details[2];
                switch (typeDetails[1]) {
                    case "treasureTrove", "book":
                        libraryId = details[9];
                        break;
                    case "forSale":
                        libraryId = details[11];
                        break;
                    default:
                        libraryId = details[8];
                }
                information.add(status);
                information.add(userId);
                information.add(libraryId);
                information.add(resourceId);
                break;

            case "borrow":
                userId = details[0];
                libraryId = details[2];
                resourceId = details[3];
                String dateBorrow = details[4];
                String timeBorrow = details[5];
                information.add(status);
                information.add(userId);
                information.add(libraryId);
                information.add(resourceId);
                information.add(dateBorrow);
                information.add(timeBorrow);
                break;

            case "return":
                userId = details[0];
                libraryId = details[2];
                resourceId = details[3];
                String resourceReturnDate = details[4];
                String resourceReturnTime = details[5];
                information.add(status);
                information.add(userId);
                information.add(libraryId);
                information.add(resourceId);
                information.add(resourceReturnDate);
                information.add(resourceReturnTime);
                information.add(minutesOrPrice);
                break;

            case "buy":
                userId = details[0];
                libraryId = details[2];
                resourceId = details[3];
                information.add(status);
                information.add(userId);
                information.add(libraryId);
                information.add(resourceId);
                information.add(minutesOrPrice);
        }

        if (!logEntries.containsKey(typeDetails[0])) {
            logEntries.put(typeDetails[0], new ArrayList<>());
        }

        logEntries.get(typeDetails[0]).add(information);
    }
}
