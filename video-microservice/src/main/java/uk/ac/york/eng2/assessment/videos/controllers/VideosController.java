package uk.ac.york.eng2.assessment.videos.controllers;

import java.net.URI;
import java.util.Optional;

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
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.VideoDTO;
import uk.ac.york.eng2.assessment.videos.events.VideosProducer;
import uk.ac.york.eng2.assessment.videos.repositories.VideosRepository;
import uk.ac.york.eng2.assessment.videos.repositories.UsersRepository;

@Controller("/videos")
public class VideosController {

	@Inject
	VideosRepository repo;

	@Inject
	UsersRepository userRepo;

	@Inject
	VideosProducer producer;

	@Get("/")
	public Iterable<Video> list() {
		return repo.findAll();
	}

	@Post("/")
	public HttpResponse<Void> add(@Body VideoDTO videoDetails) {
		Video video = new Video();
		video.setTitle(videoDetails.getTitle());
		video.setYear(videoDetails.getYear());

		repo.save(video);

		return HttpResponse.created(URI.create("/videos/" + video.getId()));
	}

	@Get("/{id}")
	public VideoDTO getVideo(long id) {
		return repo.findOne(id).orElse(null);
	}

	@Transactional
	@Put("/{id}")
	public HttpResponse<Void> updateVideo(long id, @Body VideoDTO videoDetails) {
		Optional<Video> video = repo.findById(id);
		if (video.isEmpty()) {
			return HttpResponse.notFound();
		}

		Video b = video.get();
		if (videoDetails.getTitle() != null) {
			b.setTitle(videoDetails.getTitle());
		}
		if (videoDetails.getYear() != null) {
			b.setYear(videoDetails.getYear());
		}
		repo.update(b);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteVideo(long id) {
		Optional<Video> video = repo.findById(id);
		if (video.isEmpty()) {
			return HttpResponse.notFound();
		}

		repo.delete(video.get());
		return HttpResponse.ok();
	}

	@Get("/{id}/watchers")
	public Iterable<User> getWatchers(long id) {
		Optional<Video> oVideo = repo.findById(id);
		if (oVideo.isEmpty()) {
			return null;
		}
		return oVideo.get().getWatchers();
	}

	@Transactional
	@Put("/{videoId}/watchers/{userId}")
	public HttpResponse<String> addWatcher(long videoId, long userId) {
		Optional<Video> oVideo = repo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		Optional<User> oUser = userRepo.findById(userId);
		if (oUser.isEmpty()) {
			return HttpResponse.notFound(String.format("User %d not found", userId));
		}

		Video video = oVideo.get();
		if (video.getWatchers().add(oUser.get())) {
			repo.update(video);
			producer.watchVideo(videoId, video);
		}

		return HttpResponse.ok(String.format("User %d added as watcher of video %d", userId, videoId));
	}

	@Transactional
	@Delete("/{videoId}/watchers/{userId}")
	public HttpResponse<String> removeWatcher(long videoId, long userId) {
		Optional<Video> oVideo = repo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}

		/*
		 * DELETE should be idempotent, so it's OK if the mentioned
		 * user was not a watcher of the video.
		 */ 
		Video video = oVideo.get();
		video.getWatchers().removeIf(u -> userId == u.getId());
		repo.update(video);

		return HttpResponse.ok();
	}

}
