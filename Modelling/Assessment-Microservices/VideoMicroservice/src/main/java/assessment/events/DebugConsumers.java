package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.Hashtag;
import assessment.domain.Video;
import assessment.domain.User;
import assessment.domain.Reaction;

@KafkaListener(groupId = "VideoMicroserviceDebugConsumers")
public class DebugConsumers {

	@Topic("DummyStreamOutput0")
	public void DummyStreamOutput0(@KafkaKey WindowedIdentifier window, Long count) {
	}
	
	@Topic("DummyProducer0")
	public void DummyProducer0(@KafkaKey Long id) {
		System.out.printf("%n", id);
	}


	
	@Topic("VideoAdded")
	public void VideoAdded(@KafkaKey Long id, Video video) {
		System.out.printf("%n", id);
	}
	
	@Topic("VideoWatched")
	public void VideoWatched(@KafkaKey Long id, Video video) {
		System.out.printf("%n", id);
	}
	
	@Topic("VideoReacted")
	public void VideoReacted(@KafkaKey Long id, Reaction reaction) {
		System.out.printf("%n", id);
	}
	
	@Topic("VideoDeleted")
	public void VideoDeleted(@KafkaKey Long id) {
		System.out.printf("%n", id);
	}
	
	@Topic("HashtagReacted")
	public void HashtagReacted(@KafkaKey Long id, Reaction reaction) {
		System.out.printf("%n", id);
	}
	
	@Topic("UserAdded")
	public void UserAdded(@KafkaKey Long id, User user) {
		System.out.printf("%n", id);
	}
	
	@Topic("UserDeleted")
	public void UserDeleted(@KafkaKey Long id) {
		System.out.printf("%n", id);
	}
	
	@Topic("HashtagAdded")
	public void HashtagAdded(@KafkaKey Long id, Hashtag hashtag) {
		System.out.printf("%n", id);
	}
	
	@Topic("HashtagDeleted")
	public void HashtagDeleted(@KafkaKey Long id) {
		System.out.printf("%n", id);
	}
}