package uk.ac.york.eng2.assessment.videos;

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

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.annotation.MockBean;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import uk.ac.york.eng2.assessment.videos.clients.VideosClient;
import uk.ac.york.eng2.assessment.videos.domain.Video;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.VideoDTO;
import uk.ac.york.eng2.assessment.videos.events.VideosProducer;
import uk.ac.york.eng2.assessment.videos.repositories.VideosRepository;
import uk.ac.york.eng2.assessment.videos.repositories.UsersRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class VideoControllersTest {

	@Inject
	VideosClient client;

	@Inject
	VideosRepository repo;

	@Inject
	UsersRepository userRepo;

	/*
	 * We mock the Kafka producer here, so we can just test that it's called,
	 * rather than actually checking if the event fully went through.
	 */
	private final Map<Long, Video> watchVideos = new HashMap<>();

	@MockBean(VideosProducer.class)
	VideosProducer testProducer() {
		return (key, value) -> { watchVideos.put(key,  value); };
	}

	@BeforeEach
	public void clean() {
		repo.deleteAll();
		userRepo.deleteAll();
		watchVideos.clear();
	}

	@Test
	public void noVideos() {
		Iterable<Video> iterVideos = client.list();
		assertFalse(iterVideos.iterator().hasNext(), "Service should not list any videos initially");
	}

	@Test
	public void addVideo() {
		final String videoTitle = "Container Security";
		final int videoYear = 2020;

		VideoDTO video = new VideoDTO();
		video.setTitle(videoTitle);
		HttpResponse<Void> response = client.add(video);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<Video> videos = iterableToList(client.list());
		assertEquals(1, videos.size());
		assertEquals(videoTitle, videos.get(0).getTitle());
	}

	@Test
	public void getVideo() {
		Video b = new Video();
		b.setTitle("Container Security");
		repo.save(b);

		VideoDTO videoDTO = client.getVideo(b.getId());
		assertEquals(b.getTitle(), videoDTO.getTitle(), "Title should be fetched correctly");
	}

	@Test
	public void getMissingVideo() {
		VideoDTO response = client.getVideo(0);
		assertNull(response, "A missing video should produce a 404");
	}

	@Test
	public void updateVideo() {
		Video b = new Video();
		b.setTitle("Container Security");
		repo.save(b);

		final String newTitle = "New Title";
		VideoDTO dtoTitle = new VideoDTO();
		dtoTitle.setTitle(newTitle);
		HttpResponse<Void> response = client.updateVideo(b.getId(), dtoTitle);
		assertEquals(HttpStatus.OK, response.getStatus());

		b = repo.findById(b.getId()).get();
		assertEquals(newTitle, b.getTitle());
	}

	@Test
	public void deleteVideo() {
		Video b = new Video();
		b.setTitle("Container Security");
		repo.save(b);

		HttpResponse<Void> response = client.deleteVideo(b.getId());
		assertEquals(HttpStatus.OK, response.getStatus());
		
		assertFalse(repo.existsById(b.getId()));
	}

	@Test
	public void noVideoWatchers() {
		Video b = new Video();
		b.setTitle("Container Security");
		repo.save(b);

		List<User> watchers = iterableToList(client.getWatchers(b.getId()));
		assertEquals(0, watchers.size(), "Videos should not have any watchers initially");
	}

//	@Test
//	public void oneVideoWatcher() {
//		Video b = new Video();
//		b.setTitle("Container Security");
//		b.setWatchers(new HashSet<>());
//		repo.save(b);
//
//		User u = new User();
//		u.setUsername("antonio");
//		userRepo.save(u);
//
//		b.getWatchers().add(u);
//		repo.update(b);
//
//		List<User> response = iterableToList(client.getWatchers(b.getId()));
//		assertEquals(1, response.size(), "The one watcher that was added should be listed");
//	}
//
//	@Test
//	public void addVideoWatcher() {
//		Video b = new Video();
//		b.setTitle("Container Security");
//		repo.save(b);
//
//		final String watcherUsername = "alice";
//		User u = new User();
//		u.setUsername(watcherUsername);
//		userRepo.save(u);
//
//		final Long videoId = b.getId();
//		HttpResponse<String> response = client.addWatcher(videoId, u.getId());
//		assertEquals(HttpStatus.OK, response.getStatus(), "Adding watcher to the video should be successful");
//
//		// Check the producer was called by the addition
//		assertTrue(watchVideos.containsKey(videoId));
//
//		b = repo.findById(videoId).get();
//		assertEquals(1, b.getWatchers().size(), "Video should now have 1 watcher");
//		assertEquals(watcherUsername, b.getWatchers().iterator().next().getUsername());
//	}
//
//	@Test
//	public void deleteVideoWatcher() {
//		Video b = new Video();
//		b.setTitle("Container Security");
//		b.setYear(2020);
//		b.setWatchers(new HashSet<>());
//		repo.save(b);
//
//		User u = new User();
//		u.setUsername("antonio");
//		userRepo.save(u);
//
//		b.getWatchers().add(u);
//		repo.update(b);
//
//		HttpResponse<String> response = client.removeWatcher(b.getId(), u.getId());
//		assertEquals(HttpStatus.OK, response.getStatus(), "Removing watcher to the video should be successful");
//
//		b = repo.findById(b.getId()).get();
//		assertTrue(b.getWatchers().isEmpty(), "Video should have no watchers anymore");
//	}

	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
