import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ReportSell {
    public static void handle(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPass = details[1];
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
        if (User.isInvalidPassword(userId, userPass)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }

        long totalSum = 0;
        long totalCount = 0;
        HashMap<String, Long> countMap = new HashMap<>();

        if (LogEntry.logEntries.containsKey("buy")) {
            ArrayList<ArrayList<String>> buyLogs = LogEntry.logEntries.get("buy");
            for (ArrayList<String> entry : buyLogs) {
                if (!entry.get(0).equals("success")) continue;
                String logLibraryId = entry.get(2);
                if (!logLibraryId.equals(libraryId)) continue;
                String resourceId = entry.get(3);
                long price;
                try {
                    price = Long.parseLong(entry.get(4));
                } catch (NumberFormatException e) {
                    continue;
                }
                totalSum += price;
                totalCount++;
                countMap.put(resourceId, countMap.getOrDefault(resourceId, 0L) + 1);
            }
        }

        System.out.println(totalCount + " " + totalSum);

        String bestResourceId = null;
        long bestCount = 0;
        long bestUnitPrice = Long.MAX_VALUE;

        if (totalCount > 0) {
            for (Map.Entry<String, Long> entry : countMap.entrySet()) {
                String rId = entry.getKey();
                long count = entry.getValue();
                Resource r = Resource.resources.get(Resource.getCompositeKey(libraryId, rId));
                if (r instanceof BookForSale) {
                    long unitPrice = ((BookForSale) r).getFinalPrice();
                    if (count > bestCount) {
                        bestCount = count;
                        bestUnitPrice = unitPrice;
                        bestResourceId = rId;
                    } else if (count == bestCount) {
                        if (unitPrice < bestUnitPrice) {
                            bestUnitPrice = unitPrice;
                            bestResourceId = rId;
                        } else if (unitPrice == bestUnitPrice && rId.compareTo(bestResourceId) < 0) {
                            bestResourceId = rId;
                        }
                    }
                }
            }
        } else {
            for (Resource r : Resource.resources.values()) {
                if (r instanceof BookForSale && r.getLibrary().equals(libraryId)) {
                    BookForSale bfs = (BookForSale) r;
                    long unitPrice = bfs.getFinalPrice();
                    String rId = r.getId();
                    if (bestResourceId == null) {
                        bestResourceId = rId;
                        bestUnitPrice = unitPrice;
                    } else {
                        if (unitPrice < bestUnitPrice) {
                            bestUnitPrice = unitPrice;
                            bestResourceId = rId;
                        } else if (unitPrice == bestUnitPrice && rId.compareTo(bestResourceId) < 0) {
                            bestResourceId = rId;
                        }
                    }
                }
            }
            bestCount = 0;
        }

        if (bestResourceId == null) {
            System.out.println("null");
        } else {
            System.out.println(bestResourceId + " " + bestCount + " " + (bestCount * bestUnitPrice));
        }
    }
}
