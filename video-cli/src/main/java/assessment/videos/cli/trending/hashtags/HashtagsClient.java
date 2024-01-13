package assessment.videos.cli.trending.hashtags;


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

@Client("${videos.url:`http://localhost:8081/hashtag`}")
public interface HashtagsClient {
	
	@Get("/")
	Iterable<Hashtag> GetTopTen();
}
