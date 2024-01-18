package assessment.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Join;
import javax.validation.constraints.NotNull;
/* protected region validate-body on begin */
/* protected region validate-body end */
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import assessment.domain.Hashtag;
import assessment.dto.HashtagDTO;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

	/* protected region validate-body on begin */
	@Join(value = "videos", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<Hashtag> findById(@NotNull Long id);
	/* protected region validate-body end */
	
	Optional<HashtagDTO> findOne(long id);

}
