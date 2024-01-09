package uk.ac.york.eng2.assessment.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.assessment.videos.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.domain.Video;

@KafkaClient
public interface VideosProducer {

	/*
	 * NOTE: this topic has to be created before we start the program, or we will
	 * have an error from Kafka Streams.
	 */

	String TOPIC_VIDEO_CREATED = "video-created";
	String TOPIC_VIDEO_WATCHED = "video-watched";
	String TOPIC_VIDEO_REACTED = "video-reacted";

	@Topic(TOPIC_VIDEO_CREATED)
	void createVideo(@KafkaKey Long videoId, Video video);
	
	@Topic(TOPIC_VIDEO_WATCHED)
	void watchVideo(@KafkaKey Long videoId, User user);
	
	@Topic(TOPIC_VIDEO_REACTED)
	void reactVideo(@KafkaKey Long videoId, Reaction reaction);

}
