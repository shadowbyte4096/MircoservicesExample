package assessment.hashtags;

import assessment.domain.Hashtag;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("/video")
public interface HashtagsClient {
	
	@Get("/")
	Iterable<Hashtag> GetTopTen();

}
