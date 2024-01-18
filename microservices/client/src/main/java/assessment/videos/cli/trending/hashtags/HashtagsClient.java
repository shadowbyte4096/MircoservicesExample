package assessment.videos.cli.trending.hashtags;


import assessment.videos.cli.domain.Hashtag;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("${videos.url:`http://localhost:8081/hashtag`}")
public interface HashtagsClient {
	
	@Get("/")
	Iterable<Hashtag> GetTopTen();
}
