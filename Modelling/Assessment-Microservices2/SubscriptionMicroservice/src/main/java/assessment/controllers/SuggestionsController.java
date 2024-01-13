package assessment.controllers;

import java.net.URI;
import java.util.ArrayList;
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

@Controller("/video")
public class SuggestionsController {

	
	@Inject
	UserRepository userRepo;
	
	@Inject
	VideoRepository videoRepo;
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	Producers producer;
	
	//gets videos from a hashtag -> removes ones that a user has watched -> trim to 10 videos = 10 suggested videos for a user subscription
	@Transactional
	@Post("/{userId}") //has to be post to have body
	public Iterable<Video> GetSuggestions(long userId, @Body HashtagDTO details) {
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return null;
		}
		User user = oUser.get();
		for (Hashtag hashtag : hashtagRepo.findAll()) {
			if (!hashtag.getName().equals(details.getName())) {
				continue;
			}
			Set<Video> videos = hashtag.getVideos();
			if (videos.removeAll(user.getVideos())) {
				if (videos.size() > 10) {
					ArrayList<Video> suggested = new ArrayList<Video>();
					for (Video video : videos) {
						if (suggested.size() >= 10) {
							return suggested;
						}
						suggested.add(video);
					}
				}
				return videos;
				
			}
		}		
		return null;
	}
	
	//gets videos from a hashtag -> removes ones that a user has watched -> trim to 10 videos = 10 suggested videos for a user subscription
	@Get("/")
	public HttpResponse<String> AHH() {
		long userId = 1;
		HashtagDTO details = new HashtagDTO();
		details.setName("biran12");
		
		
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound("USER NOT FOUND");
		}
		User user = oUser.get();
		for (Hashtag hashtag : hashtagRepo.findAll()) {
			if (!hashtag.getName().equals(details.getName())) {
				continue;
			}
			Set<Video> videos = hashtag.getVideos();
			if (videos.removeAll(user.getVideos())) {
				if (videos.size() > 10) {
					ArrayList<Video> suggested = new ArrayList<Video>();
					for (Video video : videos) {
						if (suggested.size() >= 10) {
							return HttpResponse.ok(String.format("suggeted size:", suggested.size()));
						}
						suggested.add(video);
					}
				}
				return HttpResponse.ok(String.format("videos size:", videos.size()));
				
			}
		}		
		return HttpResponse.notFound("HASHTAG NOT FOUND");
	}
}