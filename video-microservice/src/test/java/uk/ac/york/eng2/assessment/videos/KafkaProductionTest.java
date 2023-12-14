package uk.ac.york.eng2.assessment.videos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.configuration.kafka.annotation.KafkaKey;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.assessment.videos.clients.VideosClient;
import uk.ac.york.eng2.assessment.videos.domain.Video;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.events.VideosProducer;
import uk.ac.york.eng2.assessment.videos.repositories.VideosRepository;
import uk.ac.york.eng2.assessment.videos.repositories.UsersRepository;

/**
 * This is an integration test between our producer and Kafka itself: we actually
 * subscribe to the Kafka topic and see if the record is produced. It is an
 * asynchronous process, so we have to use the Awaitility library to describe
 * these expectations.
 */
@Property(name = "spec.name", value = "KafkaProductionTest")
@MicronautTest(transactional = false, environments = "no_streams")
public class KafkaProductionTest {

	@Inject
	VideosClient client;

	@Inject
	VideosRepository repo;

	@Inject
	UsersRepository userRepo;

	private static final Map<Long, Video> watchVideos = new HashMap<>();

	@BeforeEach
	public void setUp() {
		repo.deleteAll();
		userRepo.deleteAll();
		watchVideos.clear();
	}

	@Test
	public void addVideoWatcher() {
		Video b = new Video();
		b.setTitle("Container Security");
		repo.save(b);

		User u = new User();
		u.setUsername("antonio");
		userRepo.save(u);

		final Long videoId = b.getId();
		HttpResponse<String> response = client.addWatcher(videoId, u.getId());
		assertEquals(HttpStatus.OK, response.getStatus(), "Adding watcher to the video should be successful");

		// Check the event went to Kafka and back
		Awaitility.await()
			.atMost(Duration.ofSeconds(30))
			.until(() -> watchVideos.containsKey(videoId));
	}

	@Requires(property = "spec.name", value = "KafkaProductionTest")
	@KafkaListener(groupId = "kafka-production-test")
	static class TestConsumer {
		@Topic(VideosProducer.TOPIC_READ)
		void watchVideo(@KafkaKey Long id, Video video) {
			watchVideos.put(id, video);
		}
	}

}
