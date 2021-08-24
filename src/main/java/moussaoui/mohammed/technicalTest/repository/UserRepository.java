package moussaoui.mohammed.technicalTest.repository;

import org.springframework.data.repository.CrudRepository;

import moussaoui.mohammed.technicalTest.entity.UserEntity;

/**
 * Interface for generic CRUD for a {@link moussaoui.mohammed.technicalTest.entity.UserEntity
 * UserEntity } type.
 * 
 * @author moussaoui
 *
 */
public interface UserRepository extends CrudRepository<UserEntity, String> {

}
