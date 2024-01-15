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
import assessment.domain.Hashtag;
import assessment.domain.Video;
import assessment.domain.User;
import assessment.domain.Reaction;
import assessment.dto.HashtagDTO;
import assessment.dto.VideoDTO;
import assessment.dto.UserDTO;
import assessment.dto.ReactionDTO;
import assessment.repositories.HashtagRepository;
import assessment.repositories.VideoRepository;
import assessment.repositories.UserRepository;
import assessment.repositories.ReactionRepository;
import assessment.events.Producers;

@Controller("/reaction")
public class ReactionsController {

	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	VideoRepository videoRepo;
	
	@Inject
	UserRepository userRepo;
	
	@Inject
	ReactionRepository reactionRepo;
	
	@Inject
	Producers producer;
	
	@Get("/{videoId}/{userId}")
	public Reaction GetReaction(long videoId, long userId) {
		for (Reaction reaction : reactionRepo.findAll()) {
			if (reaction.getUser().getId() != userId) {
				continue;
			}
			else if (reaction.getVideo().getId() != videoId) {
				continue;
			}

			return reaction;
		}
		return null;
	}
	
	@Transactional
	@Put("/{videoId}/{userId}")
	public HttpResponse<String> UpdateReaction(long videoId, long userId, @Body ReactionDTO details) {
		for (Reaction reaction : reactionRepo.findAll()) {
			if (reaction.getUser().getId() != userId) {
				continue;
			}
			else if (reaction.getVideo().getId() != videoId) {
				continue;
			}
			else if (reaction.getReaction() == details.getReaction()) {
				return HttpResponse.ok(String.format("Reaction unchanged"));
			}
			
			reaction.setReaction(details.getReaction());
			reactionRepo.update(reaction);
			
			if (details.getReaction() != 0) {
				producer.VideoReacted(videoId, reaction);
				Optional<Video> oVideo = videoRepo.findById(videoId);
				Video video = oVideo.get();
				for (Hashtag hashtag : video.getHashtags()) {
					producer.HashtagReacted(hashtag.getId(), reaction);
				}
			}
			
			return HttpResponse.ok();
		}
		
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}
		
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		
		return HttpResponse.notFound(String.format("User %d has not watched Video %d", userId, videoId));
	}
	
	@Post("/{videoId}/{userId}")
	public HttpResponse<String> AddReaction(long videoId, long userId, @Body ReactionDTO details) {
		Reaction reaction = new Reaction();
		reaction.setReaction(details.getReaction());
		
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}
		Video video = oVideo.get();
		
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		User user = oUser.get();
		
		reaction.setVideo(video);
		reaction.setUser(user);
		
		reactionRepo.save(reaction);
		
		producer.VideoWatched(videoId, userId);
		if (details.getReaction() != 0) {
			producer.VideoReacted(videoId, reaction);
			for (Hashtag hashtag : video.getHashtags()) {
				producer.HashtagReacted(hashtag.getId(), reaction);
			}
		}

		return HttpResponse.created(URI.create("/reactions/" + reaction.getId()));
	}
}