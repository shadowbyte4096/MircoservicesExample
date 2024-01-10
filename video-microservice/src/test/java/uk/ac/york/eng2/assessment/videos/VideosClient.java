package uk.ac.york.eng2.assessment.videos;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import uk.ac.york.eng2.assessment.videos.domain.Video;
import uk.ac.york.eng2.assessment.videos.domain.User;
import uk.ac.york.eng2.assessment.videos.dto.VideoDTO;

@Client("/videos")
public interface VideosClient {

	@Get("/")
	Iterable<Video> list();

	@Post("/")
	HttpResponse<Void> add(@Body VideoDTO videoDetails);

	@Get("/{id}")
	VideoDTO getVideo(long id);

	@Put("/{id}")
	HttpResponse<Void> updateVideo(long id, @Body VideoDTO videoDetails);

	@Delete("/{id}")
	HttpResponse<Void> deleteVideo(long id);

	@Get("/{id}/watchers")
	public Iterable<User> getWatchers(long id);

	@Put("/{videoId}/watchers/{userId}")
	public HttpResponse<String> addWatcher(long videoId, long userId);	

	@Delete("/{videoId}/watchers/{userId}")
	public HttpResponse<String> removeWatcher(long videoId, long userId);

}
