package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.User;
import assessment.domain.Video;
import assessment.domain.Hashtag;

@KafkaListener(groupId = "SubscriptionMicroserviceDebugConsumers")
public class DebugConsumers {

	@Topic("DummyStreamOutput2")
	public void DummyStreamOutput2(@KafkaKey WindowedIdentifier window, Long count) {
	}
	
	@Topic("DummyProducer2")
	public void DummyProducer2(@KafkaKey Long id) {
		System.out.printf("%n", id);
	}


	
	@Topic("HashtagSubscribed")
	public void HashtagSubscribed(@KafkaKey Long id, Hashtag hashtag) {
		System.out.printf("%n", id);
	}
	
	@Topic("HashtagUnsubscribed")
	public void HashtagUnsubscribed(@KafkaKey Long id, Hashtag hashtag) {
		System.out.printf("%n", id);
	}
}