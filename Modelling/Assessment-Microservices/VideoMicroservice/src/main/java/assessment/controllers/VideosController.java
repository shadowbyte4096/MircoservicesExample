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

@Controller("/video")
public class VideosController {

	
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
	
	@Get("/")
	public Iterable<Video> ListVideos() {
		return null;
	}
	
	@Post("/")
	public HttpResponse<Void> AddVideo(@Body VideoDTO details) {
		return null;
	}
	
	@Get("/{id}")
	public VideoDTO GetVideo(long id) {
		return null;
	}
	
	@Put("/{id}")
	public HttpResponse<Void> UpdateVideo(long id, @Body VideoDTO details) {
		return null;
	}
	
	@Delete("/{id}")
	public HttpResponse<Void> DeleteVideo(long id) {
		return null;
	}
}