import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Search {
    public static void searchWord(String keyword) {
        List<String> foundResources = new ArrayList<>();

        String lowerKeyword = keyword.toLowerCase();
        boolean found;
        for (Resource resource : Resource.resources.values()) {
            found = false;
            if (resource instanceof Thesis thesis){
                String advisor = thesis.getAdvisor().toLowerCase();
                if(advisor.contains(lowerKeyword)){
                    found = true;
                }
            }
            if (resource instanceof Book book){
                String publisher = book.getPublisher().toLowerCase();
                if(publisher.contains(lowerKeyword)){
                    found = true;
                }
            }
            if (resource instanceof BookForSale bookForSale){
                String publisher = bookForSale.getPublisher().toLowerCase();
                if(publisher.contains(lowerKeyword)){
                    found = true;
                }
            }
            if (resource instanceof TreasureTrove treasureTrove){
                String publisher = treasureTrove.getPublisher().toLowerCase();
                if(publisher.contains(lowerKeyword)){
                    found = true;
                }
            }
            String nameLower = resource.getName().toLowerCase();
            String authorLower = resource.getAuthor().toLowerCase();
            if (nameLower.contains(lowerKeyword) || authorLower.contains(lowerKeyword) || found) {
                foundResources.add(resource.getId());
            }
        }

        if (foundResources.isEmpty()) {
            System.out.print(Main.NOT_FOUND);return;}

        foundResources.sort(String::compareTo);
        for (int i = 0; i < foundResources.size(); i++) {
            System.out.print(foundResources.get(i));
            if (i != foundResources.size() - 1) {
                System.out.print("|");
            }
        }
        System.out.println();

    }

    public static void searchUser(String token) {
        String[] data = token.split("\\|");
        String userId = data[0];
        String userPassword = data[1];
        String keyword = data[2].toLowerCase();

        if (!User.userExists(userId)) {
            System.out.print(Main.NOT_FOUND);return;}

        if (User.isInvalidPassword(userId, userPassword)) {
            System.out.print(Main.INVALID_PASS);return;}

        User currentUser = User.users.get(userId);
        if (currentUser instanceof Student) {
            System.out.print(Main.PERMISSION);return;}

        List<User> foundUsers = new ArrayList<>();

        for (User user : User.users.values()) {
            String fullName = (user.getFirstName() + " " + user.getLastName()).toLowerCase();
            if (fullName.contains(keyword)) {
                foundUsers.add(user);
            }
        }

        if (foundUsers.isEmpty()) {
            System.out.print(Main.NOT_FOUND);return;}

        foundUsers.sort(Comparator.comparing(User::getId));


        for (int i = 0; i < foundUsers.size(); i++) {
            System.out.print(foundUsers.get(i).getId());
            if (i != foundUsers.size() - 1) {
                System.out.print("|");
            }
        }
        System.out.println();

    }
}
