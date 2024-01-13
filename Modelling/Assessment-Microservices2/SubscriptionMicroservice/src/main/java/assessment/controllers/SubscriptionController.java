package assessment.controllers;

import java.net.URI;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import jakarta.inject.Inject;
import assessment.domain.User;
import assessment.domain.Video;
import assessment.domain.Hashtag;
import assessment.dto.UserDTO;
import assessment.dto.VideoDTO;
import assessment.dto.HashtagDTO;
import assessment.repositories.UserRepository;
import assessment.repositories.VideoRepository;
import assessment.repositories.HashtagRepository;
import assessment.events.Producers;

@Controller("/user")
public class SubscriptionController {

	
	@Inject
	UserRepository userRepo;
	
	@Inject
	VideoRepository videoRepo;
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	Producers producer;
	
	@Get("/{id}")
	public Iterable<Hashtag> ListSubscriptions(long id) {
		Optional<User> oUser = userRepo.findById(id);
		if (oUser.isEmpty()) {
			//return null;
		}
		User user = oUser.get();
		return user.getHashtags();
	}
	
	@Transactional
	@Post("/{userId}")
	public HttpResponse<String> AddSubscription(long userId, @Body HashtagDTO details) {
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		User user = oUser.get();
		for (Hashtag hashtag : hashtagRepo.findAll()) {
			if (!hashtag.getName().equals(details.getName())) {
				continue;
			}
			user.getHashtags().add(hashtag);
			userRepo.update(user);
			return HttpResponse.ok();
		}
		Hashtag hashtag = new Hashtag();
		hashtag.setName(details.getName());
		hashtagRepo.save(hashtag);
		user.getHashtags().add(hashtag);
		userRepo.update(user);
		return HttpResponse.ok();		
	}
	
	@Delete("/{userId}")
	public HttpResponse<String> DeleteSubscription(long userId, @Body HashtagDTO details) {
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		User user = oUser.get();
		for (Hashtag hashtag : user.getHashtags()) {
			if (!hashtag.getName().equals(details.getName())) {
				continue;
			}
			user.getHashtags().remove(hashtag);
			userRepo.update(user);
			return HttpResponse.ok();
		}
		return HttpResponse.notFound(String.format("User subcription %s not found", details.getName()));
	}
}