package assessment.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Join;
import javax.validation.constraints.NotNull;
/* protected region validate-body on begin */
/* protected region validate-body end */
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import assessment.domain.Reaction;
import assessment.dto.ReactionDTO;

@Repository
public interface ReactionRepository extends CrudRepository<Reaction, Long> {

	/* protected region validate-body on begin */

	/* protected region validate-body end */
	
	Optional<ReactionDTO> findOne(long id);

}
