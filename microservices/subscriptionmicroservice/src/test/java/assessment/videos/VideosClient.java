package assessment.videos;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import assessment.domain.Video;
import assessment.dto.HashtagDTO;

@Client("/video")
public interface VideosClient {
	
	@Post("/{userId}")
	Iterable<Video> GetSuggestions(long userId, @Body HashtagDTO details);

}
