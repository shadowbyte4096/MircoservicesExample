package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.Hashtag;
import assessment.domain.Reaction;

@KafkaListener(groupId = "TrendingHashtagMicroserviceDebugConsumers")
public class DebugConsumers {

	@Topic("DummyStreamOutput1")
	public void DummyStreamOutput1(@KafkaKey WindowedIdentifier window, Long count) {
	}
	
	@Topic("DummyProducer1")
	public void DummyProducer1(@KafkaKey Long id) {
		System.out.printf("%n", id);
	}


}