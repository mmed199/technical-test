package moussaoui.mohammed.technicalTest.service;

import moussaoui.mohammed.technicalTest.model.User;


/**
 * Provides the methodes needed to manage users
 * 
 * @author moussaoui
 *
 */
public interface UserService {
	
	/**
	 * Allow us to test if user exists in DB
	 * 
	 * @param user to test if exists
	 * @return if true, user exists.
	 */
    public boolean userExists(User user);

    /**
     * Allow us to add a new user to DB
     * @param user to save
     * @return user or null
     */
    public User addUser(User user);

    /**
     * Allow us to get the user details using it username
     * 
     * @param username to find
     * @return
     */
    public User getUserByUsername(String username);

}