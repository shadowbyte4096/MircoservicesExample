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
import assessment.dto.VideoDTO;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {

	/* protected region validate-body on begin */
	@Join(value = "hashtags", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<Video> findById(@NotNull Long id);
	/* protected region validate-body end */
	
	Optional<VideoDTO> findOne(long id);

}
