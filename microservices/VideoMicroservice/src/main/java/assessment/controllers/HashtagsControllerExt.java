package assessment.controllers;

import java.net.URI;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import assessment.domain.Hashtag;
import assessment.domain.Video;
import assessment.dto.HashtagDTO;

@Controller("/hashtag")
public class HashtagsControllerExt extends HashtagsController{

	@Override
	@Get("/")
	public Iterable<Hashtag> ListHashtags() {
		return hashtagRepo.findAll();
	}
	
	@Override
	@Transactional
	@Post("/{videoId}")
	public HttpResponse<String> AddHashtag(long videoId, @Body HashtagDTO details) {
		Optional<Video> oVideo = videoRepo.findById(videoId);
		if (oVideo.isEmpty()) {
			return HttpResponse.notFound(String.format("Video %d not found", videoId));
		}
		Video video = oVideo.get();
		for (Hashtag hashtag : hashtagRepo.findAll()) {
			if (!hashtag.getName().equals(details.getName())) {
				continue;
			}
			hashtag.getVideos().add(video);
			hashtagRepo.update(hashtag);
			return HttpResponse.ok();
		}
		Hashtag hashtag = new Hashtag();
		hashtag.setName(details.getName());
		Set<Video> videos = hashtag.getVideos();
		if (videos == null) {
			videos = new HashSet<Video>();
		}
		videos.add(video);
		hashtag.setVideos(videos);
		hashtagRepo.save(hashtag);
		producer.HashtagAdded(videoId, hashtag);
		return HttpResponse.created(URI.create("/hashtags/" + hashtag.getId()));	
	}
	
	
	
	
	
	
	
}