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
	
	@Get("/{userId}")
	public Iterable<Video> GetSuggestions(long userId, @Body HashtagDTO details) {
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return null;
		}
		User user = oUser.get();
		for (Hashtag hashtag : hashtagRepo.findAll()) {
			if (hashtag.getName() != details.getName()) {
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
}