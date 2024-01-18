package assessment.videos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.producer.MockProducer;
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
public class VideoControllersTest {

	private static Logger logger = LoggerFactory.getLogger("testLogger");
	
	@Inject
	VideosClient client;

	@Inject
	VideoRepository videoRepo;

	@Inject
	UserRepository userRepo;
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@BeforeEach
	public void clean() {
		for (Video video : videoRepo.findAll()) {
			video.setUser(null);
			videoRepo.update(video);
		}
		for (User user : userRepo.findAll()) {
			user.setVideos(new HashSet<Video>());
			userRepo.update(user);
		}
		videoRepo.deleteAll();
		userRepo.deleteAll();
		hashtagRepo.deleteAll();
	}

	@Test
	public void noVideos() {
		Iterable<Video> iterVideos = client.ListVideos();
		assertFalse(iterVideos.iterator().hasNext(), "Service should not list any videos initially");
	}

	@Test
	public void addVideo() {
		final String videoTitle = "Container Security";
		VideoDTO video = new VideoDTO();
		video.setTitle(videoTitle);
		User temp = new User();
		temp.setUsername("user");
		userRepo.save(temp);
		HttpResponse<String> response = client.AddVideo(temp.getId(), video);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<Video> videos = iterableToList(client.ListVideos());
		assertEquals(1, videos.size());
		assertEquals(videoTitle, videos.get(0).getTitle());
	}
	
	@Test
	public void addVideoNoUser() {
		final String videoTitle = "Container Security";
		VideoDTO video = new VideoDTO();
		video.setTitle(videoTitle);
		HttpResponse<String> response = client.AddVideo((long) 1, video);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
	}

	@Test
	public void getVideo() {
		Video video = new Video();
		video.setTitle("Container Security");
		videoRepo.save(video);

		VideoDTO videoDTO = client.GetVideo(video.getId());
		assertEquals(video.getTitle(), videoDTO.getTitle(), "Title should be fetched correctly");
	}

	@Test
	public void getMissingVideo() {
		VideoDTO response = client.GetVideo(0);
		assertNull(response, "A missing video should produce a 404");
	}
	
	@Test
	public void updateVideo() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		VideoDTO video = new VideoDTO();
		video.setTitle("video1");
		client.AddVideo(user.getId(), video);
		
		video.setTitle("video2");
		Long videoId = iterableToList(client.ListVideos()).get(0).getId();
		HttpResponse<Void> response = client.UpdateVideo(videoId, video);
		
		assertEquals(HttpStatus.OK, response.getStatus());
		
		List<Video> videos = iterableToList(client.ListVideos());
		assertEquals(1, videos.size());
		assertEquals("video2", videos.get(0).getTitle());
	}
	
	@Test
	public void updateVideoWrongUser() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		VideoDTO video = new VideoDTO();
		video.setTitle("video1");
		client.AddVideo(user.getId(), video);
		
		video.setTitle("video2");
		Long videoId = iterableToList(client.ListVideos()).get(0).getId();
		HttpResponse<Void> response = client.UpdateVideo(videoId + 1, video);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
	}
	
	@Test
	public void updateVideoNoTitle() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		VideoDTO video = new VideoDTO();
		video.setTitle("video1");
		client.AddVideo(user.getId(), video);
		
		video = new VideoDTO();
		Long videoId = iterableToList(client.ListVideos()).get(0).getId();
		HttpResponse<Void> response = client.UpdateVideo(videoId, video);
		
		assertEquals(HttpStatus.OK, response.getStatus());
		List<Video> videos = iterableToList(client.ListVideos());
		assertEquals(1, videos.size());
		assertEquals("video1", videos.get(0).getTitle());
	}
	

	@Test
	public void listByUserWrongUser() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		VideoDTO video = new VideoDTO();
		video.setTitle("video1");
		client.AddVideo(user.getId(), video);
		
		Iterable<Video> videos = client.ListByUser(user.getId() + 1);
		
		assertNull(videos);
	}
	
	@Test
	public void listByUser1Video() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		VideoDTO video = new VideoDTO();
		video.setTitle("video1");

		client.AddVideo(user.getId(), video);
		
		List<Video> videos = iterableToList(client.ListByUser(user.getId()));

		assertEquals(1, videos.size());
	}
	
	@Test
	public void listByUserMultipleVideo() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		for(int i = 0; i < 5; i++) {
			VideoDTO video = new VideoDTO();
			video.setTitle("video1");
			client.AddVideo(user.getId(), video);
		}		
		
		List<Video> videos = iterableToList(client.ListByUser(user.getId()));

		assertEquals(5, videos.size());
	}
	

	@Test
	public void listByUserMultipleUser() {
		User user1 = new User();
		user1.setUsername("user1");
		userRepo.save(user1);
		
		for(int i = 0; i < 5; i++) {
			VideoDTO video = new VideoDTO();
			video.setTitle("video1");
			client.AddVideo(user1.getId(), video);
		}
		
		User user2 = new User();
		user2.setUsername("user2");
		userRepo.save(user2);
		
		for(int i = 0; i < 5; i++) {
			VideoDTO video = new VideoDTO();
			video.setTitle("video1");
			client.AddVideo(user2.getId(), video);
		}
		
		List<Video> videos = iterableToList(client.ListByUser(user1.getId()));

		assertEquals(5, videos.size());
	}

	@Test
	public void listByHashtagWrongHashtag() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("video1");
		video.setUser(user);
		videoRepo.save(video);
		
		
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		Set<Video> videoSet = new HashSet<Video>();
		videoSet.add(video);
		hashtag.setVideos(videoSet);
		hashtagRepo.save(hashtag);
		
		Iterable<Video> videos = client.ListByHashtag(hashtag.getId() + 1);
		
		assertNull(videos);
	}
	
	@Test
	public void listByHashtag1Video() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		Set<Video> videoSet = new HashSet<Video>();
		Video video = new Video();
		video.setTitle("video");
		video.setUser(user);
		videoRepo.save(video);
		videoSet.add(video);
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		hashtag.setVideos(videoSet);
		hashtagRepo.save(hashtag);
		
		List<Video> videos = iterableToList(client.ListByHashtag(hashtag.getId()));

		assertEquals(1, videos.size());
	}
	
	@Test
	public void listByHashtagMultipleVideo() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		Set<Video> videoSet = new HashSet<Video>();
		for(int i = 0; i < 5; i++) {
			Video video = new Video();
			video.setTitle("video");
			video.setUser(user);
			videoRepo.save(video);
			videoSet.add(video);
		}
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag");
		hashtag.setVideos(videoSet);
		hashtagRepo.save(hashtag);
		
		List<Video> videos = iterableToList(client.ListByHashtag(hashtag.getId()));

		assertEquals(5, videos.size());
	}
	

	@Test
	public void listByHashtagMultipleHashtag() {
		User user = new User();
		user.setUsername("user");
		userRepo.save(user);
		
		Set<Video> videoSet = new HashSet<Video>();
		for(int i = 0; i < 5; i++) {
			Video video = new Video();
			video.setTitle("video");
			video.setUser(user);
			videoRepo.save(video);
			videoSet.add(video);
		}
		
		Hashtag hashtag = new Hashtag();
		hashtag.setName("hashtag1");
		hashtag.setVideos(videoSet);
		hashtagRepo.save(hashtag);
		
		videoSet = new HashSet<Video>();
		for(int i = 0; i < 5; i++) {
			Video video = new Video();
			video.setTitle("video");
			video.setUser(user);
			videoRepo.save(video);
			videoSet.add(video);
		}
		
		Hashtag hashtag2 = new Hashtag();
		hashtag2.setName("hashtag2");
		hashtag2.setVideos(videoSet);
		hashtagRepo.save(hashtag2);
		
		List<Video> videos = iterableToList(client.ListByHashtag(hashtag.getId()));

		assertEquals(5, videos.size());
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}

