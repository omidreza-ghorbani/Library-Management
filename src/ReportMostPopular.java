import java.util.*;

class ReportMostPopular {
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

        Map<String, Long> bookDurationMap = new HashMap<>();
        Map<String, Integer> bookCountMap = new HashMap<>();
        Map<String, Long> thesisDurationMap = new HashMap<>();
        Map<String, Integer> thesisCountMap = new HashMap<>();

        Set<String> seenBorrowKeys = new HashSet<>();

        if (LogEntry.logEntries.containsKey("return")) {
            ArrayList<ArrayList<String>> returnLogs = LogEntry.logEntries.get("return");
            for (ArrayList<String> log : returnLogs) {
                if (!log.get(0).equals("success")) continue;
                String logLibraryId = log.get(2);
                if (!logLibraryId.equals(libraryId)) continue;

                String resourceId = log.get(3);
                String borrowKey = log.get(1) + "_" + log.get(2) + "_" + log.get(3);
                if (seenBorrowKeys.contains(borrowKey)) continue;
                seenBorrowKeys.add(borrowKey);

                Resource r = Resource.resources.get(Resource.getCompositeKey(libraryId, resourceId));
                if (r == null || !r.getLibrary().equals(libraryId)) continue;

                long durationMinutes = Long.parseLong(log.get(6));
                if (durationMinutes < 0) continue;
                long durationDays = durationMinutes > 0 ? Math.max(1, (long) Math.ceil((double) durationMinutes / 1440)) : 0;

                if (r instanceof Book) {
                    bookDurationMap.put(resourceId, bookDurationMap.getOrDefault(resourceId, 0L) + durationDays);
                    bookCountMap.put(resourceId, bookCountMap.getOrDefault(resourceId, 0) + 1);
                } else if (r instanceof Thesis) {
                    thesisDurationMap.put(resourceId, thesisDurationMap.getOrDefault(resourceId, 0L) + durationDays);
                    thesisCountMap.put(resourceId, thesisCountMap.getOrDefault(resourceId, 0) + 1);
                }
            }
        }

        if (bookDurationMap.isEmpty()) {
            System.out.println("null");
        } else {
            String bestBookId = null;
            long bestDuration = -1;
            for (Map.Entry<String, Long> entry : bookDurationMap.entrySet()) {
                String rId = entry.getKey();
                long duration = entry.getValue();
                if (duration > bestDuration) {
                    bestDuration = duration;
                    bestBookId = rId;
                } else if (duration == bestDuration && rId.compareTo(bestBookId) < 0) {
                    bestBookId = rId;
                }
            }
            int count = bookCountMap.get(bestBookId);
            System.out.println(bestBookId + " " + count + " " + bestDuration);
        }

        if (thesisDurationMap.isEmpty()) {
            System.out.println("null");
        } else {
            String bestThesisId = null;
            long bestThesisDuration = -1;
            for (Map.Entry<String, Long> entry : thesisDurationMap.entrySet()) {
                String rId = entry.getKey();
                long duration = entry.getValue();
                if (duration > bestThesisDuration) {
                    bestThesisDuration = duration;
                    bestThesisId = rId;
                } else if (duration == bestThesisDuration && rId.compareTo(bestThesisId) < 0) {
                    bestThesisId = rId;
                }
            }
            int thesisCount = thesisCountMap.get(bestThesisId);
            System.out.println(bestThesisId + " " + thesisCount + " " + bestThesisDuration);
        }
    }
}
