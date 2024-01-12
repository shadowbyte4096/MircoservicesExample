package assessment.videos.cli.hashtags;


import assessment.videos.cli.domain.Hashtag;
import assessment.videos.cli.domain.User;
import assessment.videos.cli.domain.Video;
import assessment.videos.cli.dto.HashtagDTO;
import assessment.videos.cli.dto.VideoDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("${videos.url:`http://localhost:8080/videos`}")
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

	@Get("/{id}/hashtag")
	public Iterable<Hashtag> getHashtags(long id);

	@Put("/{videoId}/hashtag")
	public HttpResponse<String> addHashtag(long videoId, @Body HashtagDTO hashtag);	

	@Delete("/{videoId}/{hashtagId}")
	public HttpResponse<String> removeHashtag(long videoId, long hashtagId);
}
