package assessment.users;

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
import assessment.domain.Video;
import assessment.domain.Reaction;
import assessment.domain.User;
import assessment.dto.UserDTO;
import assessment.dto.VideoDTO;
import assessment.events.Producers;
import assessment.repositories.VideoRepository;
import assessment.repositories.ReactionRepository;
import assessment.repositories.UserRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class UserControllersTest {

	@Inject
	UsersClient client;

	@Inject
	VideoRepository videoRepo;

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
		videoRepo.deleteAll();
		userRepo.deleteAll();
		//watchedVideos.clear();
	}

	@Test
	public void noVideos() {
		Iterable<User> iterUsers = client.ListUsers();
		assertNull(iterUsers);
		//assertFalse(iterVideos.iterator().hasNext(), "Service should not list any videos initially");
	}

	@Test
	public void addVideo() {
		final String userTitle = "Container Security";

		UserDTO user = new UserDTO();
		user.setUsername(userTitle);
		HttpResponse<String> response = client.AddUser(user);
		assertEquals(HttpStatus.CREATED, response.getStatus(), "Update should be successful");

		List<User> users = iterableToList(client.ListUsers());
		assertEquals(1, users.size());
		assertEquals(userTitle, users.get(0).getUsername());
	}

	@Test
	public void getUser() {
		User user = new User();
		user.setUsername("Container Security");
		userRepo.save(user);

		UserDTO userDTO = client.GetUser(user.getId());
		assertEquals(user.getUsername(), userDTO.getUsername(), "Username should be fetched correctly");
	}

	@Test
	public void getMissingUser() {
		UserDTO response = client.GetUser(0);
		assertNull(response, "A missing user should produce a 404");
	}

//	@Test
//	public void updateUser() {
//		User user = new User();
//		user.setUsername("Container Security");
//		userRepo.save(user);
//
//		final String newUsername = "New Username";
//		UserDTO dtoUsername = new UserDTO();
//		dtoUsername.setUsername(newUsername);
//		HttpResponse<Void> response = client.UpdateUser(user.getId(), dtoUsername);
//		assertEquals(HttpStatus.OK, response.getStatus());
//
//		user = userRepo.findById(user.getId()).get();
//		
//		assertEquals(newUsername, user.getUsername());
//	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
