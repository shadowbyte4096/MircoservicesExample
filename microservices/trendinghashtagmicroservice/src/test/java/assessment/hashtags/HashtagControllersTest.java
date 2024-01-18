package assessment.hashtags;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import assessment.domain.Hashtag;
import assessment.domain.Reaction;
import assessment.repositories.HashtagRepository;
import assessment.repositories.ReactionRepository;

/*
 * We have to disable transactions here, as otherwise the controller will not be
 * able to see changes made by the test.
 */
@MicronautTest(transactional = false, environments = "no_streams")
public class HashtagControllersTest {

	@Inject
	HashtagsClient client;

	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	ReactionRepository reactionRepo;

	/*
	 * We mock the Kafka producer here, so we can just test that it's called,
	 * rather than actually checking if the event fully went through.
	 */

//	@MockBean(VideosProducer.class)
//	VideosProducer testProducer() {
//		return (key, value) -> { watchedVideos.put(key,  value); };
//	}

	@BeforeEach
	public void clean() {
		hashtagRepo.deleteAll();
		reactionRepo.deleteAll();
	}

	@Test
	public void onlyNine() {
		for(Long i = (long) 1; i < 10; i++) {
			Hashtag hashtag = new Hashtag();
			hashtag.setId(i);
			hashtag.setName("h" + i);
			
			hashtagRepo.save(hashtag);
		}
		List<Hashtag> hashtags = iterableToList(client.GetTopTen());
		assertEquals(9, hashtags.size());

	}
	
	@Test
	public void elevenHashtags() {
		for(Long i = (long) 1; i < 12; i++) {
			Hashtag hashtag = new Hashtag();
			hashtag.setId(i);
			hashtag.setName("h" + i);
			
			hashtagRepo.save(hashtag);
		}
		List<Hashtag> hashtags = iterableToList(client.GetTopTen());
		assertEquals(10, hashtags.size());

	}
	
	@Test
	public void orderedByLikes() {
		for(Long id = (long) 1; id < 21; id++) {
			Hashtag hashtag = new Hashtag();
			hashtag.setId(id);
			hashtag.setName("h" + id);
			hashtag.setReactions(new HashSet<Reaction>());
			for(int i = 0; i < id; i++) {
				Reaction reaction = new Reaction();
				Long reactionId = (long) ((30 * id) + i);
				LocalDateTime dateTime = LocalDateTime.now();
				reaction.setId(reactionId);
				reaction.setReaction(1);
				reaction.setTimeCreated(dateTime.toString());
				reactionRepo.save(reaction);
				hashtag.getReactions().add(reaction);
			}
			hashtagRepo.save(hashtag);
		}
		List<Hashtag> hashtags = iterableToList(client.GetTopTen());
		assertEquals(10, hashtags.size());
		for(Hashtag hashtag : hashtags) {
			assertTrue(hashtag.getId() > 10);
		}
	}
	
	@Test
	public void removeOld() {
		for(Long id = (long) 1; id < 21; id++) {
			Hashtag hashtag = new Hashtag();
			hashtag.setId(id);
			hashtag.setName("h" + id);
			hashtag.setReactions(new HashSet<Reaction>());
			Reaction reaction = new Reaction();
			Long reactionId = (long) 30 * id;
			LocalDateTime dateTime = LocalDateTime.now();
			if (id <= 10) {
				dateTime = dateTime.minusHours(2);
			}
			reaction.setId(reactionId);
			reaction.setReaction(1);
			reaction.setTimeCreated(dateTime.toString());
			reactionRepo.save(reaction);
			hashtag.getReactions().add(reaction);
			hashtagRepo.save(hashtag);
			reaction.setHashtag(hashtag);
			reactionRepo.update(reaction);
		}
		List<Hashtag> hashtags = iterableToList(client.GetTopTen());
		assertEquals(10, hashtags.size());
		for(Hashtag hashtag : hashtags) {
			assertTrue(hashtag.getId() > 10);
		}
	}
	
	private <T> List<T> iterableToList(Iterable<T> iterable) {
		List<T> l = new ArrayList<>();
		iterable.forEach(l::add);
		return l;
	}
	
}
