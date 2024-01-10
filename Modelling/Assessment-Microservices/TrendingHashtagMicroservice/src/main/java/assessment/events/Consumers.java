package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.Hashtag;
import assessment.domain.Reaction;

@KafkaListener(groupId = "TrendingHashtagMicroserviceConsumers")
public class Consumers {
	
	@Topic("HashtagReacted")
	public void HashtagReacted(@KafkaKey Long id, Reaction reaction) {
	}
}