import java.util.HashMap;

class Comment {
    static final HashMap<String, String> comments = new HashMap<>();

    static void addComment(String data) {
        String[] details = data.split("\\|");
        String userId = details[0];
        String userPassword = details[1];
        String libraryId = details[2];
        String resourceId = details[3];
        String commentText = details[4];
        User user = User.users.get(userId);

        if (!User.userExists(userId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!Library.libraries.contains(libraryId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if (!Resource.resourceExists(libraryId, resourceId)) {
            System.out.print(ResponseMessage.NOT_FOUND);
            return;
        }
        if ((user instanceof Manager || user instanceof Staff)) {
            System.out.print(ResponseMessage.PERMISSION);
            return;
        }
        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(ResponseMessage.INVALID_PASS);
            return;
        }

        String resourceKey = Resource.getCompositeKey(libraryId, resourceId);
        comments.put(resourceKey, commentText);
        System.out.print(ResponseMessage.SUCCESS);
    }
}