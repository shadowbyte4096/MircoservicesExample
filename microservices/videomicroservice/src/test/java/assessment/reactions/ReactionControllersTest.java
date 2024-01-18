package assessment.reactions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import assessment.domain.Video;
import assessment.domain.Reaction;
import assessment.domain.User;
import assessment.dto.ReactionDTO;
import assessment.repositories.VideoRepository;
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
		User tempUser = new User();
		tempUser.setUsername("name");
		Video tempVideo = new Video();
		tempVideo.setTitle("title");
		userRepo.save(tempUser);
		tempVideo.setUser(tempUser);
		videoRepo.save(tempVideo);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		HttpResponse<String> response = client.AddReaction(tempVideo.getId(), tempUser.getId(), reaction);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");
		
		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
		assertEquals(1, reactions.size());
		assertEquals(0, reactions.get(0).getReaction());
	}

	@Test
	public void GetReaction() {
		User tempUser = new User();
		tempUser.setUsername("name");
		Video tempVideo = new Video();
		tempVideo.setTitle("title");
		userRepo.save(tempUser);
		tempVideo.setUser(tempUser);
		videoRepo.save(tempVideo);
		
		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		client.AddReaction(tempVideo.getId(), tempUser.getId(), reaction);
		Reaction result = client.GetReaction(tempVideo.getId(), tempUser.getId());
		
		assertEquals(result.getReaction(), reaction.getReaction());
	}
	
	@Test
	public void UpdateReaction() {
		User tempUser = new User();
		tempUser.setUsername("name");
		Video tempVideo = new Video();
		tempVideo.setTitle("title");
		userRepo.save(tempUser);
		tempVideo.setUser(tempUser);
		videoRepo.save(tempVideo);

		ReactionDTO reaction = new ReactionDTO();
		reaction.setReaction(0);
		client.AddReaction(tempVideo.getId(), tempUser.getId(), reaction);
		
		int newReaction = 1;
		ReactionDTO dto = new ReactionDTO();
		dto.setReaction(newReaction);
		
		HttpResponse<String> response = client.UpdateReaction(tempVideo.getId(), tempUser.getId(), dto);
		assertEquals(HttpStatus.OK, response.getStatus(), "Update should be successful");
		
		List<Reaction> reactions = iterableToList(reactionRepo.findAll());
		assertEquals(1, reactions.size());
		assertEquals(newReaction, reactions.get(0).getReaction());
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
