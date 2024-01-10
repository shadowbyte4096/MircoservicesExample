package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.http.HttpResponse;
import jakarta.inject.Inject;

import java.util.Optional;

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
	
	@Topic("HashtagReacted")
	public void HashtagReacted(@KafkaKey Long id, Reaction reaction) {
		Optional<Hashtag> oHashtag = hashtagRepo.findById(id);
		if (oHashtag.isEmpty()) {
			//Oh no
		}
		Hashtag hashtag = oHashtag.get();
		reaction.setHashtag(hashtag);
		LocalDateTime dateTime = LocalDateTime.now();
		reaction.setTimeCreated(dateTime.toString());
		reactionRepo.save(reaction);
		for (Reaction repoReaction : reactionRepo.findAll()) {
			LocalDateTime reactionDateTime = LocalDateTime.parse(repoReaction.getTimeCreated());
			LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
			if (reactionDateTime.isBefore(oneHourAgo)) {
				reactionRepo.delete(repoReaction);
			}
		}
	}
	
	@Topic("HashtagAdded")
	public void HashtagAdded(@KafkaKey Long id, Hashtag hashtag) {
		hashtag.setId(id);
		hashtagRepo.save(hashtag);
	}
}