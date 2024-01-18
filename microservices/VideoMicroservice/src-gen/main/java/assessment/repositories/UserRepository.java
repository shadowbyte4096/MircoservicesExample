package assessment.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Join;
import javax.validation.constraints.NotNull;
/* protected region validate-body on begin */
/* protected region validate-body end */
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import assessment.domain.User;
import assessment.dto.UserDTO;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/* protected region validate-body on begin */
	@Join(value = "videos", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<User> findById(@NotNull Long id);
	/* protected region validate-body end */
	
	Optional<UserDTO> findOne(long id);

}
