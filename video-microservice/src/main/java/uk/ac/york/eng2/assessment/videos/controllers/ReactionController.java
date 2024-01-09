package uk.ac.york.eng2.assessment.videos.controllers;

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
import uk.ac.york.eng2.assessment.videos.domain.Video;
import uk.ac.york.eng2.assessment.videos.domain.Hashtag;
import uk.ac.york.eng2.assessment.videos.domain.Reaction;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.HashtagDTO;
import uk.ac.york.eng2.assessment.videos.dto.ReactionDTO;
import uk.ac.york.eng2.assessment.videos.dto.VideoDTO;
import uk.ac.york.eng2.assessment.videos.events.VideosProducer;
import uk.ac.york.eng2.assessment.videos.repositories.VideosRepository;
import uk.ac.york.eng2.assessment.videos.repositories.HashtagRepository;
import uk.ac.york.eng2.assessment.videos.repositories.ReactionRepository;
import uk.ac.york.eng2.assessment.videos.repositories.UsersRepository;

@Controller("/reactions")
public class ReactionController {

	@Inject
	VideosRepository videoRepo;

	@Inject
	UsersRepository userRepo;
	
	@Inject
	ReactionRepository reactionRepo;

	@Inject
	VideosProducer producer;

	@Get("/")
	public Iterable<Reaction> list() {
		return reactionRepo.findAll();
	}

	@Post("/{videoId}/{userId}")
	public HttpResponse<String> add(long videoId, long userId, @Body ReactionDTO reactionDetails) {
		Reaction reaction = new Reaction();
		reaction.setReaction(reactionDetails.getReaction());
		
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
		

		producer.watchVideo(videoId, user);
		if (reactionDetails.getReaction() != 0) {
			producer.reactVideo(videoId, reaction);
		}

		return HttpResponse.created(URI.create("/reactions/" + reaction.getId()));
	}

	@Get("/{id}")
	public ReactionDTO getReaction(long id) {
		return reactionRepo.findOne(id).orElse(null);
	}
	
	@Transactional
	@Put("/{videoId}/{userId}")
	public HttpResponse<String> updateReaction(long videoId, long userId, @Body ReactionDTO reactionDetails) {
		
		
		for (Reaction reaction : list()) {
			if (reaction.getUser().getId() != userId) {
				continue;
			}
			else if (reaction.getVideo().getId() != videoId) {
				continue;
			}
			reaction.setReaction(reactionDetails.getReaction());
			reactionRepo.update(reaction);
			
			if (reaction.getReaction() != 0){
				producer.reactVideo(videoId, reaction);
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

}
