package uk.ac.york.eng2.assessment.videos.repositories;

import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.assessment.videos.domain.Video;
import uk.ac.york.eng2.assessment.videos.dto.VideoDTO;

@Repository
public interface VideosRepository extends CrudRepository<Video, Long> {

	@Join(value = "watchers", type = Join.Type.LEFT_FETCH)
	@Override
	Optional<Video> findById(@NotNull Long id);

	Optional<VideoDTO> findOne(long id);

}
