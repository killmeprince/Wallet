package org.example.services;

import org.example.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private final Map<String, User> users;
    private User currentUser;


    public UserService() {

        this.users = new HashMap<>();
    }

    public boolean registerUser(String username, String password) {
        if (users.containsKey(username)) {
            return false;
        }
        users.put(username, new User(username, password));
        return true;
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            this.currentUser = user;
            return true;
        }
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }

    public void setUsers(Map<String, User> users) {
        this.users.putAll(users);
    }

    public Map<String, User> getUsers() {
        return users;
    }
}
