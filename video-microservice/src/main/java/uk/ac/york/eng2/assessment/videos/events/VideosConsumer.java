package uk.ac.york.eng2.assessment.videos.events;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import uk.ac.york.eng2.assessment.videos.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.domain.Video;

@KafkaListener(groupId = "videos-debug")
public class VideosConsumer {

	@Topic(VideosStreams.TOPIC_WATCHED_BY_DAY)
	public void videoWatchMetric(@KafkaKey WindowedIdentifier window, Long count) {
		System.out.printf("New value for key %s: %d%n", window, count);
	}

	@Topic(VideosProducer.TOPIC_VIDEO_CREATED)
	public void createVideo(@KafkaKey Long videoId, Video video) {
		System.out.printf("Video created: %n", videoId);
	}
	
	@Topic(VideosProducer.TOPIC_VIDEO_WATCHED)
	public void watchVideo(@KafkaKey Long videoId, Video video, Long userId, User user) {
		System.out.printf("Video %n watched by user %n", videoId, userId);
	}
	
	@Topic(VideosProducer.TOPIC_VIDEO_REACTED)
	public void watchVideo(@KafkaKey Long videoId, Video video, Long reactionId, Reaction reaction) {
		if (reaction.getReaction() == -1) {
			System.out.printf("Video %n was disliked", videoId);
		}
		else if (reaction.getReaction() == 1) {
			System.out.printf("Video %n was liked", videoId);
		}
		else {
			System.out.printf("Video %n has unknown reaction: %n", videoId, reaction.getReaction());
		}
	}
}
