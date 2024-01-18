package assessment.reactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
import assessment.domain.Reaction;
import assessment.domain.User;
import assessment.dto.ReactionDTO;
import assessment.repositories.VideoRepository;
import assessment.repositories.HashtagRepository;
import assessment.repositories.ReactionRepository;
import assessment.repositories.UserRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class ReactionControllersTest {

	private static Logger logger = LoggerFactory.getLogger("testLogger");
	
	@Inject
	ReactionsClient client;

	@Inject
	VideoRepository videoRepo;

	@Inject
	UserRepository userRepo;
	
	@Inject
	ReactionRepository reactionRepo;
	
	@Inject
	HashtagRepository hashtagRepo;

	/*
	 * We mock the Kafka producer here, so we can just test that it's called,
	 * rather than actually checking if the event fully went through.
	 */

	@BeforeEach
	public void clean() {
		reactionRepo.deleteAll();
		videoRepo.deleteAll();
		userRepo.deleteAll();
	}

	@Test
	public void addReaction() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		HttpResponse<String> response = client.AddReaction(video.getId(), user.getId(), reaction);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");
		
		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
		assertEquals(1, reactions.size());
		assertEquals(0, reactions.get(0).getReaction());
	}
	
	@Test
	public void addReactionWrongUser() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		HttpResponse<String> response = client.AddReaction(video.getId(), user.getId() + 1, reaction);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
		
		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
		assertEquals(0, reactions.size());
	}
	

	@Test
	public void addReactionWrongVideo() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		HttpResponse<String> response = client.AddReaction(video.getId() + 1, user.getId(), reaction);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
		
		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
		assertEquals(0, reactions.size());
	}
	
	@Test
	public void addLikedReaction() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(1);
		HttpResponse<String> response = client.AddReaction(video.getId(), user.getId(), reaction);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");
		
		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
		assertEquals(1, reactions.size());
		assertEquals(1, reactions.get(0).getReaction());
	}
	
//	@Test
//	public void addDislikedReaction() {
//		User user = new User();
//		user.setUsername("name");
//		userRepo.save(user);
//		
//		Video video = new Video();
//		video.setTitle("title");
//		video.setUser(user);
//		videoRepo.save(video);
//		
//		ReactionDTO reaction = new ReactionDTO();
//		reaction.setReaction(-1);
//		HttpResponse<String> response = client.AddReaction(video.getId(), user.getId(), reaction);
//		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");
//		
//		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
//		assertEquals(1, reactions.size());
//		assertEquals(-1, reactions.get(0).getReaction());
//	}
//	
//	@Test
//	public void addLikedReactionHasHashtag() {
//		User user = new User();
//		user.setUsername("name");
//		userRepo.save(user);
//		
//		Video video = new Video();
//		video.setTitle("title");
//		video.setUser(user);
//		videoRepo.save(video);
//		
//		Hashtag hashtag = new Hashtag();
//		hashtag.setName("hashtag");
//		Set<Video> videos = new HashSet<Video>();
//		videos.add(video);
//		hashtag.setVideos(videos);
//		hashtagRepo.save(hashtag);
//		
//		ReactionDTO reaction = new ReactionDTO();
//		reaction.setReaction(1);
//		HttpResponse<String> response = client.AddReaction(video.getId(), user.getId(), reaction);
//		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");
//		
//		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
//		assertEquals(1, reactions.size());
//		assertEquals(1, reactions.get(0).getReaction());
//	}

	@Test
	public void GetReaction() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		client.AddReaction(video.getId(), user.getId(), reaction);
		Reaction result = client.GetReaction(video.getId(), user.getId());
		
		assertEquals(result.getReaction(), reaction.getReaction());
	}
	
	@Test
	public void GetReactionWrongUser() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		client.AddReaction(video.getId(), user.getId(), reaction);
		Reaction result = client.GetReaction(video.getId(), user.getId() + 1);
		assertNull(result);
	}
	
	@Test
	public void GetReactionWrongVideo() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		client.AddReaction(video.getId(), user.getId(), reaction);
		Reaction result = client.GetReaction(video.getId() + 1, user.getId());
		assertNull(result);
	}
	
	@Test
	public void UpdateReaction() {
		User user = new User();
		user.setUsername("name");
		userRepo.save(user);
		
		Video video = new Video();
		video.setTitle("title");
		video.setUser(user);
		videoRepo.save(video);

		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		client.AddReaction(video.getId(), user.getId(), reaction);
		
		int newReaction = 1;
		ReactionDTO dto = new ReactionDTO();
		dto.setReaction(newReaction);
		
		HttpResponse<String> response = client.UpdateReaction(video.getId(), user.getId(), dto);
		assertEquals(HttpStatus.OK, response.getStatus(), "Update should be successful");
		
		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
		assertEquals(1, reactions.size());
		assertEquals(newReaction, reactions.get(0).getReaction());
	}
	
//	@Test
//	public void UpdateReactionWrongUser() {
//		User user = new User();
//		user.setUsername("name");
//		userRepo.save(user);
//		
//		Video video = new Video();
//		video.setTitle("title");
//		video.setUser(user);
//		videoRepo.save(video);
//
//		ReactionDTO reaction = new ReactionDTO();
//		reaction.setReaction(0);
//		client.AddReaction(video.getId(), user.getId(), reaction);
//		
//		int newReaction = 1;
//		ReactionDTO dto = new ReactionDTO();
//		dto.setReaction(newReaction);
//		
//		HttpResponse<String> response = client.UpdateReaction(video.getId(), user.getId(), dto);
//		assertEquals(HttpStatus.OK, response.getStatus(), "Update should be successful");
//		
//		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
//		assertEquals(1, reactions.size());
//		assertEquals(newReaction, reactions.get(0).getReaction());
//	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
