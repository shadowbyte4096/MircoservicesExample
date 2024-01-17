package assessment.hashtags;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import assessment.domain.Hashtag;
import assessment.repositories.HashtagRepository;
import assessment.repositories.ReactionRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class HashtagControllersTest {

	@Inject
	HashtagsClient client;

	@Inject
	HashtagRepository repo;
	
	@Inject
	ReactionRepository reacitonRepo;

	/*
	 * We mock the Kafka producer here, so we can just test that it's called,
	 * rather than actually checking if the event fully went through.
	 */

//	@MockBean(VideosProducer.class)
//	VideosProducer testProducer() {
//		return (key, value) -> { watchedVideos.put(key,  value); };
//	}

	@BeforeEach
	public void clean() {
		repo.deleteAll();
		reacitonRepo.deleteAll();
	}

	@Test
	public void noVideos() {
		Iterable<Hashtag> iterHashtags = client.GetTopTen();
		assertFalse(iterHashtags.iterator().hasNext(), "Service should not list any videos initially");
	}
}
