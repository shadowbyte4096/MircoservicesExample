package assessment.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import assessment.domain.Hashtag;
import assessment.domain.Reaction;
import assessment.dto.HashtagDTO;
import assessment.dto.ReactionDTO;
import assessment.repositories.HashtagRepository;
import assessment.repositories.ReactionRepository;

@Controller("/hashtag")
public class HashtagsController {

	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	ReactionRepository reactionRepo;
	
	
	@Get("/")
	public Iterable<Hashtag> GetTopTen() {
		HashMap<Hashtag, Integer> hashtags = new HashMap<Hashtag, Integer>();
		for (Hashtag hashtag : hashtagRepo.findAll()) {
			Integer likes = 0;
			for (Reaction reaction : hashtag.getReactions()) {
				if (reaction.getReaction() == 1) {
					likes ++;
				}
			}
			hashtags.put(hashtag, likes);
		}
		return hashtags
        		.entrySet()
        		.stream()
        		.sorted(Map.Entry.<Hashtag, Integer>comparingByValue().reversed())
        		.limit(10)
        		.map(Map.Entry::getKey)
        		.collect(Collectors.toList());
	}
}