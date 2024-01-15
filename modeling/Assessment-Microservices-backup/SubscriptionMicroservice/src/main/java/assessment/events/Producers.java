package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.User;
import assessment.domain.Video;
import assessment.domain.Hashtag;
	
@KafkaClient
public interface Producers {

	/*
	 * NOTE: this topic has to be created before we start the program, or we will
	 * have an error from Kafka Streams.
	 */
	
	@Topic("DummyProducer2")
	void DummyProducer(@KafkaKey Long id);
	
	@Topic("HashtagSubscribed")
	void HashtagSubscribed(@KafkaKey Long id, Hashtag hashtag);
	
	@Topic("HashtagUnsubscribed")
	void HashtagUnsubscribed(@KafkaKey Long id, Hashtag hashtag);
}