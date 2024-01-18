package assessmnet.suggestions;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import assessment.domain.Video;
import assessment.dto.HashtagDTO;

@Client("/video")
public interface SuggestionsClient {
	
	@Post("/{userId}")
	Iterable<Video> GetSuggestions(long userId, @Body HashtagDTO details);

}
