package br.com.calderani.rafael.tetoedc;

import br.com.calderani.rafael.tetoedc.model.User;

/**
 * Created by Rafael on 05/08/2017.
 */

public class CurrentUser {
    private static User currentUser;
    public static User getInstance() {
        return currentUser;
    }
    public static boolean initInstance(User user) {
        boolean result = false;
        if (currentUser == null) {
            currentUser = user;
            result = true;
        }

        return result;
    }

    public static void finishInstance() {
        currentUser = null;
    }

    private CurrentUser() {}
}
