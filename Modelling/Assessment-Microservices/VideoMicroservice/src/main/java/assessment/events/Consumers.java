package assessment.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import assessment.domain.Hashtag;
import assessment.domain.Video;
import assessment.domain.User;
import assessment.domain.Reaction;

@KafkaListener(groupId = "VideoMicroserviceConsumers")
public class Consumers {
	
	@Topic("VideoWatched")
	public void VideoWatched(@KafkaKey Long id, Video video) {
	}
	
	@Topic("VideoReacted")
	public void VideoReacted(@KafkaKey Long id, Reaction reaction) {
	}
}