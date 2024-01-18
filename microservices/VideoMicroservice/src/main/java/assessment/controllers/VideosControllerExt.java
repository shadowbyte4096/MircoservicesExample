package assessment.controllers;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import assessment.domain.Hashtag;
import assessment.domain.Video;
import assessment.domain.User;
import assessment.dto.VideoDTO;

@Controller("/video")
public class VideosControllerExt extends VideosController{
	
	@Override	
	@Get("/")
	public Iterable<Video> ListVideos() {
		return videoRepo.findAll();
	}

	@Override
	@Transactional
	@Post("/{userId}")
	public HttpResponse<String> AddVideo(long userId, @Body VideoDTO details) {
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}
		User user = oUser.get();
		
		Video video = new Video();
		video.setTitle(details.getTitle());
		video.setUser(user);
		videoRepo.save(video);

		Set<Video> videos = user.getVideos();
		if (videos == null) {
			videos = new HashSet<Video>();
		}
		videos.add(video);
		userRepo.update(user);
		
		producer.VideoAdded(video.getId(), video);

		return HttpResponse.created(URI.create("/videos/" + video.getId()));
	}

	@Override
	@Get("/{id}")
	public VideoDTO GetVideo(long id) {
		return videoRepo.findOne(id).orElse(null);
	}

	@Override
	@Put("/{id}")
	public HttpResponse<Void> UpdateVideo(long id, @Body VideoDTO details) {
		Optional<Video> oVideo = videoRepo.findById(id);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound();
		}

		Video video = oVideo.get();
		if (details.getTitle() != null) {
			video.setTitle(details.getTitle());
		}
		videoRepo.update(video);
		return HttpResponse.ok();
	}

	@Override
	@Transactional
	@Get("/user/{userId}")
	public Iterable<Video> ListByUser(Long userId) {
		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return null;
		}

		User user = oUser.get();
		return user.getVideos();
	}

	@Override
	@Get("/hashtag/{hashtagId}")
	public Iterable<Video> ListByHashtag(Long hashtagId) {
		Optional<Hashtag> oHashtag = hashtagRepo.findById(hashtagId);
		if (oHashtag.isEmpty()) {
			return null;
		}

		Hashtag hashtag = oHashtag.get();
		
		return hashtag.getVideos();
	}
}