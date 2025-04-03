class Buy {
    public static void buyHandler(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];
        String resourceId = details[3];

        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }
        if (!Library.libraries.contains(libraryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }
        if (!Resource.resourceExists(libraryId, resourceId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }

        User user = User.users.get(userId);
        Resource resource = Resource.resources.get(Resource.getCompositeKey(libraryId, resourceId));
        if (user instanceof Manager) {
            System.out.print(ResponseMessage.PERMISSION);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }
        if (!(resource instanceof BookForSale bookForSale)) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }
        if (user.getPenalized()) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }

        int currentCopy = bookForSale.getCopy();
        if (currentCopy <= 0) {
            System.out.print(ResponseMessage.NOT_ALLOWED);
            LogEntry.addLogEntry(data, "buy", "failed", "none");
            return;
        }
        int newCopy = currentCopy - 1;
        bookForSale.setCopy(newCopy);
        long finalPrice = bookForSale.getFinalPrice();
        System.out.print(ResponseMessage.SUCCESS);
        LogEntry.addLogEntry(data, "buy", "success", String.valueOf(finalPrice));
    }
}