package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import javax.transaction.Transactional;
import jakarta.inject.Inject;
import assessment.domain.Hashtag;
import assessment.domain.Reaction;
import assessment.repositories.HashtagRepository;
import assessment.repositories.ReactionRepository;

@KafkaListener(groupId = "TrendingHashtagMicroserviceConsumers")
public class Consumers {
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	ReactionRepository reactionRepo;
	
	//@Transactional
	//@Topic("HashtagReacted") // uncomment on src 
	public void HashtagReacted(@KafkaKey Long id, Reaction reaction) {
	}
	
	//@Transactional
	//@Topic("HashtagAdded") // uncomment on src 
	public void HashtagAdded(@KafkaKey Long id, Hashtag hashtag) {
	}
}