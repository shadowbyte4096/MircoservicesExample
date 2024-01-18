package assessment.hashtags;

import assessment.domain.Hashtag;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;

@Client("/hashtag")
public interface HashtagsClient {
	
	@Get("/")
	Iterable<Hashtag> GetTopTen();

}
