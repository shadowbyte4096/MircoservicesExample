package uk.ac.york.eng2.assessment.videos.repositories;

import java.util.Optional;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import uk.ac.york.eng2.assessment.videos.domain.Hashtag;
import uk.ac.york.eng2.assessment.videos.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.HashtagDTO;
import uk.ac.york.eng2.assessment.videos.dto.ReactionDTO;
import uk.ac.york.eng2.assessment.videos.dto.UserDTO;

@Repository
public interface ReactionRepository extends CrudRepository<Reaction, Long> {

	Optional<ReactionDTO> findOne(long id);

}
