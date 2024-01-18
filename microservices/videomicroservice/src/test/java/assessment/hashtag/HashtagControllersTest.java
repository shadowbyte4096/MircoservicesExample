package assessment.hashtag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import assessment.domain.Video;
import assessment.domain.Hashtag;
import assessment.domain.User;
import assessment.dto.HashtagDTO;
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

	@BeforeEach
	public void clean() {
		hashtagRepo.deleteAll();
		videoRepo.deleteAll();
		userRepo.deleteAll();
	}

	@Test
	public void noHashtag() {
		Iterable<Hashtag> iterVideos = client.ListHashtags();
		assertFalse(iterVideos.iterator().hasNext(), "Service should not list any hashtags initially");
	}
	
	@Test
	public void hasHashtag() {
		User tempUser = new User();
		tempUser.setUsername("name");
		Video tempVideo = new Video();
		tempVideo.setTitle("title");
		userRepo.save(tempUser);
		tempVideo.setUser(tempUser);
		videoRepo.save(tempVideo);
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		Set<Video> videos = hashtag.getVideos();
		if (videos == null) {
			videos = new HashSet<Video>();
		}
		videos.add(tempVideo);
		hashtag.setVideos(videos);
		hashtagRepo.save(hashtag);
		
		Iterable<Hashtag> iterVideos = client.ListHashtags();
		assertTrue(iterVideos.iterator().hasNext());
	}

	@Test
	public void addHashtag() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
	
		HttpResponse<String> response = client.AddHashtag(video.getId(), hashtag);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<Hashtag> hashtags = iterableToList(client.ListHashtags());
		assertEquals(1, hashtags.size());
		assertEquals("hashtag", hashtags.get(0).getName());
	}
	
	@Test
	public void addHashtagWrongVideo() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
	
		HttpResponse<String> response = client.AddHashtag(video.getId() + 1, hashtag);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
	}
	
	@Test
	public void addHashtagSameHashtag() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		client.AddHashtag(video.getId(), hashtag);
		HttpResponse<String> response = client.AddHashtag(video.getId(), hashtag);
		assertEquals(HttpStatus.OK, response.getStatus(), "Update should be successful");

		List<Hashtag> hashtags = iterableToList(client.ListHashtags());
		assertEquals(1, hashtags.size());
	}
	
	@Test
	public void addHashtagDifferentHashtag() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		client.AddHashtag(video.getId(), hashtag);
		

		hashtag.setName("hashtag2");
		HttpResponse<String> response = client.AddHashtag(video.getId(), hashtag);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<Hashtag> hashtags = iterableToList(client.ListHashtags());
		assertEquals(2, hashtags.size());
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
