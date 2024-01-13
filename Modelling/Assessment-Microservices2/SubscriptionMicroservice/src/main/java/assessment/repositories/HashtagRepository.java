package assessment.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Join;
/* protected region validate-body on begin */
/* protected region validate-body end */
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import assessment.domain.Hashtag;
import assessment.domain.Video;
import assessment.dto.HashtagDTO;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

	/* protected region validate-body on begin */
	/* protected region validate-body end */
	
	Optional<HashtagDTO> findOne(long id);

}
