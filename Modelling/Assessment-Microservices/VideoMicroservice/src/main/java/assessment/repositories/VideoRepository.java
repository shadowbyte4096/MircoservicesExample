package assessment.repositories;

import java.util.Optional;
/* protected region validate-body on begin */
/* protected region validate-body end */
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import assessment.domain.Video;
import assessment.dto.VideoDTO;

@Repository
public interface VideoRepository extends CrudRepository<Video, Long> {

	/* protected region validate-body on begin */

	/* protected region validate-body end */
	
	Optional<VideoDTO> findOne(long id);

}
