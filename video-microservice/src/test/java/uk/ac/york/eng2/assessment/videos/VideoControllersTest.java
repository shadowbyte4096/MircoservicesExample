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
import uk.ac.york.eng2.assessment.videos.domain.Video;
import uk.ac.york.eng2.assessment.videos.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.VideoDTO;
import uk.ac.york.eng2.assessment.videos.events.VideosProducer;
import uk.ac.york.eng2.assessment.videos.repositories.VideosRepository;
import uk.ac.york.eng2.assessment.videos.repositories.ReactionRepository;
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
	
	@Inject
	ReactionRepository reacitonRepo;

	/*
	 * We mock the Kafka producer here, so we can just test that it's called,
	 * rather than actually checking if the event fully went through.
	 */
	private final Map<Long, User> watchedVideos = new HashMap<>();

//	@MockBean(VideosProducer.class)
//	VideosProducer testProducer() {
//		return (key, value) -> { watchedVideos.put(key,  value); };
//	}

	@BeforeEach
	public void clean() {
		repo.deleteAll();
		userRepo.deleteAll();
		watchedVideos.clear();
	}

	@Test
	public void noVideos() {
		Iterable<Video> iterVideos = client.list();
		assertFalse(iterVideos.iterator().hasNext(), "Service should not list any videos initially");
	}

	@Test
	public void addVideo() {
		final String videoTitle = "Container Security";

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
		Video video = new Video();
		video.setTitle("Container Security");
		repo.save(video);

		VideoDTO videoDTO = client.getVideo(video.getId());
		assertEquals(video.getTitle(), videoDTO.getTitle(), "Title should be fetched correctly");
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

	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
