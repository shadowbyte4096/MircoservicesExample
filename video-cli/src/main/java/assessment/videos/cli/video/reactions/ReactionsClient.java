package assessment.videos.cli.video.reactions;


import assessment.videos.cli.dto.ReactionDTO;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.client.annotation.Client;

@Client("${reactions.url:`http://localhost:8080/reaction`}")
public interface ReactionsClient {

	@Post("/{videoId}/{userId}")
	HttpResponse<String> AddReaction(long videoId, long userId, @Body ReactionDTO reactionDetails);

	@Get("/{id}")
	ReactionDTO GetReaction(long id);

	@Put("/{videoId}/{userId}")
	HttpResponse<String> UpdateReaction(long videoId, long userId, @Body ReactionDTO reactionDetails);
}
