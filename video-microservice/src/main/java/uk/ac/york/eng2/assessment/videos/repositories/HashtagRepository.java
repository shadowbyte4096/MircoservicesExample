package uk.ac.york.eng2.assessment.videos.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.assessment.videos.domain.Hashtag;
import uk.ac.york.eng2.assessment.videos.dto.HashtagDTO;

@Repository
public interface HashtagRepository extends CrudRepository<Hashtag, Long> {

	Optional<HashtagDTO> findOne(long id);

}
