package moussaoui.mohammed.technicalTest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import moussaoui.mohammed.technicalTest.entity.UserEntity;
import moussaoui.mohammed.technicalTest.model.User;
import moussaoui.mohammed.technicalTest.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean userExists(User user) {
        Optional<UserEntity> dbUser = userRepository.findById(user.getUsername());
        return dbUser.isPresent();
    }

    public User addUser(User user) {
    	UserEntity saved = this.userRepository.save(new UserEntity(user));
        return saved.toModel();
    }

    public User getUserByUsername(String username) {
        Optional<UserEntity> dbUser = userRepository.findById(username);
        if (dbUser.isPresent()) {
            return dbUser.get().toModel();
        } else {
            return null;
        }
    }
}
