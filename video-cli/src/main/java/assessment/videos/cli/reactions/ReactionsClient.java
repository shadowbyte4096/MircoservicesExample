package assessment.videos.cli.reactions;


import assessment.videos.cli.domain.Reaction;
import assessment.videos.cli.domain.User;
import assessment.videos.cli.domain.Video;
import assessment.videos.cli.dto.ReactionDTO;
import assessment.videos.cli.dto.VideoDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("${reactions.url:`http://localhost:8080/reactions`}")
public interface ReactionsClient {

	@Get("/")
	Iterable<Reaction> list();

	@Post("/{videoId}/{userId}")
	HttpResponse<String> add(long videoId, long userId, @Body ReactionDTO reactionDetails);

	@Get("/{id}")
	ReactionDTO getReaction(long id);

	@Put("/{videoId}/{userId}")
	HttpResponse<String> updateReaction(long videoId, long userId, @Body ReactionDTO reactionDetails);
}
