package tn.esprit.pidev.entities;

public class CurrentUser {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User currentUser) {
        user = currentUser;
    }
}