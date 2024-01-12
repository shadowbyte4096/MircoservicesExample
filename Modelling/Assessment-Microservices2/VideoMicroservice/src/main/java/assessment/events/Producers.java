package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.Hashtag;
import assessment.domain.Video;
import assessment.domain.User;
import assessment.domain.Reaction;
	
@KafkaClient
public interface Producers {

	/*
	 * NOTE: this topic has to be created before we start the program, or we will
	 * have an error from Kafka Streams.
	 */
	
	@Topic("DummyProducer0")
	void DummyProducer(@KafkaKey Long id);
	
	@Topic("VideoAdded")
	void VideoAdded(@KafkaKey Long id, Video video);
	
	@Topic("VideoWatched")
	void VideoWatched(@KafkaKey Long videoId, Long userId);
	
	@Topic("VideoReacted")
	void VideoReacted(@KafkaKey Long id, Reaction reaction);
	
	@Topic("HashtagReacted")
	void HashtagReacted(@KafkaKey Long id, Reaction reaction);
	
	@Topic("UserAdded")
	void UserAdded(@KafkaKey Long id, User user);
	
	@Topic("HashtagAdded")
	void HashtagAdded(@KafkaKey Long id, Hashtag hashtag);
}