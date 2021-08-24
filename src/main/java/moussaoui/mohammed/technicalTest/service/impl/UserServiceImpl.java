package moussaoui.mohammed.technicalTest.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import moussaoui.mohammed.technicalTest.entity.UserEntity;
import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.repository.UserRepository;
import moussaoui.mohammed.technicalTest.service.UserService;

/**
 * The main implementation of {@link moussaoui.mohammed.technicalTest.service.UserService UserService}
 * 
 * @author moussaoui
 *
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean userExists(User user) {
        Optional<UserEntity> dbUser = userRepository.findById(user.getUsername());
        return dbUser.isPresent();
    }

    @Override
    public User addUser(User user) {
    	UserEntity saved = this.userRepository.save(new UserEntity(user));
        return saved.toModel();
    }

    @Override
    public User getUserByUsername(String username) {
        Optional<UserEntity> dbUser = userRepository.findById(username);
        if (dbUser.isPresent()) {
            return dbUser.get().toModel();
        } else {
            return null;
        }
    }
}
