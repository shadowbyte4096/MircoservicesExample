package assessmnet.subscriptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import assessment.domain.Hashtag;
import assessment.domain.User;
import assessment.dto.HashtagDTO;
import assessment.repositories.HashtagRepository;
import assessment.repositories.UserRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class SubscriptionsControllersTest {

	private static Logger logger = LoggerFactory.getLogger("testLogger");
	
	@Inject
	SubscriptionsClient client;

	@Inject
	HashtagRepository hashtagRepo;

	@Inject
	UserRepository userRepo;

	@BeforeEach
	public void clean() {
		userRepo.deleteAll();
		hashtagRepo.deleteAll();
	}
	
	@Test
	public void noUser() {
		Iterable<Hashtag> iterHashtag = client.ListSubscriptions(1);
		assertNull(iterHashtag);
	}
	
	@Test
	public void noSubscriptions() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		Iterable<Hashtag> iterHashtag = client.ListSubscriptions(1);
		assertFalse(iterHashtag.iterator().hasNext(), "Service should not list any Hashtag initially");
	}

	@Test
	public void addSubscription() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		HttpResponse<String> response = client.AddSubscription(1, hashtag);
		assertEquals(HttpStatus.OK, response.getStatus(), "Update should be successful");

		List<Hashtag> hashtags = iterableToList(client.ListSubscriptions(1));
		assertEquals(1, hashtags.size());
		assertEquals(hashtag.getName(), hashtags.get(0).getName());
	}
	
	@Test
	public void addSubscriptionNoUser() {
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		HttpResponse<String> response = client.AddSubscription(1, hashtag);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus(), "user shouldnt be able to be found");
	}
	
	@Test
	public void addSubscriptionHashtagExists() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		User user2 = new User();
		user2.setId((long) 2);
		userRepo.save(user2);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		client.AddSubscription(1, hashtag);
		HttpResponse<String> response = client.AddSubscription(2, hashtag);
		assertEquals(HttpStatus.OK, response.getStatus(), "Update should be successful");

		List<Hashtag> hashtags = iterableToList(client.ListSubscriptions(1));
		assertEquals(1, hashtags.size());
		assertEquals(hashtag.getName(), hashtags.get(0).getName());
	}

	@Test
	public void deleteSubscription() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		client.AddSubscription(1, hashtag);
		HttpResponse<String> response = client.DeleteSubscription(1, hashtag);
		assertEquals(HttpStatus.OK, response.getStatus(), "Update should be successful");

		List<Hashtag> hashtags = iterableToList(client.ListSubscriptions(1));
		assertEquals(0, hashtags.size());
	}
	
	@Test
	public void deleteSubscriptionWrongUser() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		client.AddSubscription(1, hashtag);
		HttpResponse<String> response = client.DeleteSubscription(2, hashtag);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
	}
	
	@Test
	public void deleteSubscriptionWrongSubscription() {
		User user = new User();
		user.setId((long) 1);
		userRepo.save(user);
		
		HashtagDTO hashtag = new HashtagDTO();
		hashtag.setName("hashtag");
		client.AddSubscription(1, hashtag);
		hashtag.setName("notHashtag");
		HttpResponse<String> response = client.DeleteSubscription(1, hashtag);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());

		List<Hashtag> hashtags = iterableToList(client.ListSubscriptions(1));
		assertEquals(1, hashtags.size());
	}
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
}
