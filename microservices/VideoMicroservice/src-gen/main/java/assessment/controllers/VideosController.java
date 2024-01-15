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
	
	//@Transactional
	//@Get("/") uncomment in src
	public Iterable<Video> ListVideos() {
		return null;
	}
	
	//@Transactional
	//@Post("/{userId}") uncomment in src
	public HttpResponse<String> AddVideo(long userId, @Body VideoDTO details) {
		return null;
	}
	
	//@Transactional
	//@Get("/{id}") uncomment in src
	public VideoDTO GetVideo(long id) {
		return null;
	}
	
	//@Transactional
	//@Put("/{id}") uncomment in src
	public HttpResponse<Void> UpdateVideo(long id, @Body VideoDTO details) {
		return null;
	}
	
	//@Transactional
	//@Get("/user/{userId}") uncomment in src
	public Iterable<Video> ListByUser(Long userId) {
		return null;
	}
	
	//@Transactional
	//@Get("/hashtag/{hashtagId}") uncomment in src
	public Iterable<Video> ListByHashtag(Long hashtagId) {
		return null;
	}
}