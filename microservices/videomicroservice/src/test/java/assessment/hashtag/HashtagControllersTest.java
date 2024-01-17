package assessment.hashtag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import assessment.domain.Video;
import assessment.domain.Hashtag;
import assessment.domain.Reaction;
import assessment.domain.User;
import assessment.dto.HashtagDTO;
import assessment.dto.VideoDTO;
import assessment.events.Producers;
import assessment.repositories.VideoRepository;
import assessment.repositories.HashtagRepository;
import assessment.repositories.ReactionRepository;
import assessment.repositories.UserRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class HashtagControllersTest {

	private static Logger logger = LoggerFactory.getLogger("testLogger");
	
	@Inject
	HashtagClient client;

	@Inject
	VideoRepository videoRepo;
	
	@Inject
	HashtagRepository hashtagRepo;

	@Inject
	UserRepository userRepo;
	
	@Inject
	ReactionRepository reacitonRepo;

	/*
	 * We mock the Kafka producer here, so we can just test that it's called,
	 * rather than actually checking if the event fully went through.
	 */
	//private final Map<Long, User> watchedVideos = new HashMap<>();

//	@MockBean(VideosProducer.class)
//	VideosProducer testProducer() {
//		return (key, value) -> { watchedVideos.put(key,  value); };
//	}

	@BeforeEach
	public void clean() {
		hashtagRepo.deleteAll();
		videoRepo.deleteAll();
		userRepo.deleteAll();
		//watchedVideos.clear();
	}

	@Test
	public void noVideos() {
		Iterable<Hashtag> iterVideos = client.ListHashtags();
		assertFalse(iterVideos.iterator().hasNext(), "Service should not list any videos initially");
	}

	@Test
	public void addHashtag() {
		final String HashtagName = "Container Security";
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName(HashtagName);
		Video temp = new Video();
		temp.setTitle("video");
		videoRepo.save(temp);
		HttpResponse<String> response = client.AddHashtag(temp.getId(), hashtag);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<Hashtag> hashtags = iterableToList(client.ListHashtags());
		assertEquals(1, hashtags.size());
		assertEquals(HashtagName, hashtags.get(0).getName());
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
