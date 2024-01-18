package assessment.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import assessment.domain.Hashtag;
import assessment.domain.Reaction;

@Controller("/hashtag")
public class HashtagsControllerExt extends HashtagsController {

	@Override
	@Transactional
	@Get("/")
	public Iterable<Hashtag> GetTopTen() {
		for (Reaction reaction : reactionRepo.findAll()) { //delete any old reactions
			LocalDateTime reactionDateTime = LocalDateTime.parse(reaction.getTimeCreated());
			LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
			if (reactionDateTime.isBefore(oneHourAgo)) {
				Hashtag hashtag = reaction.getHashtag(); //need to remove from hashtag before deleting
				hashtag.getReactions().remove(reaction);
				hashtagRepo.update(hashtag);
				reactionRepo.delete(reaction);
			}
		}
		HashMap<Hashtag, Integer> hashtags = new HashMap<Hashtag, Integer>();
		for (Hashtag hashtag : hashtagRepo.findAll()) {
			Integer likes = 0;
			Set<Reaction> reactions = hashtag.getReactions();
			for (Reaction reaction : reactions) {
				if (reaction.getReaction() == 1) {
					likes ++;
				}
			}
			hashtags.put(hashtag, likes); // adds hashtag like amounts to hashmap
		}
		return hashtags
        		.entrySet()
        		.stream()
        		.sorted(Map.Entry.<Hashtag, Integer>comparingByValue().reversed()) //sort by like count
        		.limit(10) //take top 10
        		.map(Map.Entry::getKey)
        		.collect(Collectors.toList());
	}
}