package moussaoui.mohammed.technicalTest.repository;
import org.springframework.data.repository.CrudRepository;

import moussaoui.mohammed.technicalTest.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {

}
