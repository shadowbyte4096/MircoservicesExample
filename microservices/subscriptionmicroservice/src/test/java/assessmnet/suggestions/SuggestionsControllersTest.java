package assessmnet.suggestions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import assessment.domain.Video;
import assessment.domain.Hashtag;
import assessment.domain.User;
import assessment.dto.HashtagDTO;
import assessment.repositories.VideoRepository;
import assessment.repositories.HashtagRepository;
import assessment.repositories.UserRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class SuggestionsControllersTest {

	private static Logger logger = LoggerFactory.getLogger("testLogger");
	
	@Inject
	SuggestionsClient client;

	@Inject
	VideoRepository videoRepo;

	@Inject
	UserRepository userRepo;
	
	@Inject
	HashtagRepository hashtagRepo;

	@BeforeEach
	public void clean() {
		userRepo.deleteAll();
		hashtagRepo.deleteAll();
		videoRepo.deleteAll();
	}

	@Test
	public void wrongUser() {
		HashtagDTO dto = new HashtagDTO();
		dto.setName("hashtag");
		Iterable<Video> iterVideos = client.GetSuggestions(1, dto);
		assertNull(iterVideos);
	}
	
	@Test
	public void noHashtags() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		HashtagDTO dto = new HashtagDTO();
		dto.setName("hashtag");
		Iterable<Video> iterVideos = client.GetSuggestions(1, dto);
		assertNull(iterVideos);
	}

	@Test
	public void noMatchingHashtags() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("NotHashtag");
		hashtagRepo.save(hashtag);
		
		HashtagDTO dto = new HashtagDTO();
		dto.setName("hashtag");
		Iterable<Video> iterVideos = client.GetSuggestions(1, dto);
		assertNull(iterVideos);
	}

	@Test
	public void hashtagHasNoVideos() { //shouldnt be possible
	}
	
	@Test
	public void hasVideo() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		Video video = new Video();
		video.setId((long) 2);
		videoRepo.save(video);
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		Set<Video> vids = hashtag.getVideos();
		if (vids == null) {
			vids = new HashSet<Video>();
		}
		vids.add(video);
		hashtag.setVideos(vids);
		hashtagRepo.save(hashtag);
		
		HashtagDTO dto = new HashtagDTO();
		dto.setName("hashtag");
		List<Video> videos = iterableToList(client.GetSuggestions(1, dto));
		assertEquals(1, videos.size());
	}
	

	@Test
	public void has12Videos() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		Set<Video> videoSet = new HashSet<>();
		for(Long i = (long) 1; i < 13; i++) {
			Video video = new Video();
			video.setId(i);
			videoRepo.save(video);
			videoSet.add(video);
		}
		
		
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		hashtag.setVideos(videoSet);
		hashtagRepo.save(hashtag);
		
		HashtagDTO dto = new HashtagDTO();
		dto.setName("hashtag");
		List<Video> videos = iterableToList(client.GetSuggestions(1, dto));
		assertEquals(10, videos.size());
	}
	
	@Test
	public void hasWatchedSomeVideos() {
		Set<Video> hashtagVideos = new HashSet<>();
		Set<Video> userVideos = new HashSet<>();
		for(Long i = (long) 1; i < 17; i++) {
			Video video = new Video();
			video.setId(i);
			videoRepo.save(video);
			hashtagVideos.add(video);
			if (i % 2 == 0) {
				userVideos.add(video);
			}
		}
		

		User user = new User();
		user.setId((long) 1);
		user.setVideos(userVideos);
		userRepo.save(user);
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		hashtag.setVideos(hashtagVideos);
		hashtagRepo.save(hashtag);
		
		HashtagDTO dto = new HashtagDTO();
		dto.setName("hashtag");
		List<Video> videos = iterableToList(client.GetSuggestions(1, dto));
		assertEquals(10, videos.size());
		int count = 0;
		for(Video video : videos) {
			if (video.getId() % 2 != 0) {
				count++;
			}
		}
		assertEquals(8, count);
	}
	
	@Test
	public void priotisesNotWatchedVideos() {
		Set<Video> hashtagVideos = new HashSet<>();
		Set<Video> userVideos = new HashSet<>();
		for(Long i = (long) 1; i < 30; i++) {
			Video video = new Video();
			video.setId(i);
			videoRepo.save(video);
			hashtagVideos.add(video);
			if (i % 2 == 0) {
				userVideos.add(video);
			}
		}
		

		User user = new User();
		user.setId((long) 1);
		user.setVideos(userVideos);
		userRepo.save(user);
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		hashtag.setVideos(hashtagVideos);
		hashtagRepo.save(hashtag);
		
		HashtagDTO dto = new HashtagDTO();
		dto.setName("hashtag");
		List<Video> videos = iterableToList(client.GetSuggestions(1, dto));
		assertEquals(10, videos.size());
		for(Video video : videos) {
			assertTrue(video.getId() % 2 != 0);
		}
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
