package uk.ac.york.eng2.assessment.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.assessment.videos.domain.Video;

@KafkaClient
public interface VideosProducer {

	/*
	 * NOTE: this topic has to be created before we start the program, or we will
	 * have an error from Kafka Streams.
	 */
	String TOPIC_READ = "video-watch";

	@Topic(TOPIC_READ)
	void watchVideo(@KafkaKey Long id, Video video);

}
