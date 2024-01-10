package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.User;
import assessment.domain.Video;
import assessment.domain.Hashtag;

@KafkaListener(groupId = "SubscriptionMicroserviceConsumers")
public class Consumers {
	
	@Topic("HashtagSubscribed")
	public void HashtagSubscribed(@KafkaKey Long id, Hashtag hashtag) {
	}
	
	@Topic("HashtagUnsubscribed")
	public void HashtagUnsubscribed(@KafkaKey Long id, Hashtag hashtag) {
	}
	
	@Topic("VideoAdded")
	public void VideoAdded(@KafkaKey Long id, Video video) {
	}
	
	@Topic("VideoDeleted")
	public void VideoDeleted(@KafkaKey Long id) {
	}
	
	@Topic("HashtagAdded")
	public void HashtagAdded(@KafkaKey Long id, Hashtag hashtag) {
	}
	
	@Topic("HashtagDeleted")
	public void HashtagDeleted(@KafkaKey Long id) {
	}
	
	@Topic("UserAdded")
	public void UserAdded(@KafkaKey Long id, User user) {
	}
	
	@Topic("UserDeleted")
	public void UserDeleted(@KafkaKey Long id) {
	}
}