class Buy {
    public static void buyHandler(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];
        String resourceId = details[3];

        if (!User.userExists(userId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!LibraryManager.libraries.containsKey(libraryId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (!Resource.resourceExists(libraryId, resourceId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        User user = User.users.get(userId);
        Resource resource = Resource.resources.get(Resource.getCompositeKey(libraryId, resourceId));

        if (user instanceof Manager) {
            System.out.print(Main.PERMISSION);return;}

        if (!(resource instanceof BookForSale)) {
            System.out.print(Main.NOT_ALLOWED);return;}

        if (user.getPenalized()) {
            System.out.print(Main.NOT_ALLOWED);return;}

        BookForSale bookForSale = (BookForSale) resource;
        int currentCopy = bookForSale.getCopy();
        if (currentCopy <= 0) {
            System.out.print(Main.NOT_ALLOWED);return;}

        int newCopy = currentCopy - 1;
        bookForSale.setCopy(newCopy);
        System.out.print(Main.SUCCESS);
    }
}
