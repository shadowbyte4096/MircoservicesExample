package assessment.videos.cli.subscription.videos;

import assessment.videos.cli.dto.HashtagDTO;
import assessment.videos.cli.domain.Video;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;

@Client("${videos.url:`http://localhost:8082/video`}")
public interface VideosClient {

	@Post("/{userId}")
	Iterable<Video> GetSuggestions(long userId, @Body HashtagDTO details);
}
