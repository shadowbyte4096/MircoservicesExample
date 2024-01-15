package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import assessment.domain.Hashtag;
import assessment.domain.Reaction;
import assessment.repositories.HashtagRepository;
import assessment.repositories.ReactionRepository;

import java.time.LocalDateTime;

@KafkaListener(groupId = "TrendingHashtagMicroserviceConsumers")
public class Consumers {
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	ReactionRepository reactionRepo;
	
	@Transactional
	@Topic("HashtagReacted")
	public void HashtagReacted(@KafkaKey Long id, Reaction reaction) {
		Optional<Hashtag> oHashtag = hashtagRepo.findById(id);
		if (oHashtag.isEmpty()) {
			//Oh no
		}
		Hashtag hashtag = oHashtag.get();
		LocalDateTime dateTime = LocalDateTime.now();
		reaction.setTimeCreated(dateTime.toString());
		reaction.setHashtag(hashtag);
		reactionRepo.save(reaction);
		hashtag.getReactions().add(reaction);
		hashtagRepo.update(hashtag);
	}
	
	@Topic("HashtagAdded")
	public void HashtagAdded(@KafkaKey Long id, Hashtag hashtag) {
		hashtag.setId(id);
		hashtagRepo.save(hashtag);
	}
}