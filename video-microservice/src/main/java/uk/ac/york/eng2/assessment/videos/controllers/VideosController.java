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
import uk.ac.york.eng2.assessment.videos.dto.VideoDTO;
import uk.ac.york.eng2.assessment.videos.events.VideosProducer;
import uk.ac.york.eng2.assessment.videos.repositories.VideosRepository;
import uk.ac.york.eng2.assessment.videos.repositories.HashtagRepository;
import uk.ac.york.eng2.assessment.videos.repositories.ReactionRepository;
import uk.ac.york.eng2.assessment.videos.repositories.UsersRepository;

@Controller("/videos")
public class VideosController {

	@Inject
	VideosRepository repo;

	@Inject
	UsersRepository userRepo;
	
	@Inject
	HashtagRepository hashtagRepo;
	
	@Inject
	ReactionRepository reactionRepo;

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

		repo.save(video);
		
		producer.createVideo(video.getId(), video);

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
		repo.update(b);
		return HttpResponse.ok();
	}

	@Transactional
	@Delete("/{id}")
	public HttpResponse<Void> deleteVideo(long id) {
		Optional<Video> oVideo = repo.findById(id);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound();
		}
		Video video = oVideo.get();
		
		//delete leftover reactions
		for (Reaction reaction: reactionRepo.findAll()) {
			if (reaction.getVideo().getId() != video.getId()) {
				continue;
			}
			else {
				reactionRepo.delete(reaction);
			}
		}
		
		//delete leftover hashtags
		for(Hashtag hashtag : video.getHashtags()) {
			removeHashtag(id, hashtag.getId());
		}
		
		repo.delete(video);
		
		return HttpResponse.ok();
	}

	@Get("/{id}/hashtag")
	public Set<Hashtag> getHashtags(long id) {
		Optional<Video> oVideo = repo.findById(id);
		if (oVideo.isEmpty()) {
			return null;
		}
		return oVideo.get().getHashtags();
	}

	@Transactional
	@Put("/{videoId}/hashtag")
	public HttpResponse<String> addHashtag(long videoId, @Body HashtagDTO hashtagDetails) {
		Optional<Video> oVideo = repo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}
		
		Video video = oVideo.get();
		Hashtag hashtag = new Hashtag();
		hashtag.setName(hashtagDetails.getName());
		
		hashtag.setVideo(video);
			hashtagRepo.save(hashtag);
			repo.update(video);
			return HttpResponse.ok(String.format("hashtag %s of id: %d added as a hashtag of video %d", hashtag.getName(), hashtag.getId(), videoId));
	}

	@Transactional
	@Delete("/{videoId}/{hastagId}")
	public HttpResponse<String> removeHashtag(long videoId, long hastagId) {
		Optional<Video> oVideo = repo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}
		Video video = oVideo.get();
		
		Optional<Hashtag> oHashtag = hashtagRepo.findById(hastagId);
		if (oHashtag.isEmpty()) {
			return HttpResponse.notFound(String.format("Hashtag %d not found", hastagId));
		}
		Hashtag hashtag = oHashtag.get();
		
		video.getHashtags().removeIf(u -> hastagId == u.getId());
		repo.update(video);
		
		hashtagRepo.delete(hashtag);
		
		return HttpResponse.ok();
	}

}
