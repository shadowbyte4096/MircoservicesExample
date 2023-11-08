package uk.ac.york.eng2.assessment.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.assessment.videos.domain.Video;

@KafkaListener(groupId = "videos-debug")
public class VideosConsumer {

	@Topic(VideosStreams.TOPIC_READ_BY_DAY)
	public void videoWatchMetric(@KafkaKey WindowedIdentifier window, Long count) {
		System.out.printf("New value for key %s: %d%n", window, count);
	}

	@Topic(VideosProducer.TOPIC_READ)
	public void watchVideo(@KafkaKey Long id, Video video) {
		System.out.printf("Video watch: %d%n", id);
	}
}
