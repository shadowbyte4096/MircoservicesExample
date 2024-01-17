package assessment.reactions;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;
import assessment.domain.Video;
import assessment.domain.Hashtag;
import assessment.domain.User;
import assessment.dto.HashtagDTO;
import assessment.dto.VideoDTO;

@Client("/hashtag")
public interface HashtagClient {
	
	@Get("/")
	Iterable<Hashtag> ListHashtags();

	@Post("/{videoId}")
	HttpResponse<String> AddHashtag(long videoId, @Body HashtagDTO details);

}
