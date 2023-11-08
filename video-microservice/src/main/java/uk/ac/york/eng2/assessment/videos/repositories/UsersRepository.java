package uk.ac.york.eng2.assessment.videos.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.UserDTO;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

	Optional<UserDTO> findOne(long id);

}
