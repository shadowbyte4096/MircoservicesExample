package assessment.videos.cli.video.videos;

import assessment.videos.cli.domain.Video;
import assessment.videos.cli.dto.VideoDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("${videos.url:`http://localhost:8080/video`}")
public interface VideosClient {

	@Get("/")
	Iterable<Video> ListVideos();

	@Post("/{userId}")
	HttpResponse<String> AddVideo(Long userId, @Body VideoDTO videoDetails);

	@Get("/{id}")
	VideoDTO GetVideo(long id);

	@Put("/{id}")
	HttpResponse<Void> UpdateVideo(long id, @Body VideoDTO videoDetails);

	@Get("/user/{userId}")
	public Iterable<Video> ListByUser(Long userId);

	@Get("/hashtag/{hashtagId}")
	public Iterable<Video> ListByHashtag(Long hashtagId);
}
