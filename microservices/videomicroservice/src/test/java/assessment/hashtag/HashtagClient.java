package assessment.hashtag;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import assessment.domain.Hashtag;
import assessment.dto.HashtagDTO;

@Client("/hashtag")
public interface HashtagClient {
	
	@Get("/")
	Iterable<Hashtag> ListHashtags();

	@Post("/{videoId}")
	HttpResponse<String> AddHashtag(long videoId, @Body HashtagDTO details);

}
