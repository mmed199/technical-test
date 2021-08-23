package moussaoui.mohammed.technicalTest.service;

import moussaoui.mohammed.technicalTest.model.User;

public interface UserService {
    public boolean userExists(User user);

    public User addUser(User user);

    public User getUserByUsername(String username);

}