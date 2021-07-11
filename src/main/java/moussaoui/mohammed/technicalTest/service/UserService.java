package moussaoui.mohammed.technicalTest.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import moussaoui.mohammed.technicalTest.entity.UserEntity;
import moussaoui.mohammed.technicalTest.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public boolean userExists(UserEntity user) {
		Optional<UserEntity> dbUser = userRepository.findById(user.getUsername());
		return dbUser.isPresent();
	}
	
	public UserEntity addUser(UserEntity user) {
		return this.userRepository.save(user);
	}
	
	public UserEntity getUserByUsername(String username) {
		Optional<UserEntity> dbUser = userRepository.findById(username);
		if(dbUser.isPresent()) {
			return dbUser.get();
		} else {
			return null;
		}
	}
}
